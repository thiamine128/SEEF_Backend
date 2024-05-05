package com.software.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.utils.Base64Helper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String contentType, String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(contentType);
        try {
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes), meta);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName

        String path = buildPathFromObjectName(objectName);
        log.info("文件上传到:{}", path);

        return path;
    }

    public void delete(String objectName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    public PostSignature generatePostSignature(String objectName, long expireEndTime, long maxSize) throws UnsupportedEncodingException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxSize);
        policyConds.addConditionItem(MatchMode.Exact, PolicyConditions.COND_KEY, objectName);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_CONTENT_DISPOSITION, "attachment; filename=" + objectName.substring(objectName.lastIndexOf('/') + 1));
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);

        byte[] binaryData = postPolicy.getBytes("utf-8");

        String encodedPolicy = Base64Helper.encode(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);
        return PostSignature.builder()
                .accessKeyId(accessKeyId)
                .objectName(objectName)
                .postSignature(postSignature)
                .encodedPolicy(encodedPolicy)
                .host(getHost()).build();
    }

    public String getHost() {
        return "http://" + bucketName + "." + endpoint;
    }

    public String buildPathFromObjectName(String objectName) {
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);
        return stringBuilder.toString();
    }

    @Data
    @Builder
    public static class PostSignature implements Serializable {
        private String accessKeyId;
        private String encodedPolicy;
        private String postSignature;
        private String objectName;
        private String host;

        private static final long serialVersionUID = 1L;
    }
}

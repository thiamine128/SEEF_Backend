package com.software.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OSSPostSignatureVO implements Serializable {
    private String accessKeyId;
    private String encodedPolicy;
    private String postSignature;
    private String objectName;
    private String host;

    private static final long serialVersionUID = 1L;
}

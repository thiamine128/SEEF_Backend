import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function submitHomeworkAPI(assignmentId,assignmentContext,assignmentFile) {


    // console.log(assignmentId,assignmentContext,assignmentFile);

    try {
        const response = await axios.post(`/assignment/submit?assignmentId=${assignmentId}&assignmentContext=${assignmentContext}&assignmentFile=${assignmentFile}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('提交作业成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when submitHomework",error);
    }



}


export async function handleFile(event){
    const file = event.target.files[0];
    const formData = new FormData();
    const fileUploadData = new FormData();
    formData.append('file', file);
    try {

        const response = await axios.post('user/requestUploadAvatar', formData, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        });

        // console.log(response.data);
        const keyData = {
            "name": 'ok',
            "policy": response.data.data.encodedPolicy,
            "OSSAccessKeyId": response.data.data.accessKeyId,
            "success_action_status": '200',
            "signature": response.data.data.postSignature,
            "key": response.data.data.objectName,
            "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('avatar/', '')}.png`
        };

        for (let key in keyData){
            fileUploadData.append(key, keyData[key]);
        }
        fileUploadData.append('file', file);

        const imgAxios = axios.create({baseURL: '/postFile'});

        const responseImage = await imgAxios.post(``,
        fileUploadData, {
            'Content-Type': 'application/x-www-form-urlencoded'
        });

        if (responseImage.status === 200){
            callSuccess('头像上传成功');
            setTimeout(()=>{
                location.reload();
            }, 1000);
        }

    } catch (error) {
        // console.error(error);
    }
}

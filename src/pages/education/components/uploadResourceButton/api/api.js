import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function uploadResourceAPI(currentDirectory,courseId,filename) {


    // console.log(currentDirectory,courseId,filename);

    try {
        const response = await axios.post(`/courseResource/upload?currentDirectory=${currentDirectory}&courseId=${courseId}&filename=${filename}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('上传资料成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when uoloadResource",error);
    }



}

export function hahaha(){

    callSuccess('上传资料成功');
}

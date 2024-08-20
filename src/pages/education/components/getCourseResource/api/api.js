import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function getResourceAPI(currentDirectory,courseId) {


    // console.log(currentDirectory,courseId);

    try {
        // console.log(`/courseResource/list?currentDirectory=${currentDirectory}&courseId=${courseId}`);
        const response = await axios.get(`/courseResource/list?currentDirectory=${currentDirectory}&courseId=${courseId}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1){
                //callSuccess('获得资料成功成功');
                return response.data.data;
            }
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when getResource",error);
    }



}



export async function deleteResourceAPI(currentDirectory,courseId,filename) {


    // console.log(currentDirectory,courseId,filename);

    try {
        const response = await axios.delete(`/courseResource/delete?currentDirectory=${currentDirectory}&courseId=${courseId}&filename=${filename}`);
        // console.log(response);
   
        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('删除资料成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when deleteResource",error);
    }



}
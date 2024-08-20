import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function createResourceDirectoryAPI(data) {


    // console.log(data);

    try {
        const response = await axios.post('/courseResource/mkdir',data);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1) {
                // callSuccess('创建目录成功');
            }
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when createResourceDirectory",error);
    }



}


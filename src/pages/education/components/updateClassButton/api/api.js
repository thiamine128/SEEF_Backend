import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function updateClassAPI(data) {


    // console.log(data);
    // 添加班级
    try {
        const response = await axios.post('/course/updateClass', data);
        // console.log(response.data);

        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('更新班级信息成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when updateClass",error);
    }

}

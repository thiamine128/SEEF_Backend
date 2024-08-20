import axios from 'axios';
import {callError, callSuccess} from "@/callMessage";

export async function joinClassAPI(classId) {
    try {
        const response = await axios.post(`/course/requestJoinClass`, classId);
        if (response.status === 200) {
            if (response.data.code === 1) {
                // console.log(response.data);
                callSuccess('申请班级成功，请等待审核');
                return response.data.data;
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('申请加入班级时发生错误', error);
    }
}

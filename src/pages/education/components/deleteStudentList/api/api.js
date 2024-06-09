import axios from 'axios';
import {callError, callSuccess} from "@/callMessage";

export async function deleteStudentAPI(params) {
    try {
        // console.log(params);
        const response = await axios.post(`/course`, params);
        // console.log(response.data);
        if (response.status === 200) {
            if (response.data.code === 1) {
                callSuccess('删除学生成功');
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('删除学生时出错', error);
    }
}

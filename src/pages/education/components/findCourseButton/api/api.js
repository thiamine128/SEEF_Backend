import axios from 'axios';
import {callError, callSuccess} from "@/callMessage";

export async function findCourseAPI(data) {
    try {
        let params = new URLSearchParams(data);
        const response = await axios.get('/course/list', { params });
        if (response.status === 200) {
            if (response.data.code === 1) {
                //callSuccess('查询课程成功');
                return response.data.data.records;
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('查询课程时发生错误', error);
    }
}

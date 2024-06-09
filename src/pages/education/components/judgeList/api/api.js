import axios from 'axios';
import {callError, callSuccess} from "@/callMessage";

export async function getJudgeListAPI(page, pageSize, courseId) {
    try {
        const response = await axios.get(`/course/getJoinClassRequests?page=${page}&pageSize=${pageSize}&courseId=${courseId}`);
        if (response.status === 200) {
            if (response.data.code === 1) {
                // callSuccess('查询申请班级名单成功');
                // console.log(response.data.data);
                return response.data.data.records;
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('查询申请班级名单时发生错误', error);
    }
}

export async function judgeAPI(params) {
    try {
        const response = await axios.post(`/course/pendJoinClassRequest`, JSON.stringify(params),  {headers: {
            "Content-Type": "application/json"}
        });
        if (response.status === 200) {
            if (response.data.code === 1) {
                callSuccess('审批成功');
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('审配申请加入班级名单时发生错误', error);
    }
}

import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function addTAAPI(courseId, classId, studentAccount) {
    try {
        const response =
            await axios.post(`/TA/addTA?studentAccount=${studentAccount}&classId=${classId}&courseId=${courseId}`);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.constructor)
                callSuccess('添加助教到班级成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('添加助教到班级时出错\n' + error);
    }
}

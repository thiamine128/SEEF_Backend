import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function deleteTAAPI(courseId, classId, studentId) {
    try {
        const response =
            await axios.delete(`/TA/deleteTA?studentId=${studentId}&classId=${classId}&courseId=${courseId}`);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.constructor)
                callSuccess('从班级删除助教成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('从班级删除助教时出错\n' + error);
    }
}

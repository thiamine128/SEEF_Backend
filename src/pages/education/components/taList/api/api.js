import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function getTA(classId) {
    try {
        const response =
            await axios.get(`/TA/listCourseTA?courseId=${classId}`);
        // console.log(response);
        if(response.status === 200) {
            return response.data.data
        }
    } catch (error) {
        callError('查询助教时出错\n' + error);
    }
}

import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function deleteTeacherAPI(classId, teacherAccount) {
    try {
        const response =
            await axios.delete(`/course/deleteTeacherFromClass?teacherAccount=${teacherAccount}&classId=${classId}`);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.constructor)
                callSuccess('取消教师班级分配成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('取消教师班级分配时出错\n' + error);
    }
}

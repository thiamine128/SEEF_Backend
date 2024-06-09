// 添加教师到课程组
import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function addTeacherToCourseAPI(teacherAccount, courseId) {
    try{
        const response =
            await axios.post(`/course/addTeacher?teacherAccount=${teacherAccount}&courseId=${courseId}`);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.code === 1)
                callSuccess('添加教师到课程组成功');
            else {
                callError(response.data.msg);
            }
        }
        else {
            callError('网络错误');
        }
    } catch (error) {
        callError("some errors happened when add teacher to course");
    }
}

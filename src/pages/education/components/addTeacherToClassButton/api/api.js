// 添加教师到班级
import axios from "axios";
import {callError, callSuccess} from "@/callMessage";
import {getClassData} from "@/pages/education/api";

export async function addTeacherToClassAPI(teacherAccount, classId) {
    try {
        const classInfo = await getClassData(classId);
        const courseId = classInfo.courseId;
        const response0 = await axios.post(`/course/addTeacher?teacherAccount=${teacherAccount}&courseId=${courseId}`);
        const response =
            await axios.post(`/course/addTeacherToClass?teacherAccount=${teacherAccount}&classId=${classId}`);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.constructor)
                callSuccess('分配教师到班级成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('为教师分配班级时出错',error);
    }
}

import axios from 'axios';
import {callError, callSuccess} from "@/callMessage";
import {findCourseAPI} from "@/pages/education/components/findCourseButton/api/api";

export async function findClassAPI(courseId) {
    try {
        const response = await axios.get(`/course/listClass?courseId=${courseId}`);
        if (response.status === 200) {
            if (response.data.code === 1) {
                //callSuccess('查询班级成功');
                return response.data.data;
            } else {
                callError(response.data.msg);
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError('查询班级时发生错误', error);
    }
}

export async function findClassByNameAPI(courseName) {
    try {
        let data = {
            name: courseName,
            page: 1,
            pageSize: 1,
        };
        const courses = await findCourseAPI(data);
        // console.log('this is courses:', courses);
        return await findClassAPI(courses[0].id);
    } catch (error) {
        callError('不存在此课程', error);
        return -1;
    }
}

import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function addTeacherAPI(params) {
    try {
        const response =
            await axios.post(`/admin/addTeacher`, params);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.code === 1)
                callSuccess('添加教师成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('添加助教时出错\n' + error);
    }
}

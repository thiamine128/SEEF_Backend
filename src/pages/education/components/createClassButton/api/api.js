import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function createClassAPI(data) {

    let classId = null;
    // 添加班级
    try {
        const response = await axios.post('/course/addClass', data);
        // console.log(response.data);
        classId = response.data.data.id;
        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('添加班级成功，新班级编号为：' + classId);
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        callError("some errors happened when add a class");
    }

}

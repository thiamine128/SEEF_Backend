import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function getAssignmentAPI(assignmentId) {
    // console.log(assignmentId);
    try {
        const response = await axios.get(`/assignment/studentAssignment?assignmentId=${assignmentId}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1){
                //callSuccess('获得学生作业成功');
                // console.log(response.data.data);
                return response.data.data;

            }
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when getStudentAssignment",error);
    }
}


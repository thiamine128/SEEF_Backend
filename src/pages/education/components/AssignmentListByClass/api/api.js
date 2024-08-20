import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function getAssignmentListByClassAPI(classId) {
    // console.log(classId);
    try {
        // console.log(classId);
        const response = await axios.get(`/assignment/listByClass?classId=${classId}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1){
                //callSuccess('查找班级作业成功');
                return response.data.data;
            }
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when getAssignmentListByClass",error);
    }
}

export async function submitHomeworkAPI(assignmentId,assignmentContext,assignmentFile) {


    // console.log(assignmentId,assignmentContext,assignmentFile);

    try {
        const response = await axios.post(`/assignment/submit?assignmentId=${assignmentId}&assignmentContext=${assignmentContext}&assignmentFile=${assignmentFile}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('提交作业成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when submitHomework",error);
    }



}


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

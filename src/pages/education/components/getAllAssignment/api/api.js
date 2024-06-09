import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function getAllAssignmentAPI(showOutdated) {
    // console.log(showOutdated);
    try {
        const response = await axios.get(`/assignment/studentAssignment?showOutdated=${showOutdated}`);
        // console.log(response);

        if(response.status === 200) {
            if(response.data.code === 1) {
                //callSuccess('查看所有作业成功');
            }
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log(error);
        callError("some errors happened when getAllAssignment",error);
    }
}


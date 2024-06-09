import axios from "axios";
import {callSuccess, callError} from "@/callMessage";

export async function makeAssignmentAPI(data) {


    console.log(data);

    try {
        const response = await axios.post('/assignment/makeAssignment', data);
        // console.log(response.data);

        if(response.status === 200) {
            if(response.data.code === 1) callSuccess('批改作业成功');
            else callError(response.data.msg);
        } else {
            callError('网络错误');
        }
    } catch (error) {
        console.log(error);
        callError("some errors happened when makeAssignment",error);
    }



}


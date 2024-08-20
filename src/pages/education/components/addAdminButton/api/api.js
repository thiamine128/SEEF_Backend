import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function addAdminAPI(params) {
    try {
        const response =
            await axios.post(`/admin/addAdmin`, params);
        // console.log(response);
        if(response.status === 200) {
            if(response.data.code === 1)
                callSuccess('添加管理员成功');
            else
                callError(response.data.msg);
        }
    } catch (error) {
        callError('添加管理员时出错\n' + error);
    }
}

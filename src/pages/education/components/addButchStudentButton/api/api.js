import {callError, callSuccess} from "@/callMessage";
import axios from "axios";

export async function addButchStudentAPI() {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = ".xlsx, xls";
    fileInput.addEventListener("change", handleStudent);
    fileInput.click();
}

export async function handleStudent(event) {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    // console.log(formData);
    try {
        const response = await axios.post('/admin/addButchStudent', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        // console.log(response);
        if(response.data.code === 1) {
            callSuccess('批量添加学生成功');
        }
        else {
            callError(response.data.msg);
        }
    }catch (error) {
        callError('批量添加学生时出错' + error);
    }

}

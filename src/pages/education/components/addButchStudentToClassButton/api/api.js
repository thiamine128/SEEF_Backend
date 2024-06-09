import {callError, callSuccess} from "@/callMessage";
import axios from "axios";

export async function addButchStudentToClassAPI() {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = ".xlsx, xls";
    fileInput.addEventListener("change", handleStudentToClass);
    fileInput.click();
}

export async function handleStudentToClass(event) {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    // console.log(formData);
    try {
        const response = await axios.post('/course/addButchStudentToClass', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        // console.log(response);
        if(response.data.code === 1) {
            callSuccess('批量添加学生到班级成功');
        }
        else {
            callError(response.data.msg);
        }
    }catch (error) {
        callError('批量添加学生时出错' + error);
    }

}

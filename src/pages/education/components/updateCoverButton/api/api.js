import axios from "axios";
import {callSuccess} from "@/callMessage";

export function updateCoverAPI(courseId){
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = ".jpg, .png";
    fileInput.addEventListener("change", (event) => handleCover(event, courseId));
    fileInput.click();
}

export async function handleCover(event, courseId){
    const file = event.target.files[0];
    const formData = new FormData();
    const fileUploadData = new FormData();
    formData.append('file', file);
    try {
        const response = await axios.post(`/course/addCourseCover?CourseId=${courseId}`, formData, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        });

        // console.log(response.data);
        const keyData = {
            "name": 'ok',
            "policy": response.data.data.encodedPolicy,
            "OSSAccessKeyId": response.data.data.accessKeyId,
            "success_action_status": '200',
            "signature": response.data.data.postSignature,
            "key": response.data.data.objectName,
            "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('CourseCover/', '')}.png`
        };

        for (let key in keyData){
            fileUploadData.append(key, keyData[key]);
        }
        fileUploadData.append('file', file);

        const imgAxios = axios.create({baseURL: '/postFile'});

        const responseImage = await imgAxios.post(``,
            fileUploadData, {
                'Content-Type': 'application/x-www-form-urlencoded'
            });

        if (responseImage.status === 200){
            // callSuccess('上传课程封面成功');
            setTimeout(()=>{
                location.reload();
            }, 1000);
        }

    } catch (error) {
        // console.error(error);
    }
}

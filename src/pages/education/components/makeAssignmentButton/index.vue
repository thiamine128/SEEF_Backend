<template>
    <div class="make-Assignment-container">
        <button class="first-button" @click="showMakeAssignmentPage">批改作业</button>

        <div class="make-assignment-modal" v-if="showMakeAssignmentModal">
            <div class="modal-content">
                <span class="close-icon" @click="showMakeAssignmentModal = false">&times;</span>

                <h2>批改作业</h2>

                <form @submit.prevent="MakeAssignment">
                    <div class="input-group">
                        <label for="studentId">学生ID:</label>
                        <input type="text" id="studentId" v-model="studentId" required>
                    </div>

                    <div class="input-group">
                        <label for="assignmentId">作业编号:</label>
                        <input type="text" id="assignmentId" v-model="assignmentId" required>
                    </div>

                    <div class="input-group">
                        <label for="grade">作业得分:</label>
                        <input type="text" id="grade" v-model="grade" required>
                    </div>

                    <div class="input-group">
                        <label for="feedback">作业反馈:</label>
                        <a id="attachmentLink" href="#" download style="display: none;">批改附件链接</a>
                        <left-button :path="require('@/assets/blog/upload.png')" name="上传作业附件" @click="uploadAttachmentUrl" />
                        <!-- <left-button :path="require('@/assets/blog/upload.png')" name="上传附件" @click="upload" /> -->
                    </div>

                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import {makeAssignmentAPI} from "@/pages/education/components/makeAssignmentButton/api/api";

import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
import {callSuccess, callError, callWarning} from "@/callMessage";
import axios from "axios";

export default {
    name: "makeAssignmentButton",
    components: {LeftButton, MdField},
    data() {
        return {
            showMakeAssignmentModal: false,
            studentId: null,
            assignmentId:null,
            grade:null,
            feedback:'',
        };
    },

    methods: {
        showMakeAssignmentPage() {
            this.showMakeAssignmentModal = true;
        },
        MakeAssignment(){

            let data = {
                studentId: this.studentId,
                assignmentId: this.assignmentId,
                grade: this.grade,
                feedback: this.attachmentUrl,

            };
            // console.log(data);
            makeAssignmentAPI(data);
        },



        uploadAttachmentUrl(event){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ""; // 允许接受所有类型的文件
            fileInput.addEventListener("change", async(event) => {
                const file = event.target.files[0];
                const formData = new FormData();
                const fileUploadData = new FormData();
                formData.append('file', file);
                try{
                    const response = await this.$http.post('/assignment/uploadAttachment', formData, {
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
                        "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('assignment/','' )}`
                    };
                    this.attachmentUrl =  `${response.data.data.host + '/' + response.data.data.objectName}`;
                    // console.log(keyData);

                    for (let key in keyData){
                    fileUploadData.append(key, keyData[key]);
                    }
                    fileUploadData.append('file', file);
                    const attachmentAxios = this.$http.create({baseURL: '/postFile'});

                    const responseAttachment = await attachmentAxios.post(``,
                        fileUploadData, {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    });
                    if (responseAttachment.status === 200){
                        const attachmentLink = document.getElementById("attachmentLink");
                        if (this.attachmentUrl) {
                            attachmentLink.href = (this.attachmentUrl);

                            attachmentLink.style.display = "inline"; // 显示附件链接按钮
                        } else {
                            attachmentLink.removeAttribute("href"); // 移除无效的链接
                            attachmentLink.style.display = "none"; // 隐藏附件链接按钮
                        }
                    }
                }catch (error) {
                    // console.error(error);
                }
            });
            fileInput.click();
        },

    }
}
</script>

<style scoped>
.make-assignment-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.make-assignment-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.make-assignment-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    z-index: 1000;
}

.modal-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.close-icon {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 20px;
    cursor: pointer;
}

.input-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
}

.input-group label {
    margin-bottom: 5px;
}

.input-group input {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.input-group input[type="number"] {
    width: 80px;
}

.first-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transition: background-color 0.3s ease;
    font-size: 20px;
}

.first-button:hover {
    background-color: #0056b3;
}
</style>


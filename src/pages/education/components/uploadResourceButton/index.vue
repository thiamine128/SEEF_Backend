<template>
    <div class="submit-homework-container">
        <button class="first-button" @click="showSubmitHomeworkPage">上传资料</button>

        <div class="submit-homework-modal" v-if="showSubmitHomeworkModal">
            <div class="modal-content">
                <span class="close-icon" @click="showSubmitHomeworkModal = false">&times;</span>

                <h3>上传资料</h3>

                <form @submit.prevent="uploadResource">
                    <!-- <div class="input-group">
                        <label for="currentDirectory">所在目录:</label>
                        <input type="text" id="currentDirectory" v-model="currentDirectory" required>
                    </div> -->

                    <!-- <div class="input-group">
                        <label for="courseId">课程编号:</label>
                        <input type="number" id="courseId" v-model="courseId" required>
                    </div> -->
                    <div class="input-group">
                        <label for="filename">文件名称:</label>
                        <input placeholder="请输入文件名称" type="text" id="filename" v-model="filename" required>
                    </div>

                    <div class="input-group">
                        <label for="filename">上传文件:</label>
                        <a id="attachmentLink" href="#" download style="display: none;">查看上传文件</a>
                        <left-button :path="require('@/assets/blog/upload.png')" name="上传资料附件" @click="uploadAttachmentUrl" />
                    </div>
                    <div v-if="selectFile !== null">
                        已拉取文件
                    </div>
                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {hahaha, uploadResourceAPI} from "@/pages/education/components/uploadResourceButton/api/api";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
export default {
    name: "uploadResourceButton",
    components: {LeftButton},
    data() {
        return {
            showSubmitHomeworkModal: false,
            currentDirectory: '/',
            filename:'',
            selectFile:null,
        };
    },
    props:{
        courseId:null,
    },
    methods: {
        showSubmitHomeworkPage() {
            this.showSubmitHomeworkModal = true;
        },
        async uploadResource(event){
            const fileUploadData = new FormData();
            try{
                    const response = await this.$http.post(`/courseResource/upload?currentDirectory=${this.currentDirectory}&courseId=${this.courseId}&filename=${this.filename}`,{
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    });
                    console.log(response.data);
                    const keyData = {
                        "name": 'ok',
                        "policy": response.data.data.encodedPolicy,
                        "OSSAccessKeyId": response.data.data.accessKeyId,
                        "success_action_status": '200',
                        "signature": response.data.data.postSignature,
                        "key": response.data.data.objectName,
                        "Content-Disposition": `attachment; filename=${this.filename}`
                    };
                    this.resourceFile =  `${response.data.data.host + '/' + response.data.data.objectName}`;
                    // console.log(keyData);

                    for (let key in keyData){
                    fileUploadData.append(key, keyData[key]);
                    }
                    fileUploadData.append('file', this.selectFile);
                    const attachmentAxios = this.$http.create({baseURL: '/postFile'});

                    const responseAttachment = await attachmentAxios.post(``,
                        fileUploadData, {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    });
                    if (responseAttachment.status === 200){
                        hahaha();
                        // console.log("responseAttachment");
                        // console.log(responseAttachment);
                        const attachmentLink = document.getElementById("attachmentLink");
                        if (this.resourceFile) {
                            attachmentLink.href = (this.resourceFile);

                            attachmentLink.style.display = "inline"; // 显示附件链接按钮
                        } else {
                            attachmentLink.removeAttribute("href"); // 移除无效的链接
                            attachmentLink.style.display = "none"; // 隐藏附件链接按钮
                        }
                    }
                }catch (error) {
                    // console.error(error);
                }
            
        },



        uploadAttachmentUrl(event){
            // console.log("my CourseId");
            // console.log(this.courseId);
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ""; // 允许接受所有类型的文件
            fileInput.addEventListener("change", async(event) => {
                const file = event.target.files[0];
                this.selectFile = file;
                const formData = new FormData();
                const fileUploadData = new FormData();
                formData.append('file', file);
                
                
                // 将文件名赋值给文件名称输入框
                const filenameInput = document.getElementById('filename');
                filenameInput.value = file.name;
                this.filename = filenameInput.value;


                // try{
                //     const response = await this.$http.post(`/courseResource/upload?currentDirectory=${this.currentDirectory}&courseId=${this.courseId}&filename=${this.filename}`,{
                //     headers: {
                //         'Content-Type': 'application/x-www-form-urlencoded',
                //     },
                //     });
                //     // console.log(response.data);
                //     const keyData = {
                //         "name": 'ok',
                //         "policy": response.data.data.encodedPolicy,
                //         "OSSAccessKeyId": response.data.data.accessKeyId,
                //         "success_action_status": '200',
                //         "signature": response.data.data.postSignature,
                //         "key": response.data.data.objectName,
                //         "Content-Disposition": `attachment; filename=${this.filename}`
                //     };
                //     this.resourceFile =  `${response.data.data.host + '/' + response.data.data.objectName}`;
                //     // console.log(keyData);

                //     for (let key in keyData){
                //     fileUploadData.append(key, keyData[key]);
                //     }
                //     fileUploadData.append('file', file);
                //     const attachmentAxios = this.$http.create({baseURL: '/postFile'});

                //     const responseAttachment = await attachmentAxios.post(``,
                //         fileUploadData, {
                //         'Content-Type': 'application/x-www-form-urlencoded'
                //     });
                //     if (responseAttachment.status === 200){
                //         hahaha();
                //         // console.log("responseAttachment");
                //         // console.log(responseAttachment);
                //         const attachmentLink = document.getElementById("attachmentLink");
                //         if (this.resourceFile) {
                //             attachmentLink.href = (this.resourceFile);

                //             attachmentLink.style.display = "inline"; // 显示附件链接按钮
                //         } else {
                //             attachmentLink.removeAttribute("href"); // 移除无效的链接
                //             attachmentLink.style.display = "none"; // 隐藏附件链接按钮
                //         }
                //     }
                // }catch (error) {
                //     // console.error(error);
                // }
            });
            fileInput.click();
        },

    },
}
</script>

<style scoped>
.submit-homework-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.upload-resource-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.submit-homework-modal {
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
    font-size: 14px;
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

a {
    color: #00b2ff;
    text-decoration: none;
    content: "\f0c8";
    padding: 5px 10px;
    border-radius: 3px;
}

a:hover {
    background-color: #0056b3;
    color: #fff;
}

</style>

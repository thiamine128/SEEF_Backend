<template>
    <div class="publish-homework-container">
        <button class="first-button" @click="showPublishHomeworkPage">发布作业</button>

        <div class="publish-homework-modal" v-if="showPublishHomeworkModal">
            <div class="modal-content">
                <span class="close-icon" @click="showPublishHomeworkModal = false">&times;</span>

                <h3>发布作业</h3>

                <form @submit.prevent="publishHomework">
                    <div class="input-group">
                        <label for="title">作业标题:</label>
                        <input placeholder="请输入作业标题" type="text" id="title" v-model="title" required>
                    </div>

                    <div class="input-group">
                        <label for="description">作业描述:</label>
                        <input placeholder="请输入作业描述" type="text" id="description" v-model="description" required>
                    </div>

                    <div class="input-group">
                        <label for="dueDate">作业截止日期:</label>
                        <input type="datetime-local" id="dueDate" v-model="dueDate" required>
                    </div>

                    <div class="input-group">
                        <label for="attachmentUrl">作业附件:</label>
                        <a id="attachmentLink" href="#" download style="display: none;">附件链接</a>
                        <left-button :path="require('@/assets/blog/upload.png')" name="上传作业附件" @click="uploadAttachmentUrl" />
                        <!-- <left-button :path="require('@/assets/blog/upload.png')" name="上传附件" @click="upload" /> -->
                    </div>

                    <!-- <div class="input-group">
                        <label for="classId">作业班级号:</label>
                        <input type="number" id="classId" v-model="classId" required>
                    </div> -->

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import {publishHomeworkAPI} from "@/pages/education/components/publishHomework/api/api";

import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
import {callSuccess, callError, callWarning} from "@/callMessage";
import axios from "axios";

export default {
    name: "publishHomeworkButton",
    components: {LeftButton, MdField},
    data() {
        return {
            showPublishHomeworkModal: false,
            title: null,
            description: '',
            dueDate:'',
            attachmentUrl:'',
        };
    },
    props: {
        classId: null,
    },
    methods: {
        showPublishHomeworkPage() {
            this.showPublishHomeworkModal = true;
        },
        publishHomework(event){

            const datetimeLocalString = document.getElementById('dueDate').value;

            const timestamp = new Date(datetimeLocalString).getTime();

            // console.log(timestamp);

            let data = {
                title: this.title,
                description: this.description,
                dueDate:this.dueDate,
                attachmentUrl:this.attachmentUrl,
                classId:this.classId,

            };
            publishHomeworkAPI(data);
        },

        upload(){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ".md, .markdown";
            fileInput.addEventListener("change", this.handleFileUpload);
            fileInput.click();
        },
        handleFileUpload(event) {
            const selectedFile = event.target.files[0];
            if (selectedFile) this.readMarkdownFile(selectedFile);
        },
        readMarkdownFile(file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                this.content = event.target.result
            };
            reader.readAsText(file);
        },

        download(){
            const blob = new Blob([this.content], { type: 'text/plain;charset=utf-8' });
            saveAs(blob, (this.mdTitle.length > 0 ? this.mdTitle:'temp')+ '.md');
        },
        save(){
            store.commit('setContent', {
                "title": this.mdTitle,
                "content": this.content
            });
        },



        uploadAttachmentUrl(event){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ""; // 允许接受所有类型的文件
            fileInput.addEventListener("change", async(event) => {
                const file = event.target.files[0];
                const fileName = file.name;
                const fileExtension = fileName.split('.').pop();
                const formData = new FormData();
                const fileUploadData = new FormData();
                formData.append('file', file);
                try{
                    const response = await this.$http.post(`/assignment/uploadDescAttachment`, formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    });
                    console.log(response.data);
                    console.log(response.data);
                    const keyData = {
                        "name": 'ok',
                        "policy": response.data.data.encodedPolicy,
                        "OSSAccessKeyId": response.data.data.accessKeyId,
                        "success_action_status": '200',
                        "signature": response.data.data.postSignature,
                        "key": response.data.data.objectName,
                        "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('assignment/','' )}.${fileExtension}`
                    };
                    this.attachmentUrl =  `${response.data.data.host + '/' + response.data.data.objectName}`;
                    console.log(keyData);

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
.publish-homework-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.publish-homework-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.publish-homework-modal {
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
</style>

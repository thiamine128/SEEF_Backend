<template>
    <div class="submit-homework-container">
        <button class="first-button" @click="showSubmitHomeworkPage">提交作业</button>

        <div class="submit-homework-modal" v-if="showSubmitHomeworkModal">
            <div class="modal-content">
                <span class="close-icon" @click="showSubmitHomeworkModal = false">&times;</span>

                <h3>提交作业</h3>

                <form @submit.prevent="submitHomework">
                    <div class="input-group">
<!--                        <label for="assignmentId">作业编号:</label>-->
                        <input style="width: 188px" placeholder="请输入作业编号" type="number" id="assignmentId" v-model="assignmentId" required min="1">
                    </div>

                    <div class="input-group">
<!--                        <label for="assignmentContext">作业内容:</label>-->
                        <input placeholder="请输入作业内容" type="text" id="assignmentContext" v-model="assignmentContext" required>
                    </div>

                    <div class="input-group">
                        <label for="assignmentFile">作业文件:</label>
                        <a id="attachmentLink" href="#" download style="display: none;">文件链接</a>
                        <left-button :path="require('@/assets/blog/upload.png')" name="上传作业附件" @click="uploadAttachmentUrl" />
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {submitHomeworkAPI} from "@/pages/education/components/submitHomework/api/api";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
export default {
    name: "submitHomeworkButton",
    components: {LeftButton},
    data() {
        return {
            showSubmitHomeworkModal: false,
            assignmentContext: '',
            assignmentFile:'',
            assignmentId:null,
        };
    },

    methods: {
        showSubmitHomeworkPage() {
            this.showSubmitHomeworkModal = true;
        },
        submitHomework(event){

            let data = {
                assignmentId:this.assignmentId,
                assignmentContext:this.assignmentContext,
                assignmentFile:this.assignmentFile,

            };
            submitHomeworkAPI(this.assignmentId,this.assignmentContext,this.assignmentFile);
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
                        "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('assignment/','' )}.txt`
                    };
                    this.assignmentFile =  `${response.data.data.host + '/' + response.data.data.objectName}`;
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
                        if (this.assignmentFile) {
                            attachmentLink.href = (this.assignmentFile);

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

.submit-homework-button{
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
</style>

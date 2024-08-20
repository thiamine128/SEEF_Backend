<template>
    <div class="get-assignment-container">
        <button class="first-button" @click="showGetStudentAssignmentPage">获取所有作业</button>

        <div class="get-assignment-modal" v-if="showGetStudentAssignmentModal">
            <div class="modal-content">
                <span class="close-icon" @click="showGetStudentAssignmentModal = false">&times;</span>

                <h2>获取所有作业</h2>
                <h4>学生获取所有作业</h4>
                <form @submit.prevent="getStudentAssignment">
                    <div class="input-group">
                        <label for="showOutdated">显示已截止作业</label>
                        <input type="text" id="showOutdated" v-model="showOutdated" required>
                    </div>
                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>



    <div>
        <el-dialog v-model="dialogVisible" title="作业展示">
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="作业ID"></el-table-column>
            <el-table-column prop="title" label="作业题目"></el-table-column>
            <el-table-column prop="dueDate" label="截止时间"></el-table-column>
            <el-table-column prop="classId" label="班级号"></el-table-column>
            <el-table-column prop="courseId" label="课程号"></el-table-column>
            <el-table-column prop="submissionTime" label="提交时间"></el-table-column>
            <el-table-column prop="courseName" label="课程名字"></el-table-column>

        </el-table>
        </el-dialog>

                    <!-- <el-table-column label="作业附件">
                <template #default="{ row }">
                    <a v-if="row.attachmentContext" :href=row.attachmentContext download style="display: none;">文件链接</a>
                </template>
            </el-table-column>
            <el-table-column label="批改反馈">
                <template #default="{ row }">
                    <a v-if="row.attachmentContext" :href=row.attachmentContext download style="display: none;">文件链接</a>
                </template>
            </el-table-column> -->
  </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import {getAllAssignmentAPI} from "@/pages/education/components/getAllAssignment/api/api";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
import {callSuccess, callError, callWarning} from "@/callMessage";
import axios from "axios";

export default {
    name: "getAssignmentButton",
    components: {LeftButton, MdField},
    data() {
        return {
            showGetStudentAssignmentModal: false,
            showOutdated:false,
            dialogVisible: false,
            tableData: [],
        };
    },

    methods: {
        showGetStudentAssignmentPage() {
            this.showGetStudentAssignmentModal = true;
        },
        getStudentAssignment(){
            let showOutdated = this.showOutdated;
            let responseData = getAllAssignmentAPI(showOutdated);
            if(responseData!=null){
                this.showTable(responseData)
            }
        },

        showTable(responseData){
            responseData.then(data => {
                this.tableData = data;
            });
            this.dialogVisible = true;
            // console.log("tableData");
            // console.log(this.tableData);
            // console.log(typeof(this.tableData))
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
                    console.error(error);
                }   
            });
            fileInput.click();
        },

    }
}
</script>

<style scoped>
.get-assignment-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.get-assignment-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.get-assignment-modal {
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

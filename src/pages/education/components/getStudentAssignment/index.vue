<template>
    <div class="get-assignment-container">
        <button class="first-button" @click="showGetStudentAssignmentPage">获取作业</button>

        <div class="get-assignment-modal" v-if="showGetStudentAssignmentModal">
            <div class="modal-content">
                <span class="close-icon" @click="showGetStudentAssignmentModal = false">&times;</span>

                <div style="font-size: 24px; margin-bottom: 3px; font-weight: bold; margin-top: 5px">获取学生作业</div>

                <div style="font-size: 10px; color: #8e8e8e">获取班级指定作业号所有学生作业</div>

                <form style="width: 100%; margin-top: 5px" @submit.prevent="getStudentAssignment">
                    <div class="input-group">
<!--                        <label for="assignmentId">作业编号:</label>-->
                        <input style="width: 188px" placeholder="请输入作业编号" type="number" id="assignmentId" v-model="assignmentId" required min="1">
                    </div>
                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>




    <div>
        <el-dialog v-model="dialogVisible" title="作业展示">
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="studentAccount" label="学生ID"></el-table-column>
            <el-table-column prop="assignmentId" label="作业ID"></el-table-column>
            <el-table-column prop="assignmentFile" label="作业附件">
                <template v-slot="scope">
                    <a v-if="scope.row.assignmentFile && scope.row.assignmentFile.startsWith('http://chkbigevent.oss-cn-beijing.aliyuncs.com')" :href="removeExtension(scope.row.assignmentFile)" target="_blank">查看附件</a>
                </template>
            </el-table-column>
            <el-table-column prop="grade" label="成绩"></el-table-column>
            <el-table-column prop="submissionTime" label="提交时间"></el-table-column>
            <el-table-column prop="attachmentContext" label="作业内容"></el-table-column>
            <el-table-column prop="grade" label="成绩"></el-table-column>
            <el-table-column prop="submissionTime" label="提交时间"></el-table-column>
            <el-table-column prop="attachmentContext" label="作业内容"></el-table-column>
            <el-table-column prop="feedback" label="批改反馈"></el-table-column>

        </el-table>
        </el-dialog>
  </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import {getAssignmentAPI} from "@/pages/education/components/getStudentAssignment/api/api";
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
            assignmentId: '',
            dialogVisible: false,
            tableData: [],
        };
    },

    methods: {

        removeExtension(url) {
            let lastIndex = url.lastIndexOf('.');
            if (lastIndex !== -1) {
                return url.slice(0, lastIndex);
            } else {
                return url;
            }
        },

        formatDueDate(timestamp) {
            // 将时间戳转换为 Date 对象
            const date = new Date(timestamp);

            // 格式化日期时间
            const formattedDate = `${date.getFullYear()}/${(date.getMonth() + 1).toString().padStart(2, '0')}/${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

            return formattedDate;
        },
        showGetStudentAssignmentPage() {
            this.showGetStudentAssignmentModal = true;
        },
        getStudentAssignment(){
            let assignmentId=this.assignmentId;
            // console.log(assignmentId);
            let responseData = getAssignmentAPI(assignmentId);
            console.log(responseData)
            console.log(responseData)
            // console.log("responseData");
            // console.log(Array.isArray(responseData))
            if(responseData!=null){
                this.showTable(responseData)
            }
        },


        showTable(responseData){
            responseData.then(data => {
                if (Array.isArray(data)) {
                data.forEach(item => {
                    item.submissionTime = this.formatDueDate(item.submissionTime);
                });
                this.tableData = data;
            }
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
                    let lastIndex = response.data.data.objectName.lastIndexOf('.');
                    this.attachmentUrl =  `${response.data.data.host + '/' + response.data.data.objectName.substring(0, lastIndex)}`;
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
    justify-content: left;
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

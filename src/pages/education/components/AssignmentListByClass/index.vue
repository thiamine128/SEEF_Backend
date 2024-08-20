<template>
    <div class="get-assignmentListByClass-container">
        <button class="first-button" @click="getStudentAssignment">班级作业</button>

        <div class="get-assignmentListByClass-modal" v-if="showGetAssignmentListByClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showGetAssignmentListByClassModal = false">&times;</span>

                <h2>班级作业</h2>
                <h4>获得这个班级的所有作业</h4>
                <form @submit.prevent="getStudentAssignment">
                    <!-- <div class="input-group">
                        <label for="classId">班级Id:</label>
                        <input type="number" id="classId" v-model="classId" required>
                    </div> -->
                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>


    <div>
        <el-dialog v-model="dialogVisible" title="作业展示">
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="作业ID"></el-table-column>
            <el-table-column prop="title" label="作业标题"></el-table-column>
            <el-table-column prop="description" label="作业描述"></el-table-column>
            <el-table-column prop="dueDate" label="截止日期"></el-table-column>
            <el-table-column prop="attachmentUrl" label="附件">
                <template v-slot="scope">
                    <a v-if="scope.row.attachmentUrl.startsWith('http://chkbigevent.oss-cn-beijing.aliyuncs.com')" :href="scope.row.attachmentUrl" target="_blank">查看附件</a>
                </template>
            </el-table-column>
            <el-table-column prop="classId" label="班级号"></el-table-column>
            <el-table-column label="提交作业" v-if="isStudent && !isTA">
                <template v-slot="scope">
                    <!-- <el-button type="primary" @click="submitHomework(scope.row)">提交</el-button> -->
                    <el-button type="primary" @click="submitHomework(scope.row)">提交</el-button>
                </template>
            </el-table-column>

            <el-table-column label="查看提交作业" v-if="isTeacher || isTA">
                <template v-slot="scope">
                    <el-button type="primary" @click="searchHomework(scope.row)">查询</el-button>
                </template>
            </el-table-column>
        </el-table>


        <div class="submit-homework-modal" v-if="showSubmitHomeworkModal">
            <div class="modal-content">
                <!-- <span class="close-icon" @click="showSubmitHomeworkModal = false">&times;</span> -->

                <h2>提交作业</h2>

                <form @submit.prevent="submitHomework11">
                    <!-- <div class="input-group">
                        <label for="assignmentId">作业编号:</label>
                        <input type="number" id="assignmentId" v-model="assignmentId" required>
                    </div> -->

                    <div class="input-group">
                        <label for="assignmentContext">作业内容:</label>
                        <input placeholder="请输入作业内容" type="text" id="assignmentContext" v-model="assignmentContext" required>
                    </div>

                    <div class="input-group">
                        <label for="assignmentFile">作业文件:</label>
                        <a id="attachmentLink" href="#" download style="display: none;">文件链接</a>
                        <left-button :path="require('@/assets/blog/upload.png')" name="上传作业附件" @click="uploadAttachmentUrl" />
                    </div>

                    <button style="width: 100%" type="submit" v-if="isStudent">确认</button>
                </form>
            </div>
        </div>



        <div class="search-homework-modal" v-if="showSearchHomeworkModal">
            <div class="modal-content">
                <!-- <span class="close-icon" @click="showSearchHomeworkModal = false">&times;</span> -->
                <el-dialog v-model="dialogVisible1" title="作业展示">
                    <el-table :data="tableData1" style="width: 100%">
                        <el-table-column prop="studentAccount" label="学生ID"></el-table-column>
                        <el-table-column prop="assignmentId" label="作业ID"></el-table-column>
                        <el-table-column prop="assignmentFile" label="作业文件">
                            <template v-slot="scope">
                                <a v-if="scope.row.assignmentFile && scope.row.assignmentFile.startsWith('http://chkbigevent.oss-cn-beijing.aliyuncs.com')" :href="scope.row.assignmentFile" target="_blank">查看附件</a>
                            </template>
                        </el-table-column>
<!--                        <el-table-column prop="grade" label="成绩"></el-table-column>-->
                        <el-table-column prop="submissionTime" label="提交时间"></el-table-column>
                        <el-table-column prop="attachmentContext" label="作业备注">

                        </el-table-column>
<!--                        <el-table-column prop="feedback" label="批改反馈"></el-table-column>-->

                    </el-table>
                </el-dialog>
            </div>

        </div>


        </el-dialog>

  </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import {getAssignmentListByClassAPI} from "@/pages/education/components/AssignmentListByClass/api/api";
import {submitHomeworkAPI} from "@/pages/education/components/AssignmentListByClass/api/api";
import {getAssignmentAPI} from "@/pages/education/components/AssignmentListByClass/api/api";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";


import store from "@/store/store";
import {callSuccess, callError, callWarning} from "@/callMessage";
import axios from "axios";
import { time } from "echarts";
import {isThisTAAPI} from "@/pages/education/api";

export default {
    name: "getAssignmentListByClassButton",
    components: {
        LeftButton,
        MdField,
    },
    data() {
        return {
            showGetAssignmentListByClassModal: false,
            showSubmitHomeworkModal:false,
            showSearchHomeworkModal: false,
            dialogVisible: false,
            tableData: [],
            dialogVisible1: false,
            tableData1: [],
            assignmentId: null,
            assignmentContext: '',
            attachmentUrl: '',
            isTA: null,

        };
    },
    props: {
        classId: null,
    },
    computed: {
        isStudent() {
            return store.getters.getEduIdentity === 'student';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        },
    },

    async mounted() {
        const debounce = (fn, delay) => {
            let timer;
            return (...args) => {
                if (timer) {
                    clearTimeout(timer);
                }
                timer = setTimeout(() => {
                    fn(...args);
                }, delay);
            };
        };
        const resizeObserver = window.ResizeObserver;
        window.ResizeObserver = class ResizeObserver extends resizeObserver {
            constructor(callback) {
                callback = debounce(callback, 200);
                super(callback);
            }
        };

        this.isTA = await isThisTAAPI(this.classId);
    },
    methods: {


        formatDueDate(timestamp) {
            // 将时间戳转换为 Date 对象
            const date = new Date(timestamp);

            // 格式化日期时间
            const formattedDate = `${date.getFullYear()}/${(date.getMonth() + 1).toString().padStart(2, '0')}/${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

            return formattedDate;
        },





        showTable1(responseData){
            responseData.then(data => {
                if (Array.isArray(data)) {
                data.forEach(item => {
                    item.submissionTime = this.formatDueDate(item.submissionTime);
                });
                this.tableData1 = data;
            }
            });
            this.dialogVisible1 = true;
            console.log("tableData1");
            // console.log(this.tableData1);
            console.log("tableData1");
            // console.log(this.tableData1);
            // console.log(typeof(this.tableData))
        },





        showGetAssignmentListByClassPage() {
            this.showGetAssignmentListByClassModal = true;
        },






        submitHomework(row){
            this.showSubmitHomeworkModal = true;
            this.assignmentId = row.id;
        },
        submitHomework11(){
            // console.log("this.assignmentId,this.assignmentContext,this.assignmentFile");
            // console.log(this.assignmentId,this.assignmentContext,this.assignmentFile);
            submitHomeworkAPI(this.assignmentId,this.assignmentContext,this.assignmentFile);
        },



        searchHomework(row){
            this.showSearchHomeworkModal = true;
            this.showSearchHomeworkModal = true;
            this.assignmentId = row.id;

            let responseData = getAssignmentAPI(this.assignmentId);
            console.log("assignmentId!");
            console.log(this.assignmentId);
            console.log("respnseData!");
            console.log(responseData);

            this.showTable1(responseData);


        },

        async getStudentAssignment(){
            let classId = this.classId;
            const responseData = await getAssignmentListByClassAPI(classId);
            // console.log("responseData");
            // console.log(responseData);
            if(responseData!=null){
                this.showTable(responseData)
            }
        },

        showTable(responseData){
            // console.log(time);
            const data = responseData;

            if (Array.isArray(data)) {
                data.forEach(item => {
                    item.dueDate = this.formatDueDate(item.dueDate);
                });
                this.tableData = data;
            }

            this.dialogVisible = true;
        },

        async findClass() {
            this.classes = await findClassAPI(this.courseId);
            for(let i = 0; i < this.classes.length; i++) {
                let tmp = [];
                for(let j = 0; j < this.classes[i].teachers.length; j++) {
                    let nameT = await getUserName(this.classes[i].teachers[j]);
                    tmp.push(nameT);
                }
                this.classes[i].teachers = tmp;
                if(this.classes[i].teachers.length === 0) {
                    this.classes[i].teachers = ['暂未分配教师'];
                }
                // console.log(this.classes[i].teachers);

            }
            // console.log(this.classes);
            this.$emit('update-classes', this.classes, this.courseId);
            this.$emit('update-show-course-list', false);
            this.$emit('update-show-class-list', true);
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
                    const response = await this.$http.post(`/assignment/uploadAttachment?assignmentId=${this.assignmentId}`, formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    });
                    console.log("responseData!");
                    console.log(response.data);
                    const keyData = {
                        "name": 'ok',
                        "policy": response.data.data.encodedPolicy,
                        "OSSAccessKeyId": response.data.data.accessKeyId,
                        "success_action_status": '200',
                        "signature": response.data.data.postSignature,
                        "key": response.data.data.objectName,
                        "Content-Disposition": `attachment; filename=${response.data.data.objectName.substring(response.data.data.objectName.lastIndexOf('/') + 1)}.${fileExtension}`
                    };
                    this.assignmentFile =  `${response.data.data.host + '/' + response.data.data.objectName}`;
                    console.log(this.assignmentFile);
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
    }
}
</script>

<style scoped>
.get-assignmentListByClass-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.get-assignmentListByClass-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.get-assignmentListByClass-modal {
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
    width: 200px;
}

.input-group input[type="number"] {
    width: 200px;
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

.el-dialog {
    width: 80%;
    height: 80%;
}
.el-dialog{
    z-index: 1;
}
.submit-homework-modal {
    z-index: 9999; /* 或者更高的值，以确保在最上层显示 */
}

.search-homework-modal{
    z-index: 9999; /* 或者更高的值，以确保在最上层显示 */
}

.search-homework-modal{
    z-index: 9999; /* 或者更高的值，以确保在最上层显示 */
}
</style>

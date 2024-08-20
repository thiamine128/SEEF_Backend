<template>
    <div class="submit-homework-container">
        <button class="first-button" @click="getCourseResource">课程资料</button>

        <div class="submit-homework-modal" v-if="showSubmitHomeworkModal">
            <div class="modal-content">
                <span class="close-icon" @click="showSubmitHomeworkModal = false">&times;</span>

                <h2>获取课程的资料</h2>

                <form @submit.prevent="getCourseResource">
                    <!-- <div class="input-group">
                        <label for="currentDirectory">当前目录:</label>
                        <input type="text" id="currentDirectory" v-model="currentDirectory">
                    </div> -->

                    <!-- <div class="input-group">
                        <label for="courseId">课程编号:</label>
                        <input type="number" id="courseId" v-model="courseId">
                    </div> -->
                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>


    <div>
        <el-dialog v-model="dialogVisible" title="资源展示">
        <el-table :data="tableData" style="width: 100%">
            <el-table-column label="资料">
                <template v-slot="scope">
                    {{ scope.row.split('/').pop() }}
                </template>
            </el-table-column>

            <el-table-column label="链接">
                <template v-slot="scope">
                    <a :href="'http://chkbigevent.oss-cn-beijing.aliyuncs.com/' + scope.row">下载</a>
                </template>
            </el-table-column>

            <el-table-column label="操作" v-if="isTeacher">
            <template v-slot="scope" >
                <el-button type="danger" @click="deleteItem(scope.row.split('/').pop())">
                    <div style="text-align: left">删除资源</div>
                </el-button>
            </template>
            </el-table-column>

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
import {getResourceAPI} from "@/pages/education/components/getCourseResource/api/api";
import {deleteResourceAPI} from "@/pages/education/components/getCourseResource/api/api";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";

import store from "@/store/store";
export default {
    name: "createResourceDirectoryButton",
    components: {LeftButton},
    data() {
        return {
            showSubmitHomeworkModal: false,
            dialogVisible: false,
            tableData: [],
        };
    },
    props:{
        courseId:null,
        hasPermission: null,
    },
    computed: {
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        }
    },
    methods: {
        showSubmitHomeworkPage() {
            this.showSubmitHomeworkModal = true;
        },
        getCourseResource(event){

            let responseData = getResourceAPI("/",this.courseId);
            if(responseData!=null){
                console.log("responseData");
                console.log(responseData);
                // console.log(typeof(responseData));
                this.showTable(responseData);
            }
        },

        deleteItem(firstColomn){
            deleteResourceAPI('/',this.courseId,firstColomn);
            this.getCourseResource();
        },

        showTable(responseData){
            responseData.then(result => {
                this.tableData = result.filter(filePath => filePath.split('/').pop() !== '');
            })
            this.dialogVisible = true;
            // console.log("tableData");
            // console.log(this.tableData);
            // console.log(typeof(this.tableData))
        },


        extractFileName(path){
            const pathParts = path.split('/');
            const fileName = pathParts[pathParts.length-1];
            return fileName;
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
                    const response = await this.$http.post('/courseResource/upload', formData, {
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
                    console.error(error);
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

.get-course-resource-button{
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

.center-button {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>

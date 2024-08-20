<template>
    <div class="create-course-container">
        <button class="first-button" @click="showCreatePage">创建课程</button>

        <div class="create-course-modal" v-if="showCreateCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showCreateCourseModal = false">&times;</span>

                <h3>创建课程</h3>

                <form style="width: 300px;" @submit.prevent="createCourse">
                    <div class="input-group">
                        <label style="text-align: left" for="courseName">课程名称</label>
                        <input placeholder="请输入课程名称" type="text" id="courseName" v-model="courseName" required>
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="courseCredits">课程学分</label>
                        <input placeholder="请输入课程学分" type="number" id="courseCredits" v-model="courseCredits" required min="0.1" step="0.1">
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="introduction">课程简介</label>
                        <textarea style="height: 50px; width: 288px; border: 1px solid #2b2b2b" placeholder="请输入课程简介" type="text" id="introduction" v-model="introduction" required/>
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="syllabus">课程大纲</label>
                        <textarea style="height: 50px; width: 288px; border: 1px solid #2b2b2b" placeholder="请输入课程大纲" type="text" id="syllabus" v-model="syllabus" required/>
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="evaluation">考核方式</label>
                        <input placeholder="请输入考核方式" type="text" id="evaluation" v-model="evaluation" required>
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {createCourseAPI} from "@/pages/education/components/createCourseButton/api/api";

export default {
    name: "createCourseButton",
    data() {
        return {
            showCreateCourseModal: false,
            courseName: '',
            courseCredits: 0,
            introduction: '',
            syllabus: '',
            evaluation: '',
        };
    },

    methods: {
        showCreatePage() {
            this.showCreateCourseModal = true;
        },

        createCourse(event) {
            let data = {
                name: this.courseName,
                credit: this.courseCredits,
                introduction: this.introduction,
                syllabus: this.syllabus,
                evaluation: this.evaluation,
            };
            // console.log("调用createCourseAPT");
            createCourseAPI(data);
            this.showCreateCourseModal = false;
        },
    },
}
</script>

<style scoped>
/*.create-course-container {*/
/*    display: flex;*/
/*    justify-content: center;*/
/*    align-items: center;*/
/*    margin: 20px;*/
/*}*/

/*.create-course-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*}*/

/*.create-course-modal {*/
/*    position: fixed;*/
/*    top: 50%;*/
/*    left: 50%;*/
/*    transform: translate(-50%, -50%);*/
/*    background-color: white;*/
/*    padding: 20px;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);*/
/*    z-index: 1000;*/
/*}*/

/*.modal-content {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    align-items: center;*/
/*}*/

/*.close-icon {*/
/*    position: absolute;*/
/*    top: 10px;*/
/*    right: 10px;*/
/*    font-size: 20px;*/
/*    cursor: pointer;*/
/*}*/

/*.input-group {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    margin-bottom: 15px;*/
/*}*/

/*.input-group label {*/
/*    margin-bottom: 5px;*/
/*}*/

/*.input-group input {*/
/*    padding: 5px;*/
/*    border: 1px solid #ccc;*/
/*    border-radius: 3px;*/
/*    width: 200px;*/
/*}*/

.input-group input[type="number"] {
    width: 288px;
}

.input-group input[type="text"] {
    width: 288px;
}

/*.first-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
/*    transition: background-color 0.3s ease;*/
/*    font-size: 20px;*/
/*}*/

/*.first-button:hover {*/
/*    background-color: #0056b3;*/
/*}*/
.create-course-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 180px;
    height: 60px;
}

.create-course-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.create-course-modal {
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
    width: 200px;
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

.first-button {
    width: 100%;
    height: 100%;
    background-color: rgba(11, 11, 11, 0.02);
    border: none;
    cursor: pointer;
    /*box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
    transition: background-color 0.3s ease;
    font-size: 16px;
    font-weight: bold;
    color: #2b2b2b;
}

.first-button:hover {
    background-color: rgba(55, 55, 55, 0.1);
}
</style>

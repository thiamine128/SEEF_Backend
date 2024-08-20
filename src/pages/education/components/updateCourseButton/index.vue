<template>
    <div class="update-course-container">
        <button class="first-button" @click="showUpdateCoursePage">更新课程信息</button>

        <div class="update-course-modal" v-if="showUpdateCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showUpdateCourseModal = false">&times;</span>

                <h2>更新课程信息</h2>

                <form @submit.prevent="updateCourse">
                    <div class="input-group">
                        <label for="courseId">课程编号:</label>
                        <input type="text" id="courseId" v-model="courseId" required>
                    </div>

                    <div class="input-group">
                        <label for="courseName">课程名称:</label>
                        <input type="text" id="courseName" v-model="courseName" required>
                    </div>

                    <div class="input-group">
                        <label for="courseIntroduction">课程介绍:</label>
                        <input type="text" id="courseIntroduction" v-model="courseIntroduction" required>
                    </div>

                    <div class="input-group">
                        <label for="courseCredit">课程学分:</label>
                        <input type="text" id="courseCredit" v-model="courseCredit" required>
                    </div>

                    <div class="input-group">
                        <label for="courseSyllabus">课程大纲:</label>
                        <input type="text" id="courseSyllabus" v-model="courseSyllabus" required>
                    </div>

                    <div class="input-group">
                        <label for="courseEvaluation">考核方式:</label>
                        <input type="text" id="courseEvaluation" v-model="courseEvaluation" required>
                    </div>

                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {updateCourseAPI} from "@/pages/education/components/updateCourseButton/api/api";
import  {toRefs} from 'vue';

export default {
    name: "updateCourseButton",
    data() {
        return {
            showUpdateCourseModal: false,
            courseId: 0,
            courseName: '',
            courseIntroduction:'',
            courseCredit:null,
            courseSyllabus:'',
            courseEvaluation:'',
            // teacherName: '',
            //classTime: '',
            //classLocation: '',
            //teacherId: null,
        };
    },

    methods: {
        showUpdateCoursePage() {
            this.showUpdateCourseModal = true;
        },

        updateCourse(event) {
            let data = {
                // name: this.className,
                // courseId: this.courseId,
                // teacherId: this.teacherId,
                // time: this.classTime,
                // location: this.classLocation,
                id: this.courseId,
                name: this.courseName,
                introduction: this.courseIntroduction,
                credit: this.courseCredit,
                syllabus: this.courseSyllabus,
                evaluation: this.courseEvaluation,
            };
            updateCourseAPI(data);
        },
    },
}
</script>

<style scoped>
.update-course-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.update-course-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.update-course-modal {
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

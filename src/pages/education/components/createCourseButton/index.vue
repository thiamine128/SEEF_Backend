<template>
    <div class="create-course-container">
        <button class="first-button" @click="showCreatePage">创建课程</button>

        <div class="create-course-modal" v-if="showCreateCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showCreateCourseModal = false">&times;</span>

                <h2>创建课程</h2>

                <form @submit.prevent="createCourse">
                    <div class="input-group">
                        <label for="courseName">课程名称:</label>
                        <input type="text" id="courseName" v-model="courseName" required>
                    </div>

                    <div class="input-group">
                        <label for="teacherName">任课教师:</label>
                        <input type="text" id="teacherName" v-model="teacherName" required>
                    </div>

                    <div class="input-group">
                        <label for="courseCredits">课程学分:</label>
                        <input type="text" id="courseCredits" v-model="courseCredits" required>
                    </div>

                    <button type="submit">确认</button>
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
            teacherName: '',
            courseCredits: 0,
        };
    },

    methods: {
        showCreatePage() {
            this.showCreateCourseModal = true;
        },

        createCourse(event) {
            let data = {
                name: this.username,
                email: this.email,
                verificationCode: this.code,
                password: this.password
            };
            console.log("调用createCourseAPT");
            createCourseAPI(data);
        },
    },
}
</script>

<style scoped>
.create-course-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
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

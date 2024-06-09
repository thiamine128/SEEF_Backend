<template>
    <div class="create-class-container">
        <button class="first-button" @click="showAddTeacherToCoursePage">添加教师到课程组</button>

        <div class="create-class-modal" v-if="showAddTeacherToCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showAddTeacherToCourseModal = false">&times;</span>

                <h2>添加教师到课程组</h2>

                <form @submit.prevent="addTeacherToCourse">
                    <div class="input-group">
                        <label for="courseId">课程编号:</label>
                        <input type="number" id="courseId" v-model="courseId" required min="1">
                    </div>

                    <div class="input-group">
                        <label for="courseId">教师工号:</label>
                        <input type="text" id="teacherId" v-model="teacherId" required min="1">
                    </div>
                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {addTeacherToCourseAPI} from "@/pages/education/components/addTeacherToCourseButton/api/api";
export default {
    name: "addTeacherToCourseButton",
    data() {
        return {
            showAddTeacherToCourseModal: false,
            courseId: null,
            teacherId: null,
        };
    },

    methods: {
        showAddTeacherToCoursePage() {
            this.showAddTeacherToCourseModal = true;
        },
        async addTeacherToCourse() {
            await addTeacherToCourseAPI(this.teacherId, this.courseId);
            this.showAddTeacherToCourseModal = false;
        }
    },
}
</script>

<style scoped>
.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.create-class-modal {
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
</style>

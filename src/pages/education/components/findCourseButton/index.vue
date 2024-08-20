<template>
    <div class="create-course-container">
        <button class="first-button" @click="showCreatePage">查询课程</button>

        <div class="create-course-modal" v-if="showFindCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showFindCourseModal = false">&times;</span>

                <h3>查询课程</h3>

                <form @submit.prevent="findCourse">
                    <div class="input-group">
                        <input placeholder="请输入课程名称" style="height: 20px" type="text" id="courseName" v-model="courseName">
                    </div>

                    <button type="submit" style="width: 100%">确认</button>
                </form>
            </div>
        </div>

    </div>
</template>

<script>
import {findCourseAPI} from "@/pages/education/components/findCourseButton/api/api";
// import { defineEmit } from 'vue';
import { ref } from 'vue';
export default {
    name: "findCourseButton",
    data() {
        return {
            showFindCourseModal: false,
            courseName: '',
            page: 1,
            pageSize: 50,
            courses: [],
        };
    },

    methods: {
        showCreatePage() {
            this.showFindCourseModal = true;
        },

        async findCourse() {
            let data = {
                name: this.courseName,
                page: this.page,
                pageSize: this.pageSize,
            };
            // console.log("调用findCourseAPT");
            this.courses = await findCourseAPI(data);
            this.$emit('update-courses', this.courses);
            this.$emit('update-show-class-list', false);
            this.$emit('update-show-course-list', true);
            this.$emit('update-find-name', this.courseName);
            this.showFindCourseModal = false;
        },
    },
}
</script>

<style scoped>
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

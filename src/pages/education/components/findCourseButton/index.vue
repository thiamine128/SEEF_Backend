<template>
    <div class="create-course-container">
        <button class="first-button" @click="showCreatePage">查询课程</button>

        <div class="create-course-modal" v-if="showFindCourseModal">
            <div class="modal-content">
                <span class="close-icon" @click="showFindCourseModal = false">&times;</span>

                <h2>查询课程</h2>

                <form @submit.prevent="findCourse">
                    <div class="input-group">
                        <label for="courseName">课程名称:</label>
                        <input type="text" id="courseName" v-model="courseName" required>
                    </div>

                    <button type="submit">确认</button>
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
    // setup() {
    //     const searchQuery = ref('');
    //     const emitSearchQuery = defineEmit('searchQuery'); // Define the event
    //
    //     const onSearch = () => {
    //         emitSearchQuery(searchQuery.value); // Emit the event with query
    //     };
    //     return {
    //         searchQuery,
    //         onSearch,
    //     }
    // },
    name: "findCourseButton",
    data() {
        return {
            showFindCourseModal: false,
            courseName: '课程名称',
            page: 1,
            pageSize: 10,
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
            console.log("调用findCourseAPT");
            this.courses = await findCourseAPI(data);
            console.log('In findCourseButton');
            console.log(this.courses);
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

<template>
    <div class="create-class-container">
        <button class="first-button" @click="showCreatePage">创建教学班</button>

        <div class="create-class-modal" v-if="showCreateClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showCreateClassModal = false">&times;</span>

                <h2>创建教学班</h2>

                <form @submit.prevent="createClass">
                    <div class="input-group">
                        <label for="className">课程名称:</label>
                        <input type="text" id="className" v-model="className" required>
                    </div>

                    <div class="input-group">
                        <label for="teacherName">任课教师:</label>
                        <input type="text" id="teacherName" v-model="teacherName" required>
                    </div>

                    <div class="input-group">
                        <label for="classTime">上课时间:</label>
                        <input type="text" id="classTime" v-model="classTime" required>
                    </div>

                    <div class="input-group">
                        <label for="classLocation">上课地点:</label>
                        <input type="text" id="classLocation" v-model="classLocation" required>
                    </div>

                    <button type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {createClassAPI} from "@/pages/education/components/createClassButton/api/api";

export default {
    name: "createClassButton",
    data() {
        return {
            showCreateClassModal: false,
            className: '课程名称',
            teacherName: '教师名称',
            classTime: '上课时间',
            classLocation: '课程大纲',
        };
    },

    methods: {
        showCreatePage() {
            this.showCreateClassModal = true;
        },

        createClass(event) {
            let data = {
                name: this.className,
                teacherName: this.teacherName,
                classTime: this.classTime,
                classLocation: this.classLocation
            };
            console.log("调用createClassAPI");
            createClassAPI(data);
        },
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

.create-class-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
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

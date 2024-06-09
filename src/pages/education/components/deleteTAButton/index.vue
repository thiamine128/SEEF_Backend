<template>
    <div class="create-class-container">
        <button class="first-button" @click="showDeleteTaPage">删除助教</button>

        <div class="create-class-modal" v-if="showDeleteTAModal">
            <div class="modal-content">
                <span class="close-icon" @click="showDeleteTAModal = false">&times;</span>

                <h3>删除助教</h3>

                <form @submit.prevent="deleteTA">
                    <div class="input-group">
<!--                        <label for="studentId">助教编号:</label>-->
                        <input placeholder="请输入助教学号" type="number" id="studentId" v-model="studentId" required min="1">
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {callError} from "@/callMessage";
import {deleteTAAPI} from "@/pages/education/components/deleteTAButton/api/api";
export default {
    name: "deleteTAButton",
    data() {
        return {
            showDeleteTAModal: false,
            studentId: null,
        };
    },
    props: {
        courseId: null,
        classId: null,
    },
    methods: {
        showDeleteTaPage() {
            this.showDeleteTAModal = true;
        },
        deleteTA() {
            // console.log(this.studentId, this.courseId, this.classId);
            deleteTAAPI(this.courseId, this.classId, this.studentId);
            this.showDeleteTAModal = false;
        }
    }

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

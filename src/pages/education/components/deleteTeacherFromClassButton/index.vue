<template>
    <div class="create-class-container">
        <button class="first-button" @click="showDeleteTeacherPage">取消班级分配</button>

        <div class="create-class-modal" v-if="showDeleteTeacherModal">
            <div class="modal-content">
                <span class="close-icon" @click="showDeleteTeacherModal = false">&times;</span>

                <h4>取消教师班级分配</h4>

                <form @submit.prevent="deleteTeacher">
                    <div class="input-group">
<!--                        <label for="teacherId">教师工号:</label>-->
                        <input placeholder="请输入教师工号" type="text" id="teacherId" v-model="teacherId" required>
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {callError} from "@/callMessage";
import {deleteTeacherAPI} from "@/pages/education/components/deleteTeacherFromClassButton/api/api";
export default {
    name: "deleteTeacherFromClassButton",
    data() {
        return {
            showDeleteTeacherModal: false,
            teacherId: null,
        };
    },
    props: {
        courseId: null,
        classId: null,
    },
    methods: {
        showDeleteTeacherPage() {
            this.showDeleteTeacherModal = true;
        },
        deleteTeacher() {
            // console.log(this.teacherId, this.classId);
            deleteTeacherAPI(this.classId, this.teacherId);
            this.showDeleteTeacherModal = false;
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
    font-size: 14px;
    margin-bottom: 5px;
}

.input-group input {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 3px;
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

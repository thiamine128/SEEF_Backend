<template>
    <div class="create-class-container">
        <button class="first-button" @click="addButchStudent">批量添加学生</button>
    </div>
</template>

<script>
import {addButchStudentAPI} from "@/pages/education/components/addButchStudentButton/api/api";

export default {
    name: "addAdminButton",
    data() {
        return {
        };
    },
    methods: {
        addButchStudent() {
            addButchStudentAPI();
        }
    }

}
</script>

<style scoped>
.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 60px;
    width: 180px;
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

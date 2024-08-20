<template>
    <div class="update-course-container">
        <button class="first-button" @click="updateCover">
            更新课程封面
        </button>
    </div>
</template>

<script>
import {updateCoverAPI} from "@/pages/education/components/updateCoverButton/api/api";

export default {
    name: "updateCoverButton",
    data() {
        return {
        };
    },
    props: {
        courseId: null,
    },
    methods: {
        updateCover() {
            updateCoverAPI(this.courseId);
        },
    },
}
</script>

<style scoped>
.update-course-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
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
    background-color: #34cfff;
    border: none;
    color: white;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;

}

.first-button:hover {
    background-color: #0056b3;
}
</style>

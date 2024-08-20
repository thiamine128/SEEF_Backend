<template>
    <div class="create-class-container">
        <button class="first-button" @click="showJoinClassPage">申请加入班级</button>

        <div class="create-class-modal" v-if="showJoinClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showJoinClassModal = false">&times;</span>

                <h3>申请加入班级</h3>

                <form @submit.prevent="joinClass">
                    <div class="input-group">
                        <input placeholder="请输入班级编号" type="text" id="classId" v-model="classId" required>
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {joinClassAPI} from "@/pages/education/components/joinClassButton/api/api";
export default {
    name: "joinClassButton",
    data() {
        return {
            showJoinClassModal: false,
            classes: [],
            classId: null,
        };
    },

    methods: {
        showJoinClassPage() {
            this.showJoinClassModal = true;
        },

        async joinClass() {
            await joinClassAPI({classId: this.classId});
        },
    },
}
</script>

<style scoped>
/*.create-class-container {*/
/*    display: flex;*/
/*    justify-content: center;*/
/*    align-items: center;*/
/*    margin: 20px;*/
/*}*/

/*.create-class-modal {*/
/*    position: fixed;*/
/*    top: 50%;*/
/*    left: 50%;*/
/*    transform: translate(-50%, -50%);*/
/*    background-color: white;*/
/*    padding: 20px;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);*/
/*    z-index: 1000;*/
/*}*/

/*.modal-content {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    align-items: center;*/
/*}*/

/*.close-icon {*/
/*    position: absolute;*/
/*    top: 10px;*/
/*    right: 10px;*/
/*    font-size: 20px;*/
/*    cursor: pointer;*/
/*}*/

/*.input-group {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    margin-bottom: 15px;*/
/*}*/

/*.input-group label {*/
/*    margin-bottom: 5px;*/
/*}*/

/*.input-group input {*/
/*    padding: 5px;*/
/*    border: 1px solid #ccc;*/
/*    border-radius: 3px;*/
/*    width: 200px;*/
/*}*/

/*.input-group input[type="number"] {*/
/*    width: 200px;*/
/*}*/

/*.first-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
/*    transition: background-color 0.3s ease;*/
/*    font-size: 20px;*/
/*}*/

/*.first-button:hover {*/
/*    background-color: #0056b3;*/
/*}*/
.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 180px;
    height: 60px;
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

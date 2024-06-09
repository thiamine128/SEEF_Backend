<template>
    <div class="create-class-container">
        <button class="first-button" @click="showAddAdminPage">添加管理员</button>

        <div class="create-class-modal" v-if="showAddAdminModal">
            <div class="modal-content">
                <span class="close-icon" @click="showAddAdminModal = false">&times;</span>

                <h3>添加管理员</h3>

                <form @submit.prevent="addAdmin">
                    <div class="input-group">
                        <input placeholder="请输入管理员姓名" type="text" id="teacherName" v-model="teacherName" required>
                    </div>

                    <div class="input-group">
                        <input placeholder="请输入管理员邮箱" type="text" id="teacherEmail" v-model="teacherEmail" required>
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {addAdminAPI} from "@/pages/education/components/addAdminButton/api/api";

export default {
    name: "addAdminButton",
    data() {
        return {
            showAddAdminModal: false,
            teacherName: null,
            teacherEmail: null,
        };
    },
    methods: {
        showAddAdminPage() {
            this.showAddAdminModal = true;
        },
        addAdmin() {
            let data = {
                name: this.teacherName,
                email: this.teacherEmail
            }
            // console.log(data);
            addAdminAPI(data);
            this.showAddAdminModal = false;
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

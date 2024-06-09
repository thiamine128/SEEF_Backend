<template>
    <el-row v-for="(ta, index) in tas" :key="index" v-if="tas.length > 0">
        <el-col>
            {{ ta.name + "(" + ta.realName + ")" }} : {{ ta.email }}
            <el-button @click="confirmDelete" type="warning" v-if="hasDeletePerm">删除助教</el-button>
            <div v-if="showDialog" class="dialog">
                <p>是否确定删除助教？</p>
                <el-button @click="deleteAssistant(ta.id)" type="primary">确定</el-button>
                <el-button style="margin-left: 5px" @click="cancelDelete" type="warning" >取消</el-button>
            </div>
        </el-col>
    </el-row>
    <p v-else>暂无</p>
</template>

<script>
import {getTA} from "@/pages/education/components/taList/api/api";
import {deleteTAAPI} from "@/pages/education/components/deleteTAButton/api/api";

export default {
    name: "taList",
    props: ['classId', 'courseId', 'hasDeletePerm'],
    data() {
        return {
            tas: [],
            showDialog: false,
        }
    },
    mounted() {
        this.pullTA();
    },
    methods: {
        async pullTA() {
            this.tas = await getTA(this.classId)
        },
        confirmDelete() {
            this.showDialog = true;
        },
        deleteAssistant(studentId) {
            deleteTAAPI(this.courseId, this.classId, studentId);
            this.showDialog = false;
        },
        cancelDelete() {
            this.showDialog = false;
        }
    }
}

</script>

<style scoped>
.red-btn {
    border: 2px solid red;
    border-radius: 20px;
    padding: 10px 20px;
    color: red;
    background: white;
    font-size: 16px;
    cursor: pointer;
}

.delete-btn {
    border: 2px solid red;
    border-radius: 20px;
    padding: 10px 20px;
    color: red;
    background: white;
    font-size: 16px;
    cursor: pointer;
}

.cancel-btn {
    border: 2px solid blue;
    border-radius: 20px;
    padding: 10px 20px;
    color: blue;
    background: white;
    font-size: 16px;
    cursor: pointer;
}
.dialog {
    border: 1px solid #ccc;
    padding: 20px;
    margin-top: 10px;
}
</style>

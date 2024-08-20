<template>
    <div class="approve-dialog">
        <div class="dialog-header">
            <h3>审批申请</h3>
            <button class="close-button" @click="close">X</button>
        </div>
        <div class="dialog-content">
            <table>
                <thead>
                <tr>
                    <th>序号</th>
                    <th>姓名</th>
                    <th>学号</th>
                    <th>选择</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(applicant, index) in applicants" :key="index">
                    <td>{{ index + 1 }}</td>
                    <td>{{ applicant.realName }}</td>
<!--                    这里的name指的是realName-->
                    <td>{{ applicant.name }}</td>
                    <td>
                        <input type="checkbox" v-model="selectedStudents" :value="applicant.name" />
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="dialog-footer">
            <button @click="selectAll">全选</button>
            <button @click="deselectAll">全不选</button>
            <button @click="accept">通过</button>
            <button @click="reject">拒绝</button>
        </div>
    </div>
</template>

<script>
import {ref} from "vue";
import {judgeAPI} from "@/pages/education/components/judgeList/api/api";

export default {
    name: "judgeList",
    data() {
        return {
            show: true,
            page: 1,
            pageSize: 10,
            selectedStudents: ref([]),
        };
    },
    props: {
        applicants: {
            type: Array,
        },
        classId: null,
    },
    mounted() {

    },
    methods: {
        selectAll() {
            for (let i = 0; i < this.applicants.length; i++) {
                this.selectedStudents.push(this.applicants[i].name);
            }
        },
        deselectAll() {
            this.selectedStudents = [];
        },
        async accept() {
            let data = {
                ids: [],
                state: 1,
            }
            for(let i = 0; i < this.applicants.length; i++) {
                for(let j = 0; j < this.selectedStudents.length; j++) {
                    if(this.applicants[i].name === this.selectedStudents[j]) {
                        data.ids.push(this.applicants[i].id);
                    }
                }
            }
            // console.log(data);
            await judgeAPI(data);
            this.selectedStudents = [];
            this.$emit('update-parent', false);
        },
        async reject() {
            let data = {
                ids: [],
                state: 2,
            }
            for(let i = 0; i < this.applicants.length; i++) {
                for(let j = 0; j < this.selectedStudents.length; j++) {
                    if(this.applicants[i].studentId === this.selectedStudents[j]) {
                        data.ids.push(this.applicants[i].id);
                    }
                }
            }
            // console.log(data);
            await judgeAPI(data);
            this.selectedStudents = [];
            this.$emit('update-parent', false);
        },
        close() {
            this.$emit('update-parent', false);
        }
    },
};
</script>

<style scoped>
.approve-dialog {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    z-index: 100;
}

.dialog-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0;
}

.dialog-content {
    max-height: 300px;
    overflow-y: auto;
}

.dialog-content table {
    width: 100%;
    border-collapse: collapse;
}

.dialog-content th,
.dialog-content td {
    padding: 10px;
    border: 1px solid #eee;
}

.dialog-content th {
    text-align: left;
}

.approve-buttons {
    display: flex;
}

.approve-buttons button {
    margin-right: 10px;
    padding: 5px 10px;
}
.dialog-footer button {
    margin: 5px 10px;
    padding: 8px 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: #f0f0f0;
    color: #333;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
}

.dialog-footer button:hover {
    background-color: #e0e0e0;
    color: #000;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: red;
    color: white;
    border: none;
    padding: 5px 9px;
    cursor: pointer;
    border-radius: 50px;
}
</style>

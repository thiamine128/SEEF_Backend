<template>
    <div class="class-list">
        <button class="close-button" @click="closeClassList">X</button>
        <h2>{{courseName}}班级查询结果</h2>
        <table>
            <thead>
            <tr>
                <th>班级编号</th>
                <th>任课教师</th>
                <th>上课时间</th>
                <th>上课地点</th>
                <th>详细信息</th>
                <th>操作按钮</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="x in classes" :key="x.id">
                <td>{{ x.id }}</td>
                <td>{{x.teachers.join(',')}}</td>
                <td>{{ x.time }}</td>
                <td>{{ x.location }}</td>
                <td>
                    <a class="details-link" :href="'/education/courses/' + x.courseId" target="_blank" rel="noopener noreferrer">
                        查看详情
                    </a>
                </td>
                <td>
                    <button class="choose-button" @click="applyJoinClass(x.id)" v-if="isStudent && !x.hasPermission">申请加入此班级</button>
                    <button class="choose-button" @click="showJudgePage(x.id)" v-if="x.hasPermission && isTeacher">审批申请学生</button>
                    <button class="choose-button" @click="showPublishPage(x.id)" v-if="x.hasPermission && isTeacher">发布作业</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <judge-list :applicants="nowClassList" :class-id="judgeClassId" v-if="showJudgePageModal" @update-parent="updateShowJudgePage"></judge-list>
</template>

<script>
import {applyJoinClassAPI} from "@/pages/education/components/classList/api/api";
import judgeList from "@/pages/education/components/judgeList/index.vue";
import async from "async";
import store from "@/store/store";
import {getJudgeListAPI} from "@/pages/education/components/judgeList/api/api";
import {getUserName} from "@/pages/education/api";

export default {
    name: 'ClassList',
    components: {
        judgeList
    },
    props: {
        classes: Array,
        courseId: null,
        courseName: null,
    },
    data() {
        return {
            isStudent: null,
            showJudgePageModal: false,
            judgeClassId: null,
            page: 1,
            pageSize: 20,
            classLists: null,
            nowClassList: null,
        }
    },
    methods: {
        async applyJoinClass(x) {
            await applyJoinClassAPI({classId: x});
        },
        async showJudgePage(x) {
            this.showJudgePageModal = true;
            this.judgeClassId = x;
            this.classLists = await getJudgeListAPI(this.page, this.pageSize, this.courseId);
            this.nowClassList = [];
            // console.log('classLists:'+this.classLists);
            for (let i = 0; i < this.classLists.length; i++) {
                if (this.classLists[i].classId === x) {
                    // console.log(this.classLists[i].studentId);
                    // console.log(await getUserName(this.classLists[i].studentId));
                    this.nowClassList.push({studentId: this.classLists[i].studentId,
                        name: await getUserName(this.classLists[i].studentId), id: this.classLists[i].id});
                }
            }
            // console.log(this.nowClassList);
        },
        async showPublishPage(x) {

        },
        updateShowJudgePage(newShow) {
            this.showJudgePageModal = newShow;
        },
        closeClassList() {
            this.$emit('close-class-list');
        },
    },
    computed: {
        isStudent() {
            return store.getters.getEduIdentity === 'student';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        },
    },
    mounted() {
        // try {
        //     if(this.classes) {
        //         this.courseName = '《' + this.classes[0].courseName + '》';
        //         console.log(this.classes[0]);
        //         console.log('name:', this.courseName);
        //     }
        // } catch (error) {
        //     this.courseName = '';
        // }
    }
};
</script>

<style scoped>
.class-list {
    position: relative;
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: rgba(164, 255, 252, 0.4);
    opacity: 80%;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    padding: 8px;
    border: 1px solid #ccc;
    text-align: left;
}

th {
    background-color: #f2f2f2;
}

.choose-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transition: background-color 0.3s ease;
    font-size: 14px;
    margin-right: 10px;
}

.choose-button:hover {
    background-color: #0056b3;
}

.details-link {
    display: inline-block;
    padding: 8px 16px;
    border: 2px solid #4CAF50;
    border-radius: 20px;
    color: #4CAF50;
    text-decoration: none;
    text-align: center;
    transition: background-color 0.3s, color 0.3s;
}

.details-link:hover {
    background-color: #4CAF50;
    color: white;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: red;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 50%;
}
h2 {
    text-align: center;
}
</style>

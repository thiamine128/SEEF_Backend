<template>
    <div class="course-card">
        <div class="course-layout">
            <div class="course-details" style="text-align: left">
                <div style="display: flex; flex-direction: row; align-items: center">
                <p><b style="font-size: 30px; color: black">{{ classT.name }}</b></p>
                <img v-if="isAdmin" alt="404" src="@/assets/blog/cancel.png" @click="deleteClass(classT.id)" style="width: 22px; height: 22px; margin-left: 5px; cursor: pointer">
                <div style="display: flex; flex-direction: row; justify-content: left; margin-left: 20px">
                    <el-button style="height: 35px;" class="choose-button" @click="applyJoinClass(classT.id)" v-if="isStudent && !joined">申请加入班级</el-button>
                    <el-button style="height: 35px;" class="choose-button" @click="showJudgePage(classT.id, classT.courseId)" v-if="classT.hasPermission && isTeacher">审批申请学生</el-button>
                    <el-button style="height: 35px;" class="choose-button" @click="showChosenPage(classT.id, classT.courseId)" v-if="classT.hasPermission && isTeacher">查看选课学生</el-button>
                </div>
                </div>

                <div class="button-container">
                    <add-ta-button :course-id="classT.courseId" :class-id="classT.id" v-if="classT.hasPermission && isTeacher"/>
                    <delete-t-a-button :course-id="classT.courseId" :class-id="classT.id" v-if="classT.hasPermission && isTeacher"/>
                    <update-class-button v-if="classT.hasPermission && isTeacher" :class-id="classT.id" :class-info="classT"/>
                    <delete-teacher-from-class-button :course-id="classT.courseId" :class-id="classT.id" v-if="classT.hasPermission && isTeacher"/>
                </div>
                <p><b>作业：</b> </p>
                <p style="color: #8e8e8e" v-if="!((classT.hasPermission && isTeacher) || isTA  || classT.hasPermission)">非此班级成员</p>
                <div class="button-container">
<!--                    <submit-homework-button v-if="isStudent && classT.hasPermission"/>-->
                    <publish-homework-button :classId="classT.id" v-if="(classT.hasPermission && isTeacher) || isTA" />
                    <!-- <make-assignment-button v-if="isTeacher"/> -->
                    <!-- <get-assignment-button v-if="(classT.hasPermission  && isTeacher) || isTA"/> -->
                    <get-assignment-list-by-class-button :classId="classT.id" v-if="classT.hasPermission || isTA" />
                    <!-- <get-all-assignment-button v-if="isStudent"/> -->
                </div>

                <p><b>课程资料：</b> </p>
                <p style="color: #8e8e8e" v-if="!((classT.hasPermission && isTeacher) || isTA || classT.hasPermission)">非此班级成员</p>
                <div class="button-container">
                    <!-- <create-resource-directory-button :courseId="classT.courseId" v-if="isTeacher"/> -->
                    <upload-resource-button :courseId="classT.courseId" v-if="classT.hasPermission && isTeacher || isTA"/>
                    <get-resource-button :courseId="classT.courseId" :has-permission="(classT.hasPermission && isTeacher) || isTA" v-if="classT.hasPermission || isTA" />
                    <!-- <delete-resource-button :courseId="classT.courseId" v-if="isTeacher || isTA"/> -->
                </div>
                <p><b>班级编号：</b> {{ classT.id }}</p>
                <p><b>任课教师：</b> {{ classT.teachers == null ? '' : classT.teachers.join(',') }}</p>
                <p><b>上课时间：</b> {{ classT.time }}</p>
                <p><b>上课地点：</b> {{ classT.location }}</p>
                <p><b>助教信息：</b></p>
                <ta-list :class-id="classT.id" :course-id="classT.courseId" :has-delete-perm="classT.hasPermission && isTeacher"></ta-list>
            </div>
        </div>
    </div>
    <judge-list :applicants="nowClassList" :class-id="judgeClassId" v-if="showJudgePageModal" @update-parent="updateShowJudgePage"></judge-list>

    <chosen-student-list :applicants="chosenClassList" :class-id="judgeClassId" :course-id="courseId" v-if="showChosenPageModal" @update-parent-chosen="updateShowChosenPage"></chosen-student-list>

</template>

<script>
import addTAButton from "@/pages/education/components/addTAButton/index.vue";
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import AddTaButton from "@/pages/education/components/addTAButton/index.vue";
import DeleteTAButton from "@/pages/education/components/deleteTAButton/index.vue";
import deleteTeacherFromClassButton from "@/pages/education/components/deleteTeacherFromClassButton/index.vue";
import addButchStudentToClassButton from "@/pages/education/components/addButchStudentToClassButton/index.vue";
import UpdateCourseButton from "@/pages/education/components/updateCourseButton/index.vue";
import UpdateClassButton from "@/pages/education/components/updateClassButton/index.vue";
import store from "@/store/store";
import {applyJoinClassAPI} from "@/pages/education/components/classList/api/api";
import {getJudgeListAPI} from "@/pages/education/components/judgeList/api/api";
import {isThisTAAPI, getClassStudentAPI, deleteClassAPI} from "@/pages/education/api";
import judgeList from "@/pages/education/components/judgeList/index.vue";
import chosenStudentList from "@/pages/education/components/deleteStudentList/index.vue";
import submitHomeworkButton from "@/pages/education/components/submitHomework/index.vue";
import publishHomeworkButton from "@/pages/education/components/publishHomework/index.vue";
import makeAssignmentButton from "@/pages/education/components/makeAssignmentButton/index.vue";
import getAssignmentButton from "@/pages/education/components/getStudentAssignment/index.vue";
import getAssignmentListByClassButton from "@/pages/education/components/AssignmentListByClass/index.vue";
import getAllAssignmentButton from "@/pages/education/components/getAllAssignment/index.vue";
import createResourceDirectoryButton from "@/pages/education/components/createResourceDirectory/index.vue";
import uploadResourceButton from "@/pages/education/components/uploadResourceButton/index.vue";
import getResourceButton from "@/pages/education/components/getCourseResource/index.vue";
import deleteResourceButton from "@/pages/education/components/deleteResource/index.vue";
import taList from "@/pages/education/components/taList/index.vue";


export default {
    name: "classInfo",
    components: {
        UpdateClassButton, UpdateCourseButton,
        DeleteTAButton,
        deleteTeacherFromClassButton,
        AddTaButton,
        navigationBar,
        addTAButton,
        judgeList, chosenStudentList,
        submitHomeworkButton,
        publishHomeworkButton,
        makeAssignmentButton,
        getAssignmentButton,
        getAssignmentListByClassButton,
        getAllAssignmentButton,
        createResourceDirectoryButton,
        uploadResourceButton,
        getResourceButton,
        deleteResourceButton,
        taList,
        addButchStudentToClassButton,
    },
    methods: {
        async applyJoinClass(x) {
            await applyJoinClassAPI({classId: x});
        },
        async showJudgePage(x, y) {
            this.showJudgePageModal = true;
            this.judgeClassId = x;
            this.classLists = await getJudgeListAPI(this.page, this.pageSize, y);
            this.nowClassList = [];
            // console.log('classLists:', this.classLists);
            for (let i = 0; i < this.classLists.length; i++) {
                // console.log(this.classLists[i].classId);
                if (this.classLists[i].classId === x) {
                    // console.log(this.classLists[i].studentId);
                    // console.log(await getUserName(this.classLists[i].studentId));
                    this.nowClassList.push({realName: this.classLists[i].realName,
                        name: this.classLists[i].name, id: this.classLists[i].id});
                }
            }
            // console.log("judge list:");
            // console.log(this.nowClassList);
        },
        async showChosenPage(x, y) { // classId, courseId
            this.showChosenPageModal = true;
            this.judgeClassId = x;
            this.chosenClassList = await getClassStudentAPI(x);
        },
        async showPublishPage(x) {

        },
        updateShowJudgePage(newShow) {
            this.showJudgePageModal = newShow;
        },
        updateShowChosenPage(newShow) {
            this.showChosenPageModal = newShow;
        },
        deleteClass(classId) {
            const flag = window.confirm('是否确定要删除班级?');
            if(!flag) return ;
            deleteClassAPI(classId);
        },
    },
    data() {
        return{
            showChild: false,
            detailContent: String,
            showJudgePageModal: false,
            showChosenPageModal: false,
            classLists: null,
            nowClassList: null,
            chosenClassList: null,
            page: 1,
            pageSize: 10,
            judgeClassId: null,
            isTA: false,
        }
    },
    props: {
        joined: false,
        classT: {

        },
        courseId: null,
    },
    computed: {
        isStudent() {
            return store.getters.getEduIdentity === 'student';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        },
        isAdmin() {
            return store.getters.getEduIdentity === 'admin';
        }
    },
    async mounted() {
        this.isTA = await isThisTAAPI(this.classT.id);
        // console.log(this.isTA);
    },
}
</script>

<style scoped>
.course-card {
    display: flex;
    flex-direction: column;
    width: 95%;
    padding: 15px;
    margin: 0 auto;
    border: 5px solid rgba(173, 216, 230, 0.5);
    min-height: fit-content;
}

.course-layout {
    display: flex;
    min-height: fit-content;
}

.course-image {
    width: 200px;
    height: 200px;
    object-fit: cover;
    margin-right: 20px;
}

.course-details {
    flex: 1;
}

h2 {
    margin: 0;
    font-size: 30px;
    font-weight: bold;
}

button {
    height: 25px;
    margin: 5px 0;

}

p {
    margin: 5px 0;
    font-size: 18px;
    text-align: left;
    color: #626262;
}

div {
    margin: 5px 0;
    font-size: 20px;
}

a {
    color: blue;
    text-decoration: underline;
}

.button-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: left; /* 可选：水平居中排布 */
}

.delete-button {
    font-size: 12px;
    color: red;
    border: 1px solid red;
    padding: 2px 5px;
}
</style>

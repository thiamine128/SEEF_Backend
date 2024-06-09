<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <class-info :course="course" :classT="classT"/>

    <div style="display: flex; justify-content: center;">
        <h2>作业模块</h2>
    </div>
    <div class="button-container">

        <submit-homework-button v-if="isStudent"/>
        <publish-homework-button v-if="isTeacher"/>
        <make-assignment-button v-if="isTeacher"/>
        <get-assignment-button v-if="isTeacher"/>
        <get-assignment-list-by-class-button/>
        <get-all-assignment-button/>
    </div>

    <div style="display: flex; justify-content: center;">
        <h2>课程资源模块</h2>
    </div>

    <div class="button-container">
        <create-resource-directory-button v-if="isTeacher"/>
        <upload-resource-button v-if="isTeacher"/>
        <get-resource-button/>
        <delete-resource-button v-if="isTeacher"/>
    </div>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
import classInfo from "@/pages/education/components/classInfo/index.vue";
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



import store from "@/store/store";
import {ref} from "vue";
import {getClassData, getCourseData, getUserName} from "@/pages/education/api";
export default {
    name: "classes",
    components: {
        courseInfo,
        navigationBar,
        classInfo,
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
    },
    methods:{
        async pullClassData(){
            const class_data = await getClassData(this.$route.params.classId, false);
            // console.log('class_data:');
            // console.log(class_data);
            try{
                class_data.avatar = class_data.avatar + '?t=' + new Date().getTime();
                this.my_avatar = ref(class_data.avatar);
            }catch (e){
                // console.error(e);
            }
            this.classT.time = class_data.time;
            this.classT.id = class_data.id;
            this.classT.location = class_data.location;
            this.classT.teachers = [];//class_data.teachers;
            for(let i = 0; i < class_data.teachers.length; i++) {
                const nameT = await getUserName(class_data.teachers[i]);
                this.classT.teachers.push(nameT);
            }
            if(this.classT.teachers.length === 0) {
                this.classT.teachers.push("暂未分配");
            }
            this.classT.teachers = this.classT.teachers.join('，');
            this.classT.courseId = class_data.courseId;
            this.classT.hasPermission = class_data.hasPermission;
        },
        async pullCourseData(){
            const course_data = await getCourseData(this.classT.courseId, false);
            // console.log('course_data:');
            // console.log(course_data);
            try{
                course_data.avatar = course_data.avatar + '?t=' + new Date().getTime();
                this.my_avatar = ref(course_data.avatar);
            }catch (e){
                // console.error(e);
            }
            this.course.id = course_data.id;
            this.course.name = course_data.name;
            this.course.credits = course_data.credits;
            this.course.introduction = course_data.introduction;
            this.course.evaluation = course_data.evaluation;
            this.course.cover = course_data.cover;
            this.course.syllabus = course_data.syllabus;
        },
    },
    computed: {
        isStudent() {
            return store.getters.getEduIdentity === 'student';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        }
    },
    data(){
        return {
            course: {
                name: "none",
                teacher: "none",
                schedule: "none",
                location: "none",
                credits: 0,
                introduction: '暂无',
                assessment: "暂不公布",
                image: "../../../../assets/education/course/信号处理基础.png",
                syllabus: 'none',
            },
            classT: {
                id: null,
                time: null,
                location: null,
                teachers: [],
            },
        }
    },
    async mounted() {
        await this.pullClassData();
        await this.pullCourseData();
    },
}
</script>

<style scoped>
.navigationBar {
    width: 100%;
    height: 90px;
    align-self: center;
    background-color: rgb(102, 152, 228);
}
.main {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 16px;
}
.bg-container {
    background: url('@/assets/education/bg.png');
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}
.button-container {
    display: flex;
    justify-content: space-between;
}
</style>

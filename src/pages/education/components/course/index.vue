<template>
    <el-card class="course-card">
        <div class="course-info">
            <course-cover :url="course.cover" :course-name="course.name"></course-cover>
            <div class="course-title">{{ course.name }}<img v-if="isAdmin" alt="404" src="@/assets/blog/cancel.png" @click="deleteCourse(course.id)" style="width: 22px; height: 22px; margin-left: 5px; cursor: pointer"></div>

            <div class="course-credit">编号：{{ course.id }}</div>
            <div class="course-credit">学分：{{ course.credit }}</div>
            <div class="course-teacher">考核方式：{{ course.evaluation }}</div>

            <course-button r-name="更多信息" :dest="course.id"/>

        </div>
    </el-card >
</template>
<script>
import CourseCover from "@/pages/education/components/courseCover/index.vue"
import CourseButton from "@/pages/education/components/courseButton/index.vue";
import store from "@/store/store";
import {deleteCourseAPI} from "@/pages/education/api";
export default {
    components: {CourseButton, CourseCover},
    props: {
        course: Object
    },
    methods: {
        deleteCourse(courseId) {
            const flag = window.confirm('是否确定要删除课程？');
            if(!flag) return ;
            deleteCourseAPI(courseId);
        }
    },
    computed: {
        isAdmin() {
            return store.getters.getEduIdentity === "admin";
        }
    }
}
</script>

<style scoped>
.course-card {
    background-color: #f2f2f2;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 2px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.course-info {
    font-size: 16px;
}


.course-title {
    align-items: center;
    font-size: 24px;
    font-weight: bold;
    text-align: left;
    color: #333;
    margin-bottom: 5px;
}

.course-credit, .course-teacher {
    font-size: 12px;
    text-align: left;
    color: #666;
    margin-bottom: 5px;
}

.delete-button {
    font-size: 12px;
    color: red;
    border: 1px solid red;
    padding: 2px 5px;
}
</style>

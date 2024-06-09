<template>
    <div class="bg-container"/>
    <navigation-bar></navigation-bar>
    <!-- <div class="course-square">
        <course v-for="(course, index) in courses" :key="index" :course="course" />
    </div> -->

    <div style="height: 20px;"></div>

    <div style="width: 100%; justify-content: center; display: flex">

    <div style="width: 1400px; display: flex;">
        <el-row :gutter="20" style="margin-right: 15px;margin-left: 15px;" type="flex">
            <el-col v-for="(course, index) in courses" :key="index" :span="6">
                <course :course="course" />
            </el-col>
        </el-row>
    </div>

    </div>
    <blog-bottom/>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import course from "@/pages/education/components/course/index.vue";
import createCourseButton from "@/pages/education/components/createCourseButton/index.vue";
import {findCourseAPI} from "@/pages/education/components/findCourseButton/api/api";
import async from "async";
import BlogBottom from "@/pages/blog/components/blogBottom/index.vue";
// import navigationBarTeacher from "@/pages/education/components/navigationBarTeacher/index.vue";
export default {
    name: "courseSquare",
    components: {
        BlogBottom,
        navigationBar,course,createCourseButton
    },
    data() {
        return {
            courses: [
                {
                    image: 'https://placehold.co/300x150',
                    // image: '@/assets/education/course/数据库.jpg',
                    title: '数据库',
                    teacher: '黄坚',
                    credit: '4',
                    dest: '/education/courses/DBMS'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '信号处理基础',
                    teacher: '胡峻林',
                    credit: '2',
                    dest: '/education/courses/signal'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '互联网营销',
                    teacher: '杨晴虹',
                    credit: '2',
                    dest: '/education/courses/internetMarketing'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '软件工程',
                    teacher: '杨溢龙',
                    credit: '4',
                    dest: '/education/courses/softwareEngineer'
                },
            ]
        }
    },
    methods:{
    },
    async mounted() {
        let data = {
            name: '',
            page: 1,
            pageSize: 1000,
        };
        this.courses = await findCourseAPI(data);
        // console.log(this.courses);
    }
}
</script>

<style scoped>

.el-row {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
}
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
.course-square {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}
</style>

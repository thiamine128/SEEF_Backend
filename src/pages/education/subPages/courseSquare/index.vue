<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <div class="course-square">
        <course v-for="(course, index) in courses" :key="index" :course="course" />
    </div>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import todayLesson from "@/pages/education/components/todayLesson/index.vue";
import course from "@/pages/education/components/course/index.vue";
// import card
export default {
    name: "courseSquare",
    components: {
        todayLesson,navigationBar,course
    },
    data() {
        return {
            courses: [
                {
                    image: 'https://placehold.co/300x150',
                    // image: '@/assets/education/course/数据库.png',
                    title: '数据库',
                    teacher: '黄坚',
                    credit: '4'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '信号处理基础',
                    teacher: '胡峻林',
                    credit: '2'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '互联网营销',
                    teacher: '杨晴虹',
                    credit: '2'
                },
                {
                    image: 'https://placehold.co/300x150',
                    title: '软件工程',
                    teacher: '杨溢龙',
                    credit: '4'
                },
            ]
        }
    },
    methods:{
    }
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
    background: url('@/assets/education/education_bg.jpg');
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
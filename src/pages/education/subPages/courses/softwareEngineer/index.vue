<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course"/>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
export default {
    name: "softwareEngineer",
    components: {
        navigationBar, courseInfo
    },
    data(){
        return {
            course: {
                name: "软件工程",
                teacher: "杨溢龙",
                schedule: "1~16周周二，345节",
                location: "主南405",
                credits: 4,
                assessment: "平时作业+大作业+期末考试",
                image: "../../../../assets/education/course/软件工程.png"
                // image: "@/assets/education/course/数据库.jpg"
            }
        }
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
.left-main {
    width: 500px;
    height: 500px;
}
.right-main {
    width: 500px;
    height: 800px;
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

.content-container{
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 20px;
    width: 1200px;
}
</style>
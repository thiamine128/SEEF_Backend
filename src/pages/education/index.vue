<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <div class="content-container">
        <ddl r-title="ddl"/>
        <today-lesson r-title="ddl"/>
<!--        <today-lesson/>-->
<!--        <div class="left-main">-->
<!--        </div>-->
<!--        <div class="right-main">-->
<!--        </div>-->
    </div>


</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import ddl from "@/pages/education/components/ddl/index.vue";
import todayLesson from "@/pages/education/components/todayLesson/index.vue";
export default {
    name: "index",
    components: {
        todayLesson,navigationBar,ddl
    },
    methods:{
        myClass() {
            console.log("Click");
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
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
</style>
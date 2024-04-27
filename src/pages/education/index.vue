<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <div class="main">
        <ddl/>
        <today-lesson/>
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
</style>
<!--<template>-->
<!--    <div class="parent-component">-->
<!--        <ddl />-->
<!--        <today-lesson />-->
<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--import navigationBar from "@/pages/education/components/navigationBar/index.vue";-->
<!--import ddl from "@/pages/education/components/ddl/index.vue";-->
<!--import todayLesson from "@/pages/education/components/todayLesson/index.vue";-->
<!--import matter from "@/pages/education/components/ddl/matter.vue";-->

<!--export default {-->
<!--    name: 'index',-->
<!--    components: {-->
<!--        ddl,-->
<!--        todayLesson-->
<!--    }-->
<!--    // 其他选项...-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.parent-component {-->
<!--    /* 样式 */-->
<!--    display: grid;-->
<!--    grid-template-columns: 1fr 1fr;-->
<!--    grid-gap: 16px;-->
<!--}-->
<!--</style>-->
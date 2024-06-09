<template>
    <div class="bg-container">

    </div>
    <navigation-bar/>

    <div style="width: 100%; display: flex; justify-content: center">
        <div class="content-container">
            <time-table></time-table>
        </div>
    </div>

    <blog-bottom/>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import timeTable from "@/pages/education/components/timeTable/index.vue";
import BlogBottom from "@/pages/blog/components/blogBottom/index.vue";
export default {
    name: "mySchoolTimeTable",
    components: {
        BlogBottom,
        navigationBar,
        timeTable
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
    background: url('@/assets/education/bg.png');
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

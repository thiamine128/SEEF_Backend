<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <div style="width: 100%;display: flex; justify-content: center">
        <div class="info-container">
            <course-info :course-id="$route.params.courseId"/>
            <div style="height: 40px"/>
        </div>
    </div>
    <blog-bottom/>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import router from "@/router";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
import async from "async";
import {ref} from "vue";
import BlogBottom from "@/pages/blog/components/blogBottom/index.vue";
export default {
    // const router=useRouter(),
    name: "courses",
    components: {
        BlogBottom,
        courseInfo,
        navigationBar
    },
    methods:{

    },
    data(){
        return {

        }
    },
    mounted() {

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
.info-container {
    margin-top: 20px;
    width: 1200px;
    min-height: auto;
    background-color: rgba(255, 255, 255, 0.7);
}
</style>

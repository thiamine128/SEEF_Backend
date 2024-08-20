<template>
    <div class="bg-container"/>
    <navigation-bar/>

    <div style="display: flex;flex-direction: column; width: 100%; justify-content: center; align-items: center">

        <div style="display: flex;flex-direction: row;
        justify-content: space-between; width: 1200px; aspect-ratio: 5/2.3; margin-top: 20px">
            <img src="@/assets/education-enhance/education_homepage.png" style="width: 100%; opacity: 0.96" alt="404">
        </div>

        <div v-if="isStu" style="margin-top: 20px; width: 1200px; display: flex; flex-direction: row; justify-content: space-between">
            <div style="width: 68%; display: flex">
                <md-field style="border: 1px solid rgba(11, 11, 11, 0.1); min-height: 0; margin-bottom: 10px" :input-content="broadcast_content"></md-field>
            </div>
            <div style="width: 30%" class="content-container">
                <ddl r-title="任务列表"/>
                <today-lesson style="margin-top: 20px" r-title="学期课程"/>
            </div>
        </div>

        <div/>

    </div>
    <blog-bottom/>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import ddl from "@/pages/education/components/ddl/index.vue";
import todayLesson from "@/pages/education/components/todayLesson/index.vue";
import BlogBottom from "@/pages/blog/components/blogBottom/index.vue";
import mdField from "@/pages/blog/components/mdField/index.vue";
import {callError} from "@/callMessage";
import store from "@/store/store";
export default {
    name: "index",
    components: {
        mdField,
        BlogBottom,
        todayLesson,navigationBar,ddl
    },
    methods:{
        myClass() {
            console.log("Click");
        },
        async makeBroadcast(){
            try {
                const response = await fetch('/edu_broadcast.md');
                if (response.ok) {
                    this.broadcast_content = await response.text();
                }
            } catch (error) {
                //callError(error);
            }
        },
    },
    async mounted() {
        document.documentElement.scrollTop = 0;
        await this.makeBroadcast();
    },
    data(){
        return{
            broadcast_content: ''
        }
    },
    computed:{
        isStu(){
            return store.getters.getEduIdentity === 'student';
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
    background: url('@/assets/education-enhance/education_bg.png');
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
    flex-direction: column;
    align-items: center;
    width: 25%;
}
</style>

<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
            </div>
            <hr>
            <div :style="listStyle">
                <div v-for="cls in classes">
                    <recommend-button :r-name="cls.courseName" @click="goto(cls.courseId)"></recommend-button>
<!--                    <el-link type="primary" :href="'/education/courses/' + cls.courseId">{{ cls.courseName }}</el-link>-->
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ddlButton from "@/pages/education/components/ddlButton/index.vue";
import {getMyClassAPI, getTeachersInClassAPI, listTeacherClassAPI} from "@/pages/education/api";
import RecommendButton from "@/pages/blog/components/recommendButton/index.vue";
export default {
    name: "todayLesson",
    props: ['heightSet', 'rTitle'],
    components:{RecommendButton, ddlButton},
    data(){
        return{
            frameStyle:{
                width: '100%',
                height: '45%',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                border: '0.2px solid rgba(155, 155, 155, 0.3)',
                display: 'flex',

            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1'
            },
            classes: [],
        }
    },
    methods: {
        async pullClasses() {
            this.classes = await getMyClassAPI();
        },
        goto(courseId){
            this.$router.push("/education/courses/" + courseId);
        }
    },
    computed: {

    },
    mounted() {
        // this.frameStyle.height = this.heightSet;
        this.pullClasses();
    }
}
</script>

<style scoped>
.container{
    width: 88%;
    height: 90%;
    margin: auto;
    display: flex;
    flex-direction: column;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 17px;
    text-align: center;
}
.moreFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 13px;
    text-align: center;
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 22px;
}
.linkStyle{
    text-decoration: none;
    color: #000;
    cursor: pointer;
}
hr{
    width: 100%;
}
.sortButton{
    display: flex;
    justify-content: center;
    flex-direction: row;
    align-items: center;
    width: 100px;
    padding: 5px;
    background-color: rgba(117, 21, 21, 0);
}
</style>

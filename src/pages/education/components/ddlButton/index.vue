<template>
    <div class="title-container"
         @mouseenter="hover = true"
         @mouseleave="hover = false">
        <el-button style="font-size: 10px" type="primary" @click="goToCourse(courseId)">
            {{ courseName }}
        </el-button>

            <el-text class="ddl-title">{{ title }}</el-text>

        <div style="display: flex; flex-direction: column">
            <div style="font-size: 11px">截止日期</div>
            <el-text class="ddl-duedate">{{ dueDate }}</el-text>
        </div>
<!--        <el-button class="submit-button" :type="submitType" :icon="submitIcon">{{ submitText }}</el-button>-->
  </div>
</template>

<script>
import { computed } from 'vue';


export default {
    name: "ddlButton",
    props: ['title', 'dueDate', 'courseName', 'submitted', 'courseId'],
    data(){
        return{
            hover: false,
        }
    },
    setup(props) {
        const submitType = computed(() => props.submitted != null ? "success" : "danger");
        const submitText = computed(() => props.submitted != null ? "已提交" : "未提交");
        const submitIcon = computed(() => props.submitted != null ? "check" : "close")
        return {
            submitType,
            submitText,
            submitIcon
        }
    },
    methods: {
        goToCourse(courseId) {
            window.open("/education/courses/"+courseId);
        }
    },
    mounted() {
    }
}
</script>

<style scoped>

.title-container{
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: left;
    padding: 12px;
    margin: 3px;
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.7);
    transition: background-color 0.5s ease;
}
.title-container:hover{
    background-color: rgba(255, 255, 255, 1);
}
.submit-button {
    /*margin-right: 2px;*/
    /*margin-left: 4px;*/
}
.ddl-title {

    font-size: 10px;
    margin: auto;
    text-align: left;
}
</style>

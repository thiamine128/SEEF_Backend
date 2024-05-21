<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <create-course-button v-if="showCreateCourseButton"/>
    <create-class-button v-if="showCreateClassButton"/>
    <find-course-button/>
    <course-list :courses="courses" />


</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import createCourseButton from "@/pages/education/components/createCourseButton/index.vue";
import createClassButton from "@/pages/education/components/createClassButton/index.vue";
import findCourseButton from "@/pages/education/components/findCourseButton/index.vue";
import courseList from "@/pages/education/components/courseList/index.vue";
import {reactive} from 'vue';
import store from "@/store/store";

export default {
    name: "myLesson",
    components: {
        createCourseButton,
        navigationBar,
        createClassButton,
        findCourseButton,
        courseList
    },
    methods: {

    },
    computed: {
        showCreateCourseButton() {
            return store.getters.getEduIdentity === 'teacher';
        },
        showCreateClassButton() {
            return store.getters.getEduIdentity === 'teacher';
        },

    },
    data() {
        return {
            courses: [
                {name: '课程1'},
                {name: '课程2'},
            ],
        };
    },
}
</script>

<style scoped>

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

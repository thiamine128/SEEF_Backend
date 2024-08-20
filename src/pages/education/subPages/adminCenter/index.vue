<template>
    <div class="bg-container"/>
    <navigation-bar/>

    <div style="display: flex;justify-content: left">
        <div style="align-items: center;display: flex; flex-direction: column; background-color: rgba(255, 255, 255, 0.6); height: 100vh">
            <img alt="404" src="@/assets/education/edu.png" style="width: 140px">
            <add-teacher-button v-if="isAdmin"/>
            <add-admin-button v-if="isAdmin"/>
            <add-butch-teacher-button v-if="isAdmin"/>
            <add-butch-student-button v-if="isAdmin"/>
            <download-muban v-if="isAdmin"/>
        </div>
    </div>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import addTeacherButton from "@/pages/education/components/addTeacherButton/index.vue";
import addAdminButton from "@/pages/education/components/addAdminButton/index.vue";
import addButchTeacherButton from "@/pages/education/components/addButchTeacherButton/index.vue";
import addButchStudentButton from "@/pages/education/components/addButchStudentButton/index.vue";
import store from "@/store/store";
import downloadMuban from "@/pages/education/components/downloadMuban/index.vue";
export default {
    name: "adminCenter",
    components: {
        downloadMuban,
        navigationBar,
        addTeacherButton,
        addAdminButton,
        addButchTeacherButton,
        addButchStudentButton,
    },
    methods:{
    },
    computed: {
        isAdmin() {
            return store.getters.getEduIdentity === 'admin';
        }
    },
    mounted() {
        if(store.getters.getEduIdentity !== 'admin') {
            this.$router.push('/education');
        }
    }
}
</script>

<style scoped>
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
</style>

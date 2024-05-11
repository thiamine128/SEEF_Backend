<template>
    <div class="bg-container"/>
    <navigation-bar/>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";

export default {
    name: "myLesson",
    components: {
        navigationBar
    },
    methods: {

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
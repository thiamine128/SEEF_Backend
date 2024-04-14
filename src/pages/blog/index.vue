<template>
    <div class="bg-container"/>
    <nav-bar></nav-bar>
    <blogTitle title="我的软件生涯" post-time="2023-12-11" update-time="2023-12-15"/>
</template>

<script>

import blogTitle from './components/title/index.vue'
import navBar from "@/pages/blog/components/navBar/index.vue";

export default {
    name: "blog",
    components: {
        blogTitle, navBar
    }
}
</script>

<style scoped>
.bg-container {
    background: url('@/assets/blog/blog_bg.png');
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}
</style>

<template>
    <div class="bg-container"/>
    <nav-bar></nav-bar>
    <router-view/>
</template>

<script>
import navBar from "@/pages/blog/components/navBar/index.vue";

export default {
    name: "blog",
    components: {
        navBar
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

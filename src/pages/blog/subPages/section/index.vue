<template>
    <div class="content-container">
        <article-list class="content-left" height-set="2000px" r-title="Wonderful Zone"></article-list>
        <div class="content-right">
        </div>
    </div>
</template>

<script>
import articleList from "@/pages/blog/components/articleList/index.vue";
export default {
    name: "section",
    components: {articleList}
}
</script>

<style scoped>
.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 60%;
}
.content-right{
    width: 38%;
}
</style>

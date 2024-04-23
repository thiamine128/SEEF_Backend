<template>
    <div class="content-container">
        <div class="content-left">
            <article-list height-set="2000px" r-title="Wonderful Zone"></article-list>
        </div>
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
    width: 68%;
}
.content-right{
    width: 30%;
}
</style>

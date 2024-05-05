<template>

    <img src="@/assets/blog/homehead.png" style="width: 1200px; margin-top: 25px" alt="404">

    <div class="content-container">

        <div class="content-left">
            <article-list height-set="800px" r-title="热门专区"></article-list>
            <article-list height-set="1800px" r-title="热门文章"></article-list>
        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement.png" style="width: 100%;">
            <recommend height-set="300px" r-title="今日推荐"/>
            <recommend height-set="400px" r-title="关注列表"/>
            <right-pin r-title="热门博主" content-name="recommend"></right-pin>
        </div>
    </div>
</template>

<script>
import mdField from "@/pages/blog/components/mdField/index.vue";
import blogTitle from "@/pages/blog/components/title/index.vue";
import rightPin from "@/pages/blog/components/rightPin/index.vue";
import axios from "axios";
import {inject, ref} from "vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import PersonalBox from "@/pages/blog/components/personalBox/index.vue";
import Catalog from "@/pages/blog/components/catalog/index.vue";
import ArticleList from "@/pages/blog/components/articleList/index.vue";
export default {
    name: "homepage",
    components: {ArticleList, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},

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
    width: 70%;
    gap: 50px;
}
.content-right{
    width: 28%;
}
</style>

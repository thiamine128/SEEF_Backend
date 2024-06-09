<template>

    <img src="@/assets/blog/homehead.png" :class="{homeHead1: !anime, homeHead2: anime}" alt="404">

    <div class="content-container">

        <div class="content-left">

            <md-field style="min-height: 0; margin-bottom: 10px" :input-content="broadcast_content"></md-field>

            <article-list height-set="740px" r-title="所有专区 Topic"
                          :list-set="sectionList" select="section" :total-page="sectionTotalPage"
                          @page-change="getSections"/>

            <article-list height-set="870px" r-title="所有博客 Blog"
                          :list-set="articleList" select="article" :total-page="articleTotalPage"
                          @page-change="getArticles"></article-list>

        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement.png" style="width: 100%;">
            <recommend height-set="300px" r-title="关注列表"/>
            <recommend height-set="400px" r-title="今日推荐"/>
            <right-pin r-title="热门博主" content-name="recommend"></right-pin>
        </div>
    </div>
</template>

<script>
import mdField from "@/pages/blog/components/mdField/index.vue";
import blogTitle from "@/pages/blog/components/title/index.vue";
import rightPin from "@/pages/blog/components/rightPin/index.vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import PersonalBox from "@/pages/blog/components/personalBox/index.vue";
import Catalog from "@/pages/blog/components/catalog/index.vue";
import ArticleList from "@/pages/blog/components/articleList/index.vue";
import {callError} from "@/callMessage";

export default {
    name: "homepage",
    components: {ArticleList, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},
    data(){
        return{
            broadcast_content: '',
            sectionList: [],
            sectionTotalPage: 1,
            articleList: [],
            articleTotalPage: 1,
            anime: true
        }
    },
    mounted() {
        this.makeBroadcast();
        this.getSections(1);
        this.getArticles(1);
    },
    methods:{

        async makeBroadcast(){
            try {
                const response = await fetch('/blog_broadcast.md');
                if (response.ok) {
                    this.broadcast_content = await response.text();
                } else callError('无法读取公告内容');
            } catch (error) {
                //callError(error);
            }
        },

        async getSections(pageNum){ //获取全部板块信息
            try{
                const response = await this.$http.get(`topic/pagedList?page=${pageNum}&pageSize=10`);
                if (response.status === 200) {
                    this.sectionList = response.data.data.records;
                    this.sectionTotalPage = Math.ceil(response.data.data.total / 10);
                } else callError('网络错误');
            }catch (error){
                //callError(error);
            }
        },

        async getArticles(pageNum){
            try{
                const response = await this.$http.get(
                    `blog/viewBlogs?page=${pageNum}&pageSize=6&previewLength=500&orderBy=popularity&sort=desc`
                );
                //console.log(response);
                if (response.status === 200) {
                    this.articleList = response.data.data.records;
                    this.articleTotalPage = Math.ceil(response.data.data.total / 6);
                } else callError('网络错误');
            }catch (error){
                //callError(error);
            }
        }

    }
}
</script>

<style scoped>
.homeHead1{
    width: 0;
    margin-top: 25px;
    transition: width 0.5s;
}
.homeHead2{
    width: 1200px;
    margin-top: 25px;
    transition: width 0.5s;
}
.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 70%;
    display: flex;
    flex-direction: column;
    gap: 20px;
}
.content-right{
    width: 28%;
}
</style>

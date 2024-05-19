<template>

    <div class="content-container">

        <div class="content-left">

            <article-list height-set="740px" r-title="~Topic~ 专区搜索结果"
                          :list-set="sectionList" select="section" :total-page="sectionTotalPage"
                          @page-change="getSections"/>

            <article-list height-set="880px" r-title="~Blog~ 博客搜索结果"
                          :list-set="articleList" select="article" :total-page="articleTotalPage"
                          @page-change="getArticles"></article-list>

        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement2.png" style="width: 100%;">
            <img alt="404" src="@/assets/blog/advertisement3.png" style="width: 100%;">
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
    name: "search",
    components: {ArticleList, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},
    data(){
        return{
            broadcast_content: '',
            sectionList: [],
            sectionTotalPage: 1,
            articleList: [],
            articleTotalPage: 1,
            searchContent: ''
        }
    },
    mounted() {
        this.searchContent = this.$route.params.search.replace(/\s/g, '');
        this.getSections(1);
        this.getArticles(1);
    },
    methods:{

        async getSections(pageNum){ //获取全部板块信息
            try{
                const response = await this.$http.get(`topic/pagedList?page=${pageNum}&pageSize=10&name=${this.searchContent}`);
                if (response.status === 200) {
                    this.sectionList = response.data.data.records;
                    this.sectionTotalPage = Math.ceil(response.data.data.total / 10);
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        },

        async getArticles(pageNum){
            try{
                const response = await this.$http.get(
                    `blog/viewBlogs?page=${pageNum}&pageSize=6&previewLength=500&keyword=${this.searchContent}`
                );
                console.log(response);
                if (response.status === 200) {
                    this.articleList = response.data.data.records;
                    this.articleTotalPage = Math.ceil(response.data.data.total / 6);
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        }

    }
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
    display: flex;
    flex-direction: column;
    gap: 20px;
}
.content-right{
    width: 28%;
}
</style>

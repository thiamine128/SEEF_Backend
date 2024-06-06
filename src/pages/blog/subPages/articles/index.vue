<template>
    <div class="content-container">
        <div class="content-left">

            <article-list height-set="1925px" :r-title="topicName"
            :list-set="artiList" select="article" :total-page="totalPage"
            @page-change="pullArticles"></article-list>


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
import articleList from "@/pages/blog/components/articleList/index.vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import rightPin from "@/pages/blog/components/rightPin/index.vue";
import {useRouter} from "vue-router";
import {callError} from "@/callMessage";

export default {
    name: "articles",
    components: {rightPin, recommend, articleList},
    props: ['sectionName'],
    data(){
        return{
            artiList : [],
            totalPage : 10,
            topicName : ''
        }
    },
    methods:{
        async pullArticles(pageNum){
            try{

                const response = await this.$http.get(
                    `blog/viewBlogs?page=${pageNum}&pageSize=15&previewLength=500&topicIds=${this.$route.params.topicId}&orderBy=popularity&sort=desc`,{
                    'Content-Type': 'application/x-www-form-urlencoded',
                });

                console.log(response);
                if (response.status === 200) {
                    this.artiList = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 15);
                    document.documentElement.scrollTop = 0;
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        }
    },
    mounted() {
        try{
            this.topicName = this.$route.params.sectionName;
            this.pullArticles(1);
        }catch (e) {
            callError(e);
            this.$router.push('/blog/');
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
    width: 68%;
}
.content-right{
    width: 30%;
}
.titleFont {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 28px;
    text-align: center;
    color: #4b4b4b;
}
</style>

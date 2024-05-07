<template>
    <div class="content-container">
        <div class="content-left">

            <article-list height-set="2000px" r-title="Wonderful Zone"></article-list>

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
export default {
    name: "articles",
    components: {rightPin, recommend, articleList},
    data(){
        return{

        }
    },
    methods:{

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

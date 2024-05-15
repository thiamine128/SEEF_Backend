<template>
    <div class="content-container">
        <div class="content-left">
<!--            <article-list height-set="1000px" r-title="动态 Wonderful Space"></article-list>-->
            <space-box/>
            <space-box/>
            <space-box/>
            <space-box/>
            <space-box/>
        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement.png" style="width: 100%;">
            <recommend height-set="400px" r-title="关注列表"/>
        </div>
    </div>
</template>

<script>
import articleList from "@/pages/blog/components/articleList/index.vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import SpaceBox from "@/pages/blog/components/spaceBox/index.vue";
export default {
    name: "space",
    components: {SpaceBox, recommend, articleList},
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
</style>

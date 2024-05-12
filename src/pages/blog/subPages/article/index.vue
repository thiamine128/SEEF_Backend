<template>
    <blogTitle :title="title" :post-time="postTime" :update-time="updateTime"/>
    <div class="content-container">
        <div class="content-left">
            <md-field id="md-hook" :input-content="content"></md-field>

            <div class="comment-head">

                <div style="cursor: default; margin-left: 10px">
                    0 comments
                </div>

                <div style="cursor: pointer; margin-right: 10px; color: #0c82e9">
                    Add Comment
                </div>
            </div>

        </div>

        <div class="content-right">

            <personal-box/>
            <recommend height-set="300px" r-title="今日推荐"/>
            <recommend height-set="400px" r-title="关注列表"/>
            <right-pin r-title="null" container="#md-hook" content-name="catalog"></right-pin>

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
import CommentBox from "@/pages/blog/components/commentBox/index.vue";
import dayjs from "dayjs";
export default {
    name: "article",
    components: {CommentBox, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},
    async mounted() {
        try {
            const response = await this.$http.get(`blog/detail?blogId=${this.$route.params.id}`);
            console.log(response);
            this.content = response.data.data.content;
            this.updateTime = this.dateF(response.data.data.updateTIme);
            this.postTime = this.dateF(response.data.data.createTime);
            this.title = response.data.data.title;
        } catch (error) {
            console.error("Error fetching Markdown file:", error);
        }
    },
    data(){
        return{
            content: '', title: '', postTime: '1919-8-10', updateTime: '1919-8-10',
        }
    },
    methods:{
        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },
    }
}
</script>

<style scoped>
.comment-head{
    margin-top: 30px;
    height: 50px;
    font-family: 'rage', sans-serif;
    font-size: 30px;
    background-color: rgba(255, 255, 255, 0.9);
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
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
}
.content-right{
    width: 28%;
}
</style>

<template>
    <blogTitle title="BUAA-SE-Wonderful" post-time="2023-12-11" update-time="2023-12-15"/>
    <div class="content-container">
        <div class="content-left">
            <md-field id="md-hook" :input-content="content"></md-field>

            <div class="comment-head">
                <div style="margin: auto">
                    Comment
                </div>
            </div>

            <comment-box />


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
export default {
    name: "article",
    components: {CommentBox, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},
    async created() {
        try {
            const response = await axios.get(inject('webURL')+"testMarkdown.md");
            this.content = response.data;
            console.log(this.content)
        } catch (error) {
            console.error("Error fetching Markdown file:", error);
        }
    },
    data(){
        return{
            content: '',
        }
    }
}
</script>

<style scoped>
.comment-head{
    margin-top: 30px;
    height: 30px;
    font-family: 'rage', sans-serif;
    font-size: 20px;
    background-color: rgba(255, 255, 255, 0.9);
    width: 100%;
    display: flex;
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

<template>
    <blogTitle title="BUAA-SE-Wonderful" post-time="2023-12-11" update-time="2023-12-15"/>
    <div class="content-container">
        <md-field class="content-left" :input-content="content"></md-field>
        <div class="content-right">
            <recommend height-set="300px" r-title="今日推荐"/>
            <right-pin r-title="关注列表"></right-pin>
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
export default {
    name: "article",
    components: {recommend, blogTitle, mdField, rightPin},
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
.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 70%;
}
.content-right{
    width: 28%;
}
</style>

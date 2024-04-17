<template>
    <div class="textSet">
        <div class="marginSet">
            <markdown :source="content" :plugins="plugins"/>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import {inject} from "vue";
import markdown from 'vue3-markdown-it'
import MarkdownItKatex from 'markdown-it-katex'

export default {
    name: "mdField",
    async created(){
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
        plugins:[
            {
                plugin: MarkdownItKatex
            }
        ]
      }
    },
    components:{
        markdown
    }
}
</script>

<style scoped>
@import "mdStyle.css";
.textSet{
    background-color: rgba(255, 255, 255, 0.9);
    min-height: 0;
    border-radius: 10px;
    text-align: left;
}
.marginSet{
    margin: 70px;
}
</style>

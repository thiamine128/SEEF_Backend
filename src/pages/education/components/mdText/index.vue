<template>
    <div :class="{textSet: true}">
        <div class="marginSet">
            <markdown :source="inputContent" :plugins="plugins"/>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import {inject, watch, defineProps} from "vue";
import markdown from 'vue3-markdown-it'
import MarkdownItKatex from 'markdown-it-katex'
export default {
    name: "mdText",
    props: ['inputContent'],
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
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 5px;
    font-size: 20px;
    text-align: left;
}
.marginSet{
    margin: 5px;
}
</style>

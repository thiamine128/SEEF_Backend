<template>
    <div class="textSet">
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
    name: "mdField",
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
    background-color: rgba(255, 255, 255, 0.9);
    min-height: 0;
    border-radius: 10px;
    text-align: left;
}
.marginSet{
    margin: 70px;
}
</style>

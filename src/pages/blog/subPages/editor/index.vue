<template>
    <div class="editor-row">
        <div class="left-column">
            <div class="postMark">Post</div>
            <img class="photo-set" alt="404" src="@/assets/blog/photo-camera.png">
        </div>
        <div class="inputStyle">
            <input class="textarea-title" v-model="mdTitle" placeholder="在此处输入标题">
            <textarea class="textarea-content" v-model="content" ref="left_s" placeholder="在此处编辑Markdown"></textarea>
        </div>
        <md-field class="mdStyle" :inputContent="result" ref="right_s"/>
    </div>
</template>

<script>
import {ref, watch} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
export default {
    name: "editor",
    components: {MdField},
    data(){
        const content = ref('');
        return{
            content, mdTitle: ''
        }
    },
    computed:{
        result(){
            return '# '+this.mdTitle+"\n"+this.content;
        }
    }
}
</script>

<style scoped>
.left-column{
    width: 4%;
    height: 600px;
    justify-content: center;
    background-color: #eaeaea;
    border-radius: 3px;
}
.photo-set{
    margin-top: 10px;
    width: 70%;
}
.editor-row{
    display: flex;
    flex-direction: row;
    justify-content: right;
    width: 100%;
    margin-top: 20px;
}

.inputStyle{
    width: 48%;
    height: 600px;
    display: flex;
    flex-direction: column;
}

.textarea-title{
    /*width: 100%;*/
    height: 40px;
    font-family: 'consolas', sans-serif;
    font-size: 20px;
    resize: none;
    padding-left: 40px;
}
.textarea-content{
    /*width: 100%;*/
    height: 560px;
    font-family: 'consolas', sans-serif;
    font-size: 18px;
    padding: 40px;
    resize: none;
}

.mdStyle{
    width: 48%;
    height: 600px;
    overflow-y: auto;
}
:deep(.marginSet){
    margin: 40px;
}
:deep(.textSet){
    border-radius: 0;
}
.postMark{
    font-family: 'rage', sans-serif;
    font-weight: bold;
    margin-top: 5px;
}
</style>

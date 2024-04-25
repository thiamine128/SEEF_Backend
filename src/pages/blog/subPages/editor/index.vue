<template>
    <div class="editor-row">
        <div class="left-column">
            <div class="postMark">Post</div>

            <left-button :path="require('@/assets/blog/photo-camera.png')" name="上传图片" />
            <left-button :path="require('@/assets/blog/code.png')" name="添加代码" @click = "addCode" />
            <left-button :path="require('@/assets/blog/formula.png')" name="添加公式" @click= "addFormula" />
            <left-button :path="require('@/assets/blog/upload.png')" name="上传文件" @click="upload" />
            <left-button :path="require('@/assets/blog/download.png')" name="载入本地" @click="download"/>
            <left-button :path="require('@/assets/blog/save.png')" name="暂存" @click="save"/>
            <left-button :path="require('@/assets/blog/post.png')" name="发布" @click="post"/>

        </div>
        <div class="inputStyle">
            <input class="textarea-title" v-model="mdTitle" placeholder="在此处输入标题">
            <textarea class="textarea-content" v-model="content" ref="left_s" placeholder="在此处编辑Markdown"></textarea>
        </div>
        <md-field class="mdStyle" :inputContent="result" ref="right_s"/>
    </div>
</template>

<script>
import {ref} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
import LeftButton from "@/pages/blog/components/leftButton/index.vue";

export default {
    name: "editor",
    components: {LeftButton, MdField},
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
    },
    methods:{
        addCode(){
            this.content += "\n```your language\n\n```";
        },
        addFormula(){
            this.content += "\n$your formula$";
        },
        upload(){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ".md, .markdown";
            fileInput.addEventListener("change", this.handleFileUpload);
            fileInput.click();
        },
        handleFileUpload(event) {
            const selectedFile = event.target.files[0];
            if (selectedFile) this.readMarkdownFile(selectedFile);
        },
        readMarkdownFile(file) {
            const reader = new FileReader();
            reader.onload = (event) => {
                this.content = event.target.result
            };
            reader.readAsText(file);
        },
        download(){
            const blob = new Blob([this.content], { type: 'text/plain;charset=utf-8' });
            saveAs(blob, (this.mdTitle.length > 0 ? this.mdTitle:'temp')+ '.md');
        },
        save(){

        },
        post(){

        }
    }
}
</script>

<style scoped>

.left-column{
    width: 4%;
    height: 600px;
    justify-content: center;
    align-items: center;
    background-color: #eaeaea;
    border-radius: 3px;
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

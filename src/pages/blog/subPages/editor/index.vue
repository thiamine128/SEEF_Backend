<template>
    <div class="editor-row">
        <div class="left-column">
            <div class="postMark">Post</div>

            <left-button :path="require('@/assets/blog/photo-camera.png')" name="上传图片" @click="uploadImage"/>
            <left-button :path="require('@/assets/blog/code.png')" name="添加代码" @click = "addCode" />
            <left-button :path="require('@/assets/blog/formula.png')" name="添加公式" @click= "addFormula" />
            <left-button :path="require('@/assets/blog/upload.png')" name="上传文件" @click="upload" />
            <left-button :path="require('@/assets/blog/download.png')" name="载入本地" @click="download"/>
            <left-button :path="require('@/assets/blog/save.png')" name="暂存" @click="save"/>
            <left-button :path="require('@/assets/blog/post.png')" name="发布" @click="post"/>

        </div>
        <div class="inputStyle">
            <div class="tagLine">
                <el-button style="width: 100px; height: 100%"
                   type="info" plain @click="callSection()"> {{sectionName}} </el-button>

                <el-input class="textarea-tag" v-model="tagName" placeholder="添加标签"/>
                <el-button style="width: 30px; height: 100%; margin-right: 10px" type="info" plain @click="createTag()"> + </el-button>

                <div v-for="item in tags" class="tagSet" @click="removeTag(item)">
                    {{item}}
                </div>

            </div>
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
import store from "@/store/store";
import {callSuccess, callError} from "@/callMessage";
import axios from "axios";

export default {
    name: "editor",
    components: {LeftButton, MdField},
    props: ['sectionName', 'topicId'],
    data(){
        const content = ref('');
        return{
            content, mdTitle: '', tagName: '',
            tags: []
        }
    },
    mounted() {
        let preData = store.getters.getContent;
        if (preData.title.length > 0){
            this.mdTitle = preData.title;
            this.content = preData.content;
        }
    },
    computed:{
        result(){
            return '# '+this.mdTitle+"\n"+this.content;
        }
    },
    methods:{

        callSection(){
            this.$emit('callFloat', '', 2);
        },

        createTag(){

            if (this.tagName.length == 0){
                callError('标签不可为空');
                return;
            }
            if (this.tagName.length > 10){
                callError('标签过长');
                return;
            }
            if (this.tags.includes(this.tagName)){
                callError('标签不可重复');
                return;
            }
            if (this.tags.length >= 3){
                callError('标签过多');
                return;
            }

            this.tags.push(this.tagName);

        },
        removeTag(item){
            this.tags.pop(item);
        },

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
            store.commit('setContent', {
                "title": this.mdTitle,
                "content": this.content
            });
            callSuccess('暂存成功');
        },
        async post(){
            store.commit('setContent', {
                "title": '',
                "content": ''
            });
            if (this.topicId != -1){
                try{
                    const response = await this.$http.post(`blog/create`, {
                        "title": this.mdTitle,
                        "context": this.content,
                        "topicId": this.topicId,
                        "tags": this.tags
                    });
                    if (response.status === 200) {
                        callSuccess('发表博客成功');
                        this.$router.push(`/blog/articles/${this.topicId}/${this.sectionName}`);
                    } else callError('网络错误');
                }catch (error){
                    callError(error);
                }
            }else callError('请选择专区');
        },



        uploadImage(){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ".jpg, .png";
            fileInput.addEventListener("change", this.handleImage);
            fileInput.click();
        },

        async handleImage(event){
            const file = event.target.files[0];
            const formData = new FormData();
            const fileUploadData = new FormData();
            formData.append('file', file);
            try {

                const response = await this.$http.post('/common/requestUploadImage', formData, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                });

                console.log(response.data);
                const keyData = {
                    "name": 'ok',
                    "policy": response.data.data.encodedPolicy,
                    "OSSAccessKeyId": response.data.data.accessKeyId,
                    "success_action_status": '200',
                    "signature": response.data.data.postSignature,
                    "key": response.data.data.objectName,
                    "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('image/', '')}.png`
                };

                for (let key in keyData){
                    fileUploadData.append(key, keyData[key]);
                }
                fileUploadData.append('file', file);

                const imgAxios = this.$http.create({baseURL: '/postFile'});

                const responseImage = await imgAxios.post(``,
                    fileUploadData, {
                    'Content-Type': 'application/x-www-form-urlencoded'
                });

                if (responseImage.status === 200){
                    callSuccess('图片上传成功');
                    this.content = this.content + `\n![](${response.data.data.host + '/' + response.data.data.objectName})`;
                }

            } catch (error) {
                console.error(error);
            }
        }

    }
}
</script>

<style scoped>
.tagSet{
    padding-left: 3px;
    padding-right: 3px;
    margin-left: 3px;
    height: 90%;
    min-width: 70px;
    display: flex;
    flex-direction: row;
    background-color: #e8fbff;
    border-radius: 6px;
    justify-content: center;
    align-items: center;
    font-weight: bold;
    cursor: pointer;
}
.left-column{
    width: 4%;
    height: 700px;
    justify-content: center;
    align-items: center;
    background-color: #eaeaea;
    border-radius: 3px;
}
.editor-row{
    display: flex;
    flex-direction: row;
    justify-content: right;
    width: 1400px;
    margin-top: 20px;
}

.inputStyle{
    width: 48%;
    height: 700px;
    display: flex;
    flex-direction: column;
}
.tagLine{
    height: 40px;
    display: flex;
    flex-direction: row;
    background-color: #f1f1f1;
    align-items: center;
}
.textarea-title{
    /*width: 100%;*/
    height: 40px;
    font-family: 'consolas', sans-serif;
    font-size: 20px;
    resize: none;
    padding-left: 40px;
}
.textarea-tag{
    /*width: 100%;*/
    height: 40px;
    font-family: 'consolas', sans-serif;
    font-size: 18px;
    resize: none;
    width: 100px;
    display: flex;
    justify-content: center;
    text-align: center;
}
.textarea-content{
    /*width: 100%;*/
    height: 620px;
    font-family: 'consolas', sans-serif;
    font-size: 18px;
    padding: 40px;
    resize: none;
}

.mdStyle{
    width: 48%;
    height: 700px;
    min-height: 700px;
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

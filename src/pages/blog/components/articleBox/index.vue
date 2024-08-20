<template>
    <div class="box-container" @mouseenter="hover=true" @mouseleave="hover=false">
        <img v-if="imgShow" class="img-adjust" :src="src_img" alt="404 not found" @click="callArticle">
        <div v-if="!imgShow" class="no-img-adjust" @click="callArticle">
            <div style="font-size: 80px; color: rgba(22, 22, 22, 0.1)"> {{titleShow}} </div>
        </div>

        <div class="msg">
            <div :class="{ titleFont: true }" @click="callArticle">
                {{title}}
                <div v-for="item in tagList" class="tagSet">
                    {{item}}
                </div>
            </div>
            <div :class="{ abstractFont: true }" @click="callArticle">{{abstract}}</div>
            <div class="otherMsg">
                <div :class="{ otherMsgFont: true }">{{"作者："+author}}</div>
                <div :class="{ otherMsgFont: true }">{{" | 发布时间："+postTime}}</div>
                <div :class="{ otherMsgFont: true }">{{" | 点赞："+likes}}</div>
                <div class="otherMsgFontDelete" v-if="(isAdmin || authorId === loginNow) && !isDeleted && !dShow" @click="deleteArticle"> 删除 </div>
                <div class="otherMsgFontDelete" v-if="isDeleted && !dShow"> 失效博客 </div>
            </div>
        </div>
    </div>
    <hr v-if="isHr" class="setHr">
</template>

<script>
import {deleteBlog, getBlogData, getUserData} from "@/pages/blog/api";
import {ref} from "vue";
import store from "@/store/store";
import {callInfo} from "@/callMessage";

export default {
    name: "articleBox",
    props:['title', 'abstract', 'authorId', 'postTime',
        'likes', 'articleId', 'imgSource', 'hrNotShow', 'tags', 'isDeleted', 'dShow'],
    methods:{

        async deleteArticle(){
            await deleteBlog(this.articleId);
            setTimeout(()=>{
                location.reload();
            }, 1000);
        },

        callArticle(){
            if (!this.isDeleted) this.$router.push(`/blog/article/${this.articleId}`);
            else callInfo('此帖子已被删除');
        },

        async pullPersonalData(){
            const personal_data = await getUserData(this.authorId);
            this.author = personal_data.name;
        },

        async setImage(){

            const reg = /\(([^)]+)\)/;
            if (this.imgSource != null){
                const matchResult = this.imgSource.match(reg);
                if (matchResult != null) {
                    if (matchResult[1].includes('http://chkbigevent.oss-cn-beijing.aliyuncs.com')){
                        try{
                            this.src_img =  new URL(matchResult[1], import.meta.url).href;
                            this.imgShow = true;
                        }catch (error){
                            //console.log(error);
                        }
                    }
                }else this.imgShow = false;
            }

            if (this.tags) {
                this.tagList = [];
                for (let t of this.tags){
                    if ((t+'').length > 0) this.tagList.push(t);
                }
            }

            try{
                await this.pullPersonalData();
            }catch (e){}


        }

    },
    mounted() {


        try{
            this.pullPersonalData();
            this.intervalId = setInterval(this.setImage, 500);

            if (this.hrNotShow) this.isHr = false;
            this.loginNow = store.getters.getData.id;
        }catch (e){}
    },
    data(){
        return{
            imgShow: false,
            src_img: require('@/assets/blog/testArticleImg.png'),
            author: '',
            isHr: true,
            intervalId: ref(null),
            tagList: [],
            loginNow: -1,
            hover: false,
        }
    },
    computed:{
        isAdmin(){
            return store.getters.getData.role === 'admin';
        },
        titleShow(){
            if (this.title != null){
                return this.title.slice(0, 1);
            }else return '';
        }
    }
}
</script>

<style scoped>
.box-container{
    height: 100px;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: left;
    margin: 4px;
    cursor: pointer;
    transition: background-color 0.5s;
}

.box-container:hover{
    height: 100px;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: left;
    margin: 4px;
    cursor: pointer;
    background-color: rgba(11, 11, 11, 0.02);
    transition: background-color 0.5s;
}

.img-adjust{

    aspect-ratio: 5/3;
    height: 100%;
    display: flex;
}
.no-img-adjust{
    aspect-ratio: 5/3;
    height: 100%;
    display: flex;
    background: url('@/assets/blog/article-bg.png');
}
.msg{
    display: flex;
    flex-direction: column;
    justify-content: left;
    width: 75%;
}
.otherMsg{
    display: flex;
    flex-direction: row;
    justify-content: left;
    margin-top: auto;
    align-items: center;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 25px;
    margin-left: 5px;
    text-align: left;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.abstractFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    margin-left: 7px;
    color: #8e8e8e;
    text-align: left;
}
.otherMsgFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    margin-left: 5px;
    color: #8e8e8e;
    text-align: left;
}
.setHr{
    border-top-color: rgba(44, 44, 44, 0.1);
}

.tagSet{
    padding-left: 5px;
    padding-right: 5px;
    margin-left: 3px;
    height: 60%;
    min-width: 0;
    display: flex;
    flex-direction: row;
    background-color: #def2ff;
    border-radius: 6px;
    justify-content: center;
    align-items: center;
    cursor: default;
    font-size: 10px;
}

.otherMsgFontDelete {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 10px;
    font-weight: bold;
    color: #ff8e8e;
    margin-left: 10px;
    text-align: left;
}
</style>

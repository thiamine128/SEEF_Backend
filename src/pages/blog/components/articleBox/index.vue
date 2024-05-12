<template>
    <div class="box-container" @click="callArticle">


        <img v-if="imgShow" class="img-adjust" src="@/assets/blog/testArticleImg.png" alt="404 not found">

        <div v-if="!imgShow" class="no-img-adjust">
            <div style="font-size: 80px; color: rgba(22, 22, 22, 0.1)"> {{titleShow}} </div>
        </div>

        <div class="msg">
            <div :class="{ titleFont: true }">{{title}}</div>
            <div :class="{ abstractFont: true }">{{abstract}}</div>
            <div class="otherMsg">
                <div :class="{ otherMsgFont: true }">{{"作者："+author}}</div>
                <div :class="{ otherMsgFont: true }">{{" | 发布时间："+postTime}}</div>
                <div :class="{ otherMsgFont: true }">{{" | 点赞："+likes}}</div>
            </div>
        </div>
    </div>
    <hr class="setHr">
</template>

<script>
export default {
    name: "articleBox",
    props:['title', 'abstract', 'author', 'postTime', 'likes', 'articleId'],
    methods:{
        callArticle(){
            this.$router.push(`/blog/article/${this.articleId}`);
        }
    },
    data(){
        return{
            imgShow: false
        }
    },
    computed:{
        titleShow(){
            if (this.title != null){
                // if (this.title.length <= 5) return this.title;
                // else return this.title.slice(0, 5)+'...';
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
}
.img-adjust{
    width: 25%;
    height: 100%;
    display: flex;
}
.no-img-adjust{
    width: 25%;
    height: 100%;
    display: flex;
    background: url('@/assets/blog/article-bg.png');
}
.msg{
    display: flex;
    flex-direction: column;
    justify-content: left;
}
.otherMsg{
    display: flex;
    flex-direction: row;
    justify-content: left;
    margin-top: auto;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 25px;
    margin-left: 5px;
    text-align: left;
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
</style>

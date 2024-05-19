<template>
    <div class="frameSet">
        <div class="personContainer">
            <div class="personInfo">
                <img class="portraitSet" :src="avatar_set" @error="altImg">
                <div class="textSet">
                    <div :class="{ nameFont: true }"> {{name}} </div>
                    <div :class="{ contentFont: true }"> {{description}} </div>
                    <div v-if="replyShow" :class="{ replyFont: true }"> {{replyContent}}  </div>
<!--                    <hr class="setHr">-->

                    <div v-if="articleFrontTextShow" :class="{ contentFont: true }"> 原文章内容： </div>

                    <article-box v-if="articleShow" :title="articleData.title"
                    :author-id="articleData.authorId" :abstract="articleData.abstract"
                    :img-source="articleData.imgSource" :post-time="articleData.postTime"
                    :article-id="articleData.authorId" :likes="articleData.likes" :hr-not-show="true"/>

                    <div v-if="replyToShow || replyToCommentShow"
                         :class="{ contentFont: true }"> 原评论内容： </div>

                    <div v-if="replyToCommentShow" :class="{ replyFont: true }"> {{commentContent}}  </div>

                    <reply-box v-if="replyToShow" :user-id="replyData.userId" :to-id="replyData.toId"
                    :content="replyData.content" style="margin-left: 0" />

                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ArticleBox from "@/pages/blog/components/articleBox/index.vue";
import {getBlogData, getUserData, getCommentData, getReplyData, dateF} from "@/pages/blog/api";
import ReplyBox from "@/pages/blog/components/replyBox/index.vue";

export default {
    name: "spaceBox",
    components: {ReplyBox, ArticleBox},
    props: ['eventBlock'],
    data(){
        return{
            name: '',
            description: '',
            avatar_set: require('@/assets/blog/user.png'),
            replyContent: '',
            articleShow: false,
            replyShow: false,
            replyToCommentShow: false,
            replyToShow: false,
            articleFrontTextShow: false,
            commentContent: '',
            articleData: {
                'title': '',
                'abstract': '',
                'authorId': 0,
                'postTime': '2000-1-1',
                'likes': 0,
                'articleId': 0,
                'imgSource': ''
            },
            replyData: {
                'content': '',
                'userId': 0,
                'toId': 0
            }
        }
    },
    async mounted() {
        const fromUserData = await getUserData(this.eventBlock.subject);
        try{
            this.avatar_set = require(fromUserData.avatar);
        }catch (e){}
        this.name = fromUserData.name;

        if (this.eventBlock.type === 'subscribe') await this.makeSubscribe();
        if (this.eventBlock.type === 'thumb') await this.makeThumb();
        if (this.eventBlock.type === 'favour') await this.makeFavour();
        if (this.eventBlock.type === 'comment') await this.makeComment();
        if (this.eventBlock.type === 'replyComment') await this.makeReplyComment();
        if (this.eventBlock.type === 'reply') await this.makeReply();

    },
    methods:{

        async makeSubscribe(){
            this.description = '关注了你';
        },

        async makeThumb(){
            this.description = '给你的文章点赞';
            await this.pullArticleData(this.eventBlock.object);
            this.articleShow = true;
        },

        async makeFavour(){
            this.description = '收藏了你的文章';
            await this.pullArticleData(this.eventBlock.object);
            this.articleShow = true;
        },

        async makeComment(){
            this.description = '评论了你';
            const comment_data = await getCommentData(this.eventBlock.object);
            this.replyContent = comment_data.content;
            this.replyShow = true;
            this.articleFrontTextShow = true;
            await this.pullArticleData(this.eventBlock.where);
            this.articleShow = true;
        },

        async makeReplyComment(){
            this.description = '回复了你的评论';
            const reply_data = await getReplyData(this.eventBlock.object);
            this.replyContent = reply_data.content;
            this.replyShow = true;
            try{
                const comment_data = await getCommentData(this.eventBlock.where);
                this.commentContent = comment_data.content;
                this.replyToCommentShow = true;
            }catch (e){

            }
        },

        async makeReply(){
            this.description = '回复了你';
            const reply_data = await getReplyData(this.eventBlock.object);
            this.replyContent = reply_data.content;
            this.replyShow = true;
            await this.pullReplyData(this.eventBlock.where);
            this.replyToShow = true;
        },

        altImg(e){
            this.avatar_set = require('@/assets/blog/user.png');
        },

        async pullArticleData(blogId){
            const blog_data = await getBlogData(blogId);
            this.articleData.title = blog_data.title;
            this.articleData.articleId = blogId;
            this.articleData.likes = blog_data.thumbNum;
            this.articleData.postTime = dateF(blog_data.createTime);
            this.articleData.authorId = blog_data.userId;
            this.articleData.abstract = blog_data.context.slice(0, 120);
            this.articleData.imgSource = blog_data.context.slice(0, 500);
        },

        async pullReplyData(replyId){
            const reply_data = await getReplyData(replyId);
            console.log(reply_data);
            this.replyData.toId = reply_data.toId;
            this.replyData.userId = reply_data.userId;
            this.replyData.content = reply_data.content;
        }

    }
}
</script>

<style scoped>
.bottom-style{
    margin-top: 8px;
    display: flex;
    flex-direction: row;
    gap: 8px;
}

.frameSet{
    width: 100%;
    min-height: 60px;
    background-color: rgba(255, 255, 255, 0.9);
    border: 1px solid rgba(155, 155, 155, 0.2);
    display: flex;
}
.personContainer{
    width: 96%;
    min-height: 80%;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.personInfo{
    width: 98%;
    margin-top: 5px;
    min-height: 0;
    display: flex;
    flex-direction: row;
}
.portraitSet{
    height: 50px;
    width: 50px;
    border: 3px solid rgba(205, 205, 205, 0.2);
    border-radius: 100px;
}
.textSet{
    height: 100%;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
    width: 85%;
    min-height: 0;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: left;
    color: gray;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 25px;
    font-weight: bold;
    text-align: left;
}
.replyFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: left;
}
.setHr{
    border-top-color: rgba(44, 44, 44, 0.1);
}
</style>

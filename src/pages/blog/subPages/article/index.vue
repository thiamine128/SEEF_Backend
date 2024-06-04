<template>
    <blogTitle :title="title" :post-time="postTime" :update-time="updateTime"/>
    <div class="content-container">
        <div class="content-left">
            <md-field id="md-hook" :input-content="content"></md-field>

            <div class="comment-head">

                <div style="cursor: default; margin-left: 10px">
                    {{commentNum}} comments
                </div>

                <div style="cursor: pointer; margin-right: 10px; color: #0c82e9" @click="callAddComment">
                    Add Comment
                </div>
            </div>

            <comment-box v-if="commentNum > 0" v-for="item in comments" :content="item.content"
            :post-time="dateF(item.createTime)" :reply-list="item.replies" :comment-id="item.id"
            @callReply="callAddReply" :user-id="item.userId"/>

            <el-pagination v-if="commentNum > 0" class="pagination-style"
                           v-model:current-page="currentPos"
                           ref="bottomPagination"
                           layout="prev, pager, next"
                           @current-change="pageChange()"
                           :total="10*totalPage" />

            <div v-if="commentNum === 0" style="background-color: rgba(255, 255, 255, 0.9); width: 100%; height: 200px; display: flex">
                <div style="margin: auto; font-size: 25px;
                font-weight: bold; color: #8e8e8e"> 目前还没有评论 </div>
            </div>

        </div>

        <div class="content-right">

            <personal-box v-if="personalShow" :create-time="postTime"
            :user-id="authorId" :thumb-num="thumbNum" :is-favor="isFavor" :is-like="isLike"
            :md-title="title" :content="content"
            @callLike="callChangeLike" @callFavor="callChangeFavor" @callMyFavorList="callFavorList"/>
            <recommend height-set="400px" r-title="今日推荐"/>
            <recommend height-set="400px" r-title="相关博客"/>
            <right-pin v-if="catalogShow" r-title="null" container="#md-hook" content-name="catalog"></right-pin>

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
import PersonalBox from "@/pages/blog/components/personalBox/index.vue";
import Catalog from "@/pages/blog/components/catalog/index.vue";
import CommentBox from "@/pages/blog/components/commentBox/index.vue";
import dayjs from "dayjs";
import {callError} from "@/callMessage";
import {getBlogData} from "@/pages/blog/api";
export default {
    name: "article",
    components: {CommentBox, Catalog, PersonalBox, recommend, blogTitle, mdField, rightPin},
    async mounted() {

        try{
            const blog_Data = await getBlogData(this.$route.params.id, false);
            console.log(blog_Data);
            this.content = blog_Data.context;
            this.updateTime = this.dateF(blog_Data.updateTIme);
            this.postTime = this.dateF(blog_Data.createTime);
            this.title = blog_Data.title;
            this.authorId = blog_Data.userId;
            this.thumbNum = blog_Data.thumbNum;
            this.isLike = blog_Data.isLike;
            this.isFavor = blog_Data.isFavor;
            await this.pageChange();

            setTimeout(()=>{
                this.catalogShow = true;
                this.personalShow = true;
            }, 500);
        }catch (e){
            callError(e);
            this.$router.push('/blog/');
        }
    },
    data(){
        return{
            content: '', title: '', postTime: '1919-8-10', updateTime: '1919-8-10',
            totalPage: 1, currentPos: 1, comments: [], commentNum: 0,
            catalogShow: false, authorId: -1, personalShow: false,
            thumbNum: -1, isFavor: false, isLike: false,
            intervalId: ref(null),
        }
    },
    methods:{

        callFavorList(){
            this.$emit('callFloat', '', 7);
        },

        callChangeFavor(){
            this.isFavor = !this.isFavor;
        },

        callChangeLike(){
            if (this.isLike) this.thumbNum--;
            else this.thumbNum++;
            this.isLike = !this.isLike;
        },

        callAddReply(to, commentId){
            this.$emit('callFloat', `输入回复内容`, 3, {
                "commentId": commentId,
                "to": to
            });
        },

        callAddComment(){
            this.$emit('callFloat', `对《${this.title}》发表评论`, 1, {
                "blogId": this.$route.params.id
            });
        },

        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },

        async pageChange(){
            try {
                const response = await this.$http.get(
                    `blog/viewComments?page=${this.currentPos}&pageSize=10&blogId=${this.$route.params.id}`
                );
                console.log(response);
                if (response.status === 200) {
                    this.commentNum = response.data.data.total;
                    this.comments = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 10);
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        }

    }
}
</script>

<style scoped>
.comment-head{
    margin-top: 30px;
    height: 50px;
    font-family: 'rage', sans-serif;
    font-size: 30px;
    background-color: rgba(255, 255, 255, 0.9);
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}
.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 70%;
    display: flex;
    flex-direction: column;
}
.content-right{
    width: 28%;
}
.pagination-style{
    background-color: rgba(255, 255, 255, 0.9);
    justify-content: right;
}
</style>

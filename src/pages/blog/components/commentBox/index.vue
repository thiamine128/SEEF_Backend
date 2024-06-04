<template>
    <div class="frameSet">
        <div class="personContainer">

            <div class="personInfo">
                <div>
                    <img @error="imgError" @click="callPersonalFunc" class="portraitSet" :src="comment_avatar" alt="404 not found">
                    <div :class="{ nameFont: true }" @click="callPersonalFunc"> {{author}} </div>
                </div>

                <div class="textSet">
                    <div :class="{ contentFont: true }" @click="addReply()"> {{content}} </div>
                    <div :class="{ dateFont: true }"> 发布于{{postTime}}

                        <img alt="404" src="@/assets/blog/cancel.png" v-if="isAdmin || nowLogin === userId"
                             style="width: 15px; margin-left: 5px; cursor: pointer" @click="deleteComment">

                    </div>

                </div>
            </div>

            <div class="bottom-style">

                <reply-box v-for="item in replyList" :user-id="item.userId" :to-id="item.toId"
                :content="item.content" :reply-id="item.id"
                @click="addReply(item.userId)"/>

            </div>

        </div>
    </div>
</template>

<script>
import ReplyBox from "@/pages/blog/components/replyBox/index.vue";
import {deleteBlog, deleteComment, getUserData} from "@/pages/blog/api";
import store from "@/store/store";
import {callError} from "@/callMessage";
import {ref} from "vue";

export default {
    name: "commentBox",
    components: {ReplyBox},
    props: ['userId', 'commentId', 'content', 'postTime', 'replyList'],
    computed:{
        isAdmin(){
            return store.getters.getData.role === 'admin';
        },
    },
    data(){
        return{
            comment_avatar: require('@/assets/blog/user.png'),
            author: '',
            nowLogin: -1
        }
    },
    methods:{

        imgError(){
            this.comment_avatar = require('@/assets/blog/user.png');
        },

        callPersonalFunc(){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${this.userId}`);
            }, 100);
        },

        addReply(to = -1){
            this.$emit('callReply', to, this.commentId);
        },

        async pullPersonalData(id){
            const personal_data = await getUserData(id);
            this.author = personal_data.name;
            try{
                personal_data.avatar = personal_data.avatar + '?t=' + new Date().getTime();
                this.comment_avatar = ref(personal_data.avatar);
            }catch (e){}
        },

        async deleteComment(){
            await deleteComment(this.commentId);
            setTimeout(()=>{
                location.reload();
            }, 1000);
        },

    },
    mounted() {
        this.pullPersonalData(this.userId);
        this.nowLogin = store.getters.getData.id;
    },
}
</script>

<style scoped>

.portrait-name{
    display: flex;
    flex-direction: column;
}

.bottom-style{
    display: flex;
    flex-direction: column;
    width: 100%;
}

.frameSet{
    width: 100%;
    min-height: 110px;
    background-color: rgba(255, 255, 255, 0.9);
    border: 1px solid rgba(155, 155, 155, 0.2);
    display: flex;
}

.personContainer{
    margin-top: 5px;
    margin-left: 5px;
    width: 95%;
    min-height: 102px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.personInfo{
    width: 100%;
    height: 100px;
    display: flex;
    flex-direction: row;
}

.portraitSet{
    height: 80px;
    width: 80px;
    border: 2px solid rgba(205, 205, 205, 0.2);
}
.textSet{
    height: 100%;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    padding: 10px;
    text-align: left;
    width: 680px;
    height: 70px;
    background-color: rgba(22, 22, 22, 0.07);
    overflow-x: auto;
    cursor: pointer;
}
.dateFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: right;
    color: gray;
    width: 700px;
    height: 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: right;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    margin-top: -5px;
    color: #0066ff;
    cursor: pointer;
}
</style>

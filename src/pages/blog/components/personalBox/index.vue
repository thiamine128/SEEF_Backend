<template>
    <div class="frameSet">
        <div class="personContainer">
            <div class="personInfo">
                <img class="portraitSet" :src="avatar" alt="404 not found" @click="callPersonalFunc(userId)" @error="altImg">
                <div class="textSet">
                    <div :class="{ nameFont: true }" @click="callPersonalFunc(userId)"> {{name}} </div>
                    <div :class="{ contentFont: true }"> {{email}} </div>
                    <div :class="{ contentFont: true }"> 发布于 {{createTime}} </div>
                </div>
            </div>
            <div class="bottom-style">

                <personal-button v-if="!isLike" :img-path="require('@/assets/blog/like.png')" :content="thumbNum" @click="likeArticle" />
                <personal-button v-if="isLike" :img-path="require('@/assets/blog_change/like.png')" :content="thumbNum" @click="likeArticle"/>

                <personal-button v-if="!isFavor" :img-path="require('@/assets/blog/collect.png')" content="收藏" @click="favorArticle"/>
                <personal-button v-if="isFavor" :img-path="require('@/assets/blog_change/collect.png')" content="收藏" @click="favorArticle"/>

                <personal-button v-if="!isSubscribe" :img-path="require('@/assets/blog/subscribe.png')" content="关注" @click="subscribeAuthor" />
                <personal-button v-if="isSubscribe" :img-path="require('@/assets/blog_change/subscribe.png')" content="关注" @click="subscribeAuthor" />

                <personal-button :img-path="require('@/assets/blog/edit.png')" content="编辑" @click="editArticle"/>

            </div>
        </div>
    </div>
</template>

<script>
import personalButton from "@/pages/blog/components/personalButton/index.vue";
import {callError, callInfo} from "@/callMessage";
import {favor_func, getUserData, like_func, subscribe_func, unSubscribe_func} from "@/pages/blog/api";
import store from "@/store/store";
import {ref} from "vue";
export default {
    name: "personalBox",
    components:{personalButton},
    props: [
        'userId', 'createTime', 'isFavor', 'isLike', 'isSubscribe', 'thumbNum',
        'mdTitle', 'content'
    ],
    computed:{
        isAdmin(){
            return store.getters.getData.role === 'admin';
        },
    },
    methods:{

        altImg(e){
            this.avatar = require('@/assets/blog/user.png');
        },

        callPersonalFunc(userId){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${userId}`);
            }, 100);
        },

        editArticle(){

            if (this.userId !== store.getters.getData.id ){
                callInfo('不能编辑别人的文章');
                return;
            }

            store.commit('setContent', {
                "title": this.mdTitle,
                "content": this.content
            });

            const blogId = this.$route.params.id;
            this.$router.push(`/blog/editor/${blogId}`);

        },

        likeArticle(){
            const blogId = this.$route.params.id;
            if (!this.isLike){
                //对文章点赞
                like_func(blogId, true);
            }else{
                //取消点赞
                like_func(blogId, false);
            }
            this.$emit('callLike');
        },

        favorArticle(){
            const blogId = this.$route.params.id;
            if (!this.isFavor){
                //收藏文章
                this.$emit('callMyFavorList');
                //favor_func(blogId, true);
            }else{
                //取消收藏
                favor_func(blogId, false);
                this.$emit('callFavor');
            }
        },

        subscribeAuthor(){

            if (store.getters.getData.id === this.userId){
                callInfo('不可以关注自己');
                return;
            }

            if (!this.isSubscribe){
                //关注
                subscribe_func(this.userId);
            }else{
                //取消关注
                unSubscribe_func(this.userId);
            }
            this.isSubscribe = !this.isSubscribe;
        },

        async pullPersonalData(){
            const personal_data = await getUserData(this.userId, false);
            try{
                personal_data.avatar = personal_data.avatar + '?t=' + new Date().getTime();
                this.avatar = ref(personal_data.avatar);
            }catch (error){}
            this.email = personal_data.email;
            this.name = personal_data.name;
            this.isSubscribe = personal_data.subscribed;
        },
    },
    mounted() {
        this.pullPersonalData();
    },
    data(){
        return{
            avatar: require('@/assets/blog/user.png'),
            name: '',
            email: '',
            isSubscribe: false
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
    height: 140px;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 5px;
    border: 1px solid rgba(155, 155, 155, 0.7);
    display: flex;
    margin-bottom: 10px;
}
.personContainer{
    width: 88%;
    height: 80%;
    margin: auto;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.personInfo{
    width: 100%;
    height: 80px;
    display: flex;
    flex-direction: row;
}
.portraitSet{
    height: 80px;
    width: 80px;
    border: 3px solid rgba(205, 205, 205, 0.2);
}
.textSet{
    height: 100%;
    overflow-x: auto;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
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
    cursor: pointer;
}
</style>

<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
                <a class="linkStyle">
                    <div :class="{ moreFont: true }"> SEEF </div>
                </a>
            </div>
            <hr>
            <div :style="listStyle">

                <recommend-button v-if="rTitle === '今日推荐'"
                v-for="item in recommendList" :r-name="item.name" @click="callSearchFunc(item.name)"/>

                <recommend-button v-if="rTitle === '关注列表'"
                v-for="item in subscribed" :r-name="item.name" @click="callPersonalFunc(item.id)"/>

                <recommend-button v-if="rTitle === '热门博主'" :hot-set="1"
                v-for="item in users" :r-name="item.name" :user-id="item.id" @click="callPersonalFunc(item.id)"/>

                <recommend-button v-if="rTitle === '相关博客'" :bold-set="true" :abstract="item.abstract" :tag-list="item.tags"
                :create-time="item.createTime" :author="item.author"
                v-for="item in blogs" :r-name="item.name" @click="callBlogFunc(item.id)"/>

            </div>
        </div>
    </div>
</template>

<script>
import recommendButton from "@/pages/blog/components/recommendButton/index.vue";
import {dateF, getBlogData, getSubscribed, getUserData} from "@/pages/blog/api";
export default {
    name: "recommend",
    props: ['heightSet', 'rTitle'],
    components:{recommendButton},
    methods:{

        callSearchFunc(content){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/find/${content}`);
            }, 100);
        },

        callPersonalFunc(userId){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${userId}`);
            }, 100);
        },

        callBlogFunc(blogId){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/article/${blogId}`);
            }, 100);
        }

    },
    data(){
        return{

            frameStyle:{
                width: '100%',
                height: '1px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                borderRadius: '5px',
                border: '1px solid rgba(155, 155, 155, 0.7)',
                display: 'flex',
                marginBottom: '10px'
            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1'
            },

            recommendList: [
                {name: 'linux'}, {name: 'CNN'},
                {name: '高等代数'}, {name: 'ChatGPT'},
                {name: '图论'}, {name: '软件工程'},
                {name: 'python'}, {name: 'Java'},
                {name: '数学分析'}, {name: 'SQL'},
                {name: '操作系统'}
            ],

            blogs: [], //相关博客
            users: [], //热门博主
            subscribed: [] //关注列表

        }
    },
    async mounted() {
        this.frameStyle.height = this.heightSet;

        if (this.rTitle === '关注列表'){
            try{
                const subscribed_init = await getSubscribed();
                for (let sb of subscribed_init){
                    const userData = await getUserData(sb);
                    this.subscribed.push({'name': userData.name, 'id': sb});
                }
            }catch(error){}
        }

        if (this.rTitle === '热门博主'){
            try{
                const users_init = [22371182, 22371183, 22371184, 22371185, 22371186, 22371187, 22371188];
                for (let sb of users_init){
                    const userData = await getUserData(sb);
                    this.users.push({'name': userData.name, 'id': sb});
                }
            }catch (error){}
        }

        if (this.rTitle === '相关博客'){
            try{

                const response = await this.$http.get(`blog/similar?blogId=${this.$route.params.id}&count=10&previewLength=30`);
                for (let b of response.data.data){
                    const userData = await getUserData(b.userId);
                    this.blogs.push({'name': b.title, 'id': b.id, 'abstract': b.preview, 'tags': b.tags, 'author': userData.name, 'createTime': dateF(b.createTime)});
                }

            }catch (error){}
        }

    }
}
</script>

<style scoped>
.container{
    width: 88%;
    height: 90%;
    margin: auto;
    display: flex;
    flex-direction: column;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 17px;
    text-align: center;
    color: #0c82e9;
}
.moreFont{
    font-family: 'rage', sans-serif;
    font-size: 13px;
    text-align: center;
    font-weight: bold;
    color: #d7d7d7;
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 22px;
}
.linkStyle{
    text-decoration: none;
    color: #000;
}
hr{
    width: 100%;
}

</style>

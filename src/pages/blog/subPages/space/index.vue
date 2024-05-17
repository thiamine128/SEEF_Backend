<template>
    <div class="content-container">
        <div class="content-left">
<!--            <article-list height-set="1000px" r-title="动态 Wonderful Space"></article-list>-->

            <div v-if="messageList.length === 0" style="width: 100%; height: 1000px; display: flex; background-color: rgba(255, 255, 255, 0.9);">
                <div style="margin: auto; font-weight: bold; font-size: 32px">
                    当前还没有动态信息
                </div>
            </div>



        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement.png" style="width: 100%;">
            <recommend height-set="400px" r-title="关注列表"/>
        </div>
    </div>
</template>

<script>
import articleList from "@/pages/blog/components/articleList/index.vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import SpaceBox from "@/pages/blog/components/spaceBox/index.vue";
import {callError} from "@/callMessage";
export default {
    name: "space",
    components: {SpaceBox, recommend, articleList},
    data(){
        return{
            messageList: []
        }
    },
    mounted() {
        this.pullUnreadEvent();
    },
    methods:{
        async pullUnreadEvent(){
            try{
                const response = await this.$http.get('event/pull');
                console.log(response);
            }catch(error){
                callError(error);
            }
        }
    }
}
</script>

<style scoped>
.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 68%;
}
.content-right{
    width: 30%;
}
</style>

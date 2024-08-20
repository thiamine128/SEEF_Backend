<template>
    <div class="content-container">
        <div class="content-left">
<!--            <article-list height-set="1000px" r-title="动态 Wonderful Space"></article-list>-->

            <div style="width: 100%; min-height: 700px; display: flex; background-color: rgba(255, 255, 255, 0.9); flex-direction: column">

                <div v-if="eventList.length === 0" style="margin: auto; font-weight: bold; font-size: 32px; color: rgba(15,15,15,0.3)">
                    当前还没有动态信息
                </div>

                <space-box v-for="item in eventList" :event-block="item" />
            </div>

        </div>
        <div class="content-right">
            <img alt="404" src="@/assets/blog/advertisement4.png" style="width: 100%;">
            <recommend height-set="400px" r-title="关注列表"/>
        </div>
    </div>
</template>

<script>
import articleList from "@/pages/blog/components/articleList/index.vue";
import recommend from "@/pages/blog/components/recommend/index.vue";
import SpaceBox from "@/pages/blog/components/spaceBox/index.vue";
import {callError} from "@/callMessage";
import store from "@/store/store";
export default {
    name: "space",
    components: {SpaceBox, recommend, articleList},
    data(){
        return{
            eventList: []
        }
    },
    mounted() {
        document.documentElement.scrollTop = 0;
        this.pullUnreadEvent();
    },
    methods:{
        async pullUnreadEvent(){
            try{
                const response = await this.$http.get('event/pull');
                const newEventList = response.data.data.concat();
                for (let e0 of newEventList){
                    store.commit('addEvent', e0);
                }
                this.eventList = store.getters.getEventList.concat();
                //console.log(this.eventList);
            }catch(error){
                //callError(error);
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

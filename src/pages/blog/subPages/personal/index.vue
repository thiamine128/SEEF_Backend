<template>
    <div class="content-container">
        <div class="personal-container">
            <img class="portraitSet" :src="my_avatar" @error="altImg" @click="uploadUserAvatar">
            <div class="textSet">
                <div class="nameSet">
                    <div :class="{ nameFont: true }"> {{my_name}} </div>

                    <personal-button v-if="noPermit" class="subscribeSet"
                                     :img-path="require('@/assets/blog/subscribe.png')"
                                     :content="buttonName" @click="subscribeUser"/>

                    <personal-button v-if="!noPermit" class="subscribeSet"
                                     :img-path="require('@/assets/blog/cancel.png')"
                                     content="登出" @click="logoutFunc"/>

                </div>
                <div :class="{ contentFont: true }"> {{email}} </div>
                <div :class="{ contentFont: true }"> 注册于 {{dateF(createTime)}} </div>
            </div>
        </div>
        <div class="navStyle">
            <div class="select" @click="isEdit = !noPermit" v-click-outside="handleOutsideProfile">
                <div v-if="!isEdit" style="margin-left: 30px; text-align: left; width: 100%; height: 100%">
                    {{introduction}}
                </div>

                <textarea v-if="isEdit && !noPermit" v-model="introduction"
                  style="padding-left: 30px; padding-right: 30px; text-align: left; color: #8e8e8e;
                  width: 95%; height: 100%; border: none; outline: none; background-color: rgba(0, 0, 0, 0)"/>

            </div>

            <div class="show">
                <div class="like" >
                    <div :class="{ contentFont: true }"> 热度 </div>
                    <div :class="{ contentFont: true }"> {{ popularity }}</div>
                </div>
                <div class="like" >
                    <div :class="{ contentFont: true }"> 关注 </div>
                    <div :class="{ contentFont: true }"> {{subscribes}} </div>
                </div>
                <div class="like" >
                    <div :class="{ contentFont: true }"> 文章 </div>
                    <div :class="{ contentFont: true }"> {{articles}} </div>
                </div>
            </div>

        </div>

        <div class="infoStyle">

            <personal-card  class="cardSet40" height-set="500px"  r-title="发布文章" :show-change="!noPermit"
            @addSpace="callSpaceCreate"/>

            <div ref="pieChart" style="width: 60%; height: 400px; margin: auto;"></div>


        </div>

        <div v-if="!noPermit" class="infoStyle" style="height: 870px">

            <div style="display: flex;flex-direction: column; width: 40%">
                <personal-card  style="width: 100%; border: none" height-set="435px" r-title="收藏列表" :show-change="!noPermit"
                @addCategory="callCategoryCreate"/>
                <personal-card  style="width: 100%; border: none" height-set="435px" r-title="关注列表"/>
            </div>

            <article-list style="width: 60%; border-radius: 0; border: none"
            height-set="870px" r-title="热门文章 Blogs"
            :list-set="hotspotList" select="article" :total-page="hotspotPageSize"
            @page-change="pullArticles"></article-list>

        </div>

        <div v-if="noPermit" class="infoStyle" style="height: 870px">
            <article-list style="width: 100%; border-radius: 0; border: none"
                          height-set="870px" r-title="热门文章 Blogs"
                          :list-set="hotspotList" select="article" :total-page="hotspotPageSize"
                          @page-change="pullArticles"></article-list>
        </div>

    </div>
</template>

<script>

import personalButton from "@/pages/blog/components/personalButton/index.vue";
import PersonalCard from "@/pages/blog/components/personalCard/index.vue";
import * as echarts from 'echarts';
import dayjs from "dayjs";
import {callError, callInfo} from "@/callMessage";
import store from "@/store/store";
import {getUserData, subscribe_func, unSubscribe_func, uploadAvatar} from "@/pages/blog/api";
import {ref} from "vue";
import articleList from "@/pages/blog/components/articleList/index.vue";

export default {

    name: "personal",
    components: {articleList, PersonalCard, personalButton},
    data(){
        return{
            my_avatar: require('@/assets/blog/user.png'),
            my_name: '',
            createTime: 0,
            email: '', //邮箱，现在还没有返回
            popularity: 0, //热度
            subscribes: 0, //关注（关注别人）的总数
            articles: 0, //发布的文章总数
            introduction: '该用户没有留下简介......',
            past_introduction: '该用户没有留下简介......',
            isSubscribe: false, //是否关注过
            isEdit: false,

            hotspotPageSize: 1,
            hotspotList: []
        }
    },
    computed:{

        noPermit(){
            return store.getters.getData.id != this.$route.params.userId;
        },

        buttonName(){
            return this.isSubscribe ? '取消关注' : '关注';
        }

    },
    methods:{

        logoutFunc(){
            store.dispatch('logout');
        },

        async pullArticles(pageNum){
            try{
                const response = await this.$http.get(
                    `blog/viewBlogs?page=${pageNum}&pageSize=6&previewLength=500&userId=${this.$route.params.userId}&orderBy=popularity&sort=desc`
                );
                console.log(response);
                if (response.status === 200) {
                    this.hotspotList = response.data.data.records;
                    this.hotspotPageSize = Math.ceil(response.data.data.total / 6);
                    this.articles = response.data.data.total;
                    this.popularity = Math.floor(this.hotspotList[0].popularity);
                } else callError('网络错误');
            }catch (error){}
        },

        callSpaceCreate(){
            this.$emit('callFloat', '', 4);
        },

        callCategoryCreate(pageNum){
            this.$emit('callFloat', '', 6);
        },

        async handleOutsideProfile(event){
            if (this.isEdit){
                this.isEdit = false;
                if (this.introduction.length > 150){
                    callInfo('简介过长');
                    this.introduction = '该用户没有留下简介......';
                }else if (this.introduction.length === 0){
                    this.introduction = '该用户没有留下简介......';
                }else if (this.introduction !== this.past_introduction){
                    await this.$http.post('user/update', {
                        "profile": this.introduction
                    });
                    callInfo('个人简介已修改');
                    this.past_introduction = this.introduction;
                }
            }
        },

        altImg(e){
            this.my_avatar = require('@/assets/blog/user.png');
        },

        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },

        async pullPersonalData(){
            const personal_data = await getUserData(this.$route.params.userId, false);
            console.log('personal_data:');
            console.log(personal_data);
            try{
                personal_data.avatar = personal_data.avatar + '?t=' + new Date().getTime();
                this.my_avatar = ref(personal_data.avatar);
            }catch (e){
                console.error(e);
            }
            this.my_name = personal_data.name;
            this.createTime = personal_data.createTime;
            this.email = personal_data.email;
            this.isSubscribe = personal_data.subscribed;
            this.subscribes = personal_data.subscribers;
            if (personal_data.profile){
                this.introduction = personal_data.profile;
                this.past_introduction = this.introduction;
            }
        },

        subscribeUser(){
            if (this.isSubscribe) unSubscribe_func(this.$route.params.userId);
            else subscribe_func(this.$route.params.userId);
            this.isSubscribe = !this.isSubscribe;
        },

        uploadUserAvatar(){
            if (!this.noPermit) uploadAvatar();
            else callInfo('只允许修改自己的头像');
        }

    },

    async mounted() {

        try {
            await this.pullPersonalData();
            await this.pullArticles(1);

            const myChart = echarts.init(this.$refs.pieChart);
            const pieData = [
                // { value: 1, name: 'C++' },
                // { value: 2, name: 'linux' },
            ];

            let sumT = 0, chartInput = 0, cnt = 0;
            const tagsMap = new Map();
            for (let b of this.hotspotList) {
                if (b.tags) {
                    for (let t of b.tags) {
                        if ((t + '').length > 0) {
                            if (tagsMap[t]) tagsMap[t]++;
                            else tagsMap[t] = 1;
                            sumT++;
                        }

                    }
                }
            }

            for (let prop in tagsMap) {
                cnt++;
                if (cnt <= 4) {
                    pieData.push({value: tagsMap[prop], name: prop});
                    chartInput += tagsMap[prop];
                }
            }

            if (sumT - chartInput > 0) pieData.push({value: sumT - chartInput, name: '其他'});

            // 配置项
            const option = {
                title: {
                    text: '文章贡献数据统计', // 设置标题文本
                    left: 'center', // 标题位置
                    textStyle: {
                        color: '#333', // 标题颜色
                        fontSize: 18, // 标题字体大小
                    },
                },
                series: [
                    {
                        type: 'pie',
                        radius: '55%',
                        data: pieData,
                    },
                ],
                legend: {
                    show: true,
                    bottom: '0',
                }
            };
            myChart.setOption(option);
        }catch (e) {
            callError(e);
            this.$router.push('/blog/');
        }
    },
}
</script>

<style scoped>
.like{
    height: 100%;
    width: 50px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-right: 30px;
}
.show{
    width: 400px;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: right;
}
.select{
    width: 800px;
    height: 100%;
}
.cardSet40{
    width: 40%;
}
.cardSet60{
    width: 60%;
}
.nameSet{
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
}
.infoStyle{
    height: 500px;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.9);
    display: flex;
    flex-direction: row;
}
.navStyle{
    height: 70px;
    width: 100%;
    background-color: rgba(245, 245, 245, 0.9);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.content-container{
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 20px;
    width: 1200px;
}
.personal-container{
    width: 100%;
    height: 200px;
    background-color: rgba(255, 255, 255, 0.9);
    background-size: cover;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.portraitSet{
    height: 180px;
    width: 180px;
    border: 3px solid rgba(205, 205, 205, 0.2);
    border-radius: 100px;
    margin-left: 10px;
    cursor: pointer;
}
.textSet{
    height: 100%;
    overflow-x: auto;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
    padding-top: 80px;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 20px;
    text-align: left;
    color: gray;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 40px;
    font-weight: bold;
    text-align: left;
}
</style>

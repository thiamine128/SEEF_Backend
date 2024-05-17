<template>
    <div class="content-container">
        <div class="personal-container">
            <img class="portraitSet" :src="my_avatar" @error="altImg">
            <div class="textSet">
                <div class="nameSet">
                    <div :class="{ nameFont: true }"> {{my_name}} </div>
                    <personal-button v-if="showSubscribe" class="subscribeSet"
                     :img-path="require('@/assets/blog/subscribe.png')" content="关注" >
                    </personal-button>
                </div>
                <div :class="{ contentFont: true }"> {{email}} </div>
                <div :class="{ contentFont: true }"> 注册于 {{dateF(createTime)}} </div>
            </div>
        </div>
        <div class="navStyle">
            <div class="select">
                {{introduction}}
            </div>
            <div class="show">
                <div class="like" >
                    <div :class="{ contentFont: true }"> 点赞 </div>
                    <div :class="{ contentFont: true }"> {{likes}} </div>
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

            <div ref="pieChart" style="width: 40%; height: 400px; margin: auto;"></div>
            <personal-card  class="cardSet60" height-set="500px" r-title="发布文章"/>

        </div>

        <div class="infoStyle" style="height: 400px">

            <personal-card  style="width: 50%" height-set="400px" r-title="关注列表"/>
            <personal-card  style="width: 50%" height-set="400px" r-title="收藏列表"/>

        </div>

    </div>
</template>

<script>

import personalButton from "@/pages/blog/components/personalButton/index.vue";
import PersonalCard from "@/pages/blog/components/personalCard/index.vue";
import * as echarts from 'echarts';
import dayjs from "dayjs";
import {callError} from "@/callMessage";
import store from "@/store/store";

export default {

    name: "personal",
    components: {PersonalCard, personalButton},
    data(){
        return{
            my_avatar: require('@/assets/blog/user.png'),
            my_name: '',
            createTime: 0,
            email: '', //邮箱，现在还没有返回
            likes: 0, //博客获得的总点赞数
            subscribes: 0, //关注（关注别人）的总数
            articles: 0, //发布的文章总数
            articleList: [], //发表的文章具体信息，要分页
            subscribeList: [], //关注博主的具体信息，要分页，且要有收藏夹
            collectList: [], //收藏的文章的具体信息，要分页，且要有收藏夹
            introduction: '该用户没有留下简介......'
        }
    },
    computed:{
        showSubscribe(){
            return store.getters.getData.id != this.$route.params.userId;
        }
    },
    methods:{

        altImg(e){
            this.my_avatar = require('@/assets/blog/user.png');
        },

        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },

        async pullPersonalData(){
            const response = await this.$http.get(`/user?userId=${this.$route.params.userId}`);
            console.log(response);
            const personal_data = response.data.data;
            this.my_avatar = personal_data.avatar;
            this.my_name = personal_data.name;
            this.createTime = personal_data.createTime;
            if (personal_data.profile) this.introduction = personal_data.profile;
        },

        async subscribeUser(){
            try{
                const response = await this.$http.post(`event/subscribe?user=${this.$route.params.userId}`);

            }catch(error){
                callError(error);
            }
        }

    },

    mounted() {

        this.pullPersonalData();

        const myChart = echarts.init(this.$refs.pieChart);
        const pieData = [
            { value: 1, name: '开发' },
            { value: 2, name: '维护' },
            { value: 3, name: '测试' },
            { value: 4, name: 'BUG修复' },
            { value: 5, name: '其他' },
        ];

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
                show: true, // 显示图例
                bottom: '0', //图例位置
            }
        };
        myChart.setOption(option);
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
    margin-left: 30px;
    text-align: left;
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

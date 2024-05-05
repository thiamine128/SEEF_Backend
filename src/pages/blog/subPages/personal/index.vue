<template>
    <div class="content-container">
        <div class="personal-container">
            <img @click="uploadImage" class="portraitSet" src="@/assets/blog/testPortrait.jpg" alt="404 not found">
            <div class="textSet">
                <div class="nameSet">
                    <div :class="{ nameFont: true }"> 阮阳栋 </div>
                    <personal-button class="subscribeSet"
                     :img-path="require('@/assets/blog/subscribe.png')" content="关注" >
                    </personal-button>
                </div>
                <div :class="{ contentFont: true }"> 1164754246@qq.com </div>
                <div :class="{ contentFont: true }"> 注册于2024年4月23日 </div>
            </div>
        </div>
        <div class="navStyle">
            <div class="select">
                操作系统是计算机系统中软件与硬件联系的纽带，课程内容丰富，既包含操作系统的基础理论，又涉及实际操作系统的设计与实现。操作系统实验设计是操作系统课程实践环节的集中表现，旨在巩固学生理论课学习的概念和原理，同时培养学生的工程实践能力。
            </div>
            <div class="show">
                <div class="like" >
                    <div :class="{ contentFont: true }"> 点赞 </div>
                    <div :class="{ contentFont: true }"> 0 </div>
                </div>
                <div class="like" >
                    <div :class="{ contentFont: true }"> 关注 </div>
                    <div :class="{ contentFont: true }"> 0 </div>
                </div>
                <div class="like" >
                    <div :class="{ contentFont: true }"> 文章 </div>
                    <div :class="{ contentFont: true }"> 0 </div>
                </div>
            </div>

        </div>
        <div class="infoStyle">

            <div ref="pieChart" style="width: 40%; height: 400px; margin: auto;"></div>
            <personal-card  class="cardSet60" height-set="500px"/>

        </div>
    </div>
</template>

<script>

import personalButton from "@/pages/blog/components/personalButton/index.vue";
import PersonalCard from "@/pages/blog/components/personalCard/index.vue";
import * as echarts from 'echarts';

export default {

    name: "personal",
    components: {PersonalCard, personalButton},

    methods:{

        uploadImage(){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ".jpg, .png";
            fileInput.addEventListener("change", this.handleImage);
            fileInput.click();
        },

        async handleImage(event){
            const file = event.target.files[0];
            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await this.$http.post('/user/requestUploadAvatar', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log(response.data);
            } catch (error) {
                console.error(error);
            }
        }
    },

    mounted() {
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

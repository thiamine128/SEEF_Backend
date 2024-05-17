<template>
    <div class="frameSet">
        <div class="personContainer">
            <div class="personInfo">
                <img class="portraitSet" :src="avatar_set" @error="altImg">
                <div class="textSet">
                    <div :class="{ nameFont: true }"> {{name}} </div>
                    <div :class="{ contentFont: true }"> {{description}} </div>
<!--                    <div :class="{ contentFont: true }"> 于2024年4月23日回复了你 </div>-->
<!--                    <div :class="{ replyFont: true }"> 哈哈哈  </div>-->
                    <hr class="setHr">

                    <article-box title="米浴说的道理"/>
<!--                    <article-box title="米浴说的道理"/>-->

                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ArticleBox from "@/pages/blog/components/articleBox/index.vue";

export default {
    name: "spaceBox",
    components: {ArticleBox},
    props: ['content'],
    data(){
        return{
            'name': '',
            'description': '',
            'avatar_set': require('@/assets/blog/user.png')
        }
    },
    methods:{
        altImg(e){
            this.my_avatar = require('@/assets/blog/user.png');
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
    min-height: 90px;
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

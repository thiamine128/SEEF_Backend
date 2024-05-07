<template>
    <div class="frameSet">
        <div class="personContainer">
            <div class="personInfo">
                <img class="portraitSet" src="@/assets/blog/testPortrait.jpg" alt="404 not found">
                <div class="textSet">
                    <div :class="{ nameFont: true }"> 阮阳栋 </div>
                    <div :class="{ contentFont: true }"> 发布了文章 </div>
<!--                    <div :class="{ contentFont: true }"> 于2024年4月23日回复了你 </div>-->
<!--                    <div :class="{ replyFont: true }">  </div>-->
                    <article-box title="米浴说的道理"/>

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
    props: ['content']
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
    min-height: 140px;
    background-color: rgba(255, 255, 255, 0.9);
    border: 1px solid rgba(155, 155, 155, 0.2);
    display: flex;
    margin-bottom: 10px;
}
.personContainer{
    width: 96%;
    min-height: 80%;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.personInfo{
    width: 100%;
    min-height: 80px;
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
</style>

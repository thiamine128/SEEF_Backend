<template>
    <div class="frameSet">
        <div class="personContainer">
            <div class="personInfo">
                <img class="portraitSet" src="@/assets/blog/testPortrait.jpg" alt="404 not found">
                <div class="textSet">
                    <div :class="{ nameFont: true }"> 阮阳栋 </div>
                    <div :class="{ contentFont: true }"> 1164754246@qq.com </div>
                    <div :class="{ contentFont: true }"> 发布于2024年4月23日 </div>
                </div>
            </div>
            <div class="bottom-style">
                <personal-button :img-path="require('@/assets/blog/like.png')" content="0" >
                </personal-button>
                <personal-button :img-path="require('@/assets/blog/collect.png')" content="收藏" >
                </personal-button>
                <personal-button :img-path="require('@/assets/blog/subscribe.png')" content="关注" >
                </personal-button>
                <personal-button :img-path="require('@/assets/blog/edit.png')" content="编辑" >
                </personal-button>
            </div>
        </div>
    </div>
</template>

<script>
import personalButton from "@/pages/blog/components/personalButton/index.vue";
export default {
    name: "personalBox",
    components:{personalButton}
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
}
</style>

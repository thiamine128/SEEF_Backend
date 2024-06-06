<template>
    <div class="title-container"
         @mouseenter="hover = true"
         @mouseleave="hover = false">

        <div class="blog-block">
            <div class="tag-set">
                <div :class="{titleFont: !boldSet, titleFontBold: boldSet, titleFontEX: hotSet}">
                    {{rName}}
                </div>
                <div v-if="boldSet" v-for="item in tagList" class="tagSet">
                    {{item}}
                </div>
            </div>
            <div v-if="boldSet" style="font-size: 10px; color: #515151"> {{abstract.replaceAll('#', '')+"......"}} </div>

            <div v-if="boldSet" style="font-size: 8px; color: #8e8e8e">
                作者：{{author}} | 发布时间：{{createTime}}
            </div>

            <div v-if="hotSet" style="font-size: 8px; color: #8e8e8e">
                {{profile}}
            </div>

        </div>

        <div :class="{ moreFont: true }"> > </div>
    </div>
</template>

<script>
import {getUserData} from "@/pages/blog/api";

export default {
    name: "recommendButton",
    props: ['rName', 'boldSet', 'abstract', 'tagList', 'author', 'createTime', 'hotSet', 'userId'],
    data(){
        return{
            hover: false,
            profile: '博主没有留下个人简介......'
        }
    },
    async mounted() {
        if (this.userId){
            const userData = await getUserData(this.userId);
            if (userData.profile) this.profile = userData.profile.slice(0, 20)+'......';
        }
    }
}
</script>

<style scoped>
.tagSet{
    padding-left: 2px;
    padding-right: 2px;
    margin-left: 2px;
    height: 60%;
    min-width: 0;
    display: flex;
    flex-direction: row;
    background-color: #def2ff;
    border-radius: 3px;
    justify-content: center;
    align-items: center;
    cursor: default;
    font-size: 8px;
}
.tag-set{
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
}
.blog-block{
    display: flex;
    flex-direction: column;
    text-align: left;
    min-height: 0;
}
.titleFontEX {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 13px;
    text-align: left;
    font-weight: bold;
    color: #383838;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 16px;
    text-align: left;
}
.titleFontBold {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    text-align: left;
    font-weight: bold;
    color: #353535;
}
.moreFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 16px;
    text-align: center;
    color: rgba(55, 55, 55, 0.4);
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0);
    transition: background-color 0.5s ease;
    min-height: 0;
}
.title-container:hover{
    background-color: rgba(33, 33, 33, 0.1);
}
</style>

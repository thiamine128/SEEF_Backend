<template>
    <div class="box-container" @click="callArticles">
        <div class="titleSet">

            <img style="width: 35px" alt="404" :src="srcImg">

            <div class="titleAbs">
                <div :class="{ titleFont: true }">{{title}}</div>
                <div :class="{ abstractFont: true }">{{abstract}}</div>
            </div>

        </div>
        <div :class="{ moreSet: true }"> > </div>
    </div>
    <hr class="setHr">
</template>

<script>
import {provide, ref} from "vue";

export default {
    name: "sectionBox",
    methods: {
        callArticles(){
            if (!this.editorSet) this.$router.push(`/blog/articles/${this.topicId}`);
            else this.$emit('modifyClick');
        }
    },
    props: ['topicId', 'title', 'abstract', 'editorSet'],
    computed:{
        srcImg(){
            if (this.title == null || this.title.length < 5){
                return require('@/assets/blog/black-book.png');
            }else if (this.title.length < 10){
                return require('@/assets/blog/white-book.png');
            }else{
                return require('@/assets/blog/evil-book.png');
            }
        }
    },
}
</script>

<style scoped>
.imgSet{
    width: 40px;
    height: 100%;
}
.setHr{
    border-top-color: rgba(44, 44, 44, 0.1);
}
.box-container{
    display: flex;
    justify-content: space-between;
    margin: 5px;
    width: 100%;
    height: 40px;
    align-items: center;
    cursor: pointer;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 25px;
    margin-left: 10px;
    text-align: left;
}
.moreSet{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 25px;
    margin-right: 15px;
    text-align: right;
    color: #d1d1d1;
}
.abstractFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    margin-left: 10px;
    color: #8e8e8e;
    text-align: left;
}
.titleSet{
    display: flex;
    flex-direction: row;
    justify-content: left;
}

.titleAbs{
    display: flex;
    flex-direction: column;
    justify-content: left;
}

</style>

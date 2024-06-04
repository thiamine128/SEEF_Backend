<template>
    <div class="box-container"  @mouseenter="hover=true" @mouseleave="hover=false">
        <div class="titleSet">

            <img style="width: 35px" alt="404" :src="srcImg">

            <div class="titleAbs">
                <div style="display: flex; flex-direction: row; align-items: center;">
                    <div :class="{ titleFont: true }" @click="callArticles">{{title}}</div>
                    <img v-if="isAdmin" alt="404" src="@/assets/blog/cancel.png" @click="deleteTopic"
                         style="width: 18px; margin-left: 5px; cursor: pointer">
                </div>
                <div :class="{ abstractFont: true }">{{abstract}}</div>
            </div>

        </div>
        <div :class="{ moreSet: true }"> > </div>
    </div>
    <hr class="setHr">
</template>

<script>
import {provide, ref} from "vue";
import store from "@/store/store";
import {deleteSection} from "@/pages/blog/api";

export default {
    name: "sectionBox",
    data(){
        return{
            hover: false
        }
    },
    methods: {
        callArticles(){
            if (!this.editorSet) this.$router.push(`/blog/articles/${this.topicId}/${this.title}`);
            else this.$emit('modifyClick');
        },
        async deleteTopic(){
            await deleteSection(this.topicId);
            setTimeout(()=>{
                location.reload();
            }, 100);
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
        },
        isAdmin(){
            return store.getters.getData.role === 'admin';
        },
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
    transition: background-color 0.5s;
}
.box-container:hover{
    display: flex;
    justify-content: space-between;
    margin: 5px;
    width: 100%;
    height: 40px;
    align-items: center;
    background-color: rgba(11,11,11,0.02);
    transition: background-color 0.5s;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 25px;
    margin-left: 10px;
    text-align: left;
    cursor: pointer;
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

<template>
    <div :style="frameStyle" >
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
                <el-button v-if="editorSet == true" style="width: 65px; height: 30px; margin-left: 5px"
                           type="info" plain @click="this.$emit('cancelFloat')"> 取消选择 </el-button>
            </div>
            <hr>
            <div :style="listStyle">

                <section-box v-if="select == 'section'" v-for="item in listSet"
                :title="item.name" :abstract="item.introduction"
                :topic-id="item.id" :editor-set="editorSet" @modifyClick="selectSection(item.id, item.name)"/>

                <article-box v-for="item in listSet" v-if="select == 'article'"
                :abstract="item.preview.slice(0, 120).replaceAll('#', '')" :title="item.title" :likes="item.favourNum"
                :post-time="dateF(item.createTime)" :article-id="item.id" :img-source="item.preview"
                :author-id="item.userId" :tags="item.tags" :is-deleted="item.deleted"/>

            </div>
            <el-pagination class="pagination-style"
                           v-model:current-page="currentPos"
                           ref="bottomPagination"
                           layout="prev, pager, next, jumper"
                           @current-change="pageChange()"
                           :total="10*totalPage" />
        </div>
    </div>
</template>

<script>

import articleBox from "@/pages/blog/components/articleBox/index.vue";
import SectionBox from "@/pages/blog/components/sectionBox/index.vue";
import SpaceBox from "@/pages/blog/components/spaceBox/index.vue";
import dayjs from "dayjs";
export default {
    name: "articleList",
    computed: {

    },
    props: ['heightSet', 'rTitle', 'listSet', 'select', 'totalPage', 'editorSet'],
    components:{SpaceBox, SectionBox, articleBox},
    methods:{
        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },
        pageChange(){
            this.$emit('page-change', this.currentPos);
        },
        selectSection(topicId, name){
            this.$emit('sel', topicId, name);
        },
        resetCurrentPos(){
            this.currentPos = 1;
        }
    },
    data(){
        return{
            currentPos: 1,
            frameStyle:{
                width: '100%',
                height: '1px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                borderRadius: '5px',
                border: '1px solid rgba(155, 155, 155, 0.7)',
                display: 'flex',
                marginBottom: '10px'
            },
            listStyle:{
                width: '100%',
                height: '90%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
            }
        }
    },
    mounted() {
        this.frameStyle.height = this.heightSet;
        const numbers = this.heightSet.split("").filter((char) => !isNaN(Number(char))).join("");
        this.listStyle.height = `${numbers-170}px`;
    },
}
</script>

<style scoped>
.container{
    width: 100%;
    height: 100%;
    margin: auto;
    display: flex;
    flex-direction: column;
    padding: 40px;
}
.titleFont {
    font-family: '微软雅黑', sans-serif;
    font-size: 28px;
    text-align: left;
    font-weight: bold;
    color: #0097e6;
    opacity: 0.8;
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
    height: 40px;
}
hr{
    width: 100%;
}
.pagination-style{
    margin-top: 5px;
    justify-content: right;
}
</style>

<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
            </div>
            <hr>
            <div :style="listStyle">
                <section-box title="软件工程" abstract="一个神奇的学科，我完全的不懂"></section-box>
            </div>
            <el-pagination class="pagination-style"
                           v-model:current-page="currentPos"
                           ref="bottomPagination"
                           layout="prev, pager, next, jumper"
                           @current-change="test()"
                           :total="100" />
        </div>
    </div>
</template>

<script>

import articleBox from "@/pages/blog/components/articleBox/index.vue";
import SectionBox from "@/pages/blog/components/sectionBox/index.vue";

export default {
    name: "articleList",
    props: ['heightSet', 'rTitle'],
    components:{SectionBox, articleBox},
    methods:{
        test(){
            console.log(this.currentPos);
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
                marginBottom: '10px',

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
    }
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
    font-family: 'rage', sans-serif;
    font-size: 28px;
    text-align: left;
    color: #6a6a6a;
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

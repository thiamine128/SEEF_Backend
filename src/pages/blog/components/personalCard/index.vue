<template>
    <div :style="frameStyle">
        <div class="container">

            <div :class="{ titleFont: true }"> {{rTitle}} </div>

            <div class="card-nav">
                <div class="nav-button"> 返回主列表 </div>
                <div class="nav-button"> 添加文件夹 </div>
            </div>

            <div :style="listStyle">
<!--                <article-box v-for="item in testList" :title="item.name">-->
<!--                </article-box>-->
            </div>

            <el-pagination class="pagination-style"
                           v-model:current-page="currentPos"
                           ref="bottomPagination"
                           layout="prev, pager, next"
                           @current-change="test()"
                           :total="100" />

        </div>
    </div>
</template>

<script>
import recommendButton from "@/pages/blog/components/recommendButton/index.vue";
import ArticleBox from "@/pages/blog/components/articleBox/index.vue";
export default {
    name: "personalCard",
    props: ['heightSet', 'rTitle'],
    components:{ArticleBox, recommendButton},
    data(){
        return{
            frameStyle:{
                height: '1px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                border: '1px solid rgba(155, 155, 155, 0.2)',
                display: 'flex',
                marginBottom: '10px'
            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1',
                overflowX: 'hidden'
            },

            // testList: [
            //     {name: '数据管理技术'}, {name: '软件工程'},
            //     {name: '人工智障'}, {name: '操作系统'},
            // ],

            currentPos: 0
        }
    },
    mounted() {
        this.frameStyle.height = this.heightSet;
    },
    methods:{
        test(){

        }
    }
}
</script>

<style scoped>
.nav-button{
    font-family: 'consolas', sans-serif;
    font-size: 15px;
    color: #8e8e8e;
    cursor: pointer;
}
.card-nav{
    width: 100%;
    height: 22px;
    display: flex;
    flex-direction: row;
    justify-content: right;
    align-items: center;
    margin-bottom: 10px;
    gap: 5px;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 17px;
    text-align: center;
    margin-bottom: 12px;
}
.pagination-style{
    margin-top: 5px;
    justify-content: right;
}
.container{
    width: 88%;
    height: 90%;
    margin: auto;
    display: flex;
    flex-direction: column;
}

</style>

<template>
    <div :style="frameStyle">
        <div class="container">
            <div :style="listStyle">
                <recommend-button v-for="item in testList" :r-name="item.name">
                </recommend-button>
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
export default {
    name: "personalCard",
    props: ['heightSet', 'rTitle'],
    components:{recommendButton},
    data(){
        return{
            frameStyle:{
                height: '1px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                borderRadius: '5px',
                border: '1px solid rgba(155, 155, 155, 0.7)',
                display: 'flex',
                marginBottom: '10px'
            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1'
            },
            testList: [
                {name: '数据管理技术'}, {name: '软件工程'},
                {name: '人工智障'}, {name: '操作系统'},
            ],
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

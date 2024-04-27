<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
                <a class="linkStyle">
                    <div :class="{ moreFont: true }"> 了解更多> </div>
                </a>
            </div>
            <hr>
            <div :style="listStyle">
                <ddl-button v-for="item in testList" :r-name="item.name">
                </ddl-button>
            </div>
        </div>
    </div>
</template>

<script>
import ddlButton from "@/pages/education/components/ddlButton/index.vue";
export default {
    name: "ddl",
    props: ['heightSet', 'rTitle'],
    components:{ddlButton},
    data(){
        return{
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
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1'
            },
            testList: [
                {name: '数据管理技术'}, {name: '软件工程'},
                {name: '人工智障'}, {name: '操作系统'},
            ]
        }
    },
    mounted() {
        this.frameStyle.height = this.heightSet;
    }
}
</script>

<style scoped>
.container{
    width: 88%;
    height: 90%;
    margin: auto;
    display: flex;
    flex-direction: column;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 17px;
    text-align: center;
}
.moreFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 13px;
    text-align: center;
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 22px;
}
.linkStyle{
    text-decoration: none;
    color: #000;
    cursor: pointer;
}
hr{
    width: 100%;
}
</style>

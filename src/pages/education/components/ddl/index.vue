<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
                <button @click="sortList('asc', 'date')" class="sortButton">按时间升序</button>
                <button @click="sortList('desc', 'date')" class="sortButton">按时间降序</button>
                <button @click="sortList('asc', 'priority')" class="sortButton">按优先级升序</button>
                <button @click="sortList('desc', 'priority')" class="sortButton">按优先级降序</button>
                <a class="linkStyle">
                    <div :class="{ moreFont: true }"> 更多ddl> </div>
                </a>
            </div>
            <hr>
            <div :style="listStyle">
                <ddl-button v-for="item in ddlList" :r-name="item.name+' ddl：'+item.date+' 优先级: '+item.priority">
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
                height: '500px',
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
            ddlList: [
                {name: '数据期中大作业', date: '2024.05.14', priority: 2}, {name: '软件工程文档', date: '2024.05.05', priority: 2},
                {name: '人工智障', date: '2024.05.02', priority: 3}, {name: '操作系统第四次作业', date: '2024.05.09', priority: 1},
            ],
        }
    },
    methods: {
        sortList(direction, key) {
            if (direction === 'asc' && key === 'date') {
                this.ddlList.sort((a, b) => a.date.localeCompare(b.date));
            } else if (direction === 'desc' && key === 'date') {
                this.ddlList.sort((a, b) => b.date.localeCompare(a.date));
            } else if (direction === 'asc' && key === 'priority') {
                this.ddlList.sort((a, b) => a.priority - b.priority);
            } else if (direction === 'desc' && key === 'priority') {
                this.ddlList.sort((a, b) => b.priority - a.priority);
            }
        },
    },
    computed: {

    },
    mounted() {
        // this.frameStyle.height = this.heightSet;
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
.sortButton{
    display: flex;
    justify-content: center;
    flex-direction: row;
    align-items: center;
    width: 100px;
    padding: 5px;
    background-color: rgba(117, 21, 21, 0);
}
</style>

<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div><a class="linkStyle">
                    <div :class="{ moreFont: true }"> 更多课程> </div>
                </a>
            </div>
            <hr>
            <div :style="listStyle">
                <ddl-button v-for="item in lessonList" :r-name="item.name+' 时间：'+item.date+' 教室: '+item.classroom">
                </ddl-button>
            </div>
        </div>
    </div>
</template>

<script>
import ddlButton from "@/pages/education/components/ddlButton/index.vue";
export default {
    name: "todayLesson",
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
            lessonList: [
                {name: '软件工程', date: '09:50', classroom: '主南405'}, {name: '科研课堂', date: '14:00', classroom: 'G1128'},
                {name: '工程伦理与沟通技巧', date: '15:50', classroom: '(三)203'}, {name: '软工上机', date: '19:00', classroom: '新北机房'},
            ],
        }
    },
    methods: {
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

<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
            </div>
            <hr>
            <div :style="listStyle">
                <ddl-button v-for="item in ddlList" :course-id="item.courseId" :title="item.title" :course-name="item.courseName" :due-date="dateF(item.dueDate)" :submitted="item.submissionTime">
                </ddl-button>
            </div>
        </div>
    </div>
</template>

<script>
import ddlButton from "@/pages/education/components/ddlButton/index.vue";
import {callError} from "@/callMessage";
import dayjs from "dayjs";

export default {
    name: "ddl",
    props: ['heightSet', 'rTitle'],
    components:{ddlButton},
    data(){
        return{
            frameStyle:{
                width: '100%',
                height: '45%',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                border: '0.2px solid rgba(155, 155, 155, 0.3)',
                display: 'flex',
            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1'
            },
            ddlList: [

            ],
        }
    },
    methods: {
        async pullDDLs(pageNum){
            try{
                const response = await this.$http.get(
                    `assignment/all?showOutdated=false`,{
                    'Content-Type': 'application/x-www-form-urlencoded',
                });

                // console.log(response);
                if (response.status === 200) {
                    this.ddlList = response.data.data;

                    this.ddlList.sort((a, b) => {
                        if (a.submissionTime == null) return -1;
                        if (b.submissionTime == null) return 1;
                        return a.dueDate - b.dueDate;
                    });
                    // console.log(this.ddlList)
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        },
        dateF(num) {
            return dayjs(num).format('YYYY-MM-DD');
        },
    },
    computed: {

    },
    mounted() {
        // this.frameStyle.height = this.heightSet;
        this.pullDDLs(1);
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

<template>
    <div class="replySet">
        <div class="nameFont"> <strong> {{name}}：</strong> {{content}} </div>
    </div>
</template>

<script>
import {getUserData} from "@/pages/blog/api";

export default {
    name: "replyBox",
    props: ['content', 'userId', 'toId'],
    methods:{
        async pullPersonalData(){
            const personal_data = await getUserData(this.userId);
            this.name = personal_data.name;
            if (this.toId != null){
                const personal_data2 = await getUserData(this.toId);
                this.name += ' 回复 ' + personal_data2.name;
            }
        },
    },
    data(){
        return{
            name: ''
        }
    },
    mounted() {

        this.pullPersonalData();
    }
}
</script>

<style scoped>
.replySet{
    display: flex;
    flex-direction: row;
    width: 100%;
    min-height: 15px;
    margin-top: 3px;
    margin-bottom: 3px;
    cursor: pointer;
    margin-left: 10px;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: left;
}
</style>

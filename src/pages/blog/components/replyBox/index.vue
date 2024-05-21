<template>
    <div class="replySet">
        <div class="nameFont"> <strong> {{name}}：</strong> {{content}}
            <strong v-if="userId === nowLogin && !dShow" style="margin-left: 5px; color: #ffb1b1;
            cursor: pointer" @click.stop="deleteReply">删除</strong>
        </div>
    </div>
</template>

<script>
import {deleteComment, deleteReply, getUserData} from "@/pages/blog/api";
import store from "@/store/store";

export default {
    name: "replyBox",
    props: ['content', 'userId', 'toId', 'replyId', 'dShow'],
    methods:{

        async deleteReply(){
            await deleteReply(this.replyId);
            setTimeout(()=>{
                location.reload();
            }, 1000);
        },

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
            name: '',
            nowLogin: -1
        }
    },
    mounted() {

        this.nowLogin = store.getters.getData.id;

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

<template>
    <div class="replySet">
        <div :class="{ nameFont: true }"> <strong> {{name}}：</strong> {{content}} </div>
    </div>
</template>

<script>
export default {
    name: "replyBox",
    props: ['content', 'userId', 'toId'],
    methods:{
        async pullPersonalData(){
            const response = await this.$http.get(`/user?userId=${this.userId}`);
            const personal_data = response.data.data;
            this.name = personal_data.name;

            if (this.toId != null){
                const response2 = await this.$http.get(`/user?userId=${this.toId}`);
                const personal_data2 = response2.data.data;
                this.name += ' 回复 ' + personal_data.name;
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
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: left;
    margin-left: 10px;
}
</style>

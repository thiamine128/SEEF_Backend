<template>
    <div class="replySet">
        <div :class="{ nameFont: true }"> <strong> {{name}}：</strong> {{content}} </div>
    </div>
</template>

<script>
export default {
    name: "replyBox",
    props: ['name', 'content']
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

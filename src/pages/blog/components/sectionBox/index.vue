<template>
    <div class="box-container">
        <div class="titleSet">
            <img src="/favicon.ico" alt="404 not found">
            <div :class="{ titleFont: true }">{{title}}</div>
            <div :class="{ abstractFont: true }">{{abstract}}</div>
        </div>
        <div :class="{ moreSet: true }"> > </div>
    </div>
    <hr class="setHr">
</template>

<script>
export default {
    name: "sectionBox",
    props: ['title', 'abstract']
}
</script>

<style scoped>
.setHr{
    border-top-color: rgba(44, 44, 44, 0.1);
}
.box-container{
    display: flex;
    justify-content: space-between;
    margin: 5px;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 25px;
    margin-left: 10px;
    text-align: left;
}
.moreSet{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 25px;
    margin-right: 5px;
    text-align: right;
    color: #8e8e8e;
}
.abstractFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    margin-left: 5px;
    color: #8e8e8e;
    text-align: left;
}
.titleSet{
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
}
</style>

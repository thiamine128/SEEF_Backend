<template>
    <div class="titleFather">
      <div :class="{ titleFont: true }">{{title}}</div>
      <div :class="{ dateFont: true }">{{dateString}}</div>
    </div>
</template>

<script>
export default {
    name: "titleGroup",
    props: ['title', 'dateString'],
}
</script>

<style scoped>
.titleFather{
    display: flex;
    flex-direction: column;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 50px;
    margin: auto;
    color: rgba(255, 255, 255, 0.8);
}
.dateFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    color: #bcbcbc;
    font-size: 20px;
    margin: auto;
}
</style>

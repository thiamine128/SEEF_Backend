<template>
    <div class="titleFather">
      <div :class="{ titleFont: true }">{{title}}</div>
        <div v-if="tagList.length > 0" style="display: flex; flex-direction: row; justify-content: center; align-items: center; height: 25px">
        <div v-for="item in tagList" class="tagSet">
            {{item}}
        </div>
        </div>
      <div :class="{ dateFont: true }">{{dateString}}</div>
    </div>
</template>

<script>
export default {
    name: "titleGroup",
    props: ['title', 'dateString', 'tagList'],
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
.tagSet{
    padding: 3px 5px;
    margin-left: 3px;
    height: 60%;
    min-width: 0;
    display: flex;
    flex-direction: row;
    background-color: rgba(208, 208, 208, 0.2);
    border-radius: 2px;
    justify-content: center;
    align-items: center;
    cursor: default;
    font-size: 16px;
    color: rgba(255, 255, 255, 0.8);
    font-weight: bold;
}
</style>

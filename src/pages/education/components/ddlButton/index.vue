<template>
    <div class="title-container"
         @mouseenter="hover = true"
         @mouseleave="hover = false">
        <div :class="{ titleFont: true }">{{rName}}</div>
        <div :class="{ moreFont: true }"> > </div>
    </div>
</template>

<script>
export default {
    name: "ddlButton",
    props: ['rName'],
    data(){
        return{
            hover: false
        }
    }
}
</script>

<style scoped>
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 16px;
    text-align: center;
}
.moreFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 16px;
    text-align: center;
    color: rgba(55, 55, 55, 0.4);
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0);
    transition: background-color 0.5s ease;
}
.title-container:hover{
    background-color: rgba(147, 63, 63, 0.1);
}
</style>

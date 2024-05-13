<template>
    <div class="photo-set" @mouseenter="hover=true" @mouseleave="hover=false">
        <img  class="imgLoc" alt="404" :src="path">
        <div :class="{ buttonFont: !hover, buttonFontExtra: hover }"> {{name}} </div>
    </div>
</template>

<script>
export default {
    name: "leftButton",
    props:['name', 'path'],
    data(){
        return{
            hover: false
        }
    }
}
</script>

<style scoped>
.imgLoc{
    width: 30px;
    height: 30px;
}
.buttonFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 10px;
    text-align: center;
    cursor: default;
}
.buttonFontExtra{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 10px;
    text-align: center;
    cursor: pointer;
    font-weight: bold;
    color: #00bbff;
}
.photo-set{
    margin-top: 10px;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: rgba(11, 11, 11, 0);
    transition: background-color 0.5s;
    border-radius: 10px;
    cursor: pointer;
}
.photo-set:hover{
    background-color: rgba(11, 11, 11, 0.1);
}
</style>

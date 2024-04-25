<template>
    <div class="container" @mouseenter="hover = true" @mouseleave="hover = false">
      <img class="imgSet" :src="imgPath" alt="404">
      <div :class="{ contentFont: true }"> {{content}} </div>
    </div>
</template>

<script>
import {ref} from "vue";
export default {
    name: "personalButton",
    props: ["imgPath", "content"],
    data(){
        return{
            hover: false
        }
    }
}
</script>

<style scoped>
.container{
    display: flex;
    flex-direction: row;
    width: 50px;
    height: 30px;
    background-color: rgba(22, 22, 22, 0.08);
    transition: background-color 0.5s;
    align-items: center;
    padding-left: 3px;
    padding-right: 3px;
    border-radius: 10px;
    cursor: pointer;
}
.container:hover{
    background-color: rgba(22, 22, 22, 0.2);
}
.imgSet{
    height: 20px;
    width: 20px;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    color: gray;
    margin: auto;
    /*font-weight: bold;*/
}
</style>

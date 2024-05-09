<template>
    <div class="title-container"
         @mouseenter="hover = true"
         @mouseleave="hover = false">
        <div class="text">{{rName}}</div>
    </div>
</template>

<script>
export default {
    name: "courseButton",
    props: ['rName'],
    data(){
        return{
            hover: false
        }
    }
}
</script>

<style scoped>
.text{
    text-align: center;
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 16px;
    margin: 0 auto;
}
.title-container{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0);
    transition: background-color 0.5s ease;
}
.title-container:hover{
    background-color: rgba(147, 63, 63, 0.1);
    border-radius: 15px; /* Sets a 15px radius for all corners */
    border: 1px solid #ccc;
}
</style>

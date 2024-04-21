<template>
    <div class="todayLesson">
    </div>
</template>

<script>

export default {
    name: "todayLesson",
    props: ['todayLesson'],
    data(){
        return{
            hover: false
        }
    },
    mounted() {

    },
}
</script>

<style scoped>
.todayLesson{
    width: 600px;
    height: 500px;
    margin-top: 80px;
    margin-right: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(238, 215, 68, 0.6);
    transition: background-color 0.5s ease ;
}
.col:hover{
    background-color: rgba(255, 255, 255, 0.35);
}

.linkStyle{
    text-decoration: none;
    color: #000;
    cursor: pointer;
}
.customFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 16px;
    color: white;
}
</style>

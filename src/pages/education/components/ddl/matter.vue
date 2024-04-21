<template>
    <div class="matter">
    </div>
</template>

<script>

export default {
    name: "matter",
    props: ['matter'],
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
.matter{
    width: 500px;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(73, 65, 9, 0.6);
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

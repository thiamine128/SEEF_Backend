<template>
    <div class="col"
         @mouseenter="hover = true"
         @mouseleave="hover = false"
         @mouseover="hover = true">
        <a  href="/login" class="linkStyle">
            <div :class="{ customFont: true }">{{buttonName}}</div>
        </a>
    </div>
</template>

<script>

export default {
    name: "navButton",
    props: ['buttonName'],
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
.col{
    width: 100px;
    height: 100%;
    background-color: rgba(0.34, 0.34, 0.34, 0.1);
    transition: background-color 0.5s ease ;

}
.col:hover{
    background-color: rgba(0.34, 0.34, 0.34, 0.7);
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
    margin-top: 33px;
    color: white;
}
</style>

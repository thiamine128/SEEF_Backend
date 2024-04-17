<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }">software and life</div>

        <nav class="button-nav">
            <nav-button v-for="item in buttonSet" :button-name="item.name"></nav-button>
        </nav>
    </nav>
</template>

<script>
import navButton from "@/pages/blog/components/navButton/index.vue";
export default {
    name: "navBar",
    data(){
        return{
            buttonSet: [
                {name: '首页'}, {name: '专区'}, {name:'发布'}, {name:'个人界面'}
            ]
        }
    },
    components: {navButton}
}
</script>

<style scoped>
.mainTitleContainer{
    display: flex;
    justify-content: space-between;
    /*background-color: rgba(0.3, 0.2, 0.3, 0.3);*/
}
.button-nav {
    display: flex;
    flex-direction: row;
    justify-content: right;
}
.customFont {
    font-family: 'rage', sans-serif;
    font-weight: bold;
    font-size: 32px;
    margin: 25px 200px 25px 60px;
    color: white;
}
</style>

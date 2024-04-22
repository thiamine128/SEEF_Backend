<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }">software and life</div>
        <div class="search-nav">
            <search-bar/>
            <nav class="button-nav">
                <nav-button v-for="item in buttonSet"
                :button-name="item.name" :dest="item.dest"></nav-button>
            </nav>
        </div>
    </nav>
</template>

<script>
import navButton from "@/pages/blog/components/navButton/index.vue";
import searchBar from "@/pages/blog/components/searchBar/index.vue";
export default {
    name: "navBar",
    data(){
        return{
            buttonSet: [
                {name: '首页', dest: '/'},
                {name: '专区', dest: '/blog/section'},
                {name:'发布', dest:'/blog/editor'},
                {name:'个人界面', dest: '/'}
            ]
        }
    },
    components: {navButton, searchBar}
}
</script>

<style scoped>
.mainTitleContainer{
    display: flex;
    justify-content: space-between;
}
.search-nav{
    display: flex;
    justify-content: right;
    width: 800px;
    align-items: center;
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
    margin: 25px 0 25px 60px;
    color: white;

}
</style>

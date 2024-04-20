<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }">software and life</div>
        <div class="search-nav">
            <search-bar/>
            <nav class="button-nav">
                <nav-button v-for="item in buttonSet" :button-name="item.name"></nav-button>
            </nav>
        </div>
    </nav>
</template>

<script>
import navButton from "@/pages/education/components/navigationButton/index.vue";
export default {
    name: "navBar",
    data(){
        return{
            buttonSet: [
                {name: '我的课程'}, {name: '课程广场'}, {name:'我的课表'}, {name:'个人中心'}
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

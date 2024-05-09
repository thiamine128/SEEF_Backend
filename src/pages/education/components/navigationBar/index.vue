<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }">software and life</div>
        <div class="search-nav">
            <search-bar/>
            <nav class="button-nav">
                <nav-button v-for="item in buttonSet" :button-name="item.name" :dest="item.dest"></nav-button>
            </nav>
        </div>
    </nav>
</template>

<script>
import searchBar from "@/pages/education/components/searchBar/index.vue";
import navButton from "@/pages/education/components/navigationButton/index.vue";
export default {
    name: "navBar",
    data(){
        return{
            buttonSet: [
                {name: '我的课程', dest: '/education/myLesson'},
                {name: '课程广场', dest: '/education/courseSquare'},
                {name: '我的课表', dest: '/education/mySchoolTimeTable'},
                {name: '个人中心', dest: '/education/personCenter'}
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

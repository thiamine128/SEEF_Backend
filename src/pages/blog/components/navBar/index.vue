<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }" @click="toHome">software and life</div>
        <div class="search-nav">
            <search-bar/>
            <nav class="button-nav">
                <nav-button v-for="item in buttonSet"
                :button-name="item.name" :dest="item.dest"></nav-button>

                <nav-button :button-name="spaceSet.name" :dest="spaceSet.dest"/>
            </nav>
            <el-avatar class="avatarSet" :src="imgPath" @error="altImg()"
            @mouseenter="hover=true" @mouseleave="hover=false" @click="toPersonal" />
        </div>
    </nav>
</template>

<script>
import navButton from "@/pages/blog/components/navButton/index.vue";
import searchBar from "@/pages/blog/components/searchBar/index.vue";
import store from "@/store/store";
export default {
    name: "navBar",
    mounted() {
        try{
            this.imgPath = require(store.getters.getData.avatar);
        }catch (e){
        }
    },
    data(){
        return{
            buttonSet: [
                {name: '首页', dest: '/blog/homepage'},
                {name: '专区', dest: '/blog/section/'},
                {name: '发布', dest:'/blog/editor'},
            ],
            spaceSet: {name: '动态', dest: '/blog/space'},
            hover: false,
            imgPath: require('@/assets/blog/user.png')
        }
    },
    methods:{
        toPersonal(){
            this.$router.push(`/blog/personal/${store.getters.getData.id}`);
        },
        toHome(){
            this.$router.push('/blog/');
        },
        altImg(){
            this.imgPath = require('@/assets/blog/user.png');
        },
    },
    components: {navButton, searchBar}
}
</script>

<style scoped>
.avatarSet{
    width: 50px;
    height: 50px;
    margin-right: 30px;
    margin-left: 30px;
    cursor: pointer;
    transition: height 0.2s, width 0.2s, margin-right 0.2s, margin-left 0.2s;
}
.avatarSet:hover{
    width: 54px;
    height: 54px;
    margin-right: 28px;
    margin-left: 28px;
}
.mainTitleContainer{
    display: flex;
    justify-content: space-between;
}
.search-nav{
    display: flex;
    justify-content: right;
    width: 1000px;
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
    cursor: pointer;
}
</style>

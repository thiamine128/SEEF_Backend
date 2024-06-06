<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }" @click="toHome">SEEF </div>
        <div class="search-nav">
            <search-bar/>
            <nav class="button-nav">
                <nav-button v-for="item in buttonSet"
                :button-name="item.name" :dest="item.dest"></nav-button>

                <nav-button :button-name="spaceSet.name" :dest="spaceSet.dest"/>

                <nav-button :button-name="eduSet.name" :dest="eduSet.dest"/>

<!--                <nav-button :button-name="logoutSet.name" :dest="logoutSet.dest"/>-->
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
import {getUserData} from "@/pages/blog/api";
import {ref} from "vue";
export default {
    name: "navBar",
    async mounted() {
        try{
            const personal_data = await getUserData(store.getters.getData.id, false);
            personal_data.avatar = personal_data.avatar + '?t=' + new Date().getTime();
            this.imgPath = ref(personal_data.avatar);
        }catch (e){
        }
    },
    data(){
        return{
            buttonSet: [
                {name: '首页', dest: '/blog/homepage'},
                {name: '专区', dest: '/blog/section/'},
                {name: '发布', dest:'/blog/editor/-1'},
            ],
            spaceSet: {name: '动态', dest: '/blog/space'},
            eduSet: {name: '教学', dest: '/education'},
            logoutSet: {name: '登出', dest: '/logout'},
            hover: false,
            imgPath: require('@/assets/blog/user.png')
        }
    },
    methods:{
        toPersonal(){
            this.toHome();
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${store.getters.getData.id}`);
            }, 100);
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
    width: 1200px;
    align-items: center;
}
.button-nav {
    display: flex;
    flex-direction: row;
    justify-content: right;
}
.customFont {
    font-family: 'rage', sans-serif;
    font-size: 32px;
    margin: 25px 0 25px 60px;
    color: white;
    cursor: pointer;
}
</style>

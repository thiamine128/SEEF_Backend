<template>
    <nav class="mainTitleContainer">
        <div :class="{ customFont: true }" @click="toHome"> SEEF </div>
        <div class="search-nav">
            <nav class="button-nav">
<!--                <nav-button v-for="item in buttonSet" :button-name="item.name" :dest="item.dest"></nav-button>-->
                <nav-button button-name="前往首页" dest="/education"/>
                <nav-button button-name="我的助教" dest="/education/TACenter" v-if="isT"/>
                <nav-button button-name="管理中心" dest="/education/adminCenter" v-if="isAdmin"/>
                <nav-button button-name="我的课程" dest="/education/myLesson"/>
                <nav-button button-name="课程广场" dest="/education/courseSquare"/>
                <nav-button button-name="我的课表" dest="/education/mySchoolTimeTable" v-if="!isTeacher"/>
                <nav-button button-name="前往博客" dest="/blog/homepage"/>
            </nav>
            <el-avatar class="avatarSet" :src="imgPath" @error="altImg()"
                       @mouseenter="hover=true" @mouseleave="hover=false" @click="toPersonal" />
        </div>
    </nav>
</template>

<script>
import searchBar from "@/pages/education/components/searchBar/index.vue";
import navButton from "@/pages/education/components/navigationButton/index.vue";
import store from "@/store/store";
import {getUserData} from "@/pages/blog/api";
import {ref} from "vue";
import {hasManageClassAPI} from "@/pages/education/api";
export default {
    name: "navBar",
    data(){
        return{
            buttonSet: [
                {name: '我的课程', dest: '/education/myLesson'},
                {name: '课程广场', dest: '/education/courseSquare'},
                {name: '我的课表', dest: '/education/mySchoolTimeTable'},
                {name: '前往博客', dest: '/blog/homepage'},
                // {name: '个人中心', dest: '/education/personCenter'}
            ],
            hover: false,
            imgPath: require('@/assets/blog/user.png'),
            isT: null,
        }
    },
    components: {navButton, searchBar},
    methods: {
        toPersonal(){
            this.toHome();
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${store.getters.getData.id}`);
            }, 100);
        },
        toHome(){
            this.$router.push('/education/');
        },
        altImg(){
            this.imgPath = require('@/assets/blog/user.png');
        },
    },
    computed: {
        isAdmin() {
            return store.getters.getEduIdentity === 'admin';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        }
    },
    async mounted() {
        this.isT = store.getters.getEduIsTA;

        try{
            const personal_data = await getUserData(store.getters.getData.id, false);
            personal_data.avatar = personal_data.avatar + '?t=' + new Date().getTime();
            this.imgPath = ref(personal_data.avatar);
        }catch (e){

        }

    }
}
</script>

<style scoped>

.mainTitleContainer{
    display: flex;
    justify-content: space-between;
    background-color: rgba(11, 11, 11, 0.4);
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
    font-size: 32px;
    margin: 25px 0 25px 60px;
    color: white;
    cursor: pointer;
}

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
</style>

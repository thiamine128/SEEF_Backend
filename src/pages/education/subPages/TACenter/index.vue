<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <h2 style="text-align: center">我的助教班</h2>
    <h4 style="text-align: center; color: #8e8e8e" v-if="this.myClasses === null || this.myClasses.length === 0">暂无班级</h4>

    <div class="container">
        <div v-for="(classT, index) in this.myClasses" :key="index" class="class-item">
            <p>课程名称: {{ classT.courseName }}</p>
            <p>班级编号: {{ classT.id }}</p>
            <p>时间: {{ classT.time }}</p>
            <p>地点: {{ classT.location }}</p>
            <p>任课教师: {{ classT.teachers }}</p>
            <a :href="'/education/courses/' + classT.courseId" target="_blank" rel="noopener noreferrer">
                查看详情
            </a>
        </div>
    </div>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import store from "@/store/store";
import {getClassData, getUserName, getUserRealName, hasManageClassAPI, listMyClassAPI} from "@/pages/education/api";

export default {
    name: "myLesson",
    components: {
        navigationBar,
    },
    methods: {
        getTeacherName(teachers) {
            let res = [];
        },
        fun() {
            // console.log(this.myClasses[0]);
        }
    },
    computed: {
    },
    data() {
        return {
            courses: null,
            myClasses: null,
        };
    },
    async mounted() {
        if(await hasManageClassAPI(store.getters.getData.id) === false) {
            this.$router.push('/education');
        }
        const classesId = await listMyClassAPI(store.getters.getData.id);
        let classes = []
        for(let i = 0; i < classesId.length; i++) {
            classes.push(await getClassData(classesId[i]));
            // console.log(await getClassData(classesId[i]));
        }
        this.myClasses = classes;
        for(let i = 0; i < this.myClasses.length; i++) {
            let class_data = JSON.parse(JSON.stringify( this.myClasses[i]));
            this.myClasses[i].teachers = [];//class_data.teachers;
            for(let j = 0; j < class_data.teachers.length; j++) {
                const nameT = await getUserRealName(class_data.teachers[j]);
                this.myClasses[i].teachers.push(nameT);
            }
            if(this.myClasses[i].teachers.length === 0) {
                this.myClasses[i].teachers.push("暂未分配");
            }
            this.myClasses[i].teachers = this.myClasses[i].teachers.join('，');
        }
        // console.log('this is classes', this.myClasses);
    },
}
</script>

<style scoped>

.bg-container {
    background: url('@/assets/education/bg.png');
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}

.my-classes h2 {
    font-size: 24px;
    font-weight: bold;
    text-align: center;
    color: #333;
}

.container {
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    justify-content: center;
}

.class-item {
    align-self: center;
    width: 500px;
    margin-right: 20px;
    margin-bottom: 20px;
    padding: 20px;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.class-item h2 {
    margin-top: 0;
}

.class-item h2 {
    font-size: 20px;
    font-weight: bold;
    color: #333;
}


.class-item p {
    margin-bottom: 10px;
}

.class-item a {
    display: inline-block;
    padding: 5px 10px;
    background-color: #007bff;
    color: #fff;
    text-decoration: none;
    border-radius: 4px;
    transition: background-color 0.3s ease;
}

.class-item a:hover {
    background-color: #0056b3;
}
</style>

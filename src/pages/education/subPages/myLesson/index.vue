<template>
    <div class="bg-container"/>
    <navigation-bar/>

    <div style="display: flex; flex-direction: row">

    <div class="button-container">

        <!--        <div style="height: 50px; display: flex; width: 100%">-->
        <!--            <div style="margin: auto; font-size: 30px;font-family: 'rage', 'sans-serif'"> lesson </div> </div>-->
        <img alt="404" src="@/assets/education/edu.png" style="width: 140px">
        <find-course-button  @update-courses="updateCourses" @update-show-course-list="updateShowCourseList"
                             @update-show-class-list="updateShowClassList" @update-find-name="updateFindName"/>
        <find-class-button  @update-classes="updateClasses" @update-show-class-list="updateShowClassList" @update-show-course-list="updateShowCourseList"/>

        <create-course-button v-if="isTeacher"/>
        <create-class-button v-if="isTeacher"/>

        <join-class-button v-if="isStudent"/>
        <addButchStudentToClassButton v-if="isTeacher"/>
        <add-butch-t-a-to-class-button v-if="isTeacher"/>
        <download-muban v-if="isTeacher || isAdmin"/>
        <add-teacher-to-class-button v-if="isTeacher"/>

    </div>

<!--    <button @click="upload">临时上传</button>-->
    <div style="flex: 1">

        <course-list :courses="courses" :find-name="findName" v-if="showCourseList"  @close-course-list="closeCourseList"/>
        <class-list :classes="classes" :courseId="courseId" :course-name="classCourseName" v-if="showClassList" @close-class-list="closeClassList"/>

        <h2 style="text-align: center">我的班级</h2>
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

    </div>

    </div>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import createCourseButton from "@/pages/education/components/createCourseButton/index.vue";
import createClassButton from "@/pages/education/components/createClassButton/index.vue";
import findCourseButton from "@/pages/education/components/findCourseButton/index.vue";
import findClassButton from "@/pages/education/components/findClassButton/index.vue";
import joinClassButton from "@/pages/education/components/joinClassButton/index.vue";
import addTeacherToClassButton from "@/pages/education/components/addTeacherToClassButton/index.vue";
import addTeacherToCourseButton from "@/pages/education/components/addTeacherToCourseButton/index.vue";
import addButchStudentToClassButton from "@/pages/education/components/addButchStudentToClassButton/index.vue";
import addButchTAToClassButton from "@/pages/education/components/addButchTAToClassButton/index.vue";
import courseList from "@/pages/education/components/courseList/index.vue";
import classList from "@/pages/education/components/classList/index.vue";
import downloadMuban from "@/pages/education/components/downloadMuban/index.vue";
import store from "@/store/store";
import {getMyClassAPI, getUserName, getUserRealName, listTeacherClassAPI, uploadAPI} from "@/pages/education/api";

export default {
    name: "myLesson",
    components: {
        createCourseButton,
        navigationBar,
        createClassButton,
        findCourseButton,
        findClassButton,
        courseList,
        classList,
        joinClassButton,
        addTeacherToClassButton,
        addTeacherToCourseButton,
        addButchStudentToClassButton,
        addButchTAToClassButton,
        downloadMuban,
    },
    methods: {
        updateCourses(newCourse) {
            this.courses = newCourse;
        },
        updateClasses(newClasses, newCourseId) {
            this.classes = newClasses;
            this.courseId = newCourseId;
            try {
                if(this.classes) {
                    this.classCourseName = '《' + this.classes[0].courseName + '》';
                    // console.log('name:', this.classCourseName);
                }
            } catch (error) {
                this.classCourseName = '';
            }
        },
        upload() {
            uploadAPI();
        },
        updateShowCourseList(newShowCourseList) {
            this.showCourseList = newShowCourseList;
        },
        updateShowClassList(newShowClassList) {
            this.showClassList = newShowClassList;
        },
        updateFindName(newFindName) {
            this.findName = newFindName;
        },
        closeClassList() {
            this.showClassList = false;
        },
        closeCourseList() {
            this.showCourseList = false;
        },
        getTeacherName(teachers) {
            let res = [];

        }
    },
    computed: {
        isStudent() {
            return store.getters.getEduIdentity === 'student';
        },
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        },
        isAdmin() {
            return store.getters.getEduIdentity === 'admin';
        }
    },
    data() {
        return {
            courses: null,
            classes: null,
            showCourseList: false,
            showClassList: false,
            courseId: null,
            myClasses: null,
            findName: null,
            classCourseName: null,
        };
    },
    async mounted() {
        if(store.getters.getEduIdentity === 'student')
            this.myClasses = await getMyClassAPI();
        else if(store.getters.getEduIdentity === 'teacher') {
            this.myClasses = await listTeacherClassAPI(store.getters.getId);
            if(this.myClasses === null) this.myClasses = [];
        }
        else
            this.myClasses = [];
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

.button-container {
    display: flex;
    flex-direction: column;
    justify-content: left;
    align-items: center;
    width: 180px;
    height: auto;
    min-height: 100vh;
    background-color: rgba(255, 255, 255, 0.8);
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

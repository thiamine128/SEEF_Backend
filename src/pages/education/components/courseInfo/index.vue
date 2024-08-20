<template>
    <div class="course-card">
        <div class="course-layout" style="display: flex; align-items: center;">

            <el-container>
                    <el-header class="course-title">
                    </el-header>
                <el-container>
                    <el-aside style=" overflow: hidden; display: flex; flex-direction: column; justify-content: center">

                        <course-cover style="width: 100%;" :url="course.cover" :course-name="course.name"></course-cover>

                        <div style="display: flex; flex-direction: column; align-items: center">
                            <div style="font-size: 25px; font-weight: bold">
                                {{ course.name }}
                            </div>
                            <div style="display: flex; flex-direction: column;height: 55px; align-items: center">
                                <button class="toArticles" @click="toArticles(course.name)">{{ course.name }}博客专区</button>
                                <update-cover-button :course-id="course.id" v-if="course.hasPermission"/>
                            </div>
                        </div>

                    </el-aside>

                    <el-main>
                        <p><b>编号：</b> {{ course.id }}</p>
                        <p><b>学分：</b> {{ course.credit }}</p>
                        <p><b>考核方式：</b> {{ course.evaluation }}</p>
                        <p><b>课程简介：</b></p>
                        <md-text :input-content="course.introduction == null ? '暂无' : course.introduction"></md-text>
                        <p><b>课程大纲：</b></p>
                        <md-text :input-content="course.syllabus == null ? '暂无' : course.syllabus"></md-text>
                    </el-main>
                </el-container>



                <p><b>班级信息</b></p>
                <el-tabs type="card" v-model="currentClass">
                    <el-tab-pane v-for="(cls, index) in classes" :key="index" :label="cls.name" :name="index" style="width: 100%; height: 100%;">
                        <class-info :class-t="cls" :course-id="course.id" :joined="joined"></class-info>
                    </el-tab-pane>
                </el-tabs>
            </el-container>

        </div>
    </div>
</template>

<script>
import mdText from "@/pages/education/components/mdText/index.vue"
import CourseCover from "@/pages/education/components/courseCover/index.vue";
import {getCourseData, getUserRealName} from "@/pages/education/api";
import CourseButton from "@/pages/education/components/courseButton/index.vue";
import classList from "@/pages/education/components/classList/index.vue";
import updateCoverButton from "@/pages/education/components/updateCoverButton/index.vue";
import {findClassAPI} from "@/pages/education/components/findClassButton/api/api";
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import addTeacherToCourseButton from "@/pages/education/components/addTeacherToCourseButton/index.vue";
import addTeacherToClassButton from "@/pages/education/components/addTeacherToClassButton/index.vue";
import UpdateCourseButton from "@/pages/education/components/updateCourseButton/index.vue";
import store from "@/store/store";
import createClassButton from "@/pages/education/components/createClassButton/index.vue";
import {getUserName} from "@/pages/education/api";
import classInfo from "@/pages/education/components/classInfo/index.vue";
import {fetchTopic} from "@/pages/blog/api";

export default {
    name: "courseInfo",
    components: {
        mdText,
        createClassButton,
        UpdateCourseButton, addTeacherToClassButton,
        addTeacherToCourseButton,
        navigationBar,
        classInfo,
        CourseButton,
        CourseCover,
        updateCoverButton,
    },
    data() {
        return{
            showChild: false,
            detailContent: String,
            classes: null,
            course: {
                id: null,
                name: null,
                credits: null,
                introduction: null,
                evaluation: null,
                cover: null,
                syllabus: null,
                hasPermission: null,
            },
            showClassList: false,
            currentClass: 0,
            joined: false,
            ResizeObserver: null,
        }
    },

    mounted() {
        this.pullCourseData();
    },
    props: ['courseId'],
    methods: {
        async pullCourseData(){
            const course_data = await getCourseData(this.$route.params.courseId, false);
            // console.log(course_data);
            if(course_data === null) {
                this.$router.push('/education');
                return ;
            }
            this.course.id = course_data.id;
            this.course.name = course_data.name;
            this.course.credit = course_data.credit;
            this.course.introduction = course_data.introduction;
            this.course.evaluation = course_data.evaluation;
            this.course.cover = course_data.cover;
            this.course.syllabus = course_data.syllabus;
            this.course.hasPermission = course_data.hasPermission;
            await this.pullClasses();
        },
        async pullClasses() {
            this.joined = false
            this.classes = await findClassAPI(this.course.id);
            for (let i = 0; i < this.classes.length; ++i) {
                for(let j = 0; i < this.classes[i].teachers[j]; j++) {
                    const nameT = await getUserRealName(this.classes[i].teachers[j]);
                    this.classes[i].teachers[j] = nameT;
                }
            }
            const response = await this.$http.get(
              `course/getMyClasses`,{
              'Content-Type': 'application/x-www-form-urlencoded',
            });
            let myClasses = response.data.data;
            for (let i = 0; i < this.classes.length; ++i) {
                for (let j = 0; j < myClasses.length; ++j) {
                    if (this.classes[i].id === myClasses[j].id) {
                        this.joined = true;
                        break;
                    }
                }
            }
        },
        async toArticles(courseName) {
            let res = '/blog/articles/';
            // console.log(courseName);
            let blogId = await fetchTopic(courseName);
            // console.log(blogId);
            res = res + blogId + "/" + courseName;
            // res = '/blog/articles/21/2024秋 离散化学';
            // console.log(res);
            this.$router.push(res);
        },
    },
    computed: {
        isTeacher() {
            return store.getters.getEduIdentity === 'teacher';
        }
    }
}
</script>

<style scoped>
.course-card {
    display: flex;
    flex-direction: column;
    width: 90%;

    margin: 0 auto;
    border-radius: 10px;
    min-height: fit-content;
}

.course-layout {
    display: flex;
    min-height: fit-content;
}

.course-image {
    width: 200px; /* Adjust as needed */
    height: 200px; /* Adjust as needed */
    object-fit: cover;
    margin-right: 20px; /* Spacing between image and content */
}

.course-details {
    flex: 1; /* Allow details to occupy remaining space */
}

.course-title {
    margin: 0;
    font-size: 40px;
    font-weight: bold;
}

button {
    height: 25px;
    margin: 5px 0;

}

p {
    margin: 5px 0;
    font-size: 20px;
    text-align: left;
}

div {
    margin: 5px 0;
    font-size: 25px;
}

a {
    color: blue;
    text-decoration: underline;
}

.button-outline {
    display: inline-block;
    padding: 8px 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background-color: rgba(240, 240, 240, 0.9);
    color: #333;
    text-align: center; /* 文字水平居中 */
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
}

.button-outline:hover {
    background-color: #e0e0e0;
    color: #000;
}

.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.first-button {
    padding: 10px 30px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transition: background-color 0.3s ease;
    font-size: 20px;
    height: 30px;
}

.first-button:hover {
    background-color: #0056b3;
}

.button-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center; /* 可选：水平居中排布 */
}

.toArticles {
    background-color: #1ae8e8;
    border: none;
    color: white;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.toArticles:hover{
    background-color: rgba(44, 109, 199, 0.9);
}
</style>

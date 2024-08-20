<template>
    <div class="course-list">
        <button class="close-button" @click="closeCourseList">X</button>
        <h2>“{{findName}}”相关课程查询结果</h2>
        <table>
            <thead>
            <tr>
                <th>课程名称</th>
                <th>课程编号</th>
                <th>课程简介</th>
                <th>课程详细信息</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="course in courses" :key="course.id">
                <td>{{ course.name }}</td>
                <td>{{ course.id }}</td>
                <td>{{ course.introduction }}</td>
                <td>
                    <a :href="'/education/courses/' + course.id" target="_blank" rel="noopener noreferrer">
                        查看详情
                    </a>
                    <button @click="toArticles(course.name)">前往专区</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import {fetchTopic} from "@/pages/blog/api";

export default {
    name: 'CourseList',
    props: {
        courses: Array,
        findName: null,
    },
    methods: {
        closeCourseList() {
            this.$emit('close-course-list');
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
        }
    },
};
</script>

<style scoped>
.course-list {
    position: relative;
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: rgba(158, 237, 255, 0.4);
    opacity: 80%;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    padding: 8px;
    border: 1px solid #ccc;
    text-align: left;
}

th {
    background-color: #f2f2f2;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: red;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 50%;
}

a {
    background-color: #1ae8e8;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 12px;
}

a:hover {
    background-color: rgba(44, 109, 199, 0.9);
}
button {
    background-color: #1ae8e8;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 12px;
}

button:hover {
    background-color: rgba(44, 109, 199, 0.9);
    color: white;
}

</style>

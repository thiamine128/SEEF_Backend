<template>
    <div class="course-list">
        <h2>查询结果</h2>
        <div v-for="course in courses" :key="course.id">
            <h3>{{ course.name }}</h3>
            <p>教师：{{ course.teacher }}</p>
            <p>学分：{{ course.credits }}</p>
            <p>课程大纲：{{ course.outline }}</p>
            <a :href="course.url" target="_blank" rel="noopener noreferrer">
                查看详情
            </a>
        </div>
    </div>
</template>

<script>
export default {
    name: 'courseList',
    props: {
        courses: Array,
    },
};
</script>

<style scoped>
.course-list {
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #f1eeb7;
    opacity: 80%;
}

.course-item {
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
    padding: 10px;
}

h3 {
    margin: 0;
}

a {
    text-decoration: none;
    color: #007bff;
}
</style>

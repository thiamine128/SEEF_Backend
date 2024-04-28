<template>
    <div class="course-card">
        <div class="course-image">
            <img :src="course.image" alt="Course image">
        </div>
        <div class="course-info">
            <div class="course-title">课程名称：{{ course.title }}</div>
            <div class="course-teacher">任课教师：{{ course.teacher }}</div>
            <div class="course-credit">学分：{{ course.credit }}</div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        course: Object
    }
}
</script>

<style scoped>
.course-card {
    width: 25%;
    margin-bottom: 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    background-color: #47a3c4;
}

.course-image {
    height: 150px;
    margin-bottom: 10px;
}

.course-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.course-info {
    font-size: 16px;
}

.course-title {
    font-weight: bold;
    margin-bottom: 5px;
}

.course-teacher {
    margin-bottom: 5px;
}

.course-credit {
    color: #333;
}
</style>

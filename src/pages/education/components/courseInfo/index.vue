<template>
    <div class="course-card">
        <div class="course-layout">
            <div class="course-image">
<!--                {{course.image}}-->
<!--                <img :src="course.image" width=400 :alt="course.image">-->
<!--                不知道为什么，图片就是失败，先传一个数据库的-->
                <img src="../../../../assets/education/course/数据库.jpg" width=400 :alt="course.image">
            </div>
            <div class="course-details">
                <h2>{{ course.name }}</h2>
                <p><b>任课教师：</b> {{ course.teacher }}</p>
                <p><b>上课时间：</b> {{ course.schedule }}</p>
                <p><b>上课地点：</b> {{ course.location }}</p>
                <p><b>学分：</b> {{ course.credits }}</p>
                <p><b>考核方式：</b> {{ course.assessment }}</p>
                <p><b>大纲:</b> <a :href="course.syllabus" target="_blank">View Syllabus</a></p>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "courseInfo",
    props: {
        course: {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped>
.course-card {
    display: flex;
    flex-direction: column;
    width: 80%;
    padding: 15px;
    margin: 0 auto;
    border: 5px solid #add8e6; /* Light blue border */
    border-radius: 10px; /* Rounded corners */
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

h2 {
    margin: 0;
    font-size: 40px;
    font-weight: bold;
}

p {
    margin: 5px 0;
    font-size: 25px;
}

a {
    color: blue;
    text-decoration: underline;
}
</style>

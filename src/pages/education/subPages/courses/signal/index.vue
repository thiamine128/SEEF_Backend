<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course"/>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
export default {
    name: "signal",
    components: {
        navigationBar, courseInfo
    },
    data(){
        return {
            course: {
                name: "信号处理基础",
                teacher: "胡峻林",
                schedule: "1~16周周一，67节",
                location: "主南303",
                credits: 2,
                assessment: "平时作业+期末考试",
                image: "../../../../assets/education/course/信号处理基础.png",
                detailedContent: '# 信号处理基础课程大纲\n' +
                    '\n' +
                    '## 课程简介\n' +
                    '本课程旨在介绍信号处理的基本概念、原理和应用，帮助学生掌握信号处理的基本理论和技术，为进一步学习和应用信号处理领域奠定基础。\n' +
                    '\n' +
                    '## 课程目标\n' +
                    '- 理解信号与系统的基本概念和数学描述\n' +
                    '- 掌握常见信号的特征分析方法\n' +
                    '- 理解常见的信号处理技术和算法\n' +
                    '- 能够应用信号处理技术解决实际问题\n' +
                    '\n' +
                    '## 主要内容\n' +
                    '1. 信号与系统基础\n' +
                    '   - 信号的分类与性质\n' +
                    '   - 离散时间信号与连续时间信号\n' +
                    '   - 系统的概念与分类\n' +
                    '\n' +
                    '2. 时域分析\n' +
                    '   - 傅里叶级数与傅里叶变换\n' +
                    '   - 采样定理与重建\n' +
                    '   - 离散时间信号的时域分析\n' +
                    '\n' +
                    '3. 频域分析\n' +
                    '   - 傅里叶变换与频谱分析\n' +
                    '   - 滤波器设计与频域滤波\n' +
                    '   - 快速傅里叶变换（FFT）算法\n' +
                    '\n' +
                    '4. 信号处理技术与应用\n' +
                    '   - 数字滤波器设计与实现\n' +
                    '   - 时频分析方法\n' +
                    '   - 语音信号处理基础\n' +
                    '   - 图像处理基础\n' +
                    '\n' +
                    '## 教学方法\n' +
                    '- 理论讲授\n' +
                    '- 数学建模与分析\n' +
                    '- 编程实践\n' +
                    '- 实验操作\n' +
                    '- 课程项目\n' +
                    '\n' +
                    '## 考核方式\n' +
                    '- 平时表现\n' +
                    '- 课堂互动\n' +
                    '- 课程项目报告\n' +
                    '- 期末考试\n' +
                    '\n'
                // image: "@/assets/education/course/信号处理基础.png"
            }
        }
    }
}
</script>

<style scoped>
.navigationBar {
    width: 100%;
    height: 90px;
    align-self: center;
    background-color: rgb(102, 152, 228);
}
.left-main {
    width: 500px;
    height: 500px;
}
.right-main {
    width: 500px;
    height: 800px;
}
.bg-container {
    background: url('@/assets/education/education_bg.jpg');
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}

.content-container{
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 20px;
    width: 1200px;
}
</style>
<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course"/>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
export default {
    name: "softwareEngineer",
    components: {
        navigationBar, courseInfo
    },
    data(){
        return {
            course: {
                name: "软件工程",
                teacher: "杨溢龙",
                schedule: "1~16周周二，345节",
                location: "主南405",
                credits: 4,
                assessment: "平时作业+大作业+期末考试",
                image: "../../../../assets/education/course/软件工程.png",
                detailedContent: '# 软件工程课程大纲\n' +
                    '\n' +
                    '## 课程简介\n' +
                    '本课程旨在介绍软件工程的基本理论、方法和实践，帮助学生了解软件开发的整体过程和规范，掌握软件工程的基本原则和技术，为日后的软件开发和项目管理提供基础知识。\n' +
                    '\n' +
                    '## 课程目标\n' +
                    '- 理解软件工程的基本概念和发展历程\n' +
                    '- 掌握软件开发的生命周期和常用方法\n' +
                    '- 能够运用软件工程的原理和技术进行项目管理和团队协作\n' +
                    '- 了解软件质量保证和软件测试的基本方法\n' +
                    '\n' +
                    '## 主要内容\n' +
                    '1. 软件工程概述\n' +
                    '   - 软件工程的定义和范畴\n' +
                    '   - 软件危机与软件工程的发展历程\n' +
                    '   - 软件工程的基本原则和特点\n' +
                    '\n' +
                    '2. 软件开发过程\n' +
                    '   - 软件开发生命周期模型\n' +
                    '   - 需求分析与系统设计\n' +
                    '   - 软件构建与测试\n' +
                    '   - 软件维护与更新\n' +
                    '\n' +
                    '3. 软件工程方法与工具\n' +
                    '   - 敏捷开发方法\n' +
                    '   - 面向对象分析与设计\n' +
                    '   - UML建模工具的使用\n' +
                    '   - 版本控制与配置管理\n' +
                    '\n' +
                    '4. 软件质量保证与测试\n' +
                    '   - 质量管理体系与标准\n' +
                    '   - 软件测试的基本概念和方法\n' +
                    '   - 自动化测试工具的使用\n' +
                    '\n' +
                    '## 教学方法\n' +
                    '- 理论讲授\n' +
                    '- 案例分析\n' +
                    '- 项目实践\n' +
                    '- 小组讨论\n' +
                    '- 软件开发工具操作\n' +
                    '\n' +
                    '## 考核方式\n' +
                    '- 平时作业\n' +
                    '- 课堂互动\n' +
                    '- 课程项目报告\n' +
                    '- 期末考试\n' +
                    '\n'
                // image: "@/assets/education/course/数据库.jpg"
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
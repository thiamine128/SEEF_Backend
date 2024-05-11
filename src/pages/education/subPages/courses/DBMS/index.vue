<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course"/>

</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
export default {
    name: "DBMS",
    components: {
        navigationBar, courseInfo
    },
    data(){
        return {
            course: {
                name: "DBMS",
                teacher: "黄坚",
                schedule: "1~16周周一，345节",
                location: "主南205",
                credits: 4,
                assessment: "平时作业+上机+期中大作业+期末",
                image: "../../../../assets/education/course/数据库.jpg",
                detailedContent: '# 数据库课程大纲\n' +
                    '\n' +
                    '## 课程简介\n' +
                    '本课程旨在介绍数据库的基本概念、设计原则和应用技术，帮助学生掌握数据库管理系统的基本知识和技能，为日后的数据库应用和开发打下基础。\n' +
                    '\n' +
                    '## 课程目标\n' +
                    '- 理解数据库系统的基本组成和工作原理\n' +
                    '- 掌握关系数据库设计和规范化方法\n' +
                    '- 熟悉SQL语言的基本操作和高级应用\n' +
                    '- 能够设计和实现简单的数据库应用系统\n' +
                    '\n' +
                    '## 主要内容\n' +
                    '1. 数据库基础\n' +
                    '   - 数据与信息的基本概念\n' +
                    '   - 数据库系统的组成和体系结构\n' +
                    '   - 数据库管理系统（DBMS）的功能和特点\n' +
                    '\n' +
                    '2. 数据建模与规范化\n' +
                    '   - 实体-关系模型（ER模型）基础\n' +
                    '   - 数据库设计的规范化理论与方法\n' +
                    '   - 数据库设计工具的使用\n' +
                    '\n' +
                    '3. SQL语言\n' +
                    '   - SQL基本操作（增删改查）\n' +
                    '   - 复杂查询与嵌套查询\n' +
                    '   - SQL的数据操纵语言（DML）和数据定义语言（DDL）\n' +
                    '\n' +
                    '4. 数据库应用开发\n' +
                    '   - 数据库编程基础\n' +
                    '   - 数据库安全与权限管理\n' +
                    '   - 数据库性能优化与索引设计\n' +
                    '\n' +
                    '## 教学方法\n' +
                    '- 理论讲授\n' +
                    '- 实例演示与练习\n' +
                    '- 课程项目\n' +
                    '- 实际数据库应用案例分析\n' +
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
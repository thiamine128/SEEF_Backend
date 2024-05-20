<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course"/>
</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import router from "@/router";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
import async from "async";
export default {
    // const router=useRouter(),
    name: "courses",
    components: {
        courseInfo,
        navigationBar
    },
    methods:{
    },
    data(){
        return {
            course: {
                name: "none",
                teacher: "none",
                schedule: "none",
                location: "none",
                credits: 0,
                assessment: "none",
                image: "../../../../assets/education/course/信号处理基础.png",
                detailedContent: 'none'
                // image: "@/assets/education/course/信号处理基础.png"
            }
        }
    },
    mounted() {
        console.log(this.$route.params.EnName);

        function getCourseByEnName(EnName) {
            if(EnName === "signal") {

                return {
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
                };
            }
            else if(EnName === "softwareEngineer") {
                return {
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
            else if(EnName === "DBMS") {
                return {
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
            else if(EnName === "internetMarketing") {
                return {
                        name: "互联网营销",
                        teacher: "杨晴虹",
                        schedule: "1~16周周一，89节",
                        location: "主南303",
                        credits: 2,
                        assessment: "平时作业+课堂展示",
                        image: "../../../../assets/education/course/互联网营销.png",
                        detailedContent:
                            '# 互联网营销课程大纲\n' +
                            '\n' +
                            '## 课程简介\n' +
                            '本课程旨在介绍互联网营销的基本概念、策略和工具，帮助学生了解如何利用互联网平台进行营销推广，提升品牌知名度和销售业绩。\n' +
                            '\n' +
                            '## 课程目标\n' +
                            '- 理解互联网营销的基本原理和发展趋势\n' +
                            '- 掌握各种互联网营销工具和技术的运用\n' +
                            '- 能够制定和实施有效的互联网营销策略\n' +
                            '- 了解互联网营销在不同行业和平台上的应用案例\n' +
                            '\n' +
                            '## 主要内容\n' +
                            '1. 互联网营销概述\n' +
                            '   - 互联网营销的定义和发展历程\n' +
                            '   - 互联网营销与传统营销的区别和联系\n' +
                            '   - 互联网营销的基本原理和模式\n' +
                            '\n' +
                            '2. 互联网营销策略\n' +
                            '   - 品牌定位与传播\n' +
                            '   - 内容营销与社交媒体营销\n' +
                            '   - 搜索引擎优化（SEO）与搜索引擎营销（SEM）\n' +
                            '   - 电子邮件营销与移动营销\n' +
                            '\n' +
                            '3. 互联网营销工具与技术\n' +
                            '   - 网站建设与用户体验优化\n' +
                            '   - 数据分析与用户行为跟踪\n' +
                            '   - 社交媒体平台的应用与管理\n' +
                            '   - 互联网广告投放与效果评估\n' +
                            '\n' +
                            '4. 互联网营销案例分析\n' +
                            '   - 不同行业的互联网营销实践\n' +
                            '   - 成功的互联网营销案例解析\n' +
                            '   - 互联网营销在国际市场的应用\n' +
                            '\n' +
                            '## 教学方法\n' +
                            '- 理论讲授\n' +
                            '- 案例分析\n' +
                            '- 小组讨论\n' +
                            '- 实地考察\n' +
                            '- 课程项目\n' +
                            '\n' +
                            '## 考核方式\n' +
                            '- 平时表现\n' +
                            '- 课堂互动\n' +
                            '- 课程项目报告\n' +
                            '- 期末考试\n' +
                            '\n' +
                            '以上是一个简单的互联网营销课程大纲，你觉得如何？'
                }
            }
            else {
                return {
                    name: "没有课程",
                    teacher: "没有老师",
                    schedule: "没有时间",
                    location: "没有教室",
                    credits: 0,
                    assessment: "没有考核",
                    image: "../../../../assets/education/course/信号处理基础.png",
                    detailedContent: '没有大纲'
                }
            }
        }
        this.course = getCourseByEnName(this.$route.params.EnName);
    },
}
</script>

<style scoped>
.navigationBar {
    width: 100%;
    height: 90px;
    align-self: center;
    background-color: rgb(102, 152, 228);
}
.main {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 16px;
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
</style>

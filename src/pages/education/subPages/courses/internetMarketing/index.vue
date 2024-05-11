<template>
    <div class="bg-container"/>
    <navigation-bar/>
    <course-info :course="course" />



</template>

<script>
import navigationBar from "@/pages/education/components/navigationBar/index.vue";
import courseInfo from "@/pages/education/components/courseInfo/index.vue";
export default {
    name: "internetMarketing",
    components: {
        navigationBar, courseInfo
    },
    data(){
        return {
            course: {
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
    },
    methods: {
        // showDetail() {
        //     this.show = true;
        // },
        // closeDetail() {
        //     this.show = false;
        // }
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

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
}
</style>
<template>
  <el-container class="layout-container">
    <el-aside width="300px">
      <el-scrollbar>
        <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        default-openeds="['1','4']">
          <el-sub-menu index="1">
            <template #title>
              机器学习从入门到进阶
            </template>
            <el-menu-item-group>
              <template #title>Group1</template>
              <el-menu-item index="1-1">
                <el-icon><Setting/></el-icon>课程概述
              </el-menu-item>
              <el-menu-item index="1-2">
                <el-icon><View/></el-icon>课程大纲
              </el-menu-item>
              <el-sub-menu index="1-3">
              <template #title><el-icon><Message/></el-icon>课程作业</template>
              <el-menu-item index="1-3-1">
                <el-icon><EditPen/></el-icon>提交
              </el-menu-item>
              <el-menu-item index="1-3-2">
                <el-icon><CircleCheck/></el-icon>批改
              </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="1-4">
                <template #title>
                  <el-icon><IconMenu/></el-icon>课程资料
                </template>
                <el-menu-item index="1-4-1">PPT</el-menu-item>
                <el-menu-item index="1-4-2">课程笔记</el-menu-item>
              </el-sub-menu>
              </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              软件工程
            </template>
            <el-menu-item-group>
              <template #title>Group2</template>
              <el-menu-item index="2-1">
                <el-icon><Setting/></el-icon>课程概述
              </el-menu-item>
              <el-menu-item index="2-2">
                <el-icon><View/></el-icon>课程大纲
              </el-menu-item>
              <el-sub-menu index="2-3">
              <template #title><el-icon><Message/></el-icon>课程作业</template>
              <el-menu-item index="2-3-1">
                <el-icon><EditPen/></el-icon>提交
              </el-menu-item>
              <el-menu-item index="2-3-2">
                <el-icon><CircleCheck/></el-icon>批改
              </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="2-4">
                <template #title>
                  <el-icon><IconMenu/></el-icon>课程资料
                </template>
                <el-menu-item index="2-4-1">PPT</el-menu-item>
                <el-menu-item index="2-4-2">课程笔记</el-menu-item>
              </el-sub-menu>
              </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              英语
            </template>
            <el-menu-item-group>
              <template #title>Group3</template>
              <el-menu-item index="3-1">
                <el-icon><Setting/></el-icon>课程概述
              </el-menu-item>
              <el-menu-item index="3-2">
                <el-icon><View/></el-icon>课程大纲
              </el-menu-item>
              <el-sub-menu index="3-3">
              <template #title>
                <el-icon><Message/></el-icon>课程作业</template>
              <el-menu-item index="3-3-1">
                <el-icon><EditPen/></el-icon>提交
              </el-menu-item>
              <el-menu-item index="3-3-2">
                <el-icon><CircleCheck/></el-icon>批改
              </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3-4">
                <template #title>
                  <el-icon><IconMenu/></el-icon>课程资料
                </template>
                <el-menu-item index="3-4-1">PPT</el-menu-item>
                <el-menu-item index="3-4-2">课程笔记</el-menu-item>
              </el-sub-menu>
              </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="4">
            <template #title>
              操作系统
            </template>
            <el-menu-item-group>
              <template #title>Group4</template>
              <el-menu-item index="4-1">
                <el-icon><Setting/></el-icon>课程概述
              </el-menu-item>
              <el-menu-item index="4-2">
                <el-icon><View/></el-icon>课程大纲
              </el-menu-item>
              <el-sub-menu index="4-3">
              <template #title><el-icon><Message/></el-icon>课程作业</template>
              <el-menu-item index="4-3-1">
                <el-icon><EditPen/></el-icon>提交
              </el-menu-item>
              <el-menu-item index="4-3-2">
                <el-icon><CircleCheck/></el-icon>批改
              </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="4-4">
                <template #title>
                  <el-icon><IconMenu/></el-icon>课程资料
                </template>
                <el-menu-item index="4-4-1">PPT</el-menu-item>
                <el-menu-item index="4-4-2">课程笔记</el-menu-item>
              </el-sub-menu>
              </el-menu-item-group>
          </el-sub-menu>
        </el-menu>

      </el-scrollbar>



    </el-aside>
    <el-container>
    <el-header>

      <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    @select="handleSelect"
  >
    <el-menu-item index="1">Processing Center</el-menu-item>
    <el-sub-menu index="2">
      <template #title>Workspace</template>
      <el-menu-item index="2-1">item one</el-menu-item>
      <el-menu-item index="2-2">item two</el-menu-item>
      <el-menu-item index="2-3">item three</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>item four</template>
        <el-menu-item index="2-4-1">item one</el-menu-item>
        <el-menu-item index="2-4-2">item two</el-menu-item>
        <el-menu-item index="2-4-3">item three</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="3" disabled>Info</el-menu-item>
    <el-menu-item index="4">Orders</el-menu-item>
  </el-menu>
  <div class="h-6" />
  <el-menu
    :default-active="activeIndex2"
    class="el-menu-demo"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
    @select="handleSelect"
  >
    <el-menu-item index="1">Processing Center</el-menu-item>
    <el-sub-menu index="2">
      <template #title>Workspace</template>
      <el-menu-item index="2-1">item one</el-menu-item>
      <el-menu-item index="2-2">item two</el-menu-item>
      <el-menu-item index="2-3">item three</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>item four</template>
        <el-menu-item index="2-4-1">item one</el-menu-item>
        <el-menu-item index="2-4-2">item two</el-menu-item>
        <el-menu-item index="2-4-3">item three</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <el-menu-item index="3" disabled>Info</el-menu-item>
    <el-menu-item index="4">Orders</el-menu-item>
  </el-menu>


    </el-header>

    <el-main>

      <div class="wrap">
      <div class="web-course-banner">
        <div class="container">
          <div class="title" style="text-align: center;">
            <img src="../../../../assets/education/courseInfo/logo.png" height="67" width="67" alt="">
            <h1 class="course-title" >机器学习从入门到进阶</h1>
          </div >
          <span class="course-text" style="text-align: center;"></span>
          <div class="course-list">
            <ul >
              <li class="detail-item">
                难度：初级
              </li>
              <li class = detail-item>时长：32小时</li>
              <li class = detail-item>学习人数：632人</li>
              <li class = detail-item>评分 10.0</li>
              <li class = detail-item>授课老师 XXX</li>
              <li class = detail-item>学分 2.0</li>
            </ul>
          </div>
        </div>
      </div>
      <!-- 代码略 -->
    </div>





    <div class="course-summary">
        <h2>课程概述</h2>
        <a href="https://www.flaticon.com/free-icons/productivity" title="productivity icons">Productivity icons created by juicy_fish - Flaticon</a>
        <p>这里是机器学习课程的简要介绍和概述内容。可以在这里添加更多描述课程的文字内容。</p>
    </div>


    <div class="course-outline" style="text-align: left;">
    <h2>课程大纲</h2>
    <ul>
        <li>第一章：介绍机器学习</li>
        <li>第二章：数据预处理</li>
        <li>第三章：监督学习</li>
        <!-- 其他章节 -->
    </ul>
</div>

<div class="course-assignments" style="text-align:  left;">
    <h2>课程作业</h2>
    <p>这里是学生需要完成的作业内容。</p>
</div>

<div class="course-materials" style="text-align:  left;">
    <h2>课程资料</h2>
    <ul>
        <li>教学视频</li>
        <li>课件下载</li>
        <li>相关阅读材料</li>
        <!-- 其他资料 -->
    </ul>
</div>
</el-main>




  </el-container>

</el-container>

</template>


<script  setup>

import { Menu as IconMenu, Message, Setting, EditPen , Aim , View , CircleCheck  } from '@element-plus/icons-vue'
import { ref } from 'vue'

const activeIndex = ref('1')
const activeIndex2 = ref('1')
const handleSelect = (key, keyPath) => {
  // console.log(key, keyPath)
}
const handleOpen = (key, keyPath) => {
  // console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  // console.log(key, keyPath)
}




</script>



  <style lang="css" scoped>

  .layout-container .el-aside {
    color: var(--el-text-color-primary);
    background: var(--el-color-primary-light-8);
  }

  .layout-container .el-main {
  padding: 0;
}
.layout-container .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}


    .wrap{
      width: 100%;
    }
    .web-course-banner{
      width: 100%;
      height: 700px;
      background:  rgba(255, 255, 255, 0.8) url(../../../../assets/education/courseInfo/back1.jpg) no-repeat;
      background-size: cover;
      text-align: center;
      overflow: hidden;
    }
    .container{
      width: 1200px;
      margin: 182px auto 0;
      text-align: left;
    }
    .container img{
      vertical-align: middle;
    }
    .container h1{
      display: inline-block;
      font-size: 48px;
      color: #fefcfc;
      letter-spacing: .37px;
      margin-left: 40px;
      font-family: sans-serif;
      font-weight: 500;
      line-height: 1.1;
      position: relative;
      top: 10px;
    }
    .course-text{
      width: 464px;
      display: inline-block;
      font-size: 22px;
      color: #fefcfc;
      letter-spacing: .17px;
      line-height: 36px;
      margin-top: 33px;
    }
    .course-list{
      width: 100%;
    }
    .course-list ul{
      margin-top: 63px;
      display: flex;
      align-items: center;
      justify-content: flex-start;
    }
    .course-list ul li.detail-item{
      font-size: 18px;
      color: #fefcfc;
      letter-spacing: .74px;
      height: 26px;
      padding: 0px 20px;
      margin-left: 20px;
    }


    .course-summary {
        width: 1200px;
        text-align: left
    }

    .course-summary p{
        font: 1em sans-serif;
    }
  </style>



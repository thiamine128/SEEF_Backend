<template>
    <div class="bg-container"/>
    <div :class="{setNavBar: step, setNavBarOp: !step}" >
        <nav-bar :class="{navColorStyle: notHead, navColorStyleHead: !notHead}" ></nav-bar>
    </div>
    <div class="view-set-margin">
        <router-view ref="views"/>
    </div>
    <blog-bottom/>

</template>

<script>
import navBar from "@/pages/blog/components/navBar/index.vue";
import blogBottom from "@/pages/blog/components/blogBottom/index.vue";
import {reactive} from "vue";

export default {
    name: "blog",
    components: {
        navBar, blogBottom
    },
    data(){
        const data=reactive({
            oldScrollTop:0,
        });
        const scrolling=()=>{
            // 滚动条距文档顶部的距离
            let scrollTop =window.pageYOffset ||document.documentElement.scrollTop ||document.body.scrollTop;
            // 滚动条滚动的距离
            let scrollStep = scrollTop - data.oldScrollTop;
            //console.log("header 滚动距离 ", scrollTop);
            // 更新——滚动前，滚动条距文档顶部的距离
            data.oldScrollTop = scrollTop;
            //变量windowHeight是可视区的高度
            let windowHeight =
                document.documentElement.clientHeight || document.body.clientHeight;
            //变量scrollHeight是滚动条的总高度
            let scrollHeight =
                document.documentElement.scrollHeight || document.body.scrollHeight;
            //滚动条到底部的条件
            if (scrollTop + windowHeight === scrollHeight) {
                //到达底部
            }
            if (scrollStep < 0) {
                //向上滚动
                this.step = true;
            } else {
                //向下滚动
                this.step = false;
                if (!this.notHead) this.notHead = true;
            }
            // 判断是否到了最顶部
            if (scrollTop <= 0) {
                console.log("header 到了最顶部")
                this.notHead = false;
            }
        };
      return{
          data, scrolling, notHead: false, step: true
      }
    },
    mounted() {
        window.addEventListener("scroll", this.scrolling);
    },
    unmounted() {
        window.removeEventListener("scroll", this.scrolling);
    }
}

</script>

<style scoped>
.bg-container {
    background: url('@/assets/blog/blog_bg.png');
    opacity: 0.8;
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}
.setNavBar{
    width: 100%;
    position: fixed;
    z-index: 1;
    opacity: 1.0;
    transition: opacity 1s;
}
.setNavBarOp{
    width: 100%;
    position: fixed;
    z-index: 1;
    opacity: 0;
    transition: opacity 1s;
}
.view-set-margin{
    padding-top: 87.8px;
    z-index: 0;
    display: flex;
    flex-direction: column;
    align-items: center;

}
.navColorStyle{
    background-color: rgba(0.34, 0.22, 0.28, 0.6);
    transition: background-color 1s ease ;
}
.navColorStyleHead{
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
    transition: background-color 1s ease ;
}
</style>

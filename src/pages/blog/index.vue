<template>
    <div class="bg-container"/>
    <div class="set-nav-bar" >
        <nav-bar :class="{navColorStyle: notHead, navColorStyleHead: !notHead}" ></nav-bar>
    </div>
    <div class="view-set-margin">
        <router-view/>
    </div>

</template>

<script>
import navBar from "@/pages/blog/components/navBar/index.vue";
import {reactive} from "vue";

export default {
    name: "blog",
    components: {
        navBar
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
            console.log("header 滚动距离 ", scrollTop);
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
                //你想做的事情
                console.log("header  你已经到底部了");
            }
            if (scrollStep < 0) {
                console.log("header 滚动条向上滚动了！");
            } else {
                console.log("header  滚动条向下滚动了！");
                if (!this.notHead) this.notHead = true;
            }
            // 判断是否到了最顶部
            if (scrollTop <= 0) {
                console.log("header 到了最顶部")
                this.notHead = false;
            }
        };
      return{
          data, scrolling, notHead: false
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
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}
.set-nav-bar{
    width: 100%;
    position: fixed;
    z-index: 1;
}
.view-set-margin{
    padding-top: 90px;
    z-index: 0;
}
.navColorStyle{
    background-color: rgba(11, 11, 11, 0.8);
    transition: background-color 1s ease ;
}
.navColorStyleHead{
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
    transition: background-color 1s ease ;
}
</style>

<template>
    <div class="bg-container">

        <img  class="rotating-image" alt="404" src="@/assets/blog/software_circle.png">

    </div>

    <div :class="{floatSet: floatComment, floatSetNone: !floatComment}" >

        <comment-textarea v-if="floatSelect == 1 || floatSelect == 3" style="margin: auto" :blog-id="blogId"
          @cancelFloat="cancelFloatWindow" :holder-content="holderText"
          :reply-data="replyData" :sel="floatSelect" />

        <article-list v-if="floatSelect == 2" style="width: 50%; margin: auto"
              height-set="510px" r-title="选择专区"
              :list-set="sectionList" select="section" :total-page="totalPage"
              @page-change="getSections" @cancelFloat="cancelFloatWindow"
              @sel="selectTopic" :editor-set="true" ref="selectSectionList"
              ></article-list>

        <space-create v-if="floatSelect == 4" style="margin: auto" create-name="Space"
        @cancelFloat="cancelFloatWindow" />

        <folder-select v-if="floatSelect == 5" style="margin: auto" @cancelFloat="cancelFloatWindow"
        @sel="selectCategory" folder-name="选择文章分区" />

        <space-create v-if="floatSelect == 6" style="margin: auto" create-name="Category"
          @cancelFloat="cancelFloatWindow" />

        <folder-select v-if="floatSelect == 7" style="margin: auto" @cancelFloat="cancelFloatWindow"
        @sel="selectCategory" folder-name="选择收藏夹" />

    </div>

    <el-backtop :right="50" :bottom="50" />

    <div :class="{setNavBar: step, setNavBarOp: !step}" @mouseenter="step = true">
        <nav-bar :class="{navColorStyle: notHead, navColorStyleHead: !notHead}"></nav-bar>
    </div>
    <div class="view-set-margin">

        <router-view @callFloat="callFloatWindow" :section-name="sectionName" :category-name="categoryName"
             :topic-id="topicId"  :category-id="categoryId" ref="views"/>

    </div>
    <blog-bottom/>

</template>

<script>
import navBar from "@/pages/blog/components/navBar/index.vue";
import blogBottom from "@/pages/blog/components/blogBottom/index.vue";
import {reactive} from "vue";
import CommentTextarea from "@/pages/blog/components/commentTextarea/index.vue";
import articleList from "@/pages/blog/components/articleList/index.vue";
import {callError} from "@/callMessage";
import SpaceCreate from "@/pages/blog/components/spaceCreate/index.vue";
import FolderSelect from "@/pages/blog/components/folderSelect/index.vue";

export default {
    name: "blog",
    components: {
        FolderSelect,
        SpaceCreate,
        articleList,
        CommentTextarea,
        navBar, blogBottom
    },
    data(){

        //滑动响应式组件
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
                this.notHead = false;
            }

        };
      return{

          data, scrolling, notHead: false, step: true,
          floatComment: false, holderText: '', floatSelect: 1,
          sectionList: [],
          totalPage: 10,
          sectionName: '选择专区',
          topicId: -1,
          blogId: -1,
          replyData: {},

          categoryName: '选择类别',
          categoryId: -1

      }
    },
    mounted() {
        window.addEventListener("scroll", this.scrolling); //加入监听器
    },
    unmounted() {
        window.removeEventListener("scroll", this.scrolling); //去掉监听器
    },
    methods:{

        callFloatWindow(holder, sel, data = {}){
            this.floatSelect = sel;
            this.holderText = holder;
            this.floatComment = true;
            if (sel == 2) this.getSections(1);
            else if (sel == 1) this.blogId = data.blogId;
            else if (sel == 3) this.replyData = data;
        },

        selectTopic(id, topicName){
            if (topicName.length > 4) this.sectionName = topicName.slice(0, 4)+'...';
            else this.sectionName = topicName;
            this.topicId = id;
            //console.log('topic: '+id+ " "+topicName);
            this.cancelFloatWindow();
        },

        selectCategory(id, cName){
            if (cName.length > 4) this.categoryName = cName.slice(0, 4)+'...';
            else this.categoryName = cName;
            this.categoryId = id;
            this.cancelFloatWindow();
        },

        cancelFloatWindow(){
            if (this.floatComment) this.floatComment = false;
        },

        async getSections(pageNum){
            try{
                const response = await this.$http.get(`topic/pagedList?page=${pageNum}&pageSize=6`);
                //console.log(response);
                if (response.status === 200) {
                    this.sectionList = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 6);
                } else callError('网络错误');
            }catch (error){
                //callError(error);
            }
        }

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

.bg-bottom-container {
    background: url('@/assets/blog/ic0001a.png');
    opacity: 0.9;
    background-size: cover;
    height: 100vh;
    width: 100vw;
}

@keyframes rotate {
    from {
        transform: rotate(0deg);
    }
    to {
        transform: rotate(-360deg);
    }
}

.rotating-image {
    position: absolute;
    top: 0;
    left: -20%;
    height: 100%;
    animation: rotate 30s infinite linear;
}

.floatSet{
    background-color: rgba(33, 33, 33, 0.1);
    opacity: 1.0;
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: 5;
    top: 0;
    left: 0;
    display: flex;
    transition: opacity 0.4s;
}

.floatSetNone{
    background-color: rgba(33, 33, 33, 0.1);
    opacity: 0.0;
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -5;
    top: 0;
    left: 0;
    display: flex;
    transition: opacity 0.4s;
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

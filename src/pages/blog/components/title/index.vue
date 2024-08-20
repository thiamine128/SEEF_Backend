<template>
    <div :class="{titleControlInit: init_set, titleControl: !init_set}">
        <title-group :tag-list="tags" :title="title" :date-string="dateString" class="groupControl"></title-group>
    </div>
</template>

<script>

import titleGroup from "@/pages/blog/components/titleGroup/index.vue";

export default {
    name: "blogTitle",
    props: ['title', 'postTime', 'updateTime', 'tags'],
    components: {
        titleGroup
    },
    computed: {
        dateString(){
            return '发表日期：'+this.postTime+" | "+"更新日期："+this.updateTime;
        }
    },
    data(){
        return{
            init_set: true
        }
    },
    mounted() {
        document.documentElement.scrollTop = 0;
        setTimeout(()=>{
            this.init_set = false;
        }, 1000);
    }
}
</script>

<style scoped>

.titleControl {
    margin-top: 3px;
    width: 100%;
    height: 310px;
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
    display: flex;
    flex-direction: column;
    transition: height 1s;
}

.titleControlInit {
    margin-top: 3px;
    width: 100%;
    height: 100vh;
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
    display: flex;
    flex-direction: column;
    transition: height 1s;
}

.groupControl{
    margin: auto;
}
</style>

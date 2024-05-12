<template>
    <div class="title-control">
        <title-group :title="title" :date-string="dateString" class="groupControl"></title-group>
    </div>
</template>

<script>

import titleGroup from "@/pages/blog/components/titleGroup/index.vue";

export default {
    name: "blogTitle",
    props: ['title', 'postTime', 'updateTime'],
    components: {
        titleGroup
    },
    computed: {
        dateString(){
            return '发表日期：'+this.postTime+" | "+"更新日期："+this.updateTime;
        }
    }
}
</script>

<style scoped>
.title-control {
    margin-top: 3px;
    width: 100%;
    height: 310px;
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
    display: flex;
    flex-direction: column;
}
.groupControl{
    margin: auto;
}
</style>

<template>
    <blogTitle title="BUAA-SE-Wonderful" post-time="2023-12-11" update-time="2023-12-15"/>
    <div class="content-container">
        <md-field class="content-left"></md-field>
    </div>
</template>

<script>
import mdField from "@/pages/blog/components/mdField/index.vue";
import blogTitle from "@/pages/blog/components/title/index.vue";

export default {
    name: "article",
    components: {blogTitle, mdField},
}
</script>

<style scoped>
.content-container{
    display: flex;
    flex-direction: row;
    margin-top: 3px;
}
.content-left{
    width: 70%;
}
</style>

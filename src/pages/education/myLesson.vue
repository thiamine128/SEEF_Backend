<template>
    <h1>我的课程</h1>
    <div id="daohanglan">
        <button @click="myClass">我的课程</button>
        <button >课程广场</button>
        <button>我的课表</button>
    </div>
</template>

<script>
export default {
    name: "index",
    methods:{
        myClass() {
            console.log("Click");
        }
    }
}
</script>

<style scoped>
#daohanglan {
    width: 100%;
    height: 100px;
    align-self: center;
    background-color: rgb(102, 228, 228);
}
</style>

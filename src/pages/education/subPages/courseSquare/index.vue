<template>
    <h1>课程广场</h1>
    <div id="navigation">
        <button id="myClass" @click="myClass">我的课程</button>
        <button >课程广场</button>
        <button>我的课表</button>
    </div>
</template>

<script>
export default {
    name: "courseSquare",
    methods:{
        myClass() {
            console.log("Click");
        }
    }
}
</script>

<style scoped>
#navigation {
    width: 100%;
    height: 100px;
    align-self: center;
    background-color: rgb(102, 228, 228);
}
#myClass {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 8px 16px;
    border: 1px solid #ccc;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}
</style>

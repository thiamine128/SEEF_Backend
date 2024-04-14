<template>
    <div class="col">
        <div :class="{ customFont: true }">{{buttonName}}</div>
    </div>
</template>

<script>
export default {
    name: "navButton",
    props: ['buttonName']
}
</script>

<style scoped>
.col {
    margin-right: 40px;
}
.customFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 16px;
    margin-top: 33px;
}
</style>

<template>
    <el-input
            v-model="searchInput"
            placeholder="搜索你感兴趣的课程......"
            clearable class="el-input-style"
            @mouseenter="hover=true"
            @mouseleave="hover=false"
    >
        <template #suffix>
            <div class="circular-icon-container">
                <img class="icon-search" style="width: 1em; height: 1em"
                     src="@/assets/blog/magnifying-glass.png" alt="Search"/>
            </div>
        </template>
    </el-input>
</template>

<script>
export default {
    name: "searchBar",
    data(){
        return{
            searchInput:  "",
            hover: false
        }
    }
}

</script>

<style scoped>

.el-input-style {
    width: 200px;
    transition: width 0.3s ease;
    height: 40px;
    margin-right: 50px;
}

.el-input-style:hover{
    margin-right: 40px;
    width: 400px;
    transition: width 0.3s ease;
}

:deep(.el-input__wrapper) {
    margin-left: 5px;
    font-size: 15px;
    background-color: rgba(255, 255, 255, 0);/*覆盖原背景颜色，设置成透明 */
    border-radius: 50px;
}

:deep(.el-input__inner) {
    margin-left: 10px;
    color: black;
}

.circular-icon-container {
    display: inline-block;
    width: 19px; /* 调整此值以适应你的图标大小 */
    height: 19px;
    line-height: 24px;
    border-radius: 50%;
    cursor: pointer;
    transition: box-shadow 0.3s ease;
    &:hover {
        box-shadow:0 0 0 4px rgba(0, 0, 0, 0.1);
    }
    .icon-search {
        margin-top: 4px;
    }
}
</style>

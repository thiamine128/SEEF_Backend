<template>
    <div class="bottom-style">
        <div :class="{ trademark: true }">School of Software, Beihang University</div>
    </div>
</template>

<script>
export default {
    name: "blogBottom"
}
</script>

<style scoped>
.bottom-style{
    margin-top: 20px;
    width: 100%;
    height: 150px;
    display: flex;
    background-color: rgba(0.34, 0.22, 0.28, 0.4);
}
.trademark{
    font-family: 'rage', sans-serif;
    font-weight: bold;
    font-size: 30px;
    margin: auto;
    color: white;
}
</style>

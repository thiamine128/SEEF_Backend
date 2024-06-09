<template>
    <div class="title-container"
         @mouseenter="hover = true"
         @mouseleave="hover = false" @click="goto">
        <a :href="'/education/courses/' + dest" class="linkStyle">
            <div class="text">{{rName}}</div>
        </a>
    </div>
</template>

<script>
export default {
    name: "courseButton",
    props: ['rName', 'dest'],
    data(){
        return{
            hover: false
        }
    },
    methods:{
        goto(){
            this.$router.push('/education/courses/' + this.dest);
        }
    }
}
</script>

<style scoped>
.text{
    text-align: center;
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 13px;
    margin: 0 auto;
}
.title-container{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    padding: 8px;
    cursor: pointer;
    background-color: rgba(0, 0, 0, 0);
    transition: background-color 0.5s ease;
}
.linkStyle{
    text-decoration: none;
    color: #000;
    cursor: pointer;
}
.title-container:hover{
    background-color: rgba(63, 115, 147, 0.1);
    border-radius: 15px; /* Sets a 15px radius for all corners */
}
</style>

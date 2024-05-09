<template>
    <div class="frame">
        <div class="titleFont"> Post Comment </div>
        <el-input
                v-model="commentContent"
                style="width: 400px; margin-top: 10px; margin-bottom: 10px"
                :rows="5"
                type="textarea"
                :placeholder="holderContent"
        />
        <el-button style="width: 390px;">评论</el-button>
        <div/>
        <el-button style="width: 390px; margin-top: 5px" @click="cancelComment()">取消</el-button>
    </div>
</template>

<script>
export default {
    name: "commentTextarea",
    props: ['holderContent'],
    data(){
        return{
            commentContent: ''
        }
    },
    methods:{
        postComment(){

        },
        cancelComment(){
            this.$emit('cancelFloat');
        }
    }
}
</script>

<style scoped>
.frame{
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 3px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 500px;
    height: 300px;
}
.titleFont {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 28px;
    text-align: center;
    color: #4b4b4b;
}
</style>

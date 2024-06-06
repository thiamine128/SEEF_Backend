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
        <el-button style="width: 390px;" @click="postComment()">评论</el-button>
        <div/>
        <el-button style="width: 390px; margin-top: 5px" @click="cancelComment()">取消</el-button>
    </div>
</template>

<script>
import {callError, callInfo, callSuccess} from "@/callMessage";

export default {
    name: "commentTextarea",
    props: ['holderContent', 'blogId', 'replyData', 'sel'],
    data(){
        return{
            commentContent: ''
        }
    },
    methods:{

        async postComment(){

            if (this.commentContent.length > 500){
                callInfo('内容过长');
                return;
            }

            if (this.commentContent.length === 0){
                callInfo('内容不得为空');
                return;
            }

            if (this.sel == 1){
                try {
                    const response = await this.$http.post(`comment/create`, {
                        "content": this.commentContent,
                        "blogId": this.blogId
                    });
                    if (response.status === 200) {
                        callSuccess('评论成功');
                        this.cancelComment();
                        setTimeout(()=>{
                            location.reload();
                        }, 1000);
                    } else callError('网络错误');
                }catch (error){
                    callError(error);
                }
            }else if (this.sel == 3){
                try {
                    let response = null;
                    console.log('reply: ' + this.replyData.to);
                    if (this.replyData.to < 0){
                        response = await this.$http.post(`reply/create`, {
                            "content": this.commentContent,
                            "commentId": this.replyData.commentId
                        });
                    }else{
                        response = await this.$http.post(`reply/create`, {
                            "content": this.commentContent,
                            "commentId": this.replyData.commentId,
                            "to": this.replyData.to
                        });
                    }

                    if (response.status === 200) {
                        callSuccess('回复成功');
                        this.cancelComment();
                        setTimeout(()=>{
                            location.reload();
                        }, 1000);
                    } else callError('网络错误');
                }catch (error){
                    callError(error);
                }
            }

            this.cancelComment();
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

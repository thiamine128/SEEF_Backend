<template>
    <div class="create-blog">
        <div class="titleFont"> {{createName}} </div>
        <el-input v-model="spaceInput" style="width: 80%; height: 40px; margin-top: 10px" placeholder="请输入文件夹名称"/>

        <el-button style="width: 80%; margin-top: 10px" type="primary" plain @click="createSpace()">创建</el-button>
        <div style="height: 0"/>
        <el-button style="width: 80%; margin-top: 5px" plain @click="cancelCreate()">取消</el-button>
    </div>
</template>

<script>
import {callError, callInfo, callSuccess} from "@/callMessage";

export default {
    name: "spaceCreate",
    props: ['createName'],
    data(){
        return{
            spaceInput: ''
        }
    },
    methods:{

        async createSpace(){

            if (this.spaceInput.length === 0){
                callInfo('名称不得为空');
                return;
            }

            if (this.spaceInput.length > 10){
                callInfo('名称过长');
                return;
            }

            try{
                if (this.createName === 'Space'){
                    const response = await this.$http.post(
                        `space/create?category=${this.spaceInput.replace(/\s+/g, '')}`
                    );
                    if (response.status === 200){
                        callSuccess('创建分类成功');
                        this.cancelCreate();
                        setTimeout(()=>{
                            location.reload();
                        }, 1000);
                    }else callError('网络错误');
                }else if (this.createName === 'Category'){
                    const response = await this.$http.post(
                        `blog/createFavourCategory?category=${this.spaceInput.replace(/\s+/g, '')}`
                    );
                    if (response.status === 200){
                        callSuccess('创建收藏夹成功');
                        this.cancelCreate();
                        setTimeout(()=>{
                            location.reload();
                        }, 1000);
                    }else callError('网络错误');
                }

            }catch (e){
                console.log(e);
            }

        },

        cancelCreate(){
            this.$emit('cancelFloat');
        }

    }
}
</script>

<style scoped>
.create-blog{
    background-color: rgba(255, 255, 255, 0.9);
    width: 300px;
    height: 180px;
    display: flex;
    flex-direction: column;
    padding-top: 10px;
    padding-bottom: 10px;
    border-radius: 3px;
    align-items: center;
}
.titleFont {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 28px;
    text-align: center;
    color: #4b4b4b;
}
</style>

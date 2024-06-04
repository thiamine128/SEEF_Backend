<template>
    <div :style="frameStyle">
        <div class="container">
            <div :class="{ titleFont: true }"> {{ folderName }} </div>
            <div :style="listStyle">
                <recommend-button v-if="folderName === '选择文章分区'" v-for="item in folderList"
                :r-name="item.categoryName" @click="selectFolder(item.id, item.categoryName)"/>
                <recommend-button v-if="folderName === '选择收藏夹'" v-for="item in folderList"
                :r-name="item.categoryName" @click="blogFavour(item.categoryName)"/>
            </div>

            <el-button style="width: 100%; margin-top: 5px" @click="cancelSelect()">取消</el-button>

        </div>
    </div>
</template>

<script>
import recommendButton from "@/pages/blog/components/recommendButton/index.vue";
import {callError} from "@/callMessage";
import store from "@/store/store";
import {favor_func} from "@/pages/blog/api";

export default {
    name: "folderSelect",
    props: ['folderName'],
    components: {recommendButton},
    data(){
        return{
            frameStyle:{
                width: '400px',
                height: '300px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                border: '1px solid rgba(155, 155, 155, 0.2)',
                display: 'flex',
            },
            listStyle:{
                width: '100%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
                overflowY: 'auto',
                flexGrow: '1',
                overflowX: 'hidden'
            },
            currentPos: 0,
            totalPage: 1,
            folderList: []
        }
    },
    methods:{

        async getFavour(){
            try{
                if (this.folderName === '选择文章分区'){
                    const response = await this.$http.get(`space/getALLCategory?userId=${store.getters.getData.id}`);
                    if (response.status === 200){
                        this.folderList = response.data.data;
                    }else callError('网络错误');
                }else if (this.folderName === '选择收藏夹') {
                    const response = await this.$http.get(`blog/getFavourCategory?userId=${store.getters.getData.id}`);
                    if (response.status === 200) {
                        this.folderList = response.data.data;
                        this.folderList.push({categoryName: '默认收藏夹'});
                    }
                }
            }catch (e){
                callError(e);
            }
        },

        async blogFavour(name){
            this.cancelSelect();
            await favor_func(this.$route.params.id, true, name);
            setTimeout(()=>{
                location.reload();
            }, 2000);
        },

        selectFolder(id, name){
            this.$emit('sel', id, name);
        },

        cancelSelect(){
            this.$emit('cancelFloat');
        }

    },

    async mounted() {
        await this.getFavour();
    }

}
</script>

<style scoped>
.nav-button{
    font-family: 'consolas', sans-serif;
    font-size: 15px;
    color: #8e8e8e;
    cursor: pointer;
}
.card-nav{
    width: 100%;
    height: 22px;
    display: flex;
    flex-direction: row;
    justify-content: right;
    align-items: center;
    margin-bottom: 10px;
    gap: 5px;
}
.titleFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 17px;
    text-align: center;
    margin-bottom: 12px;
}
.pagination-style{
    margin-top: 5px;
    justify-content: right;
}
.container{
    width: 88%;
    height: 90%;
    margin: auto;
    display: flex;
    flex-direction: column;
}
</style>

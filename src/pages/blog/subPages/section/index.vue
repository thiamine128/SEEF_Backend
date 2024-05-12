<template>
    <div class="content-container">
        <div class="content-left">

            <article-list height-set="1030px" r-title="Topic ~Wonderful Zone~"
                  :list-set="sectionList" select="section" :total-page="totalPage"
                  @page-change="getSections"></article-list>

        </div>
        <div class="content-right">

            <div class="create-blog">
                <div class="titleFont"> Contribute </div>
                <el-input v-model="sectionInput" style="width: 80%; height: 40px" placeholder="如果想要创建新板块，请输入板块名称"/>
                <el-input
                    v-model="sectionAbstract"
                    style="width: 80%"
                    :rows="2"
                    type="textarea"
                    placeholder="请输入板块简介"
                />
                <el-button style="width: 80%" type="primary" plain @click="createSection()">创建</el-button>
            </div>

            <img alt="404" src="@/assets/blog/advertisement.png" style="width: 100%;">
        </div>
    </div>
</template>

<script>
import articleList from "@/pages/blog/components/articleList/index.vue";
import {callError, callSuccess} from "@/callMessage";
export default {
    name: "section",
    components: {articleList},
    data(){
        return{
            sectionInput: '',
            sectionAbstract: '',
            sectionList: [],
            totalPage: 10
        }
    },
    mounted() {
        this.getSections(1);
    },
    methods:{

        async createSection(){
            if (this.sectionInput.length == 0 || this.sectionAbstract.length == 0){
                callError('内容不可为空');
            }else if (this.sectionAbstract.length > 20){
                callError('简介过长');
            }else if (this.sectionInput.length > 16){
                callError('标题过长');
            } else{
                const response = await this.$http.post('/topic/create', {
                    "name": this.sectionInput,
                    "introduction": this.sectionAbstract
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.status === 200) {
                    if (response.data.code == 1) {
                        callSuccess('板块创建成功');
                        location.reload();
                    }
                    else callError(response.data.msg);
                } else callError('网络错误');
            }
        },

        async getSections(pageNum){ //获取全部板块信息
            try{
                const response = await this.$http.get(`topic/pagedList?page=${pageNum}&pageSize=15`);
                console.log(response);
                if (response.status === 200) {
                    this.sectionList = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 15);
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }
        }

    }
}
</script>

<style scoped>
.create-blog{
    background-color: rgba(255, 255, 255, 0.9);
    width: 100%;
    height: 200px;
    margin-bottom: 30px;
    display: flex;
    flex-direction: column;
    padding-top: 10px;
    padding-bottom: 10px;
    border-radius: 3px;
    align-items: center;
    gap: 10px
}

.content-container{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 20px;
    width: 1200px;
}
.content-left{
    width: 68%;
}
.content-right{
    width: 30%;
}
.titleFont {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 28px;
    text-align: center;
    color: #4b4b4b;
}
</style>

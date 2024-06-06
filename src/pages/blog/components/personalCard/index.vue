<template>
    <div :style="frameStyle">
        <div class="container">

            <div :class="{ titleFont: true }"> {{rTitle}} </div>

            <div class="card-nav" v-if="rTitle !== '关注列表' && showChange">
                <div class="nav-button" @click="addDirector"> 添加文件夹 </div>
            </div>

            <div :style="listStyle">

                <recommend-button v-if="rTitle === '关注列表'" :hot-set="1" :user-id="item.id"
                v-for="item in treeData" :r-name="item.label" @click="callPersonalFunc(item.id)"/>

                <el-tree v-if="rTitle !== '关注列表'" style="max-width: 100%"
                :data="treeData" :props="defaultProps"
                @node-click="handleNodeClick" :default-expand-all="true">
                    <template #default="{ node, data }">
                        <span style="display:flex; flex-direction: row; align-items: center; gap: 10px">
                            <span v-if="data.createTime" style="font-weight: bold">{{ node.label }}</span>
                            <span v-if="!data.createTime" style="color: #00bbff">{{ node.label }}</span>
                            <span v-if="data.createTime" style="font-size: 8px; color: #8e8e8e">作者：{{data.authorName}} | 发布于：{{dateF(data.createTime)}}</span>
                            <img v-if="!data.createTime && data.id !== -1" alt="404" src="@/assets/blog/cancel.png" style="width: 12px" @click="deleteDirector(node.label)">
                        </span>
                    </template>
                </el-tree>


            </div>

            <el-pagination v-if="rTitle !== '关注列表'" class="pagination-style"
            v-model:current-page="currentPos"
            ref="bottomPagination"
            layout="prev, pager, next"
            @current-change="pageChange()"
            :total="10*totalPage" />

        </div>
    </div>
</template>

<script>
import recommendButton from "@/pages/blog/components/recommendButton/index.vue";
import ArticleBox from "@/pages/blog/components/articleBox/index.vue";
import {callError, callInfo} from "@/callMessage";
import {dateF, deleteCategory, deleteSpace, getSubscribed, getUserData} from "@/pages/blog/api";

export default {
    name: "personalCard",
    props: ['heightSet', 'rTitle', 'showChange'],
    components:{ArticleBox, recommendButton},
    data(){
        return{
            frameStyle:{
                height: '1px',
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

            currentPos: 1,
            totalPage: 1,

            defaultProps : {
                children: 'children',
                label: 'label',
            },

            blogList: [],
            treeData : []

        }
    },
    async mounted() {
        this.frameStyle.height = this.heightSet;
        await this.getDirectors();
        if (this.rTitle === '发布文章') await this.pullArticles(1);
        if (this.rTitle === '收藏列表') await this.pullFavour(1);
    },
    methods:{
        dateF,

        async addBlogToCategory(blog, categoryId, categoryName = '1145141919810'){
            blog["label"] = blog["title"];
            const userData = await getUserData(blog['userId']);
            blog["authorName"] = userData.name;
            let key = 0;
            for (let c of this.treeData){
                if (c.id === categoryId || c.label === categoryName){
                    c.children.push(blog);
                    key = 1;
                    break;
                }
            }
            if (key === 0){
                for (let c of this.treeData){
                    if (c.id === -1) c.children.push(blog);
                }
            }
        },

        async pullFavour(pageNum){
            try{
                const response = await this.$http.get(
                    `blog/listFavor?page=${pageNum}&pageSize=15&previewLength=20`
                );
                if (response.status === 200) {
                    this.blogList = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 15);
                } else callError('网络错误');
            }catch (error){
                //callError(error);
            }

            console.log('关注博客：');
            console.log(this.blogList);

            for (let b of this.blogList){
                if (!b.favourCategory) await this.addBlogToCategory(b, -1);
                else await this.addBlogToCategory(b, b.categoryId, b.favourCategory);
            }

        },

        async pullArticles(pageNum){
            try{
                const response = await this.$http.get(
                    `blog/viewBlogs?page=${pageNum}&pageSize=15&previewLength=20&userId=${this.$route.params.userId}&orderBy=popularity&sort=desc`
                );
                if (response.status === 200) {
                    this.blogList = response.data.data.records;
                    this.totalPage = Math.ceil(response.data.data.total / 15);
                } else callError('网络错误');
            }catch (error){
                callError(error);
            }

            for (let b of this.blogList){
                if (!b.categoryId) await this.addBlogToCategory(b, -1);
                else await this.addBlogToCategory(b, b.categoryId);
            }

        },

        callPersonalFunc(userId){
            this.$router.push(`/blog/`);
            setTimeout(()=>{
                this.$router.push(`/blog/personal/${userId}`);
            }, 100);
        },

        async pageChange(){
            for (let c of this.treeData){
                c.children = [];
            }
            await this.pullArticles(this.currentPos);
        },

        handleNodeClick(data){
            if (data.createTime){
                this.$router.push(`/blog/article/${data.id}`);
            }
        },

        async getDirectors(){
            try{
                if (this.rTitle === '发布文章'){

                    this.treeData.push({
                        label: '默认博客分类',
                        children: [],
                        id: -1
                    })

                    const response = await this.$http.get(
                        `space/getALLCategory?userId=${this.$route.params.userId}`
                    );
                    console.log('category: ');
                    console.log(response);
                    if (response.status === 200){
                        if (response.data.data){
                            for (let e of response.data.data){
                                const init_tree_data = {
                                    label: e.categoryName,
                                    children: [

                                    ],
                                    id: e.id
                                };
                                this.treeData.push(init_tree_data);
                            }
                        }
                    }else callError('网络错误');

                }else if (this.rTitle === '收藏列表'){

                    this.treeData.push({
                        label: '默认收藏夹',
                        children: [],
                        id: -1
                    })

                    const response = await this.$http.get(
                        `blog/getFavourCategory?userId=${this.$route.params.userId}`
                    );

                    console.log('favourCategory:');
                    console.log(response);

                    if (response.status === 200){
                        if (response.data.data){
                            for (let e of response.data.data){
                                const init_tree_data = {
                                    label: e.categoryName,
                                    children: [],
                                    id: e.id
                                };
                                if (init_tree_data.label !== '默认收藏夹')
                                    this.treeData.push(init_tree_data);
                            }
                        }
                    }else callError('网络错误');

                }else if (this.rTitle === '关注列表'){

                    const subscribed_init = await getSubscribed();
                    for (let sb of subscribed_init){
                        const userData = await getUserData(sb);
                        this.treeData.push({'label': userData.name, 'id': sb, 'profile': userData.profile});
                    }

                }

            }catch(e){
                callError(e);
            }
        },

        addDirector(){
            if (this.rTitle === '发布文章') this.$emit('addSpace');
            if (this.rTitle === '收藏列表') this.$emit('addCategory');
        },

        async deleteDirector(categoryName){
            if (this.rTitle === '发布文章') {
                if (categoryName === '默认博客分类'){
                    callInfo('不能删除默认分类');
                    return;
                }
                await deleteSpace(categoryName)
            }
            if (this.rTitle === '收藏列表') {
                if (categoryName === '默认收藏夹'){
                    callInfo('不能删除默认收藏夹');
                    return;
                }
                await deleteCategory(categoryName);
            }
        }

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
    background-color: rgba(22, 22, 22, 0.01);
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

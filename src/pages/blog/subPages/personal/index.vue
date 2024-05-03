<template>
    <div class="content-container">
        <div class="personal-container">
            <img @click="uploadImage" class="portraitSet" src="@/assets/blog/testPortrait.jpg" alt="404 not found">
            <div class="textSet">
                <div class="nameSet">
                    <div :class="{ nameFont: true }"> 阮阳栋 </div>
                    <personal-button class="subscribeSet"
                     :img-path="require('@/assets/blog/subscribe.png')" content="关注" >
                    </personal-button>
                </div>
                <div :class="{ contentFont: true }"> 1164754246@qq.com </div>
                <div :class="{ contentFont: true }"> 注册于2024年4月23日 </div>
            </div>
        </div>
        <div class="navStyle">
            <div class="select">

            </div>
            <div class="show">
                <div class="like" >
                    <div :class="{ contentFont: true }"> 文章 </div>
                    <div :class="{ contentFont: true }"> 0 </div>
                </div>
            </div>

        </div>
        <div class="infoStyle">

            <personal-card  class="cardSet" height-set="500px"/>

        </div>
    </div>
</template>

<script>

import personalButton from "@/pages/blog/components/personalButton/index.vue";
import PersonalCard from "@/pages/blog/components/personalCard/index.vue";

export default {
    name: "personal",
    components: {PersonalCard, personalButton},
    methods:{

        uploadImage(){
            const fileInput = document.createElement("input");
            fileInput.type = "file";
            fileInput.accept = ".jpg, .png";
            fileInput.addEventListener("change", this.handleImage);
            fileInput.click();
        },

        async handleImage(event){
            const file = event.target.files[0];
            const formData = new FormData();
            formData.append('image', file);

            try {
                const response = await this.$http.post('/user/requestUploadAvatar', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                console.log(response.data);
            } catch (error) {
                console.error(error);
            }
        }

    }
}
</script>

<style scoped>
.like{
    height: 100%;
    width: 50px;
    display: flex;
    flex-direction: column;
}
.show{
    width: 150px;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: right;
}
.select{
    width: 100px;
    height: 100%;

}
.cardSet{
    width: 40%;
}
.nameSet{
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
}
.infoStyle{
    height: 500px;
    width: 100%;
    background-color: rgba(255, 255, 255, 0.9);
    display: flex;
    flex-direction: row;
}
.navStyle{
    height: 70px;
    width: 100%;
    background-color: rgba(245, 245, 245, 0.9);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}
.content-container{
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 20px;
    width: 1200px;
}
.personal-container{
    width: 100%;
    height: 200px;
    background-color: rgba(255, 255, 255, 0.9);
    background-size: cover;
    display: flex;
    flex-direction: row;
    align-items: center;
}
.portraitSet{
    height: 180px;
    width: 180px;
    border: 3px solid rgba(205, 205, 205, 0.2);
    border-radius: 100px;
    margin-left: 10px;
    cursor: pointer;
}
.textSet{
    height: 100%;
    overflow-x: auto;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
    padding-top: 80px;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 20px;
    text-align: left;
    color: gray;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 40px;
    font-weight: bold;
    text-align: left;
}
</style>

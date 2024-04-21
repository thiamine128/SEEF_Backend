<template>
<!--    <nav class="mainTitleContainer">-->
<!--        <div :class="{ customFont: true }">software and life</div>-->
<!--        <div class="search-nav">-->
<!--            <search-bar/>-->
<!--            <nav class="button-nav">-->
<!--                <nav-button v-for="item in buttonSet" :button-name="item.name"></nav-button>-->
<!--            </nav>-->
<!--        </div>-->
<!--    </nav>-->
    <div class="personInfo">
        <p class="title">个人信息：</p>
        <p class="detail">
            姓名：{{name}}<br>
            性别：{{gender}}<br>
            学号：{{id}}<br>
            年级：{{grade}}<br>
            身份：{{identity}}<br>
            邮箱：{{emailId}}<br>
            自我介绍：{{personProfile}}
        </p>
        <img src="@/assets/education/personInfo/userPhoto.png" alt="个人照片" class="photo">
        <div class="changePhoto">
            <input type="file" ref="fileInput" /><br>
            <button @click="uploadImage">Upload Image</button>
    <!--        <img v-if="imageUrl" :src="imageUrl" alt="Uploaded Image" />-->
        </div>

    </div>
</template>

<!--<script setup>-->
<!--    let name="张三",gender="男",id=21377777,grade="大二"-->
<!--</script>-->

<script>
import axios from 'axios';

export default {
    data() {
        return {
            imageUrl: null,
            selectedFile: null,
            name: "张三",
            gender: "男",
            id: 21377777,
            grade: "大二",
            identity: "学生",
            emailId: "21377777@buaa.edu.cn",
            personProfile: "你好，我是张三，我喜欢软件学院（诚招合作，五毛一条，微信：iLoveBUAASoftware）"
        };
    },
    methods: {
        uploadImage() {
            this.selectedFile = this.$refs.fileInput.files[0];
            if (this.selectedFile) {
                const formData = new FormData();
                formData.append('file', this.selectedFile);

                axios.post('/upload', formData)// 等待修改
                    .then(response => {
                        // Handle successful upload
                        console.log('Image uploaded successfully!');
                        this.imageUrl = response.data.imageUrl; // Assuming response contains an imageUrl property
                    })
                    .catch(error => {
                        // Handle upload error
                        console.error('Error uploading image:', error);
                    });
            }
        },
    },
};
</script>

<style scoped>
.personInfo {
    width: 800px;
    height: 500px;
    background-color: lightblue;
    opacity: 0.75;
    border: 2px solid #000;
    padding: 20px;
    display: flex; /* 将div转换为flex容器 */
    align-items: center; /* 使子元素垂直居中 */
    position: relative;
    text-align: left;
    margin: 20px auto 0;
    line-height: 35px;
}
.title {
    position: absolute;
    top: 10px;
    left: 20px;
    font-size: 30px;
}
.detail {
    position: absolute;
    top: 80px;
    left: 30px;
    font-size: 20px;
    color: black;
    width: 500px;
}

.photo {
    position: absolute;
    width: 200px;
    height: 300px;
    right: 20px;
    top: 50px;
    border-radius: 10px; /* Create rounded corners */
    margin-right: 20px; /* Position the image 20px from the right edge */
}
.changePhoto {
    position: absolute;
    right: 20px;
    top: 370px;
}
.customFont {
    font-family: 'rage', sans-serif;
    font-weight: bold;
    font-size: 32px;
    margin: 25px 0 25px 60px;
    color: white;

}
</style>

<template>
    <blogTitle title="BUAA-SE-Wonderful" post-time="2023-12-11" update-time="2023-12-15"/>
    <div class="content-container">
        <md-field class="content-left" :input-content="content"></md-field>
        <div ref="pinComponent" class="content-right" ></div>
    </div>
</template>

<script>
import mdField from "@/pages/blog/components/mdField/index.vue";
import blogTitle from "@/pages/blog/components/title/index.vue";
import axios from "axios";
import {inject, ref} from "vue";
export default {
    name: "article",
    components: {blogTitle, mdField},
    async created() {
        try {
            const response = await axios.get(inject('webURL')+"testMarkdown.md");
            this.content = response.data;
            console.log(this.content)
        } catch (error) {
            console.error("Error fetching Markdown file:", error);
        }
    },
    data(){
        return{
            content: '',
            offsetT : 0
        }
    },
    computed:{

    },
    methods:{
        handleScroll() {
            const componentElement = this.$refs.pinComponent;
            this.updateDistanceFromTop(componentElement);
        },
        updateDistanceFromTop(element) {
            const rect = element.getBoundingClientRect();
            const distanceFromTop = rect.top;
            console.log('距离顶部的距离：', distanceFromTop);
            console.log('鼠标滚动的距离：', window.scrollY);
            element.style.marginTop = `${
                window.scrollY-this.offsetT+90 > 0 ? window.scrollY-this.offsetT+90 : 0
            }px`;
        }
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
        this.offsetT = this.$refs.pinComponent.getBoundingClientRect().top + window.scrollY;
        console.log('offsetT: ', this.offsetT);
    },
    unmounted() {
        window.removeEventListener("scroll", this.handleScroll);
    }
}
</script>

<style scoped>
.content-container{
    display: flex;
    flex-direction: row;
    margin-top: 3px;
}
.content-left{
    width: 70%;
}
.content-right{
    width: 30%;
    height: 100px;
    background-color: blue;
}
</style>

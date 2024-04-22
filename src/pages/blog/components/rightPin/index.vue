<template>
    <div ref="pinComponent">
        <recommend height-set="300px" :r-title="rTitle"/>
    </div>
</template>

<script>
import recommend from "@/pages/blog/components/recommend/index.vue";
export default {
    name: "rightPin",
    components:{recommend},
    props:['rTitle'],
    data(){
      return{
          offsetT : 0
      }
    },
    methods:{
        calcOffset(){
            return this.$refs.pinComponent.getBoundingClientRect().top + window.scrollY
                - parseInt(this.$refs.pinComponent.style.marginTop.match(/\d+/g));
        },
        handleScroll() {
            const componentElement = this.$refs.pinComponent;
            this.updateDistanceFromTop(componentElement);
        },
        updateDistanceFromTop(element) {
            const rect = element.getBoundingClientRect();
            const distanceFromTop = rect.top;
            // console.log('距离顶部的距离：', distanceFromTop);
            // console.log('鼠标滚动的距离：', window.scrollY);
            this.offsetT = this.calcOffset();
            element.style.marginTop = `${
                window.scrollY-this.offsetT+90 > 0 ? window.scrollY-this.offsetT+90 : 0
            }px`;
        }
    },
    mounted() {
        window.addEventListener("scroll", this.handleScroll);
    },
    unmounted() {
        window.removeEventListener("scroll", this.handleScroll);
    }
}
</script>

<style scoped>

</style>

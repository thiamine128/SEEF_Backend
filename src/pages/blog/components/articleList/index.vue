<template>
    <div :style="frameStyle">
        <div class="container">
            <div class="title-container">
                <div :class="{ titleFont: true }">{{rTitle}}</div>
            </div>
            <hr>
            <div :style="listStyle">

            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "articleList",
    props: ['heightSet', 'rTitle'],
    components:{ },
    data(){
        return{
            frameStyle:{
                width: '100%',
                height: '1px',
                backgroundColor: 'rgba(255, 255, 255, 0.9)',
                borderRadius: '5px',
                border: '1px solid rgba(155, 155, 155, 0.7)',
                display: 'flex',
                marginBottom: '10px',

            },
            listStyle:{
                width: '100%',
                height: '90%',
                borderRadius: '2px',
                backgroundColor: 'rgba(205, 205, 205, 0.2)',
            }
        }
    },
    mounted() {
        this.frameStyle.height = this.heightSet;
    }
}
</script>

<style scoped>
.container{
    width: 100%;
    height: 100%;
    margin: auto;
    display: flex;
    flex-direction: column;
    padding: 40px;
}
.titleFont {
    font-family: 'rage', sans-serif;
    font-size: 28px;
    text-align: left;
    color: #6a6a6a;
}
.title-container{
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
    height: 40px;
}
hr{
    width: 100%;
}
</style>

<template>
    <div class="editor-row">
        <div class="left-column">
            <div class="postMark">Post</div>
            <img class="photo-set" alt="404" src="@/assets/blog/photo-camera.png">
        </div>
        <textarea v-model="content" ref="left_s" placeholder="在此处编辑Markdown......"></textarea>
        <md-field class="mdStyle" :inputContent="content" ref="right_s"/>
    </div>
</template>

<script>
import {ref, watch} from "vue";
import MdField from "@/pages/blog/components/mdField/index.vue";
export default {
    name: "editor",
    components: {MdField},
    data(){
        const content = ref('');
        return{
            content
        }
    }
}
</script>

<style scoped>
.left-column{
    width: 4%;
    height: 600px;
    justify-content: center;
    background-color: #eaeaea;
    border-radius: 3px;
}
.photo-set{
    margin-top: 10px;
    width: 70%;
}
.editor-row{
    display: flex;
    flex-direction: row;
    justify-content: right;
}
textarea{
    width: 48%;
    height: 520px;
    font-family: 'consolas', sans-serif;
    font-size: 18px;
    padding: 40px;
    resize: none;
}
.mdStyle{
    width: 48%;
    height: 600px;
    overflow-y: auto;
}
:deep(.marginSet){
    margin: 40px;
}
:deep(.textSet){
    border-radius: 0;
}
.postMark{
    font-family: 'rage', sans-serif;
    font-weight: bold;
    margin-top: 5px;
}
</style>

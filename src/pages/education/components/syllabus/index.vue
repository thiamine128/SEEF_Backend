<template>
    <div class="modal-wrapper">
        <div class="modal-content" >
            {{detailedContent}}
        </div>
<!--        <vue-markdown :source="detailedContent"></vue-markdown>-->
        <button class="close-button" @click="$emit('close')">X</button>
    </div>

</template>

<script>
// import VueMarkdown from 'vue-markdown';
export default {
    name: "syllabus",
    props: {
        detailedContent: String
    },
    // components: {
    //     VueMarkdown
    // }
}
</script>

<style scoped>
.modal-wrapper {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    width: 60%;
    word-wrap: break-word;
    white-space: normal;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
}
</style>

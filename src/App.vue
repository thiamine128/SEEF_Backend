<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>

import axios from 'axios'
import {provide} from "vue";

//根组件
export default {
    name: 'App'
}
</script>

<style>
@import "assets/fonts/index.css";
#app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}
::-webkit-scrollbar {
    width: 5px;
    height: 5px;
}
::-webkit-scrollbar-thumb {
    background-color: #0fb2ff;
    opacity: 0.7;
    border-radius: 5px;
}
</style>

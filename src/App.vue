<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>

import axios from 'axios'
import {provide} from "vue";

export default {
  name: 'App',
  setup(){
      const webURL = "http://localhost:5653/"
      provide('webURL', webURL)
  },
  data: function(){
    return{

    }
  },
  components: {

  }
}
</script>

<style>
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
</style>

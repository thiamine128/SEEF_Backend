<template>
  <router-view/>
</template>

<script>

import axios from 'axios'

export default {
  name: 'App',
  data: function(){
    return{

    }
  },
  // created: function(){
  //   axios.get("http://localhost:8088/city").then(function(response){
  //
  //   })
  // },
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
  margin-top: 5px;
}
</style>

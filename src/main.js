import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import axios from 'axios'
import router from './router'

axios.defaults.baseURL = "http://localhost:8080"

const app = createApp(App);
app.use(App);
app.use(ElementPlus);
app.use(router)
app.mount('#app');

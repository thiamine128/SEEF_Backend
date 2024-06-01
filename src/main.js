import {createApp} from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import axios from "axios";
import { saveAs } from 'file-saver';
import store from "@/store/store";

axios.defaults.baseURL = '/api' //axios请求默认URL
axios.interceptors.request.use(config => {
    config.headers['token'] = store.getters.getToken; //有效
    config.headers.Authorization = `Bearer ${store.getters.getToken}`; //无效
    console.log('请求头携带token：'+`${store.getters.getToken}`);
    return config;
}, error => {
    return Promise.reject(error);
}); //请求头token设置

axios.instance.interceptors.response.use(
    result=>{
        if(result.data.code === 1)
        {
            return result.data;
        }
        //alert(result.data.message?result.data.message:'服务异常');
        ElMessage.error(result.data.message?result.data.message:'服务异常');
       return Promise.reject(result.data)
    },
    err=>{
        //判断相应状态码
        if(err.response.status===401){
            ElMessage.error('请先登录')
            router.push('/login');
        }else {
                ElMessage.error('服务异常')
        }

    
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)


const app = createApp(App);
app.use(App);
app.use(ElementPlus);
app.use(router)
app.use(store)
app.config.globalProperties.$http = axios;
app.mount('#app');

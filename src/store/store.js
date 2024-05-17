import { createStore } from 'vuex';
import axios from "axios";
import router from "@/router";
import VuexPersistence from "vuex-persist";
import {callError, callSuccess} from "@/callMessage";

//状态管理

//本地cookie存储
const vuexLocal = new VuexPersistence({
    storage: window.localStorage
});

export default createStore({


    state: { //存储内容
        token: null,
        tokenExpire: null,
        id: null,
        name: null,
        nickname: null,
        avatar: null,
        profile: null,
        role: null,
        email: null,
        tempTitle: '',
        tempContent: '',
        eventList: []
    },


    getters:{
        //获取文章暂存信息
        getContent(state){
            return{
                title: state.tempTitle,
                content: state.tempContent
            }
        },
        //获取用户个人信息
        getData(state){
            return {
                id: state.id,
                name: state.name,
                nickname: state.nickname,
                avatar: state.avatar,
                profile: state.profile,
                role: state.role,
                email: state.email
            }
        },
        //获取登录认证令牌
        getToken(state){
            console.log('当前时间：'+Date.now());
            console.log('token过期时间：'+state.tokenExpire);
            if (Date.now() > state.tokenExpire){
                state.token = null;
                console.log('token已经过期');
            }
            return state.token;
        },
        //获取动态信息
        getEventList(state){
            return state.eventList;
        }
    },


    mutations: {

        //如果想要去除token，执行以下代码，commit('setToken', null);
        setToken(state, token) {
            state.token = token;
            state.tokenExpire = Date.now() + 3600 * 1000;
        },

        //设置个人信息
        setData(state, data){
            state.id = data.id;
            state.name = data.name;
            state.nickname = data.nickname;
            state.avatar = data.avatar;
            state.profile = data.profile;
            state.role = data.role;
            state.email = data.email;
        },

        //设置文章暂存
        setContent(state, content){
            state.tempContent = content.content;
            state.tempTitle = content.title;
        },

        //存放私信
        addEvent(state, newEvent){
            state.eventList.push(newEvent);
        }

    },


    actions: {
        //用户名登录
        async login({ commit, state }, credentials) {
            try {
                const response = await axios.post('/user/login', credentials);
                console.log(response.data);
                if (response.status === 200){
                    if (response.data.code == 1){
                        commit('setToken', response.data.data.token);
                        commit('setData', response.data.data);
                        callSuccess('登录成功');
                        router.push('/blog');
                    }else callError(response.data.msg);
                }else callError('网络错误');
            } catch (error) {
                console.log('there are some errors in login');
                callError('密码错误或用户不存在');
            }
            return 1;
        },


        //邮箱登录
        async eLogin({ commit }, credentials) {
            try {
                const response = await axios.post('/user/eLogin', credentials);
                if (response.status === 200){
                    if (response.data.code == 1){
                        commit('setToken', response.data.data.token);
                        commit('setData', response.data.data);
                        callSuccess('登录成功');
                        router.push('/blog');
                    }else callError(response.data.msg);
                }else callError('网络错误');
            } catch (error) {
                console.log('there are some errors in login');
                callError('密码错误或用户不存在');
            }
        },


        //登出，清除token
        logout({ commit }) {
            try {
                commit('setToken', null);
                router.push('/');
            } catch (error) {
                console.log('there are some errors in logout');
            }
        },


    },

    //本地存储插件
    plugins: [vuexLocal.plugin]
});

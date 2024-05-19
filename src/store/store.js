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
        eventList: [],

        userMap: new Map(),
        blogMap: new Map(),
        commentMap: new Map(),
        replyMap: new Map()

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
                state.eventList = [];

                state.userMap = new Map();
                state.blogMap = new Map();
                state.commentMap = new Map();
                state.replyMap = new Map();

                console.log('token已经过期');
            }
            return state.token;
        },
        //获取动态信息
        getEventList(state){
            return state.eventList;
        },

        getMapUser: (state) => (userId) => {
            return state.userMap[userId];
        },

        getMapBlog : (state) => (blogId) =>{
            return state.blogMap[blogId];
        },

        getMapComment: (state)=> (commentId) => {
            return state.commentMap[commentId];
        },

        getMapReply: (state)=> (replyId)=>{
            return state.replyMap[replyId];
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

            state.userMap = new Map();
            state.blogMap = new Map();
            state.commentMap = new Map();
            state.replyMap = new Map();
        },

        //设置文章暂存
        setContent(state, content){
            state.tempContent = content.content;
            state.tempTitle = content.title;
        },

        //存放私信
        addEvent(state, newEvent){
            state.eventList.push(newEvent);
        },

        //清空事件
        clearEvent(state){
            state.eventList = [];
        },

        addMapUser(state, data){
            const userId = data.userId;
            const userData = data.userData;
            console.log('尝试缓存user:'+userId);
            state.userMap[userId] = userData;
            console.log(state.userMap);
            //state.userMap.set(userId, userData);
        },

        addMapBlog(state, data){
            const blogId = data.blogId;
            const blogData = data.blogData;
            state.blogMap[blogId] = blogData;
            //state.blogMap.set(blogId, blogData);
        },

        addMapComment(state, data){
            const commentId = data.commentId;
            const commentData = data.commentData;
            state.commentMap[commentId] = commentData;
            //state.commentMap.set(commentId, commentData);
        },

        addMapReply(state, data){
            const replyId = data.replyId;
            const replyData = data.replyData;
            state.replyMap[replyId] = replyData;
            //state.replyMap.set(replyId, replyData);
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

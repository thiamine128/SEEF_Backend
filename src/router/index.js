import { createRouter,createWebHashHistory, createWebHistory } from "vue-router";
import {computed, defineAsyncComponent} from 'vue'
import store from "@/store/store";

const router = createRouter({
    // history: createWebHashHistory(),  // hash 模式
    history: createWebHistory(),  // history 模式
    routes: [
        {
            path: '/login',
            name: 'login',
            component: defineAsyncComponent(() => import(`../pages/login/index.vue`)),
            meta: {
                title: '登录',
            },
        },
        {
            path: '/education',
            name: 'education',
            component: defineAsyncComponent(() => import(`../pages/education/index.vue`)),
            meta: {
                title: '教学论坛',
            },
        },
        {
            path: '/education/myLesson',
            name: 'myLesson',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/myLesson/index.vue')),
            meta: {
                title: '我的课程',
            }
        },
        {
            path: '/education/mySchoolTimeTable',
            name: 'mySchoolTimeTable',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/mySchoolTimeTable/index.vue')),
            meta: {
                title: '我的课表',
            }
        },
        {
            path: '/education/courseSquare',
            name: 'courseSquare',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/courseSquare/index.vue')),
            meta: {
                title: '课程广场',
            }
        },
        {
            path: '/education/courses/DBMS',
            name: 'DBMS',
            component: defineAsyncComponent(() => import(`../pages/education/subPages/courses/DBMS/index.vue`)),
            meta: {
                title: '数据管理技术',
            }
        },
        {
            path: '/education/courses/signal',
            name: 'signal',
            component: defineAsyncComponent(() => import(`../pages/education/subPages/courses/signal/index.vue`)),
            meta: {
                title: '信号处理基础',
            }
        },
        {
            path: '/education/courses/softwareEngineer',
            name: 'softwareEngineer',
            component: defineAsyncComponent(() => import(`../pages/education/subPages/courses/softwareEngineer/index.vue`)),
            meta: {
                title: '软件工程',
            }
        },
        {
            path: '/education/courses/internetMarketing',
            name: 'internetMarketing',
            component: defineAsyncComponent(() => import(`../pages/education/subPages/courses/internetMarketing/index.vue`)),
            meta: {
                title: '互联网营销',
            }
        },
        {
            path: '/education/personCenter',
            name: 'personSquare',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/personCenter/index.vue')),
            meta: {
                title: '个人中心',
            }
        },


        {
            path: '/blog',
            name: 'blog',
            component: defineAsyncComponent(() => import(`../pages/blog/index.vue`)),
            children:[
                {
                    path: '/blog/space',
                    name: 'space',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/space/index.vue`)),
                },
                {
                    path: '/blog/homepage',
                    name: 'homepage',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/homepage/index.vue`)),
                },
                {
                    path: '/blog/article/:id',
                    name: 'article',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/article/index.vue`)),
                },
                {
                    path: '/blog/articles/:topicId/:sectionName',
                    name: 'articles',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/articles/index.vue`)),
                },
                {
                    path: '/blog/editor',
                    name: 'editor',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/editor/index.vue`)),
                },
                {
                    path: '/blog/section',
                    name: 'section',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/section/index.vue`)),
                },
                {
                    path: '/blog/personal',
                    name: 'personal',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/personal/index.vue`)),
                },
                {
                    path: '/blog/:catchAll(.*)',
                    redirect: '/blog/homepage',
                },
                {
                    path: '/blog/',
                    redirect: '/blog/homepage'
                }
            ],
            meta: {
                title: '学业论坛',
            },
        },


        {
            path: '/:catchAll(.*)',
            redirect: '/login',
        },
        {
            path: '/',
            redirect: '/login',
        }


    ]
})

router.beforeEach((to, from, next)=>{

    const hasToken = store.getters.getToken;

    if (hasToken != null){
        console.log('现在存在token：'+hasToken);
        if (to.path === '/login') {
            next({ path: '/education' })
        }else{
            if (to.meta.title) {
                document.title = `${to.meta.title}`;
            }
            next();
        }

    }else{
        if (to.path === '/login'){
            next();
        }else{
            window.alert('未检测到token，请重新登录');
            next('/login');
        }
    }

})

router.afterEach((to, from)=>{
    console.log('change page succeed')
})

export default router

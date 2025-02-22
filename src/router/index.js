import { createRouter,createWebHashHistory, createWebHistory } from "vue-router";
import {computed, defineAsyncComponent} from 'vue'
import store from "@/store/store";

//路由设置

const router = createRouter({
    // history: createWebHashHistory(process.env.BASE_URL),  // hash 模式
    history: createWebHistory(),  // history 模式
    routes: [

        //登录注册页面路由
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
                title: '教务系统',
            }
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
            component: defineAsyncComponent(() =>
                import('@/pages/education/subPages/courseSquare/index.vue')),
            meta: {
                title: '课程广场',
            }
        },
        {
            path: '/education/adminCenter',
            name: 'adminCenter',
            component: defineAsyncComponent(() =>
                import('@/pages/education/subPages/adminCenter/index.vue')),
            meta: {
                title: '管理中心',
            }
        },
        {
            path: '/education/TACenter',
            name: 'TACenter',
            component: defineAsyncComponent(() =>
                import('@/pages/education/subPages/TACenter/index.vue')),
            meta: {
                title: '我的助教',
            }
        },
        {
            path: '/education/courses/:courseId',
            name: 'courses',
            component: defineAsyncComponent(() =>
                import(`@/pages/education/subPages/courses/index.vue`)),
        },
        // {
        //     path: '/education/classes/:classId',
        //     name: 'classes',
        //     component: defineAsyncComponent(() =>
        //         import(`@/pages/education/subPages/classes/index.vue`)),
        // },

        {
            path: '/education/CourseInfo',
            name: 'courseInfo',
            component: defineAsyncComponent(() =>
                import(`@/pages/education/subPages/courseInfo/index.vue`)),
        },

        {
            //错误路由重定向
            path: '/education/:catchAll(.*)',
            redirect: '/education',
        },

        //博客界面路由
        {
            path: '/blog',
            name: 'blog',
            component: defineAsyncComponent(() => import(`../pages/blog/index.vue`)),
            children:[
                {
                    //动态页
                    path: '/blog/space',
                    name: 'space',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/space/index.vue`)),
                },
                {
                    //主页
                    path: '/blog/homepage',
                    name: 'homepage',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/homepage/index.vue`)),
                },
                {
                    //文章页
                    path: '/blog/article/:id',
                    name: 'article',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/article/index.vue`)),
                },
                {
                    //专区页
                    path: '/blog/articles/:topicId/:sectionName',
                    name: 'articles',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/articles/index.vue`)),
                },
                {
                    //编辑器
                    path: '/blog/editor/:blogId',
                    name: 'editor',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/editor/index.vue`)),
                },
                {
                    //专区浏览
                    path: '/blog/section',
                    name: 'section',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/section/index.vue`)),
                },
                {
                    //个人页面
                    path: '/blog/personal/:userId',
                    name: 'personal',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/personal/index.vue`)),
                },
                {
                    //搜索页面
                    path: '/blog/find/:search',
                    name: 'search',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/search/index.vue`)),
                },
                {
                    //错误路由重定向
                    path: '/blog/:catchAll(.*)',
                    redirect: '/blog/homepage',
                },
                {
                    //默认页面：主页
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
        //console.log('现在存在token：'+hasToken);
        if (to.path === '/login') {
            //已经登录了，去博客页面
            next({ path: '/blog' })
        }else{
            if (to.meta.title) {
                document.title = `${to.meta.title}`;
            }
            //直接放行
            next();
        }

    }else{
        //没有登录
        if (to.path === '/login'){
            //要登录，放行
            next();
        }else{
            window.alert('未检测到token，请重新登录');
            //没有登录，去登录页面
            next('/login');
        }
    }

})

router.afterEach((to, from)=>{
    //切换路由成功
    //console.log('change page succeed');
})

export default router

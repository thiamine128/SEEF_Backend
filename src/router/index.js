import { createRouter,createWebHashHistory, createWebHistory } from "vue-router";
import { defineAsyncComponent } from 'vue'

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
            path: '/education/courseSquare',
            name: 'courseSquare',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/courseSquare/index.vue')),
            meta: {
                title: '课程广场',
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
            path: '/education/mySchedule',
            name: 'mySchedule',
            component: defineAsyncComponent(() => import('@/pages/education/subPages/mySchedule/index.vue')),
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
                    path: '/blog/article',
                    name: 'article',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/article/index.vue`)),
                },
                {
                    path: '/blog/editor',
                    name: 'editor',
                    component: defineAsyncComponent(() =>
                        import(`../pages/blog/subPages/editor/index.vue`)),
                },
                {
                    path: '/blog/:catchAll(.*)',
                    redirect: '/blog/article',
                },
                {
                    path: '/blog/',
                    redirect: '/blog/article'
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
    if (to.meta.title) {
        document.title = `${to.meta.title}`;
    }
    next()
})

router.afterEach((to, from)=>{
    console.log('afterEach')
})

export default router

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
            path: '/blog',
            name: 'blog',
            component: defineAsyncComponent(() => import(`../pages/blog/index.vue`)),
            meta: {
                title: '教学论坛',
            },
        },
        {
            path: '/*',
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

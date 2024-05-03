import VueRouter from 'vue-router';
import store from '@/store/store'; // assuming your Vuex store is imported as 'store'

const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/LoginPage',
    },
    {
      path: '/HomePage',
      name: 'HomePage',
      component: () => import("@/components/Common/HomePage.vue"),
      children: [
        {
          path: 'PersonInfo',
          name: 'PersonInfo',
          component: () => import("@/components/pages/PersonInfo.vue"),
        },
        {
          path: 'StudentInfo',
          name: 'StudentInfo',
          component: ()=> import("@/components/pages/StudentInfo.vue")
        },
        {
          path: 'TestPage',
          name: 'TestPage',
          component: ()=> import("@/components/pages/TestPage.vue")
        },
      ]
    },
    {
      path: '/LoginPage',
      name: 'LoginPage',
      component: () => import("@/components/Common/LoginPage.vue"),
    },
  ]
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.name !== 'LoginPage') {
    // 如果不是登录页，检查是否存在 token
    if (!token) {
      // 如果不存在 token，则跳转到 LoginPage 页面
      alert("Please login first!");
      setTimeout(() => {
        next('/LoginPage');
      }, 1000);
    } else {
      // 如果存在 token，则继续导航
      next();
    }
  } else {
    // 如果是登录页，直接导航
   next()
    
  }
});

export default router;

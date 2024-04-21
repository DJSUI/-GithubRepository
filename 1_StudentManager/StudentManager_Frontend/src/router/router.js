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

  if (to.name !== 'LoginPage') {
   
    if (!store.state.user) {
      
      alert("Please login first!");
      setTimeout(() => {
             next('/LoginPage');
     },1000)
    } else {
      next();
    }
  } else {
   
    next();
  }
})

export default router;

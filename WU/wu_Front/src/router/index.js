import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
     path: '/login',
    name: 'Login',
    component: ()=> import('../views/LoginView.vue')
  }, 

  {
     path: '/register',
    name: 'Register',
    component: ()=> import('../views/RegisterView.vue')
  }, 
  
  {
    path: '/',
    name: 'layout',
    component: () => import('../views/Layout.vue'),
    redirect:'/home',
    children: [
      {
         path: 'home',
         name: 'home',
         component: () => import(/* webpackChunkName: "about" */ '../views/HomeView.vue')
      },
      {
      path: 'admin',
      name: 'admin',
      component: () => import(/* webpackChunkName: "about" */ '../views/AdminView.vue')
      },
       {
      path: 'book',
      name: 'book',
      component: () => import(/* webpackChunkName: "about" */ '../views/BookView.vue')
      },
       {
      path: 'type',
      name: 'type',
      component: () => import(/* webpackChunkName: "about" */ '../views/TypeView.vue')
      },
      {
      path: 'audit',
      name: 'audit',
      component: () => import(/* webpackChunkName: "about" */ '../views/AuditView.vue')
      },
      {
      path: 'hotel',
      name: 'hotel',
      component: () => import(/* webpackChunkName: "about" */ '../views/Hotelview.vue')
      },
       {
      path: 'reserve',
      name: 'reserve',
      component: () => import(/* webpackChunkName: "about" */ '../views/ReserveView.vue')
      },
       {
      path: 'log',
      name: 'log',
      component: () => import(/* webpackChunkName: "about" */ '../views/LogView.vue')
      },
       {
      path: 'notice',
      name: 'notice',
      component: () => import(/* webpackChunkName: "about" */ '../views/NoticeView.vue')
      }
    ]

  },

  
   
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    next();
  }
  const user = localStorage.getItem("user");
  if (!user && to.path !== '/login' && to.path !== '/register' ) {
     next("/login");
    
  }
  next();
})


export default router

import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
   history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/TodoIndex/SetProject'

    },
    {
      path: '/TodoIndex',
      name: 'TodoIndex',
      component: () => import('../views/TodoIndex.vue'),
      children: [
        {
          path: 'SetProject',
          name: 'SetProject',
          component:()=> import('../components/SetProject.vue')

        },
        {
          path: 'SetMission',
          name: 'SetMission',
          component:()=> import('../components/SetMission.vue')

        }
      ]
    }
  ]


})


export default router  

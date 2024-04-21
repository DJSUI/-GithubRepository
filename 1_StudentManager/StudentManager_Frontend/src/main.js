import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import '@/mock/Student'
import VueRouter from 'vue-router'
import router from '@/router/router'
import store from '@/store/store'
Vue.config.productionTip = false
Vue.config.devtools = true;
Vue.use(VueRouter)


new Vue({
  devtools: true,
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

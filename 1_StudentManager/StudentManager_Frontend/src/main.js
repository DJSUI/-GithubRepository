import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import '@/mock/Student'
import VueRouter from 'vue-router'
import router from '@/router/router'
import store from '@/store/store'
import axios from 'axios';
Vue.config.productionTip = false
Vue.config.devtools = true;

axios.defaults.baseURL = 'http://localhost:8085/api';

axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token');
   
    if (token) {
      config.headers['token'] = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
Vue.use(VueRouter)



new Vue({
  devtools: true,
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

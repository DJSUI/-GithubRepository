import Vue from 'vue';
import Vuex from 'vuex';
// 引入router 才可以在storejs 中使用router， 被视为组件行为
import router from '@/router/router'

// vue的use必须写在自己的文件里
Vue.use(Vuex);


export default new Vuex.Store({
  state: {
    user: null,
  
  },
   actions: {
      fetchUser() {
      // 第一种获取方式 对应PersonInfo页面
      const userData = this.state.user;
           // 执行其他逻辑，比如验证用户信息、处理用户数据等
      console.log('Vuexsj.User data:', userData);
      return userData
 
    },

    // 登出清除数据

     logout(context) {
      // 清除用户信息, 调取本地mutation 接口
      context.commit('setUser', null);

      // 可选：清除本地存储中的用户信息
      localStorage.removeItem('user');

      // 可选：重定向到登陆页面
      router.push('/LoginPage');
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      // 将获取到的 token 存储到 LocalStorage 中
      localStorage.setItem('token', state.user.token);
    }
  },
 

});



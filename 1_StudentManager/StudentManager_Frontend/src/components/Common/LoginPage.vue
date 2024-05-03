<template>
  <div>
    <!-- 可以添加图片 如果无图片则挤压输入栏到中间   -->
    <v-img
      class="mx-auto my-6"
      max-width="228"
    ></v-img>

    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="8"
      max-width="448"
      rounded="lg"
    >
      <div class="text-subtitle-1 text-medium-emphasis">账号</div>

      <v-text-field
        density="compact"
        placeholder="Email password"
        prepend-inner-icon="mdi-account-outline"
        variant="outlined"
        v-model="id"
      ></v-text-field>

      <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
        密码

        <!-- <a
          class="text-caption text-decoration-none text-blue"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          Forgot login password?</a> -->
      </div>

      <v-text-field
        :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
        :type="visible ? 'text' : 'password'"
        density="compact"
        placeholder="Enter your password"
        prepend-inner-icon="mdi-lock-outline"
        variant="outlined"
        @click:append-inner="visible = !visible"
        v-model="pwd"
      ></v-text-field>

      <v-btn
        block
        class="mb-8"
        color="blue"
        size="large"
        variant="tonal"
        @click="Login"
      >
        登陆
      </v-btn>

      <v-card-text class="text-center">
        <!-- <a
          class="text-blue text-decoration-none"
          href="#"
          rel="noopener noreferrer"
          target="_blank"
        >
          Sign up now <v-icon icon="mdi-chevron-right"></v-icon>
        </a> -->
      </v-card-text>
    </v-card>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name: 'LoginPage',
  data: () => ({
    visible: false,
    pwd: null,
    id: null,
  }),
  methods: {
    Login() {
      axios.post('/login', { id: this.id, pwd: this.pwd })
        .then(response => {
          // 登录成功，打印token信息
          console.log('response.data', response.data.data.token);
            // 将获取到的 token 存储到 LocalStorage 中
          localStorage.setItem('token', response.data.data.token);
         
          this.$store.commit('setUser', response.data.data);
          // 重定向到主页
          this.$router.push('/HomePage/PersonInfo');
        })
        .catch(error => {
          console.error('Login failed:', error);
          // 处理登录失败的逻辑
        });

    }
  }
}
</script>
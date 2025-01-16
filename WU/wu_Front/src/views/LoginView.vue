<template>
  <div>

    This is login view

    <el-form :modle="admin">
      <el-form-item>
        <el-input
          v-model="admin.name"
          placeholder="username:  "
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="admin.password"
          show-password
          placeholder="password:   "
        ></el-input>
      </el-form-item>
      <el-form-item>
        <div style="display: flex; justify-content: center;">
          <el-input
            v-model="admin.verCode"
            prefix-icon="el-icon-user"
            style="width: 42%; margin-right: 10px;"
            placeholder="verification code: "
          >
          </el-input>
          <img
            :src="captchaUrl"
            width="200px"
            high="100px"
            @click="refreshCaptcha"
          />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button @click="login"> login </el-button>
      </el-form-item>
      <div style="text-align: center;">
        <a
          href="javascript:void(0)"
          style="text-decoration: none;"
          @click="navRegister"
        >click to register </a>
      </div>
    </el-form>

  </div>
</template>


<script>
import request from '@/utils/request';
export default {
  name: 'LoginView',
  data() {
    return {
      admin: {},
      // key: Math.random(),
      // captchaUrl: "/user/captcha?key=" + this.key
      key: "",
      captchaUrl: ""

    }
  },
  mounted() {
    this.key = Math.random(),
      this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key
  },
  methods: {

    login() {
      request.post("/user/login?key=" + this.key, this.admin).then(
        res => {
          if (res.code === '0') {
            this.$message({
              message: res.msg,
              type: 'success'
            });

            console.log("user", JSON.stringify(res.data));
            localStorage.setItem("user", JSON.stringify(res.data));

            this.$router.push("/")
          } else {
            this.$message({
              message: res.msg,
              type: 'error'
            });
            this.refreshCaptcha();

          }
        }
      )
    },
    navRegister() {
      this.$router.push("/register")
    },
    refreshCaptcha() {
      this.key = Math.random();
      this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key
      this.admin.verCode = "" // clear verification code
    }



  },


}
</script>

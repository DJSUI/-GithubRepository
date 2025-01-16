<template>
  <div>

    This is RegisterView

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
        <el-select
          v-model="admin.role"
          placeholder="请选择"
        >
          <el-option
            label="老师"
            value="ROLE_TEACHER"
          ></el-option>
          <el-option
            label="学生"
            value="ROLE_STUDENT"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="register"> register </el-button>
      </el-form-item>
      <div style="text-align: center;">
        <a
          href="javascript:void(0)"
          style="text-decoration: none;"
          @click="navLogin"
        >click to Login </a>
      </div>
    </el-form>
  </div>
</template>


<script>
import request from '@/utils/request';
export default {
  name: 'RegisterView',
  data() {
    return {
      admin: {}
    }
  },
  created() {


  },
  methods: {

    register() {
      this.admin.role = "ROLE_STUDENT"
      request.post("/user/register", this.admin).then(
        res => {
          if (res.code === '0') {
            this.$message({
              message: "register success",
              type: 'success'
            });
            this.$router.push("/login")
          } else {
            this.$message({
              message: res.msg,
              type: 'error'
            });
          }
        }
      )
    },
    navLogin() {
      this.$router.push("/login")
    }



  },


}
</script>

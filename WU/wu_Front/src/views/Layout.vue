<template>
  <div>
    <el-container>
      <el-header style="background-color: #4c535a">
        <img
          src="@/assets/logo.png"
          style="width: 35px"
        >
        <span style="font-size: 20px; margin-left: 15px;color: white">手拉手带小白做毕设</span>
        <el-dropdown style="float: right;height: 60px; line-height: 60px;">
          <span
            class="el-dropdown-link"
            style="color: white; font-size: 16px;"
          >
            {{ user.name }}
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <div @click="logout">
                  退出登陆
                </div>
              </el-dropdown-item>

            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh;background-color: #545c64; width: 250px">
        <el-menu
          :default-active="$route.path"
          router
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu
            index="2"
            v-if="user.role === 'ROLE_ADMIN'"
          >
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>用户管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin">用户信息</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/type">图书分类</el-menu-item>
              <el-menu-item index="/book">图书信息</el-menu-item>
              <el-menu-item index="/audit">请假审核</el-menu-item>
              <el-menu-item index="/hotel">酒店信息</el-menu-item>
              <el-menu-item index="/reserve">预定信息</el-menu-item>
              <el-menu-item index="/log">日志信息</el-menu-item>
              <el-menu-item
                index="/notice"
                v-if="user.role === 'ROLE_ADMIN'"
              >公告信息</el-menu-item>

            </el-menu-item-group>
          </el-submenu>

        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>


<script>
export default {
  name: 'Layout',
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods: {
    logout() {
      console.log("logout");
      localStorage.removeItem("user");
      this.$router.push("/login");
    }
  }
}
</script>

<style>
.el-menu {
  border-right: none !important;
}
</style>

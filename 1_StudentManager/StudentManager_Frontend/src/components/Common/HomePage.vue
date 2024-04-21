<template>
  <v-app>
    <!-- 应用栏（左侧栏目） -->
    <v-app-bar
      app
      style="background-color: skyblue;"
    >
      <!-- 抽屉控制 -->
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title style="color: white;">{{ toolbarTitle }}</v-toolbar-title>
    </v-app-bar>

    <!-- 抽屉 （上层栏目）-->
    <v-navigation-drawer
      v-model="drawer"
      app
    >
      <v-list dense>
        <!-- 导航链接 -->
        <!--首页导航链接 -->
        <v-list-item @click="toPersonInfo">
          <v-list-item-icon>
            <v-icon>mdi-account</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>登陆者信息</v-list-item-title>

          </v-list-item-content>
        </v-list-item>
        <!-- 学生信息导航链接 -->
        <v-list-item @click="toStudentInfo">
          <v-list-item-icon>
            <v-icon>mdi-school</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>学生信息</v-list-item-title>

          </v-list-item-content>
        </v-list-item>
        <!-- 测试链接  -->
        <v-list-item @click="toTestPage">
          <v-list-item-icon>
            <v-icon>mdi-wrench</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>测试页面</v-list-item-title>

          </v-list-item-content>
        </v-list-item>

        <!-- 其他链接 -->
      </v-list>
    </v-navigation-drawer>

    <!-- 主内容 -->
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>



<script>
export default {
  name: 'App',
  data() {
    return {
      drawer: null,
      toolbarTitle: "登陆者信息"
    }
  },

  methods: {
    toStudentInfo() {
      this.toolbarTitle = "学生信息查询"
      this.$router.replace({ name: "StudentInfo" }, () => { }, () => { })
    },
    toPersonInfo() {
      this.toolbarTitle = "登陆者信息"
      this.$router.replace({ name: "PersonInfo" }, () => { }, () => { })
    },
    toTestPage() {
      this.toolbarTitle = "测试页面"
      this.$router.replace({ name: "TestPage" }, () => { }, () => { })
    },

  }


}

</script>
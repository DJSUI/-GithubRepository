<template>
  <div class="center-container">
    <div class="content">
      <h1 style="margin-bottom: 50px;">欢迎您，{{ userInfo.name }}</h1>
      <div>
        <p>教职工账号 {{ userInfo.id }} </p>
        <p>担任课程: {{ userInfo.course }}</p>
        <!-- <p v-if="tClass">担任班级: {{ userInfo.tClass }}</p> -->


        <v-btn
          @click="logout"
          style=" color: white; background-color: red;"
        >logout</v-btn>
        <!-- 在此添加其他登录者信息字段 -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PersonInfo",


  // 第一种获取状态方式
  mounted() {

    this.$store.dispatch('fetchUser').then(userData => {
      this.userInfo = userData;
      console.log("Personinfo.this.userInfo", this.userInfo);
    }).catch(error => {
      console.error("Error fetching user data:", error);
    });
  },
    //  第二种获取状态方式 ,不需要dispatch 返回数值， 直接取状态
  // async beforeMount() { 
  //     this.userInfo = JSON.parse(JSON.stringify(this.$store.state.user))
  // },
  
  data() {
    return {
      userInfo: {

      }
    }
  },
  methods: {
    logout() {
      this.$store.dispatch('logout');
    }
  }

}



</script>
<style scoped>
.center-container {
  display: flex;
  justify-content: center;
  /* 水平居中 */
  align-items: center;
  /* 垂直居中 */
  height: 50vh;
  /* 设置高度为视口高度 */
}

.content {
  text-align: left;
  /* 内容水平居中 */
}
</style>
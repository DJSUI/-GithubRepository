<template>
  <div>
    <Mission
      v-for="(item, index) in addlist"
      :key="index"
      :name="item.name"
      :index="index"
      @delHandler="delMssion"
    ></Mission>
  </div>
</template>

<script setup>
import Mission from './molecular/Mission.vue'
import { reactive } from 'vue'

const addlist = reactive([{ name: "这是测试任务1" }, { name: "这是测试任务2" }, { name: "这是测试任务3" }]);
const addlist1 = reactive(["sdj", "sdf", "sdfs"]);


function delMssion(index) {
  console.log("index", index);
}
</script>

<style scoped></style>

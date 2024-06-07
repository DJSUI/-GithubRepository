<template>
  <div>
    <Mission
      v-for="(item, index) in addlist"
      :key="index"
      :name=item.name
      :index=index
      @delHandler="delMssion"
    ></Mission>

  </div>
</template>

<script>
import Mission from './molecular/Mission.vue'
import { ref } from 'vue'
export default {
  name: 'SetMission',
  components: { Mission },
  setup() {
    const addlist = ref([]);
    addlist.value = [{ name: "这是测试任务1" }, { name: "这是测试任务2" }, { name: "这是测试任务3" }]

    function delMssion(index) {
      console.log("index", index);
    }

    return {
      addlist,
      // delMssion
    }
  }
}
</script>


<style scoped></style>
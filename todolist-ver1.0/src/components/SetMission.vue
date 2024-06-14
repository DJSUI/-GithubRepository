<template>
  <div>
    <v-btn
      text="Add Mission"
      @click="handleDialogSwitch"
    ></v-btn>
    <v-btn
      text="Reset Project"
      @click="resetProject"
    ></v-btn>

    <v-dialog
      v-model="dialogSwitch"
      max-width="600"
      max-height="400"
      scrollable
    >

      <v-card
        prepend-icon="mdi-earth"
        title="Create Mssion"
      >

        <v-card-text>
          <v-text-field
            label="Create Mssion"
            v-model="missionName"
            @keyup.enter="handleSave"
          ></v-text-field>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-btn
            text="Close"
            @click="handleDialogSwitch"
          ></v-btn>

          <v-spacer></v-spacer>

          <v-btn
            color="surface-variant"
            text="Save"
            variant="flat"
            @click="handleSave"
          ></v-btn>
        </v-card-actions>
      </v-card>

    </v-dialog>
    <Mission
      v-for="(item, index) in addList"
      :key="index"
      :name="item.name"
      :index="index"
      @delHandler="delMssion"
    >
    </Mission>

  </div>
</template>

<script setup>
import Mission from './molecular/Mission.vue'
import { useRouter } from 'vue-router';
import { onMounted, reactive, ref } from 'vue'

//const addList = reactive([{ name: "这是测试任务1" }, { name: "这是测试任务2" }, { name: "这是测试任务3" }]);
const addList = reactive(JSON.parse(sessionStorage.getItem('addList')) || []);
const dialogSwitch = ref(false);
const missionName = ref("")
const router = useRouter();



function delMssion(index) {
  console.log("SetMission index", index);
  addList.splice(index, 1)
  updateSessionStorage();
}


function handleDialogSwitch() {
  console.log("DialSwitch has been called ");
  dialogSwitch.value = !dialogSwitch.value;
  console.log("DialSwitch value is ", dialogSwitch.value);

}

function handleSave() {
  if (missionName.value.trim()) {
    // 使用 splice 在数组的第一个位置插入新的任务
    addList.splice(0, 0, { name: missionName.value });
    missionName.value = ""; // 重置输入框
    updateSessionStorage();
  }
  handleDialogSwitch();
}

function resetProject() {
  router.push({ name: "SetProject" }, () => { }, () => { })

}

function updateSessionStorage() {
  sessionStorage.setItem('addList', JSON.stringify(addList))
}

onMounted(() => {

  console.log("load onMouned ");
  const storedAddList = sessionStorage.getItem('addList');
  if (storedAddList) {
    addList.splice(0, addList.length, ...JSON.parse(storedAddList));
  }
})

</script>

<style scoped></style>

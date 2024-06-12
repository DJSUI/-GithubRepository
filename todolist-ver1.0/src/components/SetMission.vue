<template>
  <div>
    <v-dialog
      v-model="dialogSwitch"
      max-width="600"
      max-height="400"
      scrollable
    >
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          text="Add Mission"
          @click="handleDialogSwitch"
        ></v-btn>
      </template>


      <v-card
        prepend-icon="mdi-earth"
        title="Select Country"
      >
        <v-divider class="mt-3"></v-divider>

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
      v-for="(item, index) in addlist"
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
import { reactive, ref } from 'vue'

const addlist = reactive([{ name: "这是测试任务1" }, { name: "这是测试任务2" }, { name: "这是测试任务3" }]);
const dialogSwitch = ref(false);
const missionName = ref("")


// const activatorProps = ref(true);
function delMssion(index) {
  console.log("SetMission index", index);
  addlist.splice(index, 1)
}


function handleDialogSwitch() {
  dialogSwitch.value = !dialogSwitch.value;
}

function handleSave() {
  if (missionName.value.trim()) {
    // 使用 splice 在数组的第一个位置插入新的任务
    addlist.splice(0, 0, { name: missionName.value });
    missionName.value = ""; // 重置输入框
  }
  handleDialogSwitch();

}
</script>

<style scoped></style>

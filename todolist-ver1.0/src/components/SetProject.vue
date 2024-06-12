<template>
  <div>

    <v-sheet
      class="mx-auto"
      width="300"
      style="margin-top: 150px;"
    >
      <v-form @submit.prevent>
        <v-text-field
          v-model="PJname"
          label="ProjectName"
          @keyup.enter="setName"
        ></v-text-field>
        <v-btn
          class="mt-2"
          type="submit"
          block
          @click="setName"
        >set Name</v-btn>
      </v-form>
    </v-sheet>
  </div>
</template>

<script setup>
import { useDataStore } from '../store/index.js'
import { useRouter } from 'vue-router';
import { ref } from 'vue'

const router = useRouter();
const dataStore = useDataStore();
const PJname = ref(null);
function setName() {
  if (PJname.value == null || PJname.value == '') {
    dataStore.setName("Please Input Project Name ")
  } else {
    console.log("para", PJname.value);
    dataStore.setName(PJname.value)
    router.push({ name: "SetMission" }, () => { }, () => { })
  }
}



</script>


<style scoped></style>
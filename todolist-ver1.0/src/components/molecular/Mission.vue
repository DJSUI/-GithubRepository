<template>
  <div>
    <v-card
      class="mx-auto"
      max-width="500px"
      style="display: flex"
    >
      <h3 style="text-align: center; width: 400px;">{{ name }}</h3>
      <v-btn
        density="compact"
        icon="mdi-close"
        style="margin-left: auto;"
        @click="emitDel"
      ></v-btn>
    </v-card>
  </div>
</template>

<script setup>

const props = defineProps({
  index: Number,
  name: String
})
const emit = defineEmits(['delHandler'])
function emitDel() {

  emit("delHandler", props.index);
}


</script>

<style scoped></style>

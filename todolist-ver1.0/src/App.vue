<template>
  <v-app>
    <TodoIndex />

  </v-app>
</template>

<script>
import TodoIndex from './views/TodoIndex.vue'

export default {
  name: 'App',

  components: {
    TodoIndex,
  },

  data: () => ({

  }),
}
</script>

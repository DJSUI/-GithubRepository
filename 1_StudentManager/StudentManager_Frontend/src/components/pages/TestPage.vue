<template>
  <div>
    <h1>This is TEST page </h1>
    <a href="#" @click="test">testtest</a>
  </div>
</template>


<script>
import axios from 'axios'
export default {
  name: "TestPage",
  methods: {
    test() {
        axios.get(`http://localhost:8085/api/login/search`)
          .then(response => {
            this.students = response.data
          })
          .catch(error => {
            console.error('!!!!edit wrong ', error)
          })
    }
  }

}



</script>
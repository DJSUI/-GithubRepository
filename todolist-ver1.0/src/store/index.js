import { defineStore } from "pinia";


export const useDataStore = defineStore('data', {
  state: () => { 
    return { storeName: 'projectName' }
  },
  getters: {
    
  },
  actions: {
    setName(projectName) {
      this.storeName =  projectName
    }
  }

})
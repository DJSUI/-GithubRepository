import { defineStore } from "pinia";


export const useDataStore = defineStore('data', {
  state: () => { 
    return { storeName: 'projectName' }
  },
  getters: {
    
  },
  actions: {
    setName(projectName) {
      this.storeName = projectName
      
    },
    initializeStore() { 
      const storeName = sessionStorage.getItem('pjName');
      if (storeName) {
        this.storeName = storeName;
      }
    }
  }

})
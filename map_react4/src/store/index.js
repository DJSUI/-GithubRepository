import webMapReducer from './modules/selectButtonClick'
import { configureStore } from '@reduxjs/toolkit'




const store = configureStore({
  reducer: {
    Maps: webMapReducer
  }
})

export default store
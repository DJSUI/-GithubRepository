import { createSlice } from "@reduxjs/toolkit"


const genboDetilResultStore = createSlice({
    name:'Maps',
    initialState:{
        showGBselectResult: false,
        showGBpropertyDetail: false,
        showRouteListCP: true,    // 可以同时切换RouteList中的两个组件的显示和隐藏 
        showRouteSearch:false,
        addressList: [],  //接受经纬度坐标
        fetchNaviInfoTrigger: false,
   
        
   

    },


    reducers: {
        // TODO: 优化关闭逻辑， 关闭父标签时候， 子标签也一并关闭 

        // 控制固定悬浮窗口GBselectResult
        changeShowGBselectResult(state){
            state.showGBselectResult = !state.showGBselectResult
    
            // if (state.showGBselectResult === false) {
            //      state.popupVisible2 = false   
            // }
        },
         // 控制浮动窗口GBpropertyDetail
         changeShowGBpropertyDetail(state) {
             state.showGBpropertyDetail = !state.showGBpropertyDetail;
         },
        
        //  控制RouteList中的两个组件的显示和隐藏 
        changeShowRouteListCP(state) {
            state.showRouteListCP = ! state.showRouteListCP
        },

    
         // 控制浮动窗口RouteSearch
         changeShowRouteSearch(state) {
            
             state.showRouteSearch = !state.showRouteSearch;
         },

        //  接收RouteSearch里的数据 
        receiveAddressList(state, arr) { 
            state.addressList = [];
            state.addressList =  JSON.parse(JSON.stringify(arr.payload))
            console.log("the List of redux = ",state.addressList);
        },
        //   控制通过api 画线开关
        changeFetchNaviInfoTrigger(state) {
            state.fetchNaviInfoTrigger = !state.fetchNaviInfoTrigger;
        }
  
        


    }
})

// 1用变量接收所有动作函数
const { changeShowGBselectResult } = genboDetilResultStore.actions
const { changeShowGBpropertyDetail } = genboDetilResultStore.actions
const { changeShowRouteListCP } = genboDetilResultStore.actions
const { changeShowRouteSearch } = genboDetilResultStore.actions
const { receiveAddressList } = genboDetilResultStore.actions
const { changeFetchNaviInfoTrigger } = genboDetilResultStore.actions



const reducer = genboDetilResultStore.reducer

// 2将存储东西的变量进行导出 
export { changeShowGBselectResult }
export { changeShowGBpropertyDetail }
export { changeShowRouteListCP }
export { changeShowRouteSearch }
export { receiveAddressList }
export { changeFetchNaviInfoTrigger }


export default reducer

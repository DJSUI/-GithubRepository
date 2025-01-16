import React, { useState, useRef, useEffect, useMemo, useCallback } from 'react';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import {receiveAddressList,changeFetchNaviInfoTrigger,changeShowRouteSearch} from '../../store/modules/selectButtonClick'
import AddressMarker from './AddressMarker/AddressMarker'
import './RouteSearch.css'; 
import Button from 'react-bootstrap/Button';

const RouteSearch = () => {
      const dispatch = useDispatch()
    // *******初始化浮动窗口外观**************
    const [isDragging, setIsDragging] = useState(false);
    const [position, setPosition] = useState({ x: 230, y: 45 }); 
  // *******************************



    // ******控制窗口移动走向****************** 
    const ref = useRef();
    const onMouseDown = (e) => {
      setIsDragging(true);
      ref.current.offset = {
        x: e.clientX - ref.current.getBoundingClientRect().left,
        y: e.clientY - ref.current.getBoundingClientRect().top,
      };
    };
    const onMouseUp = () => {
      setIsDragging(false);
    };
    const onMouseMove = (e) => {
      if (isDragging) {
        const x = e.clientX - ref.current.offset.x;
        const y = e.clientY - ref.current.offset.y;
        setPosition({ x, y });
      }
    };
    // ************************************



      // *******组件循环创建逻辑**************
    
      const [addressInfos, setAddressInfos] = useState([{ address: "", del: false }])
      const [AddressCount, setAddressCount] = useState(1)

      // 点击link时候 调用addAddressCount 函数 
      const addAddressCount = () => {
        setAddressCount(AddressCount + 1)  // 累加计数？ 可以删除吗？
        const infoBk = JSON.parse(JSON.stringify(addressInfos)) //深度拷贝
        infoBk.push({ address: "", del: false }); //infoBk 的数组中添加一个新的对象
        setAddressInfos(infoBk) //调用setAddressInfos函数 ，参数为复制好的对象 
      }

      // useCallback 相当于vue中的watch函数，当检测到addressInfos里的数据发生变化
      //才会调用useCallback里的地方匿名箭头函数 
      const addressChangeHandle = useCallback((index, address) => {
        const infoBk = JSON.parse(JSON.stringify(addressInfos))  // 深度拷贝传给infoBK 
        infoBk[index] = { address, del:false} //用来指定替换的数组中的元素 
        setAddressInfos(infoBk)  //调用setAddressInfos函数 ，参数为复制好的对象 
      },[addressInfos])

      // useMemo 和 useCallback 非常相似，
      // 它们其中的区别 useMemo 是记忆其中的数值，避免重新计算  
      //              useCallback是记忆其中的函数，避免重新计算 
      const addressItems = useMemo(() => {
        
        const items = []
        for (let i = 0; i < AddressCount; i++) {
          items.push(<AddressMarker key={i} value={addressInfos[i]?.address ?? ""} onInput={addressChangeHandle} count={i + 1} del={() => {
            const infoBk = JSON.parse(JSON.stringify(addressInfos))
            setAddressCount(Math.max(AddressCount - 1, 1))
            infoBk[i].del = true;
            
            setAddressInfos(infoBk.filter(item => !item.del))

            
          }} />)
        }

        return items
      },[AddressCount, addressChangeHandle, addressInfos])
      // *****************************





    // *************选项卡控制逻辑*****************************

    // 手动初始化选项卡组件
    const [activeTab, setActiveTab] = useState('tab1');

    useEffect(() => {
      // 在页面加载时设置默认选定标签的字体颜色为黑色
      const tabElement = document.getElementById(activeTab);
      if (tabElement) {
        tabElement.style.color = 'black';
      }
    }, [activeTab]); // 当activeTab改变时运行effect

    const handleTabClick = (tab) => {
      setActiveTab(tab);
    };

    // ************************************
  
  // **********坐标转化（点击按钮后进行转化）************
  // 异步函数
    const GecodingAddress = async (item) => {
    try {
        const response = await fetch(`https://api.mapbox.com/geocoding/v5/mapbox.places/${encodeURIComponent(item)}.json?access_token=pk.eyJ1IjoiZGFpamlhbmciLCJhIjoiY2x1cm51NDRxMDlheDJpbzk2ajhzN3p1bSJ9._Jjt7AubEPM0RCoDCM4NTg`);
        const data = await response.json();
        
        if (data.features && data.features.length > 0) {
            const { center } = data.features[0];
            console.log("converting coordinates", center);
            return center; // 返回中心坐标
        } else {
            console.log('未找到地址');
            throw new Error('未找到地址');
        }
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
};
  // 同步函数 但是async 将其变为了异步函数
const convertCord = async () => {
    try {
        const centerCoordinates = [];
      for (const ele of addressInfos) {
            // 异步函数完成后在执行后续代码
            const center = await GecodingAddress(ele.address);
            centerCoordinates.push(center);
        }
        console.log("converted coordinates.current=", centerCoordinates);
        dispatch(receiveAddressList(centerCoordinates));
    } catch (error) {
        console.error('Error during geocoding:', error);
    }
};
        
     

    

    // ***************************






  //********** 可删除test用************
        //case1: 打印数组里的数值
        // const printData = () => {
        //   console.log(
        //           addressInfos
        //       );
        // }
        
        // case2: 打印出数组里面的address信息
        // const printData = () => {
        //   addressInfos.forEach(element => {

        //       console.log(element.address);
        //   });
        // }

        // case3: 打印出用于传递给redux数组信
        // const printData = () => {
        //   console.log(
        //           coordinates
        //       );
        // }

        
  // **********************

  


  // *****控制窗口显示或者隐藏**********************
  const showRouteSearch = useSelector(state => state.Maps.showRouteSearch);
  //  如果下方数值flase 取反 true 则返回  null  提前结束本节所有代码运行

  if (!showRouteSearch) return null;

  //  如果数值true 取反胃false 则跳过
  return (
    
    <div
      ref={ref}
      className="RouteSearch-window"
      style={{
        left: `${position.x}px`,
        top: `${position.y}px`,
      }}
      onMouseDown={onMouseDown}
      onMouseUp={onMouseUp}
      onMouseLeave={onMouseUp}
      onMouseMove={onMouseMove}
    >
   
      <div className="menu-container" style={{ display: 'flex', flexDirection: 'row',width: "250px"}}>

        <div style={{ display: 'flex', flexDirection: 'column', flex: "12" ,width: "250px"  }}>
          <div className="window-header" style={{ width: '270px', justifyContent: 'space-between' }}>
            <h4 style={{color:'black'}}>ルート検索</h4>
            {/* 更新关闭按钮 */}
            <button className="close-button" onClick={() => dispatch(changeShowRouteSearch())} >X</button>
          </div>
          <div className="col routelist" style={{ flex: '1', }}>
            {addressItems}
            

          </div>
         
          <a href="#" style={{ marginTop: '25px' }} onClick={addAddressCount}> ＋　お届け先を追加</a> 
          <div style={{ display: 'flex', justifyContent: 'space-between' ,marginTop:'10px',marginBottom:'10px'}}>
            {/* <Button variant="outline-dark" size="sm"onClick={()=>dispatch(receiveAddressList(addressInfos))}>ルート保存</Button> */}
            <Button variant="outline-dark" size="sm" onClick={()=>convertCord()}>ルート保存</Button>
            <Button variant="outline-dark" size="sm" onClick={changeFetchNaviInfoTrigger} >ルート検索</Button>
          </div>
        </div>

      </div>
     
    </div>
  );
};

export default RouteSearch;

import React, { useState, useRef, useEffect } from 'react';
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { changeShowGBpropertyDetail } from '../../store/modules/selectButtonClick'

import './GBpropertyDetail.css'; 


const GBpropertyDetail = () => {
  // *******初始化浮动窗口外观**************
  const [isDragging, setIsDragging] = useState(false);
  const [position, setPosition] = useState({ x: 525, y: 45 });
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




  // *****控制窗口显示或者隐藏**********************
  const dispatch = useDispatch()
  const showGBpropertyDetail = useSelector(state => state.Maps.showGBpropertyDetail);
  //  如果下方数值flase 取反 true 则返回  null  提前结束本节所有代码运行

  if (!showGBpropertyDetail) return null;

  //  如果数值true 取反胃false 则跳过
  return (
    
    <div
      ref={ref}
      className="GBpropertyDetail-window"
      style={{
        left: `${position.x}px`,
        top: `${position.y}px`,
      }}
      onMouseDown={onMouseDown}
      onMouseUp={onMouseUp}
      onMouseLeave={onMouseUp}
      onMouseMove={onMouseMove}
    >
   
      <div className="menu-container" style={{ display: 'flex', flexDirection: 'row', overflow: 'scroll', height: '400px', width: '300px' }}>
          
        <div style={{ display: 'flex', flexDirection: 'column', flex: "12", overflow: 'auto' }}>
          <div className="window-header" style={{ width:'270px'}}>
            原簿属性検索結果
            {/* 更新关闭按钮 */}
            <button className="close-button" onClick={() => dispatch(changeShowGBpropertyDetail())} >X</button>
          </div>
          <div className="col" style={{ flex: '1', width: "100%" }}>
            
            <div style={{ display: 'flex', justifyContent: 'space-between',width:'270px' }}>
              {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
              <a href='#' style={{ fontSize: '18px', marginLeft: '10px' }}>建築物周辺情報</a>
              <button style={{ backgroundColor: 'orange', color: 'white' }}>地図ジャンプ</button>
            </div>
            <span style={{ marginLeft: '20px' }}>八王子市尾崎町１ー７−１０</span>
            <hr style={{ borderWidth: '2px' }} />
            <span style={{ marginLeft: '20px' }}>管理者    铃木三郎</span>
            <hr style={{ borderWidth: '2px' }} />
          </div>
          <div className="col" style={{ flex: '3' }}>
            <ul className="nav nav-tabs" id="tabContent">
              <li className={`nav-item ${activeTab === 'tab1' ? 'active' : ''}`}>
                <a className="nav-link" onClick={() => handleTabClick('tab1')} data-bs-toggle="tab" href="#tab1">一覧</a>
              </li>
              <li className={`nav-item ${activeTab === 'tab2' ? 'active' : ''}`}>
                <a className="nav-link" onClick={() => handleTabClick('tab2')} data-bs-toggle="tab" href="#tab2">追加</a>
              </li>
              <li className={`nav-item ${activeTab === 'tab3' ? 'active' : ''}`}>
                <a className="nav-link" onClick={() => handleTabClick('tab3')} data-bs-toggle="tab" href="#tab3">転居</a>
              </li>
            </ul>

            <div className="tab-content">
              <div className={`tab-pane fade ${activeTab === 'tab1' ? 'show active' : ''}`} id="tab1" >
                <hr style={{ borderWidth: '2px' }} />
                <div style={{ paddingLeft: '20px', display: 'flex', }} >
                  <h6 style={{ marginBottom: '0px' }}>筆頭者（カナ）</h6>
                  <span>アイオ</span>
                </div>
                <hr style={{ borderWidth: '2px' }} />




                <h6 style={{ paddingLeft: '20px' }}>家族＊同居人</h6>
                <span style={{ paddingLeft: '40px' }}>鈴木大朗</span>
                <br />
                <span style={{ paddingLeft: '40px' }}>鈴木次郎</span>
                <hr style={{ borderWidth: '2px' }} />
                <h6 style={{ paddingLeft: '20px' }}>事業証明</h6>


              </div>
              <div className={`tab-pane fade ${activeTab === 'tab2' ? 'show active' : ''}`} id="tab2">
                <div style={{ display: 'flex', justifyContent: 'flex-end', marginRight: '10px' }}>
                  <button class="btn btn-primary" style={{ backgroundColor: 'white', color: 'black', }}>編集</button>
                </div>

                <hr style={{ borderWidth: '2px' }} />

                <h6 style={{ paddingLeft: '20px' }}>家族＊同居人</h6>
                <hr style={{ borderWidth: '2px' }} />

                <h6 style={{ paddingLeft: '20px' }}>事業証明</h6>
                <hr style={{ borderWidth: '2px' }} />


                <h6 style={{ paddingLeft: '20px' }}>事業証明</h6>
                <hr style={{ borderWidth: '2px' }} />
              </div>
              <div className={`tab-pane fade ${activeTab === 'tab3' ? 'show active' : ''}`} id="tab3">
                <h3>这是転居页面内容</h3>
              </div>
            </div>
          </div>
        </div>
        </div>
     
    </div>
  );
};

export default GBpropertyDetail;

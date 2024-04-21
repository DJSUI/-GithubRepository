import React, { useState, useEffect } from 'react';
import './FunctionMenu.css';
import NameAddressSelect from './NameAddressSelect/NameAddressSelect'
import RouteList from './RouteList/ RouteList'

const FunctionMenu = () => {
   const [activeTab, setActiveTab] = useState('search'); // 默认选择的标签为検索

  useEffect(() => {
    // 在组件加载时设置所有标签的字体颜色为黑色
    const tabs = document.querySelectorAll('.nav-link');
    tabs.forEach(tab => {
      tab.style.color = 'black';
    });
  }, []); // 空数组表示这个effect仅在组件挂载时运行一次

  const handleTabClick = (tabName) => {
    setActiveTab(tabName);
  };
  return (
    <div style={{backgroundColor:'white'}}>
        <div>
        <ul className="nav nav-tabs" id="tabContent" style={{  justifyContent:'stretch', display: 'flex' }}>
          <li className={`nav-item ${activeTab === 'home' ? 'active' : ''}`} style={{ width: '25%' }}>
            {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
            <a href="#"  className="nav-link"  onClick={() => handleTabClick('home')} id="home">レイヤ</a>
          </li>
          <li className={`nav-item ${activeTab === 'search' ? 'active' : ''}`} style={{ width: '20%' }}>
            {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
            <a href="#" className="nav-link"  onClick={() => handleTabClick('search')} id="search">検索</a>
          </li>
          <li className={`nav-item  ${activeTab === 'apply' ? 'active' : ''}`} style={{ width: '30%' }}>
            {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
            <a href="#" className="nav-link" onClick={() => handleTabClick('apply')} id="apply">付加情報</a>
          </li>
          <li className={`nav-item ${activeTab === 'route' ? 'active' : ''}`} style={{ width: '25%' }}>
            {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
            <a href="#" className="nav-link" onClick={() => handleTabClick('route')} id="route">ルート</a>
          </li>
        </ul>
      </div>
        <div className="tab-content"  >
          {/* {activeTab === 'home' && <div>Home Content</div>} */}
          {activeTab === 'search' && <NameAddressSelect />}
          {/* {activeTab === 'apply' && <div>Apply Content</div>} */}
          {activeTab === 'route' && <RouteList/>}
        </div>
    </div>
  )
}

export default FunctionMenu;

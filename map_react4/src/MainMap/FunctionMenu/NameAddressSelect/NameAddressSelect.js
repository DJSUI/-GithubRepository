// Accordion.js

import React, { useState } from 'react';
import {useDispatch} from 'react-redux'
import classNames from 'classnames'
import './NameAddressSelect.css'; 
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import NameDetailSelect from './NameDetailSelect/NameDetailSelect.js'
import {changeShowGBselectResult} from '../../../store/modules/selectButtonClick.js'


const NameAddressSelect = () => {
  // 下方数值0原本为null 调为0 默认打开
  const [activeIndex, setActiveIndex] = useState(null); // 用于跟踪当前激活的手风琴项

  // 处理点击事件
  const toggle = (index) => {
    setActiveIndex(activeIndex === index ? null : index); // 切换展开/折叠
    
  };
  
  // useDispatch需要在js总定义， 不能直接用于html 语句里
  const dispatch = useDispatch()
  

  return (
    <div>
      <div className="date"> 
       <span className={classNames('arrow', {'expand':activeIndex === 0} )}></span> 
       <button className={`accordion ${activeIndex === 0 ? 'active' : ''}`} onClick={() => toggle(0)}>
        原簿属性検索
      </button>
      </div> 
      <div className={`panel ${activeIndex === 0 ? 'show' : ''}`}>
     
        <div className="form-container" style={{color:'black'}}>

            <label htmlFor="name" >氏名</label>
          <Form.Control size="sm" type="text"  />
           <label htmlFor="address">住所</label>
           <Form.Control size="sm" type="text"  />
              <label htmlFor="name">検索範囲(m)</label>
                 <Form.Control size="sm" type="text"  />
          <NameDetailSelect />
          {/* <button className="search-button"  >検索</button> */}
          <div style={{ textAlign:'end'}}>
             <Button  className="search-button" style={{backgroundColor:'#f5811b',width:'100px',height:'30px'}} variant="secondary" size="sm" onClick={() => dispatch(changeShowGBselectResult())}>
            検索
        </Button>
           </div>
      </div>
    </div>

    <div className="date"> 
       <span className={classNames('arrow', {'expand':activeIndex === 1} )}></span> 

      <button className={`accordion ${activeIndex === 1 ? 'active' : ''}`} onClick={() => toggle(1)}>
        施設検索
      </button>
    </div>
      <div className={`panel ${activeIndex === 1 ? 'show' : ''}`}>
        <p>Lorem ipsum...</p>
      </div>

      <div className="date"> 
       <span className={classNames('arrow', {'expand':activeIndex === 2} )}></span> 
      <button className={`accordion ${activeIndex === 2 ? 'active' : ''}`} onClick={() => toggle(2)}>
        交差点名検索
      </button>
      </div>

      <div className={`panel ${activeIndex === 2 ? 'show' : ''}`}>
        <p>Lorem ipsum...</p>
      </div>

      <div className="date"> 
       <span className={classNames('arrow', {'expand':activeIndex === 3} )}></span> 
      <button className={`accordion ${activeIndex === 3 ? 'active' : ''}`} onClick={() => toggle(3)}>
        付加情報検索
      </button>
      </div>

      <div className={`panel ${activeIndex === 3 ? 'show' : ''}`}>
        <p>Lorem ipsum...</p>
      </div>


    </div>
  );
};

export default NameAddressSelect;

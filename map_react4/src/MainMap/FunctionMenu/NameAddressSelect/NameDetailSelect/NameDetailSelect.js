

import React, { useState } from 'react';
import './NameDetailSelect.css'
import classNames from 'classnames'

const NameDetailSelect = () => {
  const [activeIndex, setActiveIndex] = useState(null); // 用于跟踪当前激活的手风琴项

  // 处理点击事件
  const toggle = (index) => {
    setActiveIndex(activeIndex === index ? null : index); // 切换展开/折叠
  };

  return (
    <div>
        <div className="date">
            <span className={classNames('arrow', {'expand':activeIndex === 0} )}></span>       
            <button className={`detailAccordion ${activeIndex === 0 ? 'active' : ''}`} onClick={() => toggle(0)}>
               詳細な検索設定
            </button>
        </div>

       <div className={`panel ${activeIndex === 0 ? 'show' : ''}`}>
       <div className="form-group">
            <button htmlFor="name">旧住所</button>
            <input type="text" id="name" className="form-control" placeholder="住所を入力"/>
          </div>

         <div className="form-group">
           <button htmlFor="address">事業所個別郵便番号</button>
           <input type="text" id="address" className="form-control" placeholder="郵便番号を入力"/>
         </div>

        <div className="form-group">
          <button htmlFor="name">転居者氏名</button>
          <input type="text" id="name" className="form-control" placeholder="氏名を入力"/>
        </div>

        <div className="form-group">
          <button htmlFor="name">郵便物転送開始年月日</button>
          <input type="date" id="name" className="form-control" />
        </div>


       </div>

    </div>
  );
};

export default NameDetailSelect;

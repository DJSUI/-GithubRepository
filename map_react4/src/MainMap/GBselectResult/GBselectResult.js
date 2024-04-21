import React, { useState} from 'react';

import GBselectResultList from './GBselectResultList/GBselectResultList';




import './GBselectResult.css';

const GBselectResult = () => {
  
  // *****循环结果集合组件*********
    // eslint-disable-next-line no-unused-vars
    const [ResultCount, setResultCount] = useState(5)

    const ResultList = [];
    for (let i = 1; i < ResultCount; i++) {
      ResultList.push(<GBselectResultList key={i} />);
    }
  // ***************************

  return (
    // TODO: 内容无法实现滑动 
    <div className="menu-container" style={{ display: 'flex', flexDirection: 'row', height: '655px',width:'300px', overflow: 'scroll',position:'fixed'}}>
      <div style={{ display: 'flex', flexDirection: 'column',width:'300px'}}>
        <div className="col" style={{ width: "100%", fontWeight: "bold", textAlign: "center",height:'25px'}}>
          原簿属性検索結果
          <hr style={{ borderWidth: '2px' }} />
        </div>

        <div  style={{ paddingLeft: '15px', height:'150px',margin:'0px'}}>
          <ul style={{ margin: "20px" }}>
            <li>
             -  住所
              <ol>
                新宿歌舞伎町一番街
              </ol>
            </li>
            <li>
             -  検索範囲（m） 100
            </li>
          </ul>
          <div style={{ display: 'flex', justifyContent: 'flex-end', marginRight: '10px' }}>
            <span>の検索結果</span>
          </div>
        </div>

        {/* 这里使用了一个固定高度为200px的示例，你可以根据实际需求调整高度 */}
         
              {ResultList}
    
    
      </div>
    </div>
  );
};

export default GBselectResult;

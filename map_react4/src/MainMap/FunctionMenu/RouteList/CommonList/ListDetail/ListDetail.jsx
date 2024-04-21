import React from 'react'
import './ListDetail.css'



// 只显示配送画面的一个区域 ，后面可以循环 
export default function ListDetail() {
  return (
    <div>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}>  
        <h5 style={{ marginRight: '20px', marginLeft: '10px',width:"100px" }}>先頭住所</h5>
        <span>東京都江東区豊州１丁目</span>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}>
        <h5 style={{ marginRight: '20px', marginLeft: '10px', width: "100px" }}>登録日</h5>
        <span>2024年1月1日</span>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}>
        <span style={{ marginRight: '20px', marginLeft: '10px', width: "100px" ,fontSize:'20px'}}>ルート名</span>
        <span>配達ルート１</span>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}>
        <span style={{ marginRight: '20px', marginLeft: '10px', width: "100px", fontSize: '15px' }}>フリーワード</span>
        <span>中継地を修正</span>
      </div>
    </div>
  )
}

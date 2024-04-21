import React from 'react';
import Form from 'react-bootstrap/Form';
import CommonList from './CommonList/CommonList'
import { useSelector } from 'react-redux'
import CommonDetail from './CommonDetail/CommonDetail'

/* 整体居中放置  */ 
/* 默认所有column 下的标签是纵向排列  */ 
/* 如果需要横向排列，在最外层套上div 放在colum 下 */ 
/* 如果上面套div 的方法解决不了，则选择套row */ 
/* 项目位置可以使用 textAlign 大调  margin 小调 */ 
/* aria-label 放在标签里， 相当于vue中的lab功能 */ 
export default function RouteList() {
  const showRouteListCP = useSelector((state) => state.Maps.showRouteListCP)
  return (
  

      <div  style={{ display: 'flex', flexDirection: 'row'}}>
 
        <div style={{ display: 'flex', flexDirection: 'column', flex: "1", margin: '5px', textAlign: "left", height:'600px',width:'200px' ,color:'black' }}>

             <h6>ルート種別</h6>
                  {/* 选择框 */}
          <Form.Select size="sm" style={{width:'150px'}}>
              <option selected>通集配</option>
              <option value="1">One</option>
              <option value="2">Two</option>
              <option value="3">Three</option>
         </Form.Select>     
          
          <div style={{ display: 'flex', flexDirection: 'row', fontWeight: 'bold' }}>
            <span style={{ marginRight: '80px', marginLeft: '30px', }}>部</span>
            <span>班</span>
          </div>
         
          <div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}> 

            <Form.Select size="sm" style={{ width: '80px', marginRight: '30px', marginLeft: '10px' }} >
              <option value="1" selected>1</option>
              <option value="2">2</option>
            </Form.Select>
            <Form.Select size="sm" style={{ width: '80px' }} >
              <option value="1" selected>1</option>
              <option value="2">2</option>
            </Form.Select>
          </div>
        
          
          <span style={{ fontWeight: 'bold', textAlign: "left", marginLeft: '10px', marginTop: '10px' }}>
            絞り込み条件
          </span>
            <input type="email" class="form-control form-control-sm" id="exampleFormControlInput1" placeholder="フリーワードを入力" style={{ width: '200px', marginLeft: '10px' }}/>
 

 
          {showRouteListCP && <CommonList />}
          {!showRouteListCP && <CommonDetail/>}
        </div>
     
      </div>

  )
}

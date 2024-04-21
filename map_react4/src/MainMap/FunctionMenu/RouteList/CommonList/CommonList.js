import React from 'react'
import './CommonList.css'
import ListDetail from './ListDetail/ListDetail'
import { useDispatch } from 'react-redux'
import { changeShowRouteListCP } from '../../../../store/modules/selectButtonClick'
import Accordion from 'react-bootstrap/Accordion';

//次组件继承父亲组件的布局影响，会保持默认纵向布局 
// 如果需要横向布局，使用下方标签进行包装 
//<div style={{ display: 'flex', flexDirection: 'row', marginTop: '10px' }}>  


// TODO: 下拉框看不见按钮
export default function CommonList() {
  const dispatch = useDispatch()
  return (
    <div  style={{marginTop:'40px', display:'flex', flexDirection:'column'}}>
      <h5 style={{marginLeft:'30px'}}>通配区一覧</h5>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '20px', marginLeft:'25px'}}>  
          <span style={{marginRight:'40px'}}>表示色</span>
          <span>通配区名</span>
      </div>
      <div>
      <Accordion  style={{ width: '100%',padding:'0px' }}>
      <Accordion.Item eventKey="0" style={{height:'100%', width: '100%'}}>
          <Accordion.Header>
             <svg style={{marginRight:'50px'}} xmlns="http://www.w3.org/2000/svg" width="50" height="30" fill="red" class="bi bi-arrow-up" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5" />
              </svg>
              配達区1
            </Accordion.Header>
        <Accordion.Body style={{padding:'0px'}}>
             <ListDetail/>
          </Accordion.Body>
          
        </Accordion.Item>
        
      <Accordion.Item eventKey="1" style={{height:'100', width: '100%'}}>
            <Accordion.Header>
              <svg style={{ marginRight: '50px' }} xmlns="http://www.w3.org/2000/svg" width="45" height="30" fill="blue" class="bi bi-arrow-up" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5" />
              </svg>
              配達区2
            </Accordion.Header>
        <Accordion.Body>
              test2
        </Accordion.Body>
        </Accordion.Item>
      
     
        </Accordion>
       
      </div>
     
       <div style={{ textAlign: 'right' ,marginTop:'10px'}}>
          <button type="button" class="btn btn-outline-dark" onClick={() => dispatch(changeShowRouteListCP())}>区選択</button>
       </div>

    
     
    
    
   </div>
  )
}

import React from 'react'
import { useDispatch } from 'react-redux'
import { changeShowRouteListCP } from '../../../../store/modules/selectButtonClick'
import { changeShowRouteSearch } from '../../../../store/modules/selectButtonClick'
 import Button from 'react-bootstrap/Button'


export default function CommonDetail() {
  const dispatch = useDispatch()
  return (
    <div style={{ marginTop: '40px' }}>
      <h5 style={{ marginLeft: '30px' }}>通配区一覧</h5>
      <div style={{ display: 'flex', flexDirection: 'row', marginTop: '20px', marginLeft: '50px' }}>
        <span style={{ marginRight: '40px' }}>表示色</span>
        <span>通配区名</span>
      </div>

      {/* <div class="accordion" id="accordionhai">
        <div class="accordion-item">
          <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
              <svg style={{ marginRight: '50px' }} xmlns="http://www.w3.org/2000/svg" width="50" height="30" fill="red" class="bi bi-arrow-up" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 15a.5.5 0 0 0 .5-.5V2.707l3.146 3.147a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 1 0 .708.708L7.5 2.707V14.5a.5.5 0 0 0 .5.5" />
              </svg>
              配達区1
            </button>
          </h2> */}
          {/* 需要默认打开，在下方语句中的class里 加入关键字 show */}
          {/* <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionhai">
            <div class="accordion-body">

             
            </div>
          </div>
        </div>
      </div> */}
      
      {/* TODO:  下方按钮可以做成循环组件 */}
        <Button variant='dark' size="lg" style={{width:'220px', height:'50px' ,backgroundColor:'white',color:'black',justifyContent:'space-between'}}>
           <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="skyblue" class="bi bi-circle-fill" viewBox="0 0 16 16" style={{marginRight:"50px"}}>
              <circle cx="8" cy="8" r="8"/>
          </svg>
            <span style={{marginLeft:'20px'}} >test</span>
      </Button>
        <Button variant='dark' size="lg" style={{width:'220px', height:'50px' ,backgroundColor:'white',color:'black',justifyContent:'space-between'}}>
           <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="red" class="bi bi-circle-fill" viewBox="0 0 16 16" style={{marginRight:"50px"}}>
              <circle cx="8" cy="8" r="8"/>
          </svg>
            <span style={{marginLeft:'20px'}} >test</span>
      </Button>
        <Button variant='dark' size="lg" style={{width:'220px', height:'50px' ,backgroundColor:'white',color:'black',justifyContent:'space-between'}}>
           <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="green" class="bi bi-circle-fill" viewBox="0 0 16 16" style={{marginRight:"50px"}}>
              <circle cx="8" cy="8" r="8"/>
          </svg>
            <span style={{marginLeft:'20px'}} >test</span>
      </Button>
      
      

        <div style={{ textAlign: 'right', marginTop: '10px' }}>
          <button type="button" class="btn btn-outline-dark" onClick={() => dispatch(changeShowRouteSearch())}>ルート登録</button>
        </div>
        <div style={{ textAlign: 'right', marginTop: '10px' }}>
          <button type="button" class="btn btn-outline-dark" onClick={() => dispatch(changeShowRouteListCP())}>キャンセル</button>
        </div>
      




    </div>
  )
}


// changeRouteListView1
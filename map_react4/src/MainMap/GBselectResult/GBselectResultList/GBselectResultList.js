import React from 'react'
import { useDispatch } from 'react-redux';
import { changeShowGBpropertyDetail } from '../../../store/modules/selectButtonClick';
import Nav from 'react-bootstrap/Nav';


const GBselectResultList = () => {
    const dispatch = useDispatch()
  return (

          <div style={{ height: '120px' }}>
      <hr style={{ borderColor: '#5885ba', borderWidth: '2px' }} />
      {/* <a href="#" onClick={() => dispatch(changeShowGBpropertyDetail())} >八王子市尾崎町１ー７−１０</a> */}
      <Nav.Link href="#" onClick={() => dispatch(changeShowGBpropertyDetail())}>八王子市尾崎町１ー７−１０</Nav.Link>
            <br />
            <span>高いマンション1</span>
          </div>

  )
}

export default GBselectResultList
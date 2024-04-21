import React from 'react'
import './AddressMarker.css'
import CloseButton from 'react-bootstrap/CloseButton';


const AddressMarker = (props) => {
  return (
    <div style={{ display: 'flex', alignItems: 'center', marginLeft: '5px', marginTop: '10px' }}>
      <span style={{ marginRight: '0px',fontSize:'13px',width:'80px'}}>お届け先 {props.count}</span>
      <input
        className="form-control form-control-sm"
        type="text"
        aria-label=".form-control-sm example"
        style={{ width: '140px' }}
        value={props.value}
        onInput={(e) => props.onInput(props.count - 1, e.target.value)}
      />
      <CloseButton
        style={{ marginLeft: '10px', backgroundColor: 'red' }}
        onClick={props.del}
      />
    </div>
  );

}

export default AddressMarker
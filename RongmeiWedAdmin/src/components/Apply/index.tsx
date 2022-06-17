import { Modal } from 'antd';
import React from 'react';

export interface SettleApplyProps {
  type: String;
}

export default class ApplyForm extends React.Component<SettleApplyProps> {
  
  state = {

  }

  componentDidMount(){
    console.log(123);
    
  }

  render(){
    return (
        <Modal >
          test
        </Modal>
    )
  }
}
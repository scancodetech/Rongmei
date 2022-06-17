import React from 'react'
import CustomBreadcrumb from '../../components/CustomBreadcrumb/index'
import TypingCard from '../../components/TypingCard'

export default class About extends React.Component{
  render(){
    return (
      <div>
        <CustomBreadcrumb arr={['关于我们']}/>
        <TypingCard source={'南京崇新数字科技有限公司服务大家。'} title='关于我们' />
          <TypingCard source={'待补充'} title='分销规则' />
      </div>
    )
  }
}

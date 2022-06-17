import React from 'react'
import './style.css'
import Header from '../../../../../components/Header/index'
import { Card, DatePicker, Flex, List, ListView, Picker, WhiteSpace, WingBlank } from 'antd-mobile'

import WithdrawalIcon from '../../../../../assets/QHHBillWithdrawal.svg'
import QHHBillbondIcon from '../../../../../assets/QHHBillbond.svg'
import QHHBillShopIcon from '../../../../../assets/QHHBillShop.svg'
import QHHBillInComeIcon from '../../../../../assets/QHHBillInCome.svg'

export default class QHHBillDetail extends React.Component {
    state = {
        type: '接单保证金',
        name: '转入--保证金',
        price: '-120',
        date:'2021年1月1日 12:12:12',
        orderID:'202273263786272763726375',
        status:'冻结',
        payment:'可提余额'

    }

    billIconSelect(type) {
        switch (type) {
            case '提现': return WithdrawalIcon;
            case '9.9元定制单': return QHHBillInComeIcon;
            case '接单保证金': return QHHBillbondIcon;
        }
    }

    render() {
        return (
            <div className='QHHBillDetail' >
                <Header title='账单详情' />

                <div>
                    <Flex direction='column' justify='center' style={{ fontWeight: 600, fontSize: 15 }}>
                        <img alt='' style={{ width: 50, height: 50, backgroundColor: 'white', borderRadius: '50%', marginTop: 40 }} src={this.billIconSelect(this.state.type)} />
                        <div style={{ padding: '15px 0' }}>{this.state.name}</div>
                        <div style={{ fontSize: 25 }}>{this.state.price}</div>
                    </Flex>
                </div>

                <WhiteSpace size='xl' />
                <WingBlank size='lg'>
                    <div style={{ borderTop: '1px solid #333333',padding:'0px 0',fontWeight:600,fontSize:12 }} className='Banner'>
                        <Flex direction='column' justify='start' align='start'>
                            <div>入账时间：{this.state.date}</div>
                            <div>交易单号：{this.state.orderID}</div>
                            <div>当前状态：{this.state.status}</div>
                            <div>财务类型：{this.state.type}</div>
                            <div>支付方式：{this.state.payment}</div>
                        </Flex>
                    </div>
                </WingBlank>
            </div>
        )
    }
}
import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import { Flex, List, WhiteSpace, Button, Toast } from 'antd-mobile'

export default class Wallet extends React.Component {
    state = {
        walletItems: [
            {
                label: '总余额',
                value: '123',
                key: 0
            },
            {
                label: '可提余额',
                value: '321',
                key: 1
            },
            {
                label: '接单保证金',
                value: '123321',
                key: 2
            },
        ]
    }

    render() {
        return (
            <div className='QHHWalletPage'>
                <Header title='钱包'>
                    <div style={{ fontSize: 12, padding: 5, fontWeight: 600 }} onClick={() => { this.props.history.push('/qhh/wallet/bill') }}>账单</div>
                </Header>

                <div className='ListChange'>
                    <List>
                        {
                            this.state.walletItems.map((item, index) =>
                                <List.Item
                                    // arrow='horizontal'
                                    key={item.key}
                                    activeStyle={{ backgroundColor: '#222222' }}
                                    extra={item.value}
                                    onClick={() => {
                                        // if (item.route === '') {
                                        //     Toast.fail('正在开发中~')
                                        // }
                                        // else {
                                        //     this.props.history.push(item.route);
                                        // }
                                    }}
                                >
                                    {item.label}
                                </List.Item>
                            )
                        }
                    </List>

                    <div>
                        <Flex direction='column' justify='center'>
                            <p>接单入账金额达到100电子后，可以提现。</p>
                            <p>但是钱包里至少有100电子才能抢49.9元定制单哦~</p>
                            <p>你可以在接单保证金中锁定100电子，确保接单顺畅无阻~</p>
                            <p>接单保证金也可随时转入可提余额。</p>
                        </Flex>
                    </div>
                    <WhiteSpace size='xl' />
                    <WhiteSpace size='xl' />

                    <div>
                        <Flex justify='around'>
                            <div className='BtnChange'>
                                <Button onClick={()=>{this.props.history.push('/qhh/wallet/withdrawal')}}>提现</Button>
                            </div>
                            <div className='BtnChange'>
                                <Button onClick={()=>{this.props.history.push('/qhh/wallet/recharge')}}>充值</Button>
                            </div>
                        </Flex>
                    </div>
                </div>
            </div>
        )
    }
}
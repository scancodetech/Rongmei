import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { Button, Card, Checkbox, DatePicker, Flex, InputItem, List, ListView, Picker, WhiteSpace } from 'antd-mobile'

export default class QHHWalletRecharge extends React.Component {
    state = {
        account: '139****2345',
        price: '',
        payment: 0,
    }
    render() {
        const state = this.state;
        return (
            <div className='QHHWalletRecharge'>
                <Header title='充值' />
                <WhiteSpace size='lg' />
                <div style={{ fontSize: 12, color: '#AAAAAA' }}>
                    <div>
                        <List>
                            <List.Item>
                                <Flex style={{ padding: '5px 0' }}>
                                    <div style={{ fontSize: 12 }}>账号：</div>
                                    <div style={{ fontSize: 15, paddingLeft: 10 }}>{state.account}</div>
                                </Flex>
                            </List.Item>
                        </List>
                    </div>

                    <div className='title'>充值金额：</div>

                    <div style={{ padding: '15px 15px 0 15px' }}>
                        <div style={{ backgroundColor: 'black', borderRadius: 5, padding: 15 }}>
                            <Flex direction='column' align='center' justify='center'>
                                <Flex justify='around' style={{ width: '100%' }} wrap='wrap'>
                                    <div className='BtnChange'>
                                        <Button onClick={() => { this.setState({ price: 1 }) }}>1电子</Button>
                                    </div>
                                    <div className='BtnChange'>
                                        <Button onClick={() => { this.setState({ price: 10 }) }}>10电子</Button>
                                    </div>
                                    <div className='BtnChange'>
                                        <Button onClick={() => { this.setState({ price: 50 }) }}>50电子</Button>
                                    </div>
                                    <div className='BtnChange'>
                                        <Button onClick={() => { this.setState({ price: 100 }) }}>100电子</Button>
                                    </div>
                                    <div style={{ padding: '10px 0' }}>
                                        <InputItem style={{ border: '1px solid #666666', borderRadius: 5, textAlign: 'center', color: 'white', fontWeight: 600, width: 150 }}
                                            type='number'
                                            maxLength={4}
                                            placeholder='其他数额'
                                            value={state.price}
                                            onChange={
                                                (value) => {
                                                    this.setState({
                                                        price: parseInt(value) || 0
                                                    })
                                                }
                                            }
                                        />
                                    </div>
                                    <div>充值数量必须是整数，单次充值不得超过9999电子</div>
                                </Flex>
                            </Flex>
                        </div>
                    </div>

                    <div className='title'>充值方式：</div>

                    <div style={{ padding: '10px 0' }}>
                        <List>
                            <List.Item
                                onClick={() => { this.setState({ payment: 0 }) }}
                                extra={<Checkbox checked={state.payment === 0} />}
                                activeStyle={{ backgroundColor: '#333333' }}
                            >
                                <div style={{ fontSize: 14 }}>支付宝</div>
                            </List.Item>
                            <List.Item
                                onClick={() => { this.setState({ payment: 1 }) }}
                                extra={<Checkbox checked={state.payment === 1} />}
                                activeStyle={{ backgroundColor: '#333333' }}
                            >
                                <div style={{ fontSize: 14 }}>微信</div>
                            </List.Item>
                        </List>
                    </div>

                    <WhiteSpace size='lg' />

                    <Flex justify='center'>
                        <div className='reChargeBtn'>
                            <Button>立即充值</Button>
                        </div>
                    </Flex>





                </div>

            </div>
        )
    }
}
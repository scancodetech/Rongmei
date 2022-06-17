import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { Flex, InputItem, WhiteSpace, List, Checkbox, Button } from 'antd-mobile'
const CheckboxItem = Checkbox.CheckboxItem;
class Withdrawal extends React.Component {
    state = {
        alipayChecked: true,
        wepayChecked: false,
        price: 0,
        account: "",
    }
    alipayonChange = () => {
        this.setState({
            alipayChecked: true,
            wepayChecked: false
        });
    };
    wepayonChange = (value) => {
        this.setState({
            wepayChecked: true,
            alipayChecked: false
        });
    };
    render() {
        return (
            <div className='withdrawalPage'>
                <Header title='提现' theme={{ mode: 'dark' }}>
                    {/* 这里设置卖出的页面跳转 */}
                    <div onClick={() => {
                        console.log("跳转页面")
                    }} style={{ fontSize: 14 }}>
                        提现规则
                    </div>
                </Header>

                <WhiteSpace size='lg' />
                <div className='withdrawalBanner'>
                    <Flex>
                        <div>账号：</div>
                        <div style={{ fontSize: 17 }}>137****0002</div>
                    </Flex>
                </div>
                <div className='withdrawalLittleBanner'>
                    <Flex justify='between'>
                        <div>提现金额：</div>
                        <div style={{ color: '#FE2341' }}>【电子】说明</div>
                    </Flex>
                </div>

                <div className='withdrawalBanner'>
                    <InputItem
                        className='withdrawalInputChange'
                        type='number'
                    />
                    <div>
                        <Flex justify='start' direction='column' align='start'>
                            <div>{`账户总余额：1000电子`}</div>
                            <div>{`可提余额：800电子`}</div>
                            <div>{`限制场景余额：200电子`}</div>
                        </Flex>
                    </div>
                </div>

                <div className='withdrawalLittleBanner'>
                    <Flex justify='between'>
                        <div>提现方式：</div>
                        {/* <div style={{ color: '#FE2341' }}>【电子】说明</div> */}
                    </Flex>
                </div>

                <div className='withdrawalBanner'>
                    <div className='withdrawalRadioItemChange'>
                        <CheckboxItem checked={this.state.alipayChecked} onClick={(value) => this.alipayonChange(value)}>
                            {`支付宝`}
                        </CheckboxItem>
                        {
                            this.state.alipayChecked &&
                            <Flex justify='center' align='center'>
                                <div style={{ paddingRight: 10 }}>{`支付宝账号:`}</div>
                                <InputItem
                                    className='withdrawalInputChange'
                                    type='text'
                                    onChange={(value) => { this.setState({ account: value }, () => { console.log(this.state.account) }) }}

                                />
                            </Flex>
                        }

                        <CheckboxItem checked={this.state.wepayChecked} onClick={(value) => this.wepayonChange(value)}>
                            {`微信`}
                        </CheckboxItem>
                        {
                            this.state.wepayChecked &&
                            <Flex justify='center' align='center'>
                                <div style={{ paddingRight: 10 }}>{`微信账号:`}</div>
                                <InputItem
                                    className='withdrawalInputChange'
                                    onChange={(value) => { this.setState({ account: value }, () => { console.log(this.state.account) }) }}
                                />
                            </Flex>
                        }
                    </div>
                </div>
                <WhiteSpace size='xl' />
                <WhiteSpace size='xl' />

                <div className='withdrawalBtnChange'>
                    <Flex justify='center'>
                        <Button>立即提现</Button>
                    </Flex>
                </div>
            </div>
        )
    }
}
export default Withdrawal
import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import {Flex, InputItem, WhiteSpace, List, Checkbox, Button, Toast} from 'antd-mobile'
import {api} from "../../services/api/ApiProvider";

const CheckboxItem = Checkbox.CheckboxItem;

class Withdrawal extends React.Component {
    state = {
        alipayChecked: true,
        wepayChecked: false,
        price: 0,
        account: "",
        username: "",

        largeCoins: 0,
        disableWithDrawCoins: 0,
        earnestCoins: 0
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

    async componentDidMount() {
        const userInfoRes = await api.accountService.getUserInfo();
        const res = await api.accountService.getUserAccount();
        this.setState({
            username: userInfoRes.username,
            largeCoins: res.largeCoins,
            disableWithDrawCoins: res.disableWithDrawCoins,
            earnestCoins: res.earnestCoins
        })
    }

    async withdraw() {
        if (this.state.largeCoins < this.state.price * 100) {
            Toast.fail("可提现余额不足")
            return;
        }
        try {
            if (this.state.alipayChecked) {
                await api.accountService.withdraw(this.state.price, this.state.account, '');
            } else {
                await api.accountService.withdraw(this.state.price, '', this.state.account);
            }
            Toast.success("提现申请成功")
        } catch (e) {
            Toast.fail("提现申请失败")
        }
    }

    render() {
        return (
            <div className='withdrawalPage'>
                <Header title='提现' theme={{mode: 'dark'}}>
                    {/* 这里设置卖出的页面跳转 */}
                    <div onClick={() => {
                        console.log("跳转页面")
                    }} style={{fontSize: 14}}>
                        提现规则
                    </div>
                </Header>

                <WhiteSpace size='lg'/>
                <div className='withdrawalBanner'>
                    <Flex>
                        <div>账号：</div>
                        <div style={{fontSize: 17}}>{this.state.username}</div>
                    </Flex>
                </div>
                <div className='withdrawalLittleBanner'>
                    <Flex justify='between'>
                        <div>提现金额：</div>
                        <div style={{color: '#FE2341'}}>【电子】说明</div>
                    </Flex>
                </div>

                <div className='withdrawalBanner'>
                    <InputItem
                        value={this.state.price}
                        onChange={value => {
                            this.setState({
                                price: parseFloat(value)
                            })
                        }}
                        className='withdrawalInputChange'
                        type='digit'
                    />
                    <div>
                        <Flex justify='start' direction='column' align='start'>
                            <div>{`账户总余额：${((this.state.largeCoins + this.state.disableWithDrawCoins + this.state.earnestCoins) / 100).toLocaleString()}电子`}</div>
                            <div>{`可提余额：${(this.state.largeCoins / 100).toLocaleString()}电子`}</div>
                            <div>{`限制场景余额：${(this.state.disableWithDrawCoins / 100).toLocaleString()}电子`}</div>
                            <div>{`保证金：${(this.state.earnestCoins / 100).toLocaleString()}电子`}</div>
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
                        <CheckboxItem checked={this.state.alipayChecked}
                                      onClick={(value) => this.alipayonChange(value)}>
                            {`支付宝`}
                        </CheckboxItem>
                        {
                            this.state.alipayChecked &&
                            <Flex justify='center' align='center'>
                                <div style={{paddingRight: 10}}>{`支付宝账号:`}</div>
                                <InputItem
                                    className='withdrawalInputChange'
                                    type='text'
                                    onChange={(value) => {
                                        this.setState({account: value}, () => {
                                            console.log(this.state.account)
                                        })
                                    }}

                                />
                            </Flex>
                        }

                        <CheckboxItem checked={this.state.wepayChecked} onClick={(value) => this.wepayonChange(value)}>
                            {`微信`}
                        </CheckboxItem>
                        {
                            this.state.wepayChecked &&
                            <Flex justify='center' align='center'>
                                <div style={{paddingRight: 10}}>{`微信账号:`}</div>
                                <InputItem
                                    className='withdrawalInputChange'
                                    onChange={(value) => {
                                        this.setState({account: value}, () => {
                                            console.log(this.state.account)
                                        })
                                    }}
                                />
                            </Flex>
                        }
                    </div>
                </div>
                <WhiteSpace size='xl'/>
                <WhiteSpace size='xl'/>

                <div className='withdrawalBtnChange'>
                    <Flex justify='center'>
                        <Button onClick={() => this.withdraw()}>立即提现</Button>
                    </Flex>

                </div>
            </div>
        )
    }
}

export default Withdrawal

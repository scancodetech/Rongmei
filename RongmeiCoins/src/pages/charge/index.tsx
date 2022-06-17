import React, {Component} from 'react';


import {Button, Card, Col, Image, Input, Row} from 'antd';
import chargeStyles from "@/pages/charge/charge.less";
import {DollarCircleFilled} from '@ant-design/icons';
import coin from '@/assets/coin.png';
import {getUserInfo} from '@/services/user';
import loading from "@/assets/loading.gif";
import {postAlipay, postWechat} from '@/services/pay';
import ReactDOM from 'react-dom';

class Charge extends Component<any> {

    state = {
        userInfo: '',
        selectedCard: 0,
        qrcode: '',
        totalAmount: 1,
        body: ''
    }


    async componentDidMount(): Promise<void> {
        this.getInfo();
        this.getAlipayQrcode();
    }

    async getInfo() {
        let res = await getUserInfo();
        this.setState({
            userInfo: res
        })
    }

    onClickCard(key: number) {
        let totalAmount = 0;
        switch (key) {
            case 0:
                totalAmount = 1;
                this.getAlipayQrcode();
                break;
            case 1:
                totalAmount = 10;
                this.getAlipayQrcode();
                break;
            case 2:
                totalAmount = 15;
                this.getAlipayQrcode();
                break;
            case 3:
                totalAmount = 50;
                this.getAlipayQrcode();
                break;
            case 4:
                break;
            default:
                break;
        }
        this.setState({
            totalAmount: totalAmount,
            selectedCard: key
        })
    }

    async getAlipayQrcode() {
        const btnNode = "<input type='submit' value='支付宝支付' className={chargeStyles.alipayBtn}/>";
        console.log(this.state.totalAmount);
        let res = await postAlipay(this.state.totalAmount, window.location.href)
        let formNodes = res.body.split('\n');
        const formDom = formNodes[0] + '\n' + formNodes[1] + '\n' + btnNode + '\n</form>';
        this.setState({
            body: formDom
        })

    }

    async getWechatQrcode() {
        let res = await postWechat(this.state.totalAmount, window.location.href, window.returnCitySN.cip)
        window.location.href = res.body
    }

    render() {
        return (

            <div className={chargeStyles.chargeContainer}>
                <div className={chargeStyles.head}>
                    <div className={chargeStyles.title}>
                        充值
                    </div>
                    <div className={chargeStyles.account}>
                        账号：{this.state.userInfo.username}
                    </div>
                </div>
                <div className={chargeStyles.chargeCards}>
                    <Row gutter={16}>
                        <Col flex={1}>
                            <Card
                                className={this.state.selectedCard == 0 ? chargeStyles.amountActive : chargeStyles.amount}
                                hoverable
                                onClick={() => {
                                    this.onClickCard(0)
                                }}
                                title={
                                    <div className={chargeStyles.chargeTitle}>
                                        <img src={coin} className={chargeStyles.coin}/>
                                        1
                                    </div>
                                }
                                bordered>
                                1元
                            </Card>
                        </Col>
                        <Col flex={1}>
                            <Card
                                className={this.state.selectedCard == 1 ? chargeStyles.amountActive : chargeStyles.amount}
                                hoverable
                                onClick={() => {
                                    this.onClickCard(1)
                                }}
                                title={
                                    <div className={chargeStyles.chargeTitle}>
                                        <img src={coin} className={chargeStyles.coin}/>
                                        10
                                    </div>
                                }
                                bordered>
                                10元
                            </Card>
                        </Col>
                        <Col flex={1}>
                            <Card
                                className={this.state.selectedCard == 2 ? chargeStyles.amountActive : chargeStyles.amount}
                                hoverable
                                onClick={() => {
                                    this.onClickCard(2)
                                }}
                                title={
                                    <div className={chargeStyles.chargeTitle}>
                                        <img src={coin} className={chargeStyles.coin}/>
                                        15
                                    </div>
                                }
                                bordered>
                                15元
                            </Card>
                        </Col>
                        <Col flex={1}>
                            <Card
                                className={this.state.selectedCard == 3 ? chargeStyles.amountActive : chargeStyles.amount}
                                hoverable
                                onClick={() => {
                                    this.onClickCard(3)
                                }}
                                title={
                                    <div className={chargeStyles.chargeTitle}>
                                        <img src={coin} className={chargeStyles.coin}/>
                                        50
                                    </div>
                                }
                                bordered>
                                50元
                            </Card>
                        </Col>
                        <Col flex={1}>
                            <Card
                                className={this.state.selectedCard == 4 ? chargeStyles.amountActive : chargeStyles.amount}
                                hoverable
                                onClick={() => {
                                    this.onClickCard(4)
                                }}
                                title={
                                    <div className={chargeStyles.chargeTitle}>
                                        <img src={coin} className={chargeStyles.coin}/>
                                    </div>
                                }
                                bordered>
                                <Input style={{padding: 0, maxWidth: 78}} bordered={false} placeholder={'其他金额'}
                                       suffix={'元'}
                                       onChange={({target: {value}}) => {
                                           this.setState({
                                               totalAmount: value
                                           }, () => {
                                               this.getAlipayQrcode()
                                           })
                                       }}
                                />
                            </Card>
                        </Col>
                    </Row>
                </div>
                <div className={chargeStyles.tip}>
                    充值数量必须是整数，单次最多不超过9999
                </div>
                <div className={chargeStyles.payment}>
                    {/* <div className={chargeStyles.payTitle}>
            支付方式
          </div> */}
                    <div className={chargeStyles.selectWechatPay} id='alipay'>
                        <input type='submit' onClick={()=>this.getWechatQrcode()} value='微信支付'/>
                    </div>
                    <div className={chargeStyles.selectPay} id='alipay'>
                        <div dangerouslySetInnerHTML={{__html: this.state.body}}/>
                    </div>
                    <div>

                    </div>
                    {/* <div>
            充值用户协议
          </div> */}
                </div>
            </div>
        );
    }

}

export default Charge

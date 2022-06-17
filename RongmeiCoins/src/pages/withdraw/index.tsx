import React, {Component} from 'react';
import {connect, Dispatch} from 'umi';
import {UserInfo, UserInfoModelState} from "@/models/userInfo";
import {RouteChildrenProps} from 'react-router';
import {Button, Form, Input, message, Select} from 'antd';
import {getUserAccount, getUserInfo} from '@/services/user';
import styles from './withdraw.less';
import {postWithdraw} from '@/services/pay';

const {Option} = Select;

interface WithDrawProps extends RouteChildrenProps {
    dispatch: Dispatch;
    userInfo: UserInfo;
}

class WithDraw extends Component<WithDrawProps> {

    state = {
        userAccount: {
            largeCoins: 0,
            disableWithDrawCoins: 0
        },
        userInfo: {
            username: ""
        },
        loading: false,
    }

    async componentDidMount(): Promise<void> {
        let balanceRes = await getUserAccount();
        this.setState({
            userAccount: balanceRes
        })
        this.getInfo();
    }

    async getInfo() {
        let res = await getUserInfo();
        this.setState({
            userInfo: res
        })
    }

    async onPostWithdraw(e: any) {
        this.setState({loading: true})
        let username = this.state.userInfo.username;
        let res = await postWithdraw(username, e.amount);
        if (res.infoCode === 10000) {
            message.success('您已成功申请提现！', 2);
            this.setState({
                loading: false
            })
        } else {
            message.error('提现异常');
            this.setState({
                loading: false
            })
        }
    }

    render() {
        const {userInfo} = this.props;
        return (
            <div style={{
                margin: '0 auto',
                padding: '20px auto',
                display: 'flex',
                flexDirection: 'column'
            }}>
                <div style={{textAlign: 'center', fontSize: '26px', fontWeight: 'bold', marginBottom: '10px'}}>
                    提现
                </div>
                <div style={{alignSelf: 'center', margin: '30px'}}>
                    账户名：{userInfo.username}
                </div>
                <Form onFinish={(e) => {
                    this.onPostWithdraw(e)
                }}>
                    <Form.Item
                        name='amount' label='提现金额'
                        rules={[{required: true}]}
                    >
                        <Input className={styles.formInput} placeholder='请输入金额' prefix='¥'/>
                    </Form.Item>
                    <div style={{margin: '0 0 20px 10px'}}>
                        账户总金额：{this.state.userAccount.largeCoins / 100} 元<br/>
                        可提现余额：{this.state.userAccount.largeCoins / 100 - this.state.userAccount.disableWithDrawCoins / 100} 元<br/>
                        限制场景余额：{this.state.userAccount.disableWithDrawCoins / 100} 元<br/>
                    </div>
                    <Form.Item
                        name='withdrawType'
                        initialValue='alipay'
                        label='提现平台'
                        rules={[{required: true}]}
                    >
                        <Select className={styles.formInput} defaultValue='alipay' disabled
                                style={{borderRadius: '10px', width: 'auto'}}>
                            <Option title='支付宝' value='alipay' selected={true}>支付宝</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item name='withdrawAccount' label='提现账号' rules={[{required: true}]}>
                        <Input className={styles.formInput} placeholder='请输入提现账号'/>
                    </Form.Item>
                    <Form.Item>
                        <div
                            style={{marginTop: '20px', display: 'flex', alignItems: 'center', flexDirection: 'column'}}>
                            <Button style={{borderRadius: '10px', margin: '10px auto'}} type="primary"
                                    htmlType="submit">
                                提交申请
                            </Button>
                        </div>
                    </Form.Item>
                </Form>

            </div>
        );
    }
}

export default connect(
    ({
         userInfo
     }: {
        userInfo: UserInfoModelState;
    }) => ({
        userInfo: userInfo.userInfo
    }),
)(WithDraw);

import React from 'react'
import {api} from "../../services/api/ApiProvider";
import Header from "../../components/Header";
import {Button, InputItem, List, Tag, Toast} from "antd-mobile";

const isIPhone = new RegExp('\\biPhone\\b|\\biPod\\b', 'i').test(window.navigator.userAgent);
let moneyKeyboardWrapProps;
if (isIPhone) {
    moneyKeyboardWrapProps = {
        onTouchStart: e => e.preventDefault(),
    };
}

class UserGroup extends React.Component {
    state = {
        avatarUrl: "http://39.102.36.169:6789/static/2020_05_30_04_29_35_0382_1.png",
        description: "<p>123</p><p>123</p>",
        firstType: "微博业务",
        id: 17,
        largeCoins: 0,
        largePrice: 1000,
        maxBuyNum: 3000,
        minBuyNum: 100,
        secondType: "粉丝购买",
        tags: ["34", "123"],
        title: "123",
        userGroupId: 164,

        buyNum: 100,
        pageUrl: '',
        totalPrice: 0,
    }

    async componentDidMount() {
        const userGroupId = this.props.history.location.pathname.split("/").pop();
        this.setState({
            userGroupId
        })
        const res = await api.userGroupService.getUserGroup(userGroupId);
        this.setState({
            id: res.id,
            avatarUrl: res.avatarUrl,
            description: res.description,
            firstType: res.firstType,
            largeCoins: res.largeCoins,
            largePrice: res.largePrice,
            maxBuyNum: res.maxBuyNum,
            secondType: res.secondType,
            tags: res.tags,
            title: res.title,
            userGroupId: res.userGroupId,
            buyNum: res.minBuyNum,
            totalPrice: res.largePrice * res.minBuyNum / 100000
        })
    }

    async handleSubmit() {
        try {
            await api.orderService.updateOrder({
                id: 0,
                userGroupId: this.state.userGroupId,
                largePrice: this.state.largePrice,
                avatarUrl: this.state.avatarUrl,
                userGroupTitle: this.state.title,
                pageUrl: this.state.pageUrl,
                status: "等待中",
                totalNum: this.state.buyNum,
                completeNum: 0
            })
            await api.accountService.updateUserAccount({
                largeCoins: -this.state.largePrice * this.state.buyNum / 100000 * 100
            })
            Toast.success('购买成功', 1);
            this.props.history.push('/')
        } catch (e) {
            Toast.success('购买失败', 1);
        }
    }

    render() {
        return (
            <div style={{height: '100%'}}>
                <Header title={"商品详情"}
                        back={() => {
                            this.props.history.goBack();
                        }}/>
                <div style={{padding: '0 15px', backgroundColor: 'white'}}>
                    <div style={{display: 'flex', padding: '15px 0'}}>
                        <img style={{height: '64px', marginRight: '15px'}} src={this.state.avatarUrl} alt=""/>
                        <div style={{lineHeight: 1, marginTop: '5px'}}>
                            <div style={{marginBottom: '8px', fontWeight: 'bold'}}>{this.state.title}</div>
                            <div>￥<span
                                style={{fontSize: '30px', color: '#FF6E27'}}>{this.state.largePrice / 100000}</span>
                                /条
                            </div>
                        </div>
                    </div>
                </div>
                <List className="my-list">
                    <List.Item extra={this.state.minBuyNum}>最低购买数</List.Item>
                    <List.Item extra={this.state.maxBuyNum}>最大购买数</List.Item>
                    <List.Item>{this.state.tags.map((tag) => (
                        <Tag selected style={{margin: '5px'}}>{tag}</Tag>))}</List.Item>
                </List>
                <List style={{marginTop: '10px'}} renderHeader={() => '订单参数'}>
                    <InputItem
                        value={this.state.pageUrl}
                        clear
                        placeholder="示例：https://mp.weixin.qq.com/s/XXXXXXXXXXXXXXXXX"
                        onChange={(value) => {
                            this.setState({
                                pageUrl: value
                            })
                        }}
                    >目标链接</InputItem>
                    <InputItem
                        type={'money'}
                        value={this.state.buyNum}
                        defaultValue={this.state.minBuyNum}
                        placeholder="请输入购买数量"
                        clear
                        moneyKeyboardAlign="left"
                        moneyKeyboardWrapProps={moneyKeyboardWrapProps}
                        onChange={(value) => {
                            this.setState({
                                buyNum: value,
                                totalPrice: this.state.largePrice * value / 100000
                            })
                        }}
                    >购买数量</InputItem>
                </List>
                <List style={{marginTop: '10px'}} renderHeader={() => '商品详情'}>
                    <List.Item>
                        <div dangerouslySetInnerHTML={{
                            __html: this.state.description
                        }}/>
                    </List.Item>
                </List>
                <Button style={{position: 'fixed', width: '100%', bottom: 0}} type="warning"
                        onClick={() => {
                            this.handleSubmit()
                        }}>
                    <span style={{marginRight: '20px'}}>
                        ￥{this.state.totalPrice}
                    </span>
                    <span>
                        购买
                    </span>
                </Button>
            </div>
        );
    }
}

export default UserGroup

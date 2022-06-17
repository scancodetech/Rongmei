import React from 'react'
import { withRouter } from "react-router-dom";
import './style.css'
import Header from '../../components/Header/index'
import share from "../../assets/share.png";
import logo from "../../assets/logo.png";
import { Flex, WhiteSpace, WingBlank, Icon, Button, InputItem, Modal, Toast, ActionSheet } from 'antd-mobile';
import { api } from "../../services/api/ApiProvider";
import { copyToClip, getDateDiff } from "../../utils/utils";
import link from "../../assets/link.png";
import warn from "../../assets/warn.png";

const { alert } = Modal;

@withRouter
class Sale extends React.Component {
    auctionService = api.auctionService;
    accountService = api.accountService;
    metricsService = api.metricsService;

    state = {
        id: 0,
        startPrice: 0,
        status: '竞价中',
        intervalPrice: 0,
        thing: {},
        createTime: 0,
        startTime: 0,
        endTime: 0,
        currPrice: 0,
        minePrice: 0,
        author: {},
        owner: {},
        userAccount: {},
        historyList: [],
        needEarnestMoney: false,

        dayDiff: 0,
        hourDiff: 0,
        minuteDiff: 0,
        secondDiff: 0,

        price: 0,
        needMoney: 0,
        showBidModal: false,
        showSuccessModal: false,
        bidType: '',
        bidCountText: ''
    }

    async componentDidMount() {
        const saleId = this.props.location.pathname.split('/').pop();
        let getSaleRes = await this.auctionService.getSale(saleId);
        this.setState({
            ...getSaleRes,
            price: getSaleRes.currPrice / 100
        }, () => {
            let interval = window.setInterval(() => {
                this.getBidType(this.state.startTime, this.state.endTime);
            }, 1000);
        })
        let historyRes = await this.auctionService.getSaleHistory(saleId);
        this.setState({
            historyList: historyRes.auctionHistories,
        })
    }

    getBidType(startTime, endTime) {
        let now = new Date();
        let startTimeDiff = startTime - now.getTime();
        let endTimeDiff = endTime - now.getTime();
        let timeDiff = 0;
        if (startTimeDiff > 0) {
            timeDiff = startTimeDiff;
            this.setState({
                bidType: '未开始',
                bidCountText: '距离开始'
            })
        } else if (startTimeDiff <= 0 && endTimeDiff >= 0) {
            timeDiff = endTimeDiff;
            this.setState({
                bidType: '进行中',
                bidCountText: '距离结束'
            })
        } else if (endTimeDiff < 0) {
            timeDiff = 0
            this.setState({
                bidType: '已结束',
                bidCountText: '已结束'
            })
        }
        this.countDown(timeDiff);
    }

    countDown(timeDiff) {
        let dayDiff = timeDiff / (24 * 60 * 60 * 1000);
        let hourDiff = Math.floor((timeDiff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)) + "";
        let minuteDiff = Math.floor((timeDiff % (60 * 60 * 1000)) / (60 * 1000)) + "";
        let secondDiff = Math.floor((timeDiff % 60000) / 1000) + "";
        if (hourDiff.length === 1) {
            hourDiff = "0" + hourDiff;
        }
        if (minuteDiff.length === 1) {
            minuteDiff = "0" + minuteDiff;
        }
        if (secondDiff.length === 1) {
            secondDiff = "0" + secondDiff;
        }
        this.setState({
            dayDiff: Math.floor(dayDiff),
            hourDiff: hourDiff,
            minuteDiff: minuteDiff,
            secondDiff: secondDiff,
        })
    }

    async checkEarnest() {
        if (new Date().getTime() < this.state.startTime) {
            Toast.fail("还未到竞价时间");
            return;
        }
        if (new Date().getTime() > this.state.endTime) {
            Toast.fail("已超出竞价时间");
            return;
        }
        const res = await this.auctionService.getMineSalesParticipate();
        if (res.saleItems) {
            let totalEarnest = 0;
            let saleItems = res.saleItems;
            for (let i = 0; i < saleItems.length; i++) {
                const saleItem = saleItems[i];
                totalEarnest += saleItem.startPrice * 0.2
            }
            totalEarnest += this.state.startPrice * 0.2
            let userAccount = await this.accountService.getUserAccount();
            if (userAccount.earnestCoins >= totalEarnest) {
                this.bid();
            } else {
                let needMoney = totalEarnest - userAccount.earnestCoins;
                this.setState({
                    needMoney
                })
                alert('竞拍保证费不足', `您还需要缴纳${needMoney}电子竞拍保证费，是否缴纳？`, [
                    {
                        text: '取消', onPress: async () => {
                            Toast.fail("竞拍失败");
                        }, style: 'default'
                    },
                    {
                        text: '确定', onPress: async () => {
                            try {
                                const res = await this.accountService.payEarnest(needMoney);
                                if (res.infoCode === 10000) {
                                    Toast.success("拍卖保证金充值成功");
                                    this.bid();
                                } else {
                                    Toast.fail("充值失败，余额不足");
                                    this.props.history.push(`/me/coin`);
                                }
                            } catch (e) {
                                Toast.fail("充值失败，余额不足");
                                this.props.history.push(`/me/coin`);
                            }
                        }
                    },
                ])
            }
        }
    }

    bid = async () => {
        let that = this;
        alert('确定要加价吗？', '竞价成功后将通过积分支付', [
            {
                text: '取消', onPress: async () => {
                    Toast.fail("竞拍失败");
                }, style: 'default'
            },
            {
                text: '确定', onPress: async () => {
                    try {
                        await this.auctionService.bid(that.state.price * 100, that.state.id);
                        Toast.success("出价成功")
                        that.componentDidMount();
                    } catch (e) {
                        Toast.fail('竞拍失败，请输入正确的竞价价格或检查拍卖时间');
                    }
                }
            }
        ]);
    }

    actionList = [
        {
            icon: <img src={link} alt={"复制链接"} style={{ width: 36 }} />,
            title: "复制链接",
            action: async () => {
                copyToClip("https://m.dimension.pub/#/picmobile/sale/" + this.props.location.pathname.split('/').pop());
                Toast.success("链接已复制");
                const thingId = this.state.thing.id ? this.state.thing.id : 0;
                await this.metricsService.addShare(thingId, "");
            }
        },
        {
            icon: <img src={warn} alt={"举报"} style={{ width: 36 }} />,
            title: "举报",
            action: () => {
                if (this.state.sale) {
                    this.props.history.push(`report/${this.props.location.pathname.split('/').pop()}`);
                } else {
                    Toast.info("开发中，尽情期待")
                }
            }
        }
    ]

    openShareModalVisible = () => {
        ActionSheet.showShareActionSheetWithOptions({
            options: this.actionList,
            title: '分享',
        },
            (buttonIndex) => {
                if (this.actionList[buttonIndex]) {
                    this.actionList[buttonIndex].action();
                }
            });
    }

    render() {
        return (
            <div className='page'>
                {/* 页面头部 */}

                <Header title={'竞价'} theme={{ bgColor: 'black', title: 'white', mode: 'dark' }}>
                    {/* props.children */}
                    {/* onClick事件中添加分享事件 */}
                    <div onClick={() => {
                        this.openShareModalVisible();
                    }}>
                        <img src={share} className='avatar' />
                    </div>
                </Header>

                {/* 上下留白 */}
                <WhiteSpace size='xl' />

                {/* 页面主体 */}
                <WingBlank size='lg'>

                    {/* Pic与价格等信息面板 */}
                    <div className='itemPic'>

                        <Flex justify='around'>
                            <Flex.Item>
                                <div className='itemName'>{this.state.thing.name}</div>
                            </Flex.Item>
                            <Flex.Item>
                                <div style={{ padding: '0 10px' }}>
                                    {/*<img src={heart} style={{height: 25, width: 25, float: 'right'}} onClick={() => {*/}
                                    {/*    console.log("heart")*/}
                                    {/*}}/>*/}
                                </div>
                            </Flex.Item>
                        </Flex>

                        <Flex justify='center'>
                            <img
                                src={this.state.thing.url}
                                style={{ width: '100%', height: 240, objectFit: 'cover' }} />
                        </Flex>
                        <WhiteSpace size='md' />
                        <Flex justify='around' style={{ color: 'white' }}>
                            <Flex.Item>
                                <div style={{ textAlign: 'center', }}>
                                    <div style={{ fontWeight: 600 }}>当前价格</div>
                                    <WhiteSpace size='md' />

                                    <Flex justify='center' style={{ textAlign: 'center' }} align='end'>
                                        <div style={{ fontWeight: 600, fontSize: 20, color: '#FE2341' }}>
                                            {(this.state.currPrice / 100).toLocaleString()}
                                        </div>
                                        <div style={{ fontSize: 14, fontWeight: 600, paddingLeft: 10 }}>
                                            电子
                                        </div>
                                    </Flex>
                                </div>
                            </Flex.Item>

                            <Flex.Item>
                                <div style={{ textAlign: 'center', }}>
                                    <div style={{ fontWeight: 600 }}>距离结束剩余</div>
                                    <WhiteSpace size='md' />

                                    <Flex justify='center' style={{ textAlign: 'center' }} align='end'>
                                        <div style={{ fontWeight: 600, fontSize: 20, color: '#FE2341' }}>
                                            {this.state.dayDiff}
                                        </div>
                                        <div style={{ fontSize: 10, fontWeight: 600, color: '#AAAAAA' }}>
                                            天
                                        </div>
                                        <div style={{ fontWeight: 600, fontSize: 20, color: '#FE2341' }}>
                                            {this.state.hourDiff}
                                        </div>
                                        <div style={{ fontSize: 10, fontWeight: 600, color: '#AAAAAA' }}>
                                            时
                                        </div>
                                        <div style={{ fontWeight: 600, fontSize: 20, color: '#FE2341' }}>
                                            {this.state.minuteDiff}
                                        </div>
                                        <div style={{ fontSize: 10, fontWeight: 600, color: '#AAAAAA' }}>
                                            分
                                        </div>
                                        <div style={{ fontWeight: 600, fontSize: 20, color: '#FE2341' }}>
                                            {this.state.secondDiff}
                                        </div>
                                        <div style={{ fontSize: 10, fontWeight: 600, color: '#AAAAAA' }}>
                                            秒
                                        </div>
                                    </Flex>
                                </div>
                            </Flex.Item>
                        </Flex>

                    </div>

                    <WhiteSpace size='lg' />
                </WingBlank>

                {/* 竞价信息面板 */}
                <div style={{ backgroundColor: '#000000', width: '100%', color: 'white', }}>
                    <WhiteSpace size='lg' />

                    <Flex justify='around'>
                        <Flex.Item>
                            <div style={{ textAlign: 'center' }}>
                                <div>起拍价</div>
                                <WhiteSpace size='md' />
                                <Flex justify='center' style={{ fontSize: 14 }}>
                                    <div
                                        style={{ color: '#FE2341' }}>{(this.state.startPrice / 100).toLocaleString()}</div>
                                    <div>电子</div>
                                </Flex>
                            </div>
                        </Flex.Item>
                        <Flex.Item>
                            <div style={{ textAlign: 'center' }}>
                                <div>保证金</div>
                                <WhiteSpace size='md' />
                                {this.state.needEarnestMoney ? (<Flex justify='center' style={{ fontSize: 14 }}>
                                    <div
                                        style={{ color: '#FE2341' }}>{(this.state.startPrice / 100 / 5).toLocaleString()}</div>
                                    <div>电子</div>
                                </Flex>) : '无需保证金'}
                            </div>
                        </Flex.Item>
                        <Flex.Item>
                            <div style={{ textAlign: 'center' }}>
                                <div>加价幅度</div>
                                <WhiteSpace size='md' />
                                <Flex justify='center' style={{ fontSize: 14 }}>
                                    <div
                                        style={{ color: '#FE2341' }}>{(this.state.intervalPrice / 100).toLocaleString()}</div>
                                    <div>电子</div>
                                </Flex>
                            </div>
                        </Flex.Item>
                    </Flex>
                    <WhiteSpace size='sm' />
                    <WhiteSpace size='md' />
                </div>

                {/* 历史出价面板 */}
                <WingBlank size='lg'>
                    {/* 上下留白 */}
                    <WhiteSpace size='md' />
                    <WhiteSpace size='md' />
                    <div style={{ textAlign: 'left', color: '#AAAAAA' }}>
                        <div>历史出价</div>
                        <WhiteSpace size='md' />
                        {
                            this.state.historyList.length > 0 ? <Flex justify='around' style={{ textAlign: 'center' }}>
                                <Flex.Item>
                                    <div>昵称</div>
                                </Flex.Item>
                                <Flex.Item>
                                    <div>时间</div>
                                </Flex.Item>
                                <Flex.Item>
                                    <div>出价</div>
                                </Flex.Item>
                            </Flex> : <div>暂无竞价记录</div>
                        }
                        <WhiteSpace size='lg' />
                        {
                            this.state.historyList.map((history, index) => (
                                <div key={index}>
                                    <Flex justify='around' style={{ textAlign: 'center' }}>
                                        <Flex.Item>
                                            <div>{history.user.nickname.length > 0 ? history.user.nickname : '匿名'}</div>
                                        </Flex.Item>
                                        <Flex.Item>
                                            <div>{getDateDiff(history.createTime)}</div>
                                        </Flex.Item>
                                        <Flex.Item>
                                            <div>{(history.price / 100).toLocaleString()}电子</div>
                                        </Flex.Item>
                                    </Flex>
                                    <WhiteSpace size='sm' />
                                </div>
                            ))
                        }
                        {/* 底部留白 */}
                    </div>
                    <WhiteSpace size='lg' />
                </WingBlank>

                {/* 竞品详情面板 */}
                <WingBlank size='lg' style={{ backgroundColor: 'black', borderRadius: 7, }}>
                    <WhiteSpace size='xl' />
                    <div style={{ color: 'white', fontSize: 15 }}>
                        <div style={{ fontWeight: 600 }}>竞品详情</div>
                        <WhiteSpace size='xl' />

                        <WingBlank size='md' style={{ textAlign: 'left', color: 'white' }}>
                            <div style={{ fontSize: 14, fontWeight: 500 }}>竞品简介</div>
                            <WhiteSpace size='xl' />
                            <Flex justify='start' style={{ fontSize: 10, fontWeight: 500, paddingRight: 20 }}>
                                <div style={{ paddingRight: 20 }}>上传者</div>
                                <img style={{ borderRadius: "50%", width: 25, height: 25, }}
                                    src={!this.state.author.avatarUrl || this.state.author.avatarUrl.length <= 0 ? logo : this.state.author.avatarUrl} />
                                <div style={{
                                    color: '#AAAAAA',
                                    paddingLeft: 10,
                                    fontWeight: 600
                                }}>{this.state.author.nickname}</div>
                            </Flex>
                            <WhiteSpace size='xl' />

                            <Flex justify='start' style={{ fontSize: 10, fontWeight: 500, }}>
                                <div>{this.state.thing.description}</div>
                            </Flex>
                            <WhiteSpace size='xl' />
                        </WingBlank>
                    </div>


                    {/* 竞品图片展示/此处可以使用瀑布流模式 */}
                    <Flex>
                        <img style={{ width: '100%', height: 170, objectFit: 'contain' }}
                            src={this.state.thing.url} />
                    </Flex>
                </WingBlank>

                <WhiteSpace size='lg' />

                {/* 下边栏 */}
                <div style={{ backgroundColor: 'black', height: '50px', position: 'fixed', bottom: 50, width: '100%' }}>
                    <div style={{ height: 50, width: '100%' }}>
                        <WingBlank size='lg'>
                            {/* 出价按钮 */}
                            <Flex justify='between' style={{ height: 50, textAlign: 'center' }}>
                                <Flex direction='row'>
                                    <Icon type="left" size={'md'} onClick={() => {
                                        console.log(this.state.price,this.state.startPrice)
                                        if (Number(this.state.price) > Number(this.state.startPrice/100)) {
                                            this.setState((state) => ({
                                                // 这里后期应该增加一些逻辑价格的逻辑功能，例如不会低于起拍价或当前最高竞拍价
                                                price: Number(state.price) - Number(state.intervalPrice / 100)
                                            }))
                                        }
                                    }} />
                                    <div style={{ paddingLeft: 10, paddingRight: 10 }} className='priceInputChange'>
                                        <InputItem
                                            type='number'
                                            value={this.state.price}
                                            moneyKeyboardAlign="left"
                                            onVirtualKeyboardConfirm={v => console.log('onVirtualKeyboardConfirm:', v)}
                                            style={{ width: 115, paddingLeft: 10, paddingRight: 10, height: 30 }}
                                            onChange={(value) => {
                                                this.setState({ price: value })
                                            }}
                                            // extra="电子"
                                            // clear
                                            disabledKeys={['.']}
                                        />
                                    </div>
                                    <Icon type="right" size={'md'} onClick={() => {
                                        this.setState((state) => ({
                                            // 这里后期应该增加一些逻辑价格的逻辑功能，例如不会高于某些价格上限
                                            price: Number(state.price) + Number(state.intervalPrice / 100)
                                        }))
                                    }} />
                                </Flex>
                                <Button style={{
                                    height: 30,
                                    lineHeight: '30px',
                                    width: 120,
                                    backgroundColor: '#FE2341',
                                    fontSize: 13,
                                }} type='warning' onClick={() => this.checkEarnest()}>出价</Button>
                            </Flex>
                        </WingBlank>
                    </div>
                </div>
            </div>
        )
    }
}

export default Sale

import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import {Button, Card, Flex, List, ListView, Modal, WingBlank, Radio, Grid, Toast} from 'antd-mobile'
import ListItem from 'antd-mobile/lib/list/ListItem'
import {api} from "../../../services/api/ApiProvider";

import awaitPayIcon from '../../../assets/awaitPay.svg'
import wePayIcon from '../../../assets/wepay.svg'
import aliPayIcon from '../../../assets/alipay.svg'

import wechatShareIcon from '../../../assets/wechatShare.svg'
import qqShareIcon from '../../../assets/qqShare.svg'
import qqzoneShareIcon from '../../../assets/qqzoneShare.svg'
import imageShareIcon from '../../../assets/imageShare.svg'
import scanShareIcon from '../../../assets/scanShare.svg'
import JUShareIcon from '../../../assets/JUShare.svg'
import evaluatePresentIcon from '../../../assets/evaluatePresent.svg'
import evaluateGoodIcon from '../../../assets/evaluateGood.svg'
import evaluateBlackListIcon from '../../../assets/evaluateBlackList.svg'
import awaitShare from "../../../assets/awaitShare.svg";
import awaitOrder from "../../../assets/awaitOrder.svg";
import awaitCheck from "../../../assets/awaitCheck.svg";
import awaitComplete from "../../../assets/awaitComplete.svg";
import {formatDate} from "../../../utils/utils";


const RadioItem = Radio.RadioItem;
export default class OrderList extends React.Component {
    state = {
        page: 1,
        limit: 10,
        total: 0,

        isLoading: false,
        hasMore: true,
        // 显示付款对话框
        showPayModal: false,
        // 显示分享对话框
        showShareModal: false,
        orderData: new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2
        }),
        modalPayData: {
            // 订单关闭时间
            date: '22:22:22',
            // 价格
            price: '8.99',
            // 订单ID
            id: '2021033100',
        },

        // 拼单成功对话框需要的
        modalOrderData: {
            id: '123123',
            avatar1: 'https://picsum.photos/200/300',
            avatar2: 'https://picsum.photos/200/300',
        },
        // 立即支付种类：0：钱包支付 1：微信支付 2：支付宝支付
        radioValue: 0,

        gridShareData: [
            {
                icon: wechatShareIcon,
                text: '微信'
            },
            {
                icon: qqShareIcon,
                text: 'QQ'
            },
            {
                icon: qqzoneShareIcon,
                text: 'QQ空间'
            },
            {
                icon: imageShareIcon,
                text: '图片分享'
            },
            {
                icon: scanShareIcon,
                text: '面对面扫码'
            },
            {
                icon: JUShareIcon,
                text: 'JU好友'
            },
        ],
        gridEvaluateData: [
            {
                icon: evaluatePresentIcon,
                text: '打赏',
            },
            {
                icon: evaluateGoodIcon,
                text: '赞一个',
            },
            {
                icon: evaluateBlackListIcon,
                text: '拉黑',
            },
        ]
    }

    async componentDidMount() {
        try {
            const boxOrderRes = await api.boxOrderService.queryMineBoxOrderRequest(this.getCurrentPageStatus(), this.state.page, this.state.limit);
            let newData = this.state.orderData.cloneWithRows(boxOrderRes.boxOrderItemList);
            this.setState({
                orderData: newData,
                total: boxOrderRes.total
            })
        } catch (e) {
            console.log(e);
        }
    }

    // 设置按照条件显示按钮
    renderButtonSwitch(dataItem) {
        switch (this.getOrderStatus(dataItem.payStatus, dataItem.status)) {
            case '待付款':
                return (
                    <div className='MinePHHOrderListBtnChange'>
                        <Flex justify='end'>
                            <div className='cancel'>
                                <Button onClick={(e) => {
                                    e.stopPropagation()
                                }}>取消定制</Button>
                            </div>
                            <div className='confirm'>
                                <Button onClick={(e) => {
                                    // 阻止事件冒泡
                                    e.stopPropagation(),
                                        this.setState({
                                            modalPayData: {
                                                id: dataItem.orderID,
                                                date: dataItem.date,
                                                price: dataItem.price,
                                            },
                                            showPayModal: true,
                                        }, () => {
                                            console.log(this.state.modalPayData)
                                        })
                                }}>立即付款</Button>
                            </div>
                        </Flex>
                    </div>
                );

            case '待分享':
                return (
                    <div className='MinePHHOrderListBtnChange'>
                        <Flex justify='end'>
                            <div className='share'>
                                <Button onClick={() => {
                                    this.setState({showShareModal: true})
                                }}>邀请好友拼单</Button>
                            </div>
                        </Flex>
                    </div>
                );
            case '已完成':
                return (
                    <div className='MinePHHOrderListBtnChange'>
                        <Flex justify='end'>
                            <div className='cancel'>
                                <Button>再来一单</Button>
                            </div>
                            <div className='cancel'>
                                <Button>已评价</Button>
                            </div>
                        </Flex>
                    </div>
                );
            case '待接单':
                return (
                    <div className='MinePHHOrderListBtnChange'>
                        <Flex justify='end'>
                            <div className='confirm'>
                                <Button onClick={(e) => this.remind(dataItem, e)}>催接单</Button>
                            </div>
                        </Flex>
                    </div>
                );
            case '待反馈':
                return (
                    <div className='MinePHHOrderListBtnChange'>
                        <Flex justify='end'>
                            <div className='cancel'>
                                <Button>反馈意见</Button>
                            </div>
                            <div className='confirm'>
                                <Button>查看详情</Button>
                            </div>
                        </Flex>
                    </div>
                );

        }
    }

    // 当获取到拼单成功数据后，执行函数
    showOrderModal(dataItem) {
        this.setState({
            showOrderModal: true
        })
        setTimeout(() => {
            this.setState({
                showOrderModal: false
            })
        }, 1500);

    }

    remind(dataItem, e) {
        // 阻止事件冒泡
        e.stopPropagation();
        // 网络请求成功后显示催单成功
        Toast.success('催单成功!')


    }

    onEndReached = (event) => {
        // load new data
        // hasMore: from backend data, indicates whether it is the last page, here is false
        // if (this.state.isLoading && !this.state.hasMore) {
        //     return;
        // }
        console.log('reach end', event);
        this.setState({isLoading: true});
        setTimeout(() => {
            // genData(++pageIndex);

            let newdata = this.state.orderData.cloneWithRowsAndSections(
                [
                    {
                        // 订单ID
                        orderID: '123321',
                        // 订单类型：例如8.99拼单，9.99拼单
                        orderType: '0',
                        // 订单名称：8.99拼单
                        oderName: '9.99定制单',
                        // 订单状态:待付款，待分享差1人，交易完成，付款成功，待接单，画师接单，待反馈，
                        orderStatus: '待分享',
                        // 图片URL
                        imageUrl: 'https://picsum.photos/200/300',
                        // 以下拼单内的具体属性：定制类型
                        itemType: '大头',
                        // 定制风格
                        itemStyle: '赛璐璐',
                        // 定制主题
                        itemTheme: '迷雾森林',
                        // 元素要求
                        element: '深色系、蝴123123123',
                        // 订单创建时间
                        date: '2021-09-08 08:99:99',
                        // 实付金额
                        price: '8.99',
                        avatar: 'https://picsum.photos/300/300',
                        // avatar2: 'https://picsum.photos/300/300',


                    },

                ]
            )

            this.setState({
                dataSource: newdata,
                pageSize: (this.state.pageSize) + 1,
                isLoading: false,
            }, () => {
                console.log(this.state.pageSize)
            });
        }, 1000);
    }

    getCurrentPageStatus = () => {
        const type = this.props.match.params.type;
        console.log(type)
        switch (type) {
            case 'awaitpay':
                return 0;
            case 'awaitshare':
                return 1;
            case 'awaitorder':
                return 2;
            case 'awaitcheck':
                return 3;
            case 'complete':
                return 4;
            default:
                return -1;
        }
    }

    switchTitle(type) {
        switch (type) {
            case 'all':
                return '全部订单';
            case 'awaitpay':
                return '待付款';
            case 'awaitshare':
                return '待分享';
            case 'awaitorder':
                return '待接单';
            case 'awaitcheck':
                return '待反馈';
            case 'complete':
                return '已完成';
        }
    }

    getOrderStatus(payStatus, status) {
        if (payStatus === 0) {
            return "待付款";
        } else if (payStatus === 1) {
            return "待分享";
        } else {
            if (status === 0) {
                return "待接单";
            } else if (status === 1) {
                return "制作中";
            } else if (status === 2) {
                return '待反馈';
            } else {
                return '已完成';
            }
        }
    }

    render() {
        return (
            <div className='MinePHHOrderList'>
                {/* 标题悬浮 */}
                <div style={{position: 'sticky', top: 0, zIndex: 2}}>
                    <Header title={this.switchTitle(this.props.match.params.type)}/>
                </div>

                <div>
                    <WingBlank size='lg'>
                        <ListView
                            style={{overflow: 'auto'}}
                            ref={el => this.lv = el}
                            dataSource={this.state.orderData}
                            renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                                {this.state.isLoading ? 'Loading...' : 'Loaded'}
                            </div>)}
                            pageSize={this.state.limit}
                            useBodyScroll
                            scrollRenderAheadDistance={50}
                            onEndReached={() => this.onEndReached()}
                            onEndReachedThreshold={10}
                            onScroll={(e) => {
                                console.log(e);
                            }}
                            renderRow={(dataItem) => {
                                return (
                                    <div className='MinePHHOrderListRenderRow'>
                                        <WingBlank size='md'>
                                            <Card onClick={() => {
                                                this.props.history.push(`/minephh/orderlist/${this.props.match.params.type}/detail/${dataItem.id}`)
                                            }}>
                                                <Card.Header
                                                    title={
                                                        <Flex justify='center' align='center' direction='column'
                                                              style={{marginTop: '10px'}}>
                                                            {/* 条件显示拼单图标 */}
                                                            <Flex justify='start' align='center'
                                                                  style={{width: '100%'}}>
                                                                {
                                                                    dataItem.customType === '0' &&
                                                                    <div className='MinePHHOrderListPIcon'/>
                                                                }
                                                                <div style={{
                                                                    padding: '0 10px',
                                                                    color: '#DC7798',
                                                                    fontSize: 14,
                                                                }}>{(dataItem.price / 100).toLocaleString()}定制单
                                                                </div>
                                                            </Flex>
                                                        </Flex>
                                                    }
                                                    extra={
                                                        <div style={{
                                                            fontSize: 12,
                                                            color: '#FE2341'
                                                        }}>{this.getOrderStatus(dataItem.payStatus, dataItem.status)}</div>
                                                    }
                                                />
                                                <Card.Body>
                                                    <Flex justify='start' align='start'>
                                                        <div style={{marginRight: 5}}>
                                                            <img src={dataItem.exampleUrl} alt=''
                                                                 style={{width: 75, height: 75, objectFit: 'cover',}}/>
                                                        </div>
                                                        <div className='MinePHHOrderListBodydiv'>
                                                            <Flex justify='start' direction='column' align='start'>
                                                                <div>{`定制类型：${dataItem.customType}`}</div>
                                                                <div>{`定制主题：${dataItem.customTheme}`}</div>
                                                                <div
                                                                    style={{color: '#999999',}}>{`${formatDate(dataItem.createTime)}`}</div>
                                                                {/*{*/}
                                                                {/*    this.props.match.params.type === 'awaitorder' &&*/}
                                                                {/*    <Flex justify='start' align='start'>*/}
                                                                {/*        <img src={dataItem.avatar[0]} style={{*/}
                                                                {/*            width: 20,*/}
                                                                {/*            height: 20,*/}
                                                                {/*            objectFit: 'contain',*/}
                                                                {/*            borderRadius: '50%',*/}
                                                                {/*            marginRight: 10*/}
                                                                {/*        }} alt=''/>*/}
                                                                {/*        <img src={dataItem.avatar[1]} style={{*/}
                                                                {/*            width: 20,*/}
                                                                {/*            height: 20,*/}
                                                                {/*            objectFit: 'cover',*/}
                                                                {/*            borderRadius: '50%'*/}
                                                                {/*        }} alt=''/>*/}
                                                                {/*    </Flex>*/}
                                                                {/*}*/}
                                                            </Flex>
                                                        </div>
                                                        <div className='MinePHHOrderListBodydiv'>
                                                            <Flex justify='start' direction='column' align='start'>
                                                                <div>{`定制风格：${dataItem.customStyle}`}</div>
                                                                <div>{`元素要求：${dataItem.elementRequirements}`}</div>
                                                            </Flex>
                                                        </div>
                                                    </Flex>
                                                </Card.Body>
                                                <Card.Footer
                                                    content={
                                                        <Flex justify='start' align='center'
                                                              style={{lineHeight: '20px'}}>
                                                            <div style={{fontSize: 13, color: 'white'}}>{`实付：`}<span
                                                                style={{
                                                                    color: '#FE2341',
                                                                    fontSize: 12
                                                                }}>{`${(dataItem.price / 100).toLocaleString()}电子`}</span>
                                                            </div>
                                                        </Flex>
                                                    }
                                                    extra={
                                                        this.renderButtonSwitch(dataItem)
                                                    }
                                                />
                                            </Card>
                                        </WingBlank>
                                    </div>
                                )
                            }

                            }
                        />
                    </WingBlank>
                </div>
                {/* 付款对话框 */}
                <Modal
                    className='MinePHHOrderListModalPay'
                    visible={this.state.showPayModal}
                    onClose={() => {
                        this.setState({showPayModal: false})
                    }}
                    closable={true}
                    maskClosable={false}
                    transparent={true}
                    style={{width: '100%', position: 'absolute', bottom: 0, height: 310}}
                    title={
                        <Flex direction='column'>
                            <div style={{color: '#FE2341', fontSize: 12}}>{this.state.modalPayData.date}<span
                                style={{fontSize: 12, color: 'white'}}>后订单关闭</span></div>
                            <div style={{
                                color: 'white',
                                padding: '10px 0',
                                fontSize: 25
                            }}>{`${this.state.modalPayData.price}电子`}</div>
                            <div style={{color: '#999999', fontSize: 18}}>{`¥${this.state.modalPayData.price}`}</div>
                        </Flex>
                    }
                >
                    <List>
                        <ListItem
                            onClick={() => {
                                this.setState({radioValue: 0})
                            }}
                            thumb={
                                <img style={{height: 25, width: 25, objectFit: 'cover'}} src={awaitPayIcon}/>
                            }
                            extra={
                                <RadioItem checked={this.state.radioValue === 0}/>
                            }
                        >钱包支付</ListItem>
                        <ListItem
                            onClick={() => {
                                this.setState({radioValue: 1})
                            }}
                            thumb={
                                <img style={{height: 25, width: 25, objectFit: 'cover'}} src={wePayIcon}/>
                            }
                            extra={
                                <RadioItem checked={this.state.radioValue === 1}/>
                            }
                        >微信支付</ListItem>
                        <ListItem
                            onClick={() => {
                                this.setState({radioValue: 2})
                            }}
                            thumb={
                                <img style={{height: 25, width: 25, objectFit: 'cover'}} src={aliPayIcon}/>
                            }
                            extra={
                                <RadioItem checked={this.state.radioValue === 2}/>
                            }
                        >支付宝支付</ListItem>
                    </List>
                    <div className='MinePHHOrderListpayBtn'>
                        <Button>立即支付</Button>
                    </div>
                </Modal>
                {/* 付款对话框结束 */}

                {/* 分享对话框 */}
                <Modal
                    className='MinePHHOrderListModalPay'
                    // visible={this.state.showShareModal}
                    visible={this.state.showShareModal}

                    onClose={() => {
                        this.setState({showShareModal: false})
                    }}
                    maskClosable={true}
                    transparent={true}
                    style={{width: '80%', height: 'auto'}}
                >
                    <Flex justify='center' direction='column' className='MinePHHOrderListModalShare'>
                        <div>还差一人，赶快邀请好友来拼单！</div>
                        <div style={{fontSize: 12, color: '#AAAAAA'}}>拼单成功能更快被画师接单哦~</div>
                    </Flex>
                    <Grid
                        data={this.state.gridShareData}
                        hasLine={false}
                        // square={true}
                        columnNum={3}
                        itemStyle={{border: 0, color: 'white', height: 80,}}
                        activeStyle={true}
                        activeClassName='MinePHHOrderListactiveclass'
                        renderItem={(dataItem) =>
                            <Flex justify='center' align='center' direction='column'
                                  style={{height: '100%', backgroundColor: 'transparent'}}>
                                <img src={dataItem.icon} style={{width: 30, height: 30}}/>
                                <div style={{fontSize: 15, color: 'white'}}>{dataItem.text}</div>
                            </Flex>
                        }
                    />
                </Modal>
                {/* 分享对话框结束 */}

                {/* 拼单成功对话框 */}
                <Modal
                    className='MinePHHOrderListModalPay'
                    // visible={this.state.showShareModal}
                    visible={this.state.showOrderModal}
                    // closable={true}
                    onClose={() => {
                        this.setState({showOrderModal: false})
                    }}
                    maskClosable={true}
                    transparent={true}
                    style={{width: '80%', height: 'auto'}}
                >
                    <Flex justify='center' align='center' direction='column' style={{color: 'white', fontSize: 15}}>
                        <div style={{fontSize: 20}}>拼单成功</div>
                        <div style={{color: '#AAAAAA', fontSize: 12}}>就等画师接单吧</div>
                        {/* 头像地址由接口提供 */}
                        <Flex justify='around' style={{width: '50%'}} className='MinePHHOrderListModalOrderBg'
                              style={{width: '250px'}}>
                            <img alt='' src={this.state.modalOrderData.avatar1} style={{
                                width: 50,
                                height: 50,
                                borderRadius: '50%',
                                objectFit: 'cover',
                                border: '3px solid white'
                            }}/>
                            <div style={{fontSize: 25}}>&</div>
                            <img alt='' src={this.state.modalOrderData.avatar2} style={{
                                width: 50,
                                height: 50,
                                borderRadius: '50%',
                                objectFit: 'cover',
                                border: '3px solid white'
                            }}/>
                        </Flex>
                    </Flex>
                </Modal>

                {/* 评价对话框 */}
                <Modal
                    className='MinePHHOrderListModalPay'
                    visible={this.state.showOrderModal}
                    // visible={true}
                    // closable={true}
                    onClose={() => {
                        this.setState({showOrderModal: false})
                    }}
                    maskClosable={true}
                    transparent={true}
                    style={{width: '80%', height: 'auto'}}
                >
                    <Flex justify='center' align='center' direction='column' style={{fontSize: 12, color: '#AAAAAA'}}>
                        <div style={{fontSize: 18, color: 'white'}}>评价画师此次定制</div>
                        <div>你可针对此次定制对画师进行评价</div>
                        <div>将更好的提高画师创作积极性~</div>
                    </Flex>
                    <div className='MinePHHOrderListGridTextChange'>
                        <Grid
                            data={this.state.gridEvaluateData}
                            columnNum={3}
                            hasLine={false}
                            activeClassName='MinePHHOrderListactiveclass'
                            itemStyle={{color: 'white'}}
                        />
                    </div>
                </Modal>
                {/* 评价对话框结束 */}

            </div>
        )
    }
}

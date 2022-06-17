import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import {Badge, Button, Card, Flex, Grid, Icon, List, Popover, WingBlank} from 'antd-mobile'
import ListItem from 'antd-mobile/lib/list/ListItem'
import awaitPayIcon from '../../assets/awaitPay.svg'
import awaitShare from '../../assets/awaitShare.svg'
import awaitOrder from '../../assets/awaitOrder.svg'
import awaitCheck from '../../assets/awaitCheck.svg'
import awaitComplete from '../../assets/awaitComplete.svg'

export default class MinePHH extends React.Component {
    state = {
        gridData: [
            {
                icon: awaitPayIcon,
                text: '待付款',
                router: '/minephh/orderlist/awaitpay',
                badge: 0,
            },
            {
                icon: awaitShare,
                text: '待分享',
                router: '/minephh/orderlist/awaitshare',
                badge: 0,
            },
            {
                icon: awaitOrder,
                text: '待接单',
                router: '/minephh/orderlist/awaitorder',
                badge: 0,
            },
            {
                icon: awaitCheck,
                text: '待反馈',
                router: '/minephh/orderlist/awaitcheck',
                badge: 0,
            },
            {
                icon: awaitComplete,
                text: '已完成',
                router: '/minephh/orderlist/complete',
                badge: 0,
            },
        ],
        awaitShareItem: [
            // {
            //     shareUrl: '123123',
            //     imageUrl: 'https://picsum.photos/200/300',
            //     number: 1,
            //     date: '20:12:12'
            // },
            // {
            //     shareUrl: '223223',
            //     imageUrl: 'https://picsum.photos/200/300',
            //     number: 2,
            //     date: '20:12:12'
            // },
        ]
    }

    render() {
        return (
            <div className='MinePHHPage'>
                <Header title='我的拼盒盒'/>

                <div className='orderCard'>
                    <WingBlank size='lg'>
                        <Card>
                            <Card.Header
                                title='我的订单'
                                arrow='horizontal'
                                extra={
                                    <Flex align='center' justify='end' onClick={() => {
                                        console.log("点击查看全部")
                                    }}>
                                        <div style={{lineHeight: '22px'}} onClick={() => {
                                            this.props.history.push('/minephh/orderlist/all')
                                        }}>查看全部
                                        </div>
                                        <Icon type='right'/>
                                    </Flex>
                                }
                            />
                            <Card.Body>
                                <div>
                                    <Grid
                                        data={this.state.gridData}
                                        columnNum={5}
                                        hasLine={false}
                                        square={false}
                                        activeStyle={false}
                                        renderItem={
                                            dataItem => (
                                                <div style={{height: 60,}} onClick={() => {
                                                    this.props.history.push(dataItem.router)
                                                }}>
                                                    <Flex align='center' direction='column' justify='start'>
                                                        <div style={{width: 20, height: 20}}
                                                             className='MinePHHBadgeChange'>
                                                            <Badge text={dataItem.badge} overflowCount={99} style={{
                                                                position: 'relative',
                                                                left: '16px',
                                                                top: '12px',
                                                                width: 18,
                                                                height: 18
                                                            }}/>
                                                        </div>
                                                        <img style={{width: 28, height: 28, objectFit: 'contain'}}
                                                             src={dataItem.icon}/>
                                                    </Flex>
                                                    <div style={{
                                                        padding: 5,
                                                        color: 'white',
                                                        fontSize: 15
                                                    }}>{dataItem.text}</div>
                                                </div>
                                            )
                                        }
                                    />
                                </div>

                                {/* 显示待分享面板 */}
                                {
                                    this.state.awaitShareItem.length > 0 &&
                                    <div className='MinePHHPopover'>
                                        <List>
                                            {
                                                this.state.awaitShareItem.map((item, index) =>
                                                    <ListItem
                                                        key={index}
                                                        thumb={item.imageUrl}
                                                        extra={
                                                            <div className='MinePHHPopoverBtnChange'>
                                                                <Flex justify='end'>
                                                                    <Button onClick={() => {
                                                                        console.log(item.shareUrl)
                                                                    }}>邀请好友</Button>
                                                                </Flex>
                                                            </div>}
                                                    >
                                                        {`还差${item.number}人，剩`}
                                                        {<span style={{color: '#FE2341'}}>{item.date}</span>}
                                                    </ListItem>
                                                )

                                            }

                                        </List>
                                    </div>
                                }

                            </Card.Body>
                            {/* <Card.Footer>
                                <div>
                                    11111
                                </div>
                            </Card.Footer> */}
                        </Card>
                    </WingBlank>

                </div>
            </div>

        )
    }
}

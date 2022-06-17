import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { Flex, Badge, Drawer, List, WhiteSpace, Tabs, Card, Button, Toast } from 'antd-mobile'

import Order from './Order/index'
import Production from './Production/index'
import Check from './Check/index'
import Complete from './Complete/index'

import messageIcon from '../../assets/qhhMessage.svg'
import totalIcon from '../../assets/qhhTotal.svg'
import changeInfoIcon from '../../assets/qhhChangeInfo.svg'
import qhhQDQIcon from '../../assets/qhhQDD.png'


class QHH extends React.Component {
    state = {
        tabs: [
            {
                title: '抢单'
            },
            {
                title: '制作中'
            },
            {
                title: '反馈中'
            },
            {
                title: '已完成'
            },
        ],
        // 是否存在消息
        message: true,
        // 是否存在所有内容更改

        total: true,

        // 抽屉开关
        drawerOpen: false,
        userData: {
            name: '138****9999',
            avatar: 'https://picsum.photos/200/300',
            intro: '个人简介~~~222222222222222222222233333333333333333',
        },
        drawerItems: [
            {
                label: '跨次元平台',
                route: '',
                key: 0
            },
            {
                label: '钱包',
                route: '/qhh/wallet',
                key: 1
            },
            {
                label: '个人中心',
                route: '/qhh/mine',
                key: 2
            },
            {
                label: '设置',
                route: '',
                key: 3
            },
            {
                label: '协议',
                route: '',
                key: 4
            },
        ]
    }

    render() {
        const state = this.state;
        return (
            <div className='qhhPage' style={{ zIndex: 999 }}>

                {/* 抽屉 用于在屏幕边缘显示应用导航等内容的面板。 */}
                <Drawer
                    // enableDragHandle
                    open={this.state.drawerOpen}
                    position='right'
                    onOpenChange={(value) => {
                        this.setState({
                            drawerOpen: value
                        })
                    }}
                    sidebar={
                        <div className='drawerBanner'>
                            <Flex justify='center' direction='column' style={{ width: '100%', }}>
                                <div style={{ width: '100%', paddingTop: 40 }} className='CardClassChange'>
                                    <Card full>
                                        <Card.Header
                                            thumb={this.state.userData.avatar}
                                            thumbStyle={{ width: 50, height: 50, borderRadius: '50%' }}
                                            title={
                                                <Flex justify='start' direction='column' align='start'>
                                                    <Flex justify='start'>
                                                        <div style={{ fontSize: 12, padding: '5px 0' }}>{this.state.userData.name}</div>
                                                        <img alt='' src={changeInfoIcon} style={{ width: 12, height: 12, marginLeft: 10, lineHeight: '22px', objectFit: 'cover' }} />
                                                    </Flex>
                                                    <div style={{ fontSize: 12, padding: '5px 0', color: '#AAAAAA', width: '200px', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{this.state.userData.intro}</div>
                                                </Flex>
                                            }
                                        />
                                        <Card.Body>
                                            <div className='QDDBanner'>
                                                <Flex justify='between' align='center' style={{ alignSelf: 'center', height: 50, width: '100%' }}>
                                                    <img alt='' src={qhhQDQIcon} style={{ width: 37, height: 44, lineHeight: '50px', }} />
                                                    <Flex justify='center' direction='column' align='start' style={{ overflow: 'visible', whiteSpace: 'nowrap', position: 'absolute', left: 80, top: 25 }}>
                                                        <div style={{ fontSize: 15 }}>抢单豆</div>
                                                        <div style={{ fontSize: 12, transform: 'scale(0.6)', transformOrigin: '0 0', padding: '5px 0' }}>抢单豆由平台发放，不可转售</div>
                                                    </Flex>
                                                    <div className='BtnChange'>
                                                        <Button>质押中</Button>
                                                    </div>
                                                </Flex>
                                            </div>
                                            <div className='settingListChange'>
                                                <List>
                                                    {
                                                        this.state.drawerItems.map((item, index) =>
                                                            <List.Item
                                                                // arrow='horizontal'
                                                                key={item.key}
                                                                activeStyle={{ backgroundColor: '#222222' }}
                                                                onClick={() => {
                                                                    if (item.route === '') {
                                                                        Toast.fail('正在开发中~')
                                                                    }
                                                                    else {
                                                                        this.props.history.push(item.route);
                                                                    }
                                                                }}
                                                            >
                                                                {item.label}
                                                            </List.Item>
                                                        )
                                                    }
                                                </List>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </div>
                            </Flex>
                        </div>
                    }
                    sidebarStyle={{ backgroundColor: 'black', width: 300, fontSize: 12, fontWeight: 600, color: 'white' }}
                    overlayStyle={{ backgroundColor: 'rgba(255,255,255,0.2)' }}
                >
                    <Header title='抢盒盒'>
                        <Flex justify='between'>
                            <div style={{ height: 30, lineHeight: '30px', width: 30 }}>
                                <Badge dot={this.state.message} size='small' style={{ zIndex: 2 }}>
                                    <img alt='' src={messageIcon} style={{ width: 20, height: 20 }} />
                                </Badge>
                            </div>
                            <div style={{ height: 30, lineHeight: '30px', width: 30, textAlign: 'center' }} onClick={() => { this.setState({ drawerOpen: true }, () => { console.log(this.state.drawerOpen) }) }}>
                                <Badge dot={this.state.total} size='small' style={{ zIndex: 2 }}>
                                    <img alt='' src={totalIcon} style={{ width: 20, height: 20 }} />
                                </Badge>
                            </div>
                        </Flex>
                    </Header>
                    <div className='tabsChange'>
                        <Tabs tabs={state.tabs}
                            initialPage={0}
                            animated={false}
                            useOnPan={false}
                            swipeable={false}
                            usePaged={true}
                            onTabClick={() => {
                            }}
                            tabBarUnderlineStyle={{ backgroundColor: '#FFFF00', color: '#FFFF00' }}
                            tabBarBackgroundColor={'black'}
                            tabBarActiveTextColor={'#FFFF00'}
                            tabBarInactiveTextColor={'white'}
                        >
                            <div>
                                <Order />
                            </div>
                            <div>
                                <Production />
                            </div>
                            <div>
                                <Check />
                            </div>
                            <div>
                                <Complete />
                            </div>
                        </Tabs>
                    </div>
                </Drawer>

            </div>
        )
    }
}

export default QHH

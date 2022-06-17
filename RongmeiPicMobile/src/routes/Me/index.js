import React from 'react'
import {
    List,
    Grid,
    Tabs,
    Card,
    Modal,
    Flex,
    TabBar,
    WingBlank,
    Button,
    WhiteSpace,
    Toast,
    ListView,
    Badge
} from "antd-mobile";
import { api } from "../../services/api/ApiProvider";
import { withRouter } from 'react-router-dom';
import Background from './default.png';
// import CustomIcon from './set.svg'
// import manIcon from './man.svg'
// import womenIcon from './women.svg'
// import leftIcon from '../../assets/otherLeft.svg'
import moreIcon from '../../assets/otherMore.svg'
import creatorIcon from '../../assets/creator.svg'
import memberIcon from '../../assets/member.svg'
import NPCIcon from '../../assets/NPC.svg'
import mineShareCodeIcon from '../../assets/mineShareCode.svg'
import messageIcon from '../../assets/message.svg'
import pleaseUpdateIcon from '../../assets/pleaseUpdate.svg'
import defaultAvatar from '../../assets/logo.png'


import './me.css'
import 'animate.css'
import { Linear } from 'gsap';

const Item = List.Item;
const Brief = Item.Brief;
const alert = Modal.alert;

@withRouter
class Me extends React.Component {
    accountService = api.accountService;
    auctionService = api.auctionService;
    childService = api.childService;
    commodityService = api.commodityService;
    examService = api.examService;
    distributionService = api.distributionService;

    state = {
        data: [],
        avatarUrl: "",
        nickname: "",
        largeCoins: 0,
        follows: 0,
        intro: "",
        gender: "man",
        username: "",
        isPassExam: false,

        selectItem: 0,
        selectedTab: "0",
        showAnswerModal: false,
        // 认证类型
        authentication: 0,
        // 粉丝数量
        fans: '100',
        dataSource: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        limit: 10,
        page: 0,
        loading: true,

        shareImageModalVisible: false,
        code: '',
        qrcodeUrl: ''
    }

    async componentDidMount() {
        let res = await this.accountService.getUserInfo();
        console.log(res)
        res = Object.assign(res, await this.accountService.getUserAccount())
        console.log(res)
        // res = await this.accountService.getUserAccount();

        // 捕获404异常
        try {
            const userExamRes = await this.examService.getUserExam();
            this.setState({
                isPassExam: userExamRes.isPass,
                showAnswerModal: !userExamRes.isPass
            })
        } catch (err) {
            this.setState({
                isPassExam: false,
                showAnswerModal: true
            })
        }
        // 尝试修复BUG
        await this.setState({
            avatarUrl: res.avatarUrl,
            nickname: res.nickname,
            gender: res.gender,
            intro: res.intro,
            username: res.username,
            largeCoins: res.largeCoins,
            follows: 10,
            // isPassExam: userExamRes.isPass,
            // showAnswerModal: !userExamRes.isPass
        })

        try {
            const distributionRes = await this.distributionService.getUserDistribution(res.username);
            if (distributionRes.code) {
                this.setState({
                    code: distributionRes.code,
                    qrcodeUrl: distributionRes.qrcodeUrl
                })
            }
        } catch (e) {
            console.log(e)
        }
        await this.getData();
    }

    showLogout = () => {
        alert('退出登录', '你确定要退出登录吗?', [
            { text: '取消', onPress: () => console.log('cancel'), style: 'default' },
            {
                text: '确定', onPress: () => {
                    localStorage.clear();
                    this.props.history.push('/login')
                }
            },
        ]);
    };

    topSectionStyle = {
        width: "100%",
        height: "219pt",
        backgroundImage: `url(${Background})`,
        backgroundSize: '100% 100%',
        objectFit: 'cover',
    };

    meCardStyle = {
        borderWidth: 0, // Remove Border
        shadowColor: 'rgba(0,0,0, 0.0)', // Remove Shadow IOS
        background: 'rgba(0,0,0,0)',
        margin: '-10px 0px 0px 0',
        border: '0px solid #e8e8e8',
    }

    tabs = [
        { title: '作品区' },
        { title: '资产区' },
        // {title: '素材'},
    ];

    async getData() {
        if (this.state.selectItem === 0) {
            // const thingRes = await this.auctionService.getOwnerArtworks(this.state.username);
            // if (thingRes.thingItems) {
            //     let newItems = [];
            //     for (let i = 0; i < thingRes.thingItems.length; i++) {
            //         newItems = newItems.concat({
            //             id: thingRes.thingItems[i].id,
            //             title: thingRes.thingItems[i].name,
            //             imageUrl: thingRes.thingItems[i].url
            //         })
            //     }
            //
            //     let newDataSourse = this.state.dataSource.cloneWithRows(newItems)
            //     await this.setState({
            //         dataSource: newDataSourse
            //     }, () => {
            //         console.log(this.state.dataSource)
            //     })
            // }

            const childRes = await this.childService.getMineChild(1, 10000);
            if (childRes.simpleChildItemList) {
                let newItems = [];
                for (let i = 0; i < childRes.simpleChildItemList.length; i++) {
                    newItems = newItems.concat({
                        id: childRes.simpleChildItemList[i].id,
                        title: childRes.simpleChildItemList[i].content,
                        imageUrl: childRes.simpleChildItemList[i].coverUrl
                    })
                }

                let newDataSourse = new ListView.DataSource({
                    rowHasChanged: (row1, row2) => row1 !== row2,
                });
                newDataSourse = newDataSourse.cloneWithRows(newItems)
                await this.setState({
                    dataSource: newDataSourse
                }, () => {
                    console.log(this.state.dataSource)
                })
            }
        } else if (this.state.selectItem === 1) {
            const thingRes = await this.auctionService.getFavoritesThings(0, parseInt(this.state.selectedTab));
            if (thingRes.thingItems) {
                let newItems = [];
                for (let i = 0; i < thingRes.thingItems.length; i++) {
                    newItems = newItems.concat({
                        id: thingRes.thingItems[i].id,
                        title: thingRes.thingItems[i].name,
                        imageUrl: thingRes.thingItems[i].url
                    })
                }

                let newDataSourse = new ListView.DataSource({
                    rowHasChanged: (row1, row2) => row1 !== row2,
                });
                newDataSourse = newDataSourse.cloneWithRows(newItems)
                await this.setState({
                    dataSource: newDataSourse
                }, () => {
                    console.log(this.state.dataSource)
                })
            }
        } else {
            const commodityRes = await this.commodityService.getFavoritesCommodities(0, parseInt(this.state.selectedTab));
            if (commodityRes.commodities) {
                let newItems = [];
                for (let i = 0; i < commodityRes.commodities.length; i++) {
                    newItems = newItems.concat({
                        title: commodityRes.commodities[i].title,
                        imageUrl: commodityRes.commodities[i].coverUrl
                    })
                }
                this.setState({
                    data: newItems
                })
            }
        }
    }

    render() {
        const state = this.state
        return (
            <div style={{ height: '100%' }} className='mePage'>


                <div className='headerBanner'>
                    <div style={{ padding: '5px 0px', width: '100%', minHeight: 50, }}>
                        <Flex justify='between'>
                            <div style={{ padding: '5px 0px', }}>
                                {
                                    this.state.isPassExam ?
                                        <div style={{
                                            background: '#00CC00',
                                            borderTopRightRadius: '20px',
                                            borderBottomRightRadius: '20px',
                                            color: 'white',
                                            fontSize: '12px',
                                            paddingTop: '10px',
                                            paddingBottom: '10px',
                                            paddingRight: '5px',
                                            textAlign: 'center',
                                            width: '110px',
                                            float: 'left',
                                        }}
                                            onClick={() => {
                                                this.props.history.push("/answer")
                                            }}
                                        >
                                            会员答题已完成
                                        </div> :
                                        <div style={{
                                            background: '#FFA200',
                                            borderTopRightRadius: '20px',
                                            borderBottomRightRadius: '20px',
                                            color: 'white',
                                            fontSize: '12px',
                                            paddingTop: '10px',
                                            paddingBottom: '10px',
                                            paddingRight: '5px',
                                            textAlign: 'center',
                                            width: '110px',
                                            float: 'left'
                                        }}
                                            onClick={() => {
                                                this.props.history.push("/answer")
                                            }}
                                        >
                                            参与会员答题
                                        </div>
                                }
                            </div>
                            <div style={{ padding: '5px 10px', display: 'flex', alignItems: 'center' }}>
                                <div style={{
                                    // width: 100,
                                    height: 30,
                                    marginRight: 15,
                                    display: 'inline-block',
                                    borderRadius: 25,
                                    backgroundColor: 'rgba(0,0,0,0.45)',
                                    lineHeight: '35px'
                                }}
                                     onClick={() => this.props.history.push('/message')}>
                                    <Flex justify='center' align='center'
                                          style={{ width: '90%', padding: '10px', height: 30, lineHeight: '30px' }}>
                                        <Badge dot>
                                            <img alt='' style={{ width: 20, height: 20 }} src={messageIcon} />
                                        </Badge>
                                    </Flex>
                                </div>

                                <div style={{
                                    // width: 100,
                                    height: 30,
                                    marginRight: 15,
                                    display: 'inline-block',
                                    borderRadius: 25,
                                    backgroundColor: 'rgba(0,0,0,0.45)',
                                    lineHeight: '35px'
                                }}
                                    onClick={() => console.log('求更新通知面板')}>
                                    <Flex justify='center' align='center'
                                        style={{ width: '90%', padding: '10px', height: 30, lineHeight: '30px' }}>
                                        <Badge dot>
                                            <img alt='' style={{ width: 20, height: 20 }} src={pleaseUpdateIcon} />
                                        </Badge>
                                    </Flex>
                                </div>
                                <img alt='' style={{ width: 35, height: 35 }} src={moreIcon} onClick={
                                    () => this.props.history.push('/setting')} />
                            </div>
                        </Flex>
                    </div>
                    <Card full>
                        <Card.Header
                            thumb={
                                <div>
                                    <img alt='' src={state.avatarUrl === '' ? defaultAvatar : state.avatarUrl}
                                        style={{ width: 65, height: 65, borderRadius: '50%', objectFit: 'cover' }} />
                                </div>
                            }
                            //25的lineheight 是因为padding 10 总高度又为45
                            title={
                                <div style={{ fontSize: 15, color: 'white', fontWeight: 600 }}>
                                    <Flex justify='between' align='center' style={{ height: '100%', lineHeight: '25px' }}>
                                        <div style={{
                                            padding: '10px 0px 10px 10px',
                                            fontSize: 16,
                                            minWidth: 60
                                        }}>{state.nickname === '' ? state.username : state.nickname}</div>
                                        <Flex justify='center' style={{ paddingLeft: 10 }}>
                                            <div className='followBtn'>
                                                <Button onClick={() => this.props.history.push('/setting/info')}
                                                    style={state.isFollow ? { backgroundColor: '#222222' } : {}}>{`编辑资料`}</Button>
                                            </div>
                                        </Flex>
                                    </Flex>

                                    <Flex>
                                        <div style={{
                                            width: 'auto',
                                            backgroundColor: 'rgba(0,0,0,0.3)',
                                            borderRadius: 15,
                                            marginLeft: 10,
                                            padding: '3px 10px',
                                            display: 'flex',
                                            position: 'relative',
                                            alignItems: 'center'
                                        }}>
                                            <img alt=''
                                                src={state.authentication === 0 ? memberIcon : state.authentication === 1 ? creatorIcon : NPCIcon}
                                                style={{
                                                    width: 16,
                                                    height: 16,
                                                    alignSelf: 'center',
                                                    lineHeight: '20px'
                                                }} />
                                            <div style={{
                                                fontSize: 13,
                                                marginLeft: -5,
                                                lineHeight: '20px',
                                                height: 20
                                            }}>{state.authentication === 0 ? '会员认证' : state.authentication === 1 ? '创作者认证' : 'NPC认证'}</div>
                                        </div>
                                        <div style={{
                                            width: 'auto',
                                            backgroundColor: 'rgba(0,0,0,0.3)',
                                            borderRadius: 15,
                                            marginLeft: 10,
                                            padding: '3px 10px',
                                            display: 'flex',
                                            position: 'relative',
                                            alignItems: 'center'
                                        }}
                                            onClick={() => {
                                                this.setState({
                                                    shareImageModalVisible: true
                                                })
                                            }}
                                        >
                                            <img alt='' src={mineShareCodeIcon} style={{
                                                width: 16,
                                                height: 16,
                                                alignSelf: 'center',
                                                lineHeight: '20px'
                                            }} />
                                            <div style={{
                                                fontSize: 13,
                                                marginLeft: -5,
                                                lineHeight: '20px',
                                                height: 20
                                            }}>{`我的邀请码`}</div>
                                        </div>
                                    </Flex>
                                </div>
                            }
                        />

                        <Modal
                            visible={this.state.shareImageModalVisible}
                            transparent
                            maskClosable={false}
                            onClose={() => {
                                this.setState({
                                    shareImageModalVisible: false
                                })
                            }}
                            title="邀请码"
                            footer={[{
                                text: '关闭', onPress: () => {
                                    this.setState({
                                        shareImageModalVisible: false
                                    })
                                }
                            }]}
                        >
                            <div>
                                <div style={{ color: 'black' }}>{this.state.code}</div>
                                <img style={{ width: '150px', height: '150px' }} src={this.state.qrcodeUrl} />
                            </div>
                        </Modal>

                        <Card.Body style={{ color: 'white', fontWeight: 600 }}>
                            <Flex justify='start'>
                                <div style={{ padding: 10 }}>{state.follows}<span style={{ color: '#DDDDDD' }}> 关注</span>
                                </div>
                                <div style={{ padding: 10 }}>{state.fans}<span style={{ color: '#DDDDDD' }}> 粉丝</span></div>
                            </Flex>
                            <Flex justify='start'>
                                <div style={{
                                    fontWeight: 600,
                                    color: 'white',
                                    fontSize: 12,
                                    lineHeight: '18px',
                                    paddingLeft: 10,
                                    paddingRight: 30,
                                    minHeight: '40px',
                                    width: '100%'
                                }}>{`个人介绍：${this.state.intro && this.state.intro.length > 0 ? this.state.intro : "这里可以是个人介绍也可以是个性签名"}`}</div>
                            </Flex>
                            <Flex justify='center'>
                                <div className="cardCoin">
                                    <span className="cardcoinz" style={{ color: 'white' }}>积分余额:</span>
                                    <span
                                        className="cardCoins">{(this.state.largeCoins / 100).toLocaleString()}</span>
                                    <span className="IMM" style={{ color: 'white' }}>电子</span>
                                </div>
                            </Flex>
                        </Card.Body>
                        <Card.Footer
                            content={<button className="button-tras"
                                onClick={() => {
                                    this.props.history.push("/me/portal")
                                }}>交易凭证</button>}
                            extra={<button className="button-rech"
                                onClick={() => {
                                    this.props.history.push("/me/coin")
                                }}> 钱包</button>} />
                        <Flex justify='center' align='center'>
                            <div style={{
                                height: 125,
                                width: 35,
                                position: 'absolute',
                                top: 100,
                                right: 0,
                                textAlign: "center",
                                justifyContent: 'center',
                                alignItems: 'center',
                                display: 'flex',
                                borderTopLeftRadius: 7,
                                borderBottomLeftRadius: 7,
                                border: '2px solid white',
                                background: 'linear-gradient(#45A3D0,#DF7696)'

                            }}>
                                <div style={{
                                    width: 35,
                                    textAlign: 'center',
                                    justifyContent: "center",
                                    // writingMode: '',
                                    fontSize: 18,
                                    display: 'inline-block',
                                    fontWeight: 600,
                                    color: 'white',
                                    lineHeight: '20px'
                                }}
                                    onClick={() => {
                                        this.props.history.push('/minephh')
                                    }}
                                >我的拼盒盒
                                </div>
                            </div>
                        </Flex>
                        {/* 我的拼盒盒结束 */}
                    </Card>

                </div>


                <Flex className="footSection">
                    <Tabs tabs={this.tabs}
                        page={this.state.selectItem}
                        tabBarBackgroundColor='#000'
                        tabBarInactiveTextColor='#AAAAAA'
                        tabBarActiveTextColor='#FFFFFF'
                        tabBarTextStyle={{ fontSize: '14px' }}
                        tabBarUnderlineStyle={{
                            borderColor: '#FFFFFF',
                            borderWidth: '0.1px',
                            borderRadius: '1pt',
                            marginBottom: '5px',
                        }}
                        animated={true}
                        useOnPan={false}
                        onChange={async (data, index) => {
                            await this.setState({
                                selectItem: index
                            })
                            await this.getData();
                        }}
                    >
                        <div style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            height: '100%',
                            backgroundColor: '#000'
                        }}>

                        </div>
                        <div style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            height: '100%',
                            backgroundColor: '#000'
                        }}>
                            <TabBar
                                style={{ width: '100%' }}
                                unselectedTintColor="#949494"
                                tintColor="#33A3F4"
                                barTintColor="#000"
                                hidden={false}
                                tabBarPosition="top"
                            >
                                <TabBar.Item
                                    title={null}
                                    key="2"
                                    icon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '14px',
                                        color: '#AAAAAA'
                                    }}
                                    >在拍</div>
                                    }
                                    selectedIcon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#FFFFFF',
                                        borderRadius: '5px',
                                        paddingTop: '5px',
                                        paddingBottom: '5px',
                                        backgroundColor: '#FE0341',
                                    }}
                                    >在拍</div>}
                                    selected={this.state.selectedTab === '2'}
                                    onPress={async () => {
                                        await this.setState({
                                            selectedTab: '2',
                                        });
                                        await this.getData();
                                    }}
                                >
                                </TabBar.Item>
                                <TabBar.Item
                                    title={null}
                                    key="0"
                                    icon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#AAAAAA'
                                    }}
                                    >藏品</div>
                                    }
                                    selectedIcon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#FFFFFF',
                                        borderRadius: '5px',
                                        paddingTop: '5px',
                                        paddingBottom: '5px',
                                        backgroundColor: '#FE0341',
                                    }}
                                    >藏品</div>}
                                    selected={this.state.selectedTab === '0'}
                                    onPress={async () => {
                                        await this.setState({
                                            selectedTab: '0',
                                        });
                                        await this.getData();
                                    }}
                                >
                                </TabBar.Item>
                                <TabBar.Item
                                    title={null}
                                    key="1"
                                    icon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#AAAAAA'
                                    }}
                                    >喜欢</div>
                                    }
                                    selectedIcon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#FFFFFF',
                                        borderRadius: '5px',
                                        paddingTop: '5px',
                                        paddingBottom: '5px',
                                        backgroundColor: '#FE0341',
                                    }}
                                    >喜欢</div>}
                                    selected={this.state.selectedTab === '1'}
                                    onPress={async () => {
                                        await this.setState({
                                            selectedTab: '1',
                                        });
                                        await this.getData();
                                    }}
                                >
                                </TabBar.Item>
                            </TabBar>
                        </div>
                        <div style={{
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                            height: '100%',
                            backgroundColor: '#000'
                        }}>
                            <TabBar
                                style={{ width: '100%' }}
                                unselectedTintColor="#949494"
                                tintColor="#33A3F4"
                                barTintColor="#000"
                                hidden={false}
                                tabBarPosition="top"
                            >
                                <TabBar.Item
                                    title={null}
                                    key="0"
                                    icon={<div style={{
                                        width: '60px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '14px',
                                        color: '#AAAAAA'
                                    }}
                                    >已下载</div>
                                    }
                                    selectedIcon={<div style={{
                                        width: '60px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#FFFFFF',
                                        borderRadius: '5px',
                                        paddingTop: '5px',
                                        paddingBottom: '5px',
                                        backgroundColor: '#FE0341',
                                    }}
                                    >已下载</div>}
                                    selected={this.state.selectedTab === '0'}
                                    onPress={async () => {
                                        await this.setState({
                                            selectedTab: '0',
                                        });
                                        await this.getData();
                                    }}
                                >
                                </TabBar.Item>
                                <TabBar.Item
                                    title={null}
                                    key="1"
                                    icon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#AAAAAA'
                                    }}
                                    >喜欢</div>
                                    }
                                    selectedIcon={<div style={{
                                        width: '40px',
                                        marginLeft: '10px',
                                        marginRight: '10px',
                                        fontSize: '12px',
                                        color: '#FFFFFF',
                                        borderRadius: '5px',
                                        paddingTop: '5px',
                                        paddingBottom: '5px',
                                        backgroundColor: '#FE0341',
                                    }}
                                    >喜欢</div>}
                                    selected={this.state.selectedTab === '1'}
                                    onPress={async () => {
                                        await this.setState({
                                            selectedTab: '1',
                                        });
                                        await this.getData();
                                    }}
                                >
                                </TabBar.Item>
                            </TabBar>
                        </div>
                    </Tabs>
                </Flex>
                {/* <Grid data={this.state.data}
                    columnNum={2}
                    itemStyle={{background:'black',textAlign:'center'}}
                    renderItem={dataItem => (
                        <div style={{ padding: '12.5px' }}
                            onClick={() => this.props.history.push(`detail/${dataItem.id}`)}>
                            <img src={dataItem.imageUrl} style={{ width: '75px', height: '75px' }}
                                alt="" />
                            <div style={{ color: '#888', fontSize: '14px', marginTop: '12px' }}>
                                <span>{dataItem.title}</span>
                            </div>
                        </div>
                    )}
                /> */}
                <div className='meListViewChange'>
                    <ListView
                        dataSource={this.state.dataSource}
                        ref={el => this.lv = el}
                        pageSize={10}
                        onEndReachedThreshold={10}
                        pageSize={this.state.limit}
                        scrollRenderAheadDistance={500}
                        onEndReached={() => {
                            console.log("nextpage")
                        }}
                        onEndReachedThreshold={10}
                        // horizontal={true}
                        // 这些样式会应用到一个内层的内容容器上，所有的子视图都会包裹在内容容器内
                        contentContainerStyle={{
                            position: "relative",
                            // display:'flex',
                            // flex: 'row',
                            // flexWrap: 'wrap',
                            // flexDirection:'row',
                        }}
                        renderBodyComponent={
                            () => <WingBlank size='sm'>
                                <Flex style={{ width: '100%' }}>
                                </Flex>
                            </WingBlank>

                        }
                        renderFooter={() => <div style={{ padding: 30, textAlign: 'center' }}>
                            {this.state.isLoading ? 'Loading...' : 'Loaded'}
                        </div>}
                        renderRow={(rowData, sectionID, rowID) =>
                            <div style={{ width: '50%', padding: 2, minHeight: 200 }}
                                onClick={() => {
                                    if (this.state.selectItem === 0) {
                                        this.props.history.push(`/home?startFromId=${rowData.id}&startFromType=5`)
                                    } else if (this.state.selectItem === 1){
                                        this.props.history.push(`detail/${rowData.id}`)
                                    }
                                }}>
                                <div style={{
                                    width: '100%',
                                    // alignItems: 'center',
                                    // justifyContent: 'center',
                                    textAlign: 'center',
                                }}
                                >
                                    <img src={rowData.imageUrl} style={{
                                        width: '160px',
                                        minHeight: '160px',
                                        objectFit: 'contain',
                                        borderTopLeftRadius: 7,
                                        borderTopRightRadius: 7,
                                        marginTop: 10
                                    }}
                                        alt="" />
                                    <div style={{
                                        color: '#888',
                                        fontSize: '14px',
                                        marginTop: '8px',
                                        height: 20,
                                        textAlign: 'center',
                                        color: 'white',
                                        overflow: 'hidden',
                                        whiteSpace: 'nowrap',
                                        textOverflow: 'ellipsis',
                                        width: '100%'
                                    }}>
                                        <span>{rowData.title}</span>
                                    </div>
                                </div>
                            </div>}

                    />
                </div>


                <Modal
                    // 强制答题对话框
                    // visible={false}
                    visible={this.state.showAnswerModal}
                    style={{ height: 150, width: 230, }}
                    className='answerModalChange'
                >
                    <div style={{ fontSize: 15, padding: '10px 0', fontWeight: 600 }}>请参与会员答题</div>
                    <WingBlank size='md'>
                        <div style={{ fontSize: 12 }}>该账号尚未完成会员答题，为了拥有更好的社区体验，请前往完成会员答题。</div>
                    </WingBlank>
                    <WhiteSpace size='xl' />
                    <Flex justify='center'>
                        <Button className='answerBtn' onClick={() => {
                            this.props.history.push("/answer")
                        }}>前往答题</Button>
                    </Flex>
                </Modal>
            </div>
        )
    }
}

export default Me

import React from 'react'
import './style.css'
import Header from '../../components/Header/index'

import shareIcon from '../../assets/awaitShare.svg'
import likeIcon from '../../assets/topicDetailCollect.svg'
import redlikeIcon from '../../assets/redCollection.svg'

import DataPacketIcon from '../../assets/DataPacket.svg'
import {Button, Flex, Modal, Toast, WhiteSpace, WingBlank} from 'antd-mobile'
import {api} from "../../services/api/ApiProvider";

const alert = Modal.alert;

export default class SourceMaterial extends React.Component {
    state = {
        name: '素材编号001',
        image: 'https://picsum.photos/192/100',
        fileSize: '132mb',
        colorMode: 'RGB',
        canvasSize: '2000*2000',
        resolutionRatio: '72',
        classification: ['2D', '水印', '真彩', '大屏幕', '可商用', '可商用可商用可商用'],
        certification: 'https://picsum.photos/90/130',

        id: 0,
        title: '',
        largePrice: 0,
        coverUrl: '',
        tags: [],
        contentUrl: '',
        description: '',
        signingInfo: '',
        extra: '',
        creatorUserGroupId: 0,
        downloadUrl: '',
        isExclusive: false,
        author: '',

        currUser: '',
        certificationModal: false,
        isLike: false,
    }

    async componentDidMount(): void {
        const commodityId = this.props.location.pathname.split('/').pop();
        let res = await api.commodityService.getCommodity(commodityId);
        this.setState({
            ...res
        })
        const userBaseRes = await api.accountService.getUserBase();
        if (userBaseRes.phone) {
            this.setState({
                currUser: userBaseRes.phone
            })
        }
        this.updateLike();
    }

    purchase = async () => {
        let that = this;
        try {
            await api.orderService.isOrderExist(this.state.id);
            this.download(that.state.contentUrl);
        } catch (e) {
            if (e.toString().indexOf('404') >= 0 && this.state.author && this.state.currUser !== this.state.author) {
                alert('确定要购买吗？', '下单后将自动扣除积分', [
                    {text: '取消', onPress: () => console.log('cancel'), style: 'default'},
                    {
                        text: '确定', onPress: async () => {
                            const tccRes = await api.tccService.getTCC("dimension.sale.divide");
                            let saleDivideList = eval(tccRes.tccTuple.value)
                            if (!saleDivideList || saleDivideList.length < 2) {
                                saleDivideList = [0.4, 0.3];
                            }
                            const divide = that.state.isExclusive ? saleDivideList[1] : saleDivideList[0];
                            let author = that.state.author ? that.state.author : that.state.usernameList[0];
                            let coinsToUser = Math.ceil(that.state.largePrice * (1 - divide));
                            let coinsToSystem = that.state.largePrice - coinsToUser;
                            try {
                                let accountRes = await api.accountService.getUserAccount();
                                if ((accountRes.largeCoins + accountRes.disableWithDrawCoins) >= that.state.largePrice) {
                                    await api.accountService.transferCoins(coinsToUser, author);
                                    await api.accountService.consumeDiscount(coinsToSystem);
                                    await api.orderService.updateOrder({
                                        id: 0,
                                        largePrice: that.state.largePrice,
                                        avatarUrl: that.state.coverUrl,
                                        userGroupTitle: that.state.title,
                                        pageUrl: '',
                                        status: "已完成",
                                        totalNum: 1,
                                        completeNum: 1,
                                        orderType: 'pic',
                                        relationId: that.state.id
                                    })

                                    that.download(that.state.contentUrl);
                                } else {
                                    alert('余额不足？', '点击确认前往充值', [
                                        {text: '取消', onPress: () => console.log('cancel'), style: 'default'},
                                        {
                                            text: '确定',
                                            onPress: () => that.props.history.push('/me/coin'),
                                            style: 'default'
                                        }
                                    ]);
                                }
                            } catch (e) {
                                Toast.fail("购买失败，请重试");
                            }
                        }
                    },
                ]);
            } else {
                this.download(that.state.contentUrl);
            }
        }
    }

    download = (url) => {
        window.open(url);
    }

    postLike = async () => {
        await api.relationService.postLike(this.state.id, 0);
        await this.updateLike();
    }

    updateLike = async () => {
        const res = await api.relationService.getLike(this.state.id, 0);
        this.setState({
            isLike: res.isLike
        })
    }

    render() {
        const state = this.state
        return (
            <div className='SourceMaterialPage'>
                <Header title='素材详情'>
                    <img alt='' style={{width: 20, height: 20}} src={shareIcon}/>
                </Header>

                <WingBlank size='lg'>
                    <div style={{padding: '15px 0px', minHeight: 405, color: 'white', fontWeight: 600, fontSize: 15}}>
                        <div style={{backgroundColor: 'black', padding: '10px 10px', borderRadius: 5}}>
                            <Flex justify='between'>
                                <div>{state.title}</div>
                                <img alt='' style={{width: 25, height: 25}} src={state.isLike ? redlikeIcon : likeIcon}
                                     onClick={() => this.postLike()}/>
                            </Flex>
                            <Flex justify='center'>
                                <div style={{padding: 30}}>
                                    <img alt='' style={{width: 250, height: 250, objectFit: 'cover'}}
                                         src={state.coverUrl}/>
                                </div>
                            </Flex>
                            <Flex>
                                <div style={{padding: '10px 0'}}>素材详情</div>
                            </Flex>
                            <Flex justify='start' style={{fontSize: 14, color: '#AAAAAA'}} align='start'
                                  className='detailInfo'>
                                <div>{state.description.length > 0 ? state.description : '暂无描述'}</div>
                            </Flex>
                        </div>
                    </div>
                </WingBlank>

                {/* 所属分类 */}
                <div className='banner'>
                    <Flex justify='start' align='center' wrap='wrap'>
                        <div style={{padding: '0 15px', fontWeight: 600, fontSize: 15}}>所属分类</div>
                        <Flex wrap='wrap' style={{marginTop: -10}}>
                            {
                                state.tags.map((item, index) =>
                                    <div className='classification' key={index}>{item}</div>
                                )
                            }
                        </Flex>
                    </Flex>
                </div>

                <WhiteSpace size='lg'/>

                {/* 存证证书 */}
                {/*<div className='banner'>*/}
                {/*    <Flex justify='start' align='start' wrap='wrap'>*/}
                {/*        <div style={{ padding: '0 15px', fontWeight: 600, fontSize: 15 }}>存证证书</div>*/}
                {/*        <Flex wrap='wrap'>*/}
                {/*            <img alt='' style={{ width: 90, height: 130, objectFit: 'contain' }} src={state.certification} onClick={() => this.setState({ certificationModal: true })} />*/}
                {/*        </Flex>*/}
                {/*    </Flex>*/}
                {/*</div>*/}

                <WhiteSpace size='lg'/>

                {/* 资源包 */}
                <div className='banner'>
                    <Flex justify='start' align='start' wrap='wrap'>
                        <div style={{padding: '0 15px', fontWeight: 600, fontSize: 15}}>资源包</div>
                        <Flex wrap='wrap'>
                            <div style={{
                                width: 135,
                                height: 70,
                                borderRadius: 5,
                                backgroundColor: '#333333',
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center'
                            }}>
                                <img alt='' style={{width: 30, height: 30, objectFit: 'contain'}} src={DataPacketIcon}/>
                            </div>
                        </Flex>
                    </Flex>
                </div>

                {/* 购买功能栏（浮动） */}
                <div className='banner' style={{position: 'fixed', bottom: 50, width: '100%'}}>
                    <Flex justify='between' align='start' wrap='wrap'
                          style={{padding: '0 15px', fontWeight: 600, fontSize: 15}}>
                        <div style={{display: 'flex'}}>
                            <div>价格：</div>
                            <div style={{
                                color: '#fe2341',
                                fontSize: 20
                            }}>{`${(state.largePrice / 100).toLocaleString()}电子`}</div>
                        </div>
                        <div className='buyBtn' onClick={() => this.purchase()}>
                            <Button>购买素材</Button>
                        </div>
                    </Flex>

                </div>

                {/* 查看大图 */}
                <Modal
                    visible={state.certificationModal}
                    closable={true}
                    onClose={() => {
                        this.setState({certificationModal: false})
                    }}
                    maskClosable={false}
                    style={{width: '90%', height: 'auto'}}
                    transparent
                    className='watchCertificationModal'
                >
                    <div>
                        <img alt='' style={{width: '100%', height: '100%', marginTop: 50, objectFit: "cover"}}
                             src='https://picsum.photos/900/1300'/>
                    </div>
                </Modal>
            </div>
        )
    }
}

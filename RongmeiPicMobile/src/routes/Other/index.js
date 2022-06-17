import {Card, Flex, Button, Tabs, ListView, WingBlank, PullToRefresh, Toast, Modal} from 'antd-mobile'
import React from 'react'
import './style.css'
// import Header from '../../components/Header/index'
import leftIcon from '../../assets/otherLeft.svg'
import moreIcon from '../../assets/otherMore.svg'
import creatorIcon from '../../assets/creator.svg'
import memberIcon from '../../assets/member.svg'
import pleaseUpdateIcon from '../../assets/pleaseUpdate.svg'
import likeIcon from '../../assets/topicDetailCollect.svg'
import completeIcon from '../../assets/complete.svg'
import logo from "../../assets/logo.png";
import {api} from "../../services/api/ApiProvider";


export default class Other extends React.Component {
    state = {
        username: '',
        avatarUrl: '',
        nickname: '',
        email: '',
        address: '',
        description: '',
        personWebsite: '',
        wechat: '',
        weibo: '',
        gender: '',

        // 认证   创作者1，会员0
        authentication: 1,
        isFollow: false,
        // 催更
        isUrge: false,
        follows: 0,
        fans: 0,

        dataSource: new ListView.DataSource({
            rowHasChanged: (r1, r2) => r1 !== r2
        })
    }

    async componentDidMount() {
        const username = this.props.location.pathname.split('/').pop();
        try {
            const userRes = await api.accountService.getUserEntity(username);
            const userInfoRes = await api.accountService.getUserInfoEntity(username);
            await this.setState({
                username,
                ...userRes,
                ...userInfoRes
            }, () => console.log(this.state))
        } catch (e) {
            console.log(e)
        }

        this.getUserRelation();
        this.getChild();
    }

    async getChild() {
        try {
            const userChildrenRes = await api.childService.getUserChildren(this.state.username, 1, 1000);
            let childItems = [];
            for (let i = 0; i < userChildrenRes.simpleChildItemList.length; i++) {
                childItems = childItems.concat({
                    id: userChildrenRes.simpleChildItemList[i].id,
                    title: userChildrenRes.simpleChildItemList[i].content,
                    like: 0,
                    imageUrl: userChildrenRes.simpleChildItemList[i].coverUrl
                })
            }
            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(childItems)
            }, () => console.log(this.state))
        } catch (e) {
            console.log(e)
        }
    }

    async getUserRelation() {
        try {
            const userRelationRes = await api.relationService.getUserRelation(this.state.username);
            this.setState({
                follows: userRelationRes.follows.length,
                fans: userRelationRes.fans.length
            })
            const userRelationLikeRes = await api.relationService.getUserRelationLike(this.state.username, 1);
            this.setState({
                isFollow: userRelationLikeRes.isLike
            })
        } catch (e) {
            console.log(e);
        }
    }

    async updateUserRelation(toUsername) {
        try {
            await api.relationService.updateUserRelation(toUsername, 1);
            this.getUserRelation();
            Toast.success("用户关系更新成功");
        } catch (e) {
            Toast.fail("用户关系更新失败");
        }
    }

    render() {
        const state = this.state
        return (
            <div className='otherPage'>
                <div className='headerBanner'>
                    <div style={{padding: '5px 10px', width: '100%', minHeight: 50}}>
                        <Flex justify='between'>
                            <div style={{padding: '5px 10px'}}>
                                <img alt='' style={{width: 35, height: 35}} src={leftIcon}
                                     onClick={() => this.props.history.goBack()}/>
                            </div>
                            <div style={{padding: '5px 10px', display: 'flex', alignItems: 'center'}}>
                                {
                                    state.isFollow &&
                                    (state.isUrge ?
                                            <img alt='' style={{width: 30, height: 30, marginRight: 15, opacity: 0.8}}
                                                 src={completeIcon}/>
                                            : <div style={{
                                                width: 100,
                                                height: 30,
                                                marginRight: 15,
                                                display: 'inline-block',
                                                borderRadius: 25,
                                                backgroundColor: 'rgba(0,0,0,0.45)',
                                                lineHeight: '35px'
                                            }}
                                                   onClick={() => this.setState({isUrge: true}, () => Toast.success(`该用户已收到您的提醒
                                            有更新会通知您`))}>
                                                <Flex justify='center' align='center' style={{
                                                    width: '90%',
                                                    paddingLeft: 10,
                                                    height: 30,
                                                    lineHeight: '30px'
                                                }}>
                                                    <img alt='' style={{width: 25, height: 25}} src={pleaseUpdateIcon}/>
                                                    <div style={{
                                                        color: 'white',
                                                        fontSize: 15,
                                                        fontWeight: 600,
                                                        padding: '0 5px'
                                                    }}>求更新
                                                    </div>
                                                </Flex>
                                            </div>
                                    )
                                }
                                <img alt='' style={{width: 35, height: 35}} src={moreIcon} onClick={
                                    () => Modal.alert(<div style={{fontSize: 20}}>{state.name}</div>, null, [
                                        {text: '分享', onPress: () => console.log('分享操作')},
                                        {text: '举报', onPress: () => console.log('举报操作')},
                                        {text: '拉黑', onPress: () => console.log('拉黑操作')},
                                        {text: '取消', onPress: () => console.log('取消操作')},
                                    ])
                                }/>
                            </div>
                        </Flex>
                    </div>

                    <Card full>
                        <Card.Header
                            thumb={
                                <div>
                                    <img alt=''
                                         src={state.avatarUrl.length > 0 ? state.avatarUrl : logo}
                                         style={{width: 65, height: 65, borderRadius: '50%', objectFit: 'cover'}}/>
                                </div>
                            }
                            //25的lineheight 是因为padding 10 总高度又为45
                            title={
                                <div style={{fontSize: 15, color: 'white', fontWeight: 600}}>
                                    <Flex justify='between' align='center' style={{height: '100%', lineHeight: '25px'}}>
                                        <div
                                            style={{padding: '10px 0px 10px 10px', fontSize: 16}}>{state.nickname}</div>
                                        <Flex justify='center' style={{paddingLeft: 10}}>
                                            <div className='followBtn'>
                                                <Button style={state.isFollow ? {backgroundColor: '#222222'} : {}}
                                                        onClick={() => this.updateUserRelation(this.state.username)}>{`${state.isFollow ? '取消关注' : '关注'}`}</Button>
                                            </div>
                                        </Flex>
                                    </Flex>

                                    <Flex>
                                        <div style={{
                                            width: 'auto',
                                            height: 20,
                                            backgroundColor: 'rgba(0,0,0,0.3)',
                                            borderRadius: 10,
                                            marginLeft: 10,
                                            padding: '5px 10px 5px 5px',
                                            lineHeight: '20px',
                                            display: 'flex',
                                            position: 'relative',
                                            alignItems: 'center'
                                        }}>
                                            <img alt='' src={state.authentication === 0 ? memberIcon : creatorIcon}
                                                 style={{
                                                     width: 16,
                                                     height: 16,
                                                     alignSelf: 'center',
                                                     lineHeight: '20px'
                                                 }}/>
                                            <div style={{
                                                fontSize: 13,
                                                marginLeft: -5,
                                                lineHeight: '20px',
                                                height: 20
                                            }}>{state.authentication === 0 ? '会员认证' : '创作者认证'}</div>
                                        </div>
                                    </Flex>

                                </div>
                            }
                        />
                        <Card.Body style={{color: 'white', fontWeight: 600}}>
                            <Flex justify='start'>
                                <div style={{padding: 10}}>{state.follows}<span style={{color: '#DDDDDD'}}> 关注</span>
                                </div>
                                <div style={{padding: 10}}>{state.fans}<span style={{color: '#DDDDDD'}}> 粉丝</span></div>
                            </Flex>
                            <Flex justify='start'>
                                <div style={{
                                    fontWeight: 600,
                                    color: 'white',
                                    fontSize: 12,
                                    lineHeight: '18px',
                                    minHeight: '40px',
                                    width: '100%'
                                }}>{`个人介绍：${state.description.length > 0 ? state.description : '对方什么也没有留下~'}`}</div>
                            </Flex>
                        </Card.Body>
                    </Card>
                </div>

                <Tabs
                    tabs={[{title: '作品区', sub: '1'}]}
                    tabBarPosition='top'
                    initialPage={1}
                    page='1'
                    tabBarBackgroundColor='black'
                    tabBarTextStyle={{fontSize: 18, fontWeight: 700, color: 'white',}}
                    tabBarUnderlineStyle={{border: '1px solid #AAA'}}
                >
                    <ListView
                        ref={el => this.lv = el}
                        style={{backgroundColor: 'transparent', height: 580}}
                        dataSource={state.dataSource}
                        // renderFooter={() =>
                        //     <PullToRefresh
                        //         direction='up'
                        //         refreshing
                        //         onRefresh={() => console.log('onrefresh')}
                        //     />
                        // }
                        renderSectionBodyWrapper={
                            () =>
                                <Flex justify='between' align='start' wrap='wrap' style={{
                                    width: '100%',
                                    display: 'flex',
                                    flexDirection: 'row',
                                    // flexWrap: 'wrap',
                                    padding: '5px 5px',
                                    backgroundColor: 'black'
                                }}>
                                </Flex>
                            // <div style={{ columnCount: 2, columnGap: 0, }}> </div>
                        }
                        onScroll={() => {
                            console.log("onscroll")
                        }}
                        onEndReached={() => console.log('onend')

                        }

                        pullToRefresh={
                            <PullToRefresh
                                direction='up'
                                onRefresh={() => console.log('onrefresh')}
                            />
                        }
                        renderRow={(dataItem) =>
                            <div style={{
                                width: '49%',
                                marginBottom: 10,
                                height: 200,
                                borderRadius: 7,
                                position: 'relative'
                            }} onClick={() => {
                                this.props.history.push(`/home?startFromId=${dataItem.id}&startFromType=5`)
                            }}>
                                <img alt='' src={dataItem.imageUrl}
                                     style={{height: 200, borderRadius: 7, width: '100%', objectFit: 'cover'}}/>
                                <div style={{
                                    position: 'absolute',
                                    top: 0,
                                    left: 0,
                                    width: '100%',
                                    height: '100%',
                                    backgroundColor: 'rgba(0,0,0,0.1)',
                                    borderRadius: 7
                                }}>
                                    <div style={{position: 'absolute', bottom: 10, left: 10}}>
                                        <Flex>
                                            {/*<img alt='' style={{width: 15, height: 15}} src={likeIcon}/>*/}
                                            <div style={{
                                                fontSize: 15,
                                                fontWeight: 600,
                                                color: 'white',
                                                paddingLeft: 5
                                            }}>{dataItem.title}</div>
                                        </Flex>
                                    </div>
                                </div>
                            </div>
                            // <div style={{ width: '100%', marginBottom: 10, minHeight: 200, maxHeight: 250, borderRadius: 7, padding: '0 5px' }}>
                            //     <img alt='' src={dataItem.imageUrl} style={{ minHeight: 200, maxHeight: 250, borderRadius: 7, width: '100%', objectFit: 'cover' }} />
                            // </div>
                        }
                    />
                </Tabs>
            </div>
        )
    }
}

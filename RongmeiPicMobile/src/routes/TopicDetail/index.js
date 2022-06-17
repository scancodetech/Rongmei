import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { SearchBar, Flex, ListView, ActionSheet, WhiteSpace, Card, Button, Toast } from 'antd-mobile'

import ShareIcon from '../../assets/awaitShare.svg'
import CollectIcon from '../../assets/topicDetailCollect.svg'
import { api } from "../../services/api/ApiProvider";

import link from "../../assets/link.png";
import warn from "../../assets/warn.png";

import { copyToClip, } from "../../utils/utils";



export default class TopicDetail extends React.Component {
    state = {
        searchValue: '',
        titleImg: 'https://picsum.photos/200/300',
        quote: '10000',
        id: 0,
        topic: '',
        coverUrl: '',
        description: '',
        collection: false,
        dataSource: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 }),
        linkList: [],
    }

    async componentDidMount() {
        const topic = this.props.location.pathname.split('/').pop();
        this.setState({
            topic
        })
        const topicDetailRes = await api.childService.getTopicDetail(topic);
        this.setState({
            ...topicDetailRes
        }, () => console.log(this.state))
        const topicChildrenRes = await api.childService.getTopicChildern(topic, 1, 10000);

        let newdata = this.state.dataSource.cloneWithRows(topicChildrenRes.simpleChildItemList)
        this.setState({
            dataSource: newdata
        }, () => {
            console.log(this.state.dataSource)
        })

        const getshareRes = await api.metricsService.getShare(this.state.id)
        console.log(`getshareRes`, getshareRes)
        console.log(this.state.description)
        let url = this.state.description
        let checkUrl = new RegExp('https://[^\s]*', 'g')
        console.log(url.match(checkUrl))
        this.setState({
            linkList: url.match(checkUrl) || [],
        })
    }

    renderRow(rowData, sID, rID) {
        return (
            <div className={(rID + 1) % 3 !== 0 ? 'rowItemleft' : ''} style={{
                width: '32%',
                textAlign: 'start',
                backgroundColor: 'inherit',
                height: '170px',
                marginBottom: 10
            }}
                onClick={() => {
                    this.props.history.push(`/home?startFromId=${rowData.id}&startFromType=5`)
                }}
            >
                <Flex justify='center' direction='column' align='center'>
                    <img alt='' src={rowData.coverUrl} style={{ width: '100%', height: 140, objectFit: 'cover' }} />
                </Flex>
                <div style={{
                    width: 100,
                    height: 30,
                    fontSize: 12,
                    padding: '0 10px',
                    textAlign: 'center',
                    lineHeight: '30px',
                    color: 'white',
                    overflow: 'hidden',
                    whiteSpace: 'nowrap',
                    textOverflow: 'ellipsis',
                }}>{rowData.content}</div>
            </div>
        )
    }


    actionList = [
        {
            icon: <img src={link} alt={"复制链接"} style={{ width: 36 }} />,
            title: "复制链接",
            action: async () => {
                copyToClip("https://m.dimension.pub/#/picmobile/topicdetail/" + this.state.topic);
                Toast.success("链接已复制");
                await api.metricsService.addShare(this.state.id, "");
            }
        },
        {
            icon: <img src={warn} alt={"举报"} style={{ width: 36 }} />,
            title: "举报",
            action: () => {
                if (this.state.id !== '') {
                    this.props.history.push(`/report/${this.state.id}`);
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
        const state = this.state;
        return (
            <div className='topicDetailPage'>
                <Header title={
                    <div style={{
                        width: '260px',
                        position: 'relative',
                        left: '50%',
                        transform: 'translateX(-50%)'
                    }}
                        className='searchBarChange'>
                        <SearchBar
                            placeholder='搜索'
                            onSubmit={async (value) => {
                                await this.props.history.push(`/topicDetail/${value}`)
                                this.componentDidMount();
                            }}
                        />
                    </div>
                }
                >
                    <div>
                        <img alt='' style={{ width: 20, height: 20 }} src={ShareIcon} onClick={this.postShare} onClick={this.openShareModalVisible} />
                    </div>
                </Header>

                <div>
                    <Card full>
                        <Card.Header
                            thumb={
                                <div>
                                    <img alt='' style={{ height: 125, width: 115, objectFit: 'cover' }}
                                        src={state.coverUrl.length > 0 ? state.coverUrl : 'https://picsum.photos/200/300'} />
                                </div>
                            }
                            title={
                                <Flex direction='column' align='start' justify='start'
                                    style={{ padding: '15px 15px 0 15px', height: 125 }}>
                                    <div style={{
                                        fontWeight: 600,
                                        padding: '10px 0',
                                        color: 'white'
                                    }}>#{state.topic}#
                                    </div>
                                    {/*<div style={{fontSize: 12, color: '#666666'}}>{`${state.quote}次引用`}</div>*/}
                                    {/*<div style={{height: '100%', display: 'flex'}}>*/}
                                    {/*    <div className={state.collection === true ? 'collection-active' : 'collection'}*/}
                                    {/*         onClick={() => {*/}
                                    {/*             console.log('点击收藏事件')*/}
                                    {/*         }}>*/}
                                    {/*        <Flex align='center' justify='around' style={{width: '80%'}}>*/}
                                    {/*            <img alt='' style={{width: 20, height: 20}} src={CollectIcon}/>*/}
                                    {/*            <div style={{fontSize: 13, color: 'white', fontWeight: 600}}>收藏</div>*/}

                                    {/*        </Flex>*/}
                                    {/*    </div>*/}
                                    {/*</div>*/}
                                </Flex>
                            }
                        />
                        <Card.Body>
                            <Flex justify='start' direction='column' align='start'>
                                <div className='title'>
                                    <div>简介</div>
                                </div>
                                <div className='introText'>
                                    {state.description.length > 0 ? state.description : '暂无简介'}
                                </div>
                                {
                                    this.state.linkList.map((item, index) =>
                                        <div style={{ width: '100%', color: '#FFFF00', padding: '5px 0', wordBreak: 'break-all' }} onClick={() => window.open(item)}>{item}</div>
                                    )
                                }
                                <div></div>
                                <WhiteSpace size='lg' />
                                <div className='title'>
                                    <div>参与</div>
                                </div>
                            </Flex>

                            <div className='itemContainer'>
                                <ListView
                                    dataSource={this.state.dataSource}
                                    renderSectionBodyWrapper={
                                        () => <Flex justify='start' align='center' style={{
                                            width: '100%',
                                            display: 'flex',
                                            flexDirection: 'row',
                                            flexWrap: 'wrap',
                                        }}>
                                        </Flex>
                                    }
                                    onScroll={() => {
                                        console.log("onscroll")
                                    }}
                                    onEndReached={() => {
                                        console.log("onend")
                                    }}
                                    style={{ width: '90vw', height: 'calc(100VH - 100px)' }}
                                    renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                                        {this.state.isLoading ? 'Loading...' : 'Loaded'}
                                    </div>)}
                                    renderRow={this.renderRow}
                                />
                            </div>
                        </Card.Body>
                        {/* TODO fix ui*/}
                        {/*<Card.Footer*/}
                        {/*    content={*/}
                        {/*        <Flex justify='center' align='center'>*/}
                        {/*            <div className='BtnChange'>*/}
                        {/*                <Button>参与话题</Button>*/}
                        {/*            </div>*/}
                        {/*        </Flex>*/}
                        {/*    }*/}
                        {/*/> */}
                    </Card>
                </div>
            </div>
        )
    }
}

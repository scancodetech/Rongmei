import React from 'react';
import { withRouter } from "react-router-dom";
import './style.css';
import Header from '../../../components/Header/index';
import { Card, Flex, ListView, WingBlank } from 'antd-mobile';

export default class development extends React.Component {
    state = {
        dataList: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        isLoading: false,


    }

    componentDidMount() {
        let newdata = this.state.dataList.cloneWithRows([
            {
                name: 'Q版小可爱',
                price: '10000',
                date: '2021-04-07 15:02:00',
                imgUrl: 'https://picsum.photos/200/300',
                auther: 'me',
                type: '大头',
                introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
            },
            {
                name: 'Q版小可爱',
                price: '10000',
                date: '2021-04-07 15:02:00',
                imgUrl: 'https://picsum.photos/200/300',
                auther: 'me',
                type: '大头',
                introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
            },
            {
                name: 'Q版小可爱',
                price: '10000',
                date: '2021-04-07 15:02:00',
                imgUrl: 'https://picsum.photos/200/300',
                auther: 'me',
                type: '大头',
                introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
            },

        ])
        this.setState({
            dataList: newdata
        })
    }

    onEndReached = () => {
        console.log("onend")
        this.setState({
            isLoading: true
        })
        let newdata = this.state.dataList.cloneWithRows([{
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        }, {
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        }, {
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        }, {
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        }, {
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        }, {
            name: 'Q版小可爱',
            price: '10000',
            date: '2021-04-07 15:02:00',
            imgUrl: 'https://picsum.photos/200/300',
            auther: 'me',
            type: '大头',
            introduction: '巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉巴拉'
        },])
        this.setState({
            dataList: newdata,
            isLoading: false
        })
    }
    render() {
        return (
            <div className='developmentPage'>
                <Header title='养成史' />

                {/* <ul> */}
                <ListView
                    dataSource={this.state.dataList}
                    // useBodyScroll
                    renderSectionBodyWrapper={() =>
                        <ul>

                        </ul>
                    }
                    renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                        {this.state.isLoading ? 'Loading...' : 'Loaded'}
                    </div>)}
                    initialListSize={3}
                    onScroll={() => { console.log('scroll'); }}
                    // onEndReachedThreshold={100}
                    onEndReached={this.onEndReached}
                    style={{ height: 'calc(100VH - 110px)' }}
                    renderRow={(dataItem) => (
                        <li>
                            <Card full style={{ marginLeft: 30, fontWeight: 600 }}>
                                <Card.Header
                                    title={
                                        <Flex justify='center' align='center' style={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap', width: 200, textAlign: 'start' }}>
                                            <div style={{ fontSize: 13, width: 120, textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap', }}>{dataItem.name}</div>
                                            <div style={{ color: '#fe2341', width: 80, textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap', }}>价格:{dataItem.price}电子</div>
                                        </Flex>
                                    }
                                    extra={
                                        <div style={{ fontSize: 12 }}>{dataItem.date}</div>
                                    }
                                    style={{ fontSize: 12, textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap', }}
                                />
                                <Card.Body>
                                    <Flex justify='start' align='start' style={{ fontSize: 12, fontWeight: 600, color: 'white' }}>
                                        <div style={{ padding: '0 5px' }}>
                                            <img style={{ width: 150, height: 160, objectFit: 'contain' }} src={dataItem.imgUrl} />
                                        </div>
                                        <Flex direction='column' justify='start' align='start' className='textDivChange'>
                                            <div style={{ fontSize: 15 }}>详情：</div>
                                            <div>创作者：{dataItem.auther}</div>
                                            <div>类型：{dataItem.type}</div>
                                            <Flex direction='column' justify='start' align='start'>
                                                <div style={{ width: 50 }}>简介：</div>
                                                <div style={{ wordBreak: 'break-all', textOverflow: 'ellipsis', overflow: 'scroll', height: 90 }}>{dataItem.introduction}</div>
                                            </Flex>
                                        </Flex>
                                    </Flex>
                                </Card.Body>
                            </Card>
                        </li>
                    )}
                />
                {/* </ul> */}
            </div>
        )
    }
}
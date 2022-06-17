import React from 'react'
import './style.css'
import { Button, Flex, List, ListView, Toast, View } from 'antd-mobile'
import bodyCodeIcon from '../../../assets/mineShareCodeBodyCode.png'
import Item from 'antd-mobile/lib/popover/Item'

// import Header from '../../../components/Header/index'

export default class InShare extends React.Component {
    state = {
        shareCode: 'F3JF',
        shareNumber: 2,
        shareUrl: 'https://m.dimension.pub/#/picmobile/sale/446',
        shareUserName: 'BinBin泡饭',

        dataSource: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 })
    }
    componentDidMount() {
        let newdata = this.state.dataSource.cloneWithRows([
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },
            { url: 'https://picsum.photos/200/300', name: '图例1' },

        ])
        this.setState({
            dataSource: newdata
        }, () => { console.log(this.state.dataSource) })
    }


    renderRow(rowData, sID, rID) {
        return (
            <div className={(rID + 1) % 3 !== 0 ? 'rowItemleft' : ''} style={{ width: '32%', height: 50, textAlign: 'start', backgroundColor: 'inherit', height: '170px', marginBottom: 10 }}>
                <Flex justify='center' direction='column' align='center'>
                    <img alt='' src={rowData.url} style={{ width: '100%', height: 140, objectFit: 'cover' }} />
                </Flex>
                <div style={{ padding: '0 10px', alignSelf: 'center', lineHeight: '30px', color: 'white' }}>{rowData.name}</div>
            </div>
        )
    }
    render() {
        return (
            <div className='inSharePage'>
                {/* <Header title=''/> */}
                <div className='Banner'>
                    <Flex direction='column' justify='center' style={{ color: 'white', fontWeight: 600, fontSize: 15 }}>
                        <div style={{ color: '#E75168', fontSize: 28, fontWeight: 600, paddingTop: 30 }}>跨次元</div>
                        <div style={{ padding: '10px 0' }}>邀请你加入我们   快来~</div>
                        <div>我在这里等你</div>
                        <div className='body'>
                            <Flex justify='center' direction='column'>
                                <img src={bodyCodeIcon} style={{ height: 40, width: 270, }} />
                                <div style={{ color: '#999999', fontSize: 12, transform: 'scale(0.9)', fontWeight: 600 }}>{this.state.shareUrl}</div>
                                {/* <div style={{ fontSize: 20, color: 'black', padding: '20px 0 10px 0' }}>你的邀请码</div> */}
                                <img alt='' style={{ width: 40, height: 40, borderRadius: '50%', marginTop: 10 }} src='https://picsum.photos/200/300' />
                                <div style={{ color: '#666666', padding: '10px 0' }}>{this.state.shareUserName}</div>
                                <div style={{ color: 'black', fontSize: 15 }}>使用我的邀请码注册跨次元</div>
                                <Flex justify='between' align='center' style={{ paddingTop: 40 }}>
                                    <div className='shareCodeBorder'>{this.state.shareCode[0]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[1]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[2]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[3]}</div>
                                </Flex>

                                {/* <div style={{ color: 'black', paddingTop: 30 }}>您可邀请3名好友</div> */}
                                <div style={{ color: '#EEEEEE', fontSize: 45, position: 'relative', bottom: 25, opacity: 0.8 }}>DIMENSION</div>

                                {/* <div className='Btn'>
                                    <Button onClick={() => this.copyUrl()}>立即邀请</Button>
                                </div> */}
                            </Flex>
                        </div>

                        <div className='bottom'>
                            <div className='title'>热门设定</div>

                            <div className='itemContainer'>
                                <ListView
                                    dataSource={this.state.dataSource}
                                    renderSectionBodyWrapper={
                                        () => <Flex justify='start' align='center' style={{ width: '100%', display: 'flex', flexDirection: 'row', flexWrap: 'wrap', }} >
                                        </Flex>
                                    }
                                    onScroll={() => { console.log("onscroll") }}
                                    onEndReached={() => { console.log("onend") }}
                                    style={{ width: '90vw', height: 'calc(100VH - 115px)' }}
                                    renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                                        {this.state.isLoading ? 'Loading...' : 'Loaded'}
                                    </div>)}
                                    renderRow={this.renderRow}
                                />
                            </div>
                        </div>
                    </Flex>
                </div>
            </div>
        )
    }
}
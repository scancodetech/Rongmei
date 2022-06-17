import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { Flex, ListView, WingBlank, } from 'antd-mobile'

import hotIcon from '../../assets/hot.svg'
import totalIcon from '../../assets/total.svg'
import { calculateWidth } from '../../utils/utils'

export default class CHH extends React.Component {
    state = {

        recommendData: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 }),
        totalData: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 }),
    }

    componentDidMount() {
        let newdata = this.state.recommendData.cloneWithRows([
            { name: 1 }, { name: 1 }, { name: 1 }, { name: 1 }, { name: 1 },
        ])
        this.setState({
            recommendData: newdata
        }, () => {
            console.log(this.state.recommendData)
        })
    }
    render() {
        return (
            <div className='CHHPage'>
                <Header title='抽盒盒' />

                <div>
                    <Flex justify='center' align='start' direction='column'>
                        <WingBlank size='lg'>
                            {/* 新品推荐 */}
                            <div>
                                <div style={{ paddingTop: 10, paddingBottom: 5 }}>
                                    <Flex justify='start' align='center' style={{ height: 20, }}>
                                        <div>
                                            <img style={{ width: 15, height: 15, textAlign: 'center', alignSelf: 'center' }} src={hotIcon} />
                                        </div>
                                        <div style={{ padding: '0 5px', fontWeight: 600 }}>新品推荐</div>
                                    </Flex>
                                </div>

                                <ListView
                                    dataSource={this.state.recommendData}
                                    horizontal={true}
                                    initialListSize={4}
                                    style={{ height: 140 }}
                                    renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                                        {this.state.isLoading ? 'Loading...' : 'Loaded'}
                                    </div>)}
                                    renderBodyComponent={
                                        () => <div />
                                    }
                                    renderSectionBodyWrapper={
                                        ()=><div className='recommend'/>
                                    }
                                    // renderSectionWrapper={
                                    //     () => <div className='recommend' />
                                    // }
                                    // useBodyScroll={true}
                                    onEndReached={() => { console.log("onend") }}
                                    onScroll={(e) => { console.log("onscroll"); }}
                                    onEndReachedThreshold={10}
                                    scrollEventThrottle={50}
                                    renderRow={(dataItem) =>
                                        <div className='recommendItem'>
                                            <Flex justify='center' align='center' direction='column'>
                                                <img style={{ height: 105, width: 105, borderRadius: 5, marginTop: 8, objectFit: 'cover', margin: '8px 10px 0 10px' }} alt='' src='https://picsum.photos/300/400' />
                                                <div style={{ fontSize: 12, fontWeight: 600, padding: '4px 0' }}>生肖系列</div>
                                                <div className='itemName'>¥1234</div>
                                            </Flex>
                                        </div>
                                    }
                                />

                                {/* <div className='recommend'>
                                    <div className='recommendItem'>
                                        <Flex justify='center' align='center' direction='column'>
                                            <img style={{ height: 105, width: 105, borderRadius: 5, marginTop: 8, objectFit: 'cover', margin: '8px 10px 0 10px' }} alt='' src='https://picsum.photos/300/400' />
                                            <div style={{ fontSize: 12, fontWeight: 600, padding: '4px 0' }}>生肖系列</div>
                                            <div className='itemName'>¥1234</div>
                                        </Flex>
                                    </div>
                                </div> */}
                            </div>
                            {/* 新品推荐结束 */}


                            {/* 全部盒蛋 */}
                            <div>
                                <div style={{ paddingTop: 10, paddingBottom: 5 }}>
                                    <Flex justify='start' align='center' style={{ height: 20, }}>
                                        <div>
                                            <img style={{ width: 15, height: 15, textAlign: 'center', alignSelf: 'center' }} src={totalIcon} />
                                        </div>
                                        <div style={{ padding: '0 5px', fontWeight: 600 }}>全部盒蛋</div>
                                    </Flex>
                                </div>

                                <div >
                                    <div className='total'>
                                        <div className='totalItem'>
                                            <Flex justify='center' align='center' direction='column'>
                                                <div>
                                                    <img style={{ width: '100%', minHeight: 345, maxHeight: 615, borderRadius: '7px 7px 0 0', objectFit: 'contain' }} className='imgStyle' alt='' src='https://picsum.photos/800/800' />
                                                </div>
                                                <div style={{ padding: '0 20px 0 15px', width: '100%', alignSelf: 'center' }}>
                                                    <Flex justify='between' align='center'>
                                                        <div style={{ padding: '5px 0', fontWeight: 600 }}>动物系列——辰龙</div>
                                                        <div style={{ padding: '5px 0', fontWeight: 600, color: '#FE2341' }}>¥999</div>
                                                    </Flex>
                                                </div>
                                            </Flex>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            {/* 全部盒蛋结束 */}
                        </WingBlank>
                    </Flex>
                </div>
            </div>
        )
    }
}
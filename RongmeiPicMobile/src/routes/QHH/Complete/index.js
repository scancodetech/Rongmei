import React from 'react'
import './style.css'
import { Flex, WhiteSpace, WingBlank, Button, Modal, ListView } from 'antd-mobile'
import { formatDate, getDateDiff } from "../../../utils/utils";
import { api } from "../../../services/api/ApiProvider";

class Complete extends React.Component {
    boxOrderService = api.boxOrderService;

    state = {
        completeData: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        limit: 10,
        page: 0,
        loading: true
    }

    componentDidMount() {
        this.getNextPage();
        // 前端展示效果时使用，对接接口时请删除

    }

    async getNextPage() {
        this.setState({
            loading: true
        })
        const res = await this.boxOrderService.queryBoxOrder(3, this.state.page + 1, this.state.limit);
        const newData = this.state.completeData.cloneWithRows(res.boxOrderItemList);
        this.setState({
            completeData: newData,
            page: this.state.page + 1
        })
        this.setState({
            loading: false
        })
    }

    render() {
        return (
            <div className='completePage'>
                <WingBlank size='lg'>
                    <ListView
                        style={{ minHeight: window.innerHeight - 175 }}
                        ref={el => this.lv = el}
                        dataSource={this.state.completeData}
                        renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                            {this.state.isLoading ? 'Loading...' : null}
                        </div>)}
                        pageSize={this.state.limit}
                        scrollRenderAheadDistance={500}
                        onEndReached={() => this.getNextPage()}
                        onEndReachedThreshold={10}
                        renderRow={(rowData, sectionID, rowID) =>
                            <div className='orderBanner'>
                                <div className='orderBanner'>
                                    <Flex direction='column' align='start' style={{ padding: '10px 15px' }}>
                                        <Flex align='center' justify='between'
                                            style={{ color: '#FF4C00', width: '100%' }}>
                                            <Flex>
                                                <div className='circle' />
                                                <div style={{
                                                    fontSize: 13,
                                                    fontWeight: 600,
                                                    padding: '0 10px'
                                                }}>{rowData.price / 100}元定制单
                                                </div>
                                            </Flex>
                                            <Flex>
                                                <div
                                                    style={{ color: '#999999' }}>{`完结于：${formatDate(rowData.finishTime)}`}</div>
                                            </Flex>
                                            <Flex justify='end' align='center' style={{ width: 50 }}>
                                                <div style={{
                                                    fontStyle: 'italic',
                                                    fontSize: 17,
                                                    paddingRight: 5,
                                                    color: '#FFFF96'
                                                }}>{`#${rowData.id}`}</div>
                                            </Flex>
                                        </Flex>
                                        <WhiteSpace size='sm' />
                                        <Flex justify='start' style={{ width: '100%', }}>
                                            <div className='Time' />
                                            <div style={{ paddingLeft: 10 }}>已接单{getDateDiff(rowData.acceptTime)}</div>
                                        </Flex>
                                        <WhiteSpace size='md' />
                                        <Flex style={{ width: '100%' }} align='start'>
                                            <Flex.Item>
                                                <Flex direction='column' align='start' className='requirement'>
                                                    <div>定制类型：{rowData.customType}</div>
                                                    <div>定制风格：{rowData.customStyle}</div>
                                                    <div>定制主题：{rowData.customTheme}</div>
                                                    <div
                                                        style={{
                                                            width: '100%',
                                                            height: '100%'
                                                        }}>元素要求：{rowData.elementRequirements}</div>
                                                </Flex>
                                            </Flex.Item>
                                            <Flex.Item>
                                                <Flex direction='column' align='start' className='requirement'>
                                                    <div>参考例图：</div>
                                                    <img alt='' className='imgClass' onClick={() => {
                                                        this.setState({ imgURL: rowData.exampleUrl, showModal: true })
                                                    }} src={rowData.exampleUrl} />
                                                </Flex>
                                            </Flex.Item>
                                        </Flex>
                                        <WhiteSpace size='lg' />
                                        {/* <Flex justify='center' style={{ width: '100%' }}>
                                            <Button className='uploadBtn'>上传成图</Button>
                                        </Flex> */}
                                        <Flex justify='between'
                                            style={{ paddingTop: 10, borderTop: '1px solid #444444', width: '100%' }}
                                            onClick={() => {
                                                console.log("详情页")
                                            }}>
                                            <div>{'查看详情'}</div>
                                            <div className='completeNextIcon' />
                                        </Flex>
                                    </Flex>
                                </div>
                            </div>
                        } />
                </WingBlank>
                {/* 放大图片对话框 */}
                <Modal
                    visible={this.state.showModal}
                    transparent={true}
                    closable={true}
                    onClose={() => {
                        this.setState({ showModal: false })
                    }}
                    style={{ width: '100%' }}
                    className='orderModalChange'
                >
                    <Flex justify='center' align='center' style={{ width: '100%', height: '100%' }}>
                        <img alt="" src={this.state.imgURL} className='modalImgClass' />
                    </Flex>
                    <div className='modalDownLoadBtn'>
                        <Button>保存图片</Button>
                    </div>
                </Modal>
            </div>
        )
    }

}

export default Complete

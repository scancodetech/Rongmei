import React from 'react'
import './style.css'
import { Flex, List, WhiteSpace, Tabs, WingBlank, Button, ListView, Toast, Modal } from 'antd-mobile'
import { api } from "../../../services/api/ApiProvider";

class Order extends React.Component {
    boxOrderService = api.boxOrderService;
    uploadService = api.uploadService;

    state = {
        orderData: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        limit: 10,
        page: 0,
        loading: true
    }

    async componentDidMount() {
        await this.getNextPage();
    }

    async getNextPage() {
        this.setState({
            loading: true
        })
        const res = await this.boxOrderService.queryBoxOrder(0, this.state.page + 1, this.state.limit);
        const newData = this.state.orderData.cloneWithRows(res.boxOrderItemList);
        console.log(newData)
        this.setState({
            orderData: newData,
            page: this.state.page + 1
        })
        this.setState({
            loading: false
        })
    }

    async refreshCurrentPage() {
        this.setState({
            loading: true
        })
        let newData = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        })
        const res = await this.boxOrderService.queryBoxOrder(0, 1, this.state.limit * this.state.page);
        newData = newData.cloneWithRows(res.boxOrderItemList);
        this.setState({
            orderData: newData,
            page: this.state.page + 1
        })
        this.setState({
            loading: false
        })
    }

    async grab(orderId) {
        try {
            const grabRes = await this.boxOrderService.grabBoxOrder(orderId);
            if (grabRes.infoCode === 10000) {
                Toast.success("抢单成功")
            }
        } catch (e) {
            Toast.error("抢单失败")
        }
        this.refreshCurrentPage()
    }

    render() {
        return (
            <div className='orderPage'>
                {/* <WhiteSpace size='lg' /> */}
                <WingBlank size='lg'>
                    <ListView
                        style={{ minHeight: window.innerHeight - 175 }}
                        ref={el => this.lv = el}
                        dataSource={this.state.orderData}
                        renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                            {this.state.isLoading ? 'Loading...' : null}
                        </div>)}
                        onScroll={()=>{console.log("scroll")}}
                        pageSize={this.state.limit}
                        scrollRenderAheadDistance={500}
                        onEndReached={() => this.getNextPage()}
                        onEndReachedThreshold={10}
                        renderRow={(rowData, sectionID, rowID) =>
                            <div className='orderBanner'>
                                <Flex direction='column' align='start'
                                    style={{ padding: '10px 15px', backgroundColor: '#222222' }}>
                                    <Flex align='start' justify='start' style={{ color: '#FF4C00', }}>
                                        <div className='circle' />
                                        <div style={{
                                            fontSize: 13,
                                            fontWeight: 600,
                                            padding: '0 10px'
                                        }}>{rowData.price / 100}元定制单
                                        </div>
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
                                                }} src={rowData.exampleUrl} />
                                            </Flex>
                                        </Flex.Item>
                                    </Flex>
                                    <WhiteSpace size='lg' />
                                    <Flex justify='center' style={{ width: '100%' }}>
                                        <Button className='orderBtn' onClick={() => this.grab(rowData.id)}>抢单</Button>
                                    </Flex>
                                </Flex>
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

export default Order

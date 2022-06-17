import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'

import { Flex, List, WhiteSpace, Tabs, WingBlank, Button, ListView, Toast, Modal } from 'antd-mobile'

class OrderDetail extends React.Component {
    state = {
        id: '12',
        order: '9.99元定制单',
        type: '大头',
        style: '正太',
        theme: '迷雾森林',
        element: '一定要有乌鸦',
        example: 'https://picsum.photos/200/300',
        checkItem: 'https://picsum.photos/200/300',
        completeItem: 'https://picsum.photos/200/300',
        // 反馈意见有三种状态，1：暂未反馈，创作者无法上传终图（包含资源包），2：用户填写反馈内容，创作者上传终图，3：反馈 “满意意见，无其他要求”，创作者直接上传资源包
        checkOpinion: '满意',
        endDate: '2021-3-22',
    }
    render() {
        const state = this.state;
        return (
            <div className='orderDetailPage'>
                <Header title='订单详情' theme={{ mode: 'dark' }} />

                <WingBlank size='lg'>
                    <div className='orderBanner'>
                        <Flex direction='column' align='start' style={{ padding: '10px 15px', }}>
                            <Flex align='center' justify='between' style={{ color: '#FF4C00', width: '100%' }}>
                                <Flex>
                                    <div className='circle' />
                                    <div style={{ fontSize: 13, fontWeight: 600, padding: '0 10px' }}>{state.order}</div>
                                </Flex>
                                <Flex >
                                    <div style={{ color: '#999999' }}>{`完结于：${state.endDate}`}</div>
                                </Flex>
                                <Flex justify='end' align='center' style={{ width: 50 }}>
                                    <div style={{ fontStyle: 'italic', fontSize: 17, paddingRight: 5, color: '#FFFF96' }}>{`#${state.id}`}</div>
                                </Flex>
                            </Flex>
                            <WhiteSpace size='sm' />
                            <Flex justify='start' style={{ width: '100%', }}>
                                <div className='Time' />
                                <div style={{ paddingLeft: 10 }}>已接单{"某天某小时"}</div>
                            </Flex>
                            <WhiteSpace size='md' />
                            <Flex style={{ width: '100%' }} align='start'>
                                <Flex.Item>
                                    <Flex direction='column' align='start' className='requirement'>
                                        <div>定制类型：{state.type}</div>
                                        <div>定制风格：{state.style}</div>
                                        <div>定制主题：{state.theme}</div>
                                        <div style={{ width: '100%', height: '100%' }}>元素要求：{state.element}</div>
                                    </Flex>
                                </Flex.Item>
                                <Flex.Item>
                                    <Flex direction='column' align='start' className='requirement'>
                                        <div>参考例图：</div>
                                        <img alt='' className='imgClass' onClick={() => { this.setState({ imgURL: state.example, showModal: true }) }} src={state.example} />
                                    </Flex>
                                </Flex.Item>
                            </Flex>
                            <WhiteSpace size='lg' />
                            {/* <Flex justify='center' style={{ width: '100%' }}>
                                        <Button className='uploadBtn'>上传成图</Button>
                                    </Flex> */}

                            {/* 以下为成图 */}
                            <div className='checkPart'>
                                <Flex justify='center' direction='column' align='start'>
                                    <div>初次成图：</div>
                                    <div style={{ width: '100%' }}>
                                        <img alt='' src={state.checkItem} className='checkImgBanner' />
                                    </div>
                                    <div style={{ color: '#FE2341' }}>反馈意见：</div>

                                </Flex>
                            </div>


                            {/* 反馈意见  */}
                            {
                                state.checkOpinion == '' &&

                                <Flex justify='center' style={{ width: '100%' }} direction='column'>
                                    <div className='opinionClass'>
                                        <div style={{ padding: "5px 10px", textAlign: 'center', lineHeight: '45px', fontSize: 15 }}>{'暂未反馈'}</div>
                                    </div>
                                    <WhiteSpace size='lg' />
                                </Flex>
                            }
                            {/* {
                                        item.checkOpinion != '' &&
                                        <Flex justify='center' style={{ width: '100%' }} direction='column'>
                                            <div className='opinionClass'>
                                                <div style={{ padding: "5px 10px" }}>{item.checkOpinion}</div>
                                            </div>
                                            <WhiteSpace size='lg' />
                                            <Button className='uploadBtn' >上传终图</Button>
                                        </Flex>
                                    } */}
                            {
                                state.checkOpinion == '满意' &&
                                <Flex justify='center' style={{ width: '100%' }} direction='column'>
                                    <div className='opinionClass'>
                                        <Flex justify='center' align='center'>
                                            <div className='checkGoodIcon' />
                                            <div style={{ padding: "5px 10px", textAlign: 'center', lineHeight: '45px' }}>{"满意，无其他要求"}</div>

                                        </Flex>
                                    </div>
                                    <WhiteSpace size='lg' />
                                </Flex>
                            }
                            {/* 以下为终版成图 */}
                            <div className='checkPart' style={{ border: 0 }}>
                                <Flex justify='center' direction='column' align='start'>
                                    <div>终版成图：</div>
                                    <div style={{ width: '100%' }}>
                                        <img alt='' src={state.checkItem} className='checkImgBanner' />
                                    </div>
                                </Flex>
                            </div>

                            {/* 资源包 */}
                            <div className='checkPart' style={{ border: 0, minHeight: 120 }}>
                                <Flex justify='center' direction='column' align='start' style={{ width: '100%' }}>
                                    <div>资源包：</div>
                                    <div style={{ height: 120, width: '100%', justifyContent: "center", display: 'flex', backgroundColor: "#333333", borderRadius: 5, marginTop: 10 }}>
                                        <div className='orderDetailDataPacketIcon' />
                                    </div>
                                </Flex>
                            </div>
                        </Flex>
                    </div>
                </WingBlank>
                <Modal
                    visible={this.state.showModal}
                    transparent={true}
                    closable={true}
                    onClose={() => { this.setState({ showModal: false }) }}
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
export default OrderDetail
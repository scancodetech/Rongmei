import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'

import ODawaitPayIcon from '../../../assets/ODawaitPay.png'
import ODawaitOrderIcon from '../../../assets/ODawaitOrder.png'
import ODawaitPicIcon from '../../../assets/ODawaitPic.png'

import ODawaitUpDateFileIcon from '../../../assets/ODawaitUpDateFile.png'
import ODawaitUpDatePicIcon from '../../../assets/ODawaitUpDatePic.png'

import evaluatePresentIcon from '../../../assets/evaluatePresent.svg'
import evaluateGoodIcon from '../../../assets/evaluateGood.svg'
import evaluateBlackListIcon from '../../../assets/evaluateBlackList.svg'

import {Grid, Flex, List, WhiteSpace, Tabs, WingBlank, Button, ListView, Toast, Modal, TextareaItem} from 'antd-mobile'
import {api} from "../../../services/api/ApiProvider";
import {formatDate} from "../../../utils/utils";

class OrderDetail extends React.Component {
    // 订单状态:
    // 0: 未付款，
    // 1：未接单
    // 2：已结单未上传成图
    // 3：已上传未反馈或已反馈需要修改
    // 4：已反馈——不需要修改 未上传资源文件
    // 5：已完成——可以下载资源文件，对定制进行评价
    state = {
        id: 0,
        orderType: 0,
        customType: '',
        customStyle: '',
        customTheme: '',
        elementRequirements: '',
        payStatus: 0,//0:待支付 1:待分享 2:已支付
        status: 0,//0:抢单 1:制作中 2:反馈 3:已完成
        exampleUrl: '',
        price: 0,
        username: '',
        shareUsernames: [],
        shareEndTime: 0,
        createTime: 0,
        updateTime: 0,
        acceptUsername: '',
        acceptTime: 0,
        resultCoverUrl: '',
        resultUrl: '',
        commentStatus: 0,
        comment: '',
        finishTime: 0,

        gridEvaluateData: [
            {
                icon: evaluatePresentIcon,
                text: '打赏',
            },
            {
                icon: evaluateGoodIcon,
                text: '赞一个',
            },
            {
                icon: evaluateBlackListIcon,
                text: '拉黑',
            },
        ]
    }

    async componentDidMount() {
        try {
            const boxOrderId = this.props.history.location.pathname.split("/").pop();
            const boxOrderRes = await api.boxOrderService.getBoxOrder(boxOrderId);
            this.setState({
                ...boxOrderRes
            })
        } catch (e) {
            console.log(e);
        }
    }

    downloadFile(resultUrl) {
        window.open(resultUrl);
    }

    async comment(commentStatus) {
        try {
            await api.boxOrderService.commentBoxOrder(this.state.id, commentStatus, this.state.comment);
            this.componentDidMount();
            Toast.success("评论成功")
        } catch (e) {
            Toast.fail("评论失败")
        }
    }

    render() {
        const state = this.state;
        return (
            <div className='orderDetailPage'>
                <Header title='订单详情' theme={{mode: 'dark'}}/>

                <WingBlank size='lg'>
                    <div className='orderBanner'>
                        <Flex direction='column' align='start' style={{padding: '10px 15px',}}>
                            <Flex align='center' justify='between' style={{color: '#FF4C00', width: '100%'}}>
                                <Flex>
                                    <div className='circle'/>
                                    <div style={{
                                        fontSize: 13,
                                        fontWeight: 600,
                                        padding: '0 10px'
                                    }}>{(state.price / 100).toLocaleString()}定制单
                                    </div>
                                </Flex>
                                <Flex>
                                    <div className='MinePHHOrderListPIcon'/>
                                    <div style={{color: '#999999'}}>
                                        {this.state.finishTime === 0 ? `开始于：${formatDate(state.createTime)}` : `完结于：${formatDate(state.finishTime)}`}
                                    </div>
                                </Flex>
                            </Flex>

                            <Flex style={{width: '100%'}} align='start'>
                                <Flex.Item>
                                    <Flex direction='column' align='start' className='requirement'>
                                        <div>定制类型：{state.customType}</div>
                                        <div>定制风格：{state.customStyle}</div>
                                        <div>定制主题：{state.customTheme}</div>
                                        <div style={{
                                            width: '100%',
                                            height: '100%'
                                        }}>元素要求：{state.elementRequirements}</div>
                                    </Flex>
                                </Flex.Item>
                                <Flex.Item>
                                    <Flex direction='column' align='start' className='requirement'>
                                        <div>参考例图：</div>
                                        <img alt='' className='imgClass' onClick={() => {
                                            this.setState({imgURL: state.exampleUrl, showModal: true})
                                        }} src={state.exampleUrl}/>
                                    </Flex>
                                </Flex.Item>
                            </Flex>
                            <WhiteSpace size='lg'/>
                            {/* <Flex justify='center' style={{ width: '100%' }}>
                                        <Button className='uploadBtn'>上传成图</Button>
                                    </Flex> */}

                            {/* 以下为成图 */}
                            {/* <div className='checkPart'>
                                <Flex justify='center' direction='column' align='start'>
                                    <div>初次成图：</div>
                                    <div style={{ width: '100%' }}>
                                        <img alt='' src={state.checkItem} className='checkImgBanner' />
                                    </div>
                                    <div style={{ color: '#FE2341' }}>反馈意见：</div>

                                </Flex>
                            </div> */}

                            {/* 订单状态为0时的显示 */}
                            {
                                this.state.payStatus === 0 &&
                                <Flex justify='center' direction='column' className='MinePHHODBanner'
                                      style={{padding: '100px 0'}}>
                                    <img src={ODawaitPayIcon} alt=''
                                         style={{width: 181, height: 147, marginBottom: 50}}/>
                                    <div className='MinePHHODPayBtnChange' style={{width: 160}}>
                                        <Button>立即付款</Button>
                                    </div>
                                </Flex>
                            }
                            {/* 订单状态为1时的显示 */}
                            {
                                this.state.payStatus === 2 && this.state.status === 0 &&
                                <Flex justify='center' direction='column' className='MinePHHODBanner'
                                      style={{padding: '100px 0'}}>
                                    <img src={ODawaitOrderIcon} alt='' style={{width: 181, height: 147}}/>
                                </Flex>
                            }
                            {/* 订单状态为2时的显示 */}
                            {
                                this.state.payStatus === 2 && this.state.status === 1 && this.state.resultCoverUrl.length === 0 &&
                                <Flex justify='center' direction='column' className='MinePHHODBanner'
                                      style={{padding: '100px 0'}}>
                                    <img src={ODawaitPicIcon} alt='' style={{width: 181, height: 147}}/>
                                </Flex>
                            }
                            {/* 订单状态为3时的显示 */}
                            {
                                this.state.payStatus === 2 && this.state.status === 2 && (this.state.commentStatus === 0 || this.state.commentStatus === 1) &&
                                <div className='checkPart'>
                                    <Flex justify='center' direction='column' align='center' className='MinePHHODBanner'
                                          style={{borderTop: 0}}>
                                        <div>创作者成图</div>
                                        <div style={{width: '100%'}}>
                                            <img alt='' src={state.resultCoverUrl} className='checkImgBanner'/>
                                        </div>
                                        <div>反馈意见</div>
                                        <div className='opinionClass'>
                                            <TextareaItem
                                                value={this.state.comment}
                                                onChange={(value) => {
                                                    this.setState({
                                                        comment: value
                                                    })
                                                }} rows={3}/>
                                        </div>
                                        <Flex justify='between' align='center' className='opinionBtnChange'
                                              style={{width: '100%'}}>
                                            <Button onClick={() => this.comment(2)}>很满意、无反馈</Button>
                                            <Button onClick={() => this.comment(1)}>确认反馈</Button>
                                        </Flex>
                                    </Flex>
                                </div>
                            }
                            {/* 订单状态为4时的显示 */}
                            {
                                this.state.payStatus === 2 && this.state.status === 2 && this.state.commentStatus === 2 &&
                                <div className='checkPart'>
                                    <Flex justify='center' direction='column' align='center' className='MinePHHODBanner'
                                          style={{borderTop: 0}}>
                                        <div>创作者成图</div>
                                        <div style={{width: '100%'}}>
                                            <img alt='' src={state.resultCoverUrl} className='checkImgBanner'/>
                                        </div>
                                        <div>反馈意见</div>
                                        <div className='opinionClass'>
                                            <div style={{padding: "5px 10px"}}>{this.state.comment}</div>
                                        </div>
                                        <div style={{padding: '30px 0'}}>
                                            <img src={ODawaitUpDateFileIcon} alt='' style={{width: 222, height: 147,}}/>
                                        </div>
                                    </Flex>
                                </div>
                            }
                            {/* 订单状态为5时的显示 */}
                            {
                                this.state.payStatus === 2 && this.state.status === 3 &&
                                <div className='checkPart'>
                                    <Flex justify='center' direction='column' align='center' className='MinePHHODBanner'
                                          style={{borderTop: 0,}}>
                                        <div>创作者成图</div>
                                        <div style={{width: '100%'}}>
                                            <img alt='' src={state.resultCoverUrl} className='checkImgBanner'/>
                                        </div>
                                        <div>反馈意见</div>
                                        <div className='opinionClass'>
                                            <div style={{padding: "5px 10px"}}>{this.state.comment}</div>
                                        </div>
                                        <WhiteSpace size='md'/>
                                        <div className='opinionClass' style={{
                                            height: 90,
                                            textAlign: 'center',
                                            lineHeight: '90px',
                                            fontSize: 15,
                                            color: '#FE2341'
                                        }}>
                                            {this.state.resultUrl.split("/").pop()}
                                        </div>
                                        <WhiteSpace size='md'/>
                                        <div className='MinePHHODPayBtnChange' style={{width: '100%'}}>
                                            <Button
                                                onClick={() => this.downloadFile(this.state.resultUrl)}>保存资源文件</Button>
                                        </div>
                                    </Flex>

                                    <div className='MinePHHOrderListGridTextChange'>
                                        <div style={{fontSize: 20, textAlign: 'center'}}>评价此次定制</div>
                                        <Grid
                                            data={this.state.gridEvaluateData}
                                            columnNum={3}
                                            hasLine={false}
                                            activeClassName='MinePHHOrderListactiveclass'
                                            itemStyle={{color: 'white'}}
                                        />
                                    </div>
                                </div>
                            }

                            {/* 反馈意见  */}
                            {
                                // state.checkOpinion == '' &&

                                // <Flex justify='center' style={{ width: '100%' }} direction='column'>
                                //     <div className='opinionClass'>
                                //         <TextareaItem rows={3} />
                                //     </div>
                                //     <WhiteSpace size='lg' />
                                // </Flex>
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
                                // state.checkOpinion == '满意' &&
                                // <Flex justify='center' style={{ width: '100%' }} direction='column'>
                                //     <div className='opinionClass'>
                                //         <Flex justify='center' align='center'>
                                //             <div className='checkGoodIcon' />
                                //             <div style={{ padding: "5px 10px", textAlign: 'center', lineHeight: '45px' }}>{"满意，无其他要求"}</div>

                                //         </Flex>
                                //     </div>
                                //     <WhiteSpace size='lg' />
                                // </Flex>
                            }
                            {/* 以下为终版成图 */}
                            {/* <div className='checkPart' style={{ border: 0 }}>
                                <Flex justify='center' direction='column' align='start'>
                                    <div>终版成图：</div>
                                    <div style={{ width: '100%' }}>
                                        <img alt='' src={state.checkItem} className='checkImgBanner' />
                                    </div>
                                </Flex>
                            </div> */}

                            {/* 资源包 */}
                            {/* <div className='checkPart' style={{ border: 0, minHeight: 120 }}>
                                <Flex justify='center' direction='column' align='start' style={{ width: '100%' }}>
                                    <div>资源包：</div>
                                    <div style={{ height: 120, width: '100%', justifyContent: "center", display: 'flex', backgroundColor: "#333333", borderRadius: 5, marginTop: 10 }}>
                                        <div className='orderDetailDataPacketIcon' />
                                    </div>
                                </Flex>
                            </div> */}
                        </Flex>
                    </div>
                </WingBlank>
                <Modal
                    visible={this.state.showModal}
                    transparent={true}
                    closable={true}
                    onClose={() => {
                        this.setState({showModal: false})
                    }}
                    style={{width: '100%'}}
                    className='orderModalChange'
                >
                    <Flex justify='center' align='center' style={{width: '100%', height: '100%'}}>
                        <img alt="" src={this.state.imgURL} className='modalImgClass'/>
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

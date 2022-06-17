import React from 'react';
import { withRouter } from "react-router-dom";
import './style.css';
import Header from '../../components/Header/index';
import { Flex, List, WhiteSpace, WingBlank, Icon, Image, Button, InputItem, Modal, ImagePicker } from 'antd-mobile';
import zyb from '../../assets/zyb.png'
import { api } from "../../services/api/ApiProvider";

import detailHistoryIcon from '../../assets/detailHistory.svg'

@withRouter
class Detail extends React.Component {
    auctionService = api.auctionService;
    state = {
        name: '',
        price: 0,
        description: '',
        author: '',
        coverUrl: '',
        // 控制新增对话框
        showAddModal:false,
        development: [
            {
                name: '2',
                price: 0,
                description: '2',
                author: '2',
                coverUrl: 'https://picsum.photos/200/300',
            },
            {
                name: '3',
                price: 0,
                description: '3',
                author: '3',
                coverUrl: 'https://picsum.photos/200/300',
            },
        ],
        developmentIndex: 0,
        addDevelopmentData: {
            files: [],
            name: '',
            type: '',
            author: '',
            price: '',
            introduction: '',
        }
    }

    async componentDidMount() {
        this.getData();
    }

    async getData() {
        const thingId = this.props.location.pathname.split('/').pop();
        if (thingId !== 0) {
            const thingRes = await this.auctionService.getThing(thingId);
            this.setState({
                id: thingId,
                name: thingRes.name,
                price: thingRes.price,
                description: thingRes.description,
                author: thingRes.author,
                coverUrl: thingRes.url,
                tokenId: thingRes.tokenId
            })
        }
    }

    async getToken() {
        const tokenRes = await this.auctionService.getToken(this.state.tokenId);
        if (tokenRes.value) {
            window.open(tokenRes.value);
        }
    }

    async getCover() {
        window.open(this.state.coverUrl);
    }

    onChange = (files, type, index) => {
        console.log(files, type, index);
        let newdata = this.state.addDevelopmentData
        newdata.files = files
        this.setState({
            addDevelopmentData: newdata
        }, () => { console.log(this.state.addDevelopmentData) });
    }
    changeAddData(value, type) {
        let newdata = this.state.addDevelopmentData;
        newdata[type] = value;
        this.setState({
            addDevelopmentData:newdata
        },()=>{console.log(this.state.addDevelopmentData)})
    }
    render() {
        return (
            <div className='detailPage'>
                {/* 页面头部 */}
                <Header title={'藏品详情'} theme={{ mode: 'dark' }}>
                    {/* 这里设置卖出的页面跳转 */}
                    <div onClick={() => {
                        this.props.history.push(`/saleOut/${this.state.id}`)
                    }}>
                        卖出
                    </div>
                </Header>
                {/* 页面头部结束 */}


                <div style={{ display: 'flex', flexDirection: 'row', overflow: 'scroll' }}>
                    {this.state.development.map((item, index) =>
                        <div style={{ width: '100VW' }}>
                            {/* 藏品名称 */}
                            <WingBlank size='lg'>
                                <Flex style={{ position: "relative", padding: '5px 0' }} justify='end'>
                                    <div style={{ fontSize: 15, position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%,-50%)' }}>{this.state.name}</div>
                                    <div className='add'>
                                        <Button onClick={()=>{this.setState({showAddModal:true})}}>新增记录</Button>
                                    </div>
                                </Flex>
                            </WingBlank>
                            {/* 藏品名称结束 */}

                            {/* PicView */}
                            {/* 图片处理 */}
                            <div className='imgWrapper'>
                                <img className='bannerImg' src={this.state.coverUrl} />
                            </div>
                            {/* 上下留白 */}
                            <WhiteSpace size="lg" />
                            <div>
                                <Flex justify='start'>
                                    <div style={{ color: '#FE2341', fontSize: 15, fontWeight: 600, padding: '0 10px' }}>成交价：{this.state.price} 电子</div>
                                </Flex>
                            </div>
                            {/* 上下留白 */}
                            <WhiteSpace size="lg" />
                            {/* PicView结束 */}

                            {/* 分割线 */}
                            <div style={{ width: '100%', height: '5px', borderTop: '1px solid #333333', marginBottom: 10 }} />

                            {/* 详情 */}
                            <WingBlank size='lg'>
                                <div style={{ fontSize: 14 }}>
                                    <Flex justify='between'>
                                        <div style={{ fontSize: 16, }}>详情：</div>
                                        <Flex>
                                            <img src={detailHistoryIcon} style={{ width: 45, height: 45, padding: '0 5px' }} />
                                            <div style={{ color: '#FFC600' }} onClick={()=>{this.props.history.push(`/detail/${this.state.id}/development`)}}>养成史</div>
                                        </Flex>
                                    </Flex>
                                    <WhiteSpace size="md" />
                                    <Flex>
                                        <div>创作者：</div>
                                        <div>{this.state.author}</div>
                                    </Flex>
                                    <WhiteSpace size="md" />
                                    <Flex>
                                        <div>简介：</div>
                                    </Flex>
                                    <WhiteSpace size="md" />
                                    <Flex>
                                        <div>{this.state.description}</div>
                                    </Flex>
                                    <WhiteSpace size="md" />
                                    <Flex justify='between' align='start'>
                                        <div>资源包：</div>
                                        <Flex justify='around' style={{ width: '80%' }} align='center'>
                                            <div
                                                className='btn'
                                                onClick={() => {
                                                    this.getToken()
                                                }}
                                            >
                                                <img src={zyb} style={{ width: 22, height: 26 }} />
                                            </div>
                                            <Button onClick={() => {
                                                this.getToken()
                                            }}>下载资源包</Button>
                                        </Flex>
                                    </Flex>
                                </div>
                            </WingBlank>
                            {/* 详情结束 */}

                            <WhiteSpace size="md" />
                            <WhiteSpace size="md" />
                            <div
                                className='dwnPic' onClick={() => {
                                    this.getCover()
                                }}>
                                保存图片至本地
                            </div>
                        </div>
                    )

                    }

                </div>

                <Modal
                    visible={this.state.showAddModal}
                    title='新增记录'
                    transparent
                    closable
                    maskClosable={false}
                    onClose={()=>{this.setState({showAddModal:false})}}
                    style={{ width: '90%' }}
                    className='detailPageModalChange'
                    wrapClassName='detailPageModalWrapChange'
                >
                    <Flex justify='between' align='center'>
                        <div style={{ width: 50 }}>名称：</div>
                        <InputItem style={{ width: '250px' }} placeholder='请输入名称' onChange={(value) => this.changeAddData(value, 'name')} />
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='between' align='center'>
                        <div style={{ width: 50 }}>类型：</div>
                        <InputItem style={{ width: '250px' }} placeholder='请输入类型' onChange={(value) => this.changeAddData(value, 'type')} />
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='between' align='center'>
                        <div style={{ width: 50 }}>画师：</div>
                        <InputItem style={{ width: '250px' }} placeholder='请输入画师' onChange={(value) => this.changeAddData(value, 'author')} />
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='between' align='center'>
                        <div style={{ width: 50 }}>价格：</div>
                        <InputItem style={{ width: '250px' }} placeholder='请输入价格' onChange={(value) => this.changeAddData(value, 'price')}/>
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='between' align='center'>
                        <div style={{ width: 50 }}>简介：</div>
                        <InputItem style={{ width: '250px' }} placeholder='请输入简介' onChange={(value) => this.changeAddData(value, 'introduction')}/>
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='start' align='start'>
                        <div style={{ width: 50 }}>图样：</div>
                        <div style={{ width: 140 }}>
                            <ImagePicker
                                length={1}
                                files={this.state.addDevelopmentData.files}
                                selectable={this.state.addDevelopmentData.files < 1}
                                onChange={this.onChange}
                            />
                        </div>
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex justify='start' align='start'>
                        <div style={{ width: 80 }}>资源包：</div>
                        <div style={{ width: 160 }}>
                            <div
                                className='btn'
                                onClick={() => {
                                    // this.getToken()
                                }}
                            >
                                <img src={zyb} style={{ width: 22, height: 26 }} />
                            </div>
                        </div>
                    </Flex>
                    <WhiteSpace size='lg' />
                    <Flex>
                        <div className='DetailModalBtnChange'>
                            <Button>确认新增</Button>
                        </div>
                    </Flex>
                </Modal>
            </div >
        )
    }
}

export default Detail

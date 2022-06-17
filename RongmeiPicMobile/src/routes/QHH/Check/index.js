import React from 'react'
import './style.css'
import {Flex, List, WhiteSpace, Tabs, WingBlank, Button, Modal, ListView, Toast} from 'antd-mobile'
import {getDateDiff, uploadFileToCos} from "../../../utils/utils";
import {api} from "../../../services/api/ApiProvider";

class Check extends React.Component {
    state = {
        page: 0,
        limit: 10,
        checkData: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        data: [],
        showModal: false,
    }

    async componentDidMount() {
        await this.getNextPage();
    }

    async getNextPage() {
        this.setState({
            loading: true
        })
        const res = await api.boxOrderService.queryBoxOrder(2, this.state.page + 1, this.state.limit);
        const newData = this.state.data.concat(res.boxOrderItemList);
        const newDataSource = this.state.checkData.cloneWithRows(newData);
        this.setState({
            data: newData,
            checkData: newDataSource,
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
        let newDataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        })
        const res = await api.boxOrderService.queryBoxOrder(2, 1, this.state.limit * this.state.page);
        const newData = res.boxOrderItemList;
        newDataSource = newDataSource.cloneWithRows(res.boxOrderItemList);
        this.setState({
            data: newData,
            checkData: newDataSource,
            page: this.state.page + 1
        })
        this.setState({
            loading: false
        })
    }

    onUploadResult = async (e, id) => {
        e.preventDefault();
        try {
            const file = e.target.files[0];
            const cosRes = await uploadFileToCos(file.name, file);
            if (cosRes.statusCode === 200) {
                const res = await api.boxOrderService.submitBoxOrderResult(id, "https://" + cosRes.Location);
                if (res.infoCode === 10000) {
                    Toast.success("上传成功");
                    this.refreshCurrentPage();
                }
            }
        } catch (e) {
            Toast.fail("上传失败，请重试");
        }
    };

    onUploadCover = async (e, id) => {
        e.preventDefault();
        try {
            Toast.info("正在上传");
            const file = e.target.files[0];
            const cosRes = await uploadFileToCos(file.name, file);
            if (cosRes.statusCode === 200) {
                const res = await api.boxOrderService.submitBoxOrderResultCover(id, "https://" + cosRes.Location);
                if (res.infoCode === 10000) {
                    Toast.success("上传成功");
                    this.refreshCurrentPage();
                }
            }
        } catch (e) {
            console.log(e)
            Toast.fail("上传失败，请重试");
        }
    };

    render() {
        return (
            <div className='checkPage'>
                <WingBlank size='lg'>
                    <ListView
                        style={{minHeight: window.innerHeight}}
                        ref={el => this.lv = el}
                        dataSource={this.state.checkData}
                        renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                            {this.state.isLoading ? 'Loading...' : null}
                        </div>)}
                        pageSize={this.state.limit}
                        scrollRenderAheadDistance={500}
                        onEndReached={() => this.getNextPage()}
                        onEndReachedThreshold={10}
                        renderRow={(rowData, sectionID, rowID) =>
                            <div className='orderBanner'>
                                <Flex direction='column' align='start' style={{padding: '10px 15px',}}>
                                    <Flex align='center' justify='between' style={{color: '#FF4C00', width: '100%'}}>
                                        <Flex>
                                            <div className='circle'/>
                                            <div style={{
                                                fontSize: 13,
                                                fontWeight: 600,
                                                padding: '0 10px'
                                            }}>{rowData.price}元定制单
                                            </div>
                                        </Flex>
                                        <Flex justify='end' align='center' style={{width: 100}}>
                                            <div style={{
                                                fontStyle: 'italic',
                                                fontSize: 17,
                                                paddingRight: 5,
                                                color: '#FFFF96'
                                            }}>{`#${rowData.id}`}</div>
                                        </Flex>
                                    </Flex>
                                    <WhiteSpace size='sm'/>
                                    <Flex justify='start' style={{width: '100%',}}>
                                        <div className='Time'/>
                                        <div style={{paddingLeft: 10}}>已接单{getDateDiff(rowData.acceptTime)}</div>
                                    </Flex>
                                    <WhiteSpace size='md'/>
                                    <Flex style={{width: '100%'}} align='start'>
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
                                                    this.setState({imgURL: rowData.exampleUrl, showModal: true})
                                                }} src={rowData.exampleUrl}/>
                                            </Flex>
                                        </Flex.Item>
                                    </Flex>
                                    <WhiteSpace size='lg'/>
                                    {/* <Flex justify='center' style={{ width: '100%' }}>
                                        <Button className='uploadBtn'>上传成图</Button>
                                    </Flex> */}

                                    {/* 以下为成图 */}
                                    <div className='checkPart'>
                                        <Flex justify='center' direction='column' align='start'>
                                            <div>初次成图：</div>
                                            <div style={{width: '100%'}}>
                                                <img alt='' src={rowData.resultCoverUrl} className='checkImgBanner'/>
                                            </div>
                                            <div style={{color: '#FE2341'}}>反馈意见：</div>
                                        </Flex>
                                    </div>
                                    {/* 反馈意见  */}
                                    {
                                        rowData.commentStatus === 0 &&
                                        <Flex justify='center' style={{width: '100%'}} direction='column'>
                                            <div className='opinionClass'>
                                                <div style={{
                                                    padding: "5px 10px",
                                                    textAlign: 'center',
                                                    lineHeight: '45px',
                                                    fontSize: 15
                                                }}>{'暂未反馈'}</div>
                                            </div>
                                            <WhiteSpace size='lg'/>
                                            <Button className='uploadBtn'>
                                                <input type="file" name="image" style={{
                                                    position: 'absolute',
                                                    width: '100%',
                                                    left: 0,
                                                    zIndex: 1000,
                                                    opacity: 0
                                                }} onChange={(e) => this.onUploadCover(e, rowData.id)}/>修改成图</Button>
                                        </Flex>
                                    }
                                    {
                                        rowData.commentStatus === 1 &&
                                        <Flex justify='center' style={{width: '100%'}} direction='column'>
                                            <div className='opinionClass'>
                                                <div style={{padding: "5px 10px"}}>{rowData.comment}</div>
                                            </div>
                                            <WhiteSpace size='lg'/>
                                            <Button className='uploadBtn'>
                                                <input type="file" name="image" style={{
                                                    position: 'absolute',
                                                    width: '100%',
                                                    left: 0,
                                                    zIndex: 1000,
                                                    opacity: 0
                                                }} onChange={(e) => this.onUploadCover(e, rowData.id)}/>修改成图</Button>
                                        </Flex>
                                    }
                                    {
                                        rowData.commentStatus === 2 &&
                                        <Flex justify='center' style={{width: '100%'}} direction='column'>
                                            <div className='opinionClass'>
                                                <Flex justify='center' align='center'>
                                                    <div className='checkGoodIcon'/>
                                                    <div style={{
                                                        padding: "5px 10px",
                                                        textAlign: 'center',
                                                        lineHeight: '45px'
                                                    }}>{"满意，无其他要求"}</div>

                                                </Flex>
                                            </div>
                                            <WhiteSpace size='lg'/>
                                            <Button className='uploadBtn'>
                                                <input type="file" name="image" style={{
                                                    position: 'absolute',
                                                    width: '100%',
                                                    left: 0,
                                                    opacity: 0
                                                }} onChange={(e) => this.onUploadResult(e, rowData.id)}/>
                                                上传资源包</Button>
                                        </Flex>
                                    }
                                </Flex>
                            </div>
                        }/>
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

export default Check

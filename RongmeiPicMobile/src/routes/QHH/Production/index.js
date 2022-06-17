import React from 'react'
import './style.css'
import {Flex, WhiteSpace, WingBlank, Button, Modal, ListView, Toast} from 'antd-mobile'
import {api} from "../../../services/api/ApiProvider";
import {getDateDiff, uploadFileToCos} from "../../../utils/utils";

class Production extends React.Component {
    boxOrderService = api.boxOrderService;
    uploadService = api.uploadService;

    state = {
        showModal: false,
        productionData: new ListView.DataSource({
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
        const res = await this.boxOrderService.queryBoxOrder(1, this.state.page + 1, this.state.limit);
        const newData = this.state.productionData.cloneWithRows(res.boxOrderItemList);
        this.setState({
            productionData: newData,
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
        const res = await this.boxOrderService.queryBoxOrder(1, 1, this.state.limit * this.state.page);
        newData = newData.cloneWithRows(res.boxOrderItemList);
        this.setState({
            productionData: newData,
            page: this.state.page + 1
        })
        this.setState({
            loading: false
        })
    }

    onUploadCover = async (e, id) => {
        e.preventDefault();
        try {
            Toast.info("正在上传");
            const file = e.target.files[0];
            const cosRes = await uploadFileToCos(file.name, file);
            if (cosRes.statusCode === 200) {
                const res = await this.boxOrderService.submitBoxOrderResultCover(id, "https://" + cosRes.Location);
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
            <div className='productionPage'>
                <WingBlank size='lg'>
                    <ListView
                        style={{minHeight: window.innerHeight}}
                        ref={el => this.lv = el}
                        dataSource={this.state.productionData}
                        renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                            {this.state.isLoading ? 'Loading...' : null}
                        </div>)}
                        pageSize={this.state.limit}
                        scrollRenderAheadDistance={500}
                        onEndReached={() => this.getNextPage()}
                        onEndReachedThreshold={10}
                        renderRow={(rowData, sectionID, rowID) =>
                            <div className='orderBanner'>
                                <Flex direction='column' align='start'
                                      style={{padding: '10px 15px', backgroundColor: '#222222'}}>
                                    <Flex align='start' justify='between' style={{color: '#FF4C00', width: '100%'}}>
                                        <Flex>
                                            <div className='circle'/>
                                            <div style={{
                                                fontSize: 13,
                                                fontWeight: 600,
                                                padding: '0 10px'
                                            }}>{rowData.price / 100}元定制单
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
                                    <Flex justify='center' style={{width: '100%'}}>
                                        <Button className='uploadBtn'>
                                            <input type="file" name="image" style={{
                                                position: 'absolute',
                                                width: '100%',
                                                left: 0,
                                                zIndex: 1000,
                                                opacity: 0
                                            }} onChange={(e) => this.onUploadCover(e, rowData.id)}/>上传成图
                                        </Button>
                                    </Flex>
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

export default Production

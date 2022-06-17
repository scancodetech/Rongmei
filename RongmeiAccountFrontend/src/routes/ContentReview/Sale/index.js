import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import {Button, Card, Divider, Image, Input, message, Modal, Radio, Space, Table, Tag} from "antd";
import {api} from '../../../services/api/ApiProvider'

const {TextArea} = Input;

function formatDate(time) {
    let date = new Date(time);
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = date.getDate() + ' ';
    let h = date.getHours() + ':';
    let m = date.getMinutes() + ':';
    let s = date.getSeconds();
    return Y + M + D + h + m + s;
}

class ContentReviewSalePage extends React.Component {
    draftService = api.draftService;
    accountService = api.accountService;
    noticeService = api.noticeService;

    state = {
        loading: false,
        data: [],
        isModalShow: false,
        limit: 10000,
        offset: 0,
        total: 0,
    }

    async componentDidMount() {
        try {
            const res = await this.draftService.getDraftSale();
            let saleItemList = res.saleItemList;
            this.setState({
                data: saleItemList,
                total: saleItemList.length
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    // 审核页面
    showModal = async (record) => {
        this.setState({
            isModalShow: true,
            detail: record
        });
        const res = await this.draftService.getDraftSaleDetail(record.id);
        this.setState({
            ...res
        })
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    resultValueOnchange(e) {
        console.log(e)
        this.setState({
            isApprove: e.target.value
        })
    };

    reasonValueOnchange(e) {
        console.log(e)
        this.setState({
            msg: e.target.value
        })
    };

    submitValue = async () => {
        // 按钮状态设置未loading
        this.setState({
            loading: true
        })
        try {
            await this.draftService.reviewDraft(this.state.id, 1, this.state.isApprove, this.state.msg);
            message.success("提交成功")
            await this.noticeService.sendNotice("竞品审批", this.state.isApprove ? `${this.state.title}竞品审批通过` : `${this.state.title}竞品审批不通过,${this.state.msg}`, "13700000002", this.state.author, 0);
            message.success("审批通知发送成功")
            this.componentDidMount();
        } catch (e) {
            console.log(e)
            message.error("提交失败，请重试~")
        }
        this.setState({
            loading: false,
            isModalShow: false
        });
    }

    render() {
        const columns = [
            {
                title: 'ID',
                dataIndex: 'id',
                key: 'id'
            }, {
                title: '名称',
                render: (value, record) => <p>{record.thing ? record.thing.name : ''}</p>,
            }, {
                title: '起拍价',
                dataIndex: 'startPrice',
                key: 'startPrice',
            }, {
                title: '拥有者',
                render: (value, record) => <p>{record.thing ? record.thing.owner : ''}</p>
            }, {
                title: '详细信息',
                render: (value, record) => <a onClick={() => this.showModal(record)}>查看详情</a>,
                width: '10%'
            }];
        return (
            <div>
                <CustomBreadcrumb arr={['内容审核', '素材']}/>
                <Card bordered={false} title='素材' style={{marginBottom: 10, minHeight: 440}}>
                    <Table columns={columns} dataSource={this.state.data} onChange={this.handleChange}/>
                    <Modal
                        title={null}
                        visible={this.state.isModalShow}
                        onOk={this.hideModal}
                        onCancel={this.hideModal}
                        width={1000}
                        centered
                        height={800}
                        footer={null}
                        style={{overflow: "auto"}}
                    >
                        <Divider style={{color: "red"}} plain={false}>拍品区</Divider>
                        <div>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"拍品信息"}</div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"拍品名"}</div>
                                            <div>{this.state.thing ? this.state.thing.name : ''}</div>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle" align="start">
                                            <div style={{fontSize: "15px"}}>{"封面图"}</div>
                                            <Image width={100} height={120}
                                                   src={this.state.thing ? this.state.thing.url : ''}/>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"作品详情"}</div>
                                            <a>{this.state.thing ? this.state.thing.description : ''}</a>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"素材标签"}</div>
                                            {this.state.thing && this.state.thing.tags && this.state.thing.tags.map((item, index) =>
                                                <Space size="small">
                                                    <div style={{
                                                        backgroundColor: "red",
                                                        height: "15px",
                                                        width: "15px"
                                                    }}/>
                                                    <div>{item}</div>
                                                </Space>
                                            )}
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"作者"}</div>
                                            <div>{this.state.thing ? this.state.thing.author : ''}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"拥有者"}</div>
                                            <div>{this.state.thing ? this.state.thing.owner : ''}</div>
                                        </Space>
                                    </div>

                                </Space>
                            </div>
                        </div>
                        {/* 基本信息样式结束 */}

                        <div>
                            <Divider plain={false}></Divider>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"更多信息"}</div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"起拍价"}</div>
                                            <div>{this.state.startPrice}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"最小拍卖间隔"}</div>
                                            <div>{this.state.intervalPrice}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"拍卖时间"}</div>
                                            <div>{formatDate(this.state.startTime)}-{formatDate(this.state.endTime)}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"是否需要保证金"}</div>
                                            <div>{this.state.needEarnestMoney ? "是" : "否"}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"是否开启拦截"}</div>
                                            <div>{this.state.enableIntercept ? "是" : "否"}</div>
                                        </Space>
                                    </div>

                                    {
                                        this.state.enableIntercept ? (
                                            <div>
                                                <Space size="middle">
                                                    <div style={{fontSize: "15px"}}>{"拦截价"}</div>
                                                    <div>{this.state.interceptPrice}</div>
                                                </Space>
                                            </div>
                                        ) : null
                                    }

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"是否需要版权税"}</div>
                                            <div>{this.state.needCopyrightTax ? "是" : "否"}</div>
                                        </Space>
                                    </div>

                                </Space>
                            </div>

                        </div>

                        <Card title="审核" hoverable headStyle={{textAlign: "center"}} style={{
                            width: "400px",
                            display: 'block',
                            position: 'sticky',
                            bottom: 0,
                            marginLeft: 550,
                            textAlign: 'center'
                        }}>
                            <Space size="small" direction="vertical" align={'center'}>
                                <Space size="small">
                                    <div style={{fontSize: "10px"}}>{"截止时间"}</div>
                                    <div style={{fontSize: "10px"}}>{"暂无"}</div>
                                </Space>
                                <Space size="small">
                                    <div style={{fontSize: "15px"}}>{"是否通过"}</div>
                                    <Radio.Group value={this.state.isApprove}
                                                 onChange={(e) => this.resultValueOnchange(e)}>
                                        <Radio value={true}>{"通过"}</Radio>
                                        <Radio value={false} style={{marginLeft: "95px"}}>{"不通过"}</Radio>
                                    </Radio.Group>
                                </Space>

                                {
                                    !this.state.isApprove &&
                                    <Space size="middle" align="start">
                                        <div style={{fontSize: "15px"}}>{"驳回理由"}</div>
                                        <TextArea value={this.state.msg} size="large" rows={3}
                                                  onChange={(e) => this.reasonValueOnchange(e)}
                                                  style={{marginRight: "35px"}}/>
                                    </Space>
                                }

                                <Space size="small" style={{textAlign: "center"}}>
                                    <Button type="primary" danger loading={this.state.loading}
                                            onClick={() => this.submitValue()}>{"确定"}</Button>
                                </Space>
                            </Space>
                        </Card>
                    </Modal>
                </Card>
            </div>
        )
    }
}

const styles = {
    tableStyle: {
        width: '100%'
    }
}

export default ContentReviewSalePage

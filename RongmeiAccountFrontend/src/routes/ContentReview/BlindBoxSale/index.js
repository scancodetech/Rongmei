import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import {Button, Card, Divider, Image, Input, message, Modal, Radio, Space, Table, Tag} from "antd";
import {api} from '../../../services/api/ApiProvider'

const {TextArea} = Input;

class ContentReviewBlindBoxSalePage extends React.Component {
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
            const res = await this.draftService.getDraftCommodity();
            let commodityItems = res.commodityItemList;
            this.setState({
                data: commodityItems,
                total: commodityItems.length
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
        const res = await this.draftService.getDraftCommodityDetail(record.id);
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
        // 信息提交
        try {
            await this.draftService.reviewDraft(this.state.id, 2, this.state.isApprove, this.state.msg);
            message.success("提交成功")
            await this.noticeService.sendNotice("盲盒审批", this.state.isApprove ? `${this.state.title}盲盒审批通过` : `${this.state.title}盲盒审批不通过,${this.state.msg}`, "13700000002", this.state.author, 0);
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
                dataIndex: 'title',
                key: 'title',
            }, {
                title: '价格',
                dataIndex: 'largePrice',
                key: 'largePrice',
            }, {
                title: '作者',
                dataIndex: 'author',
                key: 'author'
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
                        <Divider style={{color: "red"}} plain={false}>素材区</Divider>
                        <div>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"基本信息"}</div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"素材名"}</div>
                                            <div>{this.state.title}</div>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle" align="start">
                                            <div style={{fontSize: "15px"}}>{"封面图"}</div>
                                            <Image width={100} height={120} src={this.state.coverUrl}/>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"作品详情"}</div>
                                            <a>{this.state.contentUrl}</a>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"素材标签"}</div>
                                            {this.state.tags && this.state.tags.map((item, index) =>
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
                                            <div style={{fontSize: "15px"}}>{"价格"}</div>
                                            <div>{this.state.largePrice} IMM</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"详情"}</div>
                                            <div>{this.state.description}</div>
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
                                            <div style={{fontSize: "15px"}}>{"标志信息"}</div>
                                            <div>{this.state.signingInfo}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"是否为独家"}</div>
                                            <div>{this.state.isExclusive ? "是" : "否"}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"作者"}</div>
                                            <div>{this.state.author}</div>
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

export default ContentReviewBlindBoxSalePage

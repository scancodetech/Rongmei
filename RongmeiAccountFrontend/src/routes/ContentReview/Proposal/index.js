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

const visibilities = ["所有人可见", "车内成员可见", "仅自己可见"];

class ContentReviewProposalPage extends React.Component {
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
            const res = await this.draftService.getDraftProposal();
            let proposalItemList = res.proposalItemList;
            this.setState({
                data: proposalItemList,
                total: proposalItemList.length
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
        const res = await this.draftService.getDraftProposalDetail(record.id);
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
            await this.draftService.reviewDraft(this.state.id, 3, this.state.isApprove, this.state.msg);
            message.success("提交成功")
            await this.noticeService.sendNotice("提案审批", this.state.isApprove ? `${this.state.title}提案审批通过` : `${this.state.title}提案审批不通过,${this.state.msg}`, "13700000002", this.state.author, 0);
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
                title: '提案分类',
                dataIndex: 'proposalType',
                key: 'proposalType',
            }, {
                title: '巨人车ID',
                dataIndex: 'relationId',
                key: 'relationId',
            }, {
                title: '内容',
                dataIndex: 'content',
                key: 'content'
            }, {
                title: '详细信息',
                render: (value, record) => <a onClick={() => this.showModal(record)}>查看详情</a>,
                width: '10%'
            }];
        return (
            <div>
                <CustomBreadcrumb arr={['内容审核', '提案']}/>
                <Card bordered={false} title='提案' style={{marginBottom: 10, minHeight: 440}}>
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
                        <Divider style={{color: "red"}} plain={false}>提案区</Divider>
                        <div>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"基本信息"}</div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"巨人车ID"}</div>
                                            <div>{this.state.relationId}</div>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle" align="start">
                                            <div style={{fontSize: "15px"}}>{"内容"}</div>
                                            <div>{this.state.content}</div>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"可见性"}</div>
                                            <div>{visibilities[this.state.visibility]}</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"投票截止时间"}</div>
                                            <div>{formatDate(this.state.voteEndTime)}</div>
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
                                            <div style={{fontSize: "15px"}}>{"画师名"}</div>
                                            <div>{this.state.painterName}</div>
                                        </Space>
                                    </div>
                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"画师例图"}</div>
                                            <img src={this.state.painterSampleUrl}/>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"工期"}</div>
                                            <div>{this.state.workPeriod} 天</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"完成度"}</div>
                                            <div>{this.state.completionDegree} %</div>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"价格"}</div>
                                            <div>{(this.state.price / 100).toLocaleString()} 电子</div>
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

export default ContentReviewProposalPage

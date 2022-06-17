import React from 'react'
import {Table, Button, Space, Tag, Input, Tooltip, Modal, Divider, Image} from 'antd';
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import {Card, message, Radio} from "antd";
import {api} from '../../../services/api/ApiProvider'

const {TextArea} = Input;


// var timestamp = (new Date()).valueOf();
// var max = timestamp + 5000
// var min = timestamp - 4000

class CertificationUserPage extends React.Component {
    // 创建对象
    bidformService = api.bidformService;
    state = {
        isModalShow: false,
        // 审核Card 内的 字段
        isApprove: true,
        approveMsg: "",
        loading: false,
        data: [],
        rebid: false,
        // 详细信息内的字段，不包含Card
        detail: {},
    }

    async componentDidMount() {
        try {
            let bidformItems = (await this.bidformService.getCertificationMasterpieces()).userMasterpieceList;
            this.setState({
                data: bidformItems,
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
            console.log(e)
        }
    }

    // 审核页面
    showModal = async (record) => {
        this.setState({
            isModalShow: true,
            detail: record
        });
        if (record.status !== 0) {
            this.setState({
                rebid: true
            })
        }
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
            approveMsg: e.target.value
        })
    };

    submitValue = async () => {
        // 按钮状态设置未loading
        this.setState({
            loading: true
        })
        // 信息提交
        try {
            await this.bidformService.submitCertificationMasterpieces({
                id: this.state.detail.id,
                isApprove: this.state.isApprove,
                approveMsg: this.state.approveMsg,
            })
            message.success("提交成功")
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
                title: "ID",
                dataIndex: "id",
                key: 'id',
                ellipsis: true,
            },
            {
                title: '申请者',
                dataIndex: 'username',
                key: 'username',
                ellipsis: true,
            },
            {
                title: '审核状态',
                dataIndex: 'status',
                key: 'status',
                filters: [
                    {text: '未审核', value: 0},
                    {text: '已通过', value: 1},
                    {text: '已驳回', value: 2}
                ],
                render: (text, record) => (
                    <Space size="middle">
                        <Tag key={record.key}
                             color={record.status >= 2 ? "volcano" : record.status === 0 ? "geekblue" : "green"}>
                            {record.status >= 2 ? "已驳回" : record.status === 0 ? "未审核" : "已通过"}</Tag>
                    </Space>
                ),
                onFilter: (value, record) => record.status === value,
            },
            {
                title: '处理原因',
                dataIndex: 'approveMsg',
                key: 'approveMsg',
                ellipsis: {
                    showTitle: false,
                },
                render: approveMsg => (
                    <Tooltip placement="topLeft" title={approveMsg}>
                        {approveMsg}
                    </Tooltip>
                ),
            },
            // {
            //     title: '截止时间',
            //     dataIndex: 'deadline',
            //     key: 'deadline',
            //     filteredValue: filteredInfo.deadline || null,
            //     onFilter: (value, record) => record.deadline.includes(value),
            //     sorter: (a, b) => a.deadline - b.deadline,
            //     sortOrder: sortedInfo.columnKey === 'updateTime' && sortedInfo.order,
            //     ellipsis: true,
            //     render: (value, record) => (
            //         record.audit == 0 && timestamp > record.deadline ?
            //             <Space size="middle">
            //                 {record.deadline}
            //                 <Tag key={record.key} color={"volcano"}>
            //                     {`已超时`}
            //                 </Tag>
            //             </Space>
            //             : <Space size="middle">
            //                 {record.deadline}
            //             </Space>
            //     )
            // },
            {
                title: '详细信息',
                render: (value, record) => <a onClick={() => this.showModal(record)}>查看详情</a>,
                width: '10%'
            },
        ];
        return (
            <div>
                <CustomBreadcrumb arr={['分区审核', '代表作']}/>
                {/* <TypingCard title={"友情提示"} source={"这里提供竞价区角色审核功能<br/>"}/> */}
                <Card bordered={false} title='代表作' style={{marginBottom: 10, minHeight: 440}}>
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
                        <div>
                            <Divider style={{color: "red"}} plain={false}>{this.state.detail.certificationType}区</Divider>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"作品资料"}</div>
                                    <div>
                                        <Space align="start" size="middle">
                                            <a>{this.state.detail.imageZipUrl}</a>
                                        </Space>
                                    </div>

                                    <div>
                                        <Space size="middle" align="start">
                                            <div style={{fontSize: "15px"}}>{"作品图*"}</div>
                                            <Image width={100} height={120} src={this.state.detail.coverUrl}/>
                                        </Space>
                                    </div>

                                    <Space size="middle">
                                        <Space size="middle" style={{width: "200px"}}>
                                            <div style={{fontSize: "15px"}}>{"作品标题*"}</div>
                                            <div>{this.state.detail.title}</div>
                                        </Space>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"英文标题*"}</div>
                                            <div>{this.state.detail.enTitle}</div>
                                        </Space>
                                    </Space>

                                    <Space size="middle">
                                        <div style={{fontSize: "15px"}}>{"作品类型*"}</div>
                                        <div>{this.state.detail.pieceType}</div>
                                    </Space>

                                    <Space size="middle">
                                        <Space size="middle" style={{width: "200px"}}>
                                            <div style={{fontSize: "15px"}}>{"创作风格"}</div>
                                            <div>{this.state.detail.pieceStyle}</div>
                                        </Space>
                                        <Space size="middle">
                                            <div style={{fontSize: "15px"}}>{"创作日期"}</div>
                                            <div>{this.state.detail.pieceDate}</div>
                                        </Space>
                                    </Space>

                                    <Space size="small" direction="vertical">
                                        <div style={{fontSize: "15px"}}>{"作品简介/文设创作心路"}</div>
                                        <div style={{fontSize: "10px"}}>{this.state.detail.description}</div>
                                    </Space>

                                </Space>
                            </div>
                        </div>

                        <div>
                            <Divider plain={false}/>
                            <div>
                                <Space direction="vertical">
                                    <div style={{fontSize: "20px"}}>{"授权证明"}</div>
                                    <div>
                                        <Space align="start" size="middle">
                                            <a>{this.state.detail.authorizationUrl}</a>
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
                            {/* 局部遮罩层 */}
                            {
                                this.state.rebid &&
                                <div style={{
                                    width: "400px",
                                    height: "100%",
                                    backgroundColor: "rgba(0,0,0,0.5)",
                                    zIndex: 999,
                                    position: "absolute",
                                    top: 0,
                                    left: 0,
                                    textAlign: 'center'
                                }}>
                                    <Space size="small" style={{textAlign: "center", height: "100%"}}>
                                        <Button type="primary" danger onClick={() => {
                                            this.setState({rebid: !this.state.rebid})
                                        }}>{"重新审核"}</Button>
                                    </Space>
                                </div>
                            }

                            <Space size="small" direction="vertical" align={'center'}>
                                <Space size="small">
                                    <div style={{fontSize: "10px"}}>{"截止时间"}</div>
                                    <div style={{fontSize: "10px"}}>{"2020/10/2"}</div>
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
                                        <TextArea value={this.state.approveMsg} size="large" rows={3}
                                                  onChange={(e) => this.reasonValueOnchange(e)}
                                                  style={{marginRight: "35px"}}/>
                                    </Space>
                                }
                                {/* {
                                    this.state.resultValue==1&&
                                    <Space size="small">
                                    <div style={{ fontSize: "15px" }}>{"开放区域"}</div>
                                    <Radio.Group  defaultValue={1} onChange={(e)=>this.partitionValueOnchange(e)}>
                                        <Radio value={1}>{"竞价区"}</Radio>
                                        <Radio value={2}>{"素材区"}</Radio>
                                        <Radio value={3}>{"盒蛋区"}</Radio>

                                    </Radio.Group>
                                </Space>
                                } */}

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

export default CertificationUserPage
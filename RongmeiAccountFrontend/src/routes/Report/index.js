import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import {Card, Table, message, Button, Modal, Form, Input, Tag, Select, Space, Descriptions} from "antd";
import {api} from '../../services/api/ApiProvider'
import {formatDate} from "../../utils/utils";

class ReportPage extends React.Component {
    reportService = api.reportService;
    mallService = api.mallService;

    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '理由',
            dataIndex: 'reason',
            key: 'reason',
        }, {
            title: '描述',
            dataIndex: 'description',
            key: 'description',
        }, {
            title: '申报人',
            dataIndex: 'username',
            key: 'username',
        }, {
            title: '创建时间',
            dataIndex: 'createTime',
            key: 'createTime',
            render: (createTime, data) => {
                return formatDate(createTime);
            },
        }, {
            title: '操作',
            render: (text, data) => {
                return <Space size="middle">
                    <a onClick={async () => {
                        const saleDetail = await this.mallService.getSale(data.relationId)
                        await this.setState({
                            id: data.id,
                            reason: data.reason,
                            description: data.description,
                            username: data.username,
                            imageUrls: data.imageUrls,
                            saleId: data.relationId,
                            saleThing: saleDetail.thing
                        })
                        this.showModal()
                    }}>查看详情</a>
                </Space>
            },
        }];

    state = {
        loading: false,
        data: [],
        isModalShow: false,
        page: 1,
        limit: 9999,

        id: 0,
        reason: "",
        description: "",
        username: "",
        imageUrls: [],
        saleId: 0,
        saleThing: {}
    }

    async componentDidMount() {
        try {
            let reportItemList = (await this.reportService.getReport(this.state.page, this.state.limit)).reportItemList;
            this.setState({
                data: reportItemList
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    showModal = async () => {
        this.setState({
            isModalShow: true,
        });
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['举报', '列表']}/>
                <Modal
                    title="举报详情"
                    visible={this.state.isModalShow}
                    onOk={this.submitModal}
                    onCancel={this.hideModal}
                    okText="确认"
                    cancelText="取消"
                >
                    <Descriptions title={this.state.id}>
                        <Descriptions.Item label="理由">{this.state.reason}</Descriptions.Item>
                        <Descriptions.Item label="描述">{this.state.description}</Descriptions.Item>
                        <Descriptions.Item label="申报人">{this.state.username}</Descriptions.Item>
                        <Descriptions.Item label="拍卖ID">{this.state.saleId}</Descriptions.Item>
                        <Descriptions.Item label="拍卖品名">{this.state.saleThing.name}</Descriptions.Item>
                    </Descriptions>
                    <h2>证明截图</h2>
                    {
                        this.state.imageUrls.map((imageUrl) => (
                            <img src={imageUrl} style={{width: '100px', height: '100px'}}/>
                        ))
                    }
                </Modal>
                <Card bordered={false} title='举报列表' style={{marginBottom: 10, minHeight: 440}}>
                    <Table loading={this.state.loading}
                           dataSource={this.state.data}
                           columns={this.columns} style={styles.tableStyle}/>
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

export default ReportPage

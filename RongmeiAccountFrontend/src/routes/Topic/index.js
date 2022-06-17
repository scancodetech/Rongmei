import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import {Button, Card, Divider, Image, Input, message, Modal, Radio, Space, Table, Tag, Upload} from "antd";
import {api} from '../../services/api/ApiProvider'
import {LoadingOutlined, PlusOutlined} from '@ant-design/icons';
import {COMMON_SERVICE, httpDNS} from "../../services/api/HttpService";

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

class TopicPage extends React.Component {
    topicService = api.topicService;
    accountService = api.accountService;

    state = {
        imageUploading: false,
        loading: false,
        data: [],
        isModalShow: false,
        limit: 10000,
        offset: 0,
        total: 0,
        uploadUrl: "",

        topic: "",
        coverUrl: "",
        description: ""
    }

    async componentDidMount() {
        try {
            let baseUrl = await httpDNS.get(COMMON_SERVICE);
            this.setState({
                uploadUrl: baseUrl + "/upload"
            })

            const res = await this.topicService.getTopTopics(1, 10000);
            let topicItems = res.topicItems;
            this.setState({
                data: topicItems,
                total: res.count
            })
        } catch (e) {
            console.log(e)
            message.error("加载失败，请检查网络后重试~")
        }
    }

    showModal = async (record) => {
        this.setState({
            isModalShow: true
        });
        const res = await this.topicService.getTopicDetail(record.topic);
        this.setState({
            ...res
        });
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    descriptionOnChange = (e) => {
        this.setState({
            description: e.target.value
        })
    };

    handleImageChange = info => {
        if (info.file.status === 'uploading') {
            this.setState({imageUploading: true});
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            console.log(info.file)
            this.setState({
                coverUrl: info.file.response.url
            })
        }
    };

    submitValue = async () => {
        // 按钮状态设置未loading
        this.setState({
            loading: true
        })
        // 信息提交
        try {
            await this.topicService.updateTopic({
                topic: this.state.topic,
                coverUrl: this.state.coverUrl,
                description: this.state.description
            });
            message.success("提交成功");
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
                title: '话题',
                dataIndex: 'topic',
                key: 'topic',
            }, {
                title: '热度',
                dataIndex: 'count',
                key: 'count'
            }, {
                title: '修改信息',
                render: (value, record) => <a onClick={() => this.showModal(record)}>修改信息</a>,
                width: '10%'
            }];
        return (
            <div>
                <CustomBreadcrumb arr={['话题管理']}/>
                <Card bordered={false} title='话题管理' style={{marginBottom: 10, minHeight: 440}}>
                    <Table columns={columns} dataSource={this.state.data} onChange={this.handleChange}/>
                    <Modal
                        title={null}
                        visible={this.state.isModalShow}
                        onOk={this.submitValue}
                        onCancel={this.hideModal}
                        centered
                        style={{overflow: "auto"}}
                    >
                        <Space direction="vertical">
                            <div>
                                <Space size="middle">
                                    <div style={{fontSize: "15px"}}>#{this.state.topic}</div>
                                </Space>
                            </div>
                            <div>
                                <Space size="middle" align="start">
                                    <div style={{fontSize: "15px"}}>{"封面图"}</div>
                                    <Upload
                                        name="file"
                                        listType="picture-card"
                                        className="avatar-uploader"
                                        showUploadList={false}
                                        action={this.state.uploadUrl}
                                        onChange={this.handleImageChange}
                                    >
                                        {this.state.coverUrl.length > 0 ?
                                            <img src={this.state.coverUrl} alt="avatar" style={{width: '100%'}}/> :
                                            <div>
                                                {this.state.imageUploading ? <LoadingOutlined/> : <PlusOutlined/>}
                                                <div className="ant-upload-text">上传</div>
                                            </div>}
                                    </Upload>
                                </Space>
                            </div>
                            <div>
                                <Space size="middle" align="start">
                                    <div style={{fontSize: "15px"}}>{"简介"}</div>
                                    <TextArea value={this.state.description} onChange={this.descriptionOnChange}
                                              rows={4}/>
                                </Space>
                            </div>

                        </Space>

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

export default TopicPage

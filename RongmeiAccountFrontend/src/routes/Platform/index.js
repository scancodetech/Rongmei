import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import {Card, Table, message, Radio, Button, Modal, Form, Input, Tag, Tooltip, Icon} from "antd";
import {api} from '../../services/api/ApiProvider'

const StatusMap = [
    {
        status: 0,
        value: "开发"
    },
    {
        status: 1,
        value: "内测"
    },
    {
        status: 2,
        value: "公测"
    }
];

class PlatformPage extends React.Component {
    platformService = api.platformService;
    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '平台名',
            dataIndex: 'name',
            key: 'name',
        }, {
            title: 'key',
            dataIndex: 'platformKey',
            key: 'platformKey',
        }, {
            title: '状态',
            dataIndex: 'status',
            key: 'status',
            render: (status, data) => (
                <div>
                    <Radio.Group value={status} buttonStyle="solid" onChange={(e) => {
                        this.submitStatus(data.id, e.target.value)
                    }}>
                        {
                            StatusMap.map((roleItem) => (
                                <Radio.Button value={roleItem.status}>{roleItem.value}</Radio.Button>
                            ))
                        }
                    </Radio.Group>
                </div>
            ),
        }, {
            title: '操作',
            render: (text, data) => {
                return <Button onClick={() => this.showModal(data.id)} icon={"edit"} type={"primary"}>编辑</Button>
            },
        }];

    submitStatus = async (id, status) => {
        try {
            await this.platformService.updatePlatform({
                id: id,
                status: status
            })
            message.success("保存成功")
        } catch (e) {
            message.error("保存失败")
        }
        this.componentDidMount();
    }

    state = {
        loading: false,
        data: [],
        isModalShow: false,

        id: 0,
        name: "",
        status: 0,
        tags: [],
        inputVisible: false,
        inputValue: ''
    }

    async componentDidMount() {
        try {
            let platformItems = (await this.platformService.getPlatforms()).platformItems;
            this.setState({
                data: platformItems
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    showModal = async (id) => {
        if (id) {
            const platformDetail = await this.platformService.getPlatformDetail(id);
            this.setState({
                id: platformDetail.id,
                name: platformDetail.name,
                status: platformDetail.status,
                tags: platformDetail.accessUsernames,
            });
        }
        this.setState({
            isModalShow: true,
        });
    };

    submitModal = async () => {
        try {
            await this.platformService.updatePlatform({
                id: this.state.id,
                name: this.state.name,
                status: this.state.status,
                accessUsernames: this.state.tags
            })
            message.success("添加成功")
            this.componentDidMount();
            this.setState({
                isModalShow: false,
            });
        } catch (e) {
            message.success("添加失败，请重试~")
        }
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    handleClose = (removedTag) => {
        const tags = this.state.tags.filter(tag => tag !== removedTag);
        console.log(tags);
        this.setState({tags});
    }

    showInput = () => {
        this.setState({inputVisible: true}, () => this.input.focus());
    }

    handleInputChange = (e) => {
        this.setState({inputValue: e.target.value});
    }

    handleInputConfirm = () => {
        const state = this.state;
        const inputValue = state.inputValue;
        let tags = state.tags;
        if (inputValue && tags.indexOf(inputValue) === -1) {
            tags = [...tags, inputValue];
        }
        console.log(tags);
        this.setState({
            tags,
            inputVisible: false,
            inputValue: '',
        });
    }

    saveInputRef = input => this.input = input

    render() {
        const {tags, inputVisible, inputValue} = this.state;
        return (
            <div>
                <CustomBreadcrumb arr={['平台', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示平台的各种信息<br/>现可修改平台状态，以方便内部测试与开发<br/>鉴于安全考虑，暂不支持平台删除"}/>
                <Modal
                    title="添加平台信息"
                    visible={this.state.isModalShow}
                    onOk={this.submitModal}
                    onCancel={this.hideModal}
                    okText="确认"
                    cancelText="取消"
                >
                    <Form
                    >
                        <Form.Item label="平台名">
                            <Input
                                value={this.state.name}
                                onChange={(e) => {
                                    this.setState({
                                        name: e.target.value
                                    })
                                }} placeholder="请输入平台名"/>
                        </Form.Item>
                        <Form.Item label="状态" name="status">
                            <Radio.Group
                                value={this.state.status}
                                onChange={(e) => {
                                    this.setState({
                                        status: e.target.value
                                    })
                                }}>
                                {
                                    StatusMap.map((roleItem) => (
                                        <Radio.Button value={roleItem.status}>{roleItem.value}</Radio.Button>
                                    ))
                                }
                            </Radio.Group>
                        </Form.Item>
                        <Form.Item label="内测用户" name="accessUsernames">
                            <div>
                                {tags.map((tag, index) => {
                                    const isLongTag = tag.length > 20;
                                    const tagElem = (
                                        <Tag key={tag} closable afterClose={() => this.handleClose(tag)}>
                                            {isLongTag ? `${tag.slice(0, 20)}...` : tag}
                                        </Tag>
                                    );
                                    return isLongTag ? <Tooltip title={tag} key={tag}>{tagElem}</Tooltip> : tagElem;
                                })}
                                {inputVisible && (
                                    <Input
                                        ref={this.saveInputRef}
                                        type="text"
                                        size="small"
                                        style={{width: 150}}
                                        value={inputValue}
                                        onChange={this.handleInputChange}
                                        onBlur={this.handleInputConfirm}
                                        onPressEnter={this.handleInputConfirm}
                                    />
                                )}
                                {!inputVisible &&
                                <Button size="small" type="dashed" onClick={this.showInput}>+ 添加</Button>}
                            </div>
                        </Form.Item>
                    </Form>
                </Modal>
                <Card bordered={false} title='平台列表' style={{marginBottom: 10, minHeight: 440}}
                      extra={<Button type="primary" onClick={() => {
                          this.setState({
                              id: 0
                          })
                          this.showModal()
                      }}>添加</Button>}>
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

export default PlatformPage

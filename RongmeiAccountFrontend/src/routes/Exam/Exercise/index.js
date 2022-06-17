import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import {Card, Table, message, Radio, Button, Modal, Form, Input, Tag, Tooltip, Icon, Select, Space} from "antd";
import {api} from '../../../services/api/ApiProvider'

class ExamExercisePage extends React.Component {
    examService = api.examService;
    tccService = api.tccService;
    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '分类',
            dataIndex: 'tag',
            key: 'tag',
        }, {
            title: '题干',
            dataIndex: 'question',
            key: 'question',
        }, {
            title: '选项',
            dataIndex: 'selections',
            key: 'selections',
            render: (selections, data) => (
                <div>
                    {selections.split(" ").map((selection => (
                        <Tag color="#108ee9">{selection}</Tag>
                    )))}
                </div>
            ),
        },
        {
            title: '答案',
            dataIndex: 'answer',
            key: 'answer',
        }, {
            title: '操作',
            render: (text, data) => {
                return <Space size="middle">
                    <a onClick={async () => {
                        await this.setState({
                            id: data.id,
                            tag: data.tag,
                            question: data.question,
                            answer: data.answer,
                            selections: data.selections.split(" ")
                        })
                        this.showModal()
                    }}>编辑</a>
                    <a style={{color: 'red'}} onClick={() => {
                        this.deleteItem(data.id)
                    }}>删除</a>
                </Space>
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
        tags: [],

        id: 0,
        tag: "",
        question: "",
        answer: "",
        selections: [],
    }

    async componentDidMount() {
        try {
            let exerciseItems = (await this.examService.getAllExercise()).exerciseItemList;
            this.setState({
                data: exerciseItems
            })
            const examTypeRes = await this.tccService.getTCC('dimension.exam.type');
            const typeTcc = eval(examTypeRes.tccTuple.value);
            this.setState({
                tags: typeTcc
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

    submitModal = async () => {
        try {
            await this.examService.updateExercise(this.state.id, this.state.tag, this.state.question, this.state.answer, this.state.selections.join(" "))
            message.success("添加成功")
            this.componentDidMount();
            this.setState({
                isModalShow: false,
            });
        } catch (e) {
            message.error("添加失败，请重试~")
        }
    };

    deleteItem = async (id) => {
        try {
            await this.examService.deleteExercise(id)
            message.success("删除成功")
            this.componentDidMount();
        } catch (e) {
            message.error("添加失败，请重试~")
        }
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['考试', '题目', '列表']}/>
                <Modal
                    title="编辑题目信息"
                    visible={this.state.isModalShow}
                    onOk={this.submitModal}
                    onCancel={this.hideModal}
                    okText="确认"
                    cancelText="取消"
                >
                    <Form
                    >
                        <Form.Item label="分类">
                            <Select value={this.state.tag} style={{width: 120}} onChange={(value) => {
                                this.setState({
                                    tag: value
                                })
                            }}>
                                {
                                    this.state.tags.map((tag) => <Select.Option value={tag}>{tag}</Select.Option>)
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item label="题干">
                            <Input
                                value={this.state.question}
                                onChange={(e) => {
                                    this.setState({
                                        question: e.target.value
                                    })
                                }} placeholder="请输入题干"/>
                        </Form.Item>
                        <Form.Item label="选项">
                            <Select value={this.state.selections} mode="tags" placeholder="请输入选项（以回车分隔）" onChange={(value) => {
                                this.setState({
                                    selections: value
                                })
                            }}>
                                {
                                    this.state.selections.map((tag) => <Select.Option value={tag}>{tag}</Select.Option>)
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item label="答案">
                            <Select value={this.state.answer} onChange={(value) => {
                                this.setState({
                                    answer: value
                                })
                            }}>
                                {
                                    this.state.selections.map((tag, index) => <Select.Option
                                        value={String.fromCharCode(65 + index)}>{String.fromCharCode(65 + index)}</Select.Option>)
                                }
                            </Select>
                        </Form.Item>
                    </Form>
                </Modal>
                <Card bordered={false} title='习题列表' style={{marginBottom: 10, minHeight: 440}}
                      extra={<Button type="primary" onClick={() => {
                          this.setState({
                              id: 0,
                              tag: "",
                              question: 0,
                              answer: "",
                              selections: [],
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

export default ExamExercisePage
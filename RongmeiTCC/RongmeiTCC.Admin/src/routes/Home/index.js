import React from 'react'
import CustomBreadcrumb from '../../components/CustomBreadcrumb/index'
import TypingCard from '../../components/TypingCard'
import {Button, Card, Col, Form, Input, Row, Tag, Tooltip, Upload, message, Icon, Select, Modal} from "antd";
import {api} from "../../services/api/ApiProvider";
import MonacoEditor from "react-monaco-editor";

export default class Home extends React.Component {
    state = {
        id: 0,
        key: '',
        value: '',
        description: '',
        visible: false,

        tccList: []
    }

    async componentDidMount() {
        try {
            this.setState({
                tccList: (await api.tccService.getTCCs()).tccTupleList
            });
        } catch (e) {

        }
    }

    handleSubmit = async () => {
        try {
            message.success("修改成功");
            this.componentDidMount()
        } catch (e) {
            message.success("修改失败")
        }
    }

    openModal() {
        this.setState({
            visible: true
        })
    }

    closeModel() {
        this.setState({
            visible: false
        })
    }

    async handleOk() {
        try {
            await api.tccService.updateTCC({
                id: this.state.id,
                key: this.state.key,
                value: this.state.value,
                description: this.state.description
            });
            message.success("更新成功");
            this.componentDidMount();
            this.closeModel();
        } catch (e) {
            message.error("更新失败，请检查参数重试")
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={[]}/>
                <TypingCard
                    source={'在这里以key-value的形式自由修改项目相关的动态配置'}
                    title='动态配置中心'
                    extra={<Button type="primary" onClick={() => {
                        this.setState({
                            id: 0
                        })
                        this.openModal()
                    }}>新增</Button>}
                />
                <Modal visible={this.state.visible}
                       onOk={() => this.handleOk()}
                       onCancel={() => this.closeModel()} title="编辑配置">
                    <Form>
                        <Form.Item
                            name='key'
                            label='Key'>
                            <Input value={this.state.key} onChange={(e) => {
                                this.setState({
                                    key: e.target.value
                                })
                            }}/>
                        </Form.Item>
                        <Form.Item
                            name='description'
                            label='简介'>
                            <Input.TextArea rows={4}
                                            value={this.state.description} onChange={(e) => {
                                this.setState({
                                    description: e.target.value
                                })
                            }}/>
                        </Form.Item>
                        <Form.Item
                            name='value'
                            label='配置'>
                            <MonacoEditor
                                height="200"
                                language="json"
                                value={this.state.value}
                                options={{selectOnLineNumbers: true}}
                                onChange={(value, e) => {
                                    this.setState({
                                        value
                                    })
                                }}
                            />
                        </Form.Item>
                    </Form>
                </Modal>
                {
                    this.state.tccList.map((item) => (
                        <Card hoverable title={item.key} style={{marginTop: '10px'}} onClick={() => {
                            this.setState({
                                id: item.id,
                                key: item.key,
                                value: item.value,
                                description: item.description,
                                visible: true
                            })
                        }}>
                            {item.description}
                        </Card>
                    ))
                }
            </div>
        )
    }
}

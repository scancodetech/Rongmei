import React from 'react'
import {Card, Form, Checkbox, Input, Button, Col, Row, message, BackTop, Spin, Radio} from 'antd'
import CustomBreadcrumb from '../../../components/CustomBreadcrumb/index'
import TypingCard from '../../../components/TypingCard'
import {api} from "../../../services/api/ApiProvider";

const FormItem = Form.Item

@Form.create()
class AddPage extends React.Component {
    accessService = api.accessService;

    state = {
        loading: false
    }

    handleSubmit = async (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll(async (err, values) => {
            if (err) {
                message.warning('请先填写正确的表单')
            } else {
                this.setState({
                    loading: true
                });
                try {
                    await this.accessService.updateAccess({
                        name: values.name,
                        operation: 'ADD'
                    })
                    message.success('提交成功')
                    this.props.history.push("/home/access/list")
                } catch (e) {
                    message.error('提交失败，请重试')
                }
                this.setState({
                    loading: false
                });
            }
        });
    };

    render() {
        const {getFieldDecorator} = this.props.form
        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 3},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 20},
            },
        };
        const cardContent = '请根据需要给予客户相应的用户权限，请谨慎填写~';
        return (
            <Spin spinning={this.state.loading} delay={500}>
                <CustomBreadcrumb arr={['权限', '新增']}/>
                <TypingCard title={"友情提示"} source={cardContent}/>
                <Card bordered={false} title='新增权限'>
                    <Form layout='horizontal' style={{width: '70%', margin: '0 auto', textAlign: 'center'}}
                          onSubmit={this.handleSubmit}>
                        <FormItem style={{textAlign: 'center'}} label='权限名称' {...formItemLayout}>
                            {
                                getFieldDecorator('name', {
                                    rules: [
                                        {
                                            required: true,
                                            message: '请填写权限名称'
                                        }
                                    ]
                                })(
                                    <Input/>
                                )
                            }
                        </FormItem>
                        <FormItem style={{textAlign: 'center'}}>
                            <Button type="primary" htmlType="submit">提交</Button>
                        </FormItem>
                    </Form>
                </Card>
                <BackTop visibilityHeight={200} style={{right: 50}}/>
            </Spin>
        )
    }
}

export default AddPage

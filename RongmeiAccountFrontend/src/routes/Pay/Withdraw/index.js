import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import {Card, Table, message, Radio, Button, Modal, Form, Input, Tag, Tooltip, Icon} from "antd";
import {api} from '../../../services/api/ApiProvider'

function formatDate(time) {
    let date = new Date(time);
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = date.getDate() + ' ';
    let h = date.getHours() + ':';
    let m = date.getMinutes() + ':';
    let s = (date.getSeconds() + 1 < 10 ? '0' + (date.getSeconds() + 1) : date.getSeconds() + 1);
    return Y + M + D + h + m + s;
}

class WithdrawPage extends React.Component {
    payService = api.payService;
    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '用户名',
            dataIndex: 'user.phone',
            key: 'user.phone',
        }, {
            title: '昵称',
            dataIndex: 'userInfo',
            key: 'userInfo'
        }, {
            title: '金额',
            dataIndex: 'totalAmount',
            key: 'totalAmount',
            render: totalAmount => (
                <p>{(totalAmount / 100).toLocaleString()}</p>
            ),
        }, {
            title: '请求时间',
            dataIndex: 'createTime',
            key: 'createTime',
            render: (createTime, data) => (
                <div>{formatDate(createTime)}</div>
            ),
        }, {
            title: '状态',
            dataIndex: 'status',
            key: 'status',
            render: (status, data) =>
                status === '未完成' ? <Tag color="#108ee9">{status}</Tag> : <Tag color="#f50">{status}</Tag>
        }, {
            title: '操作',
            render: (text, data) =>
                data.status === '未完成' ?
                    <Button onClick={() => this.payService.doneWithdraw(data.id)} type={"primary"}>完成提现</Button> :
                    <Button type={"primary"} disable>已完成提现</Button>
        }
    ];

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
            let withdrawItems = (await this.payService.getWithdrawList()).withdrawItems;
            this.setState({
                data: withdrawItems
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['财务', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示用户提现请求列表<br/>现仅可查看提现请求情况<br/>鉴于安全考虑，暂不支持删除"}/>
                <Card bordered={false} title='提现列表' style={{marginBottom: 10, minHeight: 440}}>
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

export default WithdrawPage

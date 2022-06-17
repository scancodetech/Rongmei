import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import {Card, Table, message, Tag} from "antd";
import {api} from '../../../services/api/ApiProvider'

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

const PayTypeMap = ["手机", "PC"];

class RechargePage extends React.Component {
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
            dataIndex: 'userInfo.nickname',
            key: 'userInfo.nickname',
        }, {
            title: '金额',
            dataIndex: 'totalAmount',
            key: 'totalAmount',
            render: totalAmount => (
                <p>{(totalAmount / 100).toLocaleString()}</p>
            ),
        }, {
            title: '充值时间',
            dataIndex: 'createTime',
            key: 'createTime',
            render: (createTime, data) => (
                <div>{formatDate(createTime)}</div>
            ),
        }, {
            title: '支付方式',
            dataIndex: 'payType',
            key: 'payType',
            render: (payType, data) => (
                <Tag color="magenta">{PayTypeMap[payType]}</Tag>
            ),
        }];

    state = {
        loading: true,
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
            let rechargeItems = (await this.payService.getRechargeList()).rechargeItems;
            this.setState({
                data: rechargeItems,
                loading: false
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~");
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['财务', '充值列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示用户充值列表<br/>现仅可查看充值情况<br/>鉴于安全考虑，暂不支持删除"}/>
                <Card bordered={false} title='充值列表' style={{marginBottom: 10, minHeight: 440}}>
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

export default RechargePage

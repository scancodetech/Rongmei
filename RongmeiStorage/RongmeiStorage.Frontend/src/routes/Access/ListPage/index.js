import React from 'react'
import CustomBreadcrumb from "../../../components/CustomBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import {Card, Table, message, Switch} from "antd";
import {Link} from "react-router-dom";
import {api} from '../../../services/api/ApiProvider'

class ListPage extends React.Component {
    accessService = api.accessService;
    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '权限名',
            dataIndex: 'name',
            key: 'name',
        }, {
            title: 'AccessKey',
            dataIndex: 'accessKey',
            key: 'accessKey',
        }, {
            title: 'AccessSecret',
            dataIndex: 'accessSecret',
            key: 'accessSecret',
        }, {
            title: '启用',
            dataIndex: 'status',
            key: 'status',
            render: (status, data) => (<Switch checked={status !== 0} onChange={
                () => {
                    this.submitActive(data)
                }
            }/>),
        },];

    state = {
        loading: false,
        data: [],
    }

    submitActive = async (data) => {
        await api.accessService.updateAccess({
            id: data.id,
            name: data.name,
            accessKey: data.accessKey,
            accessSecret: data.accessSecret,
            status: data.status === 0 ? 1 : 0,
            operation: 'UPDATE'
        })
        this.componentDidMount()
    }

    async componentDidMount() {
        try {
            let accessItemList = (await this.accessService.loadAccess()).accessItemList;
            this.setState({
                data: accessItemList
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['权限', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示你授权的所有权限<br/>点击列表中的权限以查看详情"}/>
                <Card bordered={false} title='权限列表' style={{marginBottom: 10, minHeight: 440}}>
                    <Table loading={this.state.loading}
                           dataSource={this.state.data}
                           onChange={this.handleTableChange}
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

export default ListPage

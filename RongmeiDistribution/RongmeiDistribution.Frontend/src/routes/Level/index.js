import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import {Card, Table} from "antd";
import {Link} from "react-router-dom";
import {api} from '../../services/api/ApiProvider'

const columns = [
    {
        title: '分销层级',
        dataIndex: 'level',
        key: 'level',
    }, {
        title: '手机号',
        dataIndex: 'phone',
        key: 'username',
    }, {
        title: '邀请码',
        dataIndex: 'code',
        key: 'code',
    }, {
        title: '总赏金',
        dataIndex: 'scores',
        key: 'scores',
    }];

class Level extends React.Component {
    state = {
        loading: false,
        data: [],
    }

    componentDidMount = async () => {
        try {
            this.setState({
                loading: true,
            })
            let userDistributionRes = await api.distributionService.getUserDistribution();
            this.setState({
                data: userDistributionRes.children,
            })
            this.setState({
                loading: false,
            })
        } catch (e) {
            console.log(e)
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['分销信息']}/>
                <TypingCard title={"友情提示"} source={"这里展示你的所有下级用户<br/>"}/>
                <Card bordered={false} title='活动列表' style={{marginBottom: 10, minHeight: 440}}>
                    <Table loading={this.state.loading}
                           dataSource={this.state.data}
                           onChange={this.handleTableChange}
                           columns={columns} style={styles.tableStyle}/>
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

export default Level

import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import {Button, Card, message} from "antd";
import {api} from '../../services/api/ApiProvider'
import NodeChart from "../../components/NodeChart";
import {Utils} from "@antv/graphin";
import screenfull from "screenfull";

class DataManagement extends React.Component {
    statsService = api.statsService;

    state = {
        loading: false,
        data: Utils.mock(10).circle().graphin()
    }

    componentDidMount = async () => {
        try {
            const res = await this.statsService.loadStats();
            this.setState({
                data: {
                    nodes: res.nodes,
                    edges: res.edges
                }
            })
            window.resizeBy(100, 100)
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['存证', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示链上的所有数据~"}/>
                <Card bordered={false} title='存证列表' extra={<Button onClick={()=>{screenfull.toggle()}} type="primary">点击放大浏览器以查看</Button>}>
                    <NodeChart style={{width: '100%', height: '100%'}} data={this.state.data}/>
                </Card>
            </div>
        )
    }
}

export default DataManagement

import React from 'react'
import {Card, Col, Row, BackTop, Button, Table, Statistic} from 'antd'
import CustomBreadcrumb from '../../components/CustomBreadcrumb/index'
import {api} from "../../services/api/ApiProvider";

class Info extends React.Component {
    state = {
        username: '',
        role: '',
        nickname: '',
        code: '',
        qrcodeUrl: '',
        level: 0,
        scores: 0
    };

    componentDidMount = async () => {
        this.setState({
            loading: true
        });
        try {
            let userRes = await api.accountService.getUser();
            let userInfoRes = await api.accountService.getUserInfo();
            let userDistributionRes = await api.distributionService.getUserDistribution();
            this.setState({
                username: userRes.phone,
                role: userRes.role.name,
                nickname: userInfoRes.nickname,
                code: userDistributionRes.code,
                qrcodeUrl: userDistributionRes.qrcodeUrl,
                level: userDistributionRes.level,
                scores: userDistributionRes.scores
            })
        } catch (e) {
            console.log(e)
        }
        this.setState({
            loading: false
        });
    }

    formatDate = (now) => {
        now = new Date(now);
        const year = now.getFullYear();
        const month = now.getMonth() + 1;
        const date = now.getDate();
        const hour = now.getHours();
        const minute = now.getMinutes();
        const second = now.getSeconds();
        return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['用户信息']}/>
                <Card bordered={false} className='card-item' title={"基础信息"}>
                    <div>
                        <span>手机号：</span>
                        <span>{this.state.username}</span>
                    </div>
                    <div>
                        <span>角色：</span>
                        <span>{this.state.role}</span>
                    </div>
                    <div>
                        <span>昵称：</span>
                        <span>{this.state.nickname && this.state.nickname.length > 0 ? this.state.nickname : '暂无昵称'}</span>
                    </div>
                </Card>
                <Card bordered={false} className='card-item' title='分销信息'>
                    <div style={{display: "inline-block"}}>
                        <div style={{marginBottom: '20px'}}>
                            <Statistic title="邀请码" value={this.state.code}/>
                            <div>
                        <span style={{marginRight: '30px'}}>
                            <Statistic title="分销层级" value={this.state.level}/>
                        </span>
                                <span style={{marginRight: '30px'}}>
                            <Statistic
                                title="奖金总额"
                                value={this.state.scores}
                                precision={2}
                                valueStyle={{color: '#cf1322'}}
                                prefix={'￥'}
                            />
                        </span>
                            </div>
                        </div>
                    </div>
                    <div style={{display: "inline-block", float: 'right'}}>
                        <div>
                            抽奖二维码：
                        </div>
                        <img width={250} src={this.state.qrcodeUrl}/>
                    </div>
                </Card>
            </div>
        )
    }
}

export default Info

import React, {useRef, useEffect} from 'react'
import './style.css'
import {BackTop, Card, Carousel, Col, Form, Input, List, Radio, Row, Spin} from "antd";
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import BarChart from "../../components/BarChart";
import SliderChart from "../../components/SliderChart";
import {api} from "../../services/api/ApiProvider";

class Home extends React.Component {
    statsService = api.statsService;

    state = {
        monthData: [],
        minuteData: []
    }

    async componentDidMount() {
        const res = await this.statsService.loadHomeStats();
        let monthData = [];
        Date.prototype.format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1,                 //月份
                "d+": this.getDate(),                    //日
                "h+": this.getHours(),                   //小时
                "m+": this.getMinutes(),                 //分
                "s+": this.getSeconds(),                 //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        }

        for (let i = 0; i < res.monthStatsCount.length; i++) {
            monthData = monthData.concat({
                time: new Date(res.monthStatsCount[i].time).format("yyyy-MM"),
                num: res.monthStatsCount[i].num
            })
        }
        let minuteData = [];
        for (let i = 0; i < res.minuteStatsCount.length; i++) {
            minuteData = minuteData.concat({
                time: new Date(res.minuteStatsCount[i].time).format("hh:mm"),
                num: res.minuteStatsCount[i].num
            })
        }
        this.setState({
            monthData: monthData,
            minuteData: minuteData
        })
    }

    render() {
        const cardContent = '具备全链路可信，保障数据完整性、不可篡改性，实现跨主体信息共享及一致，创造信任新环境。<br/> 融梅区块链数字存证平台是电子数据存证的一站式服务平台，依托于崇新科技丰富的区块链技术研究及落地实践经验，适用于多种应用场景，可将电子协议、电子合同、订单、邮件、网页等信息进行存证，通过区块链、哈希验证、电子签名、可信时间戳等技术增强电子文件法律效力。';
        const listData = [
            {
                href: 'http://rongmeitech.com/map/',
                title: `融梅地图平台`,
                description:
                    '评论上链',
                content:
                    '融梅地图平台涵盖全方位的高端融媒体企业信息，为保护优质的客户信息，精选优质评论上链存储，存证企业信息及变更细节，全方位保护信息安全',
            }
        ];
        return (
            <div>
                <CustomBreadcrumb/>
                <TypingCard title={"融梅区块链可信存证"} source={cardContent}/>
                <Row gutter={16}>
                    <Col span={6}>
                        <Card style={style.cardShow} hoverable>
                            <img src={require('../../assets/img/11.png')}/>
                            <h1 style={style.cardTitle}>简单易用</h1>
                            <p style={style.cardContent}>无需关心区块链底层细节，即可快速实现基于区块链的数据存证、数据共享。</p>
                        </Card>
                    </Col>
                    <Col span={6}>
                        <Card style={style.cardShow}>
                            <img src={require('../../assets/img/22.png')}/>
                            <h1 style={style.cardTitle}>灵活开放</h1>
                            <p style={style.cardContent}>可以自选使用平台的各类功能，并自由选择部署平台节点，适应各类中小型需求。</p>
                        </Card>
                    </Col>
                    <Col span={6}>
                        <Card style={style.cardShow}>
                            <img src={require('../../assets/img/33.png')}/>
                            <h1 style={style.cardTitle}>多场景适用</h1>
                            <p style={style.cardContent}>合同、邮件、语音、图片等各类数据均可存证，在多行业、多领域均可适用。</p>
                        </Card>
                    </Col>
                    <Col span={6}>
                        <Card style={style.cardShow}>
                            <img src={require('../../assets/img/44.png')}/>
                            <h1 style={style.cardTitle}>可信保障</h1>
                            <p style={style.cardContent}>利用电子签名、可信时间戳、哈希、区块链等技术保障完整可信、不可篡改。</p>
                        </Card>
                    </Col>
                </Row>
                <Card style={style.cardShow}>
                    <h1 style={style.cardTitle}>服务数据</h1>
                    <div style={style.cardContent}>
                        <div>
                            <BarChart data={this.state.monthData}/>
                        </div>
                        <div>
                            <SliderChart data={this.state.minuteData}/>
                        </div>
                    </div>
                </Card>
                <Card style={style.cardShow}>
                    <h1 style={style.cardTitle}>典型场景应用案例</h1>
                    <div style={style.cardContent}>
                        <List
                            itemLayout="vertical"
                            size="large"
                            dataSource={listData}
                            renderItem={item => (
                                <List.Item
                                    key={item.title}
                                    extra={
                                        <img
                                            width={272}
                                            alt="logo"
                                            src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                                        />
                                    }
                                >
                                    <List.Item.Meta
                                        title={<a href={item.href}>{item.title}</a>}
                                        description={item.description}
                                    />
                                    {item.content}
                                </List.Item>
                            )}
                        />
                    </div>
                </Card>
                <BackTop visibilityHeight={200} style={{right: 50}}/>
            </div>
        )
    }
}

const style = {
    cardShow: {
        textAlign: 'center',
        marginTop: '20px'
    },
    cardTitle: {
        fontSize: '20px',
        marginTop: '20px'
    },
    cardContent: {
        color: "#666",
        marginTop: '20px'
    }
}

export default Home

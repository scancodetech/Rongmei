import React, {useRef, useEffect} from 'react'
import './style.css'
import {BackTop, Card, Carousel, Col, Form, Input, List, Radio, Row, Spin} from "antd";
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";

class Home extends React.Component {

    render() {
        const cardContent = '全平台统一<br/>全链路执行<br/>全公司基层的用户系统';
        const listData = [
            {
                href: 'http://rongmeitech.com/map',
                title: `融梅地图平台`,
                description:
                    '用户接入',
                content:
                    '用户接入',
            }
        ];
        return (
            <div>
                <CustomBreadcrumb/>
                <TypingCard title={"融梅用户管理平台"} source={cardContent}/>
                <Card style={style.cardShow}>
                    <h1 style={style.cardTitle}>内部使用场景</h1>
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

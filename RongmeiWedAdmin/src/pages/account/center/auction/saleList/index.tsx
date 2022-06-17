import React, { Component } from 'react';

import { List, Tabs, Skeleton, Row, Col, Image, Space, Typography, Table, Modal, Button } from 'antd';
import auctionStyles from "@/pages/account/center/auction/auction.less";
import {
    DeleteTwoTone, EyeTwoTone, FormOutlined, HeartTwoTone, RollbackOutlined, ShareAltOutlined
} from '@ant-design/icons';
import { getMineSales, getSaleView, getSaleDownload, getSale, getSaleHistory, deleteSale } from "@/services/auction";
import { message } from "antd";
import { getMineCertification } from "@/services/apply";
import commodityStyles from "@/pages/account/center/commodity/commodity.less";
import styles from "@/components/ItemList/index.less";
import { formatDate } from "@/utils/utils";
import { getDraftReason } from '@/services/draft';
import { discount } from "@/services/user";
import { copyToClip } from '@/utils/utils'
const { TabPane } = Tabs;
const { Paragraph } = Typography;

const historyColumn = [
    {
        title: '出价人',
        dataIndex: 'username',
        key: 'username',
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
    },
    {
        title: '出价',
        dataIndex: 'price',
        key: 'price',
    }
];

class AccountCenterAuctionSaleList extends Component<any> {

    state = {
        limit: 10,
        sales: [],
        status: "2",
        loading: false,
        isConfirmRollbackVisible: false,
        isConfirmDeleteVisible: false,

        id: 0,
        startPrice: 0,
    }

    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }

    async componentDidMount() {
        await this.checkCertificationType("竞品类");
        this.getData(parseInt(this.state.status));
    }

    async getData(status: number) {
        let res = await getMineSales(status);
        this.setState({
            loading: true,
        })
        if (this.state.status === '1') {

        }
        for (let index = 0; index < res.saleItems.length; index++) {
            let saleRes = await getSale(res.saleItems[index].id);
            let historyRes = await getSaleHistory(res.saleItems[index].id);
            let viewRes = await getSaleView(res.saleItems[index].id);
            let downloadRes = await getSaleDownload(res.saleItems[index].id);
            let draftReasonRes = await getDraftReason(res.saleItems[index].id, 1);
            res.saleItems[index].watch = viewRes.count;
            res.saleItems[index].collect = downloadRes.count;
            res.saleItems[index].currPrice = saleRes.currPrice;
            res.saleItems[index].reason = draftReasonRes.msg;
            let historyItems: any[] = [];
            for (let i = 0; i < historyRes.auctionHistories.length; i++) {
                historyItems = historyItems.concat({
                    username: historyRes.auctionHistories[i].user.username,
                    createTime: historyRes.auctionHistories[i].createTime,
                    price: historyRes.auctionHistories[i].price,
                })
            }
            res.saleItems[index].historyItems = historyItems;
        }
        this.setState({
            sales: res.saleItems,
            loading: false
        })
    }

    deleteSale = async (id: number) => {
        const res = await deleteSale(id);
        if (res.infoCode === 10000) {
            message.success("删除成功")
            this.getData(parseInt(this.state.status))
        } else {
            message.error("删除失败")
        }
    }

    rollbackSale = async (id: number) => {
        const moneyToDiscount = Math.ceil(this.state.startPrice * 0.03);
        const discountRes = await discount(moneyToDiscount);
        if (discountRes.infoCode === 10000) {
            const res = await deleteSale(id);
            if (res.infoCode === 10000) {
                message.success("回收成功")
                this.getData(parseInt(this.state.status))
            } else {
                message.error("回收失败")
            }
        } else {
            message.error("回收失败，积分不足")
        }
    }

    openRollbackModal = () => {
        this.setState({
            isConfirmRollbackVisible: true
        })
    }

    closeRollbackModal = () => {
        this.setState({
            isConfirmRollbackVisible: false
        })
    }

    openDeleteModal = () => {
        this.setState({
            isConfirmDeleteVisible: true
        })
    }

    closeDeleteModal = () => {
        this.setState({
            isConfirmDeleteVisible: false
        })
    }

    render() {
        // 导航栏key
        return (
            <div className={auctionStyles.listContainer}>
                <div className={auctionStyles.headTitle}>
                    {"竞价列表"}
                </div>
                <Modal title={null} footer={null} visible={this.state.isConfirmRollbackVisible}
                    onCancel={this.closeRollbackModal}>
                    <div style={{ textAlign: 'center', marginTop: '40px', marginBottom: '30px', fontSize: '16px' }}>
                        <p>确认回收该竞品么？</p>
                        <p>回收会收取<span style={{ color: 'red' }}>起拍价3%</span>的手续费</p>
                    </div>
                    <div style={{ textAlign: 'center' }}>
                        <Button
                            style={{
                                width: '120px',
                                borderColor: '#cecece',
                                borderRadius: '25px',
                                margin: '5px'
                            }}
                            size={"large"}
                            type="primary" htmlType="submit"
                            ghost onClick={() => {
                                this.closeRollbackModal();
                            }}>
                            取消
                        </Button>
                        <Button
                            style={{
                                width: '120px',
                                borderColor: '#cecece',
                                borderRadius: '25px',
                                margin: '5px'
                            }}
                            size={"large"}
                            type="primary" htmlType="submit"
                            onClick={async () => {
                                await this.rollbackSale(this.state.id);
                                this.closeRollbackModal();
                            }}>
                            确认回收
                        </Button>
                    </div>
                </Modal>
                <Modal title={null} footer={null} visible={this.state.isConfirmDeleteVisible}
                    onCancel={this.closeDeleteModal}>
                    <div style={{ textAlign: 'center', marginTop: '40px', marginBottom: '30px', fontSize: '16px' }}>
                        <p>确认删除该竞品么？</p>
                        <p>删除操作无法撤销</p>
                    </div>
                    <div style={{ textAlign: 'center' }}>
                        <Button
                            style={{
                                width: '120px',
                                borderColor: '#cecece',
                                borderRadius: '25px',
                                margin: '5px'
                            }}
                            size={"large"}
                            type="primary" htmlType="submit"
                            ghost onClick={() => {
                                this.closeDeleteModal();
                            }}>
                            取消
                        </Button>
                        <Button
                            style={{
                                width: '120px',
                                borderColor: '#cecece',
                                borderRadius: '25px',
                                margin: '5px'
                            }}
                            size={"large"}
                            type="primary" htmlType="submit"
                            onClick={async () => {
                                await this.deleteSale(this.state.id);
                                this.closeDeleteModal();
                            }}>
                            确认删除
                        </Button>
                    </div>
                </Modal>
                {!this.state.loading ? (
                    <Tabs activeKey={this.state.status} className={commodityStyles.tabsChange}
                        onChange={(activeKey) => {
                            this.setState({
                                status: activeKey
                            })
                            this.getData(parseInt(activeKey));
                        }
                        }>
                        <TabPane tab="已发布" key="2">
                            <List
                                itemLayout="vertical"
                                pagination={{
                                    pageSize: this.state.limit
                                }}
                                dataSource={this.state.sales}
                                renderItem={(item: any, dataIndex) => (
                                    <List.Item
                                        key={dataIndex}
                                    >
                                        <Row gutter={[8, 0]} justify='start'>
                                            <Col span={8}>
                                                <Image src={item.thing ? item.thing.url : ''}
                                                    height={250}
                                                    placeholder={true}
                                                    style={{ borderRadius: 9 }}
                                                />
                                            </Col>
                                            <Col span={16}>
                                                <div style={{ height: 250 }} className={styles.itemBg}>
                                                    <div style={{ height: 80 }}>
                                                        <div
                                                            className={styles.itemName}>{item.thing ? item.thing.name : ''}</div>
                                                        <Paragraph
                                                            style={{ width: '100%' }}
                                                            ellipsis={{
                                                                rows: 1,
                                                                expandable: false,

                                                            }}
                                                        >
                                                            {item.thing ? item.thing.description : ''}
                                                        </Paragraph>
                                                    </div>
                                                    <div style={{ height: 60 }}>
                                                        <Space size='large' style={{ height: 60 }}>
                                                            <Space>
                                                                <Space size='small' style={{ fontSize: 20 }}><EyeTwoTone />
                                                                </Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 20 }}>{item.watch}</Space>
                                                            </Space>
                                                            <Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 20 }}><HeartTwoTone />
                                                                </Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 20 }}>{item.collect}</Space>
                                                            </Space>
                                                        </Space>
                                                    </div>
                                                    <div style={{ height: 60 }}>
                                                        <Space size='large' style={{ height: 60 }} align='start'>
                                                            <Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 16, color: '#398DFF' }}>价格：
                                                                </Space>
                                                                <Space size='small' style={{
                                                                    fontSize: 16,
                                                                    color: '#398DFF'
                                                                }}>{(item.currentPrice / 100).toLocaleString() + "电子"}</Space>
                                                            </Space>
                                                            <Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 16 }}>{formatDate(item.createTime)}
                                                                </Space>
                                                            </Space>
                                                        </Space>
                                                    </div>
                                                    <div className={styles.btnBg}>
                                                        <div className={styles.btnItem} onClick={() => {
                                                            this.props.history.push(`/account/center/auction/sale/${item.id}`)
                                                        }}>
                                                            <FormOutlined className={styles.itemIcon} />
                                                            <div className={styles.itemText}>{"重新上传"}</div>
                                                        </div>
                                                        <div className={styles.btnItem} onClick={() => {
                                                            this.setState({
                                                                startPrice: item.startPrice,
                                                                id: item.id
                                                            })
                                                            this.openRollbackModal();
                                                        }}>
                                                            <RollbackOutlined style={{ color: 'red' }}
                                                                className={styles.itemIcon} />
                                                            <div className={styles.itemText}
                                                                style={{ color: 'red' }}>{"回收"}</div>
                                                        </div>
                                                        <div className={styles.btnItem}>
                                                            <ShareAltOutlined className={styles.itemIcon} />
                                                            <div className={styles.itemText}
                                                                onClick={() => {
                                                                    try {
                                                                        copyToClip("https://m.dimension.pub/#/picmobile/sale/" + item.id)
                                                                        message.success('链接复制成功！')
                                                                    } catch (e) {
                                                                        message.error('链接复制失败！')
                                                                    }
                                                                }}>{"分享"}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </Col>
                                        </Row>
                                        <Table
                                            style={{ margin: '20 10' }}
                                            columns={historyColumn}
                                            pagination={false}
                                            dataSource={item.historyItems}>
                                        </Table>
                                    </List.Item>
                                )}
                            />
                        </TabPane>
                        <TabPane tab="审核中" key="0">
                            <List
                                itemLayout="vertical"
                                pagination={{
                                    pageSize: this.state.limit
                                }}
                                dataSource={this.state.sales}
                                renderItem={(item: any, dataIndex) => (
                                    <List.Item
                                        key={dataIndex}
                                    >
                                        <Row gutter={[8, 0]} justify='start'>
                                            <Col span={8}>
                                                <Image src={item.thing ? item.thing.url : ''}
                                                    height={250}
                                                    placeholder={true}
                                                    style={{ borderRadius: 9 }}
                                                />
                                            </Col>
                                            <Col span={16}>
                                                <div style={{ height: 250 }} className={styles.itemBg}>
                                                    <div style={{ height: 80 }}>
                                                        <div
                                                            className={styles.itemName}>{item.thing ? item.thing.name : ''}</div>
                                                        <Paragraph
                                                            style={{ width: '100%' }}
                                                            ellipsis={{
                                                                rows: 1,
                                                                expandable: false,

                                                            }}
                                                        >
                                                            {item.thing ? item.thing.description : ''}
                                                        </Paragraph>
                                                    </div>
                                                    <div style={{ height: 60 }}>
                                                        <Space size='large'>
                                                            <Space size='middle'
                                                                style={{ fontSize: 20, color: '#398DFF' }}>
                                                                审核中
                                                            </Space>
                                                        </Space>
                                                    </div>
                                                    <div style={{ height: 60 }}>
                                                        <Space size='large' style={{ height: 60 }} align='start'>
                                                            <Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 16, color: '#398DFF' }}>价格：
                                                                </Space>
                                                                <Space size='small' style={{
                                                                    fontSize: 16,
                                                                    color: '#398DFF'
                                                                }}>{(item.currentPrice / 100).toLocaleString() + "电子"}</Space>
                                                            </Space>
                                                            <Space>
                                                                <Space size='small'
                                                                    style={{ fontSize: 16 }}>{formatDate(item.createTime)}
                                                                </Space>
                                                            </Space>
                                                        </Space>
                                                    </div>
                                                </div>
                                            </Col>
                                        </Row>
                                    </List.Item>
                                )}
                            />
                        </TabPane>
                        <TabPane tab="草稿箱" key="1">
                            <List
                                itemLayout="vertical"
                                pagination={{
                                    pageSize: this.state.limit
                                }}
                                dataSource={this.state.sales}
                                renderItem={(item: any, dataIndex) => (
                                    <List.Item
                                        key={dataIndex}
                                    >
                                        <Row gutter={[8, 0]} justify='start'>
                                            <Col span={8}>
                                                <Image src={item.thing ? item.thing.url : ''}
                                                    height={250}
                                                    placeholder={true}
                                                    style={{ borderRadius: 9 }}
                                                />
                                            </Col>
                                            <Col span={16}>
                                                <div style={{ height: 250 }} className={styles.itemBg}>
                                                    <div style={{ height: 80 }}>
                                                        <div
                                                            className={styles.itemName}>{item.thing ? item.thing.name : ''}</div>
                                                        <Paragraph
                                                            style={{ width: '100%' }}
                                                            ellipsis={{
                                                                rows: 1,
                                                                expandable: false,
                                                            }}
                                                        >
                                                            {item.thing ? item.thing.description : ''}
                                                        </Paragraph>
                                                    </div>
                                                    <div style={{ height: 120 }}>
                                                        <div style={{ fontSize: 20, color: 'red' }}>{"驳回原因:"}</div>
                                                        <Paragraph
                                                            ellipsis={{
                                                                rows: 4,
                                                                expandable: false,
                                                            }}
                                                        >
                                                            {item.reason}
                                                        </Paragraph>
                                                    </div>

                                                    <div className={styles.btnBg}>
                                                        <div className={styles.btnItem} onClick={() => {
                                                            this.props.history.push(`/account/center/auction/sale/${item.id}`)
                                                        }}>
                                                            <FormOutlined className={styles.itemIcon} />
                                                            <div className={styles.itemText}>{"重新上传"}</div>
                                                        </div>
                                                        <div className={styles.btnItem} onClick={() => {
                                                            this.setState({
                                                                id: item.id
                                                            })
                                                            this.openDeleteModal();
                                                        }}>
                                                            <DeleteTwoTone twoToneColor="red"
                                                                className={styles.itemIcon} />
                                                            <div className={styles.itemText}
                                                                style={{ color: 'red' }}>{"删除竞品"}</div>
                                                        </div>
                                                        <div style={{
                                                            fontSize: 15,
                                                            display: 'flex',
                                                            paddingLeft: '150px'
                                                        }}>
                                                            <div
                                                                className={styles.itemText}>{formatDate(item.createTime)}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </Col>
                                        </Row>
                                    </List.Item>
                                )}
                            />
                        </TabPane>
                    </Tabs>
                ) : (<Skeleton active />)}
            </div>
        );
    }
}

export default AccountCenterAuctionSaleList

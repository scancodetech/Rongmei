import React from 'react'
import { Button, Tabs, Row, Col, Space, Image, List, Modal, message, } from 'antd';
import styles from './index.less'
import { getMineCertification } from "@/services/apply";

import { listDataProps } from '@/components/ItemList';
import Item from 'antd/lib/list/Item';
const { TabPane } = Tabs;
export default class Promotion extends React.Component {
    state = {

    }
    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        console.log(certificationRes)
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }
    async componentDidMount() {
        await this.checkCertificationType("盒蛋类");
    }

    render() {
        return (
            <div>
                <Tabs defaultActiveKey="1" centered className={styles.tabBtn}>
                    <TabPane tab="全部活动" key="1">
                        {/* <ComList dataList={
                            [{ id: 0, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 0, blindboxStatus: 0 },
                            { id: 1, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 1, blindboxStatus: 0 },
                            { id: 2, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 2, blindboxStatus: 0 },
                            { id: 3, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 2, blindboxStatus: 1 },
                            { id: 4, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 2, blindboxStatus: 2 },
                            { id: 5, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 2, blindboxStatus: 3, reason: '资料不完整' },
                            { id: 6, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            { id: 7, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            { id: 8, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            { id: 9, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            { id: 10, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            { id: 11, img: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png', name: '活动名称1', startDate: '2021-2-1 12:30', endDate: '2021-2-3 12:30', roleStatus: 3, blindboxStatus: 0, reason: '资料不完整' },
                            ]
                        } /> */}
                    </TabPane>
                    <TabPane tab="已报名" key="2">
                        <ComList />
                    </TabPane>
                    <TabPane tab="已通过" key="3">
                        <ComList />
                    </TabPane>
                    <TabPane tab="未通过" key="4">
                        <ComList />
                    </TabPane>
                </Tabs>
            </div>
        )
    }
}

export interface promotionDataProps {
    dataList?: Array<{
        id?: Number,
        img?: String,
        name?: String,
        startDate?: String,
        endDate?: String,
        // 资格审核有4中状态，0代表未参与，1代表审核中，2代表通过，3代表未通过
        roleStatus?: Number,
        // 盒蛋状态同  0 未上传，1审核中，2审核通过，3审核未通过
        blindboxStatus?: Number,
        reason?: String
    }>


}
export class ComList extends React.Component<promotionDataProps> {
    state = {
        isShow: false,
        limit: 10,
        currentPage: 0,
        detail: {}
    }

    showModal(e) {
        console.log(e.target.dataset)
        var result = JSON.parse(e.target.dataset.detail)
        console.log(result)

        // 获得活动的ID
        // console.log(e.target.dataset.index)
        // let index = ((this.state.currentPage - 1) * 10) + Number(e.target.dataset.index)
        // console.log(index)
        this.setState({
            detail: result
        })
        this.setState({
            isShow: true
        })
    }

    render() {
        return (
            <div>
                <Row gutter={[0, 16]}>
                    <Col span={8}>
                        <div className={styles.listHead}>活动名称</div>
                    </Col>
                    <Col span={7}>
                        <div className={styles.listHead}>活动时间</div>
                    </Col>
                    <Col span={3}>
                        <div className={styles.listHead}>活动详情</div>
                    </Col>
                    <Col span={6}>
                        <div className={styles.listHead}>活动状态</div>
                    </Col>
                </Row>
                <Row gutter={[16, 16]}>
                    <Col span={24}>
                        <List
                            itemLayout="vertical"
                            pagination={{
                                onChange: page => {
                                    console.log(page);
                                    this.setState({ currentPage: page })
                                },
                                pageSize: this.state.limit
                            }}
                            // bordered={true}
                            dataSource={this.props.dataList}
                            // style={{padding:10,}}
                            // loading={true}
                            // footer={
                            //     <div>
                            //         <Divider style={{ fontWeight: 700 }}>{"已经没有更多内容了"}</Divider>
                            //     </div>
                            // }
                            renderItem={(item, dataIndex) => (
                                <List.Item
                                    key={dataIndex}
                                >
                                    <Row gutter={[8, 0]} justify='start'>
                                        {/* 图片 */}
                                        <Col span={4}>
                                            <Image src={item.img}
                                                height={80}
                                                placeholder={true}
                                                style={{ borderRadius: 9 }}
                                            ></Image>
                                        </Col>
                                        {/* 名称 */}
                                        <Col span={4} style={{ textAlign: 'start', alignSelf: 'center' }}>
                                            <div style={{ textAlign: 'start', fontSize: '15px', textOverflow: 'ellipsis', overflow: 'hidden' }}>{item.name}</div>
                                        </Col>
                                        {/* 时间 */}
                                        <Col span={7} style={{ textAlign: 'center', alignSelf: 'center', }}>
                                            <div style={{ fontSize: 15, color: '#999999', }}>{item.startDate + '——' + item.endDate}</div>
                                        </Col>
                                        {/* 详情 */}
                                        <Col span={3} style={{ alignSelf: 'center', textAlign: 'center' }}>
                                            <Space style={{ borderBottom: '2px solid #000000', }}>
                                                <div style={{ textAlign: "center", textDecoration: 'none', paddingBottom: '2px', fontWeight: 600, cursor: 'pointer' }} data-detail={JSON.stringify(item)} onClick={this.showModal = this.showModal.bind(this)}>查看详情</div>
                                            </Space>
                                        </Col>
                                        {/* 状态 */}
                                        <Col span={6} style={{ alignSelf: 'center' }}>
                                            {
                                                item.roleStatus == 0 ? (
                                                    <div style={{ textAlign: 'center' }}>
                                                        <Button type='primary' style={{ backgroundColor: 'white', color: '#FE2341' }} onClick={() => { console.log("报名参与") }}>马上报名参与</Button>
                                                    </div>
                                                ) : item.roleStatus == 1 ? (
                                                    <div style={{ textAlign: 'center' }}>
                                                        <div style={{ fontWeight: 600, color: '#FE2341' }}>资格审核中</div>
                                                    </div>
                                                ) : item.roleStatus == 2 ? (
                                                    // 审核通过后盒蛋有4中状态
                                                    item.blindboxStatus == 0 ? (
                                                        <div style={{ textAlign: 'center' }}>
                                                            <Space size='small' direction='vertical'>
                                                                <div style={{ textAlign: 'center', fontWeight: 600, color: '#02CB66' }}>资格审核通过</div>
                                                                <Button type='primary' style={{ backgroundColor: 'white', color: '#02CB66', borderColor: '#02CB66' }} onClick={() => { console.log("报名参与") }}>上传盒蛋</Button>
                                                            </Space>
                                                        </div>
                                                    ) : item.blindboxStatus == 1 ? (
                                                        <div style={{ textAlign: 'center' }}>
                                                            <div style={{ fontWeight: 600, color: '#FE2341' }}>盒蛋审核中</div>
                                                        </div>
                                                    ) : item.blindboxStatus == 2 ? (
                                                        <div style={{ textAlign: 'center' }}>
                                                            <div style={{ fontWeight: 600, color: '#02CB66' }}>盒蛋审核通过</div>
                                                        </div>
                                                    ) : (
                                                        <div style={{ textAlign: 'center' }}>
                                                            <Space size='small' direction='vertical'>
                                                                <div style={{ textAlign: 'center', fontWeight: 600, color: '#FE2341' }}>未通过</div>
                                                                <div style={{ textAlign: 'center', fontWeight: 600, color: '#FE2341' }}>{"原因：" + item.reason}</div>
                                                            </Space>
                                                        </div>
                                                    )
                                                ) : (
                                                    <div style={{ textAlign: 'center' }}>
                                                        <Space size='small' direction='vertical'>
                                                            <div style={{ textAlign: 'center', fontWeight: 600, color: '#FE2341' }}>未通过</div>
                                                            <div style={{ textAlign: 'center', fontWeight: 600, color: '#FE2341' }}>{"原因：" + item.reason}</div>
                                                        </Space>
                                                    </div>
                                                )
                                            }
                                        </Col>

                                    </Row>
                                </List.Item>
                            )}
                        />
                    </Col>
                </Row>
                {/* 查看详情的模态框 */}
                <Modal
                    title={this.state.detail.name}
                    centered
                    visible={this.state.isShow}
                    width={1000}
                    className={styles.modalTitle}
                    okText='参与活动'
                    // height={500}
                    onOk={() => { this.setState({ isShow: false }) }}
                    onCancel={() => { this.setState({ isShow: false }) }}
                >{
                        <div>

                            <Row gutter={[16, 16]}>
                                <Col span={24} >
                                    <div style={{ fontSize: 15, color: '#999999', textAlign: 'center' }}>{'活动时间：' + this.state.detail.startDate + '——' + this.state.detail.endDate}</div>
                                </Col>
                            </Row>

                            <Row gutter={[16, 16]}>
                                <Col span={24} >
                                    <Image src={this.state.detail.img}
                                        preview={false}
                                        height={400}
                                        placeholder={true}
                                        width={'100%'}
                                        style={{ borderRadius: 9 }}
                                    ></Image>
                                </Col>
                            </Row>

                            <Row gutter={[16, 16]}>
                                <Col span={24} >
                                    <Space size='small' direction='vertical' style={{ fontSize: 15, fontWeight: 600 }}>
                                        <div style={{ fontSize: 18 }}>活动说明（要求）：</div>
                                        <div>活动主办方：{ }</div>
                                        <div>参与活动注意事项：</div>
                                        <div>{"......"}</div>
                                        <div style={{ fontSize: 18 }}>活动奖励：</div>
                                        <div>{"....."}</div>
                                        <Space>
                                            <div style={{ fontSize: 18 }}>目前参与人数：</div>
                                            <div style={{ fontSize: 18, color: '#FE2341' }}>{ }人</div>
                                        </Space>
                                    </Space>
                                </Col>
                            </Row>

                        </div>

                    }

                </Modal>
            </div>


        )
    }

}
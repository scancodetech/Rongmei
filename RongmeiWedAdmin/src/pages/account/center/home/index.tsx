import React, { Component } from 'react';


import { Carousel, Card, Col, Row, Tabs, Button, Radio, Modal } from 'antd';
import homeStyles from "@/pages/account/center/home/home.less";
import { getMineUserRelation, getUserAccount, getUserInfo } from '@/services/user';
import banner1 from '@/assets/WechatIMG620.jpeg';
import banner2 from '@/assets/WechatIMG621.jpeg';
import { history } from "@@/core/history";
import { getTCC } from "@/services/tcc";
import { getMineAuctionStatistics, getMineSales } from "@/services/auction";
import { getMineBlindBoxSales, getMineBlindBoxSaleStatistics } from "@/services/blindbox";
import { getMineOrders } from "@/services/order";
import { getAuthorCommodities, getCommoditySaleStatistics } from "@/services/commodity";
import { getShareBatch } from "@/services/metrics";
import {
    getBlindBoxLikeUserStatistics,
    getCommodityLikeUserStatistics,
    getSaleLikeUserStatistics
} from "@/services/relation";



import ReactDOM from 'react-dom';


const { TabPane } = Tabs;

class AccountCenterHome extends Component<any> {

    state = {
        userInfo: {},
        fans: 0,
        follows: 0,
        roles: [],
        organizationRoles: [],
        selectedRole: '',
        isModalShow: false,
        accountInfo: '',

        saleShareNum: 0,
        saleIntervalShareNum: 0,
        saleLikeNum: 0,
        saleIntervalLikeNum: 0,
        saleJoinNum: 0,
        saleIntervalJoinNum: 0,

        blindBoxShareNum: 0,
        blindBoxIntervalShareNum: 0,
        blindBoxLikeNum: 0,
        blindBoxIntervalLikeNum: 0,
        blindBoxJoinNum: 0,
        blindBoxIntervalJoinNum: 0,

        commodityShareNum: 0,
        commodityIntervalShareNum: 0,
        commodityLikeNum: 0,
        commodityIntervalLikeNum: 0,
        commodityDownloadNum: 0,
        commodityIntervalDownloadNum: 0,
    }




    async componentDidMount(): Promise<void> {
        this.getInfo();
        this.getAccount();
        let applyRoleRes = await getTCC('dimension.pic.applyrole');
        this.setState({
            roles: eval(applyRoleRes.tccTuple.value)
        }, () => console.log(this.state.roles))
        this.getStatistics();


    }

    async getStatistics(): Promise<void> {
        const nowTime = new Date().getTime();
        const beforeTime = nowTime - 1000 * 60 * 60 * 24;
        const sales = (await getMineSales(2)).saleItems;
        const thingIds: number[] = [];
        sales.forEach((sale: any) => {
            thingIds.push(sale.thing.id);
        })
        const totalSaleShareRes = await getShareBatch(thingIds, 0, nowTime);
        let totalSaleShareCount = 0;
        totalSaleShareRes.counts.forEach((item) => {
            totalSaleShareCount += item.cnt;
        })
        const intervalSaleShareRes = await getShareBatch(thingIds, beforeTime, nowTime);
        let intervalSaleShareCount = 0;
        intervalSaleShareRes.counts.forEach((item) => {
            intervalSaleShareCount += item.cnt;
        })
        const totalSaleLikeRes = await getSaleLikeUserStatistics(0, nowTime);
        const intervalSaleLikeRes = await getSaleLikeUserStatistics(beforeTime, nowTime);
        const totalSaleJoinRes = await getMineAuctionStatistics(0, nowTime);
        const intervalSaleJoinRes = await getMineAuctionStatistics(beforeTime, nowTime);
        this.setState({
            saleShareNum: totalSaleShareCount,
            saleIntervalShareNum: intervalSaleShareCount,
            saleLikeNum: totalSaleLikeRes.count,
            saleIntervalLikeNum: intervalSaleLikeRes.count,
            saleJoinNum: totalSaleJoinRes.count,
            saleIntervalJoinNum: intervalSaleJoinRes.count,
        })

        const blindBoxSales = (await getMineBlindBoxSales(2)).blindBoxSaleItemList;
        const blindBoxNftIds: number[] = [];
        blindBoxSales.forEach((sale: any) => {
            blindBoxNftIds.push(sale.blindBoxNftId);
        })
        const totalBlindBoxShareRes = await getShareBatch(blindBoxNftIds, 0, nowTime);
        let totalBlindBoxShareCount = 0;
        totalBlindBoxShareRes.counts.forEach((item) => {
            totalBlindBoxShareCount += item.cnt;
        })
        const intervalBlindBoxShareRes = await getShareBatch(blindBoxNftIds, beforeTime, nowTime);
        let intervalBlindBoxShareCount = 0;
        intervalBlindBoxShareRes.counts.forEach((item) => {
            intervalBlindBoxShareCount += item.cnt;
        })
        const totalBlindBoxLikeRes = await getBlindBoxLikeUserStatistics(0, nowTime);
        const intervalBlindBoxLikeRes = await getBlindBoxLikeUserStatistics(beforeTime, nowTime);
        const totalBlindBoxJoinRes = await getMineBlindBoxSaleStatistics(0, nowTime);
        const intervalBlindBoxJoinRes = await getMineBlindBoxSaleStatistics(beforeTime, nowTime);
        this.setState({
            blindBoxShareNum: totalBlindBoxShareCount,
            blindBoxIntervalShareNum: intervalBlindBoxShareCount,
            blindBoxLikeNum: totalBlindBoxLikeRes.count,
            blindBoxIntervalLikeNum: intervalBlindBoxLikeRes.count,
            blindBoxJoinNum: totalBlindBoxJoinRes.count,
            blindBoxIntervalJoinNum: intervalBlindBoxJoinRes.count,
        })

        const commodities = (await getAuthorCommodities(2)).commodities;
        const commodityIds: number[] = [];
        commodities.forEach((commodity: any) => {
            commodityIds.push(commodity.id);
        })

        const totalCommodityShareRes = await getShareBatch(commodityIds, 0, nowTime);
        let totalCommodityShareCount = 0;
        totalCommodityShareRes.counts.forEach((item) => {
            totalCommodityShareCount += item.cnt;
        })
        const intervalCommodityShareRes = await getShareBatch(commodityIds, beforeTime, nowTime);
        let intervalCommodityShareCount = 0;
        intervalCommodityShareRes.counts.forEach((item) => {
            intervalCommodityShareCount += item.cnt;
        })
        const totalCommodityLikeRes = await getCommodityLikeUserStatistics(0, nowTime);
        const intervalCommodityLikeRes = await getCommodityLikeUserStatistics(beforeTime, nowTime);
        const totalCommodityDownloadRes = await getCommoditySaleStatistics(0, nowTime);
        const intervalCommodityDownloadRes = await getCommoditySaleStatistics(beforeTime, nowTime);
        this.setState({
            commodityShareNum: totalCommodityShareCount,
            commodityIntervalShareNum: intervalCommodityShareCount,
            commodityLikeNum: totalCommodityLikeRes.count,
            commodityIntervalLikeNum: intervalCommodityLikeRes.count,
            commodityDownloadNum: totalCommodityDownloadRes.count,
            commodityIntervalDownloadNum: intervalCommodityDownloadRes.count,
        })
    }

    async getInfo() {
        let res = await getUserInfo();
        this.setState({
            userInfo: res
        })
    }

    async getAccount() {
        let res = await getUserAccount();
        this.setState({
            accountInfo: res
        })
    }

    async getFigure() {
        let res = await getMineUserRelation();
        this.setState({
            fans: res.fans.length,
            follows: res.follows.length
        })
    }

    onTabSwitch() {

    }

    openRoleSelectModal() {
        this.setState({
            isModalShow: true
        })
    }

    closeRoleSelectModal() {
        this.setState({
            isModalShow: false
        })
    }

    onRoleChange = e => {
        this.setState({
            selectedRole: e.target.value,
        }, () => console.log(this.state.selectedRole));
    };

    render() {
        let userinfo = this.state.userInfo;
        return (
            <div className={homeStyles.homeContainer}>
                {/* 分区上传选择 */}
                <Modal
                    width={'600px'}
                    bodyStyle={{ padding: 'auto 30px', display: 'flex', flexDirection: 'column', }}
                    title={(<div style={{ textAlign: 'center' }}>选择身份</div>)}
                    visible={this.state.isModalShow}
                    centered
                    footer={null}
                    onOk={() => {
                        this.closeRoleSelectModal();
                    }}
                    onCancel={() => {
                        this.closeRoleSelectModal();
                    }}
                >
                    {
                        this.state.roles.map((roles) => (
                            <div>
                                <h3>{roles.type}</h3>
                                <Radio.Group
                                    style={{ width: '100%' }}
                                    value={this.state.selectedRole}
                                    onChange={(e) => {
                                        this.onRoleChange(e)
                                    }}>
                                    {
                                        roles.list.map((list) => (
                                            <Row style={{ margin: '10px auto' }}>
                                                <Col span={6}>
                                                    <h5 style={{ fontSize: 14, textAlign: 'center', }}>{list.title}:</h5>
                                                </Col>
                                                <Col span={18}>
                                                    <Row
                                                        align='top'
                                                    >
                                                        {list.typeList.map((item) => (
                                                            <Col style={{ margin: '0 0 10px 0' }} span={8}>
                                                                <Radio value={`${roles.type}/${list.title}/${item}`} key={`${roles.type}/${list.title}/${item}`}>{item}</Radio>
                                                            </Col>
                                                        ))}
                                                    </Row>
                                                </Col>
                                            </Row>
                                        ))
                                    }
                                </Radio.Group>
                            </div>

                        ))
                    }
                    <div>
                        <h3>智能创作者</h3>
                        <p style={{ color: '#1c1c1c', textAlign: 'center' }}>暂未开放</p>
                    </div>
                    <Button
                        ghost={this.state.selectedRole.length === 0}
                        disabled={this.state.selectedRole.length === 0}
                        style={{
                            margin: '20px auto',
                            fontWeight: 'bold',
                            alignSelf: 'center',
                            color: '#ffffff',
                            backgroundColor: '#fe2341',
                            border: '0px',
                            borderRadius: '10px',
                            fontSize: '18px',
                            width: '120px',
                            height: '40px'
                        }}
                        onClick={() => {
                            if (this.state.selectedRole.indexOf('机构创作者') === -1) {
                                history.push(`/account/center/applyRoles/${this.state.selectedRole}`);
                            }
                            else {
                                this.props.history.push(`/applyRoles/${this.state.selectedRole}`)
                            }
                        }}
                    >确定</Button>
                </Modal>
                {/* 头部栏 */}
                <div className={homeStyles.header}>
                    <img className={homeStyles.headerAvatar} alt='' src={userinfo.avatarUrl} />
                    <div className={homeStyles.headerStat}>
                        <div className={homeStyles.headerUserName}>{userinfo.nickname}</div>
                        <div className={homeStyles.headerInfos}>
                            {/* <div className={homeStyles.headerInfoItem}>
                <span>154</span>
                获赞
              </div> */}
                            <div className={homeStyles.headerInfoItem}>
                                <span>{this.state.follows}</span>
                                关注
                            </div>
                            <div className={homeStyles.headerInfoItem}>
                                <span>{this.state.fans}</span>
                                粉丝
                            </div>
                        </div>
                        <div className={homeStyles.headerDescription}>
                            <div>{userinfo.description ? userinfo.description : "你还没有个人简介"}</div>
                        </div>
                    </div>
                    <div id='mark-1' className={homeStyles.headerTag}>
                        <Button className={homeStyles.authBtn} onClick={() => {
                            this.openRoleSelectModal();
                        }}>
                            身份认证
                        </Button>
                    </div>
                </div>
                {/* banner */}
                <div className={homeStyles.homeBanner}>
                    <Carousel autoplay>
                        <div>
                            <img className={homeStyles.bannerItem} src={banner1} />
                        </div>
                        <div>
                            <img className={homeStyles.bannerItem} src={banner2} />
                        </div>
                    </Carousel>
                </div>
                {/* 下方信息展示部分 */}
                <Row gutter={24} className={homeStyles.homeInfo}>
                    {/* 数据总览 */}
                    <Col
                        span={18}
                        className={homeStyles.dataOverview}>
                        <h4 className={homeStyles.dataTitle}>数据总览</h4>
                        <div className={homeStyles.dataTabs}>
                            <Tabs defaultActiveKey="1" onChange={this.onTabSwitch}>
                                <TabPane tab="竞拍数据" key="1">
                                    <Row gutter={[8, 8]}>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>分享量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div className={homeStyles.tabNum}>{this.state.saleShareNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.saleIntervalShareNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>喜欢量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div className={homeStyles.tabNum}>{this.state.saleLikeNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.saleIntervalLikeNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>参与人数</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div className={homeStyles.tabNum}>{this.state.saleJoinNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.saleIntervalJoinNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                    </Row>
                                </TabPane>
                                <TabPane tab="素材数据" key="2">
                                    <Row gutter={[8, 8]}>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>分享量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.commodityShareNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.commodityIntervalShareNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>喜欢量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.commodityLikeNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.commodityIntervalLikeNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>下载量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.commodityDownloadNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.commodityIntervalDownloadNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                    </Row>
                                </TabPane>
                                <TabPane tab="盒蛋数据" key="3">
                                    <Row gutter={[8, 8]}>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>分享量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.blindBoxIntervalShareNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.blindBoxIntervalShareNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>喜欢量</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.blindBoxLikeNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.blindBoxIntervalLikeNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                        <Col span={8}>
                                            <Card className={homeStyles.tabCard}
                                                title={<div className={homeStyles.tabText}>参与人数</div>}
                                                bordered={false}>
                                                <div className={homeStyles.tabCardCount}>
                                                    <div
                                                        className={homeStyles.tabNum}>{this.state.blindBoxJoinNum}</div>
                                                    <div
                                                        className={homeStyles.tabAddNum}>+{this.state.blindBoxIntervalJoinNum}</div>
                                                </div>
                                            </Card>
                                        </Col>
                                    </Row>
                                </TabPane>
                            </Tabs>
                        </div>
                    </Col>
                    {/* 资产（钱包） */}
                    <Col
                        span={6}
                        className={homeStyles.walletIncome}
                        style={{ padding: '' }}>
                        <h4 className={homeStyles.walletTitle}>钱包</h4>
                        <div className={homeStyles.walletItem}>
                            <div className={homeStyles.itemtext}>钱包余额</div>
                            <div className={homeStyles.itemNum}>{this.state.accountInfo.largeCoins} 电子</div>
                        </div>
                        <div className={homeStyles.walletItem}>
                            <div className={homeStyles.itemtext}>素材收益</div>
                            <div className={homeStyles.itemNum}>0 电子</div>
                        </div>
                        <div className={homeStyles.walletItem}>
                            <div className={homeStyles.itemtext}>资产收益</div>
                            <div className={homeStyles.itemNum}>0 电子</div>
                        </div>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default AccountCenterHome

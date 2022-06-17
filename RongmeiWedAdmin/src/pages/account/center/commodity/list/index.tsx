import React, { Component } from 'react';
import commodityStyles from '@/pages/account/center/commodity/commodity.less';
import { Tabs, List, message, Popconfirm, Skeleton, Typography, Alert } from 'antd';
import {
    ArrowDownOutlined,
    DeleteTwoTone,
    EditOutlined,
    EyeOutlined,
    HeartOutlined,
    FormOutlined,
    ShareAltOutlined
} from '@ant-design/icons';
import {
    addCommodityView,
    deleteCommodity, getAuthorCommodities,
    getCommodityDownload,
    getCommodityView,
} from '@/services/commodity';
import { getMineCertification } from "@/services/apply";
import { getDraftReason } from "@/services/draft";
import { copyToClip } from '@/utils/utils'

const { TabPane } = Tabs;
const { Paragraph } = Typography;

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

class AccountCenterCommodityList extends Component<any> {

    state = {
        limit: 10,
        total: 0,
        commoditiesAll: [],
        visible: false,
        id: 0,
        status: "-1",
        loading: false
    }

    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }

    async componentDidMount() {
        await this.checkCertificationType("素材类");
        this.getComm(parseInt(this.state.status));
    }

    async getComm(status: number) {
        let res = await getAuthorCommodities(status);
        console.log(res)
        this.setState({
            loading: true,
        })
        if (res.commodities) {
            if (this.state.status === '1') {
                for (let i = 0; i < res.commodities.length; i++) {
                    let draftReasonRes = await getDraftReason(res.commodities[i].id, 0);
                    res.commodities[i].reason = draftReasonRes.msg;
                }
            } else if (this.state.status === '2') {
                for (let i = 0; i < res.commodities.length; i++) {
                    let viewRes = await getCommodityView(res.commodities[i].id);
                    let downloadRes = await getCommodityDownload(res.commodities[i].id);
                    res.commodities[i].view = viewRes.count;
                    res.commodities[i].download = downloadRes.count;
                }
            }
            this.setState({
                commoditiesAll: res.commodities
            })
        }
        this.setState({
            loading: false
        })
    }

    async deleteComm(id: number) {
        let res = await deleteCommodity(id);
        if (res.infoCode == 10000) {
            message.info('删除成功');
            this.getComm(parseInt(this.state.status));
        } else {
            message.info('删除失败');
        }
    }

    async onClickItem(id: number) {
        await addCommodityView(id, '');
    }

    render() {
        return (
            <div className={commodityStyles.commodityMyContainer}>
                <div className={commodityStyles.headTitle}>我的素材列表</div>
                <div className={commodityStyles.listContainer}>
                    {!this.state.loading ? (
                        <Tabs activeKey={this.state.status} className={commodityStyles.tabsChange}
                            onChange={(activeKey) => {
                                this.setState({
                                    status: activeKey
                                })
                                this.getComm(parseInt(activeKey));
                            }
                            }>
                            <TabPane tab="全部素材" key="-1">
                                <List
                                    itemLayout="vertical"
                                    size="large"
                                    pagination={{
                                        pageSize: this.state.limit
                                    }}
                                    dataSource={this.state.commoditiesAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} onClick={() => {
                                                        this.onClickItem(item.id)
                                                    }} alt='素材图片' src={item.coverUrl} />
                                                </div>
                                                <div className={commodityStyles.itemInfo}>
                                                    <div className={commodityStyles.itemTitle}>{item.title}</div>
                                                    <Paragraph
                                                        ellipsis={{
                                                            rows: 1,
                                                            expandable: false,
                                                            suffix: '...'
                                                        }}
                                                    >
                                                        {item.introduction}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemFigure}>
                                                        <div className={commodityStyles.view}>
                                                            <EyeOutlined />
                                                            <span>{item.view}</span>
                                                        </div>
                                                        <div className={commodityStyles.view}>
                                                            <ArrowDownOutlined />
                                                            {item.download}
                                                        </div>
                                                        <div className={commodityStyles.download}>
                                                            <HeartOutlined />
                                                            {item.heart}
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>已发布</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{ display: 'flex' }}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.largePrice / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <FormOutlined />
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/commodity/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red' />
                                                            <Popconfirm
                                                                title="确认删除该素材？"
                                                                onConfirm={() => {
                                                                    this.deleteComm(item.id)
                                                                }}
                                                                // onCancel={cancel}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除素材</a>
                                                            </Popconfirm>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </List.Item>
                                    )}
                                />
                            </TabPane>

                            <TabPane tab="已发布" key="2">
                                <List
                                    itemLayout="vertical"
                                    size="large"
                                    pagination={{
                                        pageSize: this.state.limit
                                    }}
                                    dataSource={this.state.commoditiesAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} onClick={() => {
                                                        this.onClickItem(item.id)
                                                    }} alt='素材图片' src={item.coverUrl} />
                                                </div>
                                                <div className={commodityStyles.itemInfo}>
                                                    <div className={commodityStyles.itemTitle}>{item.title}</div>
                                                    <Paragraph
                                                        ellipsis={{
                                                            rows: 1,
                                                            expandable: false,
                                                            suffix: '...'
                                                        }}
                                                    >
                                                        {item.introduction}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemFigure}>
                                                        <div className={commodityStyles.view}>
                                                            <EyeOutlined />
                                                            <span>{item.view}</span>
                                                        </div>
                                                        <div className={commodityStyles.view}>
                                                            <ArrowDownOutlined />
                                                            {item.download}
                                                        </div>
                                                        <div className={commodityStyles.download}>
                                                            <HeartOutlined />
                                                            {item.heart}
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>已发布</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{ display: 'flex' }}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.largePrice / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <FormOutlined />
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/commodity/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red' />
                                                            <Popconfirm
                                                                title="确认删除该素材？"
                                                                onConfirm={() => {
                                                                    this.deleteComm(item.id)
                                                                }}
                                                                // onCancel={cancel}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除素材</a>
                                                            </Popconfirm>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <ShareAltOutlined />
                                                            <a className={commodityStyles.deleteText} onClick={() => {
                                                                try {
                                                                    copyToClip("https://m.dimension.pub/#/picmobile/commodity/" + item.id)
                                                                    message.success('链接复制成功！')
                                                                } catch (e) {
                                                                    message.error('链接复制失败！')
                                                                }
                                                            }}>分享素材</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </List.Item>
                                    )}
                                />
                            </TabPane>
                            <TabPane tab="审核中" key="0">
                                <List
                                    itemLayout="vertical"
                                    size="large"
                                    pagination={{
                                        pageSize: this.state.limit
                                    }}
                                    dataSource={this.state.commoditiesAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} onClick={() => {
                                                        this.onClickItem(item.id)
                                                    }} alt='素材图片' src={item.coverUrl} />
                                                </div>
                                                <div className={commodityStyles.itemInfo}>
                                                    <div className={commodityStyles.itemTitle}>{item.title}</div>
                                                    <Paragraph
                                                        ellipsis={{
                                                            rows: 1,
                                                            expandable: false,
                                                            suffix: '...'
                                                        }}
                                                    >
                                                        {item.introduction}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemFigure}>
                                                        <div className={commodityStyles.view}>
                                                            <EyeOutlined />
                                                            <span>{item.view}</span>
                                                        </div>
                                                        <div className={commodityStyles.view}>
                                                            <ArrowDownOutlined />
                                                            {item.download}
                                                        </div>
                                                        <div className={commodityStyles.download}>
                                                            <HeartOutlined />
                                                            {item.heart}
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>审核中</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{ display: 'flex' }}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.largePrice / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    {/* <div className={commodityStyles.bottomOpts}>
                            <div className={commodityStyles.itemResale}>
                              <FormOutlined />
                              <a className={commodityStyles.resaleText} onClick={() => {
                                this.props.history.push(`/account/center/commodity/edit/${item.id}`)
                              }}>编辑信息</a>
                            </div>
                            <div className={commodityStyles.itemDelete}>
                              <DeleteTwoTone twoToneColor='red'/>
                              <Popconfirm
                                title="确认删除该素材？"
                                onConfirm={() => {
                                  this.deleteComm(item.id)
                                }}
                                // onCancel={cancel}
                                okText="确认"
                                cancelText="取消"
                              >
                                <a className={commodityStyles.deleteText}>删除素材</a>
                              </Popconfirm>
                            </div>
                          </div> */}
                                                </div>
                                            </div>
                                        </List.Item>
                                    )}
                                />
                            </TabPane>
                            <TabPane tab="未通过" key="1">
                                <List
                                    itemLayout="vertical"
                                    size="large"
                                    pagination={{
                                        pageSize: this.state.limit
                                    }}
                                    dataSource={this.state.commoditiesAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} onClick={() => {
                                                        this.onClickItem(item.id)
                                                    }} alt='素材图片' src={item.coverUrl} />
                                                </div>
                                                <div className={commodityStyles.itemInfo}>
                                                    <div className={commodityStyles.itemTitle}>{item.title}</div>
                                                    <Paragraph
                                                        ellipsis={{
                                                            rows: 1,
                                                            expandable: false,
                                                            suffix: '...'
                                                        }}
                                                    >
                                                        {item.introduction}
                                                    </Paragraph>
                                                    <div>
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
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>未通过</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{ display: 'flex' }}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.largePrice / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <EditOutlined />
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/commodity/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red' />
                                                            <Popconfirm
                                                                title="确认删除该素材？"
                                                                onConfirm={() => {
                                                                    this.deleteComm(item.id)
                                                                }}
                                                                // onCancel={cancel}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除素材</a>
                                                            </Popconfirm>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </List.Item>
                                    )}
                                />
                            </TabPane>

                        </Tabs>
                    ) : (<Skeleton active />)}
                </div>
            </div>
        );
    }
}

export default AccountCenterCommodityList

import React, {Component} from 'react';
import commodityStyles from '@/pages/account/center/groupshopping/groupshopping.less';
import {Tabs, List, message, Popconfirm, Skeleton, Typography} from 'antd';
import {
    DeleteTwoTone,
    EditOutlined,
    FormOutlined,
    ShareAltOutlined
} from '@ant-design/icons';
import {} from '@/services/commodity';
import {getDraftReason} from "@/services/draft";
import {getAuthorGroupShoppings} from "@/services/groupshopping";

const {TabPane} = Tabs;
const {Paragraph} = Typography;

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

class AccountCenterGroupShoppingList extends Component<any> {

    state = {
        limit: 10,
        total: 0,
        groupShoppingAll: [],
        visible: false,
        id: 0,
        status: "-1",
        loading: false
    }

    async componentDidMount() {
        this.getGroupShopping(parseInt(this.state.status));
    }

    async getGroupShopping(status: number) {
        let res = await getAuthorGroupShoppings(status);
        this.setState({
            loading: true,
        })
        if (res.groupShoppingItemList) {
            if (this.state.status === '1') {
                for (let i = 0; i < res.groupShoppingItemList.length; i++) {
                    let draftReasonRes = await getDraftReason(res.groupShoppingItemList[i].id, 4);
                    res.groupShoppingItemList[i].reason = draftReasonRes.msg;
                }
            }
            this.setState({
                groupShoppingAll: res.groupShoppingItemList
            })
        }
        this.setState({
            loading: false
        })
    }

    async deleteGroupShopping(id: number) {
        let res = await deleteCommodity(id);
        if (res.infoCode == 10000) {
            message.info('删除成功');
            this.getGroupShopping(parseInt(this.state.status));
        } else {
            message.info('删除失败');
        }
    }

    render() {
        return (
            <div className={commodityStyles.commodityMyContainer}>
                <div className={commodityStyles.headTitle}>我的巨人车列表</div>
                <div className={commodityStyles.listContainer}>
                    {!this.state.loading ? (
                        <Tabs activeKey={this.state.status} className={commodityStyles.tabsChange}
                              onChange={(activeKey) => {
                                  this.setState({
                                      status: activeKey
                                  })
                                  this.getGroupShopping(parseInt(activeKey));
                              }
                              }>
                            <TabPane tab="全部巨人车" key="-1">
                                <List
                                    itemLayout="vertical"
                                    size="large"
                                    pagination={{
                                        pageSize: this.state.limit
                                    }}
                                    dataSource={this.state.groupShoppingAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} alt='巨人车图片'
                                                         src={item.coverUrl}/>
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
                                                        {item.description}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>已发布</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{display: 'flex'}}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.price / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <FormOutlined/>
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/groupshopping/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red'/>
                                                            <Popconfirm
                                                                title="确认删除该巨人车？"
                                                                onConfirm={() => {
                                                                    this.deleteGroupShopping(item.id)
                                                                }}
                                                                // onCancel={cancel}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除巨人车</a>
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
                                    dataSource={this.state.groupShoppingAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} alt='巨人车图片'
                                                         src={item.coverUrl}/>
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
                                                        {item.description}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>已发布</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{display: 'flex'}}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.price / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <FormOutlined/>
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/groupshopping/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red'/>
                                                            <Popconfirm
                                                                title="确认删除该巨人车？"
                                                                onConfirm={() => {
                                                                    this.deleteGroupShopping(item.id)
                                                                }}
                                                                // onCancel={cancel}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除巨人车</a>
                                                            </Popconfirm>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <ShareAltOutlined/>
                                                            <a className={commodityStyles.deleteText}>分享巨人车</a>
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
                                    dataSource={this.state.groupShoppingAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} alt='巨人车图片'
                                                         src={item.coverUrl}/>
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
                                                        {item.description}
                                                    </Paragraph>
                                                    <div className={commodityStyles.itemData}>
                                                        <div className={commodityStyles.status}>审核中</div>
                                                        <div
                                                            className={commodityStyles.itemDate}>{formatDate(item.createTime)}</div>
                                                    </div>
                                                    <div style={{display: 'flex'}}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.price / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
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
                                    dataSource={this.state.groupShoppingAll}
                                    renderItem={(item: any, index) => (
                                        <List.Item>
                                            <div className={commodityStyles.commodityItem}>
                                                <div className={commodityStyles.itemImgBox}>
                                                    <img className={commodityStyles.itemImg} alt='巨人车图片'
                                                         src={item.coverUrl}/>
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
                                                        {item.description}
                                                    </Paragraph>
                                                    <div>
                                                        <div style={{fontSize: 20, color: 'red'}}>{"驳回原因:"}</div>
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
                                                    <div style={{display: 'flex'}}>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600,
                                                            marginRight: '5px'
                                                        }}>{(item.price / 100).toLocaleString()}</div>
                                                        <div style={{
                                                            color: '#398DFF',
                                                            padding: '10px 0',
                                                            fontWeight: 600
                                                        }}>电子
                                                        </div>
                                                    </div>
                                                    <div className={commodityStyles.bottomOpts}>
                                                        <div className={commodityStyles.itemResale}>
                                                            <EditOutlined/>
                                                            <a className={commodityStyles.resaleText} onClick={() => {
                                                                this.props.history.push(`/account/center/groupshopping/edit/${item.id}`)
                                                            }}>编辑信息</a>
                                                        </div>
                                                        <div className={commodityStyles.itemDelete}>
                                                            <DeleteTwoTone twoToneColor='red'/>
                                                            <Popconfirm
                                                                title="确认删除该巨人车？"
                                                                onConfirm={() => {
                                                                    this.deleteGroupShopping(item.id)
                                                                }}
                                                                okText="确认"
                                                                cancelText="取消"
                                                            >
                                                                <a className={commodityStyles.deleteText}>删除巨人车</a>
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
                    ) : (<Skeleton active/>)}
                </div>
            </div>
        );
    }
}

export default AccountCenterGroupShoppingList

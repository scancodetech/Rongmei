import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import {Button, Card, Divider, message, Modal, Table, Tag} from "antd";
import {api} from '../../services/api/ApiProvider'

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

const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id'
    }, {
        title: '电话',
        dataIndex: 'username',
        key: 'username',
    }, {
        title: '昵称',
        dataIndex: 'nickname',
        key: 'nickname',
    }, {
        title: '微信',
        dataIndex: 'wechat',
        key: 'wechat'
    }, {
        title: '微博',
        dataIndex: 'weibo',
        key: 'weibo'
    }, {
        title: 'address',
        dataIndex: '地址',
        key: '地址'
    }];

class CommodityPage extends React.Component {
    mallService = api.mallService;
    accountService = api.accountService;

    state = {
        loading: false,
        data: [],
        isModalShow: false,
        limit: 10000,
        offset: 0,
        total: 0,

        id: 0,
        title: '',
        largePrice: 0,
        coverUrl: '',
        tags: [],
        contentUrl: '',
        description: '',
        signingInfo: '',
        extra: '',
        downloadUrl: '',
        userGroupInfo: {},
        userInfoDetails: []
    }

    async componentDidMount() {
        try {
            const res = await this.mallService.getAllCommodities(this.state.limit, this.state.offset);
            let commodityItems = res.commodities;
            let total = res.total;
            this.setState({
                data: commodityItems,
                total: total
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    showModal = async (id) => {
        if (id) {
            const commodityAuthRes = await this.mallService.getCommodityAuthor(id);
            let userInfoDetails = [];
            for (let i = 0; i < commodityAuthRes.usernameList.length; i++) {
                const username = commodityAuthRes.usernameList[i];
                const userInfoDetail = await this.accountService.getUserInfoEntity(username);
                userInfoDetails.push(userInfoDetail);
            }
            const commodityRes = await this.mallService.getCommodity(id);
            this.setState({
                id: commodityRes.id,
                title: commodityRes.title,
                largePrice: commodityRes.largePrice,
                coverUrl: commodityRes.coverUrl,
                tags: commodityRes.tags,
                contentUrl: commodityRes.contentUrl,
                description: commodityRes.description,
                signingInfo: commodityRes.signingInfo,
                extra: commodityRes.extra,
                downloadUrl: commodityRes.downloadUrl,

                userGroupInfo: commodityAuthRes.userGroupInfo,
                userInfoDetails: userInfoDetails
            })
        }
        this.setState({
            isModalShow: true,
        });
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    deleteCommodity = (id) => {
        this.mallService.deleteCommodity(id)
    }

    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['素材', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示素材的各种信息<br/>点击后展示用户溯源信息"}/>
                <Modal
                    title="用户溯源信息"
                    visible={this.state.isModalShow}
                    onOk={this.hideModal}
                    onCancel={this.hideModal}
                    okText="确认"
                    cancelText="取消"
                >
                    {this.state.userGroupInfo ?
                        <div>
                            <h1>素材名：{this.state.title}</h1>
                            <div>
                                标价：{this.state.largePrice / 100}
                            </div>
                            <div>
                                分类：{this.state.tags.map((tag) => <Tag color="magenta">{tag}</Tag>)}
                            </div>
                            <Divider/>
                            <h1>组名：{this.state.userGroupInfo.title}</h1>
                            <Table dataSource={this.state.userInfoDetails} columns={columns} style={styles.tableStyle}/>
                        </div> : <div>未绑定组</div>
                    }
                </Modal>
                {
                    this.state.data.map((commodity) => (
                        <Card hoverable style={{marginBottom: '10px'}}
                              onClick={() => {
                                  this.showModal(commodity.id)
                              }}
                              title={
                                  <div>
                                <span>
                                    ID：{commodity.id}
                                </span>
                                  </div>
                              }
                              // extra={
                              //     <Button type="primary" onClick={this.deleteCommodity(commodity.id)}
                              //             danger>
                              //         删除
                              //     </Button>
                              // }
                        >
                            <div style={{padding: '0 15px'}}>
                                <div style={{display: 'flex', padding: '15px 0'}}>
                                    <img style={{height: '64px', marginRight: '15px'}} src={commodity.coverUrl} alt=""/>
                                    <div style={{lineHeight: 1, marginTop: '5px'}}>
                                    <span style={{float: 'left'}}>
                                        <div style={{
                                            marginBottom: '10px',
                                            fontSize: '14px',
                                            color: '#333',
                                            fontWeight: 'bold',
                                            float: 'left'
                                        }}>{commodity.title}</div>
                                        <div style={{
                                            marginTop: '45px',
                                            color: '#333',
                                            fontSize: '10px',
                                        }}>创建时间：{formatDate(commodity.createTime)}</div>
                                    </span>
                                        <span style={{
                                            float: 'right', color: '#333',
                                            fontSize: '10px', textAlign: 'right'
                                        }}>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    ))
                }
            </div>
        )
    }
}

const styles = {
    tableStyle: {
        width: '100%'
    }
}

export default CommodityPage

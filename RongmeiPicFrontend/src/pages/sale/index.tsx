import React, {Component} from 'react';

import {List, Button, Modal, Avatar, InputNumber, message, Image} from 'antd';
import {ConnectProps} from "@@/plugin-dva/connect";
import {connect, Link} from "umi";
import {ConnectState} from "@/models/connect";
import {getDateDiff} from '@/utils/utils';
import {getUserAccount, payEarnest} from "@/services/user";
import {
  ExclamationCircleOutlined,
  ShareAltOutlined,
  WarningOutlined,
  HeartOutlined,
  HeartTwoTone
} from "@ant-design/icons/lib";
import {
  bid,
  getSale,
  getSaleHistory,
  getAuthorArtworks,
  getMineSalesParticipate,
  getOwnerArtworks
} from "@/services/auction";
import saleStyles from "@/pages/sale/sale.less";
import PayModal from "@/components/PayModal";
import "./sale.less";
import {getLike, postLike} from "@/services/relation";
import {addDislike, addShare, getDislike, getShare} from "@/services/metrics";

const {confirm} = Modal;

interface SaleProps extends Partial<ConnectProps> {
  loading: boolean;
  location: any;
}

class Sale extends Component<SaleProps> {

  state = {
    id: 0,
    startPrice: 0,
    status: '竞价中',
    intervalPrice: 0,
    thing: {},
    createTime: 0,
    startTime: 0,
    endTime: 0,
    currPrice: 0,
    minePrice: 0,
    author: {},
    owner: {},
    userAccount: {},
    historyList: [],
    needEarnestMoney: false,

    dayDiff: 0,
    hourDiff: 0,
    minuteDiff: 0,
    secondDiff: 0,

    price: 0,
    needMoney: 0,
    isBidModalVisible: false,
    isPayModalVisible: false,
    bidType: '',
    bidCountText: '',

    isLike: false,
    likeNum: 0,
    shareNum: 0,
    dislikeNum: 0
  }

  async componentDidMount(): void {
    const saleId = this.props.location.pathname.split('/').pop();
    let getSaleRes = await getSale(saleId);
    this.setState({
      ...getSaleRes,
      price: getSaleRes.currPrice / 100
    }, () => {
      let interval = window.setInterval(() => {
        this.getBidType(this.state.startTime, this.state.endTime);
      }, 1000);
      this.updateLike();
      this.updateShare();
      this.updateDislike();
    })
    let historyRes = await getSaleHistory(saleId);
    this.setState({
      historyList: historyRes.auctionHistories,
    })
    let authorArtRes = await getOwnerArtworks(getSaleRes.thing.author);
    this.setState({
      authorArtworks: authorArtRes.thingItems.slice(0, 5),
    })
  }

  getBidType(startTime: number, endTime: number) {
    let now = new Date();
    let startTimeDiff = startTime - now.getTime();
    let endTimeDiff = endTime - now.getTime();
    let timeDiff = 0;
    if (startTimeDiff > 0) {
      timeDiff = startTimeDiff;
      this.setState({
        bidType: '未开始',
        bidCountText: '距离开始'
      })
    } else if (startTimeDiff <= 0 && endTimeDiff >= 0) {
      timeDiff = endTimeDiff;
      this.setState({
        bidType: '进行中',
        bidCountText: '距离结束'
      })
    } else if (endTimeDiff < 0) {
      timeDiff = 0
      this.setState({
        bidType: '已结束',
        bidCountText: '已结束'
      })
    }
    this.countDown(timeDiff);
  }

  countDown(timeDiff: number) {
    let dayDiff = timeDiff / (24 * 60 * 60 * 1000);
    let hourDiff = Math.floor((timeDiff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)) + "";
    let minuteDiff = Math.floor((timeDiff % (60 * 60 * 1000)) / (60 * 1000)) + "";
    let secondDiff = Math.floor((timeDiff % 60000) / 1000) + "";
    if (hourDiff.length == 1) {
      hourDiff = "0" + hourDiff;
    }
    if (minuteDiff.length == 1) {
      minuteDiff = "0" + minuteDiff;
    }
    if (secondDiff.length == 1) {
      secondDiff = "0" + secondDiff;
    }
    this.setState({
      dayDiff: Math.floor(dayDiff),
      hourDiff: hourDiff,
      minuteDiff: minuteDiff,
      secondDiff: secondDiff,
    })
  }

  transferDescription() {
    this.state.thing.description = '#测试';
    let description = this.state.thing.description;
    let tempArr = []
    let linkedDescription = '';
    tempArr = description.split('#');
    for (let i = 0; i < tempArr.length; i++) {
      if (i / 2 !== 0) {
        linkedDescription += "<a href='' target='_blank'>";
      } else if (i / 2 === 0 && i !== 0) {

      } else if (i === 0) {
        linkedDescription += tempArr[i];
      }
    }
  }

  async getAuthorArtworks(): void {
    let res = await getAuthorArtworks(this.state.author.nickname);
    this.setState({
      thingItems: res.thingItems,
    })
  }

  async checkEarnest() {
    if (new Date().getTime() < this.state.startTime) {
      message.error("还未到竞价时间");
      return;
    }
    if (new Date().getTime() > this.state.endTime) {
      message.error("已超出竞价时间");
      return;
    }
    const res = await getMineSalesParticipate();
    const that = this;
    if (res.saleItems) {
      let totalEarnest = 0;
      let saleItems = res.saleItems;
      for (let i = 0; i < saleItems.length; i++) {
        const saleItem = saleItems[i];
        totalEarnest += saleItem.startPrice * 0.2
      }
      totalEarnest += this.state.startPrice * 0.2
      let userAccount = await getUserAccount();
      if (userAccount.earnestCoins >= totalEarnest) {
        this.setState({
          isBidModalVisible: true
        })
      } else {
        let needMoney = totalEarnest - userAccount.earnestCoins;
        this.setState({
          needMoney
        })
        confirm({
          title: '竞拍保证费不足',
          icon: <ExclamationCircleOutlined/>,
          content: `您还需要缴纳${(needMoney / 100).toLocaleString()}电子竞拍保证费，是否缴纳？`,
          onOk() {
            that.openPayModal();
          },
          onCancel() {
            message.error("竞拍失败");
          },
        });
      }
    }
  }

  bid = async () => {
    let that = this;
    confirm({
      title: '确定要加价吗？',
      icon: <ExclamationCircleOutlined/>,
      content: '竞价成功后将通过积分支付',
      async onOk() {
        const res = await bid(that.state.price * 100, that.state.id);
        if (res.status === 400) {
          message.error('请输入正确的竞价价格');
        } else if (res.status === 404) {
          message.error('商品不存在，请确认是否下架');
        } else {
          message.success('竞价成功');
          that.componentDidMount();
          that.setState({
            isBidModalVisible: false
          })
        }
      },
    });
  }

  openPayModal() {
    this.setState({
      isPayModalVisible: true
    })
  }

  closePayModal() {
    this.setState({
      isPayModalVisible: false
    })
  }

  postLike = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await postLike(thingId, 0);
    if (res.infoCode === 10000) {
      await this.updateLike();
      message.success("状态变更成功")
    }
  }

  updateLike = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await getLike(thingId, 0);
    this.setState({
      isLike: res.isLike,
      likeNum: res.likeNum
    })
  }

  postShare = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await addShare(thingId, "");
    if (res.infoCode === 10000) {
      await this.updateShare();
      message.success("分享成功")
    }
  }

  updateShare = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await getShare(thingId);
    this.setState({
      shareNum: res.count
    })
  }

  postDislike = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await addDislike(thingId, "");
    if (res.infoCode === 10000) {
      await this.updateDislike();
      message.success("举报成功")
    }
  }

  updateDislike = async () => {
    const thingId = this.state.thing ? this.state.thing.id : 0;
    const res = await getDislike(thingId);
    this.setState({
      dislikeNum: res.count
    })
  }

  render() {
    return (
      <div className={saleStyles.container}>
        <PayModal
          onOk={async () => {
            const res = await payEarnest(this.state.needMoney);
            if (res.infoCode === 10000) {
              message.success("拍卖保证金充值成功");
              this.setState({
                isBidModalVisible: true
              });
              this.closePayModal();
            } else {
              message.error("充值失败，余额不足");
              this.props.history.push(`/account/center/wallet`);
            }
          }}
          isPayNumModalVisible={this.state.isPayModalVisible}
          onCancel={() => {
            this.closePayModal();
          }}/>
        <Modal
          title="竞价"
          visible={this.state.isBidModalVisible}
          onOk={() => this.bid()}
          onCancel={() => {
            this.setState({
              isBidModalVisible: false
            })
          }}
        >
          <div>距离开始：{`${this.state.dayDiff}天${this.state.hourDiff}时${this.state.minuteDiff}分${this.state.secondDiff}秒`}</div>
          <div>起拍价：{(this.state.currPrice / 100).toLocaleString()} 电子</div>
          <div>加价幅度：{(this.state.intervalPrice / 100).toLocaleString()} 电子</div>
          {/* 拍卖保证金：{this.state} 电子 */}
          <div>物品起拍价低于{99.99}时，保证金余额为起拍价的10%；高于{99.99}时，保证金约为起拍价的20%。</div>
          <div style={{display: 'flex'}}>出价：<InputNumber
            style={{flexGrow: 2}}
            size="large"
            value={this.state.price}
            onChange={(value) => {
              this.setState({
                price: value
              })
            }}
          />
          </div>
          竞价周期：{99}天
        </Modal>
        <div style={{display: 'flex', justifyContent: 'space-between', margin: "0 auto"}}>
          <div className={saleStyles.leftSider}>
            <div className={saleStyles.leftSiderContainer}>
              {this.state.bidType === '未开始' ?
                (<div style={{
                  backgroundColor: '#5EAF5E',
                  width: '80px',
                  textAlign: 'center',
                  color: '#fff',
                  borderRadius: '4px',
                  padding: '3px'
                }}>
                  {this.state.bidType}
                </div>)
                :
                (<div style={{
                  backgroundColor: '#fe2341',
                  width: '80px',
                  textAlign: 'center',
                  color: '#fff',
                  borderRadius: '4px',
                  padding: '3px'
                }}>
                  {this.state.bidType}
                </div>)}
              <div className={saleStyles.title}>
                <span className={saleStyles.titleText}>{this.state.thing.name}</span>
              </div>
              <div className={saleStyles.info}>
                <div className={saleStyles.infoItem}>{this.state.thing.description}</div>
                <div style={{display: 'flex', flexDirection: 'column'}}>
                  {this.state.bidCountText}：
                  <div>
                    <span style={{fontSize: '24px', fontWeight: 'bolder', margin: '0 2px'}}>{this.state.dayDiff}</span>
                    天
                    <span style={{fontSize: '24px', fontWeight: 'bolder', margin: '0 2px'}}>{this.state.hourDiff}</span>
                    时
                    <span
                      style={{fontSize: '24px', fontWeight: 'bolder', margin: '0 2px'}}>{this.state.minuteDiff}</span>
                    分
                    <span
                      style={{fontSize: '24px', fontWeight: 'bolder', margin: '0 2px'}}>{this.state.secondDiff}</span>
                    秒
                  </div>
                  <div>起拍价：
                    <span
                      style={{
                        fontSize: '22px',
                        color: '#fe2341',
                        marginRight: '2px'
                      }}>{(this.state.startPrice / 100).toLocaleString()}</span>
                    电子
                  </div>
                  <div>加价幅度：
                    <span
                      style={{
                        fontSize: '22px',
                        color: '#fe2341',
                        marginRight: '2px'
                      }}>{(this.state.intervalPrice / 100).toLocaleString()}</span>
                    电子
                  </div>
                  {this.state.needEarnestMoney ? (<div>保证金：<span style={{
                    marginRight: '2px',
                    fontSize: '22px',
                    color: '#fe2341'
                  }}>{(this.state.startPrice / 100 / 5).toFixed(2)}</span>电子</div>) : '无需保证金'}
                  <div style={{lineHeight: '32px'}}>竞价周期：1天</div>
                  <div style={{lineHeight: '32px'}}>延时周期：5分钟</div>
                  <div style={{lineHeight: '32px'}}>买断价格：该商品暂不支持买断</div>
                </div>
              </div>
              <div className={saleStyles.btns}>
                <Button className={saleStyles.btn} onClick={() => {
                  this.checkEarnest();
                }}>立即竞价</Button>
                <Button disabled className={saleStyles.btn}>立即买断</Button>
              </div>
            </div>
          </div>
          <div className={saleStyles.media}>
            <div className={saleStyles.mediaContainer}>
              <Image src={this.state.thing.url}/>
            </div>
          </div>
          <div className={saleStyles.rightSider}>
            <div className={saleStyles.rightSiderContainer}>
              <div className={saleStyles.users}>
                <div className={saleStyles.userItem}>
                  <Avatar size={40} src={this.state.author.avatarUrl}/>
                  <div className={saleStyles.userText}>
                    <span style={{display: "inline-block"}}>@{this.state.author.nickname}</span>
                    <span style={{color: "#656565", fontSize: "10px"}}>{"艺术家"}</span>
                  </div>
                </div>
                <div className={saleStyles.userItem}>
                  <Avatar size={40} src={this.state.owner.avatarUrl}/>
                  <div className={saleStyles.userText}>
                    <span>@{this.state.owner.nickname}</span>
                    <span style={{color: "#656565", fontSize: "10px"}}>{"拥有者"}</span>
                  </div>
                </div>
              </div>
              <div className={saleStyles.infos}>
                <div className={saleStyles.infoItem}>
                  {
                    this.state.isLike ?
                      <HeartTwoTone twoToneColor={"red"} onClick={() => this.postLike()} className="icon"/> :
                      <HeartOutlined onClick={() => this.postLike()} className="icon"/>
                  }
                  <div className={saleStyles.infoText}>
                    <span style={{fontWeight: 600}}>{this.state.likeNum}</span>
                    <span style={{color: "#656565"}}>{"喜欢"}</span>
                  </div>
                </div>
                <div className={saleStyles.infoItem}>
                  <ShareAltOutlined className="icon" onClick={() => this.postShare()}/>
                  <div className={saleStyles.infoText}>
                    <span style={{fontWeight: 600}}>{this.state.shareNum}</span>
                    <span style={{color: "#656565"}}>{"分享"}</span>
                  </div>
                </div>
                <div className={saleStyles.infoItem}>
                  <WarningOutlined className="icon" onClick={() => this.postDislike()}/>
                  <div className={saleStyles.infoText}>
                    <span style={{fontWeight: 600}}>{this.state.dislikeNum}</span>
                    <span style={{color: "#656565"}}>{"举报"}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className={saleStyles.historyContainer}>
          <div className={saleStyles.historyTitle}>
            <span>{"历史记录"}</span>
          </div>
          <List
            itemLayout="horizontal"
            dataSource={this.state.historyList}
            renderItem={item => (
              <List.Item>
                <List.Item.Meta
                  style={{paddingBottom: 24, borderBottom: "1px solid #cfcfcf", maxWidth: 420}}
                  title={<span>
                    <Link to="">@{item.user.nickname}</Link>
                    出价：{(item.price / 100).toLocaleString()}电子
                  </span>
                  }
                  description={
                    <span>
                      {getDateDiff(item.createTime)}&nbsp;&nbsp;&nbsp;&nbsp;
                      <Link to="">{"查看详情"}</Link>
                    </span>
                  }
                />
              </List.Item>
            )}
          />
        </div>

        <div className={saleStyles.moreArtwork}>
          <h2>{"作品拥有者"}的更多作品</h2>
          <div>
            <List
              grid={{gutter: 16, column: 5}}
              dataSource={this.state.authorArtworks}
              renderItem={item => (
                <List.Item>
                  <Link to={`/auction/${item.id}`}>
                    <div className={saleStyles.moreArtCover}>
                      <img alt="" className={saleStyles.moreArtImg} src={item.url}/>
                      <span className={saleStyles.moreArtText}>{item.name}</span>
                    </div>
                  </Link>
                </List.Item>
              )}
            />
          </div>
          <div>
            {/*<Link className={saleStyles.moreArtNav} to="">查看@{"艺术家"}的更多作品</Link>*/}
          </div>
        </div>
        {/* <Row>
          <Col span={18}>
            <Card style={{marginRight: '10px', height: '100%'}}>
              <img style={{width: '100%'}} src={this.state.thing.url}/>
            </Card>
          </Col>
          <Col span={6}>
            <Card style={{marginLeft: '10px', height: '100%', position: 'relative'}}>
              <div style={{marginBottom: '10px'}}>
                <Descriptions title={this.state.thing.name} column={1}>
                  <Descriptions.Item label="作者"><Avatar size="small"
                                                        style={{margin: '5px', marginTop: '-5px', marginBottom: 0}}
                                                        src={this.state.author.avatarUrl}
                                                        alt="avatar"/>{this.state.author.nickname}</Descriptions.Item>
                  <Descriptions.Item label="拥有者"><Avatar size="small"
                                                         style={{margin: '5px', marginTop: '-5px', marginBottom: 0}}
                                                         src={this.state.owner.avatarUrl}
                                                         alt="avatar"/>{this.state.owner.nickname}</Descriptions.Item>
                  <Descriptions.Item label="简介">{this.state.thing.description}</Descriptions.Item>
                </Descriptions>
                <Row>
                  <Col span={8}>
                    <span
                      style={{
                        color: '#999',
                        fontSize: '14px',
                        marginRight: '20px',
                      }}>起拍价</span>
                  </Col>
                  <Col span={16}>
                    <span style={{color: '#999', fontSize: '14px'}}>￥ {this.state.startPrice}</span>
                  </Col>
                </Row>
              </div>
              <div style={{marginBottom: '10px'}}>
                <Row>
                  <Col span={8}>
                  <span
                    style={{
                      color: '#999',
                      fontSize: '14px',
                      marginRight: '20px'
                    }}>最小加价幅度</span>
                  </Col>
                  <Col span={16}>
                    <span style={{color: '#999', fontSize: '14px'}}>￥ {this.state.intervalPrice}</span>
                  </Col>
                </Row>
              </div>
              <div style={{marginBottom: '10px'}}>
                <Row>
                  <Col span={8}>
                  <span
                    style={{
                      color: '#999',
                      fontSize: '14px',
                      marginRight: '20px'
                    }}>拍卖开始时间</span>
                  </Col>
                  <Col span={16}>
                    <span style={{color: '#999', fontSize: '14px'}}>{formatDate(this.state.startTime)}</span>
                  </Col>
                </Row>
              </div>
              <div style={{marginBottom: '10px'}}>
                <Row>
                  <Col span={8}>
                  <span
                    style={{
                      color: '#999',
                      fontSize: '14px',
                      marginRight: '20px'
                    }}>拍卖结束时间</span>
                  </Col>
                  <Col span={16}>
                    <span style={{color: '#999', fontSize: '14px'}}>{formatDate(this.state.endTime)}</span>
                  </Col>
                </Row>
              </div>
              <div style={{marginBottom: '10px'}}>
                <Row>
                  <Col span={8}>
                  <span
                    style={{
                      color: '#999',
                      fontSize: '14px',
                      marginRight: '20px'
                    }}>上次出价</span>
                  </Col>
                  <Col span={8}>
                    <span style={{color: '#999', fontSize: '14px'}}>￥ {this.state.minePrice}</span>
                  </Col>
                </Row>
              </div>
              <div style={{position: 'absolute', bottom: 0, width: '88%'}}>
                <div style={{marginBottom: '10px'}}>
                  <Row>
                    <Col span={8}>
                  <span
                    style={{
                      color: '#666',
                      fontSize: '26px'
                    }}>当前价</span>
                    </Col>
                    <Col span={16}>
                      <span style={{color: '#f96600', fontSize: '26px'}}>￥ {this.state.currPrice}</span>
                    </Col>
                  </Row>
                </div>
                <Button style={{width: '100%', marginBottom: '20px'}} size="large" type="primary" onClick={() => {
                  this.setState({
                    isBidModalVisible: true
                  })
                }}>竞价</Button>
              </div>
            </Card>
          </Col>
        </Row> */}
        {/*<div>*/}
        {/*  <h1 style={{fontSize: '22px', marginTop: '20px'}}>相似推荐</h1>*/}
        {/*  <div>*/}
        {/*    <List<CommodityItem>*/}
        {/*      rowKey="id"*/}
        {/*      grid={{gutter: 24, xl: 4, lg: 3, md: 3, sm: 2, xs: 1}}*/}
        {/*      dataSource={this.state.commodities}*/}
        {/*      renderItem={(item) => (*/}
        {/*        <List.Item key={item.id} style={{textAlign: 'center'}}>*/}
        {/*          <Link*/}
        {/*            to={`/commodity/${item.id}`}>*/}
        {/*            <Card className={styles.card} hoverable*/}
        {/*                  cover={<img alt={item.title} src={item.coverUrl}/>}>*/}
        {/*              <Card.Meta title={<a>{item.title}</a>}/>*/}
        {/*            </Card>*/}
        {/*          </Link>*/}
        {/*        </List.Item>*/}
        {/*      )}*/}
        {/*    />*/}
        {/*  </div>*/}
        {/*</div>*/}
      </div>
    );
  }
}

export default connect(
  ({loading}: ConnectState) =>
    ({
      loading: loading.effects["resource/searchScripts"]
    }),
)
(Sale);

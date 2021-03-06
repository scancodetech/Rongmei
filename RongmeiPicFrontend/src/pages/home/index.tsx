import React, {Component} from 'react';

import {Carousel, Col, Row, Button} from 'antd';
import {MenuDataItem} from '@ant-design/pro-layout';
import {ConnectProps} from "@@/plugin-dva/connect";
import {connect, Link} from "umi";
import {ConnectState} from "@/models/connect";
import {getTCC} from "@/services/tcc";
import {
  getAuctionTransactionHistories,
  getThingByTokenId,
  getTopArtist,
  getStatistics, getTopTags
} from "@/services/auction";
import {getCommodities} from "@/services/commodity";
import homeStyles from "./home.less";

interface HomeProps extends Partial<ConnectProps> {
  loading: boolean;
  menuData: MenuDataItem[];
  key: string;
}

class Home extends Component<HomeProps> {
  state = {
    keywords: [],
    events: [],
    commodities: [],
    key: '',
    currentTrans: [],
    topTags: [],
    topArtists: [],
    thingNum: 999,
    saleNum: 999,
    ethNum: 999,
    money: 999,
  }

  async componentDidMount(): void {
    const {dispatch} = this.props;
    let res = await getTCC('rongmei.pic.searchkeywords')
    let eventRes = await getTCC('rongmei.pic.event')
    this.setState({
      keywords: eval(res.tccTuple.value),
      events: eval(eventRes.tccTuple.value)
    })
    this.getRecommendCommodities();
    this.getRecentTrans();
    this.getTopUsers();
    this.getWholeStatistics();
    let token = localStorage.getItem('token');
    if (!token || token.length === 0) {
      if (dispatch) {
        dispatch({
          type: 'user/changeLoginShow',
          payload: {
            isShowLogin: true
          }
        });
      }
    }
  }

  async getWholeStatistics() {
    const res = await getStatistics();
    this.setState({
      thingNum: res.thingNum,
      saleNum: res.saleNum,
      ethNum: res.ethNum,
    })
    this.setState({
      money: res.ethNum
    });
  }

  async getRecentTrans() {
    const res = await getAuctionTransactionHistories({
      limit: 3,
      offset: 0,
    });
    let transHistory = res.auctionTransactionHistories;
    transHistory.forEach(async (value, key) => {
      let thingRes = await getThingByTokenId(value.tokenId);
      transHistory[key].thing = thingRes;
    });
    this.setState({
      currentTrans: transHistory,
    })
  }

  async getTopUsers(): void {
    const topArtistRes = await getTopArtist({
      limit: 3,
      offset: 0
    })
    const endTime = new Date().getTime();
    const startTime = endTime - 7 * 24 * 60 * 60 * 1000
    const nftTypeRes = await getTCC('dimension.nft.type');
    const typeTcc = eval(nftTypeRes.tccTuple.value);
    let tccMap = {};
    for (let i = 0; i < typeTcc.length; i++) {
      tccMap[typeTcc[i].tag] = typeTcc[i].name;
    }
    const topTagsRes = await getTopTags(startTime, endTime);
    let topTagNames: string[] = [];
    for (let i = 0; i < topTagsRes.tagHotItems.length; i++) {
      topTagNames = topTagNames.concat(tccMap[topTagsRes.tagHotItems[i].tag]);
    }
    topArtistRes.auctionArtistItems.forEach((item, index) => {
      if (item.id === 0)
        topArtistRes.auctionArtistItems.pop(index);
    })
    this.setState({
      topTags: topTagNames,
      topArtists: topArtistRes.auctionArtistItems
    })
  }

  handleFormSubmit = (value: string) => {
    const {dispatch} = this.props;
    if (dispatch) {
      dispatch({
        type: 'commodity/searchCommodities',
        payload: {
          tags: [],
          key: value,
          orderKey: 'all',
          offset: 0,
          limit: 20
        }
      });
    }
    this.props.history.push(`/search/${value}`)
  };

  getRecommendCommodities = async () => {
    const response = await getTCC("rongmei.orient.secondtype");
    const allType = eval(response.tccTuple.value.replace(/\s*/g, ""));
    const res = await getCommodities({
      tags: [allType[0][0].typeList[0]],
      key: '',
      orderKey: 'all',
      offset: 0,
      limit: 10
    })
    this.setState({
      commodities: res.commodities
    })
  }

  render() {
    return (
      <div className={homeStyles.auctionContainer}>
        <div className={homeStyles.auctionHero}>
          <div className={homeStyles.heroTextContainer}>
            <div className={`${homeStyles.heroText} left`}>
              ??????
            </div>
            <div className={`${homeStyles.heroText} right`}>
              <span className={homeStyles.heroHighlightText}>
                ?????????
              </span>
              <span className={homeStyles.heroTextSecondLine}>
                ??????????????????
              </span>
            </div>
          </div>
          <div className={homeStyles.heroLinks}>
            <Link to="/auction">
              <Button className={homeStyles.homeHeroCtaBtn}>
                ????????????
              </Button>
            </Link>
            <Link className={homeStyles.heroSecondaryLink} to="/auction">
              ????????????
            </Link>
          </div>
        </div>
        <Carousel
          className={homeStyles.banner}
          autoplay
          dotPosition="bottom">
          {this.state.events.map((val, index) => (
            <div className={homeStyles.bannerItem}>
              <img className={homeStyles.bannerImg} alt="" src={val.coverUrl}/>
            </div>
          ))}
        </Carousel>
        <div className={homeStyles.auctionState}>
          <div className={homeStyles.auctionStateItem}>
            <div>
              <span className={homeStyles.auctionStateItemNumber}>{this.state.thingNum}</span>
              <span className={homeStyles.auctionStateItemText}>????????????????????????</span>
            </div>
          </div>
          <div className={homeStyles.auctionStateItem}>
            <div>
              {/* to-do ??????????????? */}
              <span className={homeStyles.auctionStateItemNumber}>??{this.state.money}</span>
              <span className={homeStyles.auctionStateItemText}>???????????????</span>
            </div>
          </div>
          <div className={homeStyles.auctionStateItem}>
            <div>
              {/* to-do ??????????????? */}
              <span className={homeStyles.auctionStateItemNumber}>{this.state.saleNum}</span>
              <span className={homeStyles.auctionStateItemText}>????????????</span>
            </div>
          </div>
          {/* to-do ???????????? */}
          {/* <div className={homeStyles.auctionStateItem}>
            <div>
              <span className={homeStyles.auctionStateItemNumber}>{46}</span>
              <span className={homeStyles.auctionStateItemText}>??????</span>
            </div>
          </div> */}
        </div>
        <div className={homeStyles.auctionInfoContainer}>
          <div className={homeStyles.auctionInfo}>
            <div className={homeStyles.infoTitlecontainer}>
              <h2 className={homeStyles.infoTitle}>????????????</h2>
              <h2 className={homeStyles.infoTitle}>??????????????????</h2>
            </div>
            <div className={`${homeStyles.infoSection} first`}>
              <h6 className={homeStyles.infoSectionTitle}>????????????????????????????????????</h6>
              <p>?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</p>
            </div>
            <div className={`${homeStyles.infoSection} second`}>
              <h6 className={homeStyles.infoSectionTitle}>??????</h6>
              <p>?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</p>
            </div>
            <div className={`${homeStyles.infoSection} third`}>
              <h3 className={homeStyles.infoSectionTitle}>??????????????????</h3>
              <p>????????????????????????????????????????????????????????????????????????????????????????????????????????????VR???????????????????????????????????????????????????????????????????????????</p>
            </div>
          </div>
          <Link to="/auction" className={homeStyles.auctionInfoLink}
                style={{margin: "5em auto 0px", display: "block", textAlign: "center"}}>
            <Button className={homeStyles.homeHeroCtaBtn}>????????????</Button>
          </Link>
        </div>
        <div className={homeStyles.auctionFeatureSection}>
          {/* <div>
            <h2 className={homeStyles.recentActivityTitle}>????????????</h2>
            <Row className={homeStyles.auctionActivityGrid}>
              {
                this.state.currentTrans.map((item) => (
                  <Col xs={24} sm={8} md={8} className={homeStyles.auctionActivityGridItem}>
                    <div>
                      <div className={homeStyles.recentAuctionItem}>
                        <div className={homeStyles.squareBox} style={{marginBottom: "2em"}}>
                          <div className={homeStyles.squareContent}>
                            <Link to="/activity">
                              <div className={homeStyles.artworkThumbnailContainer}>
                                <div>
                                  <div>
                                    <div className={homeStyles.recentActivityItemImg}>
                                      <img src={item.thing == null ? '' : item.thing.url}
                                           className={homeStyles.recentActivityItemImg} alt=""/>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </Link>
                          </div>
                        </div>
                        <div className={homeStyles.artworkThumbnailIcon}>
                          <img src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png" alt="avatar"
                               className={homeStyles.activityAvatar}/>
                        </div>
                        <span className={homeStyles.recentActivityAction}>
                          <a className={homeStyles.recentActivityItemUserLink}>@{item.nickname}xxx</a>
                          <span>???</span>
                          <a className={homeStyles.recentActivityItemArtLink}>{item.name}</a>
                          <span>{"????????????"}</span>
                          <span>{`${item.eth}IMM(??${(item.eth / 100).toFixed(2)})`}</span>
                          <br/>
                          <span>2?????????</span>
                        </span>
                      </div>
                    </div>
                  </Col>
                ))
              }
            </Row>
            <div style={{textAlign: 'center', marginBottom: "5em"}}>
              <Link to="/record"
                    style={{
                      marginTop: 0,
                      marginBottom: 0,
                      marginLeft: 'auto',
                      marginRight: 'auto',
                      display: 'block',
                    }}
              >
                <Button className={homeStyles.homeHeroCtaBtn}>??????????????????</Button>
              </Link>
            </div>
          </div> */}
          <div className={homeStyles.auctionInfoContainer}
               style={{paddingTop: 100, borderTop: "1 solid rgb(0, 0, 0)",}}>
            <div className={homeStyles.auctionInfo}>
              <div className={homeStyles.infoTitleContainer}>
                <h2 className={homeStyles.infoTitle}>????????????</h2>
                <h2 className={homeStyles.infoTitle}>??????????????????</h2>
                <h2 className={homeStyles.infoTitle}>?????????</h2>
              </div>
              <div className={`${homeStyles.infoSection} first`}>
                <h6 className={homeStyles.infoSectionTitle}>????????????</h6>
                <p>??????????????????????????????????????????????????????????????????????????????????????????</p>
              </div>
              <div className={`${homeStyles.infoSection} second`}>
                <h6 className={homeStyles.infoSectionTitle}>????????????</h6>
                <p>?????????????????????????????????????????????????????????????????????</p>
              </div>
              <div className={`${homeStyles.infoSection} third`}>
                <h6 className={homeStyles.infoSectionTitle}>?????????????????????</h6>
                <p>?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</p>
              </div>
            </div>
            <a
              // target="_blank"
              href="https://www.wenjuan.in/s/UZBZJvAGBC/"
              target="_blank"
              style={{
                marginTop: '5em',
                marginBottom: 0,
                marginLeft: 'auto',
                marginRight: 'auto',
                display: 'block',
                textAlign: 'center',
              }}
            >
              <Button className={homeStyles.homeHeroCtaBtn}>????????????</Button>
            </a>
          </div>
          {/* to-do ??????????????? */}
          <div className={homeStyles.auctionChartsSection}>
            <h2>????????????</h2>
            <div className={homeStyles.auctionChartsCard}>
              <span className={homeStyles.titleSection}>
                {/* <a className={homeStyles.title} href=""> */}
                ???????????????
                {/* </a> */}
                <span className={homeStyles.subtitle}>????????????</span>
              </span>
              <hr className={homeStyles.titleDivider}/>
              {
                this.state.topArtists.map((item) => (
                  <div className={homeStyles.entry}>
                    <Link to={{
                      pathname: `/person/${item.code}`,
                    }}>
                      <span className={homeStyles.user}>
                        <div className={homeStyles.userAvatar}>
                          <img alt="" src={item.avatarUrl} className={homeStyles.avatarImg}/>
                        </div>
                        <p>
                          {`@${item.nickname}`}
                        </p>
                      </span>
                    </Link>
                    <span className={homeStyles.quantity}>123</span>
                  </div>
                ))
              }
              {/* <a className={homeStyles.linkSeeAll} href="/top-artists">????????????</a> */}
            </div>
            <div className={homeStyles.auctionChartsCard}>
              <span className={homeStyles.titleSection}>
                {/* <a className={homeStyles.title} href=""> */}
                ????????????
                {/* </a> */}
                <span className={homeStyles.subtitle}>????????????</span>
              </span>
              <hr className={homeStyles.titleDivider}/>
              {
                this.state.topTags.length > 0 ? this.state.topTags.map((item) => (
                  <div className={homeStyles.entry}>
                    <Link to={`/auction`}>
                      <span className={homeStyles.user}>
                        <div className={homeStyles.userAvatar}>
                        </div>
                        <p>
                          {item.tag}
                        </p>
                      </span>
                    </Link>
                    <span className={homeStyles.quantity}>{item.count}</span>
                  </div>
                )) : ""
              }
              {/* <a className={homeStyles.linkSeeAll} href="/top-artists">????????????</a> */}
            </div>
            <div className={homeStyles.cta}>
              <Link to="/auction">
                <Button className={homeStyles.homeHeroCtaBtn}>
                  ????????????
                </Button>
              </Link>
            </div>
          </div>

        </div>
      </div>

      // <PageHeaderWrapper
      //   title={false}
      //   content={mainSearch}
      // >
      //   <div style={{textAlign: 'center'}}>
      //     <h1 style={{fontSize: '26px'}}>????????????????????????</h1>
      //     <h5>????????????????????????????????????????????????????????????????????????????????????</h5>
      //     <Row gutter={16} style={{marginTop: '20px'}}>
      //       {
      //         this.state.events.map((event) => (
      //           <Col span={8}>
      //             <Card
      //               hoverable
      //               cover={<img src={event.coverUrl}/>}
      //             >
      //               <Card.Meta title={event.title} description={event.description}/>
      //             </Card>
      //           </Col>
      //         ))
      //       }
      //     </Row>
      //   </div>
      //   <div style={{textAlign: 'center', marginTop: '30px', width: '100%'}}>
      //     <Tabs defaultActiveKey={menuData && menuData.length > 0 ? menuData[0] : ''} onChange={async () => {
      //       this.getRecommendCommodities()
      //     }}>
      //       {
      //         menuData.map((item) => (
      //           <Tabs.TabPane tab={item.name} key={item.name}>
      //             <List<CommodityItem>
      //               rowKey="id"
      //               grid={{gutter: 24, xl: 4, lg: 3, md: 3, sm: 2, xs: 1}}
      //               dataSource={this.state.commodities}
      //               renderItem={(item) => (
      //                 <List.Item key={item.id}>
      //                   <Link
      //                     to={`/commodity/${item.id}`}>
      //                     <Card className={styles.card} hoverable
      //                           cover={<img alt={item.title} src={item.coverUrl}/>}>
      //                       <Card.Meta title={<a>{item.title}</a>}/>
      //                     </Card>
      //                   </Link>
      //                 </List.Item>
      //               )}
      //             />
      //           </Tabs.TabPane>
      //         ))
      //       }
      //     </Tabs>
      //   </div>
      // </PageHeaderWrapper>
    );
  }
}

export default connect(
  ({loading, menuData, commodity}: ConnectState) =>
    ({
      loading: loading.effects["resource/searchScripts"],
      menuData: menuData.menuData,
      key: commodity.key
    }),
)
(Home);

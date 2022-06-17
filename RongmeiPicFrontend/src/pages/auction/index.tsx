import React, {Component} from 'react';

import {Select, Card, List, Switch} from 'antd';
import {ConnectProps} from "@@/plugin-dva/connect";
import {connect, Link} from "umi";
import {ConnectState} from "@/models/connect";
import {getTCC} from "@/services/tcc";
import {getSales} from "@/services/auction";
import auctionStyles from "./auction.less";
import {Tag} from 'antd';
import {getUserInfoEntity} from "@/services/user";

const {CheckableTag} = Tag;
const {Option} = Select;

interface AuctionProps extends Partial<ConnectProps> {
  loading: boolean;
  key: string;
}

class Auction extends Component<AuctionProps> {

  state = {
    keywords: [],
    sales: [],
    key: '',
    page: 1,
    limit: 9,
    total: 10,
    outdated: false,
    ownedByAuthor: false,
    // 0: 最近活跃, 1: 价格（低到高）, 2: 价格（高到低）, 3: 最近发布
    rankType: 0,
    tags: [],
    selectedTags: [],
  }

  async componentDidMount(): void {
    let keyWordRes = await getTCC('rongmei.pic.searchkeywords')
    this.setState({
      keywords: eval(keyWordRes.tccTuple.value)
    })
    let tagRes = await getTCC('dimension.nft.type')
    this.setState({
      tags: eval(tagRes.tccTuple.value)
    })
    this.getSales()
  }

  async getSales() {
    let tags = [];
    this.state.selectedTags.forEach((selectedTag) => {
      tags = tags.concat(selectedTag.tag);
    })
    let res = await getSales({
      key: this.state.key,
      limit: this.state.limit,
      offset: (this.state.page - 1) * this.state.limit,
      outdated: !this.state.outdated,
      ownedByAuthor: !this.state.ownedByAuthor,
      rankType: this.state.rankType,
      tags
    })
    let saleItems = res.saleItems;
    let userNicknameMap = new Map();
    for (let i = 0; i < saleItems.length; i++) {
      let item = saleItems[i];
      let author = item.thing.author;
      if (userNicknameMap.has(author)) {
        saleItems[i].authorNickname = userNicknameMap.get(author);
      } else {
        const res = await getUserInfoEntity(author);
        if (res.nickname) {
          saleItems[i].authorNickname = res.nickname;
          userNicknameMap.set(author, res.nickname);
        }
      }
      let owner = item.thing.owner;
      if (userNicknameMap.has(owner)) {
        saleItems[i].ownerNickname = userNicknameMap.get(owner);
      } else {
        const res = await getUserInfoEntity(owner);
        if (res.nickname) {
          saleItems[i].ownerNickname = res.nickname;
          userNicknameMap.set(owner, res.nickname);
        }
      }
    }
    this.setState({
      sales: res.saleItems
    })
  }

  onIsOwnedByAuthor = (value: boolean) => {
    this.setState({
      ownedByAuthor: value,
    }, () => {
      this.handleFormSubmit();
    });
  }

  onOutdates = (value: boolean) => {
    this.setState({
      outdates: value,
    }, () => {
      this.handleFormSubmit();
    })

  }

  onRankType = (value: number) => {
    this.setState({
      rankType: value,
    }, () => {
      this.handleFormSubmit();
    })
  }

  async onChange(pageNumber) {
    const res = await getSales({
      key: this.state.key,
      limit: this.state.limit,
      offset: (this.state.page - 1) * this.state.limit,
      outdated: !this.state.outdated,
      ownedByAuthor: !this.state.ownedByAuthor,
      rankType: this.state.rankType,
      tags: this.state.selectedTags
    });
    this.setState({
      sales: res.saleItems,
      page: pageNumber
    })
  }

  handleFormSubmit = async () => {
    const res = await getSales({
      key: this.state.key,
      limit: this.state.limit,
      offset: 0,
      outdated: this.state.outdated,
      ownedByAuthor: this.state.ownedByAuthor,
      // 0: 最近活跃, 1: 价格（低到高）, 2: 价格（高到低）, 3: 最近发布
      rankType: this.state.rankType,
      tags: this.state.selectedTags,
    });
    this.setState({
      sales: res.saleItems
    })
  };

  handleChange(tag, checked) {
    const {selectedTags} = this.state;
    const nextSelectedTags = checked ? [...selectedTags, tag] : selectedTags.filter(t => t !== tag);
    console.log('You are interested in: ', nextSelectedTags);
    this.setState({selectedTags: nextSelectedTags}, () => {
      console.log(this.state.selectedTags);
    });
    this.getSales();
  }

  render() {
    const {total} = this.props;
    return (
      <div className={auctionStyles.auctionContainer}>
        <div className={auctionStyles.headTitle}>
          竞价市场
        </div>
        <div className={auctionStyles.headOptions}>
          <div className={auctionStyles.filterOptions}>
            <div className={auctionStyles.optionText}>筛选:</div>
            <div className={auctionStyles.filterBox}>
              {/* to-do 暂无 标价/竞价 之分，统一竞价 */}
              {/* <Switch defaultChecked />
              <span className={auctionStyles.filterText}>已标价</span> */}
              {/* <Switch defaultChecked />
              <span className={auctionStyles.filterText}>有公开竞标</span> */}
              <div className={auctionStyles.filterRow}>
                <span className={auctionStyles.filterText}>内容分类：</span>
                {this.state.tags.map((item) => (
                  <CheckableTag
                    className={auctionStyles.tagBox}
                    key={item.tag}
                    checked={this.state.selectedTags.indexOf(item) > -1}
                    onChange={checked => this.handleChange(item, checked)}
                  >
                    <span style={{fontSize: 14}}>{item.name}</span>
                  </CheckableTag>
                ))}
              </div>
              <div className={auctionStyles.filterRow}>
                <span className={auctionStyles.filterText}>权利分类：</span>
                <Switch
                  autoFocus
                  checked={this.state.ownedByAuthor}
                  onChange={this.onIsOwnedByAuthor}
                />
                <span className={auctionStyles.filterText}>创作者拥有</span>
                <Switch
                  autoFocus
                  checked={this.state.outdates}
                  onChange={this.onOutdates}
                />
                <span className={auctionStyles.filterText}>已售出</span>
              </div>
            </div>
          </div>
          <div className={auctionStyles.sortOptions}>
            <div className={auctionStyles.optionText}>排序方式:</div>
            <div>
              <Select
                onChange={this.onRankType}
                defaultValue={0}>
                <Option value={0}>最近活跃</Option>
                <Option value={1}>价格由低到高</Option>
                <Option value={2}>价格由高到低</Option>
                <Option value={3}>最新发布</Option>
              </Select>
            </div>
          </div>
        </div>
        <div>
          <List
            rowKey="id"
            style={{margin: "30px 0"}}
            pagination={{
              onChange: async page => {
                await this.setState({
                  page
                })
                this.getSales();
              },
              pageSize: this.state.limit,
              total: this.state.total
            }}
            grid={{gutter: 40, xxl: 3, xl: 3, lg: 3, md: 3, sm: 2, xs: 1}}
            //隐藏-todo
            dataSource={this.state.sales}
            // dataSource={[]}
            renderItem={(item) => (
              <List.Item key={item.id}>
                <Link
                  to={`/auction/${item.id}`}>
                  <Card className={auctionStyles.card} hoverable
                        cover={
                          <div className={auctionStyles.cardCover}>
                            <img
                              style={{
                                borderRadius: "10px 10px 0 0",
                                height: "360px",
                              }}
                              alt={item.thing.name} src={item.thing.url}/>
                          </div>
                        }>
                    <Card.Meta
                      title={
                        <div className={auctionStyles.info}>
                          <div>
                            <span style={{fontSize: "24px"}}>{item.thing.name}</span>
                          </div>
                          <div className={auctionStyles.bids}>
                            <div className={auctionStyles.listPrice}>
                              <div style={{
                                fontSize: "20px",
                                color: "#fe2431"
                              }}>{(item.currentPrice / 100).toLocaleString()} 电子
                              </div>
                              <span style={{color: "#656565", fontSize: "10px"}}>当前价格</span>
                            </div>
                            {/* to-do 当前出价人及当前出价 */}
                            {/* {item.currentBid != null &&
                              <div className={auctionStyles.currentBid}>
                                <div style={{fontSize: "20px", color: "#fe2431" }}>${item.currentBid}</div>
                                  {item.isBided?
                                  <span style={{color:"#656565", fontSize: "10px" }}>
                                  当前出价格者:<Link to="">@{item.bidderName}</Link>
                                  </span>
                                  :<span style={{color:"#656565", fontSize: "10px" }}>暂无人加价</span>}
                              </div>
                            } */}
                          </div>
                          <hr className={auctionStyles.sectionDivider}/>
                          <div className={auctionStyles.users}>
                            <div className={auctionStyles.userInfos}>
                              <span className={auctionStyles.userText}>创作者</span>
                              {/* <Avatar src={item.author.avatarUrl}/> */}
                              <Link
                                className={auctionStyles.usersLink}
                                to="">
                                <span>@</span>
                                <span>{item.authorNickname}</span>
                              </Link>
                            </div>
                            <div className={auctionStyles.userInfos}>
                              <span className={auctionStyles.userText}>拥有人</span>
                              {/* <Avatar src={item.owner.avatarUrl} /> */}
                              <Link
                                className={auctionStyles.usersLink}
                                to="">
                                <span>@</span>
                                <span>{item.ownerNickname}</span>
                              </Link>
                            </div>
                          </div>
                        </div>
                      }/>
                  </Card>
                </Link>
              </List.Item>
            )}
          />
        </div>
      </div>
    )
  }

  // render() {
  //   const {loading} = this.props;
  //   const mainSearch = (
  //     <div style={{textAlign: 'center', marginBottom: '20px'}}>
  //       <Input.Search
  //         placeholder="请输入关键词搜索"
  //         size="large"
  //         loading={loading}
  //         value={this.state.key}
  //         onChange={(e) => {
  //           this.setState({
  //             key: e.target.value
  //           })
  //         }}
  //         onSearch={this.handleFormSubmit}
  //         style={{maxWidth: 800, width: '100%', marginTop: '30px'}}
  //       />
  //       <div style={{marginTop: '10px', color: '#999'}}>
  //         推荐关键字：&nbsp;&nbsp;
  //         {
  //           this.state.keywords.map((keyword) => (
  //             <span style={{marginRight: '15px', cursor: 'pointer'}} onClick={() => {
  //               this.setState({
  //                 key: keyword
  //               })
  //             }}>{keyword}</span>))
  //         }
  //       </div>
  //     </div>
  //   );

  //   return (
  //     <PageHeaderWrapper
  //       title={false}
  //       content={mainSearch}
  //     >
  //       <div style={{textAlign: 'center'}}>
  //         <Row gutter={16} style={{marginTop: '20px'}}>
  //           {
  //             this.state.sales.map((sale) => (
  //               <Col span={8}>
  //                 <Link
  //                   to={`/auction/${sale.id}`}>
  //                   <Card
  //                     hoverable
  //                     style={{textAlign: 'left'}}
  //                     cover={<img src={sale.thing.url}/>}
  //                   >
  //                     <Card.Meta title={sale.thing.name} description={sale.thing.description}/>
  //                     <Card.Meta style={{marginTop: '5px'}} description={'起拍价：￥' + sale.startPrice}/>
  //                   </Card>
  //                 </Link>
  //               </Col>re
  //             ))
  //           }
  //         </Row>
  //       </div>
  //       <Pagination style={{textAlign: 'center', margin: '30px'}} showQuickJumper current={this.state.page}
  //                   total={this.state.total}
  //                   onChange={this.onChange}/>
  //     </PageHeaderWrapper>
  //   );
  // }
}

export default connect(
  ({loading, menuData, commodity}: ConnectState) =>
    ({
      loading: loading.effects["resource/searchScripts"],
      key: commodity.key
    }),
)
(Auction);

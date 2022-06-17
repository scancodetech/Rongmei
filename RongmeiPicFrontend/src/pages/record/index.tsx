import React, { Component } from 'react';

import { ConnectProps } from "@@/plugin-dva/connect";
import { connect, Link } from "umi";
import { List, Card } from 'antd';
import { ConnectState } from "@/models/connect";
import { getThingByTokenId, getAuctionTransactionHistories, getTopArtist, getTopTags } from "@/services/auction";
import recordStyles from "./record.less";
import Avatar from 'antd/lib/avatar/avatar';
import { getTCC } from '@/services/tcc';

interface RecordProps extends Partial<ConnectProps> {
  loading: boolean;
  location: any;
}

class Record extends Component<RecordProps> {

  state = {
    offset: 0,
    limit: 10,
    page: 0,
    recentTrans: [],
    topTags: [],
    topArtists: []
  }

  async componentDidMount(): void {
    const res = await getAuctionTransactionHistories({
      limit: this.state.limit,
      offset: this.state.offset
    });
    let transHistory = res.auctionTransactionHistories;
    transHistory.forEach(async (value, key) => {
      let thingRes = await getThingByTokenId(value.tokenId);
      transHistory[key].thing = thingRes;
    });
    this.setState({
      recentTrans: transHistory,
    })
    console.log(transHistory);
    
    this.getTopUsers();
  }

  async getTopUsers(): void {
    const endTime = new Date().getTime();
    const startTime = endTime - 7 * 24 * 60 * 60 * 1000;
    const nftTypeRes = await getTCC('dimension.nft.type');
    const typeTcc = eval(nftTypeRes.tccTuple.value);
    let tccMap = {};
    for (let i = 0; i < typeTcc.length; i++) {
      tccMap[typeTcc[i].tag] = typeTcc[i].name;
    }
    const topArtistRes = await getTopArtist({
      limit: 5,
      offset: 0
    })
    let topArtists: any[] = [];
    for (let i = 0; i < topArtistRes.auctionArtistItems.length; i++) {
      let artistItem = topArtistRes.auctionArtistItems[i];
      if (artistItem.id !== 0) {
        topArtists = topArtists.concat(artistItem);
      }
    }
    const topTagsRes = await getTopTags(startTime, endTime);
    let topTagNames: string[] = [];
    for (let i = 0; i < topTagsRes.tagHotItems.length; i++) {
      topTagNames = topTagNames.concat(tccMap[topTagsRes.tagHotItems[i].tag]);
    }
    this.setState({
      topTags: topTagNames,
      topArtists: topArtists
    })
  }

  render() {
    return (
      <div className={recordStyles.recordContainer}>
        <div className={recordStyles.headTitle}>交易动态</div>
        <div className={recordStyles.mainContent}>
          <div className={recordStyles.recordList}>
            <List
              itemLayout="vertical"
              dataSource={this.state.recentTrans}
              renderItem={(item) => (
                <div className={recordStyles.recordItem}>
                  <div className={recordStyles.recordItemHead}>
                    {/* <Avatar src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png" /> */}
                    <div className={recordStyles.recordItemInfo}>
                      <Link to="">
                        @{item.username}
                      </Link>
                      从
                      <Link to="">
                        @{item.thing?item.thing.owner:''}
                      </Link>
                      那里
                      {
                        item.nft < 0 ? "卖出" : "买入"
                      }
                      了
                      <Link to="">
                        {item.name}
                      </Link>
                    </div>
                  </div>
                  <Link to="">
                    <div>
                      <img className={recordStyles.listImg} alt="" src={item.thing?item.thing.url:''} />
                    </div>
                  </Link>
                </div>
              )}
            />
          </div>
          <div className={recordStyles.siderBar}>
            <div className={recordStyles.topcollector}>
              <List
                itemLayout="vertical"
                dataSource={this.state.topTags}
                header={<h2>最近热门</h2>}
                renderItem={item => (
                  <div className={recordStyles.topUserItem}>
                    <div>{item}</div>
                  </div>
                )}
              />
            </div>
            <div className={recordStyles.topArtist}>
              <List
                itemLayout="vertical"
                dataSource={this.state.topArtists}
                header={<h2>顶级创作者</h2>}
                renderItem={item => (
                  <div className={recordStyles.topUserItem}>
                    <img className={recordStyles.topUserAvatar} alt="" src={item.avatarUrl} />
                    <div>{item.nickname}</div>
                  </div>
                )}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default connect(
  ({ loading }: ConnectState) =>
  ({
    loading: loading.effects["resource/searchScripts"],
  }),
)

  (Record);

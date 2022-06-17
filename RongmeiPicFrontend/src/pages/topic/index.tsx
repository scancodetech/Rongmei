import React, { Component } from 'react';

import { List } from 'antd';
import { ConnectProps } from "@@/plugin-dva/connect";
import { connect, Link } from "umi";
import { ConnectState } from "@/models/connect";
import topicStyle from "@/pages/topic/topic.less";
import { getCommodityTopic } from '@/services/commodity';
import { getAuctionTopic } from '@/services/auction';

interface SaleProps extends Partial<ConnectProps> {
  loading: boolean;
  location: any;
}

class Sale extends Component<SaleProps> {

  state = {
    limit: 10,
    offset: 0,
    total: 10,
    page: 1,
    topicName: '',
    topicType: '',
    saleItems: [{ "id": 207, "startPrice": 0.0, "status": "拍卖中", "intervalPrice": 1.0, "currentPrice": 0.0, "thing": { "id": 8, "name": "NFT测试", "url": "https://image.rongmeitech.com/static/2020_09_05_14_15_42_0707_4.jpg", "price": 100.0, "description": "NFT简介", "createTime": 1599286553430, "author": "13700000002", "owner": "13700000002", "tokenId": "", "tags": [] }, "createTime": 1609232627625, "startTime": 1609232291606, "endTime": 1609232291606, "tags": [], "needEarnestMoney": false }, { "id": 191, "startPrice": 10.0, "status": "拍卖中", "intervalPrice": 100.0, "currentPrice": 710.0, "thing": { "id": 12, "name": "biovisual", "url": "https://image.rongmeitech.com/static/2020_09_06_16_55_16_0309_biovisual-logo.jpg", "price": 100.0, "description": "113123", "createTime": 1599382551817, "author": "13700000002", "owner": "13700000002", "tokenId": "1599382545847868", "tags": [] }, "createTime": 1607324208076, "startTime": 1607324185254, "endTime": 1609052365254, "tags": [], "needEarnestMoney": false }, { "id": 61, "startPrice": 1.0, "status": "拍卖中", "intervalPrice": 0.1, "currentPrice": 6.8, "thing": { "id": 60, "name": "测试78", "url": "https://image.rongmeitech.com/static/2020_11_07_13_08_14_0044_大礼堂.png", "price": 1.0, "description": "测试78", "createTime": 1604725709276, "author": "13700000002", "owner": "13700000002", "tokenId": "1604725703168989", "tags": [] }, "createTime": 1604725810584, "startTime": 1604552989667, "endTime": 1604639389667, "tags": [], "needEarnestMoney": false }, { "id": 42, "startPrice": 100.0, "status": "拍卖中", "intervalPrice": 10.0, "currentPrice": 170.0, "thing": { "id": 18, "name": "test", "url": "https://image.rongmeitech.com/static/2020_09_06_17_41_49_0917_大礼堂3.png", "price": 100.0, "description": "12312", "createTime": 1599385324921, "author": "13700000002", "owner": "13700000002", "tokenId": "1599385323630364", "tags": [] }, "createTime": 1603527786946, "startTime": 1603527710662, "endTime": 1603528250662, "tags": [], "needEarnestMoney": false }, { "id": 28, "startPrice": 100.0, "status": "拍卖中", "intervalPrice": 10.0, "currentPrice": 230.0, "thing": { "id": 8, "name": "NFT测试", "url": "https://image.rongmeitech.com/static/2020_09_05_14_15_42_0707_4.jpg", "price": 100.0, "description": "NFT简介", "createTime": 1599286553430, "author": "13700000002", "owner": "13700000002", "tokenId": "", "tags": [] }, "createTime": 1603174177066, "startTime": 1603087761909, "endTime": 1603174161909, "tags": [], "needEarnestMoney": false }, { "id": 27, "startPrice": 100.0, "status": "拍卖中", "intervalPrice": 10.0, "currentPrice": 110.0, "thing": { "id": 16, "name": "测试", "url": "https://image.rongmeitech.com/static/2020_10_19_19_12_08_0756_product_1.jpg", "price": 100.0, "description": "123123", "createTime": 1599384408766, "author": "13700000002", "owner": "13700000002", "tokenId": "1599384407387495", "tags": [] }, "createTime": 1603105882377, "startTime": 1603105850018, "endTime": 1606734650018, "tags": [], "needEarnestMoney": false }],
    commodities: [],
  }

  async componentDidMount(): void {
    let paramArray = this.props.location.pathname.split('/')
    const topicName = paramArray.pop();
    const topicType = paramArray.pop();
    this.setState({
      topicName: topicName,
      topicType: topicType
    })
    console.log(this.props);

  }

  async getCommodities() {
    let res = getCommodityTopic(
      '',
      this.state.offset,
      this.state.limit
    )
    this.setState({
      commodities: res.commodities
    })
  }

  async getSales() {
    let res = getAuctionTopic(
      '',
      this.state.offset,
      this.state.limit
    )
    this.setState({
      saleItems: res.saleItems
    })
  }

  renderList(){
    if(this.state.topicType=="commodity"){
      this.renderCommodityList();
    } else if(this.state.topicType=="auction"){
      this.renderSaleList();
    }
  }

  renderSaleList() {
    return (
      <List
        itemLayout="vertical"
        dataSource={this.state.saleItems}
        pagination={{
          onChange: page => {
            this.setState({
              offset: page * this.state.limit
            })
          },
          pageSize: this.state.limit,
        }}
        renderItem={item => (
          <List.Item style={{ display: 'flex', }}>
            <Link to={`/auction/${item.id}`}><img src={item.thing.url} style={{ width: 400, height: 300, objectFit: 'cover', marginRight: 30 }} /></Link>
            <div style={{ flexGrow: 1, display: 'flex', flexDirection: 'column', }}>
              <div style={{ fontSize: 28, lineHeight: '60px' }}>{item.thing.name}</div>
              <div style={{ fontSize: 24, color: '#666666', flexGrow: 2 }}>{item.thing.description}</div>
              <a style={{ color: '#000', lineHeight: '48px' }}>{item.thing.author}</a>
            </div>
          </List.Item>
        )}
      />);
  }

  renderCommodityList() {
    return (
      <List
        itemLayout="vertical"
        dataSource={this.state.commodities}
        pagination={{
          onChange: page => {
            this.setState({
              offset: page * this.state.limit
            })
          },
          pageSize: this.state.limit,
        }}
        renderItem={item => (
          <List.Item style={{ display: 'flex', }}>
            <Link to={`/auction/${item.id}`}><img src={item.thing.url} style={{ width: 400, height: 300, objectFit: 'cover', marginRight: 30 }} /></Link>
            <div style={{ flexGrow: 1, display: 'flex', flexDirection: 'column', }}>
              <div style={{ fontSize: 28, lineHeight: '60px' }}>{item.thing.name}</div>
              <div style={{ fontSize: 24, color: '#666666', flexGrow: 2 }}>{item.thing.description}</div>
              <a style={{ color: '#000', lineHeight: '48px' }}>{item.thing.author}</a>
            </div>
          </List.Item>
        )}
      />);
  }

  render() {
    return (
      <div className={topicStyle.topicContainer}>
        <div className={topicStyle.headTitle}>#{this.state.topicName}</div>
        <div style={{ fontSize: 18, lineHeight: '48px' }}>简介：这里是标签简介</div>
        <div>
          <div>
            {}
          </div>
        </div>
      </div>
    );
  }
}

export default connect(
  ({ loading }: ConnectState) =>
  ({
    loading: loading.effects["resource/searchScripts"]
  }),
)
  (Sale);

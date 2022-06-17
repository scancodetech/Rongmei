import React from 'react';
import { List, TabBar } from "antd-mobile";
import { withRouter } from 'react-router-dom';
import { api } from "../../services/api/ApiProvider";
import fav from "../../assets/like.png";
import share from "../../assets/share.png";

const Item = List.Item;

@withRouter
class Auction extends React.Component {

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

  async componentDidMount() {
    this.getSales()
  }

  async getSales() {
    let res = await api.auctionService.getSales({
      key: this.state.key,
      limit: this.state.limit,
      offset: (this.state.page - 1) * this.state.limit,
      outdated: !this.state.outdated,
      ownedByAuthor: !this.state.ownedByAuthor,
      rankType: this.state.rankType,
      tags: this.state.selectedTags
    })
    let saleItems = res.saleItems;
    let userNicknameMap = new Map();
    for (let i = 0; i < saleItems.length; i++) {
      let item = saleItems[i];
      let author = item.thing.author;
      if (userNicknameMap.has(author)) {
        saleItems[i].authorNickname = userNicknameMap.get(author);
      } else {
        const res = await api.userService.getUserInfoEntity(author);
        if (res.nickname) {
          saleItems[i].authorNickname = res.nickname;
          userNicknameMap.set(author, res.nickname);
        }
      }
      let owner = item.thing.owner;
      if (userNicknameMap.has(owner)) {
        saleItems[i].ownerNickname = userNicknameMap.get(owner);
      } else {
        const res = await api.userService.getUserInfoEntity(owner);
        if (res.nickname) {
          saleItems[i].ownerNickname = res.nickname;
          userNicknameMap.set(owner, res.nickname);
        }
      }
    }
    this.setState({
      sales: res.saleItems
    }, () => { console.log(this.state.sales); })
  }

  toSale() {

  }

  render() {
    return (
      <div>
        <div style={{ display: 'flex' }}>
          <div style={{ backgroundColor: '#fff', lineHeight: '36px', width:'100%', color: 'red' }}>在拍</div>
          <div style={{ backgroundColor: '#fff', lineHeight: '36px', width:'100%' }}>竞品</div>
        </div>
        <List>
          {
            this.state.sales.map((item) => (
              <Item style={{ padding: '5 0' }}>
                <div style={{ display: 'flex' }}>
                  <div style={{ flexGrow: 1, fontSize: 32 }}>
                    {item.thing.name}
                  </div>
                  <img src={fav} />
                  <img src={share} />
                </div>
                <div onClick={() => { this.toSale(item.id) }}>
                  <div style={{ color: "#9e9e9e", padding: '4 0' }}>
                    {(item.thing.description == '' || item.thing.description.length == 0) ? '暂无标题' : item.thing.description}
                  </div>
                  <div>
                    <img style={{ borderRadius: 10, height: 'auto', width: '100%' }} src={item.thing.url} />
                  </div>
                </div>
              </Item>
            ))
          }
        </List>
        {/* <ListView
              dataSource={}
            /> */}
      </div>
    )
  }
}

export default Auction

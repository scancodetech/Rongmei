import {Avatar, Button, Card, Form, Input, List, Menu, message, Modal, Popover, Radio, Select, Tabs} from 'antd';
import React, {Component} from 'react';
import {Dispatch, Link} from 'umi';
import styles from './style.less';
import {RouteChildrenProps} from "react-router";
import {getFavoritesThings, getTokenValue, moveFavoritesThing, Thing} from "@/services/auction";
import {CommodityItem, getCommodity, getFavoritesCommodities, moveFavoritesCommodity} from "@/services/commodity";
import Loading from "@/assets/loading.gif";
import {deleteFavorites, getMineFavorites, updateFavorites} from "@/services/relation";
import {MoreOutlined} from "@ant-design/icons";

const {TabPane} = Tabs;

interface ArtworkListProps extends RouteChildrenProps {
  dispatch: Dispatch;
}

const initialPanes = [
  {title: '全部藏品', key: '0'},
];

class ArtworkList extends Component<ArtworkListProps, any> {
  state = {
    activeTab: '0',
    activeType: '拍品',
    activeState: 0,
    panes: initialPanes,

    things: [],
    commodities: [],
    isFavoritesModalVisible: false,
    favoritesId: 0,
    favoritesName: "",
  }

  componentDidMount() {
    this.getFavoritesTab();
    this.getData(0, 0, '拍品');
  }

  getFavoritesTab = async () => {
    const res = await getMineFavorites();
    if (res.favoritesItems) {
      let panes = initialPanes;
      res.favoritesItems.forEach((favoritesItem) => {
        panes = panes.concat({
          title: favoritesItem.name,
          key: favoritesItem.id + ""
        })
      })
      this.setState({
        panes
      })
    }
  }

  getData = async (favoritesId: number, status: number, activeType: string) => {
    if (activeType === "拍品") {
      const thingRes = await getFavoritesThings(favoritesId, status);
      if (thingRes.thingItems) {
        this.setState({
          things: thingRes.thingItems
        })
      }
    } else if (activeType === "素材") {
      const commodityRes = await getFavoritesCommodities(favoritesId, status);
      if (commodityRes.commodities) {
        this.setState({
          commodities: commodityRes.commodities
        })
      }
    }
  }

  onTabChange = activeTab => {
    this.setState({activeTab});
    this.getData(parseInt(activeTab), this.state.activeState, this.state.activeType);
  };

  onTabEdit = (targetKey, action) => {
    this[action](targetKey);
  };

  add = () => {
    this.openFavoritesModal();
  };

  remove = async (targetKey: string) => {
    const {panes, activeTab} = this.state;
    let newActiveKey = activeTab;
    let lastIndex = 0;
    panes.forEach((pane, i) => {
      if (pane.key === targetKey) {
        lastIndex = i - 1;
      }
    });
    const newPanes = panes.filter(pane => pane.key !== targetKey);
    if (newPanes.length && newActiveKey === targetKey) {
      if (lastIndex >= 0) {
        newActiveKey = newPanes[lastIndex].key;
      } else {
        newActiveKey = newPanes[0].key;
      }
    }
    this.setState({
      activeTab: newActiveKey,
    });
    const res = await deleteFavorites(parseInt(targetKey));
    if (res.infoCode === 10000) {
      await this.getFavoritesTab();
      message.success("删除成功");
    }
  };

  onTypeChange = (e) => {
    this.setState({
      activeType: e.target.value
    })
    this.getData(parseInt(this.state.activeTab), this.state.activeState, e.target.value);
  }

  onStateClick = (e) => {
    this.setState({activeState: e.key});
    this.getData(parseInt(this.state.activeTab), e.key, this.state.activeType);
  };

  closeFavoritesModal = () => {
    this.setState({
      isFavoritesModalVisible: false
    })
  }

  openFavoritesModal = () => {
    this.setState({
      isFavoritesModalVisible: true
    })
  }

  submitFavoritesModal = async () => {
    const res = await updateFavorites({
      id: this.state.favoritesId,
      name: this.state.favoritesName,
      orderId: 0
    })
    if (res.infoCode === 10000) {
      message.success("编辑成功");
      this.getFavoritesTab();
      this.closeFavoritesModal();
    } else {
      message.error("编辑失败");
    }
  }

  moveToCommodityFavorites = async (favoriteId: number, id: number, status: number) => {
    const res = await moveFavoritesCommodity(favoriteId, id, status);
    if (res.infoCode === 10000) {
      message.success("移动成功");
    }
  }

  moveToThingFavorites = async (favoriteId: number, id: number, status: number) => {
    const res = await moveFavoritesThing(favoriteId, id, status);
    if (res.infoCode === 10000) {
      message.success("移动成功");
    }
  }

  downloadThing = async (tokenId: string) => {
    const res = getTokenValue(tokenId);
    if (res.value) {
      window.location.href = res.value;
    } else {
      message.error("无效的藏品，请联系管理员找回或删除")
    }
  }

  downloadCommodity = async (id: number) => {
    const res = getCommodity(id);
    if (res.contentUrl) {
      window.location.href = res.contentUrl;
    } else {
      message.error("无效的素材，请联系管理员找回或删除")
    }
  }

  render() {
    return (
      <div className={styles.coverCardList}>
        <Modal title="编辑藏品夹" visible={this.state.isFavoritesModalVisible} onOk={this.submitFavoritesModal}
               onCancel={this.closeFavoritesModal}>
          <Form.Item label="藏品夹名称" rules={[{required: true}]}>
            <Input value={this.state.favoritesName} onChange={(e) => {
              this.setState({
                favoritesName: e.target.value
              })
            }}/>
          </Form.Item>
        </Modal>
        <Tabs
          type="editable-card"
          onChange={this.onTabChange}
          activeKey={this.state.activeTab}
          onEdit={this.onTabEdit}
        >
          {this.state.panes.map(pane => (
            <TabPane tab={pane.title} key={pane.key} closable={pane.key !== '0'}>
              <div style={{textAlign: 'center', background: 'white', padding: '20px', marginTop: '-15px'}}>
                <Radio.Group size="large" value={this.state.activeType} onChange={this.onTypeChange}>
                  <Radio.Button value="拍品">拍品</Radio.Button>
                  <Radio.Button value="素材">素材</Radio.Button>
                </Radio.Group>
              </div>
              {this.state.activeType === '拍品' ?
                (
                  <div style={{background: 'white'}}>
                    <Menu
                      onClick={this.onStateClick}
                      selectedKeys={[this.state.activeState + ""]}
                      mode="horizontal"
                      style={{textAlign: 'center'}}
                    >
                      <Menu.Item key="0">
                        竞品
                      </Menu.Item>
                      <Menu.Item key="1">
                        喜欢
                      </Menu.Item>
                      <Menu.Item key="2">
                        竞价中
                      </Menu.Item>
                    </Menu>
                    <div style={{background: 'white', marginTop: '20px'}}>
                      <List<Thing>
                        rowKey="id"
                        style={{margin: "0 24px"}}
                        grid={{gutter: 16, column: 2}}
                        dataSource={this.state.things}
                        renderItem={(item) => (
                          <List.Item key={item.id} style={{textAlign: 'left'}}>
                            <Card
                              className={styles.card}
                              onClick={() => this.downloadThing(item.tokenId)}
                              style={{borderRadius: "10px",}}
                              hoverable
                              cover={
                                <div>
                                  <img
                                    style={{
                                      borderRadius: "10px 10px 0 0",
                                      width: "100%",
                                    }}
                                    alt={item.name}
                                    src={item.url}/>
                                </div>
                              }>
                              <Card.Meta
                                title={<div className={styles.info}>
                                  <span>{item.name}</span>
                                </div>
                                }/>
                              <div className={styles.cardRightTop}>
                                <Popover content={<div>
                                  <p>移动到</p>
                                  <Select defaultValue="0" style={{width: 120}}
                                          onChange={(value: string) => this.moveToThingFavorites(parseInt(value), item.id, this.state.activeState)}>
                                    {this.state.panes.map(pane => pane.key !== '0' ? <Select.Option
                                      value={pane.key}>{pane.title}</Select.Option> : null)}
                                  </Select>
                                </div>} title={null}>
                                  <MoreOutlined style={{fontSize: '20px', fontWeight: 'bolder'}}/>
                                </Popover>
                              </div>
                            </Card>
                          </List.Item>
                        )}
                      />
                    </div>
                  </div>
                ) : (
                  <div style={{background: 'white'}}>
                    <Menu
                      onClick={this.onStateClick}
                      selectedKeys={[this.state.activeState + ""]}
                      mode="horizontal"
                      style={{textAlign: 'center'}}
                    >
                      <Menu.Item key="0">
                        已购
                      </Menu.Item>
                      <Menu.Item key="1">
                        喜欢
                      </Menu.Item>
                    </Menu>
                    <div style={{background: 'white', marginTop: '20px'}}>
                      <List<CommodityItem>
                        rowKey="id"
                        style={{margin: "0 24px"}}
                        grid={{gutter: 16, column: 2}}
                        dataSource={this.state.commodities}
                        renderItem={(item) => (
                          <List.Item key={item.id} style={{textAlign: 'left'}}>
                            {/* <Link
                              to={`/commodity/${item.id}`}> */}
                            <a href={item.coverUrl} download>
                              <Card
                                className={styles.card}
                                style={{borderRadius: "10px",}}
                                hoverable
                                onClick={() => this.downloadCommodity(item.id)}
                                cover={
                                  <div>
                                    <img
                                      style={{
                                        borderRadius: "10px 10px 0 0",
                                        width: "100%",
                                        maxHeight: '320px',
                                        objectFit: 'cover'
                                      }}
                                      alt={item.title}
                                      src={item.coverUrl}/>
                                  </div>
                                }>
                                <Card.Meta
                                  title={<div className={styles.info}>
                                    <span>{item.title}</span>
                                  </div>
                                  }/>
                              </Card>
                              <div className={styles.cardRightTop}>
                                <Popover content={<div>
                                  <p>移动到</p>
                                  <Select defaultValue="0" style={{width: 120}}
                                          onChange={(value: string) => this.moveToCommodityFavorites(parseInt(value), item.id, this.state.activeState)}>
                                    {this.state.panes.map(pane => pane.key !== '0' ? <Select.Option
                                      value={pane.key}>{pane.title}</Select.Option> : null)}
                                  </Select>
                                </div>} title={null}>
                                  <MoreOutlined style={{fontSize: '20px', fontWeight: 'bolder'}}/>
                                </Popover>
                              </div>
                            </a>
                            {/* </Link> */}
                          </List.Item>
                        )}
                      />
                    </div>
                  </div>
                )}
            </TabPane>
          ))}
        </Tabs>
      </div>
    );
  }
}

export default ArtworkList;

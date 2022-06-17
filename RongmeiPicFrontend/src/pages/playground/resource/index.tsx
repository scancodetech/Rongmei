import {Card, Input, List, Form, Menu, Layout, Avatar} from 'antd';
import React, {Component} from 'react';
import {connect, Link} from 'umi';
import {ConnectProps, SubMenuDataItem} from "@@/plugin-dva/connect";
import {ConnectState} from "@/models/connect";
import TagSelect from "@/pages/playground/resource/components/TagSelect";
import {CommodityItem} from "@/services/commodity";
import StandardFormRow from "@/pages/playground/resource/components/StandardFormRow";
import {MenuDataItem} from "@ant-design/pro-layout";
import styles from './style.less';
import Loading from '../../../assets/loading.gif'

const {Content, Sider, Header} = Layout;

interface ResourceProps extends Partial<ConnectProps> {
  menuData: MenuDataItem[];
  subMenuData: SubMenuDataItem[];
  commodities: CommodityItem[];
  total: number;
  loading: boolean;
  location: any;
}

class Resource extends Component<ResourceProps> {
  state = {
    offset: 0,
    limit: 30,
    tags: {},
    orderKey: ['all'],
    firstType: ''
  }

  searchState = {
    keywords: [],
    events: [],
    commodities: [],
    key: ''
  }

  async componentDidMount(): void {
    let firstType = this.props.location.pathname.split('/').pop();
    const {dispatch} = this.props;
    if (dispatch) {
      await dispatch({
        type: 'menuData/getMenuData'
      })
    }
    const {menuData} = this.props;
    if (firstType === 'playground') {
      firstType = menuData[0] ? menuData[0].name : 'playground';
    }
    if (firstType !== 'playground') {
      this.setState({
        firstType
      })
      if (dispatch) {
        dispatch({
          type: 'menuData/getSubMenuData',
          payload: {firstType}
        })
        dispatch({
          type: 'commodity/searchCommodities',
          payload: {
            tags: [],
            key: '',
            orderKey: 'all',
            offset: 0,
            limit: 30
          }
        })
      }
    } else {
      this.setState({
        firstType: ''
      })
    }
  }

  search() {
    const {dispatch} = this.props;
    if (dispatch) {
      const {tags} = this.state;
      const allTags: string[] = [];
      Object.keys(this.state.tags).forEach(key => {
        for (let i = 0; i < tags[key].length; i++) {
          allTags.push(tags[key][i])
        }
      });
      dispatch({
        type: 'commodity/searchCommodities',
        payload: {
          tags: allTags,
          key: '',
          orderKey: this.state.orderKey[0],
          offset: this.state.offset,
          limit: this.state.limit
        }
      })
    }
  }

  handleClick = (e) => {
    this.setState({
      firstType: e.key
    })
    const {dispatch} = this.props;
    if (dispatch) {
      dispatch({
        type: 'menuData/getSubMenuData',
        payload: {firstType: e.key}
      })
      dispatch({
        type: 'commodity/searchCommodities',
        payload: {
          tags: [],
          key: '',
          orderKey: 'all',
          offset: 0,
          limit: 30
        }
      })
    }
  };

  render() {
    const {menuData, subMenuData, commodities, total} = this.props;
    const menuStyle = {
      display: 'flex',
      justifyContent: 'center',
      flexWrap: 'nowrap',
    };
    return (
      <div className={styles.filterCardList}>
        <Content>
          <Layout>
            <Header className="site-layout-background" width={200}>
              <Menu
                onClick={this.handleClick}
                style={menuStyle}
                selectedKeys={[this.state.firstType]}
                mode="horizontal"
              >
                {
                  menuData.map((item) => (
                    <Menu.Item key={item.name}>{item.name}</Menu.Item>
                  ))
                }
              </Menu>
            </Header>
            <Content style={{minHeight: 280}}>
              {
                this.state.firstType !== '' ? (
                  <div>
                    {/* <div style={{textAlign: 'center', marginBottom: '20px'}}>
        <h1 style={{fontSize: '24px'}}>跨次元数字艺术平台，带您领略数字艺术</h1>
        <Input.Search
          placeholder="请输入关键词搜索"
          size="large"
          loading={loading}
          value={this.searchState.key}
          onSearch={this.handleFormSubmit}
          style={{maxWidth: 800, width: '100%'}}
        />
        <div style={{marginTop: '10px', color: '#999'}}>
          推荐关键字：&nbsp;&nbsp;
          {
            this.state.keywords.map((keyword) => (
              <span style={{marginRight: '15px', cursor: 'pointer'}} onClick={() => {
                this.setState({
                  key: keyword
                })
              }}>{keyword}</span>))
          }
        </div>
      </div> */}
                    <Card bordered={false} style={{padding: '0 24px'}}>
                      <Form>
                        <StandardFormRow block>
                          {subMenuData.map((item) => (
                            <div>
                              <span style={{marginRight: '20px', color: 'rgba(0, 0, 0, 0.85)'}}>{item.name}：</span>
                              <TagSelect style={{display: 'inline'}} onChange={(value => {
                                const newTags = this.state.tags;
                                newTags[item.name] = value;
                                this.setState({
                                  tags: newTags
                                });
                                this.search();
                              })}>
                                {
                                  item.typeList.map((typeItem) => (
                                    <TagSelect.Option value={typeItem}>{typeItem}</TagSelect.Option>
                                  ))
                                }
                              </TagSelect>
                            </div>
                          ))}
                        </StandardFormRow>
                        <StandardFormRow title="排序方式" grid last>
                          <TagSelect hideCheckAll value={this.state.orderKey} onChange={(value) => {
                            for (let i = 0; i < value.length; i++) {
                              if (this.state.orderKey.indexOf(value[i]) < 0) {
                                this.setState({
                                  orderKey: [value[i]]
                                })
                              }
                            }
                            this.search();
                          }}>
                            <TagSelect.Option value="all">综合排序</TagSelect.Option>
                            <TagSelect.Option value="hot">热门下载</TagSelect.Option>
                            <TagSelect.Option value="new">最新上传</TagSelect.Option>
                          </TagSelect>
                        </StandardFormRow>
                      </Form>
                    </Card>
                    <br/>
                    <div className={styles.commodityMainGrid}>
                      <List<CommodityItem>
                        rowKey="id"
                        style={{margin: "0 24px"}}
                        grid={{gutter: 24, xxl: 4, xl: 4, lg: 3, md: 3, sm: 2, xs: 1}}
                        pagination={{
                          onChange: page => {
                            this.setState({
                              offset: page * this.state.limit
                            })
                          },
                          pageSize: this.state.limit,
                          total
                        }}
                        //隐藏-todo
                        dataSource={commodities}
                        // dataSource={[]}
                        renderItem={(item) => (
                          <List.Item key={item.id} style={{textAlign: 'left'}}>
                            <Link
                              to={`/commodity/${item.id}`}>
                              <Card
                                className={styles.card}
                                style={{borderRadius: "10px",}}
                                hoverable
                                cover={
                                  <div className={styles.cardCover}>
                                    <img
                                      style={{
                                        borderRadius: "10px 10px 0 0",
                                        width: "100%",
                                        height: '360px',
                                        objectFit: 'cover',
                                      }}
                                      alt={item.title}
                                      src={item.coverUrl === "http://39.102.36.169:6789/static/2020_09_06_13_11_37_0880_CG艺术家_20200906093556_20200906093632289.xlsx" ? Loading : item.coverUrl}/>
                                  </div>
                                }>
                                <Card.Meta
                                  title={<div className={styles.info}>
                                    <span>{item.title}</span>
                                    <span style={{
                                      float: "right",
                                      color: "#fe2431"
                                    }}>{(item.largePrice / 100).toLocaleString()}电子</span>
                                    <div style={{marginTop: "10px", alignItems: "center"}}>
                                      <Avatar
                                        src="https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png"/>
                                      <Link
                                        style={{marginLeft: "10px", color: "#000"}}
                                        to="">
                                        <span style={{
                                          float: "right",
                                          color: "#fe2431",
                                          fontSize: "10px",
                                          lineHeight: "32px"
                                        }}>{"1小时前"}</span>
                                      </Link>
                                    </div>
                                  </div>
                                  }/>
                                <div className={styles.price}>
                                  <div className={styles.vipTip}>
                                    <span>会员免费</span>
                                  </div>
                                </div>
                              </Card>
                            </Link>
                          </List.Item>
                        )}
                      />
                    </div>
                  </div>
                ) : null
              }
            </Content>
          </Layout>
        </Content>
      </div>
    );
  }
}

export default connect(
  ({menuData, commodity, loading}: ConnectState) =>
    ({
      menuData: menuData.menuData,
      subMenuData: menuData.subMenuData,
      commodities: commodity.commodities,
      total: commodity.total,
      loading: loading.effects["commodity/searchCommodities"],
    }),
)
(Resource);

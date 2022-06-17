import {Card, List, Input} from 'antd';
import React, {Component} from 'react';
import {connect, Link} from 'umi';
import {ConnectProps} from "@@/plugin-dva/connect";
import {ConnectState} from "@/models/connect";
import {CommodityItem} from "@/services/commodity";
import styles from './style.less';

interface SearchProps extends Partial<ConnectProps> {
  commodities: CommodityItem[];
  total: number;
  loading: boolean;
  location: any;
}

class Search extends Component<SearchProps> {
  state = {
    offset: 0,
    limit: 20,
    tags: {},
    orderKey: ['all'],
    key: ''
  }

  componentDidMount(): void {
    const {dispatch} = this.props;
    const newKey = this.props.location.pathname.split('/').pop();
    if (newKey && newKey.length > 0) {
      this.setState({
        key: newKey
      })
    }
    if (dispatch) {
      dispatch({
        type: 'commodity/searchCommodities',
        payload: {
          tags: [],
          key: newKey,
          orderKey: 'all',
          offset: 0,
          limit: 20
        }
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

  handleFormSubmit = (value) => {
    const {dispatch} = this.props;
    if (dispatch) {
      dispatch({
        type: 'commodity/searchCommodities',
        payload: {
          tags: [],
          key: value,
          orderKey: 'all',
          offset: this.state.offset,
          limit: this.state.limit
        }
      });
      this.props.history.push(`/search/${value}`)
    }
  };

  render() {
    const {commodities, total, loading} = this.props;
    return (
      <div className={styles.filterCardList}>
        <div style={{textAlign: 'center', marginBottom: '50px'}}>
          <Input.Search
            placeholder="请输入关键词搜索"
            size="large"
            loading={loading}
            value={this.state.key}
            onSearch={this.handleFormSubmit}
            style={{width: '100%'}}
            onChange={(event) => {
              this.setState({
                key: event.target.value
              })
            }}
          />
          <div style={{color: '#999', fontSize: '12px', marginTop: '5px', float: 'left'}}>约{total}条结果</div>
        </div>
        <List<CommodityItem>
          rowKey="id"
          grid={{gutter: 24, xl: 4, lg: 3, md: 3, sm: 2, xs: 1}}
          pagination={{
            onChange: page => {
              this.setState({
                offset: page * this.state.limit
              })
            },
            pageSize: this.state.limit,
            total
          }}
          dataSource={commodities}
          renderItem={(item) => (
            <List.Item key={item.id}>
              <Link
                to={`/commodity/${item.id}`}>
                <Card className={styles.card} hoverable
                      cover={<img alt={item.title} src={item.coverUrl}/>}>
                  <Card.Meta title={<a>{item.title}</a>}/>
                </Card>
              </Link>
            </List.Item>
          )}
        />
      </div>
    );
  }
}

export default connect(
  ({commodity, loading}: ConnectState) =>
    ({
      commodities: commodity.commodities,
      total: commodity.total,
      loading: loading.effects["commodity/searchCommodities"],
    }),
)
(Search);

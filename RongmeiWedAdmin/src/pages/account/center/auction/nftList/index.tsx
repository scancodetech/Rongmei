import React, { Component } from 'react';

import auctionStyles from "@/pages/account/center/auction/auction.less";
import { deleteThing, getThings } from '@/services/auction';
import { ArrowDownOutlined, DeleteOutlined, EditOutlined, EyeOutlined, HeartOutlined } from '@ant-design/icons';
import { List, message, Popconfirm } from 'antd';
import {getMineCertification} from "@/services/apply";

function formatDate(time: any) {
  let date = new Date(time);
  let Y = date.getFullYear() + '-';
  let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  let D = date.getDate() + ' ';
  let h = date.getHours() + ':';
  let m = date.getMinutes() + ':';
  let s = date.getSeconds();
  return Y + M + D + h + m + s;
}

class AccountCenterNftListContact extends Component<any> {

  state = {
    things: [],
    visible: false,
    id: 0
  }

  async checkCertificationType(type: string) {
    const certificationRes = await getMineCertification(type);
    if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
      message.error("暂无此区域权限，正在前往验证")
      this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
    }
  }

  async componentDidMount() {
    await this.checkCertificationType("竞品类");
    this.getThings();
  }

  async getThings() {
    let res = await getThings();
    this.setState({
      things: res.thingItems
    })
  }

  async deleteNft(id) {
    let res = await deleteThing(id);
    if (res.infoCode == 10000) {
      message.info('删除成功');
      this.getThings();
    } else {
      message.info('删除失败');
    }
  }

  render() {
    return (
      <div className={auctionStyles.listContainer}>
        <div className={auctionStyles.headTitle}>
          铸造列表
        </div>
        <div>
          <List
            itemLayout="vertical"
            size="large"
            // pagination={{
            //     onChange: page => {
            //         this.setState({
            //             offset: page * this.state.limit
            //         })
            //     },
            //     pageSize: this.state.limit
            // }}
            dataSource={this.state.things}
            renderItem={item => (
              <List.Item>
                <div className={auctionStyles.auctionItem}>
                  <div className={auctionStyles.listImg}>
                    <img className={auctionStyles.itemImg} onClick={() => {
                      this.props.history.push(`/account/center/auction/nft/${item.id}`)
                    }} alt='竞价图片' src={item.url} />
                  </div>
                  <div className={auctionStyles.itemInfo}>
                    <div className={auctionStyles.itemTitle}>{item.name == '' ? '(null)' : item.name}</div>
                    <div className={auctionStyles.itemDescription}>{item.description}</div>
                    <div className={auctionStyles.itemFigure}>
                      <div className={auctionStyles.view}>
                        <EyeOutlined />
                        {item.view}
                      </div>
                      <div className={auctionStyles.download}>
                        <ArrowDownOutlined />
                        {item.download}
                      </div>
                      <div className={auctionStyles.like}>
                        <HeartOutlined />
                        {item.like}
                      </div>
                    </div>
                    <div className={auctionStyles.itemPrice}>
                      <div className={auctionStyles.priceText}>价格：</div>
                      <div className={auctionStyles.price}>{item.price} 电子</div>
                      {/* <div className={auctionStyles.priceText}>起拍价：</div>
                                            <div className={auctionStyles.price}>{item.startPrice} 电子</div>
                                            <div className={auctionStyles.priceText}>最低加价：</div>
                                            <div className={auctionStyles.price}>{item.minBid} 电子</div> */}
                    </div>
                    <div className={auctionStyles.itemDate}>
                      <div className={auctionStyles.dateText}>{item.date}</div>
                    </div>
                    <div className={auctionStyles.bottomOpts}>
                      <div className={auctionStyles.itemResale}>
                        <EditOutlined />
                        <a className={auctionStyles.resaleText}
                          onClick={() => {
                            this.props.history.push(`/account/center/auction/nft/${item.id}`)
                          }}>编辑信息</a>
                      </div>
                      <div className={auctionStyles.itemDelete}>
                        <DeleteOutlined />
                        <Popconfirm
                          title="确认删除该铸造？"
                          onConfirm={() => { this.deleteNft(item.id) }}
                          // onCancel={cancel}
                          okText="确认"
                          cancelText="取消"
                        >
                          <a className={auctionStyles.deleteText} >删除</a>
                        </Popconfirm>
                      </div>
                    </div>
                  </div>
                </div>
              </List.Item>
            )}
          />
        </div>
      </div>
    );
  }
}

export default AccountCenterNftListContact

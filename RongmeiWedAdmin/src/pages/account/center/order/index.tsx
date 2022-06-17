import React, { Component } from 'react';

import { List, Tabs, message, Button, Modal, Form, Select, Input } from 'antd';
import orderStyles from "@/pages/account/center/order/order.less";
import cover from '@/assets/product_3.jpg';
import { getOrders, updateOrder } from '@/services/order';
import { DeleteOutlined, SyncOutlined } from '@ant-design/icons';
import cover1 from '@/assets/temp1.jpg';
import cover2 from '@/assets/temp2.jpg';
import cover3 from '@/assets/temp3.jpg';
import cover4 from '@/assets/temp4.jpg';
import cover5 from '@/assets/temp5.jpg';
import cover6 from '@/assets/temp6.jpg';

const { TabPane } = Tabs;

function formatDate(time) {
  let date = new Date(time);
  let Y = date.getFullYear() + '-';
  let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
  let D = date.getDate() + ' ';
  let h = date.getHours() + ':';
  let m = date.getMinutes() + ':';
  let s = date.getSeconds();
  return Y + M + D + h + m + s;
}

const tabs = [
  { title: '等待中' },
  { title: '审核中' },
  { title: '排队中' },
  { title: '进行中' },
  { title: '异常订单' },
  { title: '退款中' },
  { title: '已完成' },
  { title: '已关闭' }
];

class AccountCenterOrder extends Component<any> {

  state = {
    limit: 10,
    total: 0,
    orders: [],
    visible: false,
    id: 0,
    status: '',
    completeNum: 0,
  }
  // fake data
  commodityList = [
    {
      imgCover: cover1,
      title: 'Saint',
      price: 2.01,
      date: '2020-10-27 19:31'
    },
    {
      imgCover: cover2,
      title: 'Orient',
      price: 1.21,
      date: '2020-10-27 14:11'
    },
    {
      imgCover: cover3,
      title: 'Cruel God',
      price: 1.31,
      date: '2020-10-27 10:54'
    }
  ]

  artworkList = [
    {
      imgCover: cover4,
      title: 'Hidoi',
      price: 1.49,
      date: '2020-10-27 22:12'
    },
    {
      imgCover: cover5,
      title: 'Groovy',
      price: 1.64,
      date: '2020-10-27 19:13'
    },
    {
      imgCover: cover6,
      title: 'Cross The Sea',
      price: 1.93,
      date: '2020-10-26 15:34'
    }
  ]

  async componentDidMount() {
    let res = await getOrders(localStorage.getItem("userGroupId"))
    this.setState({
      orders: res.orderItems
    })
  }

  handleCancel = () => {
    this.setState({
      visible: false,
    })
  }

  handleShow = (order) => {
    console.log(order);

    this.setState({
      visible: true,
      id: order.id,
      status: order.status,
      completeNum: order.completeNum
    })
  }

  handleOk = async () => {
    try {
      await updateOrder({
        id: this.state.id,
        status: this.state.status,
        completeNum: this.state.completeNum
      })
      message.success("更新成功")
      this.componentDidMount();
    } catch (e) {
      message.error("更新失败，请重试")
    }
    this.handleCancel();
  }

  // onTabChange() {

  // }

  render() {
    return (
      <div className={orderStyles.orderContainer}>
        <Modal visible={this.state.visible}
          onOk={this.handleOk}
          onCancel={() => this.handleCancel()} title="修改订单状态">
          <Form>
            <Form.Item
              name='completeNum'
              label='完成数量'>
              <Input value={this.state.completeNum} onChange={(e) => {
                this.setState({
                  completeNum: e.target.value
                })
              }} />
            </Form.Item>
            <Form.Item
              name='status'
              label='订单状态'>
              <Select
                value={this.state.status}
                onChange={(value) => {
                  this.setState({
                    status: value
                  })
                }}
              >
                {tabs.map(tab => (
                  <Select.Option key={tab.title}>{tab.title}</Select.Option>
                ))}
              </Select>
            </Form.Item>
          </Form>
        </Modal>
        <div className={orderStyles.headTitle}>订单列表</div>
        <div className={orderStyles.listContainer}>
          {/* <Tabs defaultActiveKey="1" onChange={this.onTabChange}>
            <TabPane tab='素材列表' key="1"> */}
          <List
            itemLayout="vertical"
            size="large"
            // pagination={{
            //   onChange: page => {
            //     this.setState({
            //       offset: page * this.state.limit
            //     })
            //   },
            //   pageSize: this.state.limit
            // }}
            dataSource={this.state.orders}
            renderItem={item => (
              <List.Item>
                <div className={orderStyles.orderItem}>
                  <div className={orderStyles.itemImgBox}>
                    <img className={orderStyles.itemImg} alt='素材图片' src={item.avatarUrl} />
                    {/* <div className={orderStyles.imgTag}>素材</div> */}
                  </div>
                  <div className={orderStyles.itemInfo}>
                    <div className={orderStyles.itemTitle}>
                      <span>{item.userGroupTitle}</span>
                      <Button type="primary" onClick={() => {
                        this.handleShow(item)
                      }}>{item.status}</Button>
                    </div>
                    <div className={orderStyles.itemPrice}>
                      购买价格：{item.orderType === 'group' ? item.largePrice / 100000 : item.largePrice / 100} 电子
                      <div>x{item.totalNum}</div>
                    </div>
                    <div className={orderStyles.itemDate}>{formatDate(item.createTime)}</div>
                    <div className={orderStyles.bottomOpts}>
                      <div className={orderStyles.itemOrderNum}>订单号：{item.orderId}</div>
                      <div className={orderStyles.itemDelete}>
                        <DeleteOutlined />
                        <div className={orderStyles.deleteText}>删除订单</div>
                      </div>
                    </div>
                  </div>
                </div>
              </List.Item>
            )}
          />
          {/* </TabPane> */}
          {/* <TabPane tab='艺术品列表' key="2">
              <List
                itemLayout="vertical"
                size="large"
                pagination={{
                  onChange: page => {
                    this.setState({
                      offset: page * this.state.limit
                    })
                  },
                  pageSize: this.state.limit
                }}
                dataSource={this.artworkList}
                renderItem={item => (
                  <List.Item>
                    <div className={orderStyles.orderItem}>
                      <div className={orderStyles.itemImgBox}>
                        <img className={orderStyles.itemImg} alt='艺术品图片' src={item.imgCover} />
                      </div>
                      <div className={orderStyles.itemInfo}>
                        <div className={orderStyles.itemTitle}>{item.title}</div>
                        <div className={orderStyles.itemPrice}>购买价格：{item.price} IMM</div>
                        <div className={orderStyles.itemDate}>{item.date}</div>
                        <div className={orderStyles.bottomOpts}>
                          <div className={orderStyles.itemResale}>
                            <SyncOutlined />
                            <div className={orderStyles.resaleText}>转手竞价</div>
                          </div>
                          <div className={orderStyles.itemDelete}>
                            <DeleteOutlined />
                            <div className={orderStyles.deleteText}>删除订单</div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </List.Item>
                )}
              />
            </TabPane>
          </Tabs> */}
        </div>
      </div>
    );
  }
}

export default AccountCenterOrder

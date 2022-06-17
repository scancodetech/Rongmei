import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { Card, DatePicker, Flex, List, ListView, Picker, WhiteSpace } from 'antd-mobile'

import WithdrawalIcon from '../../../../assets/QHHBillWithdrawal.svg'
import QHHBillbondIcon from '../../../../assets/QHHBillbond.svg'
import QHHBillShopIcon from '../../../../assets/QHHBillShop.svg'
import QHHBillInComeIcon from '../../../../assets/QHHBillInCome.svg'




const nowTimeStamp = Date.now();
// Make sure that in `time` mode, the maxDate and minDate are within one day.
let minDate = new Date(nowTimeStamp - 1e7);
const maxDate = new Date(nowTimeStamp);
// console.log(minDate, maxDate);
if (minDate.getDate() !== maxDate.getDate()) {
    // set the minDate to the 0 of maxDate
    minDate = new Date(maxDate.getFullYear(), maxDate.getMonth(), maxDate.getDate());
}
function formatDate(date) {
    /* eslint no-confusing-arrow: 0 */
    const pad = n => n < 10 ? `0${n}` : n;
    const dateStr = `${date.getFullYear()}年${pad(date.getMonth() + 1)}月`;
    // const dateStr = `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;
    // const timeStr = `${pad(date.getHours())}:${pad(date.getMinutes())}`;
    // return `${dateStr} ${timeStr}`;
    return `${dateStr}`
}
export default class Bill extends React.Component {
    state = {
        typePicker: [
            {
                label: '全部类型',
                value: '全部类型',
            },
            {
                label: '收入',
                value: '收入',
            },
            {
                label: '支出',
                value: '支出',
            },
        ],
        PickerValue: ['全部类型'],
        DatePickerValue: '',
        // 支出
        expenditure: '123',
        // 收入
        income: '321',

        dataSource: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 }),
    }
    componentDidMount() {
        let newData = this.state.dataSource.cloneWithRows([
            {
                type: '提现',
                name: '交易名',
                price: '-102',
                date: '2021年4月15日 22:22:22',
                id: '111',
            },
            {
                type: '9.9元定制单',
                name: '交易名',
                price: '-102',
                date: '2021年4月15日 22:22:22',
                id: '222',

            },
            {
                type: '接单保证金',
                name: '交易名',
                price: '-102',
                date: '2021年4月15日 22:22:22',
                id: '333',
            },
        ])

        this.setState({
            dataSource: newData
        }, () => { console.log(this.state.dataSource) })
    }
    billIconSelect(type) {
        switch (type) {
            case '提现': return WithdrawalIcon;
            case '9.9元定制单': return QHHBillInComeIcon;
            case '接单保证金': return QHHBillbondIcon;
        }
    }

    render() {
        return (
            <div className='QHHWalletBill'>
                <Header title='账单' />
                <div>
                    <Flex justify='center' direction='column'>
                        <div className='PickerChange'>
                            <Picker
                                title='账单类型'
                                data={this.state.typePicker}
                                cols={1}
                                value={this.state.PickerValue}
                                onChange={(value) => {
                                    this.setState({
                                        PickerValue: value
                                    }, () => { console.log(this.state.PickerValue) })
                                }}
                            >
                                <List.Item arrow="down"></List.Item>
                            </Picker>
                        </div>
                    </Flex>
                </div>
                <WhiteSpace size='xl' />
                <div >
                    <div className='DatePickerChange'>
                        <List>
                            <DatePicker
                                title='时间'
                                mode='month'
                                maxDate={maxDate}
                                extra={this.state.DatePickerValue.hasOwnProperty('year') ? `${this.state.DatePickerValue.year}年${this.state.DatePickerValue.month}月` : `${formatDate(maxDate)}`}
                                format={val => formatDate(val)}
                                // value={this.state.DatePickerValue}
                                onChange={(value) => {
                                    this.setState({
                                        DatePickerValue: { year: value.getUTCFullYear().toString(), month: (value.getUTCMonth() + 1).toString() }
                                    }, () => { console.log(formatDate(value)) })
                                }}
                            >
                                <List.Item arrow='down'></List.Item>
                            </DatePicker>
                            <List.Item>
                                <Flex justify='start' style={{ color: 'white', fontSize: 12, fontWeight: 600 }}>
                                    <div style={{ paddingRight: 10 }}>{`支出：${this.state.expenditure}电子`}</div>
                                    <div>{`收入：${this.state.income}电子`}</div>
                                </Flex>
                            </List.Item>
                        </List>
                    </div>
                </div>

                <div>
                    <ListView
                        dataSource={this.state.dataSource}
                        onEndReached={() => { console.log("endreach") }}
                        onScroll={() => { console.log("onscroll") }}
                        renderSectionWrapper={(sectionID) =>
                            <div className='ListChange' key={sectionID}>
                                <List>

                                </List>
                            </div>
                        }
                        style={{ height: 'calc(100VH - 252px)' }}
                        renderFooter={() => (<div style={{ padding: 30, textAlign: 'center' }}>
                            {this.state.isLoading ? 'Loading...' : 'Loaded'}
                        </div>)}
                        renderRow={(rowData, sectionID, rowID) =>
                            <List.Item key={rowID}
                                onClick={() => { this.props.history.push(`/qhh/wallet/bill/detail/${rowData.id}`) }}
                                activeStyle={{ backgroundColor: '#333333' }}
                                extra={
                                    <div style={{ color: 'red' }}>
                                        {rowData.price}
                                    </div>
                                }
                                thumb={
                                    <img alt='' style={{ width: 30, height: 30, borderRadius: '50%', backgroundColor: 'white', border: '1px solid white' }} src={this.billIconSelect(rowData.type)} />
                                }
                                multipleLine
                            >
                                <div style={{ fontSize: 15, color: 'white', fontWeight: 600, color: '#DDDDDD' }}>
                                    {rowData.name}
                                </div>

                                <List.Item.Brief style={{ fontSize: 12 }}>
                                    {rowData.date}
                                </List.Item.Brief>

                            </List.Item>
                        }
                    />
                    {/* <div className='ListChange'>
                        <List>
                            <List.Item
                                extra={
                                    <div style={{color:'red'}}>
                                        {"-120"}
                                    </div>
                                }
                                thumb={
                                    <img alt='' style={{ width: 25, height: 25, borderRadius: '50%', }} src={'https://picsum.photos/200/300'} />
                                }
                                multipleLine
                            >
                                <div style={{fontSize:15,color:'white',fontWeight:600}}>
                                    {'提现'}
                                </div>

                                <List.Item.Brief style={{fontSize:12}}>
                                    {'2021年5月'}
                                </List.Item.Brief>

                            </List.Item>
                        </List>
                    </div> */}
                </div>

            </div>
        )
    }
}
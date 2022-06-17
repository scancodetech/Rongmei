import React from 'react'
import {withRouter} from "react-router-dom";
import './style.css'
import Header from '../../components/Header/index'
import {
    Flex,
    WhiteSpace,
    WingBlank,
    Icon,
    Button,
    InputItem,
    TextareaItem,
    Modal,
    List,
    Checkbox,
    DatePicker, Toast
} from 'antd-mobile';
import {api} from "../../services/api/ApiProvider";

// import moment from 'moment';

@withRouter
class SaleOut extends React.Component {
    auctionService = api.auctionService;
    state = {
        name: '',
        // 简介
        description: '',
        // 是否设置保证金
        needEarnestMoney: false,
        // 是否拦截
        enableIntercept: false,
        // 是否收取版权费    这里根据原创作者的设置进行显示，卖出方不能修改
        needCopyrightTax: true,
        // 拦截价
        interceptPrice: 999999,
        // 起始价
        startPrice: 0,
        // 加价幅度
        intervalPrice: 0,
        startTime: 0,
        endTime: 0
    }

    async componentDidMount() {
        const thingId = this.props.location.pathname.split('/').pop();
        if (thingId !== 0) {
            const thingRes = await this.auctionService.getThing(thingId);
            this.setState({
                id: thingId,
                name: thingRes.name,
                startPrice: thingRes.price
            })
        }
    }

    async submit() {
        const res = await this.auctionService.updateSale({
            startPrice: this.state.startPrice,
            thingId: this.state.thingId,
            intervalPrice: this.state.intervalPrice,
            startTime: this.state.startTime,
            endTime: this.state.endTime,
            tags: [],
            needEarnestMoney: this.state.needEarnestMoney,
            enableIntercept: this.state.enableIntercept,
            needCopyrightTax: this.state.needCopyrightTax,
            interceptPrice: this.state.interceptPrice,
            rights: []
        })
        if (res.infoCode === 10000) {
            Toast.success("提交成功，请耐心等待审核");
            this.props.history.push('/me');
        }
    }

    render() {
        return (
            <div className='page' style={{minHeight: window.innerHeight - 50}}>
                {/* 页面固定头部 */}
                <Header title='卖出' theme={{mode: 'dark'}}/>

                {/* 上下留白 */}
                <WhiteSpace size='lg'/>

                <div className='mainView'>

                    {/* 名称与简介 */}
                    {/* 注意这两个DIV含有层级样式关系，请勿删除！ */}
                    <div>
                        <div>
                            <div style={{width: 50,}}>名称:</div>
                            <div style={{width: 245, alignSelf: 'center'}}>
                                <InputItem
                                    value={this.state.name}
                                    style={{width: '100%', height: '30px',}}
                                />
                            </div>
                        </div>
                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='sm'/>

                        <div>
                            <div style={{width: 50, alignSelf: 'baseline', paddingTop: 5}}>简介:</div>
                            <div style={{textAlign: 'baseline', width: 245}}>
                                <TextareaItem
                                    rows={3}
                                    value={this.state.description}
                                    onChange={(value) => {
                                        this.setState({description: value}, () => {
                                            console.log(this.state.description)
                                        })
                                    }}
                                />
                            </div>
                        </div>
                    </div>
                    {/* 名称与简介结束 */}

                    {/* 复选框 */}
                    {/* 上下留白 */}
                    <WhiteSpace size='xl'/>
                    {/* 注意这两个DIV含有层级样式关系，请勿删除！ */}
                    <div>
                        <div>
                            <div style={{
                                display: 'flex',
                                alignItems: 'center',
                                justifyItems: 'center',
                                width: '100%',
                                height: 21,
                                lineHeight: '21px'
                            }}>
                                {/* <CheckboxItem/> */}
                                <Checkbox style={{fontSize: 10}}
                                          checked={this.state.needEarnestMoney}
                                          onChange={(e) => {
                                              this.setState({needEarnestMoney: e.target.checked}, () => {
                                                  console.log(this.state.needEarnestMoney)
                                              })
                                          }}/>
                                <div style={{paddingLeft: 15}}>需要保证金</div>
                                <div style={{fontSize: 12, color: '#AAAAAA'}}>（保证金为起拍价的5%）</div>
                            </div>
                        </div>
                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <div>
                            <div style={{
                                display: 'flex',
                                alignItems: 'center',
                                justifyItems: 'center',
                                width: '100%',
                                height: 21,
                                lineHeight: '21px'
                            }}>
                                {/* <CheckboxItem/> */}
                                <Checkbox style={{fontSize: 10}}
                                          checked={this.state.enableIntercept}
                                          onChange={(e) => {
                                              this.setState({enableIntercept: e.target.checked}, () => {
                                                  console.log(this.state.enableIntercept)
                                              })
                                          }}/>
                                <div style={{paddingLeft: 15}}>允许拦截</div>
                            </div>
                        </div>
                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <div>
                            <div style={{
                                display: 'flex',
                                alignItems: 'center',
                                justifyItems: 'center',
                                width: '100%',
                                height: 'auto',
                                lineHeight: '21px'
                            }}>
                                {/* <CheckboxItem/> */}
                                <Checkbox style={{fontSize: 10}}
                                          checked={this.state.needCopyrightTax}
                                          disabled
                                          onChange={(e) => {
                                              this.setState({enableIntercept: e.target.checked}, () => {
                                                  console.log(this.state.enableIntercept)
                                              })
                                          }}/>
                                <div style={{paddingLeft: 15, marginRight: 10}}>收取版权税</div>
                                <div style={{
                                    fontSize: 12,
                                    color: '#AAAAAA',
                                    textAlign: 'start',
                                    width: 170,
                                    wordWrap: 'break-word'
                                }}>开启后在平台任何交易中收取该竞品交易额的10%，作为版权费
                                </div>
                            </div>
                        </div>
                    </div>
                    {/* 复选框结束 */}

                    {/* 表单信息 */}
                    {/* 上下留白 */}
                    <WhiteSpace size='xl'/>

                    <div>
                        {this.state.enableIntercept &&
                        <div>
                            <div style={{width: 60,}}>拦截价：</div>
                            <div style={{width: 230, alignSelf: 'center'}}>
                                <InputItem
                                    value={this.state.interceptPrice}
                                    min={this.state.startPrice}
                                    style={{width: '100%', height: '30px',}}
                                    onChange={(e) => {
                                        // console.log(e)
                                        this.setState({
                                            interceptPrice: e
                                        })
                                    }}
                                />
                            </div>
                        </div>
                        }

                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='sm'/>

                        <div>
                            <div style={{width: 60,}}>起拍价:</div>
                            <div style={{width: 230, alignSelf: 'center'}}>
                                <InputItem
                                    value={this.state.startPrice}
                                    min={0}
                                    suffix={"电子"}
                                    type="number"
                                    style={{width: '100%', height: '30px',}}
                                    onChange={(e) => {
                                        // console.log(e)
                                        this.setState({
                                            startPrice: e
                                        })
                                    }}
                                />
                            </div>
                        </div>
                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='sm'/>

                        <div>
                            <div style={{width: 100,}}>最小加价幅度:</div>
                            <div style={{width: 190, alignSelf: 'center'}}>
                                <InputItem
                                    value={this.state.intervalPrice}
                                    min={0}
                                    suffix={"电子"}
                                    type="number"
                                    style={{width: '100%', height: '30px',}}
                                    onChange={(e) => {
                                        // console.log(e)
                                        this.setState({
                                            intervalPrice: e
                                        })
                                    }}
                                />
                            </div>
                        </div>
                        {/* 上下留白 */}
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='sm'/>

                        <div>
                            <div style={{width: 100,}}>竞价起止时间:</div>
                            <div style={{width: 190, alignSelf: 'center',}}>
                                {/* 这里出现BUG 日期时间选择框无法显示！！！ */}
                                <DatePicker
                                    style={{color: '#fff'}}
                                    value={new Date(this.state.startTime)}
                                    onChange={(date) => {
                                        this.setState({
                                            startTime: date.getTime()
                                        })
                                    }}
                                >
                                    <List.Item style={{color: '#fff'}} arrow="horizontal">开始时间</List.Item>
                                </DatePicker>
                                <DatePicker
                                    style={{color: '#fff'}}
                                    value={new Date(this.state.endTime)}
                                    onChange={(date) => {
                                        this.setState({
                                            endTime: date.getTime()
                                        })
                                    }}>
                                    <List.Item style={{color: '#fff'}} arrow="horizontal">结束时间</List.Item>
                                </DatePicker>
                            </div>
                        </div>
                    </div>
                    {/* 表单信息结束 */}

                    {/* 确认卖出按钮 */}
                    {/* 上下留白 */}
                    <WhiteSpace size='xl'/>
                    <WhiteSpace size='sm'/>
                    <div>
                        <Flex>
                            <Button onClick={() => {
                                console.log("确认卖出")
                            }}>确认卖出</Button>
                        </Flex>
                    </div>
                    {/* 确认卖出按钮结束 */}
                </div>
            </div>
        )
    }
}

export default SaleOut

import React, {Component} from 'react';

import auctionStyles from "@/pages/account/center/auction/auction.less";
import {AutoComplete, Button, Checkbox, DatePicker, Input, message, Modal, Select, Upload} from 'antd';
import {getSale, getThing, getThings, updateSale} from '@/services/auction';
import moment from 'moment';
import {SearchOutlined} from '@ant-design/icons';
import {getTCC} from "@/services/tcc";
import {getMineCertification} from "@/services/apply";

const {RangePicker} = DatePicker;
const {TextArea} = Input;

class AccountCenterSaleContact extends Component<any> {

    state = {
        id: 0,
        startPrice: 0,
        status: '竞价中',
        intervalPrice: 0,
        createTime: new Date().getTime(),
        startTime: new Date().getTime(),
        endTime: new Date().getTime(),
        thingId: 0,
        currPrice: 0,
        thing: null,
        selectedTag: '',
        needEarnestMoney: false,
        uploadLoading: false,
        submitLoading: false,
        things: [],
        tags: ['']
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
        const res = await getThings();
        this.setState({
            things: res.thingItems
        });
        let saleId = this.props.location.pathname.split('/').pop();
        if (saleId != 0) {
            const res = await getSale(saleId);
            this.setState({
                id: saleId,
                startPrice: res.startPrice,
                status: res.status,
                intervalPrice: res.intervalPrice,
                createTime: res.createTime,
                startTime: res.startTime,
                endTime: res.endTime,
                thing: res.thing,
                currPrice: res.currPrice,
                thingId: res.thing.id,
                selectedTag: res.tags.length > 0 ? res.tags[0] : '',
                needEarnestMoney: res.needEarnestMoney
            })
        }
        const nftTypeRes = await getTCC('dimension.nft.type');
        this.setState({
            tags: eval(nftTypeRes.tccTuple.value)
        }, () => {
            console.log(this.state.tags);
        })
    }

    onTagSelect = (value) => {
        this.setState({
            selectedTag: value
        })
    }

    submit = async () => {
        this.setState({
            submitLoading: true
        })
        try {
            await updateSale({
                id: this.state.id,
                startPrice: this.state.startPrice,
                status: this.state.status,
                intervalPrice: this.state.intervalPrice,
                createTime: this.state.createTime,
                startTime: this.state.startTime,
                endTime: this.state.endTime,
                thingId: this.state.thingId,
                tags: [this.state.selectedTag],
                needEarnestMoney: this.state.needEarnestMoney
            })
            message.success("提交成功")
            this.props.history.push('/account/center/auction/sale')
        } catch (e) {
            message.error("提交失败")
        }
        this.setState({
            submitLoading: false
        })
    }

    render() {
        return (
            <div className={auctionStyles.uploadContainer}>
                <div className={auctionStyles.headTitle}>
                    竞价信息
                </div>
                <div className={auctionStyles.generalInfo}>
                    基本信息
                </div>
                <div className={auctionStyles.choose}>
                    <AutoComplete
                        className={auctionStyles.editInput}
                        dropdownClassName="certain-category-search-dropdown"
                        dropdownMatchSelectWidth={false}
                        dropdownStyle={{width: 300}}
                        style={{width: '50%'}}
                        value={this.state.thingId.toString()}
                        dataSource={
                            this.state.things.map((thing) => (
                                <AutoComplete.Option value={thing.id + ""}>
                                    {thing.name}
                                </AutoComplete.Option>
                            ))
                        }
                        onChange={(value) => this.setState({
                            thingId: value
                        }, async () => {
                            let res = await getThing(this.state.thingId)
                            this.setState({
                                thing: res,
                                selectedTag: res.tags.length > 0 ? res.tags[0] : ''
                            })
                        })}
                        placeholder="搜索以选择合适的竞价品"
                    >
                        <Input suffix={<SearchOutlined/>}/>
                    </AutoComplete>
                </div>
                <div>
                    <div className={auctionStyles.generalInfo}>竞品分类</div>
                    <Select value={this.state.selectedTag} style={{marginTop: 10, width: 120}}
                            onChange={this.onTagSelect}>
                        {
                            this.state.tags.map((item) => (
                                <Option value={item.tag}>{item.name}</Option>
                            ))
                        }
                    </Select>
                </div>
                <div className={auctionStyles.edit}>
                    <div className={auctionStyles.editName}>
                        <div className={auctionStyles.editText}>名称</div>
                        <Input className={auctionStyles.editInput} disabled
                               value={this.state.thing == null ? '' : this.state.thing.name}/>
                    </div>
                    <div className={auctionStyles.editDescription}>
                        <div className={auctionStyles.editText}>简介</div>
                        <TextArea rows={4} className={auctionStyles.editInput} disabled
                                  value={this.state.thing == null ? '' : this.state.thing.description}/>
                    </div>
                    {/* <div className={auctionStyles.editTag}>
                        <div className={auctionStyles.editText}>标签</div>
                        <Input className={auctionStyles.editInput} disabled />
                    </div> */}
                    {/* <div className={auctionStyles.editTagDescription}>
                <div className={auctionStyles.editText}>标签简介</div>
                <TextArea rows={4} className={auctionStyles.editInput} placeholder="输入标签简介" />
              </div> */}
                    <div className={auctionStyles.wallet}>
                        {/* <div className={auctionStyles.generalInfo}>
                  钱包信息
                </div> */}
                        {/* <div className={auctionStyles.privateKey}>
                  <div className={auctionStyles.editText}>钱包私钥</div>
                  <Input className={auctionStyles.editInput} placeholder="输入钱包私钥" />
                </div> */}
                        {/* <div className={auctionStyles.walletAdress}>
                  <div className={auctionStyles.editText}>钱包地址</div>
                  <Input className={auctionStyles.editInput} placeholder="输入钱包地址" />
                </div> */}
                        <div className={auctionStyles.isAllowed}>
                            <Checkbox onChange={(e) => {
                                this.setState({
                                    needEarnestMoney: e.target.checked
                                })
                            }} value={this.state.needEarnestMoney}>需要竞拍保证金</Checkbox>
                        </div>
                        {/* <div className={auctionStyles.editPrice}>
                            <div className={auctionStyles.editText}>原始价格</div>
                            <Input className={auctionStyles.editInput} placeholder="" />
                        </div> */}
                        <div className={auctionStyles.startPrice}>
                            <div className={auctionStyles.editText}>起拍价</div>
                            <Input className={auctionStyles.editInput} placeholder=""
                                   onChange={({target: {value}}) => {
                                       this.setState({
                                           startPrice: value
                                       })
                                   }}
                                   defaultValue={this.state.startPrice}/>
                        </div>
                        <div className={auctionStyles.minBid}>
                            <div className={auctionStyles.editText}>最小加价幅度</div>
                            <Input className={auctionStyles.editInput} placeholder=""
                                   onChange={({target: {value}}) => {
                                       this.setState({
                                           intervalPrice: value
                                       })
                                   }} defaultValue={this.state.intervalPrice}/>
                        </div>
                        {/* <div className={auctionStyles.auctionType}>
                  <div className={auctionStyles.editText}>竞价方式</div>
                  <Radio.Group onChange={this.onAuctionType} value={this.state.auctionType}>
                    <Radio value={1}>荷兰拍</Radio>
                    <Radio value={2}>英式拍</Radio>
                  </Radio.Group>
                </div> */}
                        <div className={auctionStyles.timeRange}>
                            <div className={auctionStyles.startTime}>
                                <div className={auctionStyles.editText}>竞价起止时间：</div>
                                <RangePicker
                                    showTime={{format: 'HH:mm'}}
                                    format="YYYY-MM-DD HH:mm"
                                    placeholder={['开始时间', '结束时间']}
                                    value={[moment(this.state.startTime), moment(this.state.endTime)]}
                                    onChange={(value) => {
                                        this.setState({
                                            startTime: new Date(value[0]).getTime(),
                                            endTime: new Date(value[1]).getTime()
                                        })
                                    }}
                                />
                                {/* <DatePicker
                      format="YYYY-MM-DD HH:mm:ss"
                      disabledDate={this.disabledDate}
                      disabledTime={this.disabledDateTime}
                      showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}
                    />
                                    --
                                    <DatePicker
                      format="YYYY-MM-DD HH:mm:ss"
                      disabledDate={this.disabledDate}
                      disabledTime={this.disabledDateTime}
                      showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}
                    /> */}
                            </div>
                        </div>
                    </div>
                </div>

                <Button className={auctionStyles.bottomBtn}
                        onClick={() => {
                            this.submit()
                        }}>开始上传</Button>
                <Modal>
                    {/* 艺术品上传成功 */}
                </Modal>
            </div>
        );
    }
}

export default AccountCenterSaleContact
import React, { Component } from 'react';

import auctionStyles from "@/pages/account/center/auction/auction.less";
import {
    AutoComplete,
    Button,
    Checkbox,
    DatePicker,
    Input,
    message,
    Modal,
    Row,
    Select,
    Space,
    Upload,
    Col,
    Menu,
    InputNumber,
    Image
} from 'antd';
import { getSale, getThing, getThings, updateSale } from '@/services/auction';
import moment from 'moment';
import { SearchOutlined, ExclamationCircleTwoTone } from '@ant-design/icons';
import { getTCC } from "@/services/tcc";
import { getMineCertification } from "@/services/apply";
import { number } from 'prop-types';
import { Card } from '@material-ui/core';

const { RangePicker } = DatePicker;
const { TextArea } = Input;

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
        // needEarnestMoney: false,
        uploadLoading: false,
        submitLoading: false,
        things: [],
        tags: [''],
        // 当前导航栏
        current: 'light',
        // 拦截价
        interceptPrice: 9999999,
        // 是否设置拦截价
        enableIntercept: false,
        // 是否设置保证金
        needEarnestMoney: false,
        // 是否设置版权费
        needCopyrightTax: false,
        // 是否显示注意事项
        isCard: false,
        // 显示手续费模态框
        showTipModal: false,
        neverShowTips: false
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
                startPrice: res.startPrice / 100,
                status: res.status,
                intervalPrice: res.intervalPrice / 100,
                createTime: res.createTime,
                startTime: res.startTime,
                endTime: res.endTime,
                thing: res.thing,
                currPrice: res.currPrice / 100,
                thingId: res.thing.id,
                selectedTag: res.tags.length > 0 ? res.tags[0] : '',
                needEarnestMoney: res.needEarnestMoney,
                interceptPrice: res.interceptPrice / 100,
                enableIntercept: res.enableIntercept,
                needCopyrightTax: res.needCopyrightTax
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
                startPrice: this.state.startPrice * 100,
                status: this.state.status,
                intervalPrice: this.state.intervalPrice * 100,
                createTime: this.state.createTime,
                startTime: this.state.startTime,
                endTime: this.state.endTime,
                thingId: this.state.thingId,
                tags: [this.state.selectedTag],
                needEarnestMoney: this.state.needEarnestMoney,
                interceptPrice: this.state.interceptPrice * 100,
                enableIntercept: this.state.enableIntercept,
                needCopyrightTax: this.state.needCopyrightTax
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

    handleClick = e => {
        console.log('click ', e);
        this.setState({ current: e.key });
    };

    onChange(checkedValues) {
        console.log('checked = ', checkedValues);
    }

    iconOnHover(e) {
        console.log(e)
        this.setState({
            isCard: true
        })
    }

    iconOnOut(e) {
        console.log(e)
        this.setState({
            isCard: false
        })
    }

    async tipsCheckBoxOnChange(e) {
        await this.setState({
            neverShowTips: e.target.checked
        }, () => console.log(this.state.neverShowTips))
        const storage = window.localStorage;
        storage.setItem('isShowTips', (!this.state.neverShowTips).toString())
        this.setState({
            showTipModal: false,
        })
    }

    openShowTipModal() {
        this.setState({
            showTipModal: true,
        })
    }

    closeShowTipModal() {
        this.setState({
            showTipModal: false,
        })
    }

    render() {
        const { current } = this.state;
        return (
            <div className={auctionStyles.uploadContainer}>
                <Menu onClick={this.handleClick} className={auctionStyles.antMenuItemSale} selectedKeys={[current]}
                    mode="horizontal" style={{ textAlign: 'center', fontSize: 20, fontWeight: 600 }}>
                    <Menu.Item key="light">
                        轻巧版
                    </Menu.Item>
                    <Menu.Item key="pro">
                        专业版
                    </Menu.Item>
                </Menu>
                <Space size='middle' direction='vertical'>
                    <Space size='small' direction='horizontal' align='start'>
                        <Space size='middle'>
                            <div className={auctionStyles.headTitle}>
                                竞品信息
                            </div>
                        </Space>

                        <Space size='small' direction='vertical' style={{ position: 'absolute', right: 0 }}>
                            <ExclamationCircleTwoTone twoToneColor='red'
                                style={{ fontSize: 35, marginLeft: 275, padding: '10px 10px' }}
                                onMouseOver={(e) => this.iconOnHover(e)}
                                onMouseOut={(e) => this.iconOnOut(e)} />
                            {this.state.isCard &&
                                <Card style={{ width: 310, fontSize: 15, height: 90 }}>
                                    <p style={{
                                        padding: '5px 10px',
                                        color: 'red'
                                    }}>本平台坚持负面清单制度，如有规定未明确的权利分配，均归会员所有。若要新增个性权利，可联系客服添加。</p>
                                </Card>
                            }

                        </Space>

                    </Space>
                    <Space size='small'>
                        <div className={auctionStyles.generalInfo}>
                            基本信息
                        </div>
                    </Space>

                    <Space size='small'>
                        <AutoComplete
                            className={auctionStyles.wrapperchange}
                            dropdownClassName="certain-category-search-dropdown"
                            dropdownMatchSelectWidth={false}
                            dropdownStyle={{ width: 480 }}
                            style={{ width: 480 }}
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
                            <Input suffix={<SearchOutlined />} />
                        </AutoComplete>
                    </Space>

                    {
                        this.state.thing &&
                        <Space size='small' direction='horizontal'>
                            <Space size='small' direction='vertical'>
                                <Space size='small'>
                                    <div style={{ fontSize: 15, fontWeight: 600 }}>竞品图片</div>
                                </Space>
                                <Space size='small' direction='horizontal'>
                                    <img style={{ objectFit: 'cover', width: 240, height: 300 }}  src={this.state.thing.url} alt="data:image/png;base64,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" />
                                </Space>
                            </Space>
                        </Space>
                    }


                    <Space size='small' direction='horizontal'>
                        <Space size='small' direction='vertical'>
                            <Space size='small'>
                                <div style={{ fontSize: 15, fontWeight: 600 }}>竞品分类</div>
                            </Space>
                            <Space size='small' direction='horizontal'>
                                <Select value={this.state.selectedTag} style={{ width: 480, }}
                                    className={auctionStyles.changeSelect}
                                    onChange={this.onTagSelect}>
                                    {
                                        this.state.tags.map((item) => (
                                            <Option value={item.tag}>{item.name}</Option>
                                        ))
                                    }
                                </Select>
                            </Space>
                        </Space>
                        {/* 专业版权利分类 */}
                        {/* <Space size='small' direction='vertical'>
                            <Space size='small'>
                                <div style={{ fontSize: 15, fontWeight: 600 }}>权利分类</div>
                            </Space>
                            <Space size='small'>
                                <Select value={this.state.selectedTag} style={{ width: 300, }}
                                    onChange={this.onTagSelect}>
                                    {
                                        this.state.tags.map((item) => (
                                            <Option value={item.tag}>{item.name}</Option>
                                        ))
                                    }
                                </Select>
                            </Space>
                        </Space> */}

                    </Space>
                    <Space size='small'>
                        <div style={{ fontSize: 15, fontWeight: 600 }}>权利分类：所有权利均被转移</div>
                    </Space>

                    <Space size='small' direction='vertical'>
                        <Space size='small' direction='vertical'>
                            <div style={{ fontSize: 15, fontWeight: 600 }}>名称</div>
                        </Space>
                        <Space size='small' direction='vertical'>
                            <Input className={auctionStyles.editInput}
                                placeholder={"竞价名称"} disabled
                                value={this.state.thing == null ? '' : this.state.thing.name} />
                        </Space>

                    </Space>

                    <Space size='small' direction='vertical'>
                        <Space size='small'>
                            <div className={auctionStyles.editText}>简介</div>

                        </Space>
                        <Space size='small'>
                            <TextArea rows={4} className={auctionStyles.editInput} disabled
                                style={{ width: 890 }}
                                value={this.state.thing == null ? '' : this.state.thing.description}
                                placeholder={"竞价简介"} />
                        </Space>

                    </Space>
                    <Space size='small'>
                        <Row gutter={[16, 16]}>
                            <Col span={12}>
                                <Space size='small' direction='horizontal'>
                                    <Checkbox checked={this.state.needEarnestMoney} onChange={(e) => {
                                        this.setState({ needEarnestMoney: e.target.checked }, () => {
                                            console.log(this.state.needEarnestMoney)
                                        })
                                    }}>需要保证金</Checkbox>
                                    <div style={{ color: '#555555' }}>(保证金为起拍价的20%)</div>
                                </Space>

                            </Col>
                            <Col span={12}>
                                <Checkbox checked={this.state.enableIntercept} onChange={(e) => {
                                    this.setState({ enableIntercept: e.target.checked })
                                }}>允许拦截</Checkbox>
                            </Col>
                            <Col span={24}>
                                <Space size='small' direction='horizontal'>
                                    <Checkbox checked={this.state.needCopyrightTax} onChange={(e) => {
                                        this.setState({ needCopyrightTax: e.target.checked })
                                    }}>收取版权费</Checkbox>
                                    <div style={{ color: '#555555' }}>(开启后可在平台任何交易中收取该竞品交易额的10%，作为版权费)</div>
                                </Space>
                            </Col>
                        </Row>
                    </Space>

                    <Space size='small' direction='vertical'>

                        <Space direction='vertical'>
                            <Space size='small'>
                                <div className={auctionStyles.editText}>起拍价</div>
                            </Space>
                            <Space size='small'>
                                <Input className={auctionStyles.editInput}
                                    style={{ width: 480 }}
                                    value={this.state.startPrice}
                                    min={0}
                                    suffix={"电子"}
                                    type="number"
                                    onChange={({ target: { value } }) => {
                                        this.setState({
                                            startPrice: value
                                        })
                                    }}
                                />
                            </Space>
                        </Space>

                        <Space size='small'>
                            <div className={auctionStyles.editText}>最小加价幅度</div>
                        </Space>
                        <Space size='small'>
                            <InputNumber className={auctionStyles.editInput}
                                style={{ width: 480 }}
                                value={this.state.intervalPrice}
                                min={0}
                                onChange={(e) => {
                                    // console.log(e)
                                    this.setState({
                                        intervalPrice: e
                                    })
                                }}
                            />
                        </Space>

                        {
                            this.state.enableIntercept &&
                            <div>
                                <Space direction='vertical'>
                                    <Space size='small'>
                                        <div className={auctionStyles.editText}>拦截价</div>
                                    </Space>
                                    <Space size='small'>
                                        <InputNumber className={auctionStyles.editInput}
                                            value={this.state.interceptPrice}
                                            style={{ width: 480 }}
                                            min={this.state.startPrice}
                                            onChange={(e) => {
                                                // console.log(e)
                                                this.setState({
                                                    interceptPrice: e
                                                })
                                            }}
                                        />
                                    </Space>
                                </Space>
                            </div>
                        }

                        <Space size="small" direction='vertical'>
                            <Space size='small'>
                                <div className={auctionStyles.editText}>竞价起止时间：</div>
                            </Space>

                            <Space size='small'>
                                <RangePicker
                                    showTime={{ format: 'HH:mm' }}
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
                            </Space>
                        </Space>
                    </Space>

                    <Space size='small'>
                        <div style={{ width: '890px', textAlign: 'center' }}>
                            <Button className={auctionStyles.bottomBtn}
                                onClick={async () => {
                                    // 本地存储用户选择信息
                                    if (!window.localStorage) {
                                        console.log("浏览器不支持localstorage");
                                        this.submit();
                                    } else {
                                        //主逻辑业务
                                        const storage = window.localStorage;
                                        // await storage.removeItem('isShowTipes') //生产环境下取消
                                        const isShowTips = await storage.getItem("isShowTips")
                                        console.log(isShowTips)

                                        if (isShowTips == null || isShowTips == '' || isShowTips == 'true') {
                                            this.openShowTipModal();
                                        } else {
                                            this.submit();
                                        }
                                    }
                                }}>开始上传</Button>
                        </div>
                    </Space>
                </Space>

                <Modal
                    visible={this.state.showTipModal}
                    confirmLoading={this.state.submitLoading}
                    onOk={async () => {
                        this.submit();
                        this.closeShowTipModal();
                    }}
                    onCancel={() => this.closeShowTipModal()}
                    okText="确认"
                    cancelText="取消"
                >
                    <p style={{ fontSize: 16, fontWeight: 600, textAlign: 'center' }}>竞品成交后将收取15%的平台服务费</p>
                    <div style={{ textAlign: 'center' }}>
                        <Checkbox onChange={(e) => this.tipsCheckBoxOnChange(e)}
                            style={{ color: '#999999' }}>下次不再提示</Checkbox>

                    </div>
                </Modal>
            </div>
        );
    }

}

export default AccountCenterSaleContact

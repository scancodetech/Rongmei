import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import {
    Flex,
    Picker,
    WhiteSpace,
    WingBlank,
    List,
    InputItem,
    ImagePicker,
    Button,
    Modal,
    Checkbox,
    Toast,
    ListView,
    Card,
    Carousel,
    TextareaItem
} from 'antd-mobile'
import {api} from '../../../services/api/ApiProvider'
import {uploadFileToCos} from "../../../utils/utils";

function countDown(timeDiff) {
    let hourDiff = Math.floor((timeDiff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)) + "";
    let minuteDiff = Math.floor((timeDiff % (60 * 60 * 1000)) / (60 * 1000)) + "";
    let secondDiff = Math.floor((timeDiff % 60000) / 1000) + "";
    if (hourDiff.length === 1) {
        hourDiff = "0" + hourDiff;
    }
    if (minuteDiff.length === 1) {
        minuteDiff = "0" + minuteDiff;
    }
    if (secondDiff.length === 1) {
        secondDiff = "0" + secondDiff;
    }
    return hourDiff + ":" + minuteDiff + ":" + secondDiff;
}

class Customized extends React.Component {
    accountService = api.accountService;
    moneyService = api.moneyService;
    boxOrderService = api.boxOrderService;
    uploadService = api.uploadService;
    state = {
        // 最终用户选择的定制数据
        customType: [],
        customStyle: [],
        customTheme: [],
        elementRequirements: '',
        example: [],
        price: 999,
        // 世界观内容
        worldOutlook: '',
        // 拼单类型
        cooperateType: '',

        // 类型选择器
        typePicker: [
            {
                label: '大头',
                value: '大头',
            },
            {
                label: 'Q版',
                value: 'Q版',
            },
            {
                label: '半身',
                value: '半身',
            },
            {
                label: '正比',
                value: '正比',
            },
            {
                label: '其他',
                value: '其他',
            },
        ],
        // 风格选择器
        stylePicker: [
            {
                label: '萌妹',
                value: '萌妹',
            },
            {
                label: '酷哥',
                value: '酷哥',
            },
            {
                label: '御姐',
                value: '御姐',
            },
            {
                label: '叔系',
                value: '叔系',
            },
            {
                label: '正太',
                value: '正太',
            },
            {
                label: '古风',
                value: '古风',
            },
            {
                label: '其他',
                value: '其他',
            },
        ],
        // 主题选择器
        themePicker: [
            {
                label: '十二生肖主题盒蛋',
                value: '十二生肖主题盒蛋',
            },
            {
                label: '自定义主题',
                value: '自定义主题',
            }
        ],
        // 世界观选择器
        worldOutlookPicker: [
            {
                label: '恐龙人世界观',
                value: '恐龙人世界观',
            },
            {
                label: '其他',
                value: '其他',
            },
        ],
        worldOutlookExtra: '请选择',
        typeExtra: '请选择',
        styleExtra: '请选择',
        themeExtra: '请选择',
        // 选择支付宝支付方式
        Check: true,
        // 绘制立即充值代码
        body: '',
        showConfirmModal: false,
        // 拼单项选择对话框
        showCooperateModal: false,
        // 查看更多拼单对话框
        showMoreOrderModal: false,
        rechargeAmount: 9.99,
        // listView 的datasource
        dataSource: new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        }),
        // 推荐拼单列表
        recommendData: [],
    }

    async componentDidMount() {
        const price = this.props.history.location.pathname.split("/").pop();
        await this.setState({price})
        const res = await this.boxOrderService.getSharingBoxOrder();
        if (res.boxOrderShareItemList) {
            let newDataSource = this.state.dataSource.cloneWithRows(res.boxOrderShareItemList)
            await this.setState({recommendData: res.boxOrderShareItemList, dataSource: newDataSource})
        }
    }

    onImageChange = (files, type, index) => {
        this.setState({
            example: files
        });
    }

    async getWechatQrcode() {
        let res = await this.accountService.postWechat(this.state.rechargeAmount, window.location.href, window.returnCitySN.cip)
        window.location.href = res.body
    }

    async getAlipayQrcode() {
        const btnNode = "<input type='submit' value='立即支付' class='confirmClass'/>";
        // console.log(this.state.totalAmount);
        let res = await this.accountService.postAlipay(this.state.rechargeAmount, window.location.href)
        let formNodes = res.body.split('\n');
        const formDom = formNodes[0] + '\n' + formNodes[1] + '\n' + btnNode + '\n</form>';
        this.setState({
            body: formDom
        })
    }

    async submit(orderType) {
        if (this.state.customType.length <= 0) {
            Toast.fail("请选择定制类别");
            return;
        }
        if (this.state.customStyle.length <= 0) {
            Toast.fail("请选择定制风格");
            return;
        }
        if (this.state.customTheme.length <= 0) {
            Toast.fail("请选择定制主题");
            return;
        }
        if (this.state.example.length <= 0) {
            Toast.fail("请上传例图");
            return;
        }
        const res = await uploadFileToCos(this.state.example[0].file.name, this.state.example[0].file);
        if (res.statusCode === 200) {
            try {
                Toast.info("正在从积分扣款");
                const res = await this.accountService.consumeDiscount(this.state.price);
                if (res.infoCode === 10000) {
                    Toast.info("积分扣款成功");
                    await this.boxOrderService.submitBoxOrderRequest({
                        id: 0,
                        orderType: orderType,
                        cooperateType: this.state.cooperateType,
                        customType: this.state.customType[0],
                        worldOutlook: this.state.worldOutlook[0],
                        customStyle: this.state.customStyle[0],
                        customTheme: this.state.customTheme[0],
                        elementRequirements: this.state.elementRequirements,
                        exampleUrl: "https://" + res.Location,
                        price: this.state.price - 100
                    })
                    Toast.info("提交需求成功");
                }
            } catch (e) {
                Toast.info("积分不足，请先充值，充值后请重新提交");
                this.setState({showConfirmModal: true})
            }
        }
    }

    shareOrder = async (id) => {
        try {
            Toast.info("正在从积分扣款");
            const res = await this.accountService.consumeDiscount(this.state.price);
            if (res.infoCode === 10000) {
                Toast.info("积分扣款成功");
                await this.boxOrderService.shareBoxOrder(id)
                this.componentDidMount();
                Toast.info("提交需求成功");
            }
        } catch (e) {
            Toast.info("积分不足，请先充值");
            this.setState({showConfirmModal: true})
        }
    }

    ItemsInCarousel(data) {
        const Items = [];
        const ListNumber = parseInt((data.length / 3) + (data.length % 3 === 0 ? 0 : 1))
        for (let i = 0; i < ListNumber - 1; i++) {
            Items.push(
                <List>
                    {
                        (() => {
                            let item = [];
                            for (let j = i * 3; j <= i * 3 + 2; j++) {
                                item.push(
                                    <List.Item
                                        thumb={
                                            <div style={{padding: '0 0 0 10px'}}>
                                                <img style={{width: 40, height: 40, borderRadius: '50%'}}
                                                     src={data[j].imageUrl}/>
                                            </div>
                                        }
                                        extra={
                                            <div>
                                                <Flex justify='between'>
                                                    <Flex direction='column' align='center'>
                                                        <div style={{
                                                            color: 'white',
                                                            fontWeight: 600,
                                                            fontSize: 12
                                                        }}>还差<span style={{color: '#fe2341'}}>{1}人</span>拼成
                                                        </div>
                                                        <div>剩余：<span>{countDown(data[j].endTime - new Date().getTime())}</span>
                                                        </div>
                                                    </Flex>
                                                    <Flex>
                                                        <div className='btnChange'>
                                                            <Button
                                                                onClick={() => this.shareOrder(data[j].id)}>去拼单</Button>
                                                        </div>
                                                    </Flex>
                                                </Flex>
                                            </div>
                                        }
                                    >
                                        <div style={{
                                            width: '80%',
                                            textAlign: 'start',
                                            alignItems: 'center',
                                            display: 'flex',
                                            justifyContent: 'center'
                                        }}>
                                            <Flex direction='column' justify='start' align='center'>
                                                <div style={{fontSize: 12, color: 'white'}}>{data[j].usernames[0]}</div>
                                                <div
                                                    className='typeLabel'>{data[j].cooperateType.length > 0 ? data[j].cooperateType : '风格拼单'}</div>
                                            </Flex>
                                        </div>
                                    </List.Item>
                                )
                            }
                            return item
                        })()

                    }
                </List>
            )
        }
        Items.push(
            <List>
                {
                    (() => {
                        let item = []
                        for (let k = data.length - 1; k >= (data.length - 1) - (data.length - 1) % 3; k--) {
                            item.push(
                                <List.Item
                                    thumb={
                                        <div style={{padding: '0 0 0 10px'}}>
                                            <img style={{width: 40, height: 40, borderRadius: '50%'}}
                                                 src={data[k].imageUrl}/>
                                        </div>
                                    }
                                    extra={
                                        <div>
                                            <Flex justify='between'>
                                                <Flex direction='column' align='center'>
                                                    <div style={{color: 'white', fontWeight: 600, fontSize: 12}}>还差<span
                                                        style={{color: '#fe2341'}}>{1}人</span>拼成
                                                    </div>
                                                    <div>剩余：<span>{countDown(data[k].endTime - new Date().getTime())}</span>
                                                    </div>
                                                </Flex>
                                                <Flex>
                                                    <div className='btnChange'>
                                                        <Button onClick={() => this.shareOrder(data[k].id)}>去拼单</Button>
                                                    </div>
                                                </Flex>
                                            </Flex>
                                        </div>
                                    }
                                >
                                    <div style={{
                                        width: '80%',
                                        textAlign: 'start',
                                        alignItems: 'center',
                                        display: 'flex',
                                        justifyContent: 'center'
                                    }}>
                                        <Flex direction='column' justify='start' align='center'>
                                            <div style={{fontSize: 12, color: 'white'}}>{data[k].usernames[0]}</div>
                                            <div
                                                className='typeLabel'>{data[k].cooperateType.length > 0 ? data[k].cooperateType : '风格拼单'}</div>
                                        </Flex>
                                    </div>
                                </List.Item>
                            )
                        }
                        return item
                    })()
                }
            </List>
        )
        const x = []
        x.push(
            <div>1</div>
        )
        console.log(typeof x)

        // this.coopListItem = Items;
        return (Items)
    }

    render() {
        return (
            <div className='customizedPage'>
                {/* 标题可以通过服务器请求动态获取 */}
                <Header title={`${this.state.price / 100}定制`} theme={{mode: 'dark'}}/>
                <WhiteSpace size='xl'/>

                <WingBlank size='lg'>
                    <div>
                        <Flex justify='start' direction='column'>
                            {/* 定制类型 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{width: 80, fontSize: 14}}>定制类型</div>
                                    <div style={{width: 250}}>
                                        <Picker
                                            title="选择定制类别"
                                            data={this.state.typePicker}
                                            value={this.state.customType}
                                            cols={1}
                                            onChange={(value) => {
                                                this.setState({
                                                    customType: value,
                                                    typeExtra: value[0]
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                       className='pickerListChange'
                                                       style={{textAlign: 'center',}}/>
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg'/>
                                {
                                    this.state.typeExtra.toString() === "其他" &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{width: 80, fontSize: 14}}/>
                                            <div style={{width: 250}}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的类型'
                                                    onChange={(value) => {
                                                        this.setState({
                                                            customType: [value]
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg'/>
                                    </div>

                                }
                            </div>
                            {/* 定制类型结束 */}

                            {/* 定制风格 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{width: 80, fontSize: 14}}>定制风格</div>
                                    <div style={{width: 250}}>
                                        <Picker
                                            title="选择定制风格"
                                            data={this.state.stylePicker}
                                            value={this.state.customStyle}
                                            cols={1}
                                            onChange={(value) => {
                                                this.setState({
                                                    customStyle: value,
                                                    styleExtra: value[0]
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                       className='pickerListChange'
                                                       style={{textAlign: 'center',}}/>
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg'/>
                                {
                                    this.state.styleExtra.toString() === '其他' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{width: 80, fontSize: 14}}/>
                                            <div style={{width: 250}}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的风格'
                                                    onChange={(value) => {
                                                        this.setState({
                                                            customStyle: [value]
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg'/>
                                    </div>

                                }
                            </div>
                            {/* 定制结束风格 */}

                            {/* 世界观要求 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{width: 80, fontSize: 14}}>世界观：</div>
                                    <div style={{width: 250}} className='TextareaChange'>
                                        <Picker
                                            title="选择世界观"
                                            data={this.state.worldOutlookPicker}
                                            value={this.state.worldOutlook}
                                            cols={1}
                                            onChange={(value) => {
                                                this.setState({
                                                    worldOutlook: value,
                                                    worldOutlookExtra: value[0]
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                       className='pickerListChange'
                                                       style={{textAlign: 'center',}}/>
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg'/>
                                {
                                    this.state.worldOutlookExtra.toString() === '其他' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{width: 80, fontSize: 14}}/>
                                            <div style={{width: 250}} className='TextareaChange'>
                                                <TextareaItem
                                                    rows={4}
                                                    disabled={this.state.worldOutlookExtra.toString() !== '其他'}
                                                    placeholder='请输入世界观'
                                                    onChange={(value) => {
                                                        this.setState({
                                                            worldOutlook: [value]
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg'/>
                                    </div>
                                }
                            </div>
                            {/* 世界观要求结束 */}

                            {/* 定制主题 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{width: 80, fontSize: 14}}>定制主题</div>
                                    <div style={{width: 250}}>
                                        <Picker
                                            title="选择定制主题"
                                            data={this.state.themePicker}
                                            value={this.state.customTheme}
                                            cols={1}
                                            onChange={(value) => {
                                                this.setState({
                                                    customTheme: value,
                                                    themeExtra: value[0]
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                       className='pickerListChange'
                                                       style={{textAlign: 'center',}}/>
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg'/>
                                {
                                    this.state.themeExtra.toString() === '自定义主题' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{width: 80, fontSize: 14}}/>
                                            <div style={{width: 250}}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的主题'
                                                    onChange={(value) => {
                                                        this.setState({customTheme: [value]})
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg'/>
                                    </div>

                                }
                            </div>
                            {/* 定制主题结束 */}

                            {/* <WhiteSpace size='lg' /> */}

                            {/* 元素要求 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{width: 80, fontSize: 14}}>元素要求</div>
                                    <div style={{width: 250}}>
                                        <InputItem
                                            className='pickerListChange'
                                            placeholder='请输入你的元素要求'
                                            onChange={(value) => {
                                                this.setState({elementRequirements: value})
                                            }}
                                        />
                                    </div>
                                </Flex>
                            </div>
                            {/* 元素要求结束 */}
                            <div style={{fontSize: 12, transform: 'scale(0.8)'}}>元素要求仅能提一个，可详细说明。例如：希望能有蝴蝶元素。</div>
                            <WhiteSpace size='lg'/>

                            {/* 参考例图 */}
                            <div>
                                <Flex justify='around' align='start'>
                                    <div style={{width: 80, fontSize: 14}}>参考例图</div>
                                    <div style={{width: 250,}} className='imagePickerChange'>
                                        <ImagePicker
                                            files={this.state.example}
                                            length={1}
                                            selectable={this.state.example.length < 1}
                                            multiple={false}
                                            onChange={this.onImageChange}
                                        />
                                    </div>
                                </Flex>

                                <WhiteSpace size='lg'/>
                            </div>
                            {/* 参考例图结束 */}
                        </Flex>
                    </div>

                </WingBlank>


                {/* 拼单开始 */}
                {
                    this.state.recommendData.length > 0 ?
                        <div className='customizedOrderBanner'>
                            <div className='BannerDiv'>
                                <div style={{borderBottom: '1px solid #444444'}}>
                                    <WingBlank size='lg'>
                                        <Flex justify='center' direction='column' align='center'>
                                            <Flex justify='between' style={{width: '100%',}} align='center'>
                                                <div>{this.state.recommendData.length}人在拼单，可直接参与</div>
                                                <div onClick={() => {
                                                    this.setState({showMoreOrderModal: true})
                                                }}>查看更多
                                                </div>
                                            </Flex>
                                        </Flex>
                                    </WingBlank>
                                </div>
                            </div>


                            {/* 走马灯 */}
                            {
                                this.state.recommendData.length &&
                                <Carousel
                                    vertical={true}
                                    autoplay={true}
                                    infinite
                                    dots={false}
                                    autoplayInterval={5000}
                                >
                                    {
                                        this.ItemsInCarousel(this.state.recommendData)
                                    }
                                </Carousel>
                            }
                        </div> : null
                }
                {/* 拼单结束 */}


                <WingBlank size='lg'>
                    {/* 定制说明 */}
                    <div>
                        <Flex direction='column' align='start' className='rule'>
                            <div style={{color: '#FE2341'}}>定制说明</div>
                            <div>1、会员接受创作者使用模板；</div>
                            <div>2、交稿格式为透明底PNG&源文件PSD；</div>
                            <div>3、创作者的创作方式为数字化创作（如板绘）；</div>
                            <div>4、交稿画布尺寸不低于2000*2000px；分辨率不低于72ppi；</div>
                            <div>5、完稿后有提出一条修改意见的机会，修改后自动完成交易；</div>
                            <div>6、版权完全交割给会员，画师仅提供创作服务；</div>
                            <div>7、在跨次元“我的”资产区中可查收已完成的定制盲盒；</div>
                            <div>8、定制盲盒盒蛋转卖须知：（1）未养所定制盲盒卖出不得高于原定制价格；（2）定制盲盒可作为打赏礼物送出，价值等同于定制价格；其他具体须知查看链接：</div>
                            <div>9、订单由跨次元平台保障。</div>
                        </Flex>
                    </div>
                    {/* 定制说明结束 */}
                    <WhiteSpace size='xl'/>
                    {/* <WhiteSpace size='md' /> */}
                    {/* 发起定制按钮 */}
                    {/* <Flex justify='center'>
                        <div className='btnChange'>
                            <Button onClick={() => {
                                this.submit();
                            }}>发起定制</Button>
                        </div>
                    </Flex> */}
                    {/* 发起定制按钮结束 */}
                    <WhiteSpace size='md'/>
                    {/* 服务协议 */}
                    {/* <div style={{ fontSize: 12, transform: 'scale(0.8)' }}>发起定制即代表你已阅读并同意<span style={{ color: '#FE2341' }}
                        onClick={() => {
                            console.log("协议跳转")
                        }}>《跨次元平台测试服务协议》</span>
                    </div> */}
                    {/* 服务协议结束 */}


                    <WhiteSpace size='md'/>
                </WingBlank>


                <div>
                    <Flex justify='end'>
                        <div style={{color: '#fe2341', fontWeight: 600, fontSize: 12, padding: '10px 10px'}}>
                            拼单定制将更快被画师接单哦~
                        </div>
                    </Flex>
                </div>
                {/* 新的发起定制按钮 */}
                <div>
                    <Flex style={{width: '100%'}}>
                        <div style={{width: '50%'}} className='submitBtnChange'>
                            <Button onClick={() => {
                                this.submit(0)
                            }}>9.99发起单独定制</Button>
                        </div>
                        <div style={{width: '50%'}} className='submitBtnChange'>
                            <Button style={{backgroundColor: '#fe2341'}} onClick={() => {
                                this.setState({showCooperateModal: true})
                            }}>8.99发起拼团定制</Button>
                        </div>
                    </Flex>
                </div>
                {/* 新的发起定制按钮结束 */}


                {/* 充值支付页面 */}
                <Modal
                    visible={this.state.showConfirmModal}
                    // title='充值'
                    style={{width: '100%', height: 265, position: 'absolute', bottom: 0,}}
                    maskClosable={false}
                    closable
                    onClose={() => {
                        this.setState({showConfirmModal: false})
                    }}
                >
                    <WingBlank size='lg'>
                        <WhiteSpace size='lg'/>
                        <WhiteSpace size='md'/>
                        <div>
                            <Flex justify='center'>
                                <div style={{fontSize: 25, fontWeight: 600,}}>{'¥' + this.state.rechargeAmount}</div>
                            </Flex>
                            <Flex justify='center'>
                                {/* 这里后续需要根据平台充值优惠政策修改 */}
                                <div style={{fontSize: 13, fontWeight: 600,}}>{this.state.rechargeAmount + '电子'}</div>
                            </Flex>
                        </div>
                        <WhiteSpace size='lg'/>

                        <div style={{borderTop: '2px solid #333333'}}>
                            <WhiteSpace size='sm'/>
                            <Flex justify='center' onClick={() => {
                                this.setState({Check: true})
                            }}>
                                <div style={{height: 50, width: '100%'}}>
                                    <Flex justify='around'>
                                        <Flex justify='start'
                                              style={{width: '40%', color: 'white', fontSize: 15, fontWeight: 600}}>
                                            <div className='wechatIcon'/>
                                            <div style={{height: 50, lineHeight: '50px', paddingLeft: 20}}>微信支付</div>
                                        </Flex>
                                        <Flex justify='end'
                                              style={{width: '40%', color: 'white', fontSize: 15, fontWeight: 600,}}>
                                            <Checkbox checked={this.state.Check} onChange={(e) => {
                                                this.setState({Check: true})
                                            }}/>
                                        </Flex>
                                    </Flex>
                                </div>
                            </Flex>
                            <Flex justify='center' onClick={() => {
                                this.setState({Check: false})
                                this.getAlipayQrcode();
                            }}>
                                <div style={{height: 50, width: '100%'}}>
                                    <Flex justify='around'>
                                        <Flex justify='start'
                                              style={{width: '40%', color: 'white', fontSize: 15, fontWeight: 600}}>
                                            <div className='alipayIcon'/>
                                            <div style={{height: 50, lineHeight: '50px', paddingLeft: 20}}>支付宝支付</div>
                                        </Flex>
                                        <Flex justify='end'
                                              style={{width: '40%', color: 'white', fontSize: 15, fontWeight: 600,}}>
                                            <Checkbox checked={!(this.state.Check)} onChange={(e) => {
                                                this.setState({Check: false})
                                            }}/>
                                        </Flex>
                                    </Flex>
                                </div>
                            </Flex>
                        </div>
                        <WhiteSpace size='sm'/>
                        {/* 这里的支付方式可以通过this.state.Check来获得，true时为微信支付，false时为支付宝支付，如后续增加 */}
                        {
                            this.state.Check ? <div>
                                <input type='submit' style={{
                                    background: '#1AAD19',
                                    height: '40px',
                                    lineHeight: '40px',
                                    textAlign: 'center',
                                    color: '#fff'
                                }} onClick={() => this.getWechatQrcode()} value='微信支付'/>
                            </div> : <div dangerouslySetInnerHTML={{__html: this.state.body}}/>
                        }
                    </WingBlank>
                </Modal>
                {/* 充值支付页面结束 */}


                {/* 更多拼单列表对话框 */}
                <Modal
                    visible={this.state.showMoreOrderModal}
                    onClose={() => {
                        this.setState({showMoreOrderModal: false})
                    }}
                    closable={true}
                    maskClosable={false}
                    transparent={true}
                    title='可参与的拼单'
                    style={{width: '95%', height: '80%'}}
                    className='PHHCustModal'
                    wrapClassName='PHHCustWrap'
                >
                    <div>
                        <ListView
                            dataSource={this.state.dataSource}
                            renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                                {this.state.isLoading ? 'Loading...' : 'Loaded'}
                            </div>)}
                            onScroll={() => {
                                console.log('scroll');
                            }}
                            onEndReached={() => {
                                console.log('onend');
                            }}
                            style={{height: '500px'}}
                            className='customizedPage'
                            renderSectionWrapper={() =>
                                <List className='customizedOrderBanner'/>
                            }
                            renderRow={(dataItem) =>
                                <List.Item
                                    thumb={
                                        <div style={{padding: '0 0 0 10px'}}>
                                            <img style={{width: 40, height: 40, borderRadius: '50%'}}
                                                 src={dataItem.imageUrl}/>
                                        </div>
                                    }
                                    extra={
                                        <div>
                                            <Flex justify='between'>
                                                <Flex direction='column' align='center'>
                                                    <div style={{color: 'white', fontWeight: 600, fontSize: 12}}>还差<span
                                                        style={{color: '#fe2341'}}>1人</span>拼成
                                                    </div>
                                                    <div>剩余：<span>{countDown(dataItem.endTime - new Date().getTime())}</span>
                                                    </div>
                                                </Flex>
                                                <Flex>
                                                    <div className='btnChange'>
                                                        <Button
                                                            onClick={() => this.shareOrder(dataItem.id)}>去拼单</Button>
                                                    </div>
                                                </Flex>
                                            </Flex>
                                        </div>
                                    }
                                >
                                    <div style={{
                                        width: '80%',
                                        textAlign: 'start',
                                        alignItems: 'center',
                                        display: 'flex',
                                        justifyContent: 'center'
                                    }}>
                                        <Flex direction='column' justify='start' align='center'>
                                            <div style={{fontSize: 12, color: 'white'}}>{dataItem.usernames[0]}</div>
                                            <div
                                                className='typeLabel'>{dataItem.cooperateType.length > 0 ? dataItem.cooperateType : '风格拼单'}</div>
                                        </Flex>
                                    </div>
                                </List.Item>
                            }
                        />
                    </div>
                </Modal>
                {/* 更多拼单列表对话框结束 */}


                {/* 8.99拼单对话框 */}
                <Modal
                    visible={this.state.showCooperateModal}
                    closable={true}
                    maskClosable={false}
                    onClose={() => {
                        this.setState({showCooperateModal: false})
                    }}
                    transparent={true}
                    title='拼单项选择'
                    style={{width: '95%', height: 'auto', color: 'white'}}
                    className='PHHCustModal'
                    wrapClassName='PHHCustWrap'
                >
                    <div className='listItem'>
                        <List style={{width: '100%'}}>
                            <Flex direction='column' justify='center'>
                                <Checkbox.CheckboxItem className='checkBox'
                                                       checked={this.state.cooperateType === '类型拼单'}
                                                       onChange={() => {
                                                           this.setState({cooperateType: '类型拼单'})
                                                       }}>类型拼单</Checkbox.CheckboxItem>
                                <Checkbox.CheckboxItem className='checkBox'
                                                       checked={this.state.cooperateType === '风格拼单'}
                                                       onChange={() => {
                                                           this.setState({cooperateType: '风格拼单'})
                                                       }}>风格拼单</Checkbox.CheckboxItem>
                                <Checkbox.CheckboxItem className='checkBox'
                                                       checked={this.state.cooperateType === '世界观拼单'}
                                                       onChange={() => {
                                                           this.setState({cooperateType: '世界观拼单'})
                                                       }}
                                >世界观拼单</Checkbox.CheckboxItem>
                                <Checkbox.CheckboxItem className='checkBox'
                                                       checked={this.state.cooperateType === '主题拼单'}
                                                       onChange={() => {
                                                           this.setState({cooperateType: '主题拼单'})
                                                       }}
                                >主题拼单</Checkbox.CheckboxItem>
                                <Checkbox.CheckboxItem className='checkBox'
                                                       checked={this.state.cooperateType === '元素拼单'}
                                                       onChange={() => {
                                                           this.setState({cooperateType: '元素拼单'})
                                                       }}
                                >元素拼单</Checkbox.CheckboxItem>
                            </Flex>
                        </List>

                        <div>
                            <Flex justify='around'>
                                <div className='submitBtn'>
                                    <Button style={{backgroundColor: '#999999'}} onClick={() => {
                                        this.setState({showCooperateModal: false})
                                    }}>取消</Button>
                                </div>
                                <div className='submitBtn'>
                                    <Button style={{backgroundColor: '#FE2341'}} onClick={() => {
                                        this.submit(1);
                                        this.setState({showCooperateModal: false})
                                    }}>确定</Button>
                                </div>
                            </Flex>
                        </div>
                    </div>
                </Modal>
                {/* 8.99拼单对话框结束 */}


            </div>
        )
    }

}

export default Customized

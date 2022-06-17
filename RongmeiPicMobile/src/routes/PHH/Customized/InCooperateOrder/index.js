import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import {
    Flex,
    Picker,
    WhiteSpace,
    WingBlank,
    List,
    InputItem,
    ImagePicker,
    Button,
    Carousel,
    TextareaItem
} from 'antd-mobile'
export default class InCooperateOrder extends React.Component {
    state = {
        // 最终用户选择的定制数据
        customizedValue: {
            customType: [],
            customStyle: [],
            customTheme: [],
            elementRequirements: '',
            example: [],
            price: 899,
            // 世界观内容
            worldOutlook: '',
            // 拼单类型
            cooperateType: '',
        },
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
        typeExtra: '请选择',
        styleExtra: '请选择',
        themeExtra: '请选择',
        worldOutlookExtra: '请选择',
    }

    onImageChange = (files, type, index) => {
        console.log(files, type, index);
        this.setState((state) => {
            return {
                customizedValue: Object.assign({}, state.customizedValue, { example: files })
            }
        });
    }
    render() {
        return (
            <div className='customizedPage'>
                {/* 标题可以通过服务器请求动态获取 */}
                <Header title={`${this.state.customizedValue.price / 100}定制`} theme={{ mode: 'dark' }} />
                <WhiteSpace size='xl' />

                <WingBlank size='lg'>
                    <div>
                        <Flex justify='start' direction='column'>
                            {/* 定制类型 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{ width: 80, fontSize: 14 }}>定制类型</div>
                                    <div style={{ width: 250 }}>
                                        <Picker
                                            title="选择定制类别"
                                            data={this.state.typePicker}
                                            cols={1}
                                            extra={this.state.typeExtra}
                                            onChange={(value) => {
                                                // this.state.typeExtra = value
                                                this.setState((state) => {
                                                    return {
                                                        customizedValue: Object.assign({}, state.customizedValue, { customType: value }),
                                                        typeExtra: value
                                                    }
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                className='pickerListChange'
                                                style={{ textAlign: 'center', }} />
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg' />
                                {
                                    console.log(this.state.typeExtra),
                                    this.state.typeExtra.toString() === "其他" &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{ width: 80, fontSize: 14 }} />
                                            <div style={{ width: 250 }}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的类型'
                                                    onChange={(value) => {
                                                        const customizedValueCopy = this.state.customizedValue;
                                                        customizedValueCopy.type = value
                                                        this.setState((state) => {
                                                            return {
                                                                customizedValue: Object.assign({}, state.customizedValue, { customType: value }),
                                                            }
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg' />
                                    </div>

                                }
                            </div>
                            {/* 定制类型结束 */}

                            {/* 定制风格 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{ width: 80, fontSize: 14 }}>定制风格</div>
                                    <div style={{ width: 250 }}>
                                        <Picker
                                            title="选择定制风格"
                                            data={this.state.stylePicker}
                                            cols={1}
                                            extra={this.state.styleExtra}
                                            onChange={(value) => {
                                                this.setState((state) => {
                                                    return {
                                                        customizedValue: Object.assign({}, state.customizedValue, { customStyle: value }),
                                                        styleExtra: value
                                                    }
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                className='pickerListChange'
                                                style={{ textAlign: 'center', }} />
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg' />
                                {
                                    this.state.styleExtra.toString() === '其他' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{ width: 80, fontSize: 14 }} />
                                            <div style={{ width: 250 }}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的风格'
                                                    onChange={(value) => {
                                                        this.setState((state) => {
                                                            return {
                                                                customizedValue: Object.assign({}, state.customizedValue, { customStyle: value })
                                                            }
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg' />
                                    </div>

                                }
                            </div>
                            {/* 定制结束风格 */}

                            {/* 世界观要求 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{ width: 80, fontSize: 14 }}>世界观：</div>
                                    <div style={{ width: 250 }} className='TextareaChange'>
                                        <Picker
                                            title="选择世界观"
                                            data={this.state.worldOutlookPicker}
                                            cols={1}
                                            extra={this.state.worldOutlookExtra}
                                            onChange={(value) => {
                                                this.setState((state) => {
                                                    return {
                                                        customizedValue: Object.assign({}, state.customizedValue, { worldOutlook: value }),
                                                        worldOutlookExtra: value
                                                    }
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                className='pickerListChange'
                                                style={{ textAlign: 'center', }} />
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg' />
                                {
                                    this.state.worldOutlookExtra.toString() !== '请选择' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{ width: 80, fontSize: 14 }} />
                                            <div style={{ width: 250 }} className='TextareaChange'>
                                                <TextareaItem
                                                    rows={4}
                                                    // 可以替换为一个函数返回具体世界观介绍
                                                    value={this.state.worldOutlookExtra.toString() !== '其他' ? '内置的世界观' : ''}
                                                    disabled={this.state.worldOutlookExtra.toString() !== '其他'}
                                                    // className='pickerListChange'
                                                    placeholder='请输入世界观'
                                                    onChange={(value) => {
                                                        this.setState((state) => {
                                                            return {
                                                                customizedValue: Object.assign({}, state.customizedValue, { customTheme: value })
                                                            }
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg' />
                                    </div>
                                }
                            </div>
                            {/* 世界观要求结束 */}

                            {/* 定制主题 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{ width: 80, fontSize: 14 }}>定制主题</div>
                                    <div style={{ width: 250 }}>
                                        <Picker
                                            title="选择定制主题"
                                            data={this.state.themePicker}
                                            cols={1}
                                            extra={this.state.themeExtra}
                                            onChange={(value) => {
                                                this.state.themeExtra = value
                                                this.setState((state) => {
                                                    return {
                                                        customizedValue: Object.assign({}, state.customizedValue, { customTheme: value })
                                                    }
                                                })
                                            }}
                                        >
                                            <List.Item arrow="down"
                                                className='pickerListChange'
                                                style={{ textAlign: 'center', }} />
                                        </Picker>
                                    </div>
                                </Flex>
                                <WhiteSpace size='lg' />
                                {
                                    this.state.themeExtra.toString() === '自定义主题' &&
                                    <div>
                                        < Flex justify='around' align='center'>
                                            <div style={{ width: 80, fontSize: 14 }} />
                                            <div style={{ width: 250 }}>
                                                <InputItem
                                                    className='pickerListChange'
                                                    placeholder='请输入你想定制的主题'
                                                    onChange={(value) => {
                                                        this.setState((state) => {
                                                            return {
                                                                customizedValue: Object.assign({}, state.customizedValue, { customTheme: value })
                                                            }
                                                        })
                                                    }}
                                                />
                                            </div>
                                        </Flex>
                                        <WhiteSpace size='lg' />
                                    </div>

                                }
                            </div>
                            {/* 定制主题结束 */}

                            {/* <WhiteSpace size='lg' /> */}

                            {/* 元素要求 */}
                            <div>
                                <Flex justify='around' align='center'>
                                    <div style={{ width: 80, fontSize: 14 }}>元素要求</div>
                                    <div style={{ width: 250 }}>
                                        <InputItem
                                            className='pickerListChange'
                                            placeholder='请输入你的元素要求'
                                            onChange={(value) => {
                                                this.setState((state) => {
                                                    return {
                                                        customizedValue: Object.assign({}, state.customizedValue, { elementRequirements: value })
                                                    }
                                                })
                                            }}
                                        />
                                    </div>
                                </Flex>
                            </div>
                            {/* 元素要求结束 */}
                            <div style={{ fontSize: 12, transform: 'scale(0.8)' }}>元素要求仅能提一个，可详细说明。例如：希望能有蝴蝶元素。</div>
                            <WhiteSpace size='lg' />

                            {/* 参考例图 */}
                            <div>
                                <Flex justify='around' align='start'>
                                    <div style={{ width: 80, fontSize: 14 }}>参考例图</div>
                                    <div style={{ width: 250, }} className='imagePickerChange'>
                                        <ImagePicker
                                            files={this.state.customizedValue.example}
                                            multiple={false}
                                            selectable={this.state.customizedValue.example <= 1}
                                            onChange={this.onImageChange}
                                        />
                                    </div>
                                </Flex>

                                <WhiteSpace size='lg' />
                            </div>
                            {/* 参考例图结束 */}
                        </Flex>
                    </div>

                </WingBlank>
                <div style={{ position: 'absolute', bottom: 0 }}>
                    <Flex justify='center' style={{ width: '100%' }}>
                        <div className='submitBtn'>
                            <Button>8.99确认定制</Button>
                        </div>
                    </Flex>
                </div>
            </div>
        )
    }
}
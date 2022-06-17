import React from 'react'
import './style.css'
import { Flex, List, WhiteSpace, ImagePicker, WingBlank, Button, Modal, ListView, Toast, InputItem } from 'antd-mobile'
import Header from '../../../../components/Header/index'

class Application extends React.Component {
    state = {
        nickName: '',
        QQNumber: '',
        socialMedia: '',
        works: [],

    }
    onChange = (files, type, index) => {
        console.log(files, type, index);
        this.setState({
            works: files
        });
    }
    render() {
        const type = this.props.match.params.type
        return (
            <div className='applicationPage'>
                {/* 可以通过路由传值获取 */}
                <Header title={`认证申请【${type === 'auction' ? '竞价区' : type === 'material' ? '素材区' : type === 'egg' ? '盒蛋区' : ''}】`} theme={{ mode: 'dark' }} />
                <WingBlank size='lg'>
                    <Flex justify='center' align='center' direction='column' className='qhhRowChange'>
                        <WhiteSpace size='lg' />
                        <Flex justify='around' style={{ padding: 10 }}>
                            <div style={{ width: 80 }}>圈名<span style={{ color: '#FE2341' }}>*</span>：</div>
                            <InputItem
                                className='applicationInputItemChange'
                                onChange={(value) => { this.setState({ nickName: value }, () => { console.log(this.state.nickName) }) }}
                            />
                        </Flex>
                        <Flex justify='around' style={{ padding: 10 }}>
                            <div style={{ width: 80 }}>QQ号<span style={{ color: '#FE2341' }}>*</span>：</div>
                            <InputItem
                                className='applicationInputItemChange'
                                onChange={(value) => { this.setState({ QQNumber: value }, () => { console.log(this.state.QQNumber) }) }}
                            />
                        </Flex>
                        <Flex justify='around' style={{ padding: 10 }}>
                            <div style={{ width: 80 }}>社交媒体：</div>
                            <InputItem
                                className='applicationInputItemChange'
                                onChange={(value) => { this.setState({ socialMedia: value }, () => { console.log(this.state.socialMedia) }) }}
                            />
                        </Flex>
                        <Flex justify='around' style={{ padding: 10 }} align='start'>
                            <div style={{ width: 80 }}>过往原创<span style={{ color: '#FE2341' }}>*</span>：</div>
                            <div style={{ width: 180 }} className='applicationImagePickerChange'>
                                <ImagePicker
                                    length={1}
                                    files={this.state.works}
                                    multiple={true}
                                    onChange={this.onChange}
                                />
                            </div>
                        </Flex>
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='xl'/>
                        <WhiteSpace size='xl'/>

                        <Flex>
                            <div className='applicationButtonChange'>
                                <Button>提交审核</Button>
                            </div>
                        </Flex>
                    </Flex>
                </WingBlank>

            </div>
        )
    }
}
export default Application

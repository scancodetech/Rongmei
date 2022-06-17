import React from 'react'
import './style.css'
import { Flex, List, WhiteSpace, ImagePicker, WingBlank, Button, Modal, ListView, Toast, InputItem } from 'antd-mobile'
import Header from '../../../components/Header/index'

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
        return (
            <div className='applicationPage'>
                <Header title='创作者申请' theme={{ mode: 'dark' }} />
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
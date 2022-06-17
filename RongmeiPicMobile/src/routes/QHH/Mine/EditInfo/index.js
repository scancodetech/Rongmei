import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { Flex, List, WhiteSpace, Button, Toast, WingBlank, InputItem, TextareaItem } from 'antd-mobile'

export default class QHHEditInfo extends React.Component {
    state = {
        nickName: '123',
        intro: '321',
    }
    render() {
        return (
            <div className='QHHEditInfoPage'>
                <Header title='编辑资料' >
                    <div style={{ color: '#FFFF00' }}>保存</div>
                </Header>
                <div className='banner'>
                    <Flex direction='column' justify='center' align='center'>
                        <Flex justify='between'>
                            <div style={{ width: 80, fontSize: 14, color: 'white' }}>昵称：</div>
                            <div style={{ width: 200 }}>
                                <InputItem
                                    clear
                                    value={this.state.nickName}
                                    placeholder={this.state.nickName}
                                    onChange={(value) => { this.setState({ nickName: value }) }}
                                />
                            </div>

                        </Flex>
                        <WhiteSpace size='xl' />
                        <Flex justify='between' align='start'>
                            <div style={{ width: 80, fontSize: 12, color: 'white' }}>个人简介：</div>
                            <div style={{ width: 200 }}>
                                <TextareaItem
                                    rows={4}
                                    value={this.state.intro}
                                    // value={this.state.intor}
                                    onChange={(value) => { this.setState({ intro: value }) }}
                                />
                            </div>

                        </Flex>
                    </Flex>
                </div>
            </div>
        )
    }
}
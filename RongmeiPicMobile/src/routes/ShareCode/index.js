import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { Button, Flex, Toast } from 'antd-mobile'

import bodyCodeIcon from '../../assets/mineShareCodeBodyCode.png'

export default class ShareCode extends React.Component {
    state = {
        shareCode: 'F3JF',
        shareNumber: 2,
        shareUrl: 'https://m.dimension.pub/#/picmobile/sale/446',
    }
    copyUrl() {
        // 复制到剪贴板API
        navigator.clipboard.writeText(this.state.shareUrl).then(function () {
            Toast.success('已复制链接，快去邀请吧')
        }, function () {
            Toast.fail('复制链接失败，请手动复制')
        })
    }
    render() {
        return (
            <div className='shareCodePage'>
                <Header title='我的邀请' />
                <div className='Banner'>
                    <Flex direction='column' justify='center' style={{ color: 'white', fontWeight: 600, fontSize: 15 }}>
                        <div style={{ color: '#E75168', fontSize: 28, fontWeight: 600, paddingTop: 30 }}>跨次元</div>
                        <div style={{ padding: '10px 0' }}>邀请你加入我们   快来~</div>
                        <div>我在这里等你</div>
                        <div className='body'>
                            <Flex justify='center' direction='column'>
                                <img src={bodyCodeIcon} style={{ height: 40, width: 270, }} />
                                <div style={{ color: '#999999', fontSize: 12, transform: 'scale(0.9)', fontWeight: 600 }}>{this.state.shareUrl}</div>
                                <div style={{ fontSize: 20, color: 'black', padding: '20px 0 10px 0' }}>你的邀请码</div>

                                <Flex justify='between' align='center'>
                                    <div className='shareCodeBorder'>{this.state.shareCode[0]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[1]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[2]}</div>
                                    <div className='shareCodeBorder'>{this.state.shareCode[3]}</div>
                                </Flex>

                                <div style={{ color: 'black', paddingTop: 30 }}>您可邀请3名好友</div>
                                <div style={{ color: '#EEEEEE', fontSize: 45 }}>DIMENSION</div>

                                <div className='Btn'>
                                    <Button onClick={() => this.copyUrl()}>立即邀请</Button>
                                </div>
                            </Flex>
                        </div>

                        <div className='bottom'>
                            <Flex justify='center' align='center' direction='column'>
                                <div style={{ padding: '15px 0', fontSize: 16 }}>我的邀请</div>
                                <Flex justify='around' align='center' style={{ width: '100%' }}>
                                    <Flex direction='column' align='center'>
                                        <div style={{ fontSize: 12, color: '#AAAAAA' }}>已邀请人数</div>
                                        <div style={{ fontSize: 30, padding: '10px 0' }}>{this.state.shareNumber}</div>
                                    </Flex>
                                    <Flex direction='column' align='center'>
                                        <div style={{ fontSize: 12, color: '#AAAAAA' }}>还可邀请人数</div>
                                        <div style={{ fontSize: 30, padding: '10px 0' }}>{3 - this.state.shareNumber}</div>
                                    </Flex>
                                </Flex>
                            </Flex>
                        </div>
                    </Flex>
                </div>
            </div>
        )
    }
}
import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import { Flex, WingBlank } from 'antd-mobile'

import changeInfoIcon from '../../../assets/qhhChangeInfo.svg'

export default class Mine extends React.Component {
    state = {
        avatar: 'https://picsum.photos/200/300',
        name: '11123123123',
        intro: '123123~',
        orderNumber: '123',
        appraiseNumber: '321',
        completeNumber: '223',
    }

    render() {
        const state = this.state
        return (
            <div className='QHHMinePage'>
                <Header title='个人中心' />

                <div className='avatar'>
                    <Flex direction='column' align='center' justify='center'>
                        <img alt='' style={{ width: 70, height: 70, borderRadius: '50%', marginBottom: 10 }} src={state.avatar} />
                        <Flex justify='center'>
                            <div style={{ fontSize: 14, padding: '5px 0', color: 'white', paddingLeft: 22 }}>{this.state.name}</div>
                            <img alt='' src={changeInfoIcon} style={{ width: 12, height: 12, marginLeft: 10, lineHeight: '22px', objectFit: 'cover' }} onClick={() => { this.props.history.push('/qhh/mine/editInfo') }} />
                        </Flex>
                        <div style={{ fontSize: 12, padding: '5px 0', color: '#AAAAAA', overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{this.state.intro}</div>
                    </Flex>
                </div>

                <WingBlank size='md'>
                    <div className='banner'>
                        <div className='title'>创作者</div>
                        <Flex>
                            <Flex.Item>
                                <Flex justify='between' direction='column' align='center'>
                                    <div style={{ fontSize: 15, color: '#FFFF00', padding: '15px 0' }}>{`${state.orderNumber}单`}</div>
                                    <div style={{ fontSize: 12, color: '#AAAAAA', }}>已接单</div>
                                </Flex>
                            </Flex.Item>
                            <Flex.Item>
                                <Flex justify='center' direction='column'>
                                    <div style={{ fontSize: 15, color: '#FFFF00', padding: '15px 0' }}>{`${state.appraiseNumber}单`}</div>
                                    <div style={{ fontSize: 12, color: '#AAAAAA' }}>已反馈</div>
                                </Flex>
                            </Flex.Item>
                            <Flex.Item>
                                <Flex justify='center' direction='column'>
                                    <div style={{ fontSize: 15, color: '#FFFF00', padding: '15px 0' }}>{`${state.completeNumber}单`}</div>
                                    <div style={{ fontSize: 12, color: '#AAAAAA' }}>已完结</div>
                                </Flex>
                            </Flex.Item>
                        </Flex>
                    </div>

                </WingBlank>

            </div>
        )

    }
}

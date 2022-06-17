import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { Flex } from 'antd-mobile'

export default class Contact extends React.Component {
    render() {
        return (
            <div className='contactPage'>
                <Header title='联系我们' />
                <div style={{ position: 'absolute', left: '50%', top: '50%', transform: 'translate(-50%,-50%)', width: 260, height: 280, backgroundColor: '#000000', borderRadius: 5 }}>
                    <Flex justify='center' direction='column'>
                        <div className='CARContactDivStyle'>
                            <div>长按保存图片</div>
                            <div className='CARContactUSIcon' />
                            <div>
                                <div style={{ color: "white", padding: '5px 0 ' }}>扫一扫~</div>
                                <div style={{ color: 'white', padding: '5px 0' }}>了解更多信息哦~</div>
                            </div>
                        </div>
                    </Flex>
                </div>
            </div>
        )
    }
}
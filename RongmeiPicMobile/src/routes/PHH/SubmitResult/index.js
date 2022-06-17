import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import { Flex, List, WhiteSpace } from 'antd-mobile'

class SubmitResult extends React.Component {
    state = {
        submitResult: true
    }
    render() {
        return (
            <div className='submitPage'>
                {/* 可以设置几秒后自动跳转回我的页面，减少BUG产生概率 */}
                {/* <Header title='9.99定制' theme={{ mode: 'dark' }} /> */}
                {
                    this.state.submitResult &&
                    <Flex justify='center' direction='column'>
                        <div className='submitLogo' />
                        <WhiteSpace size='xl' />
                        <div>定制需求提交成功</div>
                        <WhiteSpace size='lg' />
                        <div>可在“我的--拼盒盒”中查看进度</div>
                    </Flex>
                }
                {
                    !this.state.submitResult &&
                    <Flex justify='center'>
                        <div>提交失败</div>
                    </Flex>
                }

            </div>
        )
    }
}
export default SubmitResult
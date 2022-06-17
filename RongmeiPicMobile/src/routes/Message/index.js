import React from 'react'
import Header from '../../components/Header/index'
import {withRouter} from 'react-router-dom';
import Demo from './MessageItem/MessageItem'
import RenderInBody from "./MessageItem/RenderInBody";
import ReactDOM from "react-dom";

const message_data = [
    {
        message_type: '0',
        message_time: '2021/4/15 12:34:23',
        content: '尊敬的李又又12138，您溜设的内容已通过审核！\n快去首页推荐看看吧～',
    },
    {
        message_type: '2',
        message_time: '2021/4/18 10:12:45',
        content: '您的提现申请 ：100电子 已成功到账。'
    },
    {
        message_type: '1',
        message_time: '2021/4/15 12:34:23',
        content: '时间给予设群  X  跨次元平台，联合发起活动 活动时间：2021.4.27-5.4 一起来溜自己最爱的设子，\n' +
            '+点赞最高的崽将获得第一届人气王称号哦~ 具体操作流程如下：https://shimo.im/docs/DOzTjZM4L7EGdM0l 奖品如下：\n' +
            'A:最高人气将获得200元现金奖励（打入你的跨次元平台账户）\n' +
            'B:所有参加的会员均获得一张个商水印卡（活动结束后发放到跨次元账户中，可以免费抽取精美水印）',
        activity_link: 'https://xxxy.com'
    },
]

@withRouter
class Message extends React.Component {
    render() {
        return (
            <div style={{backgroundColor: '#222'}}>
                <Header title='消息' theme={{ mode: 'dark' }} />
                <Demo/>
            </div>
        )
    }
}

export default Message

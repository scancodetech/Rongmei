import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import { List, WhiteSpace, Modal, Flex, InputItem, WingBlank, Button, ImagePicker } from 'antd-mobile'

class CreatorCenter extends React.Component {
    state = {
        CreatorCenterItems: [
            {
                label: '竞价区',
                route: '/setting/creatorcenter/auction',
                key: 0
            },
            {
                label: '素材区',
                route: '/setting/creatorcenter/commodity',
                key: 1
            },
            {
                label: '盒蛋区',
                route: '/setting/creatorcenter/blindbox',
                key: 2
            },
        ]
    }
    render() {
        return (
            <div className='creatorCenterPage'>
                <Header title='铸造' theme={{ mode: 'dark' }} />

                <WhiteSpace size='lg' />
                <div className='creatorCenterListChange'>
                    <List>
                        {
                            this.state.CreatorCenterItems.map((item, index) =>
                                <List.Item arrow='horizontal'
                                    key={item.key}
                                    activeStyle={{ backgroundColor: '#222222' }}
                                    onClick={() => { this.props.history.push(item.route) }}
                                >
                                    {item.label}
                                </List.Item>
                            )
                        }

                    </List>
                </div>
            </div>
        )
    }
}
export default CreatorCenter

import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { List, WhiteSpace, Modal, Toast } from 'antd-mobile'
const alert = Modal.alert;


class Setting extends React.Component {
    state = {
        settingItems: [
            {
                label: '修改信息',
                route: '/setting/info',
                key: 0
            },
            {
                label: '修改密码',
                route: '/setting/password',
                key: 1
            },
            {
                label: '创作者中心【铸造】',
                route: '/setting/creatorcenter',
                key: 2
            },
            {
                label: '存证与维权',
                route: '/setting/certificationandreport',
                key: 3
            },
            {
                label: '申请npc',
                route: '',
                key: 4
            },
            {
                label: '抢盒盒',
                route: '/qhh',
                key: 5
            },
            {
                label: '智囊团',
                route: '',
                key: 6
            },
            {
                label: '全部协议',
                route: '/setting/agreement',
                key: 7
            },
            

        ]
    }
    // 退出登录框
    showLogout = () => {
        alert('退出登录', '你确定要退出登录吗?', [
            { text: '取消', onPress: () => console.log('cancel'), style: 'default' },
            {
                text: '确定', onPress: () => {
                    localStorage.clear();
                    this.props.history.push('/login')
                }
            },
        ]);
    };

    render() {
        return (
            <div className='settingPage'>
                <Header title='设置' theme={{ mode: 'dark' }} />
                <WhiteSpace size='lg' />
                <div className='settingListChange'>
                    <List>
                        {
                            this.state.settingItems.map((item, index) =>
                                <List.Item arrow='horizontal'
                                    key={item.key}
                                    activeStyle={{ backgroundColor: '#222222' }}
                                    onClick={() => {
                                        if(item.key===6){
                                            window.location.href = 'https://m.dimension.pub/thinktank/#/login'
                                        }
                                        else if (item.route === '') {
                                            Toast.fail('正在开发中~')
                                        }
                                        else {
                                            this.props.history.push(item.route);
                                        }
                                    }}
                                >
                                    {item.label}
                                </List.Item>
                            )
                        }
                        <List.Item arrow='horizontal'
                            key='logout'
                            activeStyle={{ backgroundColor: '#222222' }}
                            onClick={() => { this.showLogout() }}
                        >
                            {`退出账号`}
                        </List.Item>
                    </List>
                </div>
            </div>
        )
    }
}
export default Setting
import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import { List, WhiteSpace, Modal, Flex, InputItem, WingBlank, Button, ImagePicker } from 'antd-mobile'

export default class CertificationAndReport extends React.Component {
    state = {
        CertificationAndReportItems: [
            {
                label: '铸造',
                route: '/setting/creatorcenter',
                key: 0
            },
            {
                label: '存证查询',
                route: '/setting/certificationandreport/certification',
                key: 1
            },
            {
                label: '维权',
                route: '/setting/certificationandreport/report',
                key: 2
            },
            // {
            //     label: '规则须知',
            //     route: '/setting/creatorcenter/egg',
            //     key: 3
            // },
            {
                label: '联系我们',
                route: '/setting/certificationandreport/contact',
                key: 4
            },
        ]
    }
    render() {
        return (
            <div className='CARPage'>
                <Header title='存证与维权' theme={{ mode: 'dark' }} />
                <WhiteSpace size='lg' />
                <div className='certificationandreportListChange'>
                    <List>
                        {
                            this.state.CertificationAndReportItems.map((item, index) =>
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
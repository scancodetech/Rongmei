import React from 'react'
import CustomMenu from "../CustomMenu/index";

class SiderNav extends React.Component {
    render() {

        return (
            <div style={{height: '100vh', overflowY: 'scroll'}}>
                <div style={styles.logo}></div>
                <CustomMenu menus={
                    [
                        {
                            title: '首页',
                            icon: 'home',
                            key: '/home',
                        },
                        {
                            title: '权限管理',
                            icon: 'desktop',
                            key: '/home/access',
                            subs: [
                                {key: '/home/access/role', title: '角色管理', icon: '',},
                                {key: '/home/access/employee', title: '员工列表', icon: '',}
                            ]
                        },
                        {
                            title: '平台管理',
                            icon: 'laptop',
                            key: '/home/platform',
                        },
                        {
                            title: '素材追溯',
                            icon: 'laptop',
                            key: '/home/commodity',
                        },
                        {
                            title: '组管理',
                            icon: 'usergroup-add',
                            key: '/home/group',
                        },
                        {
                            title: '财务管理',
                            icon: 'laptop',
                            key: '/home/pay',
                            subs: [
                                {key: '/home/pay/recharge', title: '充值列表', icon: '',},
                                {key: '/home/pay/withdraw', title: '提现列表', icon: '',}
                            ]
                        },
                        {
                            title: '审核',
                            icon: '',
                            key: '/home/certification',
                            subs: [
                                {
                                    title: '分区审核',
                                    icon: '',
                                    key: '/home/certification/roleaudit',
                                    subs: [
                                        {
                                            title: '创作者资料',
                                            icon: '',
                                            key: '/home/certification/roleaudit/user'
                                        },
                                        {
                                            title: '代表作',
                                            icon: '',
                                            key: '/home/certification/roleaudit/masterpiece'
                                        }
                                        // {title: '机构', key: '/home/certification/roleaudit/', icon: ''}
                                        // {title:'素材区',key:'',icon:''},
                                        // {title:'盒蛋区',key:'',icon:''},
                                        // {title:'审核规则',key:'',icon:''},
                                    ]
                                },
                                {
                                    title: '内容审核',
                                    icon: '',
                                    key: '/home/certification/content',
                                    subs: [
                                        {
                                            title: '素材',
                                            icon: '',
                                            key: '/home/certification/content/commodity'
                                        },
                                        {
                                            title: '拍品',
                                            icon: '',
                                            key: '/home/certification/content/sale'
                                        },
                                        {
                                            title: '盒蛋',
                                            icon: '',
                                            key: '/home/certification/content/blindboxsale'
                                        },
                                        {
                                            title: '巨人车',
                                            icon: '',
                                            key: '/home/certification/content/groupshopping'
                                        },
                                        {
                                            title: '提案',
                                            icon: '',
                                            key: '/home/certification/content/proposal'
                                        },
                                        {
                                            title: '溜孩子',
                                            icon: '',
                                            key: '/home/certification/content/child'
                                        }
                                    ]
                                },
                            ]
                        },
                        {
                            title: '考试',
                            icon: '',
                            key: '/home/exam',
                            subs: [
                                {
                                    title: '题目',
                                    icon: '',
                                    key: '/home/exam/exercise'
                                },
                                {
                                    title: '审核题目',
                                    icon: '',
                                    key: '/home/exam/checkexercise'
                                }
                            ]
                        },
                        {
                            title: '话题管理',
                            icon: 'desktop',
                            key: '/home/topic',
                        },
                        {
                            title: '举报',
                            icon: 'info-circle-o',
                            key: '/home/report'
                        },

                        {
                            title: '关于我们',
                            icon: 'info-circle-o',
                            key: '/home/about'
                        }
                    ]
                }/>
            </div>
        )
    }
}

const styles = {
    logo: {
        height: '32px',
        background: 'rgba(255, 255, 255, .2)',
        margin: '16px'
    }
}

export default SiderNav

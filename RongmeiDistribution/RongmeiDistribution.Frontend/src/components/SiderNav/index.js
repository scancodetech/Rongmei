import React from 'react'
import CustomMenu from "../CustomMenu/index";

const menus = [
    {
        title: '首页',
        icon: 'home',
        key: '/home',
    },
    {
        title: '用户信息',
        icon: 'desktop',
        key: '/home/info',
    },
    {
        title: '分销信息',
        icon: 'fork',
        key: '/home/level',
    },
    {
        title: '关于我们',
        icon: 'info-circle-o',
        key: '/home/about'
    }
]


class SiderNav extends React.Component {
    render() {

        return (
            <div style={{height: '100vh', overflowY: 'scroll'}}>
                <div style={styles.logo}></div>
                <CustomMenu menus={menus}/>
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

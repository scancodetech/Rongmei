/**
 * Ant Design Pro v4 use `@ant-design/pro-layout` to handle Layout.
 * You can view component api by:
 * https://github.com/ant-design/ant-design-pro-layout
 */
import ProLayout, {
    MenuDataItem,
    BasicLayoutProps as ProLayoutProps,
    Settings,
} from '@ant-design/pro-layout';
import React, {useState, useEffect} from 'react';
import {Link, connect, Dispatch} from 'umi';
import {Result, Button} from 'antd';
import Authorized from '@/utils/Authorized';
import RightContent from '@/components/GlobalHeader/RightContent';
import {ConnectState} from '@/models/connect';
import {getAuthorityFromRouter} from '@/utils/utils';
import {MenuDataModelState} from "@/models/menuData";
import logo from '../assets/logo.png';
import Footer from './Footer';
import {UserInfoModelState} from "@/models/userInfo";
import styles from '@/layouts/BasicLayout.less';

const noMatch = (
    <Result
        status={403}
        title="403"
        subTitle="Sorry, you are not authorized to access this page."
        extra={
            <Button type="primary">
                <Link to="/user/login">前往登录</Link>
            </Button>
        }
    />
);

export interface BasicLayoutProps extends ProLayoutProps {
    breadcrumbNameMap: {
        [path: string]: MenuDataItem;
    };
    route: ProLayoutProps['route'] & {
        authority: string[];
    };
    settings: Settings;
    dispatch: Dispatch;
    userInfo: UserInfoModelState;
    history: any;
}

export type BasicLayoutContext = { [K in 'location']: BasicLayoutProps[K] } & {
    breadcrumbNameMap: {
        [path: string]: MenuDataItem;
    };
};
/**
 * use Authorized check all menu item
 */

const menuDataRender = (menuList: MenuDataItem[]): MenuDataItem[] =>
    menuList.map((item) => {
        const localItem = {...item, children: item.children ? menuDataRender(item.children) : []};
        return Authorized.check(item.authority, localItem, null) as MenuDataItem;
    });

const BasicLayout: React.FC<BasicLayoutProps> = (props) => {
    const {
        dispatch,
        children,
        settings,
        location = {
            pathname: '/',
        },
        history
    } = props;

    /**
     * init variables
     */
    const handleMenuCollapse = (payload: boolean): void => {
        if (dispatch) {
            dispatch({
                type: 'global/changeLayoutCollapsed',
                payload,
            });
        }
    }; // get children authority

    const authorized = getAuthorityFromRouter(props.route.routes, location.pathname || '/') || {
        authority: undefined,
    };
    return (
        <ProLayout
            className={styles.global}
            logo={logo}
            menuHeaderRender={(logoDom, titleDom) => (
                <Link to="/" style={{marginRight: '0', width: '1000px'}}>
                    <h1 style={{marginLeft: '100px', fontWeight: 'bold', fontSize: '24px'}}>跨次元创作服务平台</h1>
                </Link>
            )}
            onCollapse={handleMenuCollapse}
            menuItemRender={(menuItemProps, defaultDom) => {
                if (menuItemProps.isUrl || menuItemProps.children || !menuItemProps.path) {
                    return defaultDom;
                }
                return <Link to={menuItemProps.path}>{defaultDom}</Link>;
            }}
            itemRender={(route, params, routes, paths) => {
                const first = routes.indexOf(route) === 0;
                return first ? (
                    <Link to={paths.join('/')}>{route.breadcrumbName}</Link>
                ) : (
                    <span>{route.breadcrumbName}</span>
                );
            }}
            // footerRender={() => <Footer/>}
            menuDataRender={menuDataRender}
            rightContentRender={() => <div style={{paddingRight: '100px'}}><RightContent history={history}/></div>}
            {...props}
            {...settings}
        >
            <Authorized authority={authorized!.authority} noMatch={noMatch}>
                {children}
            </Authorized>
            <Footer></Footer>
        </ProLayout>
    );
};

export default connect(({global, settings, userInfo}: ConnectState) => ({
    collapsed: global.collapsed,
    settings,
    userInfo
}))(BasicLayout);

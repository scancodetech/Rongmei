// https://umijs.org/config/
import {defineConfig} from 'umi';
import defaultSettings from './defaultSettings';
import proxy from './proxy';

const {REACT_APP_ENV} = process.env;
export default defineConfig({
    hash: true,
    antd: {},
    base: '/',
    publicPath: '/',
    dva: {
        hmr: true,
    },
    history: {
        type: 'hash',
    },
    locale: {
        // default zh-CN
        default: 'zh-CN',
        // default true, when it is true, will use `navigator.language` overwrite default
        antd: true,
        baseNavigator: true,
    },
    dynamicImport: {
        loading: '@/components/PageLoading/index',
    },
    targets: {
        ie: 11,
    },
    // umi routes: https://umijs.org/docs/routing
    routes: [
        {
            path: '/',
            component: '../layouts/BasicLayout',
            authority: ['ROLE_USER'],
            routes: [
                {
                    path: '/',
                    redirect: '/charge'
                },
                {
                    name: '充值',
                    path: '/charge',
                    component: './charge',
                },
                {
                    name: '提现',
                    path: '/withdraw',
                    component: './withdraw',
                },
                {
                    name: '充值须知',
                    path: '/notice',
                    component: './notice',
                },
                {
                    component: './404',
                },
            ],
        },
        {
            component: './404',
        },
    ],
    // Theme for antd: https://ant.design/docs/react/customize-theme-cn
    theme: {
        // ...darkTheme,
        'primary-color': defaultSettings.primaryColor,
    },
    ignoreMomentLocale: true,
    proxy: proxy[REACT_APP_ENV || 'dev'],
    manifest: {
        basePath: '/',
    },
});

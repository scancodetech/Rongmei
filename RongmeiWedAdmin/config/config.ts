// https://umijs.org/config/
import { defineConfig } from 'umi';
import defaultSettings from './defaultSettings';
import proxy from './proxy';

const { REACT_APP_ENV } = process.env;
export default defineConfig({
  hash: true,
  antd: {},
  base: '/picadmin',
  publicPath: '/picadmin/',
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
      component: '../layouts/SecurityLayout',
      routes: [
        {
          path: '/',
          component: '../layouts/BasicLayout',
          authority: ['ROLE_USER'],
          routes: [
            {
              path: '/',
              redirect: '/welcome'
            },
            {
              path: '/account',
              routes: [
                {
                  name: '个人中心',
                  path: '/account/center',
                  component: './account/center',
                  routes: [
                    {
                      path: '/account/center',
                      redirect: '/account/center/home',
                    },
                    {
                      name: '首页',
                      path: '/account/center/home',
                      component: './account/center/home',
                    },
                    {
                      name: '订单管理',
                      path: '/account/center/order',
                      component: './account/center/order',
                    },
                    {
                      name: '素材管理',
                      path: '/account/center/commodity',
                      routes: [
                        {
                          name: '我的素材',
                          path: '/account/center/commodity/list',
                          component: './account/center/commodity/list',
                        },
                        {
                          name: '编辑素材',
                          path: '/account/center/commodity/edit/:id',
                          component: './account/center/commodity/upload'
                        },
                        {
                          name: '素材设置',
                          path: '/account/center/commodity/setting',
                          component: './account/center/commodity/setting',
                        },
                      ]
                    },
                    {
                      name: '竞价管理',
                      path: '/account/center/auction',
                      routes: [
                        {
                          name: '铸造',
                          path: '/account/center/auction/nft/:id',
                          component: './account/center/auction/nft',
                        },
                        {
                          name: '新增竞价',
                          path: '/account/center/auction/sale/:saleid',
                          component: './account/center/auction/sale',
                        },
                        {
                          name: '铸造列表',
                          path: '/account/center/auction/nft',
                          component: './account/center/auction/nftList',
                        },
                        {
                          name: '竞价列表',
                          path: '/account/center/auction/sale',
                          component: './account/center/auction/saleList',
                        },
                      ]
                    },
                    {
                      name: '盒蛋管理',
                      path: '/account/center/blindbox',
                      routes: [
                        {
                          name: '铸造A',
                          path: '/account/center/blindbox/nft/:id',
                          component: './account/center/blindbox/nft',
                        },
                        {
                          name: '铸造B',
                          path: '/account/center/blindbox/castb',
                          component: './account/center/blindbox/castb',
                        },
                        {
                          name: '铸造列表',
                          path: '/account/center/blindbox/nft',
                          component: './account/center/blindbox/nftList',
                        },
                        {
                          name: '新增盒蛋',
                          path: '/account/center/blindbox/egg/:saleid',
                          component: './account/center/blindbox/sale',
                        },
                        {
                          name: '盒蛋列表',
                          path: '/account/center/blindbox/egg',
                          component: './account/center/blindbox/saleList',
                        },
                        {
                          name: '活动列表',
                          path: '/account/center/blindbox/promotion',
                          component: './account/center/blindbox/promotion',
                        },

                      ]
                    },
                    {
                      name: '巨人车管理',
                      path: '/account/center/groupshopping',
                      routes: [
                        {
                          name: '我的巨人车',
                          path: '/account/center/groupshopping/list',
                          component: './account/center/groupshopping/list',
                        },
                        {
                          name: '编辑巨人车',
                          path: '/account/center/groupshopping/edit/:id',
                          component: './account/center/groupshopping/upload'
                        }
                      ]
                    },
                    {
                      name: '创作者学院',
                      path: '/account/center/school',
                      routes: [
                        {
                          name: '平台规则',
                          path: '/account/center/school/rule',
                          component: './account/center/school/rule',
                        },
                        {
                          name: '内容资产创作者如何经营',
                          path: '/account/center/school/manage',
                          component: './account/center/school/manage',
                        },
                        {
                          name: '如何变现',
                          path: '/account/center/school/realize',
                          component: './account/center/school/realize',
                        },
                      ]
                    },
                    {
                      name: '存证与维权',
                      path: '/account/center/storage',
                      routes: [
                        {
                          name: '存证中心',
                          path: '/account/center/storage/nft',
                          component: './account/center/storage/nft',
                        },
                        {
                          name: '维权中心',
                          path: '/account/center/storage/right',
                          component: './account/center/storage/right',
                        },
                      ]
                    },
                    {
                      name: '帮助中心',
                      path: '/account/center/help',
                      routes: [
                        {
                          name: '联系我们',
                          path: '/account/center/help/contact',
                          component: './account/center/help/contact',
                        },
                        {
                          name: '内容设置',
                          path: '/account/center/help/setting',
                          component: './account/center/help/setting',
                        },
                        {
                          name: '跨次元自治委员会',
                          path: '/account/center/help/autonomy',
                          component: './account/center/help/autonomy',
                        },
                      ]
                    },
                    {
                      name: '分区认证',
                      path: '/account/center/applyRoles/内容创作者/:type/:role',
                      component: './account/center/applyRoles'
                    },

                    {
                      path: '/account/center/protocol/:type',
                      component: './account/center/protocol',
                    },
                  ],
                },
              ],
            },
            {
              path: '/applyRoles/机构创作者/:type/:role',
              component: './account/center/organizationApplyRoles'
            },
            {
              path: '/protocol/:type',
              component: './protocol',
            },
            {
              path: '/welcome',
              component: './welcome',
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
    basePath: '/picadmin',
  },
});

import { Menu, Card, Col, Divider, Input, Row, Button, message } from 'antd';
import React, { Component } from 'react';
import { GridContent } from '@ant-design/pro-layout';
import { history, connect, Dispatch } from 'umi';
import { RouteChildrenProps } from 'react-router';
import { UserInfo, UserInfoModelState } from "@/models/userInfo";
import {
    BankOutlined,
    BuildOutlined, BulbOutlined,
    CarOutlined,
    HomeOutlined, LockOutlined,
    MoneyCollectOutlined,
    PhoneOutlined,
} from "@ant-design/icons/lib";
import styles from '@/pages/account/center/layout.less';
import Guide from '@/components/Guide/index'
// 用于获取用户分区审核信息
import { getMineCertification } from "@/services/apply";

const { SubMenu } = Menu;

interface CenterProps extends RouteChildrenProps {
    dispatch: Dispatch;
    userInfo: UserInfo;
}

interface CenterState {
    tabKey?: 'articles' | 'applications' | 'projects';
    userRelation: {
        fans: [],
        follows: []
    };
}

class Center extends Component<CenterProps,
    CenterState> {
    public input: Input | null | undefined = undefined;

    state = {
        userRelation: {
            fans: [],
            follows: [],
        },
        showGuide: 'none',
    }

    onMenuClick = (event: ClickParam) => {
        const { key } = event;

        if (key === 'logout') {
            const { dispatch } = this.props;

            if (dispatch) {
                dispatch({
                    type: 'user/logout',
                });
                dispatch({
                    type: 'userInfo/clear',
                });
                history.push(`/`);
                location.reload();
            }

            return;
        }

        history.push(`${key}`);
    };

    async componentDidMount(): Promise<void> {
        let token = localStorage.getItem('token');

        if (!token || token.length === 0) {
            history.push('/')
        }


        // 控制用户引导的开关条件
        const certificationRes1 = await getMineCertification('竞品类');
        const certificationRes2 = await getMineCertification('素材类');
        const certificationRes3 = await getMineCertification('盒蛋类');
        if (certificationRes1.isUserCertificationChecked || certificationRes2.isUserCertificationChecked || certificationRes3.isUserCertificationChecked) {
            this.setState({ showGuide: 'none' })
        }
        else {
            if (this.props.history.location.pathname === "/account/center/home") {
                this.setState({ showGuide: '' })
            }
        }
        console.log(certificationRes1)
    }

    render() {
        const { userInfo, children } = this.props;
        const dataLoading = !userInfo;
        return (
            <GridContent style={{ paddingTop: 30, paddingBottom: 30, overflowX: 'auto' }}>
                {/* 根据display显示引导框，对接时需要获取用户是否进行身份认证，没有进行身份认证则弹出引导框，display=''时显示，display='none'时隐藏 */}
                <Guide display={this.state.showGuide} />
                <Row gutter={24} style={{ width: '1280px', margin: 'auto' }}>
                    <Col span={6} style={{}}>
                        <Card className={styles.siderBar}
                            // style={{height: document.getele}}
                            bordered={false} loading={dataLoading}>
                            {!dataLoading && (
                                <div>
                                    <div style={{ textAlign: 'center' }}>
                                        <Button
                                            style={{ fontSize: '16px', width: '100%' }}
                                            type="primary"
                                            size="large"
                                            danger
                                            onClick={() => {
                                                history.push('/account/center/auction/nft/0')
                                            }}
                                        >
                                            铸造
                                        </Button>
                                    </div>

                                    <Divider />
                                    <Menu
                                        style={{ border: 'none' }}
                                        defaultSelectedKeys={[history.location.pathname]}
                                        mode="inline"
                                        onClick={this.onMenuClick}
                                    >
                                        <Menu.Item key="/account/center/home">
                                            <HomeOutlined />
                                            首页
                                        </Menu.Item>
                                        <SubMenu key="/account/center/commodity" icon={<BuildOutlined />} title="素材管理">
                                            <Menu.Item key="/account/center/commodity/list">
                                                我的素材
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/commodity/edit/0">
                                                上传素材
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/commodity/setting">
                                                素材设置
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/auction" icon={<MoneyCollectOutlined />}
                                            title="竞价管理">
                                            <Menu.Item key="/account/center/auction/nft/0">
                                                铸造
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/auction/nft">
                                                铸造列表
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/auction/sale/0">
                                                新增竞价
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/auction/sale">
                                                竞价列表
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/blindbox" icon={<BulbOutlined />}
                                            title="盒蛋管理">
                                            <Menu.Item key="/account/center/blindbox/nft/0">
                                                铸造A
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/blindbox/castb">
                                                铸造B
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/blindbox/nft">
                                                铸造列表
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/blindbox/egg/0">
                                                新增盒蛋
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/blindbox/egg">
                                                盒蛋列表
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/blindbox/promotion">
                                                活动列表
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/groupshopping" icon={<CarOutlined />} title="巨人车管理">
                                            <Menu.Item key="/account/center/groupshopping/list">
                                                我的巨人车
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/groupshopping/edit/0">
                                                上传巨人车
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/school" icon={<BankOutlined />} title="创作者学院">
                                            <Menu.Item key="/account/center/school/rule">
                                                平台规则
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/school/manage">
                                                内容资产创作者如何经营
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/school/realize">
                                                如何变现
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/storage" icon={<LockOutlined />} title="存证与维权">
                                            <Menu.Item key="/account/center/storage/nft">
                                                存证中心
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/storage/right">
                                                维权中心
                                            </Menu.Item>
                                        </SubMenu>
                                        <SubMenu key="/account/center/help" icon={<PhoneOutlined />} title="帮助中心">
                                            <Menu.Item key="/account/center/help/contact">
                                                联系我们
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/help/setting">
                                                内容规范
                                            </Menu.Item>
                                            <Menu.Item key="/account/center/help/autonomy">
                                                跨次元自治委员会
                                            </Menu.Item>
                                        </SubMenu>
                                    </Menu>
                                </div>
                            )}
                        </Card>
                    </Col>
                    <Col span={18} style={{ alignSelf: 'end' }}>
                        <Card className={styles.mainContent} bordered={false}>
                            {children}
                        </Card>
                    </Col>
                </Row>
            </GridContent>
        );
    }
}

export default connect(
    ({
        userInfo
    }: {
        userInfo: UserInfoModelState;
    }) => ({
        userInfo: userInfo.userInfo
    }),
)(Center);

import { LogoutOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Menu, Modal, Spin, Alert, Button, Form, Checkbox } from 'antd';
import { ClickParam } from 'antd/es/menu';
import React from 'react';
import { history, ConnectProps, connect } from 'umi';
import { ConnectState } from '@/models/connect';
import { UserInfoModelState } from '@/models/userInfo';
import { StateType } from "@/models/user";
import HeaderDropdown from '../HeaderDropdown';
import styles from './index.less';
import LoginFrom from '../Login';
import logo from '../../assets/logo.png';

const { Tab, Password, Mobile, Captcha, Submit } = LoginFrom;

const LoginMessage: React.FC<{
    content: string;
}> = ({ content }) => (
    <Alert
        style={{
            marginBottom: 24,
        }}
        message={content}
        type="error"
        showIcon
    />
);

export interface GlobalHeaderRightProps extends Partial<ConnectProps> {
    user?: StateType;
    userInfo?: UserInfoModelState;
}

class AvatarDropdown extends React.Component<GlobalHeaderRightProps> {
    state = {
        isAgree: false,
        phone: '',
        captcha: '',
        showLogin: false
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
            }
            history.push(`/`);
            location.reload();

            return;
        }

        history.push(`/account/${key}`);
    };

    onIsAgree = (value: any) => {
        this.setState({
            isAgree: value.target.checked
        })
    }

    onAvatarClick = () => {
        const { dispatch } = this.props;

        if (dispatch) {
            dispatch({
                type: 'user/changeLoginShow',
                payload: {
                    isShowLogin: true
                }
            });
        }
    }

    login = async () => {
        const { dispatch } = this.props;

        if (dispatch) {
            await dispatch({
                type: 'user/login',
                payload: {
                    phone: this.state.phone,
                    captcha: this.state.captcha
                }
            });
            await dispatch({
                type: 'userInfo/getUserInfo'
            });
            await dispatch({
                type: 'user/changeLoginShow',
                payload: {
                    isShowLogin: false
                }
            });
            history.push('/account/center')
        }
    }

    onLoginCancel = () => {
        const { dispatch } = this.props;

        if (dispatch) {
            dispatch({
                type: 'user/changeLoginShow',
                payload: {
                    isShowLogin: false
                }
            });
        }
    }

    render(): React.ReactNode {
        const { user, userInfo } = this.props;
        const menuHeaderDropdown = (
            <Menu className={styles.menu} selectedKeys={[]} onClick={this.onMenuClick}>
                <Menu.Item key="center">
                    <UserOutlined />
                    个人中心
                </Menu.Item>
                <Menu.Divider />
                <Menu.Item key="logout">
                    <LogoutOutlined />
                    退出登录
                </Menu.Item>
            </Menu>
        );
        return userInfo ? (
            !userInfo.userInfo.id || userInfo.userInfo.id === 0 ?
                (<span className={`${styles.action} ${styles.account}`}>
                    <div onClick={() => {
                        this.onAvatarClick()
                    }}>
                        <Avatar size="small" className={styles.avatar}
                            src="https://rongmei-common.oss-cn-beijing.aliyuncs.com/user-blue.svg"
                            alt="avatar"
                        />
                        <span>登录</span>
                    </div>

                    <Modal
                        visible={user && user.isShowLogin}
                        onCancel={() => {
                            this.onLoginCancel()
                        }}
                        footer={null}>
                        {user && user.status === 'error' && (
                            <LoginMessage content="验证码错误" />
                        )}
                        <div className={styles.top}>
                            <div className={styles.header}>
                                <img alt="logo" className={styles.logo} src={logo} />
                                <span className={styles.title}>跨次元平台</span>
                            </div>
                            <div className={styles.desc}>国际领先的内容数字资产交易平台</div>
                        </div>
                        <Form>
                            <Mobile
                                name="phone"
                                placeholder="手机号或邮箱: "
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入手机号或邮箱！',
                                    }
                                ]}
                                onChange={(e) => {
                                    this.setState({
                                        phone: e.target.value
                                    })
                                }}
                            />
                            <Captcha
                                name="captcha"
                                placeholder="验证码"
                                countDown={120}
                                getCaptchaButtonText=""
                                getCaptchaSecondText="秒"
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入验证码！',
                                    },
                                ]}
                                onChange={(e) => {
                                    this.setState({
                                        captcha: e.target.value
                                    })
                                }}
                            />
                            {/* <Password
              name="password"
              placeholder="密码："
              rules={[
                  {
                      required: true,
                      message: '请输入密码！',
                  },
              ]}
              onChange={(e) => {
                  this.setState({
                      password: e.target.value
                  })
              }}
          /> */}
                        </Form>
                        <div className={styles.desc} style={{ marginTop: '0' }}>
                            <Checkbox style={{ marginRight: '10px' }} defaultChecked={this.state.isAgree} onChange={this.onIsAgree.bind(this)}></Checkbox>
                            点击登录即代表您同意<a href={`/picadmin/#/picadmin/protocol/creator`} target='_blank'>《跨次元创作者服务协议》</a></div>
                        <div style={{ textAlign: 'center' }}>
                            <Button style={{ width: '50%' }} disabled={!this.state.isAgree} type="primary" shape="round" size="large" onClick={() => {
                                this.login()
                            }}>登录</Button>
                        </div>
                    </Modal>
                    <span className={styles.name}>{userInfo.userInfo.nickname}</span>
                </span>) : (
                    <HeaderDropdown overlay={menuHeaderDropdown}>
                        <span className={`${styles.action} ${styles.account}`}>
                            <Avatar size="small" className={styles.avatar}
                                src={userInfo.userInfo.avatarUrl ? userInfo.userInfo.avatarUrl : "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png"}
                                alt="avatar" />
                            <span className={styles.name}>{userInfo.userInfo.nickname}</span>
                        </span>
                    </HeaderDropdown>
                )
        ) : (
            <span className={`${styles.action} ${styles.account}`}>
                <Spin
                    size="small"
                    style={{
                        marginLeft: 8,
                        marginRight: 8,
                    }}
                />
            </span>
        );
    }
}

export default connect(({ user, userInfo }: ConnectState) => ({
    user,
    userInfo,
}))(AvatarDropdown);

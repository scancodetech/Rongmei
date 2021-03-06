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

    componentDidMount() {
        const { dispatch } = this.props;

        if (dispatch) {
            dispatch({
                type: 'userInfo/getUserInfo',
            });
        }
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

        history.push(`/${key}`);
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
            history.push('/')
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
                <Menu.Item key="logout">
                    <LogoutOutlined />
                    ????????????
                </Menu.Item>
            </Menu>
        );
        return userInfo ? (
            !userInfo.userInfo.id || userInfo.userInfo.id === 0 ?
                (<span className={`${styles.action} ${styles.account}`}>
                    <Avatar size="small" className={styles.avatar}
                        src="https://rongmei-common.oss-cn-beijing.aliyuncs.com/user-blue.svg"
                        alt="avatar"
                        onClick={() => {
                            this.onAvatarClick()
                        }}
                    />
                    <Modal
                        visible={user && user.isShowLogin}
                        onCancel={() => {
                            this.onLoginCancel()
                        }}
                        footer={null}>
                        {user && user.status === 'error' && (
                            <LoginMessage content="???????????????" />
                        )}
                        <div className={styles.top}>
                            <div className={styles.header}>
                                <img alt="logo" className={styles.logo} src={logo} />
                                <span className={styles.title}>???????????????</span>
                            </div>
                            <div className={styles.desc}>???????????????????????????</div>
                        </div>
                        <Form>
                            <Mobile
                                name="phone"
                                placeholder="?????????: "
                                rules={[
                                    {
                                        required: true,
                                        message: '?????????????????????',
                                    },
                                    {
                                        pattern: /^1\d{10}$/,
                                        message: '????????????????????????',
                                    },
                                ]}
                                onChange={(e) => {
                                    this.setState({
                                        phone: e.target.value
                                    })
                                }}
                            />
                            <Captcha
                                name="captcha"
                                placeholder="?????????"
                                countDown={120}
                                getCaptchaButtonText=""
                                getCaptchaSecondText="???"
                                rules={[
                                    {
                                        required: true,
                                        message: '?????????????????????',
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
              placeholder="?????????"
              rules={[
                  {
                      required: true,
                      message: '??????????????????',
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
                            ??????????????????????????????<a target="_black" href="https://www.kancloud.cn/minshiyu/unique-user-protocol/content">???????????????????????????</a></div>
                        <div style={{ textAlign: 'center' }}>
                            <Button style={{ width: '50%' }} disabled={!this.state.isAgree} type="primary" shape="round" size="large" onClick={() => {
                                this.login()
                            }}>??????</Button>
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

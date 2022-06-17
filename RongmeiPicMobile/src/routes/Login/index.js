import React from 'react'
import {Button, Flex, InputItem, Toast, WingBlank, WhiteSpace} from 'antd-mobile'
import './style.css'
import {withRouter} from 'react-router-dom'
import 'animate.css'
import {api} from '../../services/api/ApiProvider'

@withRouter
class Login extends React.Component {
    accountService = api.accountService;
    distributionService = api.distributionService;

    state = {
        loading: false,
        value: '',
        hasError: true,
        code: '',
        emailLogin: false,
        shareCode: '',
        // 获取验证码间隔
        timeOut: 120,


    }
    onErrorClick = () => {
        if (this.state.hasError) {
            Toast.info('电话号码格式不正确');
        }
    }
    onChange = (value) => {
        if (value.replace(new RegExp("\\s", 'g'), '').length < 11) {
            this.setState({
                hasError: true,
            });
        } else {
            if (this.state.timeOut === 120) {
                this.setState({
                    hasError: false,
                });
            }
        }
        this.setState({
            value,
        });
    }

    onChange2 = (value) => {
        if (value.length <= 6) {
            this.setState({
                code: value
            });
        }

    }
    mailOnChange = (value) => {
        // var checkEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        var checkEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        // console.log(checkEmail.test(value))
        if (this.state.timeOut === 120) {
            this.setState({
                hasError: !checkEmail.test(value)
            });
        }
        this.setState({
            value,
        })
    }
    shareOnChange = (value) => {
        const checkShareCode = new RegExp("^[A-Za-z0-9]+", 'g');
        let sourceStr = value.replace(new RegExp("-", 'g'), '');
        console.log(sourceStr)
        if (sourceStr.length === 0 || sourceStr.match(checkShareCode)) {
            let newStr = "";
            for (let i = 0; i < sourceStr.length; i++) {
                newStr = newStr.concat(sourceStr.substr(i, 1));
                if ((i === 2 && sourceStr.length > 3) || (i === 6 && sourceStr.length > 7)) {
                    newStr = newStr.concat("-")
                }
            }
            this.setState({
                shareCode: newStr
            }, () => console.log(this.state.shareCode))
        }
    }

    componentDidMount() {
        const isLogin = localStorage.getItem('token') && localStorage.getItem('token').length > 0;
        if (isLogin) {
            this.props.history.push('/home')
        }
        if (this.props.location.search.length > 0) {
            this.setState({
                shareCode: this.props.location.search.split('code=')[1]
            })
        }

        // 挂载时获取验证码间隔时间
        if (localStorage.getItem('timeOut') != null) {
            this.setState({
                timeOut: +localStorage.getItem('timeOut'),
            }, () => {
                console.log(this.state.timeOut)
            })
        } else {
            this.setState({
                timeOut: 0,
            }, () => {
                console.log(this.state.timeOut)
            })
        }

        if (this.state.timeOut > 0) {
            this.awaitTimeOut()
        }
        // this.props.history.push('/sale/0')
    }

    awaitTimeOut() {
        let timer = setInterval(() => {
            this.setState({
                timeOut: this.state.timeOut - 1,
                hasError: true,
            }, () => {
                localStorage.setItem('timeOut', this.state.timeOut);
                console.log(this.state.timeOut);
            })
            if (this.state.timeOut <= 0) {
                clearInterval(timer)
                this.state.emailLogin ? this.onChange(this.state.value) : this.mailOnChange(this.state.value)
                this.setState({
                    timeOut: 120,
                    // hasError: false,
                }, () => {
                    localStorage.setItem('timeOut', 0);
                })
            }
        }, 1000)

    }

    async getCaptcha() {
        try {
            const phone = this.state.value.replace(new RegExp("\\s+", 'g'), '');
            await this.accountService.sendCaptcha({
                phone: phone
            })
            Toast.success('验证码发送成功', 1);
            this.awaitTimeOut();
        } catch (e) {
            Toast.info('验证码发送失败，请重试', 1);
        }
    }

    async login() {
        try {
            const username = this.state.value.replace(new RegExp("\\s+", 'g'), '');
            try {
                await this.accountService.getUserEntity(username);
            } catch (e) {
                if (this.state.shareCode.length < 10) {
                    Toast.info("请先输入邀请码");
                    return;
                }
                const sharecode = this.state.shareCode.replace(new RegExp("-", 'g'), '')
                console.log(sharecode)
                await this.distributionService.distribution(sharecode, username);
            }
            const res = await this.accountService.login({
                phone: username,
                captcha: this.state.code
            })
            localStorage.setItem('token', res.token);
            Toast.success('登录成功', 1);
            this.props.history.push('/home')
        } catch (e) {
            Toast.info('验证码错误，请重试', 1);
        }
    }

    render() {
        return (
            <div className='login-page'>
                <div style={{backgroundColor: 'rgba(0,0,0,0.6)', height: '100VH',}}>
                    <Flex justify='center' style={{paddingTop: '15%'}}>
                        <h1 style={{color: 'white',}}>登录</h1>
                    </Flex>

                    {/* <div style={{ marginLeft: '30px', marginTop: '50px' }}>
          <h1>登录</h1>
          <p><span style={{ color: "#8D8C8F" }}>请阅读</span><a style={{ color: '#6387f6' }}
            href={"http://www.dimension.hub"}> 跨次元用户协议</a></p>
              </div> */}
                    <div>
                        <WingBlank size="lg" style={{marginTop: '30px', margin: '30px'}}>
                            <Flex justify='center'>
                                <Flex.Item style={{
                                    flex: 2,
                                    fontSize: 14,
                                    color: 'white',
                                    paddingRight: 20,
                                    fontWeight: 'bold'
                                }}>账号</Flex.Item>
                                <Flex.Item style={{flex: 8}}>
                                    {
                                        this.state.emailLogin === false &&
                                        <InputItem
                                            type="phone"
                                            placeholder="请输入手机号"
                                            error={this.state.hasError}
                                            onErrorClick={this.onErrorClick}
                                            onChange={this.onChange}
                                            value={this.state.value}
                                            // ref={el => this.inputRef = el}
                                            // width={300}
                                        >
                                            {/* <div style={{ textAlign: 'center', width: '70%' }}>+86</div> */}
                                        </InputItem>
                                    }
                                    {
                                        this.state.emailLogin === true &&
                                        <InputItem
                                            id='shareCode'
                                            type="mail"
                                            placeholder="请输入邮箱号"
                                            error={this.state.hasError}
                                            onErrorClick={this.onErrorClick}
                                            onChange={this.mailOnChange}
                                            value={this.state.value}
                                            // ref={el => this.inputRef = el}
                                            // width={300}
                                        >
                                            {/* <div style={{ textAlign: 'center', width: '70%' }}>+86</div> */}
                                        </InputItem>
                                    }

                                </Flex.Item>
                            </Flex>

                            {/* 上下留白 */}
                            <WhiteSpace size="xl"/>
                            <WhiteSpace size="md"/>

                            <Flex justify='center'>
                                <Flex.Item style={{
                                    flex: 2,
                                    fontSize: 14,
                                    color: 'white',
                                    paddingRight: 20,
                                    fontWeight: 'bold'
                                }}>验证码</Flex.Item>
                                <Flex.Item style={{flex: 5}}>
                                    <InputItem
                                        type="digit"
                                        // type='text'
                                        value={this.state.code}
                                        placeholder="验证码"
                                        maxLength={6}
                                        onChange={(value) => this.onChange2(value)}
                                    />
                                </Flex.Item>
                                <Flex.Item style={{alignSelf: 'center', flex: 3}}>
                                    <Button type="warning"
                                            style={{height: '35px', lineHeight: '35px', fontSize: '12px',}}
                                            disabled={this.state.hasError} onClick={() => {
                                        this.getCaptcha()
                                    }}
                                            focused>{this.state.timeOut === 120 ? `获取验证码` : `${this.state.timeOut}S后可获取`}</Button>
                                </Flex.Item>
                            </Flex>
                            {/* 留白 */}
                            <WhiteSpace size="xl"/>
                            {/* <WhiteSpace size="xl" /> */}

                            {/* 邮箱手机号切换登录 */}
                            <Flex justify='end'>
                                {
                                    this.state.emailLogin === true &&
                                    <div onClick={() => {
                                        this.setState({emailLogin: false, value: '', hasError: true})
                                    }}>切换手机号登录</div>
                                }
                                {
                                    this.state.emailLogin === false &&
                                    <div onClick={() => {
                                        this.setState({emailLogin: true, value: '',})
                                    }}>切换邮箱登录</div>
                                }
                            </Flex>

                            <WhiteSpace size="xl"/>
                            <WhiteSpace size="xl"/>
                            <div>
                                <Flex justify='center' direction='column' align='center'>
                                    <div style={{fontSize: 15, fontWeight: 600}}>邀请码</div>
                                    <WhiteSpace size="lg"/>

                                    <Flex justify='center' style={{width: '100%'}}>
                                        <div className='shareCodeInputItem'>
                                            <InputItem
                                                type='text'
                                                maxLength={12}
                                                value={this.state.shareCode}
                                                onChange={(value) => this.shareOnChange(value)}
                                            />
                                        </div>
                                    </Flex>

                                </Flex>
                                <WhiteSpace size="xl"/>
                                <WhiteSpace size="xl"/>
                            </div>

                            <Flex justify='center'>
                                <div style={{alignSelf: 'center'}}>
                                    <Button type="warning" style={{
                                        width: '270px',
                                        height: 35,
                                        lineHeight: '35px',
                                        fontSize: 18,
                                        fontWeight: 500
                                    }}
                                            onClick={() => {
                                                this.login()
                                            }} focused>登录</Button>
                                </div>
                            </Flex>
                        </WingBlank>
                    </div>
                    <div style={{
                        width: '100%',
                        position: 'absolute',
                        textAlign: 'center',
                        bottom: '20px',
                        color: "#8D8C8F",
                        fontWeight: 600
                    }}>跨次元
                    </div>
                </div>
            </div>
        )
    }
}

export default Login

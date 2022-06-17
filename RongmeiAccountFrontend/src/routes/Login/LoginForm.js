import React from 'react'
import {randomNum, calculateWidth} from '../../utils/utils'
import {Form, Input, Row, Col, message} from 'antd'
import {api} from "../../services/api/ApiProvider";
import {withRouter} from "react-router-dom";

@withRouter
class LoginForm extends React.Component {
    formRef = React.createRef();
    state = {
        focusItem: -1,   //保存当前聚焦的input
        code: ''         //验证码
    }

    componentDidMount() {
        this.createCode()
    }

    /**
     * 生成验证码
     */
    createCode = () => {
        const ctx = this.canvas.getContext('2d')
        const chars = [1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        let code = ''
        ctx.clearRect(0, 0, 80, 39)
        for (let i = 0; i < 4; i++) {
            const char = chars[randomNum(0, 57)]
            code += char
            ctx.font = randomNum(20, 25) + 'px SimHei'  //设置字体随机大小
            ctx.fillStyle = '#D3D7F7'
            ctx.textBaseline = 'middle'
            ctx.shadowOffsetX = randomNum(-3, 3)
            ctx.shadowOffsetY = randomNum(-3, 3)
            ctx.shadowBlur = randomNum(-3, 3)
            ctx.shadowColor = 'rgba(0, 0, 0, 0.3)'
            let x = 80 / 5 * (i + 1)
            let y = 39 / 2
            let deg = randomNum(-25, 25)
            /**设置旋转角度和坐标原点**/
            ctx.translate(x, y)
            ctx.rotate(deg * Math.PI / 180)
            ctx.fillText(char, 0, 0)
            /**恢复旋转角度和坐标原点**/
            ctx.rotate(-deg * Math.PI / 180)
            ctx.translate(-x, -y)
        }
        this.setState({
            code
        })
    }
    loginSubmit = async (values) => {
        this.setState({
            focusItem: -1
        })
        console.log(this.state)
        console.log(values)
        if (this.state.code.toUpperCase() !== values.verification.toUpperCase()) {
            this.formRef.current.setFields({
                verification: {
                    value: values.verification,
                    errors: [new Error('验证码错误')]
                }
            })
            return
        }
        try {
            let res = await api.accountService.loginWithPassword(
                {
                    username: values.phone,
                    password: values.password
                }
            )
            console.log(res);
            localStorage.setItem('token', res.token);
            message.success('登录成功')
            this.props.history.push('/home')
        } catch (e) {
            console.log(e)
            localStorage.setItem('token', '');
            message.error('用户名密码错误')
        }
    }
    register = () => {
        this.props.switchShowBox('register')
        setTimeout(() => this.props.form.resetFields(), 500)
    }

    render() {
        const {focusItem, code} = this.state
        return (
            <div className={this.props.className}>
                <h3 className='title'>用户登录</h3>
                <Form onFinish={this.loginSubmit} ref={this.formRef}>
                    <Form.Item name="phone" rules={[{required: true, message: '请输入手机号'}]}>
                        <Input
                            onFocus={() => this.setState({focusItem: 0})}
                            onBlur={() => this.setState({focusItem: -1})}
                            maxLength={16}
                            placeholder='手机号'
                            addonBefore={<span className='iconfont icon-User'
                                               style={focusItem === 0 ? styles.focus : {}}/>}/>
                    </Form.Item>
                    <Form.Item name="password" rules={[{required: true, message: '请输入密码'}]}>
                        <Input
                            onFocus={() => this.setState({focusItem: 1})}
                            onBlur={() => this.setState({focusItem: -1})}
                            type='password'
                            maxLength={16}
                            placeholder='密码'
                            addonBefore={<span className='iconfont icon-suo1'
                                               style={focusItem === 1 ? styles.focus : {}}/>}/>
                    </Form.Item>
                    <Form.Item
                        name="verification"
                        rules={
                            [
                                {required: true, message: '请输入验证码'},
                                {
                                    validator: (rule, value, callback) => {
                                        if (value.length >= 4 && code.toUpperCase() !== value.toUpperCase()) {
                                            callback('验证码错误')
                                        }
                                        callback()
                                    }
                                }
                            ]
                        }
                    >
                        <Row>
                            <Col span={15}>
                                <Input
                                    onFocus={() => this.setState({focusItem: 2})}
                                    onBlur={() => this.setState({focusItem: -1})}
                                    maxLength={4}
                                    placeholder='验证码'
                                    addonBefore={<span className='iconfont icon-securityCode-b'
                                                       style={focusItem === 2 ? styles.focus : {}}/>}/>
                            </Col>
                            <Col span={9}>
                                <canvas onClick={this.createCode} width="80" height='39'
                                        ref={el => this.canvas = el}/>
                            </Col>
                        </Row>
                    </Form.Item>
                    <div className='bottom'>
                        <input className='loginBtn' type="submit" value='登录'/>
                    </div>
                </Form>
                <div className='footer'>
                    <div>欢迎登陆</div>
                </div>
            </div>
        )
    }
}

const styles = {
    focus: {
        width: '20px',
        opacity: 1
    },
}

export default LoginForm

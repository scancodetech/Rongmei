import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import {List, WhiteSpace, Modal, Flex, InputItem, WingBlank, Button, ImagePicker, Toast} from 'antd-mobile'
import {api} from "../../../services/api/ApiProvider";

class Password extends React.Component {
    state = {
        password: '',
        newPassword: '',
        confirmation: '',

    }
    submit = async () => {
        const state = this.state
        if (state.confirmation.length < 6 || state.newPassword.length < 6) {
            Toast.fail('密码长度最小为6位')
        } else if (state.newPassword !== state.confirmation) {
            Toast.fail('确认密码与新密码不相同')
        } else {
            try {
                await api.accountService.updatePassword(this.state.newPassword);
                Toast.success("更新成功");
                this.props.history.goBack();
            } catch (e) {
                Toast.fail("更新失败");
            }
        }
    }

    render() {
        return (
            <div className='passwordPage'>
                <Header title='修改密码' theme={{mode: 'dark'}}/>
                {/*<WhiteSpace size='lg' />*/}
                {/*<div className='BannerTitle'>*/}
                {/*    <WingBlank size='lg'>*/}
                {/*        <Flex justify='start'>*/}
                {/*            <div>原密码：</div>*/}
                {/*            <InputItem*/}
                {/*                type='password'*/}
                {/*                onChange={(value) => { this.setState({ password: value }) }}*/}
                {/*            />*/}
                {/*        </Flex>*/}
                {/*    </WingBlank>*/}
                {/*</div>*/}
                <WhiteSpace size='lg'/>
                <div className='BannerTitle'>
                    <WingBlank size='lg'>
                        <Flex justify='start'>
                            <div>新密码：</div>
                            <InputItem
                                type='password'
                                onChange={(value) => {
                                    this.setState({newPassword: value})
                                }}
                            />
                        </Flex>
                    </WingBlank>
                </div>
                <WhiteSpace size='lg'/>
                <div className='BannerTitle'>
                    <WingBlank size='lg'>
                        <Flex justify='start'>
                            <div>原密码：</div>
                            <InputItem
                                type='password'
                                onChange={(value) => {
                                    this.setState({confirmation: value})
                                }}
                            />
                        </Flex>
                    </WingBlank>
                </div>

                <WhiteSpace size='xl'/>
                <WhiteSpace size='xl'/>

                <div className='passwordBtnChange'>
                    <Flex justify='center'>
                        <Button onClick={() => this.submit()}>确认</Button>
                    </Flex>
                </div>
            </div>
        )
    }
}

export default Password

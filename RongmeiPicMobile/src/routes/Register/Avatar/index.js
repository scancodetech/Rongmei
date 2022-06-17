import React from 'react'
import './style.css'
import { ActivityIndicator, Button, Flex, ImagePicker, InputItem, Toast } from 'antd-mobile'
import { api } from "../../../services/api/ApiProvider";

export default class Avatar extends React.Component {
    state = {
        files: [],
        nickname: '',
        isChange: false,
        animating: false,
    }

    async componentDidMount() {
        const res = await api.accountService.getUserInfo();
        // let files = [{
        //     url: res.avatarUrl,
        //     id: '1'
        // }]
        this.setState({
            ...res,
            // files,
            avatarUrl: res.avatarUrl,
            nickname: res.nickname
        })
    }

    async submit() {
        await this.setState({ animating: true })
        try {
            let avatarUrl = this.state.avatarUrl;
            if (this.state.isChange && this.state.files.length > 0) {
                let res = await api.uploadService.upload(this.state.files[0].file);
                avatarUrl = res.url;
            }
            await api.accountService.saveUserInfo({
                ...this.state,
                avatarUrl
            });
            this.setState({ animating: false })

            this.props.history.push('/home')
        } catch (e) {
            Toast.fail("信息提交失败，请检查网络后重试");
        }
    }

    onChange = (files, type, index) => {
        console.log(files, type, index);
        this.setState({
            files,
            isChange: true
        });
    }

    render() {
        return (
            <div className='RegisterAvatarPage'>
                <Flex justify='center' direction='column' align='center' style={{ width: '100%' }}>
                    <div style={{ color: 'white', fontSize: 20, fontWeight: 600, padding: '50px 0 20px 0' }}>让大家更好的认识你
                    </div>
                    <div
                        style={{ color: 'white', fontSize: 15, fontWeight: 600, paddingBottom: 30, paddingTop: 20 }}>上传头像
                    </div>
                    <div style={{ width: 80, height: 80, position: 'relative' }} className='ImagePickerChange'>
                        <ImagePicker
                            files={this.state.files}
                            selectable={this.state.files.length < 1}
                            length={1}
                            onChange={this.onChange}
                        />
                        <div className='addImg' />
                    </div>
                    <div style={{ paddingTop: 40 }}>
                        <div style={{ fontSize: 16, color: 'white', fontWeight: 600 }}>你的昵称</div>
                        <div className='inputItemChange'>
                            <InputItem
                                type='text'
                                placeholder='输入你的圈名'
                                maxLength={8}
                                value={this.state.nickname}
                                onChange={(value) => this.setState({ nickname: value }, () => console.log(this.state.nickname))}
                            />
                        </div>
                        <div style={{ fontSize: 12, color: '#666666', fontWeight: 600 }}>2-8个字符，不含特殊字符</div>
                    </div>
                    <div className='submit'>
                        <Button disabled={this.state.files.length < 1 || this.state.nickname.length < 2}
                            onClick={() => this.submit()}>完成</Button>
                    </div>

                </Flex>
                <ActivityIndicator
                    toast
                    text='上传中...'
                    animating={this.state.animating}
                />
            </div>
        )
    }
}

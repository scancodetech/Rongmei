import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'
import {List, WhiteSpace, Modal, Flex, InputItem, WingBlank, Button, ImagePicker, Toast} from 'antd-mobile'
import {api} from "../../../services/api/ApiProvider";
import lrz from 'lrz';
import {uploadFileToCos} from "../../../utils/utils";

class Info extends React.Component {
    state = {
        nickname: '',
        intro: '',
        gender: '男',
        avatarUrl: 'https://picsum.photos/200/200',
    }

    async componentDidMount() {
        const res = await api.accountService.getUserInfo();
        if (res.id && res.id !== 0) {
            this.setState({
                ...res
            })
        }
    }

    onChange = async (files, type, index) => {
        const file = files[files.length - 1].file;
        if (type === 'add') {
            const rst = await lrz(file);
            const res = await uploadFileToCos(file.name, rst.file);
            if (res.statusCode === 200) {
                this.setState({
                    avatarUrl: "https://" + res.Location
                });
            }
        }
    };

    submit = async () => {
        try {
            await api.accountService.saveUserInfo({
                ...this.state
            })
            Toast.success("上传成功")
            this.props.history.goBack();
        } catch (e) {
            Toast.fail("上传失败")
        }
    }

    render() {
        return (
            <div className='infoPage'>
                <Header title='修改信息' theme={{mode: 'dark'}}/>
                <div className='InfoAvatarBanner'>
                    <Flex justify='center' direction='column'>
                        {/* <img alt='' src={this.state.avatar[0].url} style={{ objectFit: "contain", width: 100, height: 100, borderRadius: '50%', marginTop: 20 }} /> */}
                        <div className='InfoAvatarImagePickerChange'>
                            <ImagePicker
                                files={[{url: this.state.avatarUrl}]}
                                length={1}
                                selectable={true}
                                disableDelete={true}
                                onChange={this.onChange}
                                onImageClick={() => {
                                    console.log(this)
                                }}
                            />
                        </div>
                        <div style={{color: 'white', padding: "15px 0", fontWeight: 600, fontSize: 15}}>修改头像</div>
                    </Flex>
                </div>

                <div className='InfoBanner'>
                    <Flex justify='center' direction='column'>
                        <div className='BannerTitle'>
                            <WingBlank size='lg'>
                                <Flex justify='start'>
                                    <div>昵称：</div>
                                    <InputItem
                                        value={this.state.nickname}
                                        onChange={(value) => {
                                            this.setState({nickname: value}, () => {
                                                console.log(this.state.nickname)
                                            })
                                        }}
                                    />
                                </Flex>
                            </WingBlank>
                        </div>
                        <WhiteSpace size='md'/>
                        <div className='BannerTitle'>
                            <WingBlank size='lg'>
                                <Flex justify='start'>
                                    <div>性别：</div>
                                    <Flex>
                                        <Flex>
                                            <div style={{padding: '0 10px'}}>男</div>
                                            <div
                                                className={['BannerTitleBtn', this.state.gender === '男' ? 'BannerTitleBtn-active' : ''].join(' ')}
                                                onClick={() => {
                                                    this.setState({gender: '男'})
                                                }}>
                                                <Flex justify='center' style={{height: 30}}>
                                                    <div className='BannerTitleIconMale'/>
                                                </Flex>
                                            </div>
                                        </Flex>
                                        <Flex>
                                            <div style={{padding: '0 10px'}}>女</div>
                                            <div
                                                className={['BannerTitleBtn', this.state.gender === '女' ? 'BannerTitleBtn-active' : ''].join(' ')}
                                                onClick={() => {
                                                    this.setState({gender: '女'})
                                                }}>
                                                <Flex justify='center' style={{height: 30}}>
                                                    <div className='BannerTitleIconFemale'/>
                                                </Flex>
                                            </div>
                                        </Flex>
                                    </Flex>

                                </Flex>
                            </WingBlank>
                        </div>
                        <WhiteSpace size='md'/>
                        <div className='BannerTitle'>
                            <WingBlank size='lg'>
                                <Flex justify='start'>
                                    <div>个人介绍：</div>
                                    <InputItem
                                        value={this.state.intro}
                                        placeholder='让大家认识你吧~'
                                        onChange={(value) => {
                                            this.setState({intro: value}, () => {
                                                {
                                                    console.log(this.state.intro)
                                                }
                                            })
                                        }}
                                    />
                                </Flex>
                            </WingBlank>
                        </div>
                    </Flex>
                </div>
                <WhiteSpace size='xl'/>
                <div className='InfoBtnChange'>
                    <Flex justify='center'>
                        <Button onClick={this.submit}>保存</Button>
                    </Flex>
                </div>
            </div>
        )
    }
}

export default Info

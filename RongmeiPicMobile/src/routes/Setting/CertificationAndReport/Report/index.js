import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import { ActionSheet, Toast, List, WhiteSpace, Modal, Flex, InputItem, WingBlank, Button, ImagePicker, ListView } from 'antd-mobile'
import { render } from 'nprogress'
import dropDown from '../../../../assets/drop-down.png';

export default class CARReport extends React.Component {
    state = {
        // 自定义的举报，没有指定举报设子的ID
        reason: '',
        description: '',

        isReasonShow: false,
        files: []

    }
    showSelectReason() {
        const BUTTONS = ['偷孩子', '作品抄袭', '作品含明显推广', '色情低俗', '其他', '取消'];
        ActionSheet.showActionSheetWithOptions({
            options: BUTTONS,
            destructiveButtonIndex: BUTTONS.length - 1,
            message: '举报原因',
            maskClosable: true,
        },
            async (buttonIndex) => {
                if (buttonIndex < BUTTONS.length - 1) {
                    this.setState({
                        reason: BUTTONS[buttonIndex]
                    })
                }
            });
    }
    submitReport = async () => {
        let imageUrls = [];
        for (const file of this.state.files) {
            const res = await this.uploadService.upload(file.file);
            console.log(res)
            imageUrls = imageUrls.concat(res.url)
        }
        const res = await this.reportService.submitReport(
            this.state.reason, this.state.description, imageUrls, this.state.id
        )
        if (res.infoCode === 10000) {
            Toast.success("举报成功，请耐心等待");
            this.props.history.goBack();
        } else {
            Toast.fail("举报失败，请重试");
        }
    }

    onImageChange = (files, type, index) => {
        let filterFiles = [];
        files.forEach((file) => {
            if (file.file.size <= 1024 * 1024) {
                filterFiles = filterFiles.concat(file)
            }
        })
        this.setState({
            files: filterFiles,
        });
    }
    render() {
        return (
            <div className='CARReportPage'>
                <Header title='维权' />
                <WingBlank size='lg'>
                    <div style={{ height: '60px' }}>
                        <span style={{
                            float: 'left',
                            color: 'white',
                            fontSize: '18px',
                            marginLeft: '10px',
                            marginTop: '20px'
                        }}>举报原因</span>
                        <span style={{
                            float: 'left',
                            color: 'white',
                            fontSize: '14px',
                            marginTop: '22px'
                        }}>（必填）</span>
                        <span
                            onClick={() => {
                                this.showSelectReason()
                            }}
                            style={{
                                float: 'right',
                                color: 'white',
                                fontSize: '16px',
                                marginRight: '10px',
                                marginTop: '20px'
                            }}>
                            {this.state.reason.length === 0 ? "点击选择" : this.state.reason}
                            <img src={dropDown}
                                style={{ marginLeft: '4px', paddingTop: '4px', width: '16px', height: '16px' }} />
                        </span>
                    </div>
                    <div style={{
                        borderBottom: 'solid 0.01px',
                        borderBottomColor: '#222'
                    }}>
                    </div>
                    <div style={{ height: '100px' }}>
                        <div style={{
                            float: 'left',
                            color: 'white',
                            fontSize: '18px',
                            marginLeft: '10px',
                            marginTop: '20px'
                        }}>描述详情
                        </div>
                        <div style={{
                            width: '100%',
                            float: 'right',
                            marginRight: '10px',
                            marginTop: '20px'
                        }}>
                            <textarea style={{
                                marginLeft: '20px',
                                float: 'left',
                                background: '#111',
                                border: 'none',
                                color: '#aaa',
                                fontSize: '14px',
                                width: window.innerWidth - 60
                            }}
                                onChange={(e) => {
                                    this.setState({
                                        description: e.target.value
                                    })
                                }}
                                value={this.state.description}
                                placeholder={"填写证据相关文字描述"} />
                        </div>
                    </div>
                    <div style={{
                        borderBottom: 'solid 0.01px',
                        borderBottomColor: '#222'
                    }}>
                    </div>
                    <div style={{ height: '60px' }}>
                        <span style={{
                            float: 'left',
                            color: 'white',
                            fontSize: '18px',
                            marginLeft: '10px',
                            marginTop: '20px'
                        }}>凭证照片</span>
                        <span style={{
                            float: 'left',
                            color: 'white',
                            fontSize: '14px',
                            marginTop: '22px'
                        }}>（必填）</span>
                        <span style={{
                            float: 'right',
                            color: '#AAA',
                            fontSize: '12px',
                            marginRight: '10px',
                            marginTop: '22px'
                        }}>
                            单张不超过1MB({this.state.files.length}/8)
                        </span>
                    </div>
                    <div>
                        <ImagePicker
                            style={{
                                width: '100%',
                                float: 'left',
                                marginBottom: '40px'
                            }}
                            files={this.state.files}
                            onChange={this.onImageChange}
                            selectable={this.state.files.length < 8}
                            multiple={true}
                        />
                    </div>
                    <div style={{ height: '80px', paddingTop: '30px', textAlign: 'center' }}>
                        <a style={{
                            color: 'white',
                            fontSize: '18px',
                            borderRadius: '5px',
                            border: 'solid 0.6px',
                            padding: '5px',
                            paddingLeft: '25px',
                            paddingRight: '25px',
                        }}
                            onClick={() => this.submitReport()}
                        >提交</a>
                    </div>
                </WingBlank>
            </div>
        )
    }
}
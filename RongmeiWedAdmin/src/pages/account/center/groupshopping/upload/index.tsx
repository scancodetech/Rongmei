import React, {Component} from 'react';

import {COMMON_SERVICE} from '@/services/config';
import commodityStyles from '@/pages/account/center/groupshopping/groupshopping.less';
import {
    Button,
    Checkbox,
    Input,
    Upload,
    message,
    Select,
    Row,
    Col,
    Space,
    DatePicker
} from 'antd';
import {InboxOutlined, LoadingOutlined, CloudUploadOutlined} from '@ant-design/icons';
import 'react-quill/dist/quill.snow.css';
import {getServiceBaseUrl} from "@/services/DNS";
import ClassCropperModal from '@/components/CropImage/ClassCropperModal';
import {RcFile} from "antd/lib/upload/interface";
import auctionStyles from "@/pages/account/center/auction/auction.less";
import {getUserBase} from "@/services/user";
import {getGroupShopping, updateGroupShopping} from "@/services/groupshopping";
import {getTCC} from "@/services/tcc";
import moment from "moment";
import {uploadFileToCos} from "@/services/upload";

const MAX_FILE_SIZE = 5 * 1024 * 1024 // 文件最大限制为5M

const {RangePicker} = DatePicker;
const {TextArea} = Input;

class AccountCenterGroupShoppingUpload extends Component<any> {

    state = {
        id: 0,
        title: '',
        price: 0.00,
        coverUrl: '',
        tags: [],
        contentUrl: '',
        information: '',
        description: '',
        comment: '',
        startTime: new Date().getTime(),
        endTime: new Date().getTime(),

        allMenuTypes: [],
        uploadLoading: false,
        submitLoading: false,
        isAgree: false,

        isCropperModalVisible: false,
        cropperModalFile: null
    }

    onAgreeChange(value) {
        this.setState({
            isAgree: value.target.checked
        })
    }

    async componentDidMount() {
        let groupShoppingId = this.props.location.pathname.split('/').pop();
        if (groupShoppingId != 0) {
            const res = await getGroupShopping(groupShoppingId);
            this.setState({
                id: groupShoppingId,
                title: res.title,
                price: res.price / 100,
                coverUrl: res.coverUrl,
                tags: res.tags,
                contentUrl: res.contentUrl,
                information: res.information,
                description: res.description,
                comment: res.comment,
                startTime: res.startTime,
                endTime: res.endTime,
            })
        }

        let groupShoppingTypeRes = await getTCC(`dimension.groupshopping.type`);
        const groupShoppingTypes = eval(groupShoppingTypeRes.tccTuple.value);
        this.setState({
            allMenuTypes: groupShoppingTypes
        })

        let userRes = await getUserBase();
        if (userRes.phone) {
            console.log(userRes.phone)
            this.setState({
                author: userRes.phone
            })
        }
    }

    handleCoverBeforeUpload = (file: RcFile, FileList: RcFile[]) => {
        if (file) {
            if (file.size <= MAX_FILE_SIZE) {
                this.setState(
                    {
                        cropperModalFile: file // 先把上传的文件暂存在state中
                    },
                    () => {
                        this.setState({
                            isCropperModalVisible: true // 然后弹出modal
                        })
                    }
                )
            } else {
                console.log('文件过大')
            }
        }
    }

    beforeUpload = async (file) => {
        this.setState({
            uploadLoading: true
        })
        try {
            const resp = await uploadFileToCos(file.name, file)
            if (resp.statusCode === 200) {
                this.setState({
                    contentUrl: "https://" + resp.Location
                })
            }
        } catch (e) {
            console.log(e)
            message.error(`${file.name} 文件上传失败`);
        }
        this.setState({
            uploadLoading: false
        })
        return false;
    }

    submit = async () => {
        if (!this.state.isAgree) {
            message.info({
                duration: 2,
                content: '请先同意以上条款',
                style: {
                    marginTop: '45vh',
                },
            });
            return;
        }
        this.setState({
            submitLoading: true
        })
        try {
            await updateGroupShopping({
                id: this.state.id,
                title: this.state.title,
                price: this.state.price * 100,
                coverUrl: this.state.coverUrl,
                tags: this.state.tags,
                contentUrl: this.state.contentUrl,
                information: this.state.information,
                description: this.state.description,
                comment: this.state.comment,
                startTime: this.state.startTime,
                endTime: this.state.endTime
            })
            message.success("提交成功")
            this.props.history.push('/account/center/groupshopping/list')
        } catch (e) {
            message.error("提交失败")
        }
        this.setState({
            submitLoading: false
        })
    }

    render() {
        return (
            <div className={commodityStyles.uploadContainer}>
                <div className={commodityStyles.headTitle}>
                    巨人车上传
                </div>
                <div className={commodityStyles.generalInfo}>
                    基本信息
                </div>
                <div className={commodityStyles.upload}>
                    {this.state.isCropperModalVisible ? <ClassCropperModal
                        isClassCropperModalVisible={this.state.isCropperModalVisible}
                        uploadedImageFile={this.state.cropperModalFile}
                        onClose={() => {
                            this.setState({isCropperModalVisible: false})
                        }}
                        onSubmit={(url) => {
                            this.setState({
                                contentUrl: url,
                                coverUrl: url,
                                isCropperModalVisible: false
                            })
                        }}
                    /> : null}
                    <Upload
                        listType="picture-card"
                        style={{width: '180px', height: '180px',}}
                        className={commodityStyles.uploadArea}
                        showUploadList={false}
                        beforeUpload={this.handleCoverBeforeUpload}
                    >
                        {this.state.coverUrl ?
                            <img src={this.state.coverUrl} alt="avatar" style={{height: '100%'}}/> :
                            <div>
                                {this.state.uploadLoading ? <LoadingOutlined/> :
                                    <CloudUploadOutlined style={{fontSize: 60}}/>}
                                <div
                                    // className="ant-upload-text"
                                >
                                    <div className={auctionStyles.antuploadtext}>{"点击/拖动上传"}</div>
                                </div>
                                <div>{"大小：1MB以内"}</div>
                            </div>}
                    </Upload>
                </div>
                <div className={commodityStyles.edit} style={{width: '100%'}}>
                    <Row gutter={[16, 16]}>
                        <Col span={8}>
                            <div className={commodityStyles.editName}>
                                <div className={commodityStyles.editText}>巨人车名称</div>
                                <Input required className={commodityStyles.editInput}
                                       value={this.state.title}
                                       onChange={({target: {value}}) => {
                                           this.setState({
                                               title: value
                                           })
                                       }}
                                       placeholder="输入巨人车名称"/>
                            </div>
                        </Col>
                        <Col span={8} offset={4}>
                            <div className={commodityStyles.editTag}>
                                <div className={commodityStyles.editText}>巨人车标签</div>
                                <Select mode="tags" style={{width: '100%'}}
                                        className={commodityStyles.editInput}
                                        placeholder="请选择标签（输入回车以创建自定义项，请注意与tcc平台的二级分类对应）" onChange={(value) => {
                                    this.setState({
                                        tags: value
                                    })
                                }}>
                                    {this.state.allMenuTypes.map((item) => (
                                        <Select.Option key={item} value={item}>{item}</Select.Option>
                                    ))}
                                </Select>
                                {/* <Input className={commodityStyles.editInput} placeholder="输入素材标签" /> */}
                            </div>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={20}>
                            <div className={commodityStyles.editText}>车票价格</div>
                            <Input
                                className={commodityStyles.editInput}
                                suffix="电子"
                                type="number"
                                value={this.state.price}
                                onChange={({target: {value}}) => {
                                    this.setState({
                                        price: value
                                    })
                                }}
                                placeholder="请输入价格"/>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={20}>
                            <div className={commodityStyles.deitDescription}>
                                <div className={commodityStyles.editText}>巨人车资料</div>
                                <TextArea
                                    style={{borderRadius: '8px'}}
                                    placeholder="请输入巨人车资料"
                                    autoSize={{minRows: 6}}
                                    value={this.state.information}
                                    onChange={({target: {value}}) => {
                                        this.setState({
                                            information: value
                                        })
                                    }}
                                />
                            </div>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={20}>
                            <div className={commodityStyles.deitDescription}>
                                <div className={commodityStyles.editText}>巨人车介绍</div>
                                <TextArea
                                    style={{borderRadius: '8px'}}
                                    placeholder="请输入巨人车介绍"
                                    autoSize={{minRows: 6}}
                                    value={this.state.description}
                                    onChange={({target: {value}}) => {
                                        this.setState({
                                            description: value
                                        })
                                    }}
                                />
                            </div>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={20}>
                            <div className={commodityStyles.deitDescription}>
                                <div className={commodityStyles.editText}>巨人车备注</div>
                                <TextArea
                                    style={{borderRadius: '8px'}}
                                    placeholder="请输入巨人车备注"
                                    autoSize={{minRows: 6}}
                                    value={this.state.comment}
                                    onChange={({target: {value}}) => {
                                        this.setState({
                                            comment: value
                                        })
                                    }}
                                />
                            </div>
                        </Col>
                    </Row>

                    <Space size="small" direction='vertical'>
                        <Space size='small'>
                            <div className={auctionStyles.editText}>巨人车起止时间：</div>
                        </Space>

                        <Space size='small'>
                            <RangePicker
                                showTime={{format: 'HH:mm'}}
                                format="YYYY-MM-DD HH:mm"
                                placeholder={['开始时间', '结束时间']}
                                value={[moment(this.state.startTime), moment(this.state.endTime)]}
                                onChange={(value) => {
                                    this.setState({
                                        startTime: new Date(value[0]).getTime(),
                                        endTime: new Date(value[1]).getTime()
                                    })
                                }}
                            />
                        </Space>
                    </Space>

                    <div className={commodityStyles.wallet}>
                        <div className={commodityStyles.generalInfo}>
                            资源信息
                        </div>
                        <Row gutter={[16, 16]}>
                            <Col span={20}>
                                <Upload.Dragger
                                    showUploadList={false}
                                    beforeUpload={this.beforeUpload}
                                    className={commodityStyles.updataFilechange}
                                >
                                    {this.state.contentUrl ?
                                        <div style={{width: '100%'}}>文件链接:
                                            <a style={{fontWeight: 'bold'}}
                                               href={this.state.contentUrl}>{this.state.contentUrl}</a>
                                            （点击重新上传以替换）</div> :
                                        <div>
                                            {this.state.uploadLoading ?
                                                <p className="ant-upload-drag-icon"><LoadingOutlined/></p> :
                                                <p className="ant-upload-drag-icon">
                                                    <InboxOutlined/>
                                                </p>}
                                            <p style={{
                                                fontSize: 20,
                                                fontWeight: 600,
                                                color: '#999999'
                                            }}>点击或拖动资源至此以上传</p>

                                        </div>}
                                </Upload.Dragger>
                            </Col>
                        </Row>

                    </div>

                </div>
                <div className={commodityStyles.notice}>
                    <div className={commodityStyles.noticeTitle}>上传素材须知</div>
                    <div className={commodityStyles.noticeItem}>1、我上传的所有定制素材不侵犯第三方知识产权，我是这些作品的权利人</div>
                    <div className={commodityStyles.noticeItem}>2、我上传的所有定制巨人车模版都是未曾公开销售的，只有我自己拥有此模版源文件</div>
                    <div className={commodityStyles.noticeItem}>3、我遵守跨次元个人/机构用户协议的所有内容，以及网站交易规则</div>
                    <div className={commodityStyles.noticeItem}>4、我对我上传的定制巨人车 ，承担因此导致的所有法律责任</div>
                    <div className={commodityStyles.noticetip}>温馨提示：违反以上条款的，网站将中止为您的合作，取消上传资格</div>
                    <div className={commodityStyles.noticeBox}>
                        <Checkbox onChange={this.onAgreeChange.bind(this)}
                                  style={{fontWeight: 600, padding: '0 10px'}}>我了解并同意</Checkbox>
                        <Button className={commodityStyles.uploadBtn} type='primary' danger
                                onClick={this.submit}>开始上传</Button>
                    </div>
                </div>
            </div>
        );
    }
}

export default AccountCenterGroupShoppingUpload

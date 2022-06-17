import React, {Component} from 'react';

import {COMMON_SERVICE} from '@/services/config';
import commodityStyles from '@/pages/account/center/commodity/commodity.less';
import {Button, Checkbox, Input, Upload, message, Select, Switch, AutoComplete, Row, Col} from 'antd';
import {InboxOutlined, LoadingOutlined, CloudUploadOutlined} from '@ant-design/icons';
import {getTCC} from '@/services/tcc';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import {getCommodity, updateCommodity} from '@/services/commodity';
import {getServiceBaseUrl} from "@/services/DNS";
import ClassCropperModal from '@/components/CropImage/ClassCropperModal';
import {RcFile} from "antd/lib/upload/interface";
import auctionStyles from "@/pages/account/center/auction/auction.less";
import {getUserBase, getUserGroup} from "@/services/user";
import {getMineCertification} from "@/services/apply";

const MAX_FILE_SIZE = 5 * 1024 * 1024 // 文件最大限制为5M

const {TextArea} = Input;

class AccountCenterCommodityUpload extends Component<any> {

    state = {
        id: 0,
        title: '',
        price: 0.00,
        coverUrl: '',
        tags: [],
        contentUrl: '',
        description: '',
        signingInfo: '',
        downloadUrl: '',
        uploadUrl: '',
        isExclusive: true,
        author: '',

        groupUsers: [],
        subMenuTypes: [],
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

    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }

    async componentDidMount() {
        await this.checkCertificationType("素材类");
        let baseUrl = await getServiceBaseUrl(COMMON_SERVICE);
        this.setState({
            uploadUrl: baseUrl + "/upload"
        })
        // let allSitesRes = await getTCC("rongmei.mall.sites");
        // const allSites = eval(allSitesRes.tccTuple.value);
        // for (let i = 0; i < allSites.length; i++) {
        let firstTypeRes = await getTCC(`rongmei.pic.firsttype`);
        let secondTypeRes = await getTCC(`rongmei.pic.secondtype`);
        const wholeFirstTypes = eval(firstTypeRes.tccTuple.value);
        const wholeSecondTypes = eval(secondTypeRes.tccTuple.value);
        let allSubMenuTypes = [];
        for (let i = 0; i < wholeSecondTypes.length; i++) {
            let subMenuTypes = [];
            for (let j = 0; j < wholeSecondTypes[i][0].typeList.length; j++) {
                subMenuTypes.push(wholeSecondTypes[i][0].typeList[j])
            }
            allSubMenuTypes.push({
                name: wholeFirstTypes[i],
                subMenuTypes
            })
        }
        // }
        this.setState({
            subMenuTypes: allSubMenuTypes
        }, () => {
            console.log(this.state.subMenuTypes);
        })
        let commodityId = this.props.location.pathname.split('/').pop();
        if (commodityId != 0) {
            const res = await getCommodity(commodityId);
            this.setState({
                id: commodityId,
                title: res.title,
                price: res.largePrice / 100,
                coverUrl: res.coverUrl,
                tags: res.tags,
                contentUrl: res.contentUrl,
                description: res.description,
                signingInfo: res.signingInfo,
                downloadUrl: res.downloadUrl,
                isExclusive: res.isExclusive,
                author: res.author,
            })
        }
        let userGroupId = localStorage.getItem('userGroupId');
        const userGroupRes = await getUserGroup(userGroupId);
        if (userGroupRes.userGroupItem) {
            const groupUsers = [];
            const userItems = userGroupRes.userGroupItem.userItems;
            for (let i = 0; i < userItems.length; i++) {
                groupUsers.push({
                    label: userItems[i].phone,
                    value: userItems[i].phone
                })
            }
            this.setState({
                groupUsers
            })
        }
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

    onContentChange = async (info) => {
        this.setState({
            uploadLoading: true
        })
        if (info.file.status === 'done') {
            try {
                this.setState({
                    contentUrl: info.file.response.url
                })
                this.setState({
                    uploadLoading: false
                })
                message.success(`${info.file.name} 文件上传成功`);
            } catch (e) {
                message.error(`${info.file.name} 文件上传失败`);
            }
        } else if (info.file.status === 'error') {
            message.error(`${info.file.name} 文件上传失败`);
        }
    }

    submit = async () => {
        if (this.state.isAgree == false) {
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
            await updateCommodity({
                id: this.state.id,
                title: this.state.title,
                largePrice: this.state.price * 100,
                coverUrl: this.state.coverUrl,
                tags: this.state.tags,
                contentUrl: this.state.contentUrl,
                description: this.state.description,
                signingInfo: this.state.signingInfo,
                creatorUserGroupId: localStorage.getItem('userGroupId'),
                isExclusive: this.state.isExclusive,
                author: this.state.author
            })
            message.success("提交成功")
            this.props.history.push('/account/center/commodity/list')
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
                    素材上传
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
                                <div className={commodityStyles.editText}>素材名称</div>
                                <Input required className={commodityStyles.editInput}
                                       value={this.state.title}
                                       onChange={({target: {value}}) => {
                                           this.setState({
                                               title: value
                                           })
                                       }}
                                       placeholder="输入素材名称"/>
                            </div>
                        </Col>
                        <Col span={8} offset={4}>
                            <div className={commodityStyles.editTag}>
                                <div className={commodityStyles.editText}>素材标签</div>
                                <Select mode="tags" style={{width: '100%'}}
                                        className={commodityStyles.editInput}
                                        placeholder="请选择标签（输入回车以创建自定义项，请注意与tcc平台的二级分类对应）" onChange={(value) => {
                                    this.setState({
                                        tags: value
                                    })
                                }}>

                                    {this.state.subMenuTypes.map((item) => (
                                        <Select.OptGroup label={item.name}>
                                            {
                                                item.subMenuTypes.map((subType) => (
                                                    <Select.Option key={subType}
                                                                   value={subType}>{subType}</Select.Option>
                                                ))
                                            }
                                        </Select.OptGroup>
                                    ))}
                                </Select>
                                {/* <Input className={commodityStyles.editInput} placeholder="输入素材标签" /> */}
                            </div>
                        </Col>
                    </Row>

                    <Row>
                        <Col span={20}>
                            <div className={commodityStyles.editText}>素材价格</div>
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
                                <div className={commodityStyles.editText}>素材简介</div>
                                <TextArea
                                    style={{borderRadius: '8px'}}
                                    placeholder="请输入素材简介"
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


                    <Row gutter={[16, 16]}>
                        <Col span={24}>
                            <div className={commodityStyles.deitDescription}>
                                <div className={commodityStyles.editText}>素材作者</div>
                                <Row gutter={[16, 16]}>
                                    <Col span={8}>
                                        <AutoComplete
                                            style={{width: '100%'}}
                                            required className={commodityStyles.editInput}
                                            options={this.state.groupUsers}
                                            value={this.state.author}
                                            onChange={(value: string) => {
                                                this.setState({
                                                    author: value
                                                })
                                            }}
                                            placeholder="输入素材作者"
                                        />
                                    </Col>
                                    <Col span={8} offset={4}>
                                        <div style={{verticalAlign: 'middle'}}>
                                            <Switch defaultChecked onChange={(checked) => {
                                                this.setState({
                                                    isExclusive: checked
                                                })
                                            }}/>
                                            <span className={auctionStyles.editText}
                                                  style={{marginLeft: '10px'}}>独家销售</span>
                                            <div style={{marginTop: '20px', color: '#999'}}>独家销售将享有40%的分成，非独家30%的分成
                                            </div>
                                        </div>
                                    </Col>
                                </Row>
                            </div>
                        </Col>
                    </Row>

                    <div style={{marginBottom: '60px'}}>
                        <div className={commodityStyles.editText}>签约信息（如：微博、抖音、b站、lofter等网站账号信息）</div>
                        <Row gutter={[16, 16]}>
                            <Col span={20}>
                                <ReactQuill style={{height: 200}} theme="snow" value={this.state.signingInfo}
                                            placeholder={"输入签约信息"}
                                            onChange={(value) => {
                                                this.setState({
                                                    signingInfo: value
                                                })
                                            }}/>
                            </Col>
                        </Row>
                    </div>
                    <div className={commodityStyles.wallet}>
                        <div className={commodityStyles.generalInfo}>
                            资源信息
                        </div>
                        <Row gutter={[16, 16]}>
                            <Col span={20}>
                                <Upload.Dragger
                                    showUploadList={false}
                                    onChange={this.onContentChange}
                                    action={this.state.uploadUrl}
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
                    <div className={commodityStyles.noticeItem}>2、我上传的所有定制素材模版都是未曾公开销售的，只有我自己拥有此模版源文件</div>
                    <div className={commodityStyles.noticeItem}>3、我遵守跨次元个人/机构用户协议的所有内容，以及网站交易规则</div>
                    <div className={commodityStyles.noticeItem}>4、我对我上传的定制素材 ，承担因此导致的所有法律责任</div>
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

export default AccountCenterCommodityUpload

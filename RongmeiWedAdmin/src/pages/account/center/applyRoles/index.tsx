import { ConnectState } from '@/models/connect';
import { Button, Form, Input, message, Select, Space, Upload } from 'antd';
import React, { Component } from 'react';
import styles from './applyRoles.less';
import { region, sites } from './applyList';

import { history, connect } from 'umi';
import { InboxOutlined, LoadingOutlined, MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { DatePicker } from 'antd';
import { Checkbox } from 'antd';
import { applyArtworks, applyInfos, getMineCertification } from '@/services/apply';
import { getServiceBaseUrl } from '@/services/DNS';
import { COMMON_SERVICE } from '@/services/config';
import moment from 'moment';
import auctionStyles from "@/pages/account/center/auction/auction.less";
import { getTCC } from "@/services/tcc";

const { Option } = Select;

class ApplyRoles extends Component<any> {
    formRef = React.createRef();
    stepTwoFormRef = React.createRef();
    state = {
        isAgree: false,
        step: 1,
        protocolType: '',
        uploadUrl: '',
        selectRegion: '',
        url: '',
        uploadLoading: false,
        submitLoading: false,
        id: 0,
        certificationType: '',
        name: '',
        userTypes: [],
        avatarUrl: '',
        position: '',
        email: '',
        outerPlatforms: [{
            platform: '',
            userInfo: ''
        }],
        howToUse: [],
        howToKnow: '',
        gender: '',
        birthday: '',
        address: '',
        wechat: '',
        qq: '',
        description: '',

        imageZipUrl: '',
        coverUrl: '',
        authorizationUrl: '',
        title: '',
        enTitle: '',
        pieceType: '',
        pieceStyle: '',
        pieceDate: '',

        typeList: []
    }

    async componentDidMount() {
        let userTypes = this.props.match.params.role;
        let protocolType = '';
        switch (this.props.match.params.type) {
            case '竞品类':
                protocolType = 'auction';
                break;
            case '素材类':
                protocolType = 'commodity';
                break;
            case '盒蛋类':
                protocolType = 'blindbox';
                break;
            default:
                break;
        }
        (this.setState({
            protocolType: protocolType
        }))
        let baseUrl = await getServiceBaseUrl(COMMON_SERVICE);
        this.setState({
            uploadUrl: baseUrl + "/upload"
        })
        let newUserTypes = userTypes !== '0' ? this.state.userTypes.concat(userTypes) : this.state.userTypes;
        this.setState({
            certificationType: this.props.match.params.type,
            userTypes: newUserTypes,
        })
        this.formRef.current.setFieldsValue({
            userTypes: newUserTypes
        })
        let applyRoleRes = await getTCC('dimension.pic.applyrole');
        const tupleValues = eval(applyRoleRes.tccTuple.value);
        console.log(this.props.match.params.type)
        console.log(tupleValues)
        for (let i = 0; i <= tupleValues.length; i++) {
            console.log(tupleValues[0].list[i].title)
            if (tupleValues[0].list[i].title === this.props.match.params.type) {
                this.setState({
                    typeList: tupleValues[0].list[i].typeList
                })
                break;
            }
        }
        this.updateFormDataAndStep();
    }

    async updateFormDataAndStep() {
        let step: number = 1;
        const certificationRes = await getMineCertification(this.props.match.params.type);
        console.log(certificationRes)
        if (certificationRes.isUserCertificationChecked) {
            step = 2;
        }
        if (certificationRes.userCertification) {
            const userCertification = certificationRes.userCertification;
            if (this.formRef.current) {
                await this.setState({
                    ...userCertification
                })
                this.formRef.current.setFieldsValue({
                    name: userCertification.name,
                    userTypes: userCertification.userTypes,
                    position: userCertification.position,
                    phone: userCertification.phone,
                    email: userCertification.email,
                    outerPlatforms: userCertification.outerPlatforms,
                    howToUse: userCertification.howToUse.split(" "),
                    howToKnow: userCertification.howToKnow,
                    gender: userCertification.gender,
                    birthday: moment(userCertification.birthday),
                    address: userCertification.address,
                    wechat: userCertification.wechat,
                    qq: userCertification.qq,
                    description: userCertification.description
                })
            }
        }
        if (certificationRes.userCertification && certificationRes.isUserMasterpieceChecked) {
            step = 3;
        }
        if (certificationRes.userMasterpiece) {
            const userMasterpiece = certificationRes.userMasterpiece;
            if (this.stepTwoFormRef.current) {
                this.setState({
                    ...userMasterpiece
                })
                this.stepTwoFormRef.current.setFieldsValue({
                    title: userMasterpiece.title,
                    enTitle: userMasterpiece.enTitle,
                    pieceType: userMasterpiece.pieceType,
                    pieceStyle: userMasterpiece.pieceStyle,
                    pieceDate: userMasterpiece.pieceDate,
                    description: userMasterpiece.description,
                    outerPlatforms: userMasterpiece.outerPlatforms
                })
            }
        } else {
            this.setState({
                id: 0
            })
        }
        if (step === 3) {
            step = 4;
        }
        this.setState({
            step
        })
    }

    async updateFormData() {
        const certificationRes = await getMineCertification(this.props.match.params.type);
        if (certificationRes.userCertification) {
            const userCertification = certificationRes.userCertification;
            if (this.formRef.current) {
                await this.setState({
                    ...userCertification
                })
                this.formRef.current.setFieldsValue({
                    name: userCertification.name,
                    position: userCertification.position,
                    phone: userCertification.phone,
                    email: userCertification.email,
                    outerPlatforms: userCertification.outerPlatforms,
                    howToUse: userCertification.howToUse.split(" "),
                    howToKnow: userCertification.howToKnow,
                    gender: userCertification.gender,
                    birthday: moment(userCertification.birthday),
                    address: userCertification.address,
                    wechat: userCertification.wechat,
                    qq: userCertification.qq,
                    description: userCertification.description
                })
            }
        }
        if (certificationRes.userMasterpiece) {
            const userMasterpiece = certificationRes.userMasterpiece;
            if (this.stepTwoFormRef.current) {
                this.setState({
                    ...userMasterpiece
                })
                this.stepTwoFormRef.current.setFieldsValue({
                    title: userMasterpiece.title,
                    enTitle: userMasterpiece.enTitle,
                    pieceType: userMasterpiece.pieceType,
                    pieceStyle: userMasterpiece.pieceStyle,
                    pieceDate: userMasterpiece.pieceDate,
                    description: userMasterpiece.description,
                    outerPlatforms: userMasterpiece.outerPlatforms
                })
            }
        }
    }

    onAgreeChange(value) {
        this.setState({
            isAgree: value.target.checked
        })
    }

    onFirstStepFinish = async (values: any) => {
        try {
            await applyInfos({
                id: this.state.id,
                certificationType: this.state.certificationType,
                userTypes: this.state.userTypes,
                ...values,
                howToUse: values.howToUse.join(" ")
            }).then(() => {
                this.nextStep();
            })
        } catch (e) {
            console.log(e);
        }
    }

    onSecondStepFinish = async (values: any) => {
        try {
            await applyArtworks({
                id: this.state.id,
                certificationType: this.state.certificationType,
                imageZipUrl: this.state.imageZipUrl,
                coverUrl: this.state.coverUrl,
                authorizationUrl: this.state.authorizationUrl,
                ...values,
            }).then(() => {
                this.nextStep();
            })
        } catch (e) {
            console.log(e);
        }
    }

    nextStep = async () => {
        switch (this.state.step) {
            case 1:
                await this.setState({ id: 0, step: 2 });
                break;
            case 2:
                await this.setState({ id: 0, step: 3 });
                break;
            default:
                break;
        }
        this.updateFormData();
    }

    renderMain = () => {
        switch (this.state.step) {
            case 1:
                return this.renderFirstStep();
            case 2:
                return this.renderSecondStep();
            case 3:
                return this.renderThirdStep();
            case 4:
                return this.renderForthStep();
            default:
                break;
        }
    }

    renderFirstStep() {
        return (
            <div style={{
                fontFamily: 'SourceHanSansCN-Regular',
                backgroundColor: '#ffffff',
                minHeight: 'calc(100vh - 364px)',
                width: '720px',
                padding: '30px 80px',
                margin: '30px auto'
            }}>
                <h2>创作者资料</h2>
                <Form
                    onFinish={this.onFirstStepFinish}
                    autoComplete='off'
                    requiredMark={true}
                    layout={'vertical'}
                    ref={this.formRef}
                >
                    <Form.Item
                        className={styles.applyInput}
                        name='name'
                        label='创作者昵称'
                        rules={[{ required: true, message: '请输入画师名称' }]}
                    >
                        <Input placeholder='请输入画师名称（不影响跨次元昵称展示）' />
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='userTypes'
                        label='创作者身份'
                        rules={[{ required: true, message: '请选择创作者身份' }]}
                    >
                        <Checkbox.Group style={{ width: '100%' }}>
                            {
                                this.state.typeList.map((typeItem) => (
                                    <Checkbox value={typeItem}>{typeItem}</Checkbox>
                                ))
                            }
                        </Checkbox.Group>
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='position'
                        label='地区'
                        initialValue={this.state.position || ''}
                        rules={[{ required: true, message: '请选择国家/地区' }]}
                    >
                        <Select
                            placeholder={(<div style={{ color: "#bfbfbf" }}>请选择国家/地区</div>)}
                            value={this.state.selectRegion}
                        >
                            {
                                region.map((item) => (
                                    <Option value={item.label}>{item.label}</Option>
                                ))
                            }
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name='email'
                        label='邮箱'
                        rules={[{
                            required: true,
                            message: '请输入邮箱（便于通知审核结果）'
                        }, {
                            pattern: /^[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([-_.][a-zA-Z0-9]+)*\.[a-z]{2,}$/,
                            message: '请输入正确的邮箱格式'
                        }]}
                    >
                        <Input className={styles.applyInput} placeholder='请输入邮箱（便于通知审核结果）' />
                    </Form.Item>
                    <Form.List
                        initialValue={['']}
                        name='outerPlatforms'
                        rules={[
                            {
                                validator: async (platform, userInfo) => {
                                    if (!platform || platform.length < 1) {
                                        return Promise.reject(new Error('请填写至少一个站外信息'));
                                    }
                                },
                            },
                        ]}
                    >
                        {
                            (fields, { add, remove }) => (
                                <div className={styles.applyInput}>
                                    <div className={styles.outers}
                                        style={{
                                            padding: '0 0 8px',
                                            lineHeight: '1.5715em',
                                            whiteSpace: 'normal',
                                            textAlign: 'left'
                                        }}>
                                        <label>站外信息（微博、半次元、米画师、LOFTER、站酷、涂鸦王国、晋江文学网）</label>
                                    </div>
                                    {/* <span style={{fontSize:'13px', color:'#333333'}}>至少填写一项站外信息以便通过审核</span> */}
                                    {fields.map(field => (
                                        <Space key={field.key} style={{ display: 'flex', marginBottom: 8 }}
                                            align="baseline">
                                            <Form.Item
                                                {...field}
                                                name={[field.name, 'platform']}
                                                fieldKey={[field.fieldKey, 'platform']}
                                                rules={[{ required: true, message: '请选择平台' }]}
                                            >
                                                <Select
                                                    placeholder="请选择平台"
                                                >
                                                    {
                                                        sites.map((item) => (
                                                            <Option value={item.value}>{item.title}</Option>
                                                        ))
                                                    }
                                                </Select>
                                            </Form.Item>
                                            <Form.Item
                                                {...field}
                                                name={[field.name, 'userInfo']}
                                                fieldKey={[field.fieldKey, 'userInfo']}
                                                rules={[{ required: true, message: '请填写链接' }]}
                                            >
                                                <Input placeholder="请输入平台个人链接" />
                                            </Form.Item>
                                            <MinusCircleOutlined onClick={() => remove(field.name)} />
                                        </Space>
                                    ))}
                                    <Form.Item>
                                        <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                                            新增一条
                                        </Button>
                                    </Form.Item>
                                </div>
                            )
                        }
                    </Form.List>
                    <Form.Item
                        name='howToUse'
                        label='你想如何使用跨次元创作者平台'
                        rules={[{ required: true }]}
                    >
                        <Select
                            style={{ width: '70%' }}
                            mode='multiple'
                            allowClear
                            value={this.state.howToUse}
                            placeholder='请选择'>
                            <Option value={'竞品管理'}>竞品管理</Option>
                            <Option value={'素材管理'}>素材管理</Option>
                            <Option value={'盒蛋管理'}>盒蛋管理</Option>
                            <Option value={'创作者学院'}>创作者学院</Option>
                            <Option value={'存证与维权'}>存证与维权</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name='howToKnow'
                        label='你从哪里得知跨次元平台'
                        rules={[{ required: true }]}
                    >
                        <Select style={{ width: '70%' }} value={this.state.howToKnow} placeholder='请选择渠道'>
                            <Option value={'朋友推荐'}>朋友推荐</Option>
                            <Option value={'线上社区（如QQ社群）'}>线上社区（如QQ社群）</Option>
                            <Option value={'社交平台（如微博）'}>社交平台（如微博）</Option>
                            <Option value={'其他'}>其他</Option>
                        </Select>
                    </Form.Item>
                    <h2>更多信息</h2>
                    <p style={{ fontSize: '14px', color: '#666666' }}>提交以下选填信息，便于你更快通过认证</p>
                    <Form.Item
                        className={styles.applyInput}
                        name='gender'
                        label='性别'>
                        <Select value={this.state.gender} placeholder='请选择性别'>
                            <Option value={'male'}>男</Option>
                            <Option value={'female'}>女</Option>
                            <Option value={'other'}>其他</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='birthday'
                        label='生日'>
                        <DatePicker placeholder='请输入日期'>

                        </DatePicker>
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='address'
                        label='地址'>
                        <Input placeholder='请输入详细地址' />
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='wechat'
                        label='微信号'>
                        <Input placeholder='请输入微信号' />

                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='qq'
                        label='QQ'>
                        <Input placeholder='请输入QQ' />

                    </Form.Item>
                    <Form.Item
                        style={{ marginBottom: '40px' }}
                        className={styles.applyInput}
                        name='description'
                        label='创作者简介'>
                        <Input.TextArea
                            autoSize={{ minRows: 4 }}
                            placeholder='请输入简介' />

                    </Form.Item>
                    {/* <Form.Item
            name="agreement"
            valuePropName="checked"
            rules={[
              {
                validator: (_, value) =>
                  value ? Promise.resolve() : Promise.reject('请点击同意协议'),
              },
            ]}
          >
            <Checkbox>
            我已经了解并同意<a href="">《竞价创作者平台协议》</a>
            </Checkbox>
          </Form.Item> */}
                    <Form.Item>
                        <Button
                            className={styles.applyBtn}
                            type="primary" htmlType="submit"
                            style={{ float: 'right' }}
                        >
                            下一步
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        )
    }

    renderSecondStep() {
        return (
            <div style={{
                backgroundColor: '#ffffff',
                minHeight: 'calc(100vh - 364px)',
                width: '720px',
                padding: '30px 80px',
                margin: '30px auto'
            }}>
                <h2>作品上传</h2>
                <h3>*作品文件</h3>
                <div style={{
                    fontSize: '10px',
                    fontFamily: 'Source Han Sans CN',
                    fontWeight: 'normal',
                    color: '#333333',
                    margin: '10px auto 20px'
                }}>
                    1.首次入驻请上传不少于三张原创素材作品，必须带图层或录制创作过程上传否则不予通过<br />
                    2.建议水印笔刷难以分辨原创的素材录制创造过程并上传，可加快审核<br />
                    3.厚码、未打码、模糊、无清晰图层的作品可能会被驳回<br />
                    4.建议上传png，jpg格式，图片分辨率不低于72ppi, 视频分辨率不低于360p 文件不超过200MB。<br />
                </div>
                <div>
                    <Upload.Dragger
                        showUploadList={false}
                        onChange={(info) => {
                            this.setState({
                                uploadLoading: true
                            })
                            if (info.file.status === 'done') {
                                try {
                                    this.setState({
                                        imageZipUrl: info.file.response.url
                                    })
                                } catch (e) {
                                    message.error(`${info.file.name} 文件上传失败`);
                                }
                            } else if (info.file.status === 'error') {
                                message.error(`${info.file.name} 文件上传失败`);
                            }
                            this.setState({
                                uploadLoading: false
                            })
                        }}
                        action={this.state.uploadUrl}
                    >
                        {this.state.imageZipUrl.length > 0 ?
                            <div style={{ width: '100%' }}>文件链接:
                                <div style={{ fontWeight: 'bold' }}>{this.state.imageZipUrl}</div>
                                （点击重新上传以替换）</div> :
                            <div>
                                {this.state.uploadLoading ?
                                    <p className="ant-upload-drag-icon"><LoadingOutlined /></p> :
                                    <p className="ant-upload-drag-icon">
                                        <InboxOutlined />
                                    </p>}
                                <p className="ant-upload-text">点击或拖动资源（压缩包）至此以上传</p>
                            </div>}
                    </Upload.Dragger>
                </div>
                <Form
                    layout={'vertical'}
                    style={{ marginTop: '20px' }}
                    ref={this.stepTwoFormRef}
                    onFinish={this.onSecondStepFinish}>
                    <h3>
                        你认为你最好的作品资料
                    </h3>
                    <div className={styles.upload}>
                        <Upload
                            listType="picture-card"
                            className={auctionStyles.uploadArea}
                            showUploadList={false}
                            action={this.state.uploadUrl}
                            onChange={(info) => {
                                this.setState({
                                    uploadLoading: true
                                })
                                if (info.file.status === 'done') {
                                    try {
                                        this.setState({
                                            coverUrl: info.file.response.url
                                        })
                                    } catch (e) {
                                        message.error(`${info.file.name} 文件上传失败`);
                                    }
                                } else if (info.file.status === 'error') {
                                    message.error(`${info.file.name} 文件上传失败`);
                                }
                                this.setState({
                                    uploadLoading: false
                                })
                            }}
                        >
                            {this.state.coverUrl ?
                                <img src={this.state.coverUrl} alt="avatar" style={{ height: '100%' }} /> :
                                <div>
                                    {this.state.uploadLoading ? <LoadingOutlined /> : <PlusOutlined />}
                                    <div className="ant-upload-text">上传封面图</div>
                                </div>}
                        </Upload>
                    </div>
                    <Form.Item
                        className={styles.applyInput}
                        name='title'
                        label='标题'
                        rules={[{ required: true }]}>
                        <Input placeholder='请输入标题' />
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='enTitle'
                        label='英文标题'
                        rules={[{ required: true }]}>
                        <Input placeholder='请输入英文标题' />
                    </Form.Item>
                    {/* <Form.Item
            className={styles.applyInput}
            name='pieceType'
            label='作品类型'
            rules={[{ required: true }]}>

          </Form.Item> */}
                    <Form.Item
                        className={styles.applyInput}
                        name='pieceStyle'
                        label='创作风格'
                        rules={[{ required: true }]}>
                        <Select value={this.state.pieceType} placeholder='请选择'>
                            <Option value='赛璐璐'>赛璐璐</Option>
                            <Option value='伪厚涂'>伪厚涂</Option>
                            <Option value='厚涂'>厚涂</Option>
                            <Option value='CG'>CG</Option>
                            <Option value='暗黑'>暗黑</Option>
                            <Option value='日韩'>日韩</Option>
                            <Option value='西幻'>西幻</Option>
                            <Option value='国潮'>国潮</Option>
                            <Option value='赛博朋克'>赛博朋克</Option>
                            <Option value='古风'>古风</Option>
                            <Option value='现代'>现代</Option>
                            <Option value='克苏鲁'>克苏鲁</Option>
                            <Option value='机械'>机械</Option>
                            <Option value='其他'>其他</Option>
                        </Select>
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='pieceDate'
                        label='创作日期'
                        rules={[{ required: true }]}>
                        <Input placeholder='请输入创作日期' />
                    </Form.Item>
                    <Form.Item
                        className={styles.applyInput}
                        name='description'
                        label='作品简介/文设创作心路'
                        rules={[{ required: true }]}>
                        <Input placeholder='请输入简介' />
                    </Form.Item>
                    <Form.List
                        initialValue={['']}
                        name='outerPlatforms'
                        rules={[
                            {
                                validator: async (platform, userInfo) => {
                                    if (!platform || platform.length < 1) {
                                        return Promise.reject(new Error('请填写至少一个站外信息'));
                                    }
                                },
                            },
                        ]}
                    >
                        {
                            (fields, { add, remove }) => (
                                <div>

                                    <div className={styles.outers}
                                        style={{
                                            padding: '0 0 8px',
                                            lineHeight: '1.5715em',
                                            whiteSpace: 'normal',
                                            textAlign: 'left'
                                        }}>
                                        <label>站外信息（微博、半次元、米画师、LOFTER、站酷、涂鸦王国、晋江文学网）</label>
                                    </div>
                                    {/* <span style={{fontSize:'13px', color:'#333333'}}>至少填写一项站外信息以便通过审核</span> */}
                                    {fields.map(field => (
                                        <Space key={field.key} style={{ display: 'flex', marginBottom: 8 }}
                                            align="baseline">
                                            <Form.Item
                                                {...field}
                                                name={[field.name, 'platform']}
                                                fieldKey={[field.fieldKey, 'platform']}
                                                rules={[{ required: true, message: '请选择平台' }]}
                                            >
                                                <Select
                                                    placeholder="请选择平台"
                                                >
                                                    {
                                                        sites.map((item) => (
                                                            <Option value={item.value}>{item.title}</Option>
                                                        ))
                                                    }
                                                </Select>
                                            </Form.Item>
                                            <Form.Item
                                                {...field}
                                                name={[field.name, 'userInfo']}
                                                fieldKey={[field.fieldKey, 'userInfo']}
                                                rules={[{ required: true, message: '请填写链接' }]}
                                            >
                                                <Input placeholder="请输入平台个人链接" />
                                            </Form.Item>
                                            <MinusCircleOutlined onClick={() => remove(field.name)} />
                                        </Space>
                                    ))}
                                    <Form.Item>
                                        <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined />}>
                                            新增一条
                                        </Button>
                                    </Form.Item>
                                </div>
                            )
                        }
                    </Form.List>
                    <h3>授权证明*</h3>
                    <div style={{
                        fontSize: '10px',
                        fontFamily: 'Source Han Sans CN',
                        fontWeight: 'normal',
                        color: '#333333',
                        margin: '10px auto 20px'
                    }}>
                        上传以下证明作品能力的信息将有助于你的作品加快通过审核，例如：
                        ·作品历史成交最高价
                        ·其他创作平台的后台截图
                        ·其他能够证明相关创作能力的文件
                        支持格式：jpeg,jpg,png,pdf,多个文件请压缩后上传
                        大小限制：不超过200MB
                    </div>

                    <div className={styles.upload}>
                        <Upload.Dragger
                            showUploadList={false}
                            onChange={(info) => {
                                this.setState({
                                    uploadLoading: true
                                })
                                if (info.file.status === 'done') {
                                    try {
                                        this.setState({
                                            authorizationUrl: info.file.response.url
                                        })
                                    } catch (e) {
                                        message.error(`${info.file.name} 文件上传失败`);
                                    }
                                } else if (info.file.status === 'error') {
                                    message.error(`${info.file.name} 文件上传失败`);
                                }
                                this.setState({
                                    uploadLoading: false
                                })
                            }}
                            action={this.state.uploadUrl}
                        >
                            {this.state.imageZipUrl.length > 0 ?
                                <div style={{ width: '100%' }}>文件链接:
                                    <div style={{ fontWeight: 'bold' }}>{this.state.authorizationUrl}</div>
                                    （点击重新上传以替换）</div> :
                                <div>
                                    {this.state.uploadLoading ?
                                        <p className="ant-upload-drag-icon"><LoadingOutlined /></p> :
                                        <p className="ant-upload-drag-icon">
                                            <InboxOutlined />
                                        </p>}
                                    <p className="ant-upload-text">点击或拖动资源至此以上传</p>
                                </div>}
                        </Upload.Dragger>
                    </div>
                    <Form.Item>
                        <div style={{ display: 'flex', flexDirection: 'column', }}>
                            <Checkbox style={{ marginBottom: '30px' }} onChange={this.onAgreeChange.bind(this)}>我已经了解并同意
                                <a href={`/picadmin/#/picadmin/account/center/protocol/${this.state.protocolType}`}
                                    target='_blank'>《{this.state.certificationType}创作者平台协议》</a></Checkbox>
                            <Button type="primary" htmlType="submit" disabled={!this.state.isAgree}
                                style={{ alignSelf: 'flex-end' }} className={styles.applyBtn}>提交申请</Button>
                        </div>
                    </Form.Item>
                </Form>

            </div>
        )
    }

    renderThirdStep() {
        return (
            <div style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                backgroundColor: '#ffffff',
                minHeight: 'calc(100vh - 364px)',
                width: '720px',
                padding: '30px 80px',
                margin: '30px auto'
            }}>
                <div className={styles.success}></div>
                <p style={{ margin: '50px auto' }}>您已经成功提交了申请，请耐心等待结果通知~</p>
                <Button
                    style={{ marginBottom: '30px' }}
                    className={styles.applyBtn}
                    onClick={() => {
                        history.push('/welcome');
                    }}>返回首页</Button>
            </div>
        )
    }

    renderForthStep() {
        return (
            <div style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                backgroundColor: '#ffffff',
                minHeight: 'calc(100vh - 364px)',
                width: '720px',
                padding: '30px 80px',
                margin: '30px auto'
            }}>
                <div className={styles.success}></div>
                <p style={{ margin: '50px auto' }}>恭喜您已经通过审核，尽情创作吧~</p>
                <Button
                    style={{ marginBottom: '30px' }}
                    className={styles.applyBtn}
                    onClick={() => {
                        history.push('/welcome');
                    }}>返回首页</Button>
            </div>
        )
    }

    render() {
        return this.renderMain();
    }

}

export default connect(({ user, userInfo }: ConnectState) => ({
    user,
    userInfo,
}))(ApplyRoles);
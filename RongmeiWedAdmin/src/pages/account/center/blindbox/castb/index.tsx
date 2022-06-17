import React, { Component } from 'react';

import { Button, message, Input, Modal, Upload, Switch, Select, Space } from 'antd';
import auctionStyles from "@/pages/account/center/auction/auction.less";
import { getThing, updateThing, updateToken } from "@/services/auction";
import { LoadingOutlined, CloudUploadOutlined } from '@ant-design/icons';
import { getUserBase, getUserBasisSecurity } from '@/services/user';
import { mint, createAccount } from '@/services/ethereum';
import { InboxOutlined } from "@ant-design/icons/lib";
import { getTCC } from '@/services/tcc';
import { randomStorageSource, randomWord } from '@/utils/utils';
import { COMMON_SERVICE } from "@/services/config";
import { getServiceBaseUrl } from "@/services/DNS";
import { RcFile } from "antd/lib/upload/interface";
import ClassCropperModal from "@/components/CropImage/ClassCropperModal";
import { postToStorage } from "@/services/outer";
import CryptoJS from "crypto-js";
import { getMineCertification } from "@/services/apply";
import { uploadFileToCos } from "@/services/upload";

const { Option } = Select;
const { TextArea } = Input;

const MAX_FILE_SIZE = 5 * 1024 * 1024 // 文件最大限制为5M

class EggNFTB extends Component<any> {

    state = {
        id: 0,
        name: '',
        url: '',
        price: 0,
        description: '',
        createTime: new Date().getTime(),
        author: '',
        owner: '',
        tokenId: 0,
        chainTxId: '',
        confirmationLetterUrl: '',
        tags: [''],
        selectedTag: '',
        nearAccountId: '',
        uploadLoading: false,
        submitLoading: false,

        isCropperModalVisible: false,
        cropperModalFile: null,
        localFile: null
    }

    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        // console.log(certificationRes)
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }

    async componentDidMount() {
        await this.checkCertificationType("竞品类");
        const userSecurityRes = await getUserBasisSecurity();
        const nftTypeRes = await getTCC('dimension.nft.type');
        this.setState({
            tags: eval(nftTypeRes.tccTuple.value)
        }, () => {
            console.log(this.state.tags);

        })
        if (userSecurityRes.nearAccountId) {
            this.setState({
                nearAccountId: userSecurityRes.nearAccountId
            })
        }
        let id = this.props.location.pathname.split('/').pop();
        if (id != 0) {
            const res = await getThing(id);
            this.setState({
                id,
                name: res.name,
                url: res.url,
                price: res.price / 100,
                description: res.description,
                createTime: res.createTime,
                author: res.author,
                owner: res.owner,
                tokenId: res.tokenId,
                selectedTag: res.tags.length > 0 ? res.tags[0] : ''
            })
        }
    }

    onTagSelect = (value) => {
        this.setState({
            selectedTag: value
        })
    }

    handleCoverBeforeUpload = (file: File, FileList: File[]) => {
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

    /**
     *
     * blob二进制 to base64
     **/
    blobToDataURI = (blob, callback) => {
        const reader = new FileReader();
        reader.onload = function (e) {
            callback(e.target.result);
            console.log(e)
        }
        reader.readAsDataURL(blob);
    }

    submit = async () => {
        this.setState({
            submitLoading: true
        })
        try {
            if (this.state.id == 0 && this.state.nearAccountId.length == 0) {
                message.info("检测到未绑定钱包，开始自动生成钱包……");
                message.info("正在获取用户信息……");
                let res = await getUserBase();
                if (res.phone) {
                    let username = res.phone;
                    this.createNearAccount(username, "chongxin_rongmei_" + randomWord(false, 6))
                    message.info("自动生成钱包成功");
                } else {
                    message.error("用户信息获取失败，请联系客服");
                }
            }
            let owner = this.state.owner;
            let author = this.state.author;
            if (this.state.id === 0) {
                message.info("正在提交事务……");
                const res = await getUserBase();
                await mint(res.phone, this.state.tokenId);
                owner = res.phone;
                author = res.phone;
            }
            await updateThing({
                id: this.state.id,
                name: this.state.name,
                url: this.state.url,
                price: this.state.price * 100,
                description: this.state.description,
                createTime: this.state.createTime,
                author: author,
                owner: owner,
                tokenId: this.state.tokenId,
                tags: [this.state.selectedTag],
                chainTxId: this.state.chainTxId,
                confirmationLetterUrl: this.state.confirmationLetterUrl
            })
            message.success("提交成功")
            this.props.history.push('/account/center/auction/nft')
        } catch (e) {
            console.log(e)
            message.error("提交失败")
        }
        this.setState({
            submitLoading: false
        })
    }

    async createNearAccount(username: string, accountId: string) {
        await createAccount(username, accountId);
        let res = await getUserBasisSecurity();
        if (res.nearAccountId) {
            this.setState({
                nearAccountId: res.nearAccountId,
            });
        }
    }

    beforeUpload = async (file) => {
        this.setState({
            uploadLoading: true
        })
        try {
            const resp = await uploadFileToCos(file.name, file)
            if (resp.statusCode === 200) {
                const tokenRes = await updateToken({
                    tokenId: 0,
                    value: "https://" + resp.Location
                });
                this.blobToDataURI(file, async (result) => {
                    const digest = CryptoJS.SHA256(result).toString().substr(0, 48);
                    let res = await getUserBase();
                    const cunanchainRes = await postToStorage(digest, file.name, res.phone, await randomStorageSource(), "竞品类", this.state.url);
                    this.setState({
                        contentUrl: "https://" + resp.Location,
                        tokenId: tokenRes.tokenId,
                        chainTxId: cunanchainRes.txId,
                        confirmationLetterUrl: cunanchainRes.confirmationLetterUrl
                    })
                    message.success(`${file.name} 文件上传成功`);
                    this.setState({
                        uploadLoading: false
                    })
                })
            }
        } catch (e) {
            console.log(e)
            message.error(`${file.name} 文件上传失败`);
        }
        return false;
    }

    render() {
        return (
            <Space size='middle' direction="vertical">

                <Space size="middle" direction="vertical">
                    <Space size="small">
                        <div className={auctionStyles.headTitle}>
                            铸造品分类
                        </div>
                    </Space>

                    <Space size="middle">
                        <Select className={auctionStyles.changeSelect} placeholder={'请选择分类'}
                            style={{ width: 240, fontSize: 18, textAlign: 'center', color: 'black', }}
                            onChange={this.onTagSelect}>
                            {
                                this.state.tags.map((item) => (
                                    <Option value={item.tag}>{item.name}</Option>
                                ))
                            }
                        </Select>
                    </Space>
                </Space>

                <Space size="middle" direction="vertical">
                    <Space size="middle">
                        <div className={auctionStyles.generalInfo}>
                            基本信息
                        </div>
                    </Space>
                    <Space size="middle" align="start">
                        <Space size="small">
                            <div style={{ fontWeight: 600, fontSize: 16 }}>封面图</div>
                        </Space>
                        <div className={auctionStyles.upload}>
                            {this.state.isCropperModalVisible ? <ClassCropperModal
                                isClassCropperModalVisible={this.state.isCropperModalVisible}
                                uploadedImageFile={this.state.cropperModalFile}
                                onClose={() => {
                                    this.setState({ isCropperModalVisible: false })
                                }}
                                onSubmit={(url) => {
                                    this.setState({
                                        url,
                                        isCropperModalVisible: false
                                    })
                                }}
                            /> : null}
                            <Upload
                                listType="picture-card"
                                style={{ width: '250px', height: '250px' }}
                                className={auctionStyles.uploadArea}
                                showUploadList={false}
                                beforeUpload={this.handleCoverBeforeUpload}
                            >
                                {this.state.url ?
                                    <img src={this.state.url} alt="avatar" style={{ height: '100%' }} /> :
                                    <div>

                                        {this.state.uploadLoading ? <LoadingOutlined /> :
                                            <CloudUploadOutlined style={{ fontSize: 60 }} />}
                                        <div className={auctionStyles.antuploadtext}>{"点击/拖动上传"}</div>
                                        <div>{"大小：1MB以内"}</div>
                                    </div>}
                            </Upload>
                        </div>
                    </Space>
                </Space>

                <Space size="middle" direction="vertical" className={auctionStyles.edit}>
                    <Space size='small' direction='vertical'>
                        <Space size='small'>
                            <div className={auctionStyles.editText}>名称</div>
                        </Space>
                        <Space size='small'>
                            <Input
                                className={auctionStyles.editInput}

                                placeholder="输入名称"
                                onChange={({ target: { value } }) => {
                                    this.setState({
                                        name: value
                                    })
                                }}
                                value={this.state.name} />
                        </Space>
                    </Space>

                    <Space size='small' direction='vertical'>
                        <Space size='small'>
                            <div className={auctionStyles.editText}>简介</div>
                        </Space>
                        <Space size='small' style={{ width: '100%' }}>
                            <TextArea rows={5} className={auctionStyles.editInput}
                                style={{ width: '890px', fontWeight: 500, fontSize: 16 }}
                                placeholder="输入简介"
                                onChange={({ target: { value } }) => {
                                    this.setState({
                                        description: value
                                    })
                                }}
                                value={this.state.description} />
                        </Space>
                    </Space>
                    <Space size='middle' direction='vertical'>
                        <Space size='small'>
                            <div className={auctionStyles.generalInfo}>
                                钱包信息
                            </div>
                        </Space>

                        <Space size='small' direction='vertical'>
                            <div className={auctionStyles.editText}>钱包地址</div>
                            <Input className={auctionStyles.editInput} value={this.state.nearAccountId}
                                style={{ backgroundColor: '#eeeeee', width: 480, fontSize: 16, fontWeight: 600 }}
                                placeholder="正在加载钱包地址"
                                disabled />
                        </Space>
                    </Space>

                    <Space size='middle' direction='vertical'>
                        <Space size='small' direction='vertical'>
                            <div className={auctionStyles.generalInfo}>
                                资源信息
                            </div>
                        </Space>
                        <Space size='small' direction='vertical'>
                            <Upload.Dragger
                                showUploadList={false}
                                beforeUpload={this.beforeUpload}
                                className={auctionStyles.updataFilechange}
                                style={{ width: 480 }}
                            >
                                {this.state.tokenId ?
                                    <div style={{ width: '100%' }}>文件tokenId:
                                        <div style={{ fontWeight: 'bold' }}>{this.state.tokenId}</div>
                                        （点击重新上传以替换）</div> :
                                    <div style={{}}>
                                        {this.state.uploadLoading ?
                                            <p className="ant-upload-drag-icon"><LoadingOutlined /></p> :
                                            <p className="ant-upload-drag-icon">
                                                <InboxOutlined />
                                            </p>}
                                        <p style={{ fontSize: 20, fontWeight: 600, color: '#999999' }}>点击或拖动资源至此以上传</p>
                                    </div>}
                            </Upload.Dragger>
                        </Space>
                    </Space>

                    <Space size='small'>
                        <Switch disabled defaultChecked />
                        <span className={auctionStyles.editText} style={{ marginLeft: '5px' }}>自动铸造</span>
                    </Space>
                </Space>

                <div style={{ width: '890px', textAlign: 'center' }}>
                    <Button className={auctionStyles.bottomBtn} onClick={() => {
                        this.submit()
                    }} danger>开始铸造</Button>
                </div>

                <Modal>
                    {/* 艺术品上传成功 */}
                </Modal>
            </Space>


        );

    }
}

export default EggNFTB

import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import {
    List,
    WhiteSpace,
    Modal,
    Flex,
    InputItem,
    WingBlank,
    Button,
    ImagePicker,
    Picker,
    TextareaItem, Toast
} from 'antd-mobile'
import {api} from "../../../../services/api/ApiProvider";
import CryptoJS from "crypto-js";
import {randomWord, uploadFileToCos} from "../../../../utils/utils";
import lrz from "lrz";

export default class CCAuction extends React.Component {
    state = {
        authentication: 3,
        classification: '',
        name: '',
        introduction: '',
        coverUrl: '',
        downloadFile: null,
        downloadUrl: '',
        nearAccountId: '',
        classificationPicker: [],
        tokenId: '',
        chainTxId: '',
        confirmationLetterUrl: '',
        username: '',

        submitLoading: false
    }

    async checkCertificationType(type) {
        let zhType;
        if (type === "auction") {
            zhType = "竞品类";
        } else if (type === "commodity") {
            zhType = "素材类";
        } else {
            zhType = "盒蛋类";
        }
        const certificationRes = await api.applyService.getMineCertification(zhType);
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            this.setState({
                authentication: 1
            })
        }
    }

    async componentDidMount() {
        const type = this.props.match.params.type
        await this.checkCertificationType(type);
        const userSecurityRes = await api.accountService.getUserBasisSecurity();
        const nftTypeRes = await api.tccService.getTCC('dimension.nft.type');
        const nftTypes = eval(nftTypeRes.tccTuple.value);
        let classificationPickerDataType = [];
        for (let i = 0; i < nftTypes.length; i++) {
            classificationPickerDataType = classificationPickerDataType.concat({
                label: nftTypes[i].name,
                value: nftTypes[i].tag
            })
        }
        this.setState({
            classificationPicker: classificationPickerDataType
        }, () => {
            console.log(this.state.tags);
        })
        if (userSecurityRes.nearAccountId) {
            this.setState({
                nearAccountId: userSecurityRes.nearAccountId
            })
        }
        let res = await api.accountService.getUserBase();
        if (res.phone) {
            let username = res.phone;
            this.setState({
                username
            })
            Toast.success("用户信息获取成功");
        } else {
            Toast.fail("用户信息获取失败，请联系客服");
        }
    }

    PiconChange = async (files, type, index) => {
        if (type === 'add') {
            const file = files[files.length - 1].file;
            const rst = await lrz(file);
            const res = await uploadFileToCos(file.name, rst.file);
            if (res.statusCode === 200) {
                this.setState({
                    coverUrl: "https://" + res.Location
                });
            }
        } else {
            this.setState({
                coverUrl: ''
            });
        }
    }

    FileonChange = async (files, type, index) => {
        if (type === 'add') {
            const file = files[files.length - 1].file;
            console.log(file)
            this.setState({
                downloadFile: file
            });
        } else {
            this.setState({
                downloadUrl: ''
            });
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
        }
        reader.readAsDataURL(blob);
    }

    createNearAccount = async (username, accountId) => {
        await api.balanceService.createAccount(username, accountId);
        let res = await api.accountService.getUserBasisSecurity();
        if (res.nearAccountId) {
            this.setState({
                nearAccountId: res.nearAccountId,
            });
        }
    }

    storage = async (blob, type, title, callback) => {
        try {
            const uploadRes = await api.uploadService.upload(blob);
            const url = uploadRes.url;
            const res = await api.auctionService.updateToken({
                tokenId: 0,
                value: url
            });
            const storageAddressRes = await api.tccService.getTCC('dimension.storage.address');
            const storageAddresses = eval(storageAddressRes.tccTuple.value);
            const index = Math.floor(Math.random() * storageAddresses.length)
            this.blobToDataURI(blob, async (result) => {
                const digest = CryptoJS.SHA256(result).toString().substr(0, 48);
                const storageRes = await api.outerService.postToStorage({
                    fileHash: digest,
                    fileTitle: title,
                    username: this.state.username,
                    address: storageAddresses[index],
                    source: type,
                    picUrl: url
                });
                this.setState({
                    tokenId: res.tokenId,
                    chainTxId: storageRes.txId,
                    confirmationLetterUrl: storageRes.confirmationLetterUrl
                })
                callback();
                Toast.success(`文件存证成功`);
            })
        } catch (e) {
            console.log(e)
            Toast.fail(`文件存证失败`);
        }
    }

    submitAuction = async () => {
        this.setState({
            submitLoading: true
        })
        try {
            if (this.state.nearAccountId.length === 0) {
                Toast.info("检测到未绑定钱包，开始自动生成钱包……");
                Toast.info("正在获取用户信息……");
                let res = await api.accountService.getUserBase();
                if (res.phone) {
                    let username = res.phone;
                    this.createNearAccount(username, "chongxin_rongmei_" + randomWord(false, 6))
                    Toast.success("自动生成钱包成功");
                } else {
                    Toast.fail("用户信息获取失败，请联系客服");
                }
            }
            if (this.state.classification.length <= 0) {
                Toast.fail("请先选择主题")
                return;
            }
            const res = await api.accountService.getUserBase();
            let owner = res.phone;
            let author = res.phone;
            if (this.state.id === 0) {
                Toast.info("正在提交事务……");
                await api.balanceService.mint(owner, this.state.tokenId);
            }
            await this.storage(this.state.downloadFile, "竞品类", this.state.name, async () => {
                await api.auctionService.updateThing({
                    id: 0,
                    name: this.state.name,
                    url: this.state.coverUrl,
                    price: 0,
                    description: this.state.introduction,
                    author: author,
                    owner: owner,
                    tokenId: this.state.tokenId,
                    tags: this.state.classification,
                    chainTxId: this.state.chainTxId,
                    confirmationLetterUrl: this.state.confirmationLetterUrl
                })
                this.setState({
                    submitLoading: false
                })
                Toast.success("提交成功")
                this.props.history.goBack();
            });
        } catch (e) {
            console.log(e)
            Toast.fail("提交失败")
        }
    }

    submitCommodity = async () => {
        this.setState({
            submitLoading: true
        })
        await this.storage(this.state.downloadFile, "素材类", this.state.name, () => {
            this.setState({
                submitLoading: false
            })
            this.props.history.goBack();
        });
    }

    submitBlindbox = async () => {
        this.setState({
            submitLoading: true
        })
        try {
            if (this.state.nearAccountId.length === 0) {
                Toast.info("检测到未绑定钱包，开始自动生成钱包……");
                Toast.info("正在获取用户信息……");
                let res = await api.accountService.getUserBase();
                if (res.phone) {
                    let username = res.phone;
                    this.createNearAccount(username, "chongxin_rongmei_" + randomWord(false, 6))
                    Toast.success("自动生成钱包成功");
                } else {
                    Toast.fail("用户信息获取失败，请联系客服");
                }
            }
            if (this.state.classification.length <= 0) {
                Toast.fail("请先选择主题")
                return;
            }
            const res = await api.accountService.getUserBase();
            let owner = res.phone;
            let author = res.phone;
            if (this.state.id === 0) {
                Toast.info("正在提交事务……");
                await api.balanceService.mint(owner, this.state.tokenId);
            }
            await this.storage(this.state.downloadFile, "盒蛋类", this.state.name, async () => {
                await api.blindBoxService.updateBlindBoxNFT({
                    id: this.state.id,
                    name: this.state.name,
                    url: this.state.coverUrl,
                    price: 0,
                    description: this.state.introduction,
                    author: author,
                    owner: owner,
                    tokenId: this.state.tokenId,
                    theme: this.state.classification,
                    classification: this.state.classification,
                    chainTxId: this.state.chainTxId,
                    confirmationLetterUrl: this.state.confirmationLetterUrl
                })
                this.setState({
                    submitLoading: false
                })
                Toast.success("提交成功")
                this.props.history.goBack();
            });
        } catch (e) {
            console.log(e)
            Toast.fail("提交失败")
        }
    }

    submit() {
        const type = this.props.match.params.type
        if (type === "auction") {
            this.submitAuction();
        } else if (type === "commodity") {
            this.submitCommodity();
        } else {
            this.submitBlindbox();
        }
    }

    render() {
        const type = this.props.match.params.type
        return (
            <div className='CCauctionPage'>
                <Header
                    title={`铸造【${type === 'auction' ? '竞价区' : type === 'commodity' ? '素材区' : type === 'blindbox' ? '盒蛋区' : ''}】`}
                    theme={{mode: 'dark'}}/>
                <div className='CCauctionBanner'>
                    <WingBlank size='lg'>
                        <Flex justify='center' direction='column'>
                            <div>价格小于100电子可在此处铸造，大于100电子请去跨次元web端铸造</div>
                            <a style={{color: '#FE2341'}} onClick={() => {
                                window.location.href = 'https://www.dimension.pub/'
                            }}>https://www.dimension.pub/</a>
                        </Flex>
                    </WingBlank>

                    {
                        this.state.authentication === 0 &&
                        <div style={{marginTop: 120}}>
                            <Flex justify='center' direction='column'>
                                <div className='CCauctionIcon'/>
                                <WhiteSpace size='xl'/>
                                <div>您还没有认证开放本区，请先点击此处申请开放</div>
                                <WhiteSpace size='xl'/>
                                <div className='CCautionBtnChange'>
                                    <Button onClick={() => {
                                        this.props.history.push(`/setting/creatorcenter/application/${type}`)
                                    }}>前往认证</Button>
                                </div>
                            </Flex>
                        </div>
                    }
                    {
                        this.state.authentication === 1 &&
                        <div style={{marginTop: 120}}>
                            <Flex justify='center' direction='column'>
                                <div className='CCauctionIconIng'/>
                                <WhiteSpace size='xl'/>
                                <div>审核中...</div>
                                <WhiteSpace size='xl'/>
                            </Flex>
                        </div>
                    }
                    {
                        this.state.authentication === 2 &&
                        <div style={{marginTop: 120}}>
                            <Flex justify='center' direction='column'>
                                <div className='CCauctionIconFail'/>
                                <WhiteSpace size='xl'/>
                                <div style={{color: '#FE2341'}}>您未通过审核，原因：过往原创不符合</div>
                                <WhiteSpace size='lg'/>
                                <div style={{color: '#FE2341'}}>请重新提交审核</div>


                                <WhiteSpace size='xl'/>
                                <div className='CCautionBtnChange'>
                                    <Button onClick={() => {
                                        this.props.history.push(`/setting/creatorcenter/application/${type}`)
                                    }}>再次审核</Button>
                                </div>
                            </Flex>
                        </div>
                    }
                    {
                        this.state.authentication === 3 &&
                        <div style={{fontSize: 15}}>
                            <WhiteSpace size='lg'/>
                            <WingBlank size='lg'>
                                <div className='CCapplicationTypeChange'>
                                    <Flex justify='start' direction='column' align='start'>
                                        <div>
                                            <Flex justify='start' align='center'>
                                                <div style={{width: 80}}>铸造分类：</div>
                                                <div style={{width: 200}} className='CCapplicationTypePickerChange'>
                                                    <Picker
                                                        data={this.state.classificationPicker}
                                                        cols={1}
                                                        value={this.state.classification}
                                                        onOk={(value) => {
                                                            this.setState({classification: value}, () => {
                                                                console.log(this.state.classification)
                                                            })
                                                        }}
                                                    >
                                                        <List.Item arrow='down'/>
                                                    </Picker>
                                                </div>
                                            </Flex>
                                        </div>
                                        <WhiteSpace size='xl'/>
                                        <div>
                                            <Flex justify='start' align='center'>
                                                <div style={{width: 80}}>名称：</div>
                                                <div style={{width: 200}} className='CCapplicationTypePickerChange'>
                                                    <InputItem
                                                        placeholder={'请输入名称'}
                                                        onChange={(value) => {
                                                            this.setState({name: value}, () => {
                                                                console.log(this.state.name)
                                                            })
                                                        }}
                                                    />
                                                </div>
                                            </Flex>
                                        </div>
                                        <WhiteSpace size='xl'/>
                                        <div>
                                            <Flex justify='start' align='start'>
                                                <div style={{width: 80}}>简介：</div>
                                                <div style={{width: 200}} className='CCapplicationTypeTextareaChange'>
                                                    <TextareaItem
                                                        placeholder={'请输入简介'}
                                                        rows={3}
                                                        onChange={(value) => {
                                                            this.setState({introduction: value}, () => {
                                                                console.log(this.state.introduction)
                                                            })
                                                        }}
                                                    />
                                                </div>
                                            </Flex>
                                        </div>
                                        <WhiteSpace size='xl'/>
                                        <div>
                                            <Flex justify='start' align='start'>
                                                <div style={{width: 80}}>图片：</div>
                                                <div style={{width: 150}}
                                                     className='CCapplicationTypeImagePickerChange'>
                                                    <ImagePicker
                                                        length={1}
                                                        files={this.state.coverUrl.length > 0 ? [{url: this.state.coverUrl}] : []}
                                                        onChange={this.PiconChange}
                                                        selectable={this.state.coverUrl.length <= 0}
                                                    />
                                                </div>
                                            </Flex>
                                        </div>
                                        <WhiteSpace size='xl'/>
                                        <div>
                                            <Flex justify='start' align='center'>
                                                <div style={{width: 80}}>钱包地址：</div>
                                                <div style={{width: 200}}
                                                     className='CCapplicationTypeInputDisableChange'>
                                                    <InputItem
                                                        value={this.state.nearAccountId}
                                                        disabled
                                                    />
                                                </div>
                                            </Flex>
                                        </div>
                                        <WhiteSpace size='xl'/>
                                        <div>
                                            <Flex justify='start' align='start'>
                                                <div style={{width: 80}}>资源文件：</div>
                                                <div style={{width: 200}}
                                                     className='CCapplicationTypeInputDisableChange'>
                                                    {
                                                        this.state.downloadFile ? (
                                                            <p style={{color: '#FE2341'}}>{this.state.downloadFile.name}</p>
                                                        ) : null
                                                    }
                                                    <ImagePicker
                                                        length={2}
                                                        files={[]}
                                                        onChange={this.FileonChange}
                                                        accept={"*"}
                                                        selectable={this.state.downloadUrl.length <= 0}
                                                    />
                                                </div>
                                            </Flex>
                                        </div>
                                    </Flex>
                                    <div className='CCapplicationTypeButtonChange'>
                                        <Flex justify='center'>
                                            <Button disabled={this.state.submitLoading}
                                                    onClick={() => this.submit()}>铸造</Button>
                                        </Flex>
                                    </div>
                                </div>

                            </WingBlank>

                        </div>

                    }
                </div>
            </div>
        )
    }
}

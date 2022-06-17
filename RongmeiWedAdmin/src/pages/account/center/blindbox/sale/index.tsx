import React, {Component} from "react";
import info from "@/assets/logo/Infoc.png";
import {Button, DatePicker, Dropdown, Input, Menu, message, Space, Upload} from "antd";
import {CloudUploadOutlined, DownOutlined} from "@ant-design/icons";
import auctionStyles from "@/pages/account/center/auction/auction.less";
import ClassCropperModal from "@/components/CropImage/ClassCropperModal";
import TextArea from "antd/lib/input/TextArea";
import BoxShow from "@/components/BoxShow/BoxShow";
import {eggPack, serInfo, serList} from "@/services/blindbox";

const { RangePicker } = DatePicker;


class Sale extends Component {

    constructor(props) {
        super(props);
    }

    state = {
        style: '测试名称',
        hiddenEgg: '',
        eggList: [],
        styleList: [],
        info: '',
        imgUrl: '',
        imgUploading: false,
        isCropperModalVisible: false,
        cropperModalFile: '',
        styleSelect: false,
        boxNum: '',
        styleNum: '',
        price: '',
        saleTime: '',
        endTime: '',
        serIntro: ''
    }

    componentDidMount() {


        //TODO: net
        this.getSer().then(()=>{
            if(this.state.styleList.length > 0){
                this.setState({
                    style: this.state.styleList[0]
                }, ()=>{
                    this.getSerInfo();
                })
            }
        });
    }

    async getSer(){
        let res = '';
        try{
            res = await serList();
            this.setState({
                styleList: res.boxEggName
            })
        }catch (e) {
            message.error('network error')
        }
    }

    async getSerInfo(){
        let res = '';
        try {
            res = await serInfo(this.state.style);
            this.setState({
                hiddenEgg: res.hideBoxEggItem,
                eggList: res.boxEggItemList,
                styleNum: res.boxEggItemList[0].specification === '1' ? '9' : '12',
                boxNum: res.boxEggItemList[0].limitNumber,
            }, ()=>{
                this.setState({
                    styleSelect: true
                })
            })
        }catch (e) {
            message.error('network error');
        }
    }

    handleMenuClick(e){
        let index = e.key;
        let styleName = this.state.styleList[index];
        this.setState({
            style: styleName
        }, ()=>{
            this.getSerInfo();
        })
    }

    menu(){
        return(
            <Menu onClick={this.handleMenuClick.bind(this)}>
                {
                    this.state.styleList.map((value, index) => {
                        return(
                            <Menu.Item key={index}>
                                {value}
                            </Menu.Item>
                        );
                    })
                }
            </Menu>
        );
    }

    handleBeforeUpload(file: File, fileList: File[]){
        if (file) {
            // TODO:
            this.setState({
                cropperModalFile: file,
            }, ()=>{
                this.setState({
                    isCropperModalVisible: true,
                    imgUploading: true,
                }, ()=>{
                })
            })
        }
    }

    selectTime(date, dateString){
        let start_time = dateString[0];
        let end_time = dateString[1];

        let start_date = new Date(start_time.replace(/-/g, '/'));
        let end_date = new Date(end_time.replace(/-/g, '/'));

        let start_time_stamp = start_date.getTime();
        let end_time_stamp = end_date.getTime();

        this.setState({
            saleTime: start_time_stamp,
            endTime: end_time_stamp,
        })
    }

    checkEmpty(){
        if(this.state.imgUrl.length < 1){
            return false;
        }
        if(this.state.saleTime.length < 1){
            return false;
        }
        if(this.state.serIntro.length < 1){
            return false;
        }
        if(this.state.endTime.length < 1){
            return false;
        }
        if(this.state.price.length < 1){
            return false;
        }
        return true;
    }

    async submit(){
        if(this.checkEmpty()){
            let rdata = {
                "coverUrl": this.state.imgUrl,
                "seriesName": this.state.style,
                "startTime": this.state.saleTime,
                "endTime": this.state.endTime,
                "seriesIntroduction": this.state.serIntro,
                "price": this.state.price,
            }
            let res = ''
            try{
                res = await eggPack(rdata);
                if(res.infoCode === 10000){
                    message.success('新增盒蛋成功')
                }else{
                    message.error(res.description);
                }
            }catch (e) {
                message.error('network error')
            }


        }else{
            message.error('新增盒蛋信息有空值，请检查');
        }

    }

    render() {
        return(
            <div>
                <div style={{lineHeight: '40px'}}>
                    <div style={{display: 'inline-block'}}>
                        <span style={{fontSize: '16pt', fontWeight: 'bold'}}>新增盒蛋</span>
                    </div>
                    <div style={{display: 'inline-block', float: 'right', verticalAlign: 'center'}}>
                        <span style={{display: 'inline-block', color: '#FE9F23'}}>请先阅读铸造说明</span>
                        <img style={{width: '40px', height: '40px', padding:'5px 5px', float: 'right'}} src={info} alt="img"/>
                    </div>
                </div>

                <div>
                    <div style={{display: 'inline-block'}}>
                        <span style={{marginRight: '20px'}}>选择系列:</span>
                        <Dropdown overlay={this.menu.bind(this)}>
                            <Button style={{width: '160px'}}>
                                {this.state.style}
                                <DownOutlined />
                            </Button>
                        </Dropdown>
                    </div>

                    <div style={{marginTop: '30px'}}>
                        <div style={{display: 'inline-block'}}>
                            <span style={{marginRight: '20px'}}>系列图鉴:</span>
                        </div>

                        <div style={{display: 'inline-block'}}>
                            <div style={{display: 'block'}}>
                                <Upload
                                    listType="picture-card"
                                    showUploadList={false}
                                    className={auctionStyles.uploadAreaSmall}
                                    beforeUpload={this.handleBeforeUpload.bind(this)}
                                >
                                    {
                                        this.state.imgUrl ?
                                            <img src={this.state.imgUrl} alt='avatar' style={{height: '100%', borderRadius: '15px', overflow: 'hidden'}}/> :
                                            <div>
                                                <p className="ant-upload-drag-icon">
                                                    <CloudUploadOutlined style={{color: '#bbb', fontSize: 55}}/>
                                                </p>
                                                <p
                                                    style={{color: 'red', fontWeight: 'bold'}}
                                                    className="ant-upload-text">点击/拖动上传</p>
                                            </div>
                                    }
                                </Upload>
                            </div>
                        </div>
                    </div>

                    <div style={{marginTop: '15px'}}>
                        <div>
                            售卖时间
                        </div>
                        <div style={{marginTop: '8px', width: '100%'}}>
                            <Space direction="vertical" style={{width: '100%'}}>
                                <RangePicker showTime placeholder="请选择" style={{width: '60%', borderRadius: '10px'}} onChange={this.selectTime.bind(this)}/>
                            </Space>
                        </div>
                    </div>

                    <div style={{marginTop: '15px'}}>

                        <div>
                            其他说明
                        </div>

                        <TextArea
                            rows={4}
                            style={{borderRadius: '10px', marginTop: '8px'}}
                            placeholder='关于该系列盒蛋的其他说明' onChange={
                            ({target: {value}}) => {
                                this.setState({
                                    serIntro: value
                                })
                            }
                        }/>
                    </div>

                    <div style={{marginTop: '8px'}}>
                        <span>
                            每套款数: {this.state.styleNum} 款
                        </span>

                        <span style={{marginLeft: '20px'}}>
                            每箱套数: {this.state.boxNum} 套
                        </span>
                    </div>

                    <h3 style={{marginTop: '10px'}}>
                        盒蛋图样
                    </h3>

                    {
                        this.state.styleSelect ?
                            <div>
                                <div>
                                    <div style={{display: 'inline-block', width: '8%', verticalAlign: 'top'}}>
                                        <span style={{marginRight: '20px'}}>隐藏款:</span>
                                    </div>
                                    <div style={{display: 'inline-block', width: '92%'}}>
                                        <div style={{width: '25%', display: 'inline-block', marginBottom: '20px'}} key="0">
                                            <BoxShow
                                                url={this.state.hiddenEgg.coverUrl}
                                                name={this.state.hiddenEgg.boxEggIntroduction}
                                                limit={this.state.hiddenEgg.limitNumber}>
                                            </BoxShow>
                                        </div>
                                    </div>
                                </div>

                                <div>
                                    <div style={{display: 'inline-block', width: '8%', verticalAlign: 'top'}}>
                                        <span style={{marginRight: '20px'}}>普通款:</span>
                                    </div>
                                    <div style={{display: 'inline-block', width: '92%'}}>
                                            {
                                                this.state.eggList.map((value, index) => {
                                                    return (
                                                        <div style={{width: '25%', display: 'inline-block', marginBottom: '20px'}} key={index}>
                                                            <BoxShow url={value.coverUrl} name={value.boxEggIntroduction} limit={value.limitNumber}>
                                                            </BoxShow>
                                                        </div>
                                                    );
                                                })
                                            }
                                    </div>
                                </div>
                            </div> :
                            null
                    }

                </div>

                <div>
                    {
                        this.state.isCropperModalVisible ?
                            <ClassCropperModal
                                isClassCropperModalVisible={this.state.isCropperModalVisible}
                                uploadedImageFile={this.state.cropperModalFile}
                                onClose={() => {
                                    this.setState({isCropperModalVisible: false})
                                }}
                                onSubmit={(url) => {
                                    console.log('img');
                                    console.log(url);
                                    this.setState({
                                        imgUrl: url,
                                        isCropperModalVisible: false,
                                        imgUploading: false,
                                    }, ()=>{
                                    })
                                }}
                            /> :
                            null
                    }
                </div>

                <div style={{marginTop: '10px'}}>
                    <div style={{display: 'inline-block'}}>
                        <h3>定价</h3>
                    </div>
                    <div style={{display: 'inline-block', marginLeft: '10px'}}>
                        <div style={{display: 'inline-block', width: '140px'}}>
                            <Input
                                type='number'
                                size='small'
                                value={this.state.price}
                                onChange={({target: {value}})=>{
                                    if(parseInt(value) >= 0 || value === ''){
                                        this.setState({
                                            price: value
                                        })
                                    }else{
                                        message.error('定价不能小于0');
                                    }
                                }}
                            />
                        </div> /抽
                    </div>
                </div>

                <div style={{marginTop: '40px', float: 'right'}}>
                    <Button type="primary" shape="round" size="large" onClick={this.submit.bind(this)}>随机装盒</Button>
                </div>
            </div>
        );
    }
}

export default Sale;

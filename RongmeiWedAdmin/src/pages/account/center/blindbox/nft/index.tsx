import React, {Component} from 'react';
import {Dropdown, Button, Menu, Input, Switch, message} from 'antd';
import { DownOutlined} from '@ant-design/icons';
import info from '../../../../../assets/logo/Infoc.png'
import BlindBox from "@/components/BlindBox/BlindBox";
import {getUserBase} from "@/services/user";
import {castEgg} from "@/services/blindbox";

const {TextArea} = Input;

class BlindBoxCasting extends Component{

    state = {
        info: [
            {
                'style': '9款设定',
                'spec': '3*3套盒',
                'count': '9套',
                'number': 9
            },
            {
                'style': '12款设定',
                'spec': '3*4套盒',
                'count': '12套',
                'number': 12
            }
        ],
        select: 0,
        style: '',
        spec: '',
        count: '',
        boxNum: '',
        name: [],
        imgUrl: [],
        number: [''],
        fileUrl: [],
        imgUploading: [],
        fileUploading: [],
        serName: '',
        serIntro: '',
        walletAddress: '',
        auto: false,
    }

    componentDidMount() {
        let index = this.state.select;
        let num = this.state.info[index].number;

        this.setState({
            style: this.state.info[index].style,
            spec: this.state.info[index].spec,
            count: this.state.info[index].count,
            name: Array(num).fill(''),
            imgUrl: Array(num).fill(''),
            number: Array(num).fill(''),
            fileUrl: Array(num).fill(''),
            imgUploading: Array(num).fill(false),
            fileUploading: Array(num).fill(false),
        })
    }

    transformTime(timestamp) {
        if (timestamp) {
            let time = new Date(timestamp);
            let y = time.getFullYear(); //getFullYear方法以四位数字返回年份
            let M = time.getMonth() + 1; // getMonth方法从 Date 对象返回月份 (0 ~ 11)，返回结果需要手动加一
            let d = time.getDate(); // getDate方法从 Date 对象返回一个月中的某一天 (1 ~ 31)
            let h = time.getHours(); // getHours方法返回 Date 对象的小时 (0 ~ 23)
            let m = time.getMinutes(); // getMinutes方法返回 Date 对象的分钟 (0 ~ 59)
            let s = time.getSeconds(); // getSeconds方法返回 Date 对象的秒数 (0 ~ 59)
            return y + '/' + this.addZero(M) + '/' + this.addZero(d) + ' ' + this.addZero(h) + ':' + this.addZero(m) + ':' + this.addZero(s);

        } else {
            return '';
        }
    }

    setArgument(index: Number, key: String, value: String){
        switch (key){
            case '0':
                let nameOra = this.state.name;
                nameOra[index] = value;
                this.setState({
                    name: nameOra
                })
                break;
            case '1':
                /*
                if(parseInt(value) > 0 || value===''){
                    let numberOra = this.state.number;
                    // numberOra[index] = value;
                    numberOra = Array(this.state.info[this.state.select].number).fill(value);
                    this.setState({
                        number: numberOra,
                        boxNum: value,
                    })
                }else{
                   message.error('数量必须大于0');
                }

                 */
                break;
            case '2':
                let imgUrlOra = this.state.imgUrl;
                imgUrlOra[index] = value;
                this.setState({
                    imgUrl: imgUrlOra,
                })
                break;
            case '3':
                let fileUrlOra = this.state.fileUrl;
                fileUrlOra[index] = value;
                this.setState({
                    fileUrl: fileUrlOra,
                })
                break;
            case '4':
                let fileUploadingOra = this.state.fileUploading;
                fileUploadingOra[index] = value;
                this.setState({
                    fileUploading: fileUploadingOra,
                })
                break;
            case '5':
                let imgUploadingOra = this.state.imgUploading;
                imgUploadingOra[index] = value;
                this.setState({
                    imgUploading: imgUploadingOra,
                })
                break;
        }
    }

    handleMenuClick(e){
        let index = e.key;
        let num = this.state.info[index].number;

        this.setState({
            select: index,
            style: this.state.info[index].style,
            spec: this.state.info[index].spec,
            count: this.state.info[index].count,
            name: Array(num).fill(''),
            imgUrl: Array(num).fill(''),
            number: Array(num).fill(''),
            fileUrl: Array(num).fill(''),
            imgUploading: Array(num).fill(false),
            fileUploading: Array(num).fill(false),
        })
    }

    menu(){
       return(
           <Menu onClick={this.handleMenuClick.bind(this)}>
               <Menu.Item key="0">
                   9款设定
               </Menu.Item>
               <Menu.Item key="1">
                   12款设定
               </Menu.Item>
           </Menu>
       );
    }

    checkStatus(){
        if(this.state.boxNum.length < 1){
            return false;
        }
        if(this.state.serName.length < 1){
            return false;
        }
        if(this.state.serIntro.length < 1){
            return false;
        }
        if(this.state.walletAddress.length < 1){
            return false;
        }
        let len = this.state.info[this.state.select].number;

        for(let i=0; i<len; i++){
            if(this.state.name[i].length < 1){
                return false;
            }
        }
        for(let i=0; i<len; i++){
            if(this.state.imgUrl[i].length < 1){
                return false;
            }
        }
        for(let i=0; i<len; i++){
            if(this.state.number[i].length < 1){
                return false;
            }
        }
        for(let i=0; i<len; i++){
            if(this.state.fileUrl[i].length < 1){
                return false;
            }
        }

        return true;
    }

    checkUpload(){
        for(let i=0; i<this.state.imgUploading.length; i++){
            if(this.state.imgUploading[i] || this.state.fileUploading[i]){
                return false;
            }
        }
        return true;
    }

    async submit(){
        if(this.checkStatus()){
            if(this.checkUpload()){
                let reqData = {
                    "specification": this.state.select + 1,
                    "boxNumber": this.state.boxNum,
                    "hideBoxEgg": {
                        "coverUrl": this.state.imgUrl[0],
                        "boxEggName": this.state.name[0],
                        "resourceInfoURL": this.state.fileUrl[0],
                        "limitNumber": this.state.number[0],
                    },
                    "commonBoxEggList": [

                    ],
                    "walletAddess": this.state.walletAddress,
                    "seriesName": this.state.serName,
                    "seriesIntroduction": this.state.serIntro
                }
                for(let i=1; i<this.state.name.length; i++){
                    reqData.commonBoxEggList.push(
                        {
                            "coverUrl": this.state.imgUrl[i],
                            "boxEggName": this.state.name[i],
                            "resourceInfoURL": this.state.fileUrl[i],
                            "limitNumber": this.state.number[i],
                        }
                    )
                }

                try{
                    let res = await castEgg(reqData);
                    console.log(res);
                    if(res.infoCode === 10000){
                        message.success(res.description);
                    }else{
                        message.error('error!');
                    }
                }catch(e){
                    message.error('error!');
                    console.log(e)
                }


            }else{
                message.error('文件尚未上传完成，请等待');
            }
        }else{
            message.error('铸造信息有空值，请检查');
        }
    }

    render() {
        return(
            <div style={{marginLeft: '12px'}}>
                <div style={{lineHeight: '40px'}}>
                    <div style={{display: 'inline-block'}}>
                        <span style={{fontSize: '16pt', fontWeight: 'bold'}}>铸造盒蛋设定</span>
                    </div>
                    <div style={{display: 'inline-block', float: 'right', verticalAlign: 'center'}}>
                        <span style={{display: 'inline-block', color: '#FE9F23'}}>请先阅读铸造说明</span>
                        <img style={{width: '40px', height: '40px', padding:'5px 5px', float: 'right'}} src={info} alt="img"/>
                    </div>
                </div>
                <div>
                    <div style={{marginTop: '10px'}}>
                        <div style={{display: 'inline-block'}}>
                            <span style={{marginRight: '20px'}}>铸造款数:</span>
                            <Dropdown overlay={this.menu.bind(this)}>
                                <Button style={{width: '120px'}}>
                                    {this.state.style}
                                    <DownOutlined />
                                </Button>
                            </Dropdown>
                        </div>
                        <div style={{display: 'inline-block', marginLeft: '60px'}}>
                            <span style={{marginRight: '20px'}}>套盒规格:</span>
                            <Button style={{width: '120px'}}>{this.state.spec}</Button>
                        </div>
                    </div>
                    <div style={{marginTop: '10px'}}>
                        <div style={{display: 'inline-block'}}>
                            <span style={{marginRight: '20px'}}>每箱套数:</span>
                            <Button style={{width: '120px'}}>{this.state.count}</Button>
                        </div>
                        <div style={{display: 'inline-block', marginLeft: '60px'}}>
                            <span style={{marginRight: '20px'}}>铸造箱数:</span>
                            <Input placeholder='填写数字' type='number' style={{width: '120px'}} value={this.state.boxNum}  onChange={({target: {value}}) => {
                                if(parseInt(value) > 0 || value === ''){

                                    let oraNumber = Array(this.state.info[this.state.select].number).fill(value);
                                    let sn = this.state.info[this.state.select].number;
                                    let xn = value * (sn + 1);
                                    if(xn === 0){
                                        xn = '';
                                    }
                                    for(let i=1; i<sn; i++){
                                        oraNumber[i] = xn;
                                    }
                                    this.setState({
                                        boxNum: value,
                                        number: oraNumber
                                    })
                                }else{
                                    message.error('数量必须大于0');
                                }
                            }}/>
                        </div>

                        <div style={{display: 'inline-block', marginLeft: '60px'}}>
                            <span style={{marginRight: '20px'}}>中奖率: </span>
                            <span>{1 / (this.state.info[this.state.select].number * this.state.info[this.state.select].number)}</span>
                        </div>
                    </div>
                </div>
                <div style={{marginTop: '10px'}}>
                    <div style={{display: 'inline-block', width: '8%', verticalAlign: 'top'}}>
                        <span style={{marginRight: '20px'}}>隐藏款:</span>
                    </div>
                    <div style={{display: 'inline-block', width: '92%'}}>
                        <div style={{width: '25%', display: 'inline-block', marginBottom: '20px'}} key="0">
                            <BlindBox index="0" setArgument={this.setArgument.bind(this)} number={this.state.boxNum}>
                            </BlindBox>
                        </div>
                    </div>
                </div>
                <div style={{marginTop: '10px'}}>
                    <div style={{display: 'inline-block', width: '8%', verticalAlign: 'top'}}>
                        <span style={{marginRight: '20px'}}>普通款:</span>
                    </div>
                    <div style={{display: 'inline-block', width: '92%'}}>
                        {
                            this.state.name.slice(1, this.state.info[this.state.select].number).map((value, index) => {
                                return(
                                    <div style={{width: '25%', display: 'inline-block', marginBottom: '20px'}} key={index+1}>
                                        <BlindBox index={index+1} setArgument={this.setArgument.bind(this)} number={this.state.number[index+1]}>
                                        </BlindBox>
                                    </div>
                                );
                            })
                        }
                    </div>
                </div>
                <div style={{width: '30%', borderRadius: '5px'}}>
                    <h4>系列名称:</h4>
                    <Input size='middle' placeholder='输入盒蛋名称' onChange={
                        ({target: {value}})=>{
                            this.setState({
                               serName: value
                            })
                        }
                    }/>
                </div>
                <div style={{width: '96%', borderRadius: '5px', marginTop: '6px'}}>
                    <h4>系列简介:</h4>
                    <TextArea rows={4} placeholder='输入盒蛋简介' onChange={
                        ({target: {value}}) => {
                            this.setState({
                                serIntro: value
                            })
                        }
                    }/>
                </div>
                <div style={{marginTop: '6px', width: '60%' }}>
                    <h2>钱包信息</h2>
                    <h4>钱包地址:</h4>
                    <Input size='large' style={{backgroundColor: '#eee'}} onChange={
                        ({target: {value}}) => {
                            this.setState({
                                walletAddress: value
                            })
                        }
                    }/>
                </div>
                <div style={{height: '40px', marginTop: '6px'}}>
                    <div style={{display: 'inline-block', float: 'left', marginTop: '3px'}}>
                        <Switch defaultChecked onChange={
                            (checked, event) => {
                                this.setState({
                                    auto: checked
                                })
                            }
                        }/>
                    </div>
                    <div style={{display: 'inline-block', fontSize: '15pt', float: 'left', marginLeft: '6px'}}>
                        自动铸造
                    </div>
                </div>

                <div style={{marginTop: '6px', textAlign: 'right'}}>
                    <Button type="primary" danger size='large' shape='round' onClick={this.submit.bind(this)}>
                        <p style={{fontSize: '13pt', padding: '0 12px'}}>
                            开始铸造
                        </p>
                    </Button>
                </div>
            </div>
        );
    }
}

export default BlindBoxCasting;

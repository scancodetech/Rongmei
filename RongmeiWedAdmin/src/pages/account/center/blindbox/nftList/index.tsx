import React, {Component} from "react";
import {Tabs, Upload, Button, Input, message} from 'antd';
import customStyle from './index.less'
import BlindBoxItem from "@/components/BlindBoxItem/BlindBoxItem";
import {PlusSquareOutlined} from "@ant-design/icons";
import {castList, eggPub} from "@/services/blindbox";

const { TabPane } = Tabs;

class CastingList extends Component{

    constructor(props) {
        super(props);

        this.tabLeft = this.tabLeft.bind(this)
        this.handleDist = this.handleDist.bind(this)
    }

    state = {
        distShow: false,
        imgUrl: '',
        name: '',
        intro: '',
        limit: '',
        pub: '',
        left: '',
        write: 0,
        line: [],
        castList: [],
        castListM: [],
        castListR: [],
        id: '',
        nameDict: {}
    }

    componentDidMount() {
        this.getCastList('0');
        this.getCastList('1');
        this.getCastList('2');
    }

    async getCastList(ind){
        let res = '';
        try{
            res = await castList(ind);
            console.log('---')
            console.log(res);
            switch (ind){
                case '0':
                    this.setState({
                        castList: res.boxEggItemList
                    })
                    console.log('->0')
                    break
                case '1':
                    this.setState({
                        castListM: res.boxEggItemList
                    })
                    console.log('->1')
                    break
                case '2':
                    this.setState({
                        castListR: res.boxEggItemList
                    })
                    console.log('->2')
                    break
            }
        }catch (e){
            message.error('error');
        }
    }

    callback(key){
        console.log(key);
    }

    addZero(m) {
        return m < 10 ? '0' + m : m;
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
            return y + '年' + this.addZero(M) + '月' + this.addZero(d) + '日 ' + this.addZero(h) + ':' + this.addZero(m);

        } else {
            return '';
        }
    }

    handleDist(id, imgUrl, name, intro, limit, pub){
        this.setState({
            id: id,
            distShow: true,
            imgUrl: imgUrl,
            name: name,
            intro: intro,
            limit: limit,
            pub: pub,
            left: limit - pub,
            line: [],
            write: 0,
            nameDict: {}
        });
    }

    tabMiddle(){
        return(
            this.state.castListM ?
                <div style={{width: '100%'}}>
                    {
                        this.state.castListM.map((value, index)=>{
                            return(
                                <BlindBoxItem
                                    info={{
                                        'id': value.id,
                                        'imgUrl': value.coverUrl,
                                        'limit': value.limitNumber,
                                        'name': value.boxEggName,
                                        'time': this.transformTime(value.updateTime),
                                        'intro': value.boxEggIntroduction,
                                        'pub': value.publishNumber,
                                        'price': value.price
                                    }}
                                    clickDist={this.handleDist}
                                    key={index}>

                                </BlindBoxItem>
                            );
                        })
                    }
                </div> : null
        );
    }

    tabRight(){
        return(
            this.state.castListR ?
                <div style={{width: '100%'}}>
                    {
                        this.state.castListR.map((value, index)=>{
                            return(
                                <BlindBoxItem
                                    info={{
                                        'id': value.id,
                                        'imgUrl': value.coverUrl,
                                        'limit': value.limitNumber,
                                        'name': value.boxEggName,
                                        'time': this.transformTime(value.updateTime),
                                        'intro': value.boxEggIntroduction,
                                        'pub': value.publishNumber,
                                        'price': value.price
                                    }}
                                    clickDist={this.handleDist}
                                    key={index}>

                                </BlindBoxItem>
                            );
                        })
                    }
                </div> : null
        );
    }

    tabLeft(){
        return(
            this.state.castList ?
            <div style={{width: '100%'}}>
                {
                    this.state.castList.map((value, index)=>{
                        return(
                            <BlindBoxItem
                                info={{
                                    'id': value.id,
                                    'imgUrl': value.coverUrl,
                                    'limit': value.limitNumber,
                                    'name': value.boxEggName,
                                    'time': this.transformTime(value.updateTime),
                                    'intro': value.boxEggIntroduction,
                                    'pub': value.publishNumber,
                                    'price': value.price
                                }}
                                clickDist={this.handleDist}
                                key={index}>

                            </BlindBoxItem>
                        );
                    })
                }
            </div> : null
        );
    }

    castPage(){
        return(
            <div>
                <div style={{fontSize: '18pt', fontWeight: 'bold'}}>
                    铸造列表
                </div>
                <Tabs defaultActiveKey="1" onChange={this.callback.bind(this)} centered className={customStyle.tabsCustom}>
                    <TabPane tab="已铸造" key="1">
                        {
                            this.tabLeft()
                        }
                    </TabPane>
                    <TabPane tab="审核中" key="2">
                        {
                            this.tabMiddle()
                        }
                    </TabPane>
                    <TabPane tab="草稿箱" key="3">
                        {
                            this.tabRight()
                        }
                    </TabPane>
                </Tabs>
            </div>
        );
    }

    isEmptyObject(obj) {
        for (const key in obj) {
            return false;
        }
        return true;
    }

    async dist(){
        let iden = true;
        let names = [];
        if(this.isEmptyObject(this.state.nameDict)){
            iden = false;
        }
        for(const key in this.state.nameDict){
            let value = this.state.nameDict[key];
            if(value.length < 1){
                iden = false;
            }else{
                names.push(value);
            }
        }
        if(iden){
            let res = '';
            let rdata = {
                "boxEggId": this.state.id,
                "usernameList": names
            }
            try{
                res = await eggPub(rdata);
                if(res.infoCode === 10000){
                    message.success("分发盒蛋成功")
                }else{
                    message.error(res.description)
                }
            }catch (e) {
                console.log('network error')
            }
        }else{
            message.error('接收人信息不能为空，请检查')
        }
    }

    addChange(index, e){
        let value = e.target.value;
        let nameDictOra = this.state.nameDict;
        nameDictOra[index] = value;
        this.setState({
            nameDict: nameDictOra
        })
    }

    distPage(){
        return(
            <div>
                <div style={{fontSize: '18pt', fontWeight: 'bold'}}>
                    分发盒蛋
                </div>
                <div style={{width: '100%', height: '200px', marginTop: '10px'}}>
                    <div style={{width: '20%', height: '100%', display: 'inline-block', float: 'left'}}>
                        <div style={{width: '100%', height: '92%', borderRadius: '5px', backgroundColor: '#bbb'}}>
                            <img src={this.state.imgUrl} alt="pic" style={{width: '100%', height: '100%'}}/>
                        </div>
                    </div>
                    <div style={{display: 'inline-block', float: 'left', position: 'relative', height: '100%', width: '76%', marginLeft: '20px'}}>
                        <div style={{fontSize: '17pt', marginTop: '10px'}}>
                            {this.state.name}
                        </div>
                        <div style={{marginTop: '8px', fontSize: '11pt'}}>
                            {this.state.intro}
                        </div>
                        <div style={{color: 'red', position: 'absolute', bottom: '35px'}}>
                            <span>
                                限量: {this.state.limit}
                            </span>

                            <span style={{marginLeft: '30px'}}>
                                已分发: {this.state.pub} / {this.state.limit}
                            </span>
                        </div>
                    </div>
                </div>

                <div style={{marginTop: '30px'}}>
                    <div style={{display: 'inline-block'}}>
                        <Upload
                            showUploadList={false}
                        >
                            <Button type="primary" shape="round" size="small">通过CSV文件导入地址</Button>
                        </Upload>

                    </div>
                    <div style={{display: 'inline-block', marginLeft: '30px'}}>
                        <a href="#">下载模版</a>
                    </div>
                </div>

                <div style={{marginTop: '30px'}}>
                    <h3 style={{display: 'inline-block'}}>
                        填写接收秘宝的账户名称
                    </h3>
                    <h3 style={{display: 'inline-block', marginLeft: '30px', color: '#aaa'}}>
                        (尚余数量:{this.state.left} 已填写:{this.state.write})
                    </h3>
                </div>

                <div style={{width: '70%', marginTop: '10px'}}>
                    {
                        this.state.line.map((value, index)=>{
                            return(
                                <div style={{width: '100%', height: '30px', marginBottom: '8px'}} key={index}>
                                    <div style={{width: '6%', fontSize: '14pt', display: 'inline-block'}}>
                                        #{value+1}
                                    </div>
                                    <div style={{width: '90%', display: 'inline-block'}}>
                                        <Input size='small' placeholder='请输入账户名称' style={{borderBottom: '1px solid black'}} bordered={false} onChange={this.addChange.bind(this, index)}/>
                                    </div>
                                </div>
                            );
                        })
                    }

                    <div style={{color: 'red', marginLeft: '30%'}}
                         onClick={()=>{
                             if(this.state.left > 0){
                                 let lineOra = this.state.line;
                                 lineOra.push(this.state.write);
                                 this.setState({
                                     left: this.state.left - 1,
                                     write: this.state.write + 1,
                                     line: lineOra
                                 })
                             }
                         }}
                    >
                        <PlusSquareOutlined />
                        &nbsp; &nbsp;
                        新增盒蛋接收地址
                    </div>

                    <div style={{marginTop: '30px'}}>
                        <div style={{display: 'inline-block'}}>
                            <Button
                                shape="round"
                                size="large"
                                onClick={()=>{
                                    this.setState({
                                        distShow: false
                                    })
                                }}
                            >
                                &nbsp;&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;&nbsp;
                            </Button>
                        </div>

                        <div style={{display: 'inline-block', marginLeft: '30px'}}>
                            <Button type="primary" shape="round" size="large" onClick={this.dist.bind(this)}>确认分发</Button>
                        </div>

                    </div>
                </div>
            </div>
        );
    }

    render() {
        return(
            this.state.distShow ? this.distPage(): this.castPage()
        );
    }
}

export default CastingList;


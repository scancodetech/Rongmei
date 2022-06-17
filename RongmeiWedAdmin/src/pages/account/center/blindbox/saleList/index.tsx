import React, {Component} from "react";
import {Tabs, Upload, Button, Input, message} from 'antd';
import customStyle from './index.less'
import BlindBoxItem from "@/components/BlindBoxItem/BlindBoxItem";
import {PlusSquareOutlined} from "@ant-design/icons";
import {getDislikeBatch} from "@/services/metrics";
import BoxItem from "@/components/BoxItem/BoxItem";
import {eggList} from "@/services/blindbox";

const { TabPane } = Tabs;

class SaleList extends Component{

    constructor(props) {
        super(props);

        this.tabLeft = this.tabLeft.bind(this)
    }

    state = {
        itemInfo: '',
        ifItem: false,
    }

    componentDidMount() {
        this.getEggList();
    }

    async getEggList(){
        let res = '';
        try{
            res = await eggList('1');
            this.setState({
                itemInfo: res.boxEggItemList
            }, ()=>{
                this.setState({
                    isItem: true
                })
            })
        }catch (e) {
           message.error('network error');
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

    tabLeft(){
        return(
            this.state.isItem ?
            <div style={{width: '100%'}}>
                {
                    this.state.itemInfo.map((value, index) => {
                        return(
                            <BoxItem
                                key={index}
                                info={{
                                    'type': value.isHide === 1 ? '隐藏款' : '普通款',
                                    'serName': value.seriesName,
                                    'imgUrl': value.coverUrl,
                                    'limit': value.limitNumber,
                                    'name': value.boxEggName,
                                    'time': this.transformTime(value.updateTime),
                                    'intro': value.boxEggIntroduction,
                                    'pub': value.publishNumber,
                                    'price': value.price
                                }}
                            >
                            </BoxItem>
                        );
                    })
                }
            </div>:
                null
        );
    }

    castPage(){
        return(
            <div>
                <div style={{fontSize: '18pt', fontWeight: 'bold'}}>
                    盒蛋列表
                </div>
                <Tabs defaultActiveKey="1" onChange={this.callback.bind(this)} centered className={customStyle.tabsCustom}>
                    <TabPane tab="已发布" key="1">
                        {
                            this.tabLeft()
                        }
                    </TabPane>
                    <TabPane tab="审核中" key="2">
                        Content of Tab Pane 2
                    </TabPane>
                    <TabPane tab="草稿箱" key="3">
                        Content of Tab Pane 3
                    </TabPane>
                </Tabs>
            </div>
        );
    }

    distPage(){
        return(
            <div>DIST</div>
        );
    }

    render() {
        return(
            this.castPage()
        );
    }
}

export default SaleList;

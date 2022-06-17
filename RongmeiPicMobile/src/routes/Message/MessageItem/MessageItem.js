import React,{Component} from 'react'
import { ListView, WhiteSpace } from 'antd-mobile';
import ReactDOM from 'react-dom'
import notiIcon from '../../../assets/noti.png'
import tradeIcon from '../../../assets/trade.png'
import activityIcon from '../../../assets/activity.png'
import rightArrowIcon from '../../../assets/arrow-right.svg'
import Header from "../../../components/Header";
import { NavBar, Icon, PullToRefresh, Toast } from 'antd-mobile'


function MyBody(props) {
    return (
        <div className="am-list-body my-body">
            <span style={{ display: 'none' }}>you can custom body wrap element</span>
            {props.children}
        </div>
    );
}

const data = [
    {
        img: notiIcon,
        title: '小助手',
        is_official: '1',
        time: '20分钟',
        content: '尊敬的 李又又12138 ，您遛设的内容 已通过 审核！ 快去首页推荐看看吧~',
        is_link: '0',
    },
    {
        img: tradeIcon,
        title: '交易通知',
        is_official: '0',
        time: '18:16',
        content: '您的提现申请 ：100电子 已成功到账。',
        is_link: '0',
    },
    {
        img: activityIcon,
        title: '活动通知',
        is_official: '0',
        time: '20:32',
        content: '时间给予设群  X  跨次元平台，联合发起【次元人气赛 】活动 ' +
            '活动时间：2021.4.27-5.4 ' +
            '一起来溜自己最爱的设子，点赞最高的崽将获得第一届人气王称号哦~' +
            '具体操作流程如下：' +
            'https://shimo.im/docs/DOzTjZM4L7EGdM0l' +
            '奖品如下' +
            'A:最高人气将获得 奖励（打入你的跨次元平台账户） B:所有参加的会员均获得一张' +
            '（活动结束后发放到跨次元账户中，可以免费抽取精美水印）',
        is_link: '1',
    },
];

const NUM_ROWS = 3;

class myCourse extends Component {
    constructor(props) {
        super(props)
        // 创建ListViewDataSource对象
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2 // rowHasChanged(prevRowData, nextRowData); 用其进行数据变更的比较
        })
        this.state = {
            dataSource,
            datas: [],
            pageNo: 1,
            pageSize: 3,
            hasMore: true,
            refreshing: true,
            isLoading: true,
            dataArr: [],
            height: document.documentElement.clientHeight - 60,
        }
    }

    componentDidMount() {
        this.getData(true)
    }

    getData(ref = false) {
        //获取数据
        var para = {}
        para.pageSize = this.state.pageSize
        para.pageNo = this.state.pageNo

        var dataList;
        // 获取数据
        if(para.pageNo >= NUM_ROWS){
            dataList = [];
        }else{
            dataList = data;
        }

        const len = dataList.length
        console.log('Debug: len: ' + len);
        if (len <= 0) { // 判断是否已经没有数据了
            this.setState({
                refreshing: false,
                isLoading: false,
                hasMore: false
            })

            Toast.info('没有数据了~', 1)
            return false
        }
        if (ref) {
            //这里表示刷新使用
            // 下拉刷新的情况，重新添加数据即可(这里等于只直接用了第一页的数据)
            this.setState({
                pageNo: this.state.pageNo,
                dataSource: this.state.dataSource.cloneWithRows(dataList), // 数据源中的数据本身是不可修改的,要更新datasource中的数据，请（每次都重新）调用cloneWithRows方法
                hasMore: true, // 下拉刷新后，重新允许开下拉加载
                refreshing: false, // 是否在刷新数据
                isLoading: false, // 是否在加载中
                dataArr: dataList // 保存数据进state，在下拉加载时需要使用已有数据
            })
        } else {
            // 这里表示上拉加载更多
            // 合并state中已有的数据和新增的数据
            var dataArr = this.state.dataArr.concat(dataList) //关键代码
            this.setState({
                pageNo: this.state.pageNo,
                dataSource: this.state.dataSource.cloneWithRows(dataArr), // 数据源中的数据本身是不可修改的,要更新datasource中的数据，请（每次都重新）调用cloneWithRows方法
                refreshing: false,
                isLoading: false,
                dataArr: dataArr // 保存新数据进state
            })
        }
    }

    // 下拉刷新
    onRefresh = () => {
        this.setState({
            refreshing: true,
            isLoading: true,
            pageNo: 1 // 刷新嘛，一般加载第一页，或者按你自己的逻辑（比如每次刷新，换一个随机页码）
        }, ()=>{
            this.getData(true)
        })
    }

    // 滑动到底部时加载更多
    onEndReached = (event) => {
        // 加载中或没有数据了都不再加载
        if (this.state.isLoading || !this.state.hasMore) {
            return
        }
        this.setState({
            isLoading: true,
            pageNo: this.state.pageNo + 1, // 加载下一页
        }, ()=> {
            this.getData(false)
        })

    }

    render() {
        const row = (rowData, sectionID, rowID) => {
            // 这里rowData,就是上面方法cloneWithRows的数组遍历的单条数据了，直接用就行
            return (
                <div key={rowID} style={{backgroundColor: '#222'}}>
                    <div
                        style={{
                            width: '100%',
                            margin: '0',
                            padding: '2px 18px',
                            textAlign: 'start',
                            height: '60px',
                            verticalAlign: 'middle',
                            backgroundColor: '#000',
                            color: '#FFF',
                            position: 'relative'
                        }}
                    >
                        <img style={{width:'36px', height:'36px', marginTop:'12px', alignContent: 'start'}} src={rowData.img} alt="img"/>
                        <div style={{display: 'inline-block', fontSize: '17px', marginLeft: '10px', verticalAlign: 'top',  lineHeight: '60px', width: 'fit-content'}}>{rowData.title}</div>
                        <div style={{display: 'inline-block', marginLeft: '5px', height: '40px', verticalAlign: 'middle'}}>
                            <span style={{backgroundColor: '#555', fontSize: '12px', color: '#aaa'}}>{rowData.is_official==='1' ? '官方' : ''}</span>
                        </div>
                        <div style={{display: 'inline-block', fontSize: '14px', color: '#555', position: 'absolute', right: '18px', lineHeight: '60px'}}>{rowData.time}</div>
                    </div>

                    <div
                        style={{
                            backgroundColor: '#000',
                            color: '#FFF',
                            textAlign: 'start',
                            padding: '2px 18px',
                            paddingBottom: '25px'
                        }}
                    >
                        <p style={{lineHeight: '25px', fontSize: '15px', color: '#FFF'}}>{rowData.content}</p>
                        <div style={{height: '25px', textAlign: 'right', marginTop: '8px', verticalAlign: 'middle', display: rowData.is_link==='1'?'block':'none'}}>
                            <div style={{display: 'inline-block', verticalAlign: 'top', lineHeight: '25px', marginRight: '10px'}}>立即参与</div>
                            <img style={{height: '19px', width: '17px', marginTop: '4px'}} src={rightArrowIcon} alt="img"/>
                        </div>
                    </div>
                    <WhiteSpace size="xs" />
                </div>
            );
        }
        return (
            /*
            <ListView
                ref={el => this.lv = el}
                dataSource={this.state.dataSource}

                // renderHeader={() =>  (<Header title='消息' theme={{ mode: 'dark' }} />) }
                renderFooter={() => (<div className="footer">{this.state.isLoading ? '加载中...' : '暂无更多数据'}</div>)}
                renderRow={row}
                useBodyScroll
                pullToRefresh={<PullToRefresh
                    refreshing={this.state.refreshing}
                    onRefresh={this.onRefresh}
                />}
                onEndReachedThreshold={6}
                onEndReached={this.onEndReached}
                pageSize={3}
            />

             */
            <ListView
                ref={el => this.lv = el}
                dataSource={this.state.dataSource}
                renderFooter={() => (<div className="footer">{this.state.isLoading ? '加载中...' : '暂无更多数据'}</div>)}
                renderBodyComponent={() => <MyBody />}
                renderRow={row}
                style={{
                    height: this.state.height,
                    overflow: 'auto',
                }}
                pullToRefresh={<PullToRefresh
                    refreshing={this.state.refreshing}
                    onRefresh={this.onRefresh}
                />}
                pageSize={3}
                onEndReached={this.onEndReached}
                onEndReachedThreshold={6}
            />
        );
    }
}

export default myCourse;

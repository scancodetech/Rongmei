import React from "react";
import { api } from "../../services/api/ApiProvider";
import "./playground.css";
import like from "../../assets/like.png";
import likeRed from "../../assets/like-red.png";
import share from "../../assets/share.png";
import logo from "../../assets/logo.png";
import link from "../../assets/link.png";
import warn from "../../assets/warn.png";
import commentIcon from "../../assets/comment.svg";
import commentlikeIcon from '../../assets/commentLike.svg'
import commentlikeSIcon from '../../assets/commentLikeS.svg'
import reloadIcon from '../../assets/reload.svg'
import history from '../../assets/searchHistory.svg'

import inviteIcon from '../../assets/@.svg'

import { withRouter } from "react-router-dom";
import { ActionSheet, List, Modal, Toast, ActivityIndicator, Flex, ListView, Card, Icon, InputItem, SearchBar, Tabs, Button } from "antd-mobile";
import { copyToClip, formatDateToSecond } from "../../utils/utils";

import ReactDOM from 'react-dom'

@withRouter
class Playground extends React.Component {
    accountService = api.accountService;
    auctionService = api.auctionService;
    recommendService = api.recommendService;
    relationService = api.relationService;
    metricsService = api.metricsService;
    examService = api.examService;

    constructor(props) {
        super(props)
        this.state = {
            page: 1,
            limit: 10,
            offset: 0,
            data: [],
            index: 0,
            recommend: null,

            isLike: false,
            likeNum: 0,
            shareNum: 0,

            y: 0,
            // 热门搜索推荐
            hotSearch: ['在家遛孩子', '国风大设', '水印', '二次元世界'],
            // 搜索历史
            searchHistory: [],
            // 是否显示全部搜索记录
            isAll: false,
            // 搜索框内容
            searchValue: '',
            // 显示搜索结果
            showSearchResult: false,
            SearchResult: new ListView.DataSource({ rowHasChanged: (r1, r2) => r1 !== r2 }),
            tabPage: 0,
            loadedImages: 0,
            isShareModalVisible: false,
            // 设置加载动画
            animating: false,
            // 更多简介对话框
            showMoreIntroModal: false,
            // 搜索对话框
            searchModal: false,
            // 评论对话框
            commentModal: false,
            comments: [],
            commentData: new ListView.DataSource({
                rowHasChanged: (r1, r2) => {
                    console.log(r1 !== r2)
                    return r2 !== r1 || r1.replyComment !== r2.replyComment
                }
            }),
            commentsNum: 0,
            toNickname: '',
            toUsername: '',
            toCommentId: 0,
            // 用户评论的内容
            commentValue: '',
            // 加载二级评论
            loadSecondaryCommentsId: '',
        };
    }

    async componentWillMount() {

        let sI = localStorage.getItem('scrollInfo')
        if (sI) {
            sI = JSON.parse(sI);
            await this.setState({
                index: sI.index - 1,
                page: sI.page,
                limit: sI.limit,
            }, () => console.log(`index,page,limit`, this.state.index, this.state.page, this.state.limit))

            this.setState({
                animating: true
            })
            let res = await this.recommendService.getRecommend(this.state.page, 10, 0, 0)
            let recommendItemList = res.recommendItemList;
            if (recommendItemList.length > 0) {
                let newIndex = this.state.index + 1;
                let recommend = recommendItemList.length > 0 ? recommendItemList[0] : this.state.recommend;
                // let newData = this.state.data.concat(recommendItemList);
                let newData=recommendItemList
                let imageForPreload = [];
                for (let i = 0; i < recommendItemList.length; i++) {
                    imageForPreload = imageForPreload.concat(recommendItemList[i].coverUrl);
                }
                await this.preLoadImg(imageForPreload)

                while (this.state.loadedImages.length <= 0) {
                    // wait for loading
                }
                console.log(recommendItemList)
                await this.setState({
                    index: newIndex,
                    recommend,
                    data: newData,
                    animating: false
                })
            } else {
                this.setState({
                    animating: false
                })
                Toast.info('没有更多内容了。', 1);
            }
            let recommend = this.state.data[sI.index];
            await this.setState({
                recommend,
            }, () => console.log(this.state.recommend))
        }
        else {
            this.getRecommend();
        }
        this.updateLike();
        this.updateShare();
        this.loadCommentsData();

        // this.nextItem()
        // 是否完成过注册
        try {
            const userInfoRes = await this.accountService.getUserInfo();
            if (userInfoRes.avatarUrl === '' && userInfoRes.nickname === '' && userInfoRes.birthday === 0) {
                this.props.history.push('/register')
            }
            else {
                // 这里查看是否通过考试
                try {
                    const userExamRes = await this.examService.getUserExam();
                    if (userExamRes.isPass === false || null) {
                        this.props.history.push("/me")
                    }
                }
                catch (err) {
                    this.props.history.push("/me")
                }
            }
        } catch (err) {
            console.log(err)
        }
    }

    componentDidMount() {
        const noscrolldiv = document.getElementById('noscroll');
        let truediv = ReactDOM.findDOMNode(noscrolldiv)
        truediv.addEventListener('touchmove', e => {
            e.preventDefault();
        }, { passive: false })
        this.setState({ searchHistory: JSON.parse(localStorage.getItem('searchHistory'))||[] })

        // 模拟数据
        this.setState({
            SearchResult: this.state.SearchResult.cloneWithRows([
                { type: 'account', nickname: '二次元世界元世界', fansNum: '1000', account: '123456', avatarUrl: 'https://picsum.photos/200/300?random=1' },
                { type: 'topic', title: '你滴不懂', quoteNum: '1000', avatarUrl: 'https://picsum.photos/200/200?random=2' },
                { type: 'account', nickname: '你滴不懂', fansNum: '1000', account: '763443702@qq.com', avatarUrl: 'https://picsum.photos/200/200?random=3' }])
        })
    }

    // 保存浏览位置信息
    setScrollInfo() {
        let scrollInfo = {
            index: this.state.index,
            page: this.state.page,
            limit: this.state.limit,
        }
        console.log('scrollInfo', scrollInfo)
        localStorage.setItem('scrollInfo', JSON.stringify(scrollInfo))
    }
    async getRecommend() {
        this.setState({
            animating: true
        })
        let startFromId = 0;
        let startFromType = 0;
        if (this.props.location.search.length > 0) {
            let params = this.props.location.search.split('&')
            startFromId = params[0].split('startFromId=')[1]
            startFromType = params[1].split('startFromType=')[1]
        }
        // let res = await this.recommendService.getRecommend(this.state.page, this.state.limit, 0, 1)
        try {
            let res = await this.recommendService.getRecommend(this.state.page, this.state.limit, startFromId, startFromType)
            let recommendItemList = res.recommendItemList;
            console.log('recommendItemList',recommendItemList)
            if (recommendItemList.length > 0) {
                // let newIndex = this.state.index + 1;
                let recommend = recommendItemList.length > 0 ? recommendItemList[0] : this.state.recommend;
                // let newData = this.state.data.concat(recommendItemList);
                let newData=recommendItemList
                let imageForPreload = [];
                for (let i = 0; i < recommendItemList.length; i++) {
                    imageForPreload = imageForPreload.concat(recommendItemList[i].coverUrl);
                }
                await this.preLoadImg(imageForPreload)

                while (this.state.loadedImages.length <= 0) {
                    // wait for loading
                }
                console.log(recommendItemList)
                await this.setState({
                    // index: newIndex,
                    recommend,
                    data: newData,
                    animating: false
                })
            } else {
                this.setState({
                    animating: false,
                })
                Toast.info('没有更多内容了。', 1);
            }
        }
        catch (err) {
            this.setState({
                animating: false,
            })
            Toast.info('没有更多内容了。', 1);
        }
    }

    // 图片预加载
    async preLoadImg(coverUrlList) {
        const that = this;
        await that.setState({
            loadedImages: 0
        })
        const img = new Image();
        img.onload = async () => {
            img.onload = null;
            await that.setState({
                loadedImages: that.state.loadedImages + 1
            })
        }
        for (let i = 0; i < coverUrlList.length; i++) {
            img.src = coverUrlList[i];
        }
    }

    postLike = async () => {
        const recommendId = this.state.recommend ? this.state.recommend.id : 0;
        try {
            await this.relationService.postLike(recommendId, this.state.recommend.type);
        }
        catch (err) {
            console.log(err)
        }
        await this.updateLike();
    }

    updateLike = async () => {
        const recommendId = this.state.recommend ? this.state.recommend.id : 0;
        // 如果跳转到register页面会报错
        try {
            const res = await this.relationService.getLike(recommendId, this.state.recommend.type);
            this.setState({
                isLike: res.isLike,
                likeNum: res.likeNum
            })
        }
        catch (res) {
            console.log(res)
        }
    }

    postShare = async () => {
        this.openShareModalVisible();
    }
    updateShare = async () => {
        const recommendId = this.state.recommend ? this.state.recommend.id : 0;
        const res = await this.metricsService.getShare(recommendId);
        this.setState({
            shareNum: res.count
        })
    }

    async nextItem() {
        if (this.state.index !== 9) {
            let newIndex = this.state.index + 1;
            let recommend = this.state.data[newIndex];
            await this.setState({
                index: newIndex,
                recommend,
            })
        } else {
            await this.setState({
                page: this.state.page + 1,
                index: 0
            })
            await this.getRecommend();
        }
        this.updateLike();
        this.updateShare();
        this.loadCommentsData();
        this.setScrollInfo()
    }

    async lastItem() {
        if (this.state.index > 0) {
            let newIndex = this.state.index - 1;
            let recommend = this.state.data[newIndex];
            await this.setState({
                index: newIndex,
                recommend,
            })
        }
        else if (this.state.index === 0 && this.state.page !== 1) {
            let newIndex = 9;
            let recommend = this.state.data[newIndex];
            await this.setState({
                index: newIndex,
                recommend,
                page: this.state.page - 1
            }, () => this.getRecommend())

        }
        this.updateLike();
        this.updateShare();
        this.loadCommentsData();
        this.setScrollInfo();
    }

    toSale(id) {
        this.props.history.push(`sale/${id}`);
    }

    toCommodity(id) {
        this.props.history.push(`commodity/${id}`);
    }

    actionList = [
        {
            icon: <img src={link} alt={"复制链接"} style={{ width: 36 }} />,
            title: "复制链接",
            action: async () => {
                if (this.state.recommend.type === 0) {
                    copyToClip("https://m.dimension.pub/#/picmobile/commodity/" + this.state.recommend.id);
                    Toast.success("链接已复制");
                    await this.metricsService.addShare(this.state.recommend.id, "");
                    await this.updateShare();
                } else if (this.state.recommend.type === 1) {
                    copyToClip("https://m.dimension.pub/#/picmobile/sale/" + this.state.recommend.id);
                    Toast.success("链接已复制");
                    await this.metricsService.addShare(this.state.recommend.id, "");
                    await this.updateShare();
                } else {
                    copyToClip(`https://m.dimension.pub/#/picmobile/home?startFromId=${this.state.recommend.id}&startFromType=${this.state.recommend.type}`);
                    Toast.success("链接已复制");
                    await this.metricsService.addShare(this.state.recommend.id, "");
                    await this.updateShare();
                }
            }
        },
        {
            icon: <img src={warn} alt={"举报"} style={{ width: 36 }} />,
            title: "举报",
            action: () => {
                if (this.state.recommend) {
                    this.props.history.push(`report/${this.state.recommend.id}`);
                } else {
                    Toast.info("开发中，尽情期待")
                }
            }
        }
    ]

    openShareModalVisible = () => {
        ActionSheet.showShareActionSheetWithOptions({
            options: this.actionList,
            title: '分享',
        },
            (buttonIndex) => {
                if (this.actionList[buttonIndex]) {
                    this.actionList[buttonIndex].action();
                }
            });
    }

    showComment = async () => {
        try {
            await this.setState({
                commentModal: true
            })
            this.commentInput.focus()
        } catch (e) {

        }
    }

    loadCommentsData = async () => {
        try {
            const commentRes = await api.commentService.getComments(this.state.recommend.id, 0, 1, 10000);
            const comments = commentRes.comments;
            for (let i = 0; i < comments.length; i++) {
                comments[i].replyComment = [];
            }
            let commentData = new ListView.DataSource({
                rowHasChanged: (r1, r2) => {
                    console.log(r1 !== r2)
                    return r2 !== r1 || r1.content !== r2.content
                }
            })
            this.setState({
                comments,
                commentData: commentData.cloneWithRows(comments),
                commentsNum: commentRes.total
            })
        } catch (e) {

        }
    }

    // 浅拷贝造成数据不更新的问题 不能直接对浅拷贝的数组进行赋值
    async addReplyComment(rID) {
        try {
            const comments = this.state.comments;
            const commentRes = await api.commentService.getComments(this.state.recommend.id, comments[rID].id, 1, 10000);
            comments[rID].replyComment = commentRes.comments;
            let newCommentData = new ListView.DataSource({
                rowHasChanged: (r1, r2) => {
                    console.log(r1 !== r2)
                    return r2 !== r1 || r1.content !== r2.content
                }
            })
            this.setState({
                commentData: newCommentData.cloneWithRows(comments)
            })
        } catch (e) {

        }
    }

    sendComment = async () => {
        try {
            if (this.state.commentValue.length > 0) {
                let toUsername = this.state.toUsername;
                if (toUsername.length === 0) {
                    toUsername = this.state.recommend.owner
                }
                await api.commentService.sendComment(toUsername, this.state.commentValue, this.state.toCommentId, this.state.recommend.id);
                this.loadCommentsData();
                Toast.success("评论成功")
            }
        } catch (e) {
            console.log(e)
            Toast.fail("评论失败")
        }
    }

    render() {
        return (
            <div id='noscroll' style={{ background: '#000' }} className='playgroundChange'>
                <div style={{
                    width: '100%',
                    top: '0px',
                    position: 'absolute',
                    zIndex: 999,
                    background: '#000',
                }}>
                    <div style={{ position: 'relative' }}>
                        <div style={{
                            // textAlign: 'center',
                            color: '#fff',
                            paddingTop: '20px',
                            paddingBottom: '20px',
                            fontSize: '18px',
                            fontWeight: 'bold',
                            backgroundColor: 'transparent',
                            position: 'absolute',
                            left: '50%',
                            transform: 'translateX(-50%)',
                            // paddingLeft:'100px'
                        }}>推荐
                    </div>
                        <div style={{
                            textAlign: 'right',
                            color: '#fff',
                            fontSize: '18px',
                            fontWeight: 'bold',
                            // background: '#000',
                            backgroundColor: 'transparent',
                            position: 'absolute',
                            right: 0,
                            width: 'auto',
                            padding: '18px 10px'
                        }}
                            onClick={() => { this.setState({ searchModal: true }, () => this.autoFocusInst.focus()) }}
                        >
                            <Icon type='search' size='sm' />
                        </div>
                    </div>

                </div>
                <div
                    onTouchStart={(e) => {
                        this.setState({
                            y: e.changedTouches[0].clientY
                        })
                    }}
                    onTouchEnd={(e) => {
                        if (e.changedTouches[0].clientY - this.state.y <= -100) {
                            this.nextItem();
                        } else if (e.changedTouches[0].clientY - this.state.y >= 100) {
                            this.lastItem();
                        }
                    }}>
                    <List>
                        {
                            this.state.recommend ? (<List.Item style={{
                                height: window.innerHeight - 50,
                                width: window.innerWidth,
                                backgroundColor: '#000',
                                backgroundImage: `url(${this.state.recommend.coverUrl})`,
                                backgroundRepeat: 'no-repeat',
                                backgroundPositionX: 'center',
                                backgroundPositionY: 'center',
                                backgroundSize: 'contain',
                            }}
                            >
                                <div style={{
                                    position: 'absolute',
                                    top: '0px',
                                    left: '0px',
                                    width: window.innerWidth - 70,
                                    height: window.innerHeight - 100,
                                    zIndex: 999
                                }}
                                    onClick={() => {
                                        if (this.state.recommend.type === 0) {
                                            this.toCommodity(this.state.recommend.id)
                                        } else if (this.state.recommend.type === 1) {
                                            this.toSale(this.state.recommend.id)
                                        }
                                    }}
                                >
                                </div>
                                <div style={{ position: 'absolute', right: '10px', zIndex: 999 }}>
                                    <div onClick={() => {
                                        this.props.history.push(`/other/${this.state.recommend.owner}`)
                                    }}>
                                        <img style={{
                                            width: '40px',
                                            height: '40px',
                                            borderRadius: '20px',
                                            marginBottom: '15px',
                                            objectFit: 'cover'
                                        }}
                                            src={!this.state.recommend.ownerAvatarUrl || this.state.recommend.ownerAvatarUrl.length === 0 ? logo : this.state.recommend.ownerAvatarUrl} />
                                    </div>
                                    <div style={{ marginBottom: '15px' }} onClick={() => this.postLike()}>
                                        {
                                            this.state.isLike ?
                                                <img style={{ width: '40px', height: '40px' }} src={likeRed} /> :
                                                <img style={{ width: '40px', height: '40px' }} src={like} />
                                        }
                                        <p style={{
                                            color: '#fff',
                                            textAlign: 'center',
                                            textShadow: '1px 1px 5px #333'
                                        }}>{this.state.likeNum}</p>
                                    </div>
                                    <div style={{ marginBottom: '15px' }}
                                        onClick={() => this.showComment()}>
                                        <img style={{ width: 40, height: 40 }} alt='' src={commentIcon} />
                                        <p style={{
                                            color: '#fff',
                                            textAlign: 'center',
                                            textShadow: '1px 1px 5px #333'
                                        }}>{this.state.commentsNum}</p>
                                    </div>
                                    <div onClick={() => this.postShare()}>
                                        <img style={{ width: '40px', height: '40px' }}
                                            src={share} />
                                        <p style={{
                                            color: '#fff',
                                            textAlign: 'center',
                                            textShadow: '1px 1px 5px #333'
                                        }}>{this.state.shareNum}</p>
                                    </div>
                                </div>
                                <div
                                    style={{
                                        position: 'absolute',
                                        bottom: '10px',
                                        height: "auto",
                                        visibility: !this.state.showMoreIntroModal ? 'visible' : 'hidden'
                                    }}>
                                    <Flex direction='column' justify='start' align='start' wrap='wrap'
                                        style={{ height: 'auto' }}>

                                        <Flex justify='start' align='center' style={{ backgroundColor: 'white', borderRadius: 3, height: 20, padding: '2px 5px', fontSize: 12, fontWeight: 600 }}>
                                            {
                                                (() => {
                                                    switch (5) {
                                                        case 0: return (
                                                            <Flex style={{ fontSize: 12 }}>
                                                                <div style={{ paddingRight: 5, borderRight: '1px solid black', marginRight: 5 }}>进行中</div>
                                                                <div>当前价格：{<span style={{ color: "#FE2341" }}>{`102电子`}</span>}</div>
                                                            </Flex>
                                                        )
                                                        case 1: return (
                                                            <div style={{ color: '#FF7E00', fontSize: 12, padding: '0 5px' }}>完售感谢</div>
                                                        )
                                                        case 2: return (
                                                            <div style={{ padding: '0 5px' }}>预告中...</div>
                                                        )
                                                        case 5: return (
                                                            <div>售价：{<span style={{ color: "#FE2341" }}>{`9.9电子`}</span>}</div>
                                                        )
                                                    }
                                                })()
                                            }
                                        </Flex>

                                        <div style={{ fontSize: 16, fontWeight: 'bold', color: '#fff' }}>
                                            {(!this.state.recommend.title || this.state.recommend.title === '' || this.state.recommend.title.length === 0) ? '暂无标题' : this.state.recommend.title}
                                        </div>
                                        <Flex direction='column' align='start' style={{
                                            height: 'auto',
                                            // lineHeight: '30px',
                                            color: 'white',
                                            // minHeight: 60,
                                            width: 'calc(100VW - 30px)'
                                        }}>
                                            <div>
                                                {
                                                    this.state.recommend.topics.map((topic) => (
                                                        <span style={{ color: 'white', fontSize: 18, fontWeight: 700 }} onClick={() => {
                                                            this.props.history.push(`/topicdetail/${topic}`)
                                                        }}>
                                                            #{topic}
                                                        </span>
                                                    ))
                                                }
                                            </div>
                                            <Flex justify='start' style={{ width: '100%' }}>
                                                <p className='introText'>
                                                    {(!this.state.recommend.description || this.state.recommend.description === '' || this.state.recommend.description.length === 0) ? '暂无简介' : this.state.recommend.description}
                                                </p>
                                                {
                                                    this.state.recommend.description.length > 18 &&
                                                    < p style={{ color: '#AAAAAA', fontSize: 17, }}
                                                        onClick={() => this.setState({ showMoreIntroModal: true })}>更多</p>
                                                }
                                            </Flex>
                                        </Flex>
                                    </Flex>
                                </div>
                            </List.Item>) : null
                        }
                    </List>
                </div>
                <ActivityIndicator
                    toast
                    text="正在加载..."
                    animating={this.state.animating}
                />
                {/* 全部简介对话框 */}
                <Modal
                    visible={this.state.showMoreIntroModal}
                    maskClosable
                    // transparent
                    onClose={() => this.setState({ showMoreIntroModal: false })}
                    style={{ width: '100%', height: 'auto', position: 'absolute', bottom: 0 }}
                    wrapClassName='showMoreIntroWrapChange'
                    className='showMoreIntroChange'
                >
                    {
                        this.state.recommend ? <div>
                            <Flex direction='column' justify='start' align='start' wrap='wrap'
                                style={{ height: 'auto', width: "100%", padding: '10px 15px' }}>
                                <div style={{ fontSize: 18, fontWeight: 'bold', color: '#fff' }}>
                                    {(!this.state.recommend.title || this.state.recommend.title === '' || this.state.recommend.title.length === 0) ? '暂无标题' : this.state.recommend.title}
                                </div>
                                <Flex wrap='wrap' align='start' style={{
                                    height: 'auto',
                                    // lineHeight: '30px',
                                    color: 'white',
                                    // minHeight: 60,
                                    width: 'calc(100VW - 30px)'
                                }}>
                                    {
                                        this.state.recommend.topics.map((topic) => (
                                            <span style={{ color: '#fe2341', fontWeight: 700 }} onClick={() => {
                                                this.props.history.push(`/topicdetail/${topic}`)
                                            }}>
                                                #{topic}
                                            </span>
                                        ))
                                    }
                                    <div style={{ overflowY: 'scroll', marginBottom: 30 }}>
                                        <p style={{
                                            width: '100%',
                                            wordBreak: 'break-all',
                                            height: 'auto',
                                            maxHeight: 200,
                                            fontWeight: 600
                                        }}>
                                            {(!this.state.recommend.description || this.state.recommend.description === '' || this.state.recommend.description.length === 0) ? '暂无简介' : this.state.recommend.description}
                                        </p>
                                    </div>
                                    <div style={{
                                        height: 25,
                                        position: 'absolute',
                                        bottom: 15,
                                        right: 15,
                                        color: '#AAAAAA',
                                        fontWeight: 600
                                    }} onClick={() => this.setState({ showMoreIntroModal: false })}>收起
                                        </div>

                                </Flex>

                            </Flex>

                        </div>
                            : null
                    }

                </Modal>
                {/* 评论对话框 */}
                <Modal
                    // visible={true}
                    visible={this.state.commentModal}
                    // transparent
                    style={{ width: '100%', height: '75%', position: 'absolute', bottom: 0 }}
                    closable
                    onClose={() => this.setState({ commentModal: false })}
                    title={<div style={{ padding: "5px 0", fontSize: 15 }}>{`${this.state.commentsNum}条评论`}</div>}
                    className='homeCommentModal'

                >
                    <div>
                        <ListView
                            dataSource={this.state.commentData}
                            style={{ height: 400 }}
                            onEndReached={() => console.log('endreach')}
                            onScroll={() => console.log('scroll')}
                            renderRow={(dataItem, sID, rID) =>
                                <Card full>
                                    <Card.Header
                                        thumb={
                                            <div
                                                style={{ display: 'flex', justifyContent: "center", alignSelf: 'start' }}>
                                                <img alt='' style={{ height: 35, width: 35, borderRadius: '50%' }}
                                                    src={dataItem.fromAvatarUrl} />
                                            </div>
                                        }
                                        title={
                                            <div style={{ display: 'flex', alignItems: 'start', width: '100%' }}>
                                                <Flex align='start' justify='start' direction='column'
                                                    style={{ width: '90%' }}>
                                                    <span style={{
                                                        color: '#666666',
                                                        fontSize: 12,
                                                        fontWeight: 600
                                                    }}>{dataItem.fromNickname.length === 0 ? dataItem.fromUsername : dataItem.fromNickname}</span>
                                                    <div style={{
                                                        fontSize: 12,
                                                        width: '100%',
                                                        minHeight: 18
                                                    }}>{dataItem.content}</div>
                                                </Flex>
                                                <Flex direction='column' justify='center' align='center'
                                                    style={{ width: '10%' }} onClick={() => console.log('点赞操作')}>
                                                    <img alt='' style={{ width: 15, height: 15, }}
                                                        src={dataItem.isLike ? commentlikeSIcon : commentlikeIcon} />
                                                    <span style={{ fontSize: 12 }}>{dataItem.likeNum}</span>
                                                </Flex>
                                            </div>
                                        }
                                    />
                                    <Card.Body>
                                        <Flex direction='column' justify='start' align='start'>
                                            <Flex style={{ width: '50%' }} justify='between'>
                                                <div
                                                    style={{ color: '#999999' }}>{formatDateToSecond(dataItem.createTime)}</div>
                                                <span style={{ color: '#666666', fontWeight: 600 }}
                                                    onClick={() => {
                                                        this.setState({
                                                            toNickname: dataItem.fromNickname.length > 0 ? dataItem.fromNickname : dataItem.fromUsername,
                                                            toUsername: dataItem.fromUsername,
                                                            toCommentId: dataItem.id
                                                        })
                                                        this.commentInput.focus()
                                                    }}>回复</span>
                                            </Flex>
                                            {
                                                dataItem.replyNum > 0 &&
                                                <Flex justify='start' align='start' direction='column'
                                                    style={{ width: '100%' }}>
                                                    {/* 展开二级回复按钮 */}
                                                    <Flex onClick={() => this.addReplyComment(rID)}>
                                                        <span style={{
                                                            fontSize: 12,
                                                            color: '#AAAAAA',
                                                        }}>{`—— 展开${dataItem.replyNum}条回复`}</span>
                                                        <Icon type='down' size='sm'
                                                            style={{ lineHeight: "25px", paddingLeft: 10 }} />
                                                    </Flex>
                                                    <Flex style={{ width: '100%' }} direction='column' align='start'>
                                                        {

                                                            dataItem.replyComment.map((item, index) =>
                                                                <Card full style={{ width: '100%' }} key={index}>
                                                                    <Card.Header
                                                                        thumb={
                                                                            <div style={{
                                                                                display: 'flex',
                                                                                justifyContent: "center",
                                                                                alignSelf: 'start'
                                                                            }}>
                                                                                <img alt='' style={{
                                                                                    height: 35,
                                                                                    width: 35,
                                                                                    borderRadius: '50%'
                                                                                }} src={item.fromAvatarUrl} />
                                                                            </div>
                                                                        }
                                                                        title={
                                                                            <div style={{
                                                                                display: 'flex',
                                                                                alignItems: 'start',
                                                                                width: '100%'
                                                                            }}>
                                                                                <Flex align='start' justify='start'
                                                                                    direction='column'
                                                                                    style={{ width: '90%' }}>
                                                                                    <Flex>
                                                                                        <span style={{
                                                                                            color: '#666666',
                                                                                            fontSize: 12,
                                                                                            fontWeight: 600
                                                                                        }}>{item.fromNickname.length > 0 ? item.fromNickname : item.fromUsername}</span>
                                                                                        <Icon type='right' size='sm'
                                                                                            style={{ lineHeight: "25px" }} />
                                                                                        <span style={{
                                                                                            color: '#666666',
                                                                                            fontSize: 12,
                                                                                            fontWeight: 600
                                                                                        }}>{item.toNickname.length > 0 ? item.toNickname : item.toUsername}</span>
                                                                                    </Flex>
                                                                                    <div style={{
                                                                                        fontSize: 12,
                                                                                        width: '100%',
                                                                                        minHeight: 18
                                                                                    }}>{item.content}</div>
                                                                                </Flex>
                                                                                <Flex direction='column'
                                                                                    justify='center' align='center'
                                                                                    style={{ width: '10%' }}
                                                                                    onClick={() => console.log('点赞操作')}>
                                                                                    <img alt='' style={{
                                                                                        width: 15,
                                                                                        height: 15,
                                                                                    }}
                                                                                        src={item.isLike ? commentlikeSIcon : commentlikeIcon} />
                                                                                    <span
                                                                                        style={{ fontSize: 12 }}>{item.likeNum}</span>
                                                                                </Flex>
                                                                            </div>
                                                                        }
                                                                    />
                                                                    <Card.Body>
                                                                        <Flex direction='column' justify='start'
                                                                            align='start'>
                                                                            <Flex style={{ width: '50%' }}
                                                                                justify='between'>
                                                                                <div
                                                                                    style={{ color: '#999999' }}>{formatDateToSecond(dataItem.createTime)}</div>
                                                                                <span style={{
                                                                                    color: '#666666',
                                                                                    fontWeight: 600
                                                                                }} onClick={() => {
                                                                                    this.setState({
                                                                                        toNickname: dataItem.fromNickname.length > 0 ? dataItem.fromNickname : dataItem.fromUsername,
                                                                                        toUsername: dataItem.fromUsername,
                                                                                        toCommentId: dataItem.id
                                                                                    })
                                                                                    this.commentInput.focus();
                                                                                }}>回复</span>
                                                                            </Flex>
                                                                        </Flex>
                                                                    </Card.Body>
                                                                </Card>
                                                            )
                                                        }
                                                    </Flex>
                                                </Flex>
                                            }
                                        </Flex>
                                    </Card.Body>
                                </Card>
                            }
                        />
                        {/* 评论输入框 */}
                        <Flex style={{
                            position: 'absolute',
                            bottom: 0,
                            borderTop: '1px solid #EEE',
                            height: 60,
                            width: window.innerWidth,
                            left: 0,
                            padding: '5px 10px',
                            boxSizing: 'border-box',
                            background: 'white'
                        }}
                            justify='around'
                        >
                            <div className='commentInputItem'>
                                <InputItem
                                    onChange={(value => this.setState({
                                        commentValue: value
                                    }))}
                                    ref={el => this.commentInput = el}
                                    placeholder={this.state.toUsername.length === 0 ? '留下你的精彩评论' : `回复@${this.state.toNickname}`}
                                />
                            </div>
                            {/*<img alt='' style={{width: 30, height: 30}} src={inviteIcon}/>*/}
                            <span style={{ color: this.state.commentValue.length > 0 ? '#fe2341' : '#AAA' }}
                                onClick={() => this.sendComment()}>回复</span>
                        </Flex>
                    </div>
                </Modal>
                <Modal
                    visible={this.state.searchModal}
                    // className='PlaygroundSearchModal'
                    popup
                    animationType='slide-down'
                    wrapClassName='PlaygroundSearchModal'
                // onClose={() => this.setState({ isAll: false })}
                >
                    <div style={{ padding: '10px', backgroundColor: '#111' }}>
                        <SearchBar
                            value={this.state.searchValue || ''}
                            onChange={value => this.setState({ searchValue: value })}
                            ref={ref => this.autoFocusInst = ref}
                            placeholder="搜索"
                            onSubmit={value => {
                                let history = JSON.parse(localStorage.getItem('searchHistory')) || [];
                                if (!history.includes(value)) {
                                    // 最多记录10个搜索记录
                                    if (history.length >= 10) {
                                        history.shift()
                                    }
                                    history.push(value)
                                    this.setState({ searchHistory: history })
                                    localStorage.setItem('searchHistory', JSON.stringify(history))
                                }
                                this.setState({
                                    showSearchResult: true
                                })
                            }
                            }
                            onClear={value => console.log(value, 'onClear')}
                            onFocus={() => this.setState({ isAll: false, showSearchResult: false })}
                            onBlur={() => console.log('onBlur')}
                            onCancel={() => this.setState({ searchModal: false, isAll: false, showSearchResult: false })}
                            showCancelButton
                        />
                    </div>

                    {
                        this.state.showSearchResult ?
                            <div>
                                <Tabs tabs={[{ title: '综合' }, { title: '用户' }, { title: '话题' }]}
                                    tabBarBackgroundColor='#111'
                                    tabBarActiveTextColor='#FFF'
                                    tabBarUnderlineStyle={{ border: '1px solid #FE2341' }}
                                    page={this.state.tabPage}
                                    onTabClick={(tab, index) => this.setState({ tabPage: index })}
                                >
                                    <div>
                                        <ListView
                                            dataSource={this.state.SearchResult}
                                            useBodyScroll
                                            style={{ backgroundColor: 'transparent' }}

                                            renderRow={(dataItem) =>
                                                this.state.tabPage === 0 ?
                                                    dataItem.type === 'account' ?
                                                        <Card full>
                                                            <Card.Header
                                                                thumb={
                                                                    <img alt='' src={dataItem.avatarUrl} style={{ width: 60, height: 60, borderRadius: '50%', objectFit: 'cover', }} />
                                                                }
                                                                title={
                                                                    <Flex direction='column' justify='around' style={{ color: "#AAAAAA", fontSize: 12, paddingLeft: 10 }} align='start'>
                                                                        <div style={{ color: "white", fontSize: 15 }}>{dataItem.nickname}</div>
                                                                        <div style={{}}>{`粉丝：${dataItem.fansNum}`}</div>
                                                                        <div>{`账号：${dataItem.account}`}</div>
                                                                    </Flex>
                                                                }
                                                                extra={
                                                                    <Flex>
                                                                        <div className='followBtn'>
                                                                            <Button>关注</Button>
                                                                        </div>
                                                                    </Flex>
                                                                }
                                                            />
                                                        </Card>
                                                        :
                                                        <Card full>
                                                            <Card.Header
                                                                thumb={
                                                                    <img alt='' src={dataItem.avatarUrl} style={{ width: 60, height: 60, borderRadius: 5, objectFit: 'cover', }} />
                                                                }
                                                                title={
                                                                    <Flex direction='column' justify='around' style={{ color: "#AAAAAA", fontSize: 12, paddingLeft: 10 }} align='start'>
                                                                        <div style={{ color: "white", fontSize: 15 }}>{`#${dataItem.title}`}</div>
                                                                        <div style={{}}>{`${dataItem.quoteNum}次引用`}</div>
                                                                    </Flex>
                                                                }
                                                            />
                                                        </Card>
                                                    :
                                                    this.state.tabPage === 1 ?
                                                        dataItem.type === 'account' ?
                                                            <Card full>
                                                                <Card.Header
                                                                    thumb={
                                                                        <img alt='' src={dataItem.avatarUrl} style={{ width: 60, height: 60, borderRadius: '50%', objectFit: 'cover', }} />
                                                                    }
                                                                    title={
                                                                        <Flex direction='column' justify='around' style={{ color: "#AAAAAA", fontSize: 12, paddingLeft: 10 }} align='start'>
                                                                            <div style={{ color: "white", fontSize: 15 }}>{dataItem.nickname}</div>
                                                                            <div style={{}}>{`粉丝：${dataItem.fansNum}`}</div>
                                                                            <div>{`账号：${dataItem.account}`}</div>
                                                                        </Flex>
                                                                    }
                                                                    extra={
                                                                        <Flex>
                                                                            <div className='followBtn'>
                                                                                <Button>关注</Button>
                                                                            </div>
                                                                        </Flex>
                                                                    }
                                                                />
                                                            </Card>
                                                            : null
                                                        :
                                                        dataItem.type === 'topic' ?
                                                            <Card full>
                                                                <Card.Header
                                                                    thumb={
                                                                        <img alt='' src={dataItem.avatarUrl} style={{ width: 60, height: 60, borderRadius: 5, objectFit: 'cover', }} />
                                                                    }
                                                                    title={
                                                                        <Flex direction='column' justify='around' style={{ color: "#AAAAAA", fontSize: 12, paddingLeft: 10 }} align='start'>
                                                                            <div style={{ color: "white", fontSize: 15 }}>{`#${dataItem.title}`}</div>
                                                                            <div style={{}}>{`${dataItem.quoteNum}次引用`}</div>
                                                                        </Flex>
                                                                    }
                                                                />
                                                            </Card>
                                                            : null
                                            }
                                        />
                                    </div>
                                </Tabs>
                            </div>
                            :
                            <div>
                                {
                                    this.state.searchHistory.length > 0 &&
                                    <Flex direction='column' style={{ backgroundColor: '#111', padding: '10px 30px', fontSize: 16, color: "#AAAAAA", fontWeight: 600 }} align='start'>
                                        {
                                            (() => {
                                                let items = [];
                                                for (let i = 0; i < (this.state.isAll ? this.state.searchHistory.length : this.state.searchHistory.length > 3 ? 3 : this.state.searchHistory.length); i++) {
                                                    items.push(
                                                        <Flex style={{ width: '100%', marginBottom: 10 }} key={i} justify='between' align='center' >
                                                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                                                <img alt='' src={history} style={{ width: 16, height: 16, marginRight: 10, display: 'inline-block', }} />
                                                                <div style={{ display: "inline-block" }}>{this.state.searchHistory[i]}</div>
                                                            </div>
                                                            <Icon type='cross' size='md' onClick={() => {
                                                                let newData = this.state.searchHistory
                                                                newData.splice(i, 1)
                                                                this.setState({
                                                                    searchHistory: newData
                                                                }, () => {
                                                                    localStorage.setItem('searchHistory', JSON.stringify(this.state.searchHistory))
                                                                })
                                                            }} />
                                                        </Flex>
                                                    )
                                                }
                                                items.push(
                                                    <Flex justify='center' style={{ width: '100%', fontWeight: 600, fontSize: 15 }}
                                                        onClick={() => {
                                                            if (this.state.isAll || this.state.searchHistory.length <= 3) {
                                                                localStorage.setItem('searchHistory', JSON.stringify(''))
                                                                this.setState({ searchHistory: [] })
                                                            }
                                                            else {
                                                                this.setState({ isAll: true })
                                                            }
                                                        }}
                                                    >{this.state.isAll || this.state.searchHistory.length <= 3 ? '清空全部搜索记录' : '全部搜索记录'}</Flex>
                                                )
                                                return items
                                            })()
                                        }
                                    </Flex>
                                }
                                <Flex direction='column' align='start' style={{ padding: 10, fontSize: 18, color: 'white', fontWeight: 600, backgroundColor: '#111' }}>
                                    <Flex justify='between' style={{ width: '100%', paddingBottom: 10 }}>
                                        <div >猜你想猜</div>
                                        <div style={{ display: 'flex', alignItems: 'center' }} onClick={() => console.log('换一批')}>
                                            <div style={{ fontSize: 15, color: '#666', display: 'inline-block', paddingRight: 5 }}>换一批</div>
                                            <img alt='' src={reloadIcon} style={{ width: 16, height: 16, lineHeight: '22px', alignSelf: 'center' }} />
                                        </div>
                                    </Flex>
                                    <Flex wrap='wrap' style={{ width: '100%', fontWeight: 'normal' }}>
                                        {
                                            this.state.hotSearch.map((item, index) =>
                                                <div style={{ width: "50%", paddingBottom: 15, fontSize: 16 }} key={item}>{item}</div>
                                            )
                                        }
                                    </Flex>
                                </Flex>
                            </div>
                    }



                </Modal>
            </div>
        )
    }
}

export default Playground;

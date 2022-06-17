import React from 'react'
import { withRouter } from 'react-router-dom';
import './style.css'
import Header from '../../components/Header/index'
import { Button, Flex, ImagePicker, Modal, TextareaItem, Toast, WingBlank } from 'antd-mobile';
import { api } from "../../services/api/ApiProvider";
import { uploadFileToCos } from "../../utils/utils";
import lrz from "lrz";

const prompt = Modal.prompt;

@withRouter
class liuhaizi extends React.Component {
    state = {
        hotTopics: [{
            topic: "测试",
            count: 1
        }, {
            topic: "测试",
            count: 1
        }],

        files: [],
        textValue: '',
        topics: [],

        submitting: false
    }

    async componentDidMount() {
        const res = await api.childService.getTopTopics(1, 20);
        this.setState({
            hotTopics: res.topicItems
        })
    }

    onChange = (files, type, index) => {
        console.log(files, type, index);
        this.setState({
            files,
        });
    }

    async submit() {
        if (this.state.files.length === 0) {
            Toast.fail("请先上传图片");
            return;
        }
        try {
            this.setState({
                submitting: true
            })
            Toast.info("正在上传");
            const rst = await lrz(this.state.files[0].file);
            const res = await uploadFileToCos(this.state.files[0].file.name, rst.file);
            if (res.statusCode === 200) {
                await api.childService.updateChild({
                    id: 0,
                    coverUrl: "https://" + res.Location,
                    content: this.state.textValue,
                    topics: this.state.topics
                });
            }
            Toast.success("上传成功");
            this.props.history.goBack()
        } catch (e) {
            alert(e)
            Toast.fail("上传失败");
        }
        this.setState({
            submitting: false
        })
    }

    render() {
        return (
            <div className='liuhaiziPage'>
                <Header title='遛孩子'>
                    <div className='sendBtnChange'>
                        <Button onClick={() => {
                            this.submit()
                        }} disabled={this.state.submitting}>发表</Button>
                    </div>
                </Header>
                <WingBlank size='lg'>
                    <div style={{ width: '100%', minHeight: 175, backgroundColor: '#222222' }}>
                        <Flex justify='start' align='start' style={{ padding: 10 }} wrap='wrap'>
                            <Flex direction='column' style={{ width: '60%' }} align='start'>
                                <TextareaItem className='textArea'
                                    placeholder='写标题并使用合适的话题，能让更多人看到。'
                                    rows={4}
                                    style={{ fontSize: 14 }}
                                    onChange={(value) => {
                                        this.setState({ textValue: value }, () => {
                                            console.log(this.state.textValue)
                                        })
                                    }}
                                />
                            </Flex>

                            <div style={{ height: 125, width: 125 }} className='imagePicker'>
                                <ImagePicker
                                    files={this.state.files}
                                    multiple={false}
                                    length={1}
                                    selectable={this.state.files.length < 1}
                                    onChange={this.onChange}
                                />
                            </div>
                            <Flex wrap='wrap' justify='start'>
                                {
                                    this.state.topics.map((topic, index) => (
                                        <span className='topicTalk'>
                                            {topic}
                                            <div className='topicTalkRemove' data-index={index} onClick={(index) => {
                                                let newTopics = this.state.topics;
                                                let deleteIndex = index.target.getAttribute("data-index")
                                                newTopics.splice(deleteIndex, 1)
                                                // newTopics.pop()
                                                this.setState({
                                                    topics: newTopics
                                                })
                                            }} />
                                        </span>
                                    ))
                                }
                                <span className='addTalk' onClick={() => prompt('话题', '请输入话题',
                                    [
                                        {
                                            text: '取消'
                                        },
                                        {
                                            text: '确认',
                                            onPress: value => {
                                                let newTopics = this.state.topics;
                                                newTopics = newTopics.concat(value.replace(new RegExp("#+", 'g'), ''));
                                                this.setState({
                                                    topics: newTopics
                                                })
                                            }
                                        },
                                    ], 'default', null, ['#话题'])}>
                                    #添加话题
                                    </span>
                            </Flex>
                        </Flex>
                    </div>
                    <div style={{ marginTop: '10px' }}>
                        {
                            this.state.hotTopics.map((topicItem) => (
                                <Flex style={{ marginBottom: '20px', marginTop: '20px' }} onClick={() => {
                                    let newTopics = this.state.topics.concat(topicItem.topic);
                                    this.setState({
                                        topics: newTopics
                                    })
                                }}>
                                    <Flex.Item>
                                        <div style={{
                                            color: "#FFFFFF",
                                            textAlign: 'left',
                                            fontSize: "16px",
                                        }}>#{topicItem.topic}</div>
                                    </Flex.Item>
                                    <Flex.Item>
                                        <div style={{
                                            color: "#666666",
                                            fontSize: "12px",
                                            textAlign: 'right'
                                        }}>{topicItem.count}次引用
                                        </div>
                                    </Flex.Item>
                                </Flex>
                            ))
                        }
                    </div>
                </WingBlank>
            </div>
        )
    }
}

export default liuhaizi

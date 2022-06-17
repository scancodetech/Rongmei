import React from 'react'
import CustomBreadcrumb from "../../components/CustomBreadcrumb";
import TypingCard from "../../components/TypingCard";
import {Card, Table, message, Radio, Button, Modal, Form, Input, Tag, Tooltip} from "antd";
import {api} from '../../services/api/ApiProvider'

class UserGroupPage extends React.Component {
    userGroupService = api.userGroupService;
    columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id'
        }, {
            title: '组名',
            dataIndex: 'title',
            key: 'title',
        }, {
            title: '用户数',
            dataIndex: 'userItems',
            key: 'userItems',
            render: text => <p>{text.length}</p>
        }, {
            title: '组内积分',
            dataIndex: 'largeCoins',
            key: 'largeCoins',
            render: text => <p>{text / 100}</p>
        }, {
            title: '操作',
            render: (text, data) => {
                return <Button onClick={() => this.showModal(data.id)} icon={"edit"} type={"primary"}>编辑</Button>
            },
        }];

    state = {
        loading: false,
        data: [],
        isModalShow: false,

        id: 0,
        title: "",
        usernames: [],
        price: 0,
        inputVisible: false,
        inputValue: ''
    }

    async componentDidMount() {
        try {
            let userGroupItems = (await this.userGroupService.getUserGroups()).userGroupItems;
            this.setState({
                data: userGroupItems
            })
        } catch (e) {
            message.error("加载失败，请检查网络后重试~")
        }
    }

    showModal = async (id) => {
        if (id) {
            const userGroupItem = (await this.userGroupService.getUserGroup(id)).userGroupItem;
            let usernames = [];
            for (let i = 0; i < userGroupItem.userItems.length; i++) {
                usernames.push(userGroupItem.userItems[i].phone)
            }
            this.setState({
                id: userGroupItem.id,
                title: userGroupItem.title,
                userItems: userGroupItem.userItems,
                usernames
            });
        }
        this.setState({
            isModalShow: true,
        });
    };

    submitModal = async () => {
        try {
            console.log(this.state.usernames)
            await this.userGroupService.updateUserGroup({
                id: this.state.id,
                title: this.state.title,
                usernames: this.state.usernames
            })
            message.success("更新成功")
            this.componentDidMount();
            this.setState({
                isModalShow: false,
            });
        } catch (e) {
            message.success("添加失败，请重试~")
        }
    };

    hideModal = () => {
        this.setState({
            isModalShow: false,
        });
    };

    handleClose = (removedTag) => {
        const usernames = this.state.usernames.filter(tag => tag !== removedTag);
        this.setState({usernames});
    }

    showInput = () => {
        this.setState({inputVisible: true}, () => this.input.focus());
    }

    handleInputChange = (e) => {
        this.setState({inputValue: e.target.value});
    }

    handleInputConfirm = () => {
        const state = this.state;
        const inputValue = state.inputValue;
        let usernames = state.usernames;
        if (inputValue && usernames.indexOf(inputValue) === -1) {
            usernames = [...usernames, inputValue];
        }
        this.setState({
            usernames,
            inputVisible: false,
            inputValue: '',
        });
    }

    saveInputRef = input => this.input = input

    render() {
        const {tags, inputVisible, inputValue} = this.state;
        return (
            <div>
                <CustomBreadcrumb arr={['用户组', '列表']}/>
                <TypingCard title={"友情提示"} source={"这里展示用户组的各种信息<br/>现可修改用户组的成员及标题属性<br/>鉴于安全考虑，暂不支持用户组删除"}/>
                <Modal
                    title="添加用户组信息"
                    visible={this.state.isModalShow}
                    onOk={this.submitModal}
                    onCancel={this.hideModal}
                    okText="确认"
                    cancelText="取消"
                >
                    <Form
                    >
                        <Form.Item label="用户组">
                            <Input
                                value={this.state.title}
                                onChange={(e) => {
                                    this.setState({
                                        title: e.target.value
                                    })
                                }} placeholder="请输入组名"/>
                        </Form.Item>
                        <Form.Item label="组用户" name="usernames">
                            <div>
                                {this.state.usernames.map((username, index) => {
                                    const isLongTag = username.length > 20;
                                    const tagElem = (
                                        <Tag key={username} closable afterClose={() => this.handleClose(username)}>
                                            {isLongTag ? `${username.slice(0, 20)}...` : username}
                                        </Tag>
                                    );
                                    return isLongTag ?
                                        <Tooltip title={username} key={username}>{tagElem}</Tooltip> : tagElem;
                                })}
                                {inputVisible && (
                                    <Input
                                        ref={this.saveInputRef}
                                        type="text"
                                        size="small"
                                        style={{width: 150}}
                                        value={inputValue}
                                        onChange={this.handleInputChange}
                                        onBlur={this.handleInputConfirm}
                                        onPressEnter={this.handleInputConfirm}
                                    />
                                )}
                                {!inputVisible &&
                                <Button size="small" type="dashed" onClick={this.showInput}>+ 添加</Button>}
                            </div>
                        </Form.Item>
                    </Form>
                </Modal>
                <Card bordered={false} title='组列表' style={{marginBottom: 10, minHeight: 440}}
                      extra={<Button type="primary" onClick={() => {
                          this.setState({
                              id: 0
                          })
                          this.showModal()
                      }}>添加</Button>}>
                    <Table loading={this.state.loading}
                           dataSource={this.state.data}
                           columns={this.columns} style={styles.tableStyle}/>
                </Card>
            </div>
        )
    }
}

const styles = {
    tableStyle: {
        width: '100%'
    }
}

export default UserGroupPage

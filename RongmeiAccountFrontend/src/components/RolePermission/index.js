import React,{Component} from "react"
import {Modal, Menu, Checkbox, Button} from "antd";
import './index.css'
import { CloseOutlined } from '@ant-design/icons';

const { SubMenu } = Menu;

class RolePermission extends Component{

    constructor(props) {
        super(props);
        this.state = {
            // fake data
            permissionList: [
                [{'title': '首页', 'fatherID': 0, 'itemID': 1, 'index': 0}],
                [
                    {'title': '权限管理', 'fatherID': 0, 'itemID': 2, 'index': 1},
                    {'title': '角色管理', 'fatherID': 2, 'itemID': 3, 'index': 1},
                    {'title': '员工列表', 'fatherID': 2, 'itemID': 4, 'index': 1}
                ],
                [{'title': '平台管理', 'fatherID': 0, 'itemID': 5, 'index': 2}],
                [{'title': '素材追溯', 'fatherID': 0, 'itemID': 6, 'index': 3}],
                [{'title': '组管理', 'fatherID': 0, 'itemID': 7, 'index': 4}],
                [{'title': '财务管理', 'fatherID': 0, 'itemID': 8, 'index': 5}],
                [
                    {'title': '审核', 'fatherID': 0, 'itemID': 9, 'index': 6},
                    {'title': '分区审核', 'fatherID': 9, 'itemID': 10, 'index': 6},
                    {'title': '内容审核', 'fatherID': 9, 'itemID': 11, 'index': 6},
                    {'title': '活动审核', 'fatherID': 9, 'itemID': 12, 'index': 6},
                    {'title': '人员与机构审核', 'fatherID': 9, 'itemID': 13, 'index': 6}
                ],
                [
                    {'title': '考试', 'fatherID': 0, 'itemID': 14, 'index': 7},
                    {'title': '题目', 'fatherID': 14, 'itemID': 15, 'index': 7},
                    {'title': '审核题目', 'fatherID': 14, 'itemID': 16, 'index': 7}
                ],
                [{'title': '话题管理', 'fatherID': 0, 'itemID': 17, 'index': 8}],
                [{'title': '举报', 'fatherID': 0, 'itemID': 18, 'index': 9}]
            ],
            checkList: Array(19).fill(false)
        }

        this.okButtonClick = this.okButtonClick.bind(this);
    }

    checkClick(itemID, fatherID, index){
        this.setState(
            {
                checkList: this.state.checkList.map((item, _index) => _index === itemID ? !item : item)
            },
            () => {
                if(fatherID===0){
                    if(this.state.permissionList[index].length > 1){
                        let sonItemList = [];
                        this.state.permissionList[index].map((sonItem) =>
                            sonItemList.push(sonItem.itemID)
                        )
                        let isFatherChecked = this.state.checkList[itemID];
                        this.setState({
                            checkList: this.state.checkList.map((item, _index) => sonItemList.indexOf(_index) > 0 ? isFatherChecked : item)
                        })
                    }
                }else{
                    this.setState(
                        {
                            checkList: this.state.checkList.map((item, _index) => _index === fatherID ? true : item)
                        }
                    )
                }
            });
    }

    permissionButtonClick(itemID, fatherID, index){
        this.checkClick(itemID, fatherID, index);
    }

    okButtonClick(){
        let selectList = [];
        this.state.permissionList.forEach((item) => {
            if(item.length > 1){
                item.forEach((sonItem) => {
                    if(this.state.checkList[sonItem.itemID]){
                        selectList.push(sonItem.title);
                    }
                })
            }else{
                if(this.state.checkList[item[0].itemID]){
                    selectList.push(item[0].title);
                }
            }
        })
        this.props.modalOK(selectList);
    }

    render() {
        return (
            <Modal
                centered={true}
                width={'60%'}
                visible={this.props.isModalShow}
                closable={false}
                footer={null}
                onOk={this.props.submitModal}
                onCancel={this.props.hideModal}
                okText="确认"
                cancelText="取消"
            >
                <Menu
                    onClick={this.handleClick}
                    mode="inline"
                    style={{width: "50%", height: "480px", overflowY: "auto", overflowX: "hidden", display: "inline-block"}}
                >
                    {
                        this.state.permissionList.map((item, index) => {
                            return (
                                item.length > 1 ? (
                                    <SubMenu
                                        key={item[0].itemID}
                                        fatherID={item[0].fatherID}
                                        itemID={item[0].itemID}
                                        title={
                                            <div>
                                                <Checkbox
                                                    onClick={
                                                        (e) => {
                                                            e.stopPropagation();
                                                            this.checkClick(item[0].itemID, item[0].fatherID, item[0].index);
                                                        }
                                                    }
                                                    disabled={this.props.disabledItem.indexOf(item[0].title) > -1}
                                                    checked={this.state.checkList[item[0].itemID]}
                                                />
                                                <span>&nbsp;&nbsp;{item[0].title}</span>
                                            </div>
                                        }>
                                        {
                                            item.slice(1, item.length).map((sonItem, sonIndex) => {
                                                return (
                                                    <Menu.Item
                                                        key={sonItem.itemID}
                                                        fatherID={sonItem.fatherID}
                                                        itemID={sonItem.itemID}
                                                    >
                                                        <Checkbox
                                                            checked={this.state.checkList[sonItem.itemID]}
                                                            onClick={
                                                                this.checkClick.bind(this, sonItem.itemID, sonItem.fatherID, sonItem.index)
                                                            }
                                                            disabled={this.props.disabledItem.indexOf(sonItem.title) > -1}
                                                            >{sonItem.title}</Checkbox>
                                                    </Menu.Item>
                                                );
                                            })

                                        }
                                    </SubMenu>
                                ) : (
                                    <Menu.Item
                                        key={item[0].itemID}
                                        fatherID={item[0].fatherID}
                                        itemID={item[0].itemID}
                                    >
                                        <Checkbox
                                            checked={this.state.checkList[item[0].itemID]}
                                            onClick={
                                                (e) => {
                                                    this.checkClick(item[0].itemID, item[0].fatherID, item[0].index);
                                                }
                                            }
                                            disabled={this.props.disabledItem.indexOf(item[0].title) > -1}
                                        >{item[0].title}</Checkbox>
                                    </Menu.Item>
                                )
                            );
                        })
                    }
                </Menu>

                <div
                    style={{width: "47%", float:"right", display: "inline-block", verticalAlign: "top"}}
                >
                    <span
                        style={{marginTop: "10px", marginBottom: "15px", display: "inline-block"}}
                    >已选权限</span>
                    <div
                        style={{height: "360px", borderRadius: "10px", border: "1px solid #AAAAAA", paddingTop: "5px", paddingLeft: "5px"}}
                    >
                        {
                           this.props.disabledItem.map((item)=>{
                               return (
                                   <Button
                                       type="primary"
                                       icon={<CloseOutlined/>}
                                       size="small"
                                       style={{margin: '5px 4px'}}
                                       disabled
                                   >
                                       {item}
                                   </Button>
                               );
                           })
                        }
                        {
                            this.state.permissionList.map((item, index) => {
                                return (
                                    item.length > 1 ? (
                                        item.map((sonItem, sonIndex) => {
                                            return (
                                                this.state.checkList[sonItem.itemID] ? (
                                                        <Button
                                                            type="primary"
                                                            icon={<CloseOutlined/>}
                                                            size="small"
                                                            onClick={this.permissionButtonClick.bind(this, sonItem.itemID, sonItem.fatherID, sonItem.index)}
                                                            style={{margin: '5px 4px'}}
                                                        >
                                                            {sonItem.title}
                                                        </Button>
                                                ) : (
                                                    <span/>
                                                )
                                            );
                                        })
                                    ) : (
                                        this.state.checkList[item[0].itemID] ? (
                                            <Button
                                                type="primary"
                                                icon={<CloseOutlined/>}
                                                size="small"
                                                onClick={this.permissionButtonClick.bind(this, item[0].itemID, item[0].fatherID, item[0].index)}
                                                style={{margin: '5px 4px'}}
                                            >
                                                {item[0].title}
                                            </Button>
                                        ) : (
                                            <span/>
                                        )
                                    )
                                );
                            })
                        }

                    </div>

                    <div
                        style={{marginTop: "40px", textAlign: "right"}}
                    >
                        <Button key="back" onClick={this.props.modalCancel}>
                            取消
                        </Button>
                        <Button
                            key="submit"
                            type="primary"
                            style={{marginLeft: "30px"}}
                            onClick={this.okButtonClick}>
                            确定
                        </Button>
                    </div>
                </div>
            </Modal>
        );
    }

}

export default RolePermission;

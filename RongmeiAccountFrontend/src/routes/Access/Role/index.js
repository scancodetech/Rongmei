import React from 'react'
import './index.css'
import FlexBreadcrumb from "../../../components/FlexBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import RoleItem from "../../../components/RoleItem"
import {Card, Button, Row, Col, Input} from "antd";
import roleAdmin from "../../../assets/img/roleAdmin.png"
import roleFinancial from "../../../assets/img/roleFinancial.png"
import roleOperation from "../../../assets/img/roleOperation.png"
import backIcon from "../../../assets/img/backIcon.svg"
import {api} from '../../../services/api/ApiProvider'
import RolePermission from "../../../components/RolePermission";

const { TextArea } = Input;

const RoleMap = [
    {
        name: "ROLE_USER",
        value: "普通用户"
    },
    {
        name: "ROLE_ADMIN",
        value: "管理员"
    }
];
class RolePage extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            // 角色
            roleItems: [
                {
                    pic: roleAdmin,
                    title: "管理员",
                    content: "管理员为企业或团队负责\n" +
                        "人，可享有全平台权限"
                },
                {
                    pic: roleFinancial,
                    title: "财务",
                    content: "财务具有账单等相关权限"
                },
                {
                    pic: roleOperation,
                    title: "运营",
                    content: "运营可以使用运营中心功能，\n" +
                        "进行营销管理、活动报名等权限"
                }

            ],
            // 二级页面
            addRole: false,
            // 对话框
            isModalShow: false
        }

        this.handleAddEmployee = this.handleAddEmployee.bind(this);
        this.handleAddRole = this.handleAddRole.bind(this);
        this.handleAddRoleBack = this.handleAddRoleBack.bind(this);
        this.handleAddRoleSubmit = this.handleAddRoleSubmit.bind(this);
        this.handleAddRolePermission = this.handleAddRolePermission.bind(this);
        this.modalOK = this.modalOK.bind(this);
        this.modalCancel = this.modalCancel.bind(this);
        this.modalShow = this.modalShow.bind(this);
        this.modalHide = this.modalHide.bind(this);
    }

    handleAddEmployee(index){
        // 处理添加账户操作
        console.log(index);
    }

    handleAddRole(){
        // 处理添加角色操作
        this.setState({
            addRole: true
        })
    }

    handleAddRoleBack(){
        // 处理添加角色返回按钮
        this.setState({
            addRole: false
        })
    }

    async handleAddRoleSubmit(){
        let token = localStorage.getItem('token');
        console.log(token)
        console.log('click role submit');
        // TODO: 这里测试获取所有角色的接口
        try {
            let res = await api.roleService.getAllRole(
            )
            console.log('success');
            console.log(res);
        } catch (e) {
            console.log('fail');
            console.log(e);
        }

/*
// TODO: 测试添加角色的接口
let roleData = {
    "roleName":"测试员1",
    "roleStr":"具有员工管理权限",
    "itemNames":["权限管理","角色列表","员工列表"]
}
try {
    let res = await api.roleService.addRole(
        roleData
    )
    console.log('success');
    console.log(res);
} catch (e) {
    console.log('fail')
    console.log(e)
}

 */
}

handleAddRolePermission(){
// 处理添加角色选择权限事件
console.log('click role permission');
this.modalShow();
}

modalShow(){
this.setState({
    isModalShow: true
})
}

modalHide(){
this.setState({
    isModalShow: false
})
}

modalCancel(){
this.modalHide();
}

modalOK(){
this.modalHide();
}

submitModal(){
console.log('submit action')
}

getRoleItems(){
// 获取角色
return (
    <div>
        <div style={styles.cardTitle}>
            <Button type="primary" onClick={this.handleAddRole}>新增角色</Button>
        </div>
        <div style={styles.cardContent}>
            <Row>
                {
                    this.state.roleItems.map((item, index) => {
                        return (
                            <Col span={8} key={index}>
                                <RoleItem
                                    index={index}
                                    imgSrc={item.pic}
                                    title={item.title}
                                    content={item.content}
                                    handleAdd={this.handleAddEmployee}
                                />
                            </Col>
                        );
                    })
                }
            </Row>
        </div>
    </div>
);
}

addRole(){
return (
    <div>
        <div style={styles.roleTitle}>
            <img src={backIcon} alt="img" style={styles.roleBack} onClick={this.handleAddRoleBack}/>
            <span>新增角色</span>
        </div>
        <div style={styles.roleContent}>
            <span>角色名称:</span>
            <Input placeholder="请输入角色名称，最多15个字" maxLength={15} style={styles.roleInput}/>
        </div>
        <div style={styles.roleContent}>
            <span style={{display: 'inline-block', verticalAlign: 'top'}}>角色描述:</span>
            <TextArea rows={3} placeholder="请输入角色描述，最多30个字" maxLength={30} style={styles.roleInput}/>
        </div>
        <div style={styles.roleContent}>
            <span>权限配置:</span>
            <Button
                onClick={this.handleAddRolePermission}
                style={styles.roleButton}
                type="primary"
                ghost
                size="">
                选择
            </Button>
        </div>
        <div style={styles.roleSubmit}>
            <Button
                onClick={this.handleAddRoleSubmit}
                type="primary"
                size="">
                提交
            </Button>
        </div>

    </div>
);
}

render() {
return (
    <div>
        <FlexBreadcrumb arr={['权限管理', '角色管理']}/>
        <TypingCard title={"角色管理"} source={"目前权限管理分为三大类: 【管理员】、【财务】、【运营】<br/>可以根据员工的职能选择角色，新增角色下的员工账号"}/>
        <RolePermission
            isModalShow={this.state.isModalShow}
            modalOK={this.modalOK}
            modalCancel={this.modalCancel}
            disabledItem={[]}
            checkedItem={[]}
            >
        </RolePermission>
        <Card style={styles.cardShow}>
            {
                this.state.addRole ? this.addRole() : this.getRoleItems()
            }
        </Card>

    </div>
)
}
}

const styles = {
tableStyle: {
width: '100%'
},
cardShow: {
textAlign: 'center',
marginTop: '10px'
},
cardTitle: {
textAlign: 'right',
marginRight: '1.5%'
},
cardContent: {
width: '90%',
marginLeft: '5%',
marginTop: '30px'
},
roleTitle: {
marginTop: '10px',
textAlign: 'left'
},
roleBack: {
width: '26px',
marginRight: '20px',
},
roleContent: {
textAlign: 'left',
marginTop: '30px',
verticalAlign: 'start'
},
roleInput: {
display: 'inline-block',
width: '30%',
marginLeft: '22px'
},
roleButton: {
marginLeft: '22px'
},
roleSubmit: {
marginTop: '40px',
textAlign: 'left',
marginBottom: '20px'
}
}

export default RolePage;

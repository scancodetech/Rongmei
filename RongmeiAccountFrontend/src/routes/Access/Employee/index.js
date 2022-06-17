import React, {Component} from "react"
import FlexBreadcrumb from "../../../components/FlexBreadcrumb";
import TypingCard from "../../../components/TypingCard";
import {Card, Menu, Dropdown, Button, Table, Input, Checkbox} from "antd";
import './index.css'
import {CloseOutlined, PlusOutlined, DownOutlined} from '@ant-design/icons'
import backIcon from "../../../assets/img/backIcon.svg";
import RolePermission from "../../../components/RolePermission";

class Employee extends Component{

    constructor(props) {
        super(props);

        this.state = {
            menuList: ["全部", "管理员", "财务", "运营"],
            menuValue: ['all', 'admin', 'financial', 'operation'],
            menuSelect: 0,
            filterItem: 'all',
            contentColumns: [
                {
                    title: '员工账号',
                    dataIndex: 'account',
                    key: 'account',
                    align: 'center',
                    render: text => <a>{text}</a>,
                    filterMultiple: false,
                    filters:[{text:"全部", value:"all"},{text:"管理员",value:"admin"},{text:"财务",value:"financial"},{text:"运营",value:"operation"}],
                    // onFilter: (value, record) => record.identify.includes(this.state.filterItem),
                    onFilter: (value, record) => record.identify.includes(value),
                },
                {
                    title: '角色',
                    dataIndex: 'role',
                    key: 'role',
                    align: 'center',
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    render: () => (
                        <a>Delete</a>
                    ),
                },
            ],
            contentData: [
                {
                    key: '1',
                    account: '123',
                    role: '管理员',
                    identify: 'all admin',
                },
                {
                    key: '2',
                    account: '234',
                    role: '财务',
                    identify: 'all financial',
                },
                {
                    key: '3',
                    account: '345',
                    role: '运营',
                    identify: 'all operation',
                },
            ],
            // 二级页面
            showAddEmployee: false,
            employeeList: [
                {label: '管理员', value: 0},
                {label: '财务', value: 1},
                {label: '运营', value: 2},
                {label: '机构小B', value: 3},
            ],
            rolePermission: [
                ['首页', '权限管理', '平台管理', '财务管理', '审核', '考试', '话题管理', '举报'],
                ['首页', '财务管理', '关于我们', '举报'],
                ['考试', '话题管理', '举报'],
                []
            ],
            oraPermission: [],
            customPermission: [],
            showPermissionButton: 'none',
            isModalShow: false
        }
        this.handleMenuClick = this.handleMenuClick.bind(this);
        this.titleMenu = this.titleMenu.bind(this);
        this.handleTableChange = this.handleTableChange.bind(this);
        this.handleAddEmployee = this.handleAddEmployee.bind(this);
        this.handleAddEmployeeBack = this.handleAddEmployeeBack.bind(this);
        this.handleEmployeeSelect = this.handleEmployeeSelect.bind(this);
        this.getEmployeeItems = this.getEmployeeItems.bind(this);
        this.addEmployee = this.addEmployee.bind(this);
        this.modalOK = this.modalOK.bind(this);
        this.modalCancel = this.modalCancel.bind(this);
        this.modalShow = this.modalShow.bind(this);
        this.modalHide = this.modalHide.bind(this);
        this.submitModa = this.submitModal.bind(this);
        this.handleAddPermission = this.handleAddPermission.bind(this);
        this.customButtonClick = this.customButtonClick.bind(this);
    }

    handleMenuClick(e) {
        let selectIndex = parseInt(e.key, 8)-1;
        this.setState({
            menuSelect: selectIndex,
        });
        this.handleTableChange(this.state.menuValue[selectIndex])
    }

    handleTableChange(filters){
        console.log('TC: ' + filters);
        this.setState({
            filterItem: filters,
        });
    }

    handleAddEmployee(){
        this.setState({
            showAddEmployee: true
        })
    }

    handleAddEmployeeBack(){
        this.setState({
            showAddEmployee: false
        })
    }

    handleEmployeeSelect(selectValue){
        let ora = [];
        for(const item of selectValue){
            ora = ora.concat(this.state.rolePermission[item]);
        }
        let unique = [];
        for(const item of ora){
            if(unique.indexOf(item) === -1){
                unique.push(item);
            }
        }
        let isButtonShow = selectValue.length > 0 ? 'inline-block' : 'none';
        this.setState({
            oraPermission: unique,
            showPermissionButton: isButtonShow
        })
    }

    titleMenu(){
        return (
            <Menu onClick={this.handleMenuClick}>
                <Menu.Item key="1">
                    {this.state.menuList[0]}
                </Menu.Item>
                <Menu.Item key="2">
                    {this.state.menuList[1]}
                </Menu.Item>
                <Menu.Item key="3">
                    {this.state.menuList[2]}
                </Menu.Item>
                <Menu.Item key="4">
                    {this.state.menuList[3]}
                </Menu.Item>
            </Menu>
        );
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

    modalOK(selectItem){
        this.modalHide();
        this.setState({
            customPermission: selectItem
        })
    }

    submitModal(){
        console.log('submit action')
    }

    handleAddPermission(){
        this.modalShow();
    }

    customButtonClick(item){
        let newPermission = this.state.customPermission;
        let index = newPermission.indexOf(item);
        if(index > -1){
            newPermission.splice(index, 1)
        }
        this.setState({
            customPermission: newPermission
        })
    }

    getEmployeeItems(){
        return(
            <div>
                <div style={styles.cardTitle}>
                <span
                    style={{display: 'inline-block', marginRight: '8px'}}
                >
                    筛选:
                </span>

                    <Dropdown
                        overlay={this.titleMenu}
                    >
                        <Button>
                            {this.state.menuList[this.state.menuSelect]} <DownOutlined />
                        </Button>
                    </Dropdown>

                    <Button
                        onClick={this.handleAddEmployee}
                        style={{float: 'right'}}
                        type="primary"
                        size="">
                        新增员工
                    </Button>
                </div>
                <Table columns={this.state.contentColumns}  dataSource={this.state.contentData} onChange={this.handleTableChange}/>
            </div>
        );
    }

    addEmployee(){
        return (
            <div>
                <div style={styles.employeeTitle}>
                    <img src={backIcon} alt="img" style={styles.employeeBack} onClick={this.handleAddEmployeeBack}/>
                    <span>新增员工</span>
                </div>
                <div style={styles.employeeContent}>
                    <span>员工账号:</span>
                    <Input placeholder="请输入员工账号，如手机号" maxLength={20} style={styles.employeeInput}/>
                </div>
                <div style={styles.employeeContent}>
                    <span>角色选择:</span>
                    <Checkbox.Group
                        style={{marginLeft: '20px'}}
                        options={this.state.employeeList}
                        defaultValue={['Apple']}
                        onChange={this.handleEmployeeSelect} />
                </div>
                <div style={styles.employeeContent}>
                    <span
                        style={{
                            display: 'inline-block',
                            verticalAlign: 'top',
                        }}
                    >
                        该角色具有的权限:
                    </span>
                    <div
                        style={{
                            marginLeft: '20px',
                            display: 'inline-block',
                            width: '50%',
                        }}
                    >
                        {
                            this.state.oraPermission.map((item)=>{
                                return(
                                    <Button
                                        style={{marginRight: '10px', marginBottom: '10px'}}
                                        size="small"
                                        disabled
                                    >
                                        {item}
                                    </Button>
                                )
                            })
                        }
                        {<br/>}
                        {
                            this.state.customPermission.map((item)=>{
                                return(
                                    <Button
                                        // onClick={this.customButtonClick.bind(this, item)}
                                        type="primary"
                                        style={{marginRight: '10px', marginBottom: '10px'}}
                                        size="small"
                                        // icon={<CloseOutlined/>}
                                    >
                                        {item}
                                    </Button>
                                )
                            })
                        }
                    </div>
                </div>
                <div style={styles.employeeContentNarrow}>
                    <Button
                        onClick={this.handleAddPermission}
                        type="primary"
                        ghost
                        icon={<PlusOutlined />}
                        size="small"
                        style={{margin: '0 4px 4px 135px', display: this.state.showPermissionButton}}
                    >
                        修改权限
                    </Button>
                </div>

                <div style={styles.employeeContent}>
                    <Button
                        type="primary"
                        size=""
                        style={{margin: '5px 4px 5px 4px', width: '100px'}}
                    >
                        提交
                    </Button>
                </div>
            </div>
        );
    }

    render() {
        return (
            <div>
                <FlexBreadcrumb arr={['权限管理', '员工列表']}/>
                <TypingCard title={"员工列表"} source={"目前权限管理分为三大类: 【管理员】、【财务】、【运营】<br/>可以根据员工的职能选择角色，新增角色下的员工账号"}/>
                <RolePermission
                    isModalShow={this.state.isModalShow}
                    modalOK={this.modalOK}
                    modalCancel={this.modalCancel}
                    disabledItem={this.state.oraPermission}
                    checkedItem={this.state.customPermission}
                >
                </RolePermission>
                <Card className="contentShow" style={styles.cardShow}>
                    {
                        this.state.showAddEmployee ? (
                            this.addEmployee()
                        ) : (
                            this.getEmployeeItems()
                        )
                    }
                </Card>
            </div>
        )
    }
}

const styles = {
    cardShow: {
        textAlign: 'center',
        marginTop: '10px',
        padding: 0,
    },
    cardTitle: {
        textAlign: 'left',
        marginLeft: '24px',
        marginRight: '24px',
        marginBottom: '10px'
    },
    cardContent: {
        width: '90%',
        marginLeft: '5%',
        marginTop: '30px'
    },
    employeeTitle: {
        marginTop: '10px',
        textAlign: 'left',
        paddingLeft: '24px',
    },
    employeeBack: {
        width: '26px',
        marginRight: '20px',
    },
    employeeContent: {
        textAlign: 'left',
        marginTop: '30px',
        verticalAlign: 'start',
        paddingLeft: '24px',
    },
    employeeContentNarrow: {
        textAlign: 'left',
        marginTop: '10px',
        verticalAlign: 'start',
        paddingLeft: '24px',
    },
    employeeInput: {
        display: 'inline-block',
        width: '30%',
        marginLeft: '22px'
    },
}

export default Employee;

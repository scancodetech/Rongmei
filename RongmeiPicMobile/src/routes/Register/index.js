import {DatePicker, Flex, Grid, List, Button, Toast} from 'antd-mobile'
import React from 'react'
import './style.css'

import maleIcon from '../../assets/gender/male.svg'
import maleSIcon from '../../assets/gender/maleS.svg'
import femaleIcon from '../../assets/gender/female.svg'
import femaleSIcon from '../../assets/gender/femaleS.svg'
import robotIcon from '../../assets/gender/robot.svg'
import robotSIcon from '../../assets/gender/robotS.svg'
import secrecyIcon from '../../assets/gender/secrecy.svg'
import secrecySIcon from '../../assets/gender/secrecyS.svg'
import {api} from "../../services/api/ApiProvider";

const nowTimeStamp = Date.now();
const minDate = new Date(1900, 0, 1, 1, 0, 0, 0);
const maxDate = new Date(nowTimeStamp);
export default class Register extends React.Component {
    state = {
        genderType: [
            {
                text: '',
                icon: maleIcon,
                iconS: maleSIcon,
                value: '男',
            },
            {
                text: '',
                icon: femaleIcon,
                iconS: femaleSIcon,
                value: '女',
            },
            {
                text: '',
                icon: robotIcon,
                iconS: robotSIcon,
                value: '机器人',
            },
            {
                text: '',
                icon: secrecyIcon,
                iconS: secrecySIcon,
                value: '未知',
            },
        ],

        // 注册信息
        gender: '',
        birthday: 0
    }

    async componentDidMount() {
        const res = await api.accountService.getUserInfo();
        this.setState({
            ...res,
            gender: res.gender,
            birthday: res.birthday
        })
    }

    async nextStep() {
        try {
            await api.accountService.saveUserInfo(this.state);
            this.props.history.push('/register/interest')
        } catch (e) {
            Toast.fail("信息提交失败，请检查网络后重试");
        }
    }

    render() {
        const state = this.state
        return (
            <div className='registerPage'>
                <div className='genderBanner'>
                    <Flex justify='center' direction='column' align='center' style={{width: '100%'}}>
                        <div style={{color: 'white', fontSize: 20, fontWeight: 600}}>选择性别</div>
                        <div style={{width: '100%', padding: '20px 20px'}}>
                            <Grid data={state.genderType}
                                  square
                                  columnNum={4}
                                  hasLine={false}
                                  activeStyle={false}
                                  renderItem={(dataItem) => (
                                      <div onClick={() => this.setState({gender: dataItem.value}, () => {
                                          console.log(this.state.gender)
                                      })} style={{
                                          height: 50,
                                          width: 80,
                                          boxSizing: 'border-box',
                                          borderRadius: '50%',
                                          marginTop: 10
                                      }}>
                                          <img src={state.gender === dataItem.value ? dataItem.iconS : dataItem.icon}
                                               style={state.gender === dataItem.value ? {
                                                   width: 50,
                                                   height: 50,
                                                   border: '2px solid #fe2341',
                                                   borderRadius: '50%',
                                                   boxSizing: 'border-box',
                                               } : {width: 50, height: 50, boxSizing: 'border-box'}}/>
                                      </div>
                                  )
                                  }
                            />
                        </div>

                    </Flex>
                </div>
                <div className='birthdayBanner'>
                    <Flex justify='center' direction='column' align='center' style={{width: '100%'}}>
                        <div style={{color: 'white', fontSize: 20, fontWeight: 600}}>出生日期</div>
                        <DatePicker
                            value={new Date(state.birthday)}
                            mode="date"
                            minDate={minDate}
                            maxDate={maxDate}
                            className='birthdayPicker'
                            onChange={(value) => this.setState({birthday: value.getTime()}, () => console.log(this.state.birthday),)}
                        >
                            {/* <div className='DataPickList'> */}
                            <List.Item className='DataPickList'>Date</List.Item>
                            {/* </div> */}
                        </DatePicker>
                    </Flex>
                </div>

                <div className='submit'>
                    <Button disabled={this.state.gender === '' || this.state.birthday === ''} onClick={() => {
                        this.nextStep()
                    }}>{`下一步`}</Button>
                </div>

            </div>
        )
    }
}

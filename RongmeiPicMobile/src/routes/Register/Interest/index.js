import {Grid, Flex, Button, Toast} from 'antd-mobile'
import React from 'react'
import './style.css'
import {api} from "../../../services/api/ApiProvider";

export default class Interest extends React.Component {
    tccService = api.tccService;
    state = {
        gridData: [],
        selectValue: [],
    }

    async componentDidMount() {
        const tccRes = await this.tccService.getTCC("dimension.m.interestlist");
        if (tccRes.tccTuple.value) {
            const tccInterestValue = eval(tccRes.tccTuple.value)
            console.log(tccInterestValue)
            this.setState({
                gridData: tccInterestValue
            }, () => console.log(this.state.gridData))
        }

        const res = await api.accountService.getUserInfo();
        this.setState({
            ...res,
            selectValue: res.interests
        })
    }

    submitInterest = async () => {
        try {
            await api.accountService.saveUserInfo({
                ...this.state,
                interests: this.state.selectValue
            });
            this.props.history.push('/register/avatar')
        } catch (e) {
            Toast.fail("信息提交失败，请检查网络后重试");
        }
    }

    render() {
        return (
            <div className='RegisterInterestPage'>
                <Flex justify='center' direction='column' align='center' style={{width: '100%'}}>
                    <div style={{color: 'white', fontSize: 20, fontWeight: 600, padding: '50px 0 20px 0'}}>兴趣选择</div>
                    <div style={{color: '#AAAAAA', fontSize: 12, fontWeight: 600, paddingBottom: 30}}>选择你近期感兴趣的内容</div>
                </Flex>
                <div style={{marginTop: 0}} className='gridChange'>
                    <Grid data={this.state.gridData}
                          onClick={(Obj) => {
                              let newData = this.state.selectValue
                              console.log(newData)
                              let index = newData.indexOf(Obj.name)
                              index !== -1 ? newData.splice(index, 1) : newData.push(Obj.name)

                              // 传入value或name都可以
                              this.setState({selectValue: newData}, () => console.log(this.state.selectValue))
                          }}
                          hasLine={false}
                          columnNum={3}
                          activeStyle={false}
                          renderItem={(dataItem) =>
                              <Flex justify='center'>
                                  <div style={{width: 100, height: 120, position: 'relative', textAlign: 'center'}}>
                                      <img alt='' style={{width: 90, height: 90, borderRadius: 5, objectFit: 'cover',}}
                                           src={dataItem.url}/>
                                      <div style={{
                                          width: 100,
                                          height: 20,
                                          textAlign: 'center',
                                          color: 'white',
                                          fontWeight: 600,
                                          lineHeight: '20px'
                                      }}>{dataItem.name}</div>
                                      <div
    className={this.state.selectValue.includes(dataItem.name) ? 'gridIcon-active' : 'gridIcon'}/>
                                  </div>
                              </Flex>
                          }
                    />
                </div>

                <div style={{
                    position: 'fixed',
                    bottom: 0,
                    width: '100%',
                    left: 0,
                    backgroundColor: 'black',
                    height: 55
                }}>
                    <div className='submit'>
                        <Button disabled={this.state.selectValue.length < 6}
                                onClick={this.submitInterest}>{this.state.selectValue.length < 6 ? `至少关注6个兴趣` : `下一步`}</Button>
                    </div>
                </div>
            </div>
        )
    }
}

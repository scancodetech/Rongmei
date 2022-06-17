import React from 'react'
import './style.css'
import Header from '../../../../components/Header/index'
import {
    SearchBar,
    List,
    WhiteSpace,
    Modal,
    Flex,
    InputItem,
    WingBlank,
    Button,
    ImagePicker,
    ListView
} from 'antd-mobile'
import {api} from "../../../../services/api/ApiProvider";

export default class Certification extends React.Component {
    state = {
        searchData: []
    }

    componentDidMount() {

    }

    onSearch = async (value) => {
        try {
            const storageRes = await api.outerService.queryFromStorage(value);
            this.setState({
                searchData: storageRes.storageItemList
            })
        } catch (e) {
            console.log(e)
        }
    }

    render() {
        return (
            <div className='certificationPage'>
                <Header title='存证查询'>
                    <div className='certificationBarCode'/>
                </Header>
                <WhiteSpace size='sm'/>
                <WingBlank size='lg'>
                    <div className='CertificationSearchBarChange'>
                        <SearchBar onChange={(value => this.onSearch(value))} placeholder="搜索存证物名称" maxLength={20}/>
                    </div>

                </WingBlank>
                <Flex justify='center' style={{width: '100%'}}>
                    {
                        this.state.searchData.length < 1 ?
                            <Flex justify='center' direction='column'
                                  style={{marginTop: 80, fontSize: 15, color: 'white'}}>
                                <div className='certificationNullIcon'/>
                                <WhiteSpace size='lg'/>
                                <div>没搜到存证证书呢~</div>
                                <WhiteSpace size='md'/>
                                <div>请核对名称哦！</div>
                            </Flex> : (
                                <Flex justify='center' direction='column'
                                      style={{marginTop: 20, fontSize: 15, color: 'white'}}>
                                    <img style={{width: window.innerWidth - 60}}
                                         src={this.state.searchData[0].certificationUrl}/>
                                </Flex>
                            )
                    }
                </Flex>


            </div>
        )
    }
}

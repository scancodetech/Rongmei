import React from 'react'
import {withRouter, Switch, Redirect, Route} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'
import Index from "../../routes/Index";

const Home = LoadableComponent(() => import('../../routes/Home/index'))  //参数一定要是函数，否则不会懒加载，只会代码拆分

const AccessAddPage = LoadableComponent(() => import('../../routes/Access/AddPage'))
const AccessListPage = LoadableComponent(() => import('../../routes/Access/ListPage'))

const DataManagement = LoadableComponent(() => import('../../routes/DataManagement'))

//关于
const About = LoadableComponent(() => import('../../routes/About/index'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{padding: 16, position: 'relative'}}>
                <Switch>
                    <Route exact path='/home' component={Home}/>

                    <Route exact path='/home/access/add' component={AccessAddPage}/>
                    <Route exact path='/home/access/list' component={AccessListPage}/>

                    <Route exact path='/home/data' component={DataManagement}/>

                    <Route exact path='/home/about' component={About}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain

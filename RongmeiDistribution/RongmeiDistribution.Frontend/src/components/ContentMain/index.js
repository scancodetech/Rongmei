import React from 'react'
import {withRouter, Switch, Redirect, Route} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'

const Login = LoadableComponent(() => import('../../routes/Login/index'))
const Home = LoadableComponent(() => import('../../routes/Home/index'))  //参数一定要是函数，否则不会懒加载，只会代码拆分

const Info = LoadableComponent(() => import('../../routes/Info'))
const Level = LoadableComponent(() => import('../../routes/Level'))

//关于
const About = LoadableComponent(() => import('../../routes/About/index'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{padding: 16, position: 'relative'}}>
                <Switch>
                    <Route exact path='/login' component={Login}/>
                    <Route exact path='/home' component={Home}/>

                    <Route exact path='/home/info' component={Info}/>
                    <Route exact path='/home/Level' component={Level}/>

                    <Route exact path='/home/about' component={About}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain

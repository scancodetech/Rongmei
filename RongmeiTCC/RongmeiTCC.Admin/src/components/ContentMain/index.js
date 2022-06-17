import React from 'react'
import {withRouter, Switch, Redirect, Route} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'

const Home = LoadableComponent(() => import('../../routes/Home'))
//关于
const About = LoadableComponent(() => import('../../routes/About/index'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{padding: 16, position: 'relative'}}>
                <Switch>
                    <Route exact path='/home/about' component={About}/>
                    <Route exact path='/home' component={Home}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain

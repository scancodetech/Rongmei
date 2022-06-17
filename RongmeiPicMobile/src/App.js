import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom'
import Index from './routes/Index/index'
import Login from './routes/Login/index'

import Register from './routes/Register/index'
import RegisterInterest from './routes/Register/Interest/index'
import RegisterAvatar from './routes/Register/Avatar/index'


import './App.css'
import './assets/font/iconfont.css'


class App extends Component {

    render() {
        return (
            <Switch>
                <Route exact path='/login/:user?' component={Login} />
                <Route exact path='/register' component={Register} />
                <Route exact path='/register/interest' component={RegisterInterest} />
                <Route exact path='/register/avatar' component={RegisterAvatar} />


                {/* <Route exact path='/login(/:user)' component={Login}/> */}
                <Route path='/' component={Index} />
            </Switch>
        )
    }
}

export default App;

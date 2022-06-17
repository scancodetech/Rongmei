import React, {Component} from 'react';

import {ConnectProps} from "@@/plugin-dva/connect";
import Resource from './resource';

interface PlaygroundProps extends Partial<ConnectProps> {
  location: any;
}

class Playground extends Component<PlaygroundProps> {

  render() {
    return (
      <Resource location={this.props.location}/>
    );
  }
}

export default Playground;

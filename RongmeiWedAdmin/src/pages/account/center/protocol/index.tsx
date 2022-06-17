import React, { Component } from 'react';
import ReactMarkdown from "react-markdown";
import { commodityPrivateProtocol, auctionPrivateProtocol, blindboxPrivateProtocol } from "./protocols";

class Protocol extends Component<any> {

  state = {
    protocolText: '',
  }

  async componentDidMount() {
    let protocolText = '';
    switch (this.props.match.params.type) {
      case 'commodity':
        protocolText = commodityPrivateProtocol;
        break;
      case 'auction':
        protocolText = auctionPrivateProtocol;
        break;
      case 'blindbox':
        protocolText = blindboxPrivateProtocol;
        break;
      default:
        break;
    }(this.setState({
      protocolText
    }))

  }

  render() {
    return (
      <div style={{ padding: '30px 50px', backgroundColor: '#ffffff' }}>
        {this.state.protocolText === '' ? '' : <ReactMarkdown source={this.state.protocolText}></ReactMarkdown>}
      </div>
    );
  }
}

export default Protocol
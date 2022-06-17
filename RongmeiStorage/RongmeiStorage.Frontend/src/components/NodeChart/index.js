import React from "react";
import {Modal} from 'antd';
import Graphin from "@antv/graphin";
import "@antv/graphin/dist/index.css";

class NodeChart extends React.Component {
    constructor(props) {
        super(props)
        this.nodeRef = React.createRef();
    }

    componentDidMount() {
        const {graph} = this.nodeRef.current;
        graph.on('node:click', e => {
            const model = e.item.get('model');
            Modal.info({
                title: model.data.id,
                content: model.data.label,
                maskClosable: true
            });
        });
    }

    render() {
        return (
            <Graphin data={this.props.data} layout={{name: "force"}} ref={this.nodeRef}>
            </Graphin>
        )
    }
}

export default NodeChart

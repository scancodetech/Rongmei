import React from 'react'
import {Link, withRouter} from 'react-router-dom'
import {Chart, Geom, Axis, Tooltip, Legend} from 'bizcharts';

const scale = {
    time: {
        alias: "时间",
        tickCount: 10,
        nice: false
    },
    num: {
        alias: "调用人次"
    },
    type: {
        type: "cat"
    }
};

//withRouter一定要写在前面，不然路由变化不会反映到props中去
@withRouter
class SliderChart extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Chart
                data={this.props.data}
                scale={scale}
                forceFit
            >
                <Tooltip/>
                <Axis/>
                <Legend/>
                <Geom
                    type="line"
                    position="time*num"
                    color={["type", ["#ff7f0e"]]}
                    shape="smooth"
                    size={1}
                />
            </Chart>
        );
    }
}

export default SliderChart

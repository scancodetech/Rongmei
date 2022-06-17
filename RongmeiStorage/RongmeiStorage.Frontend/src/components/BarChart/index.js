import React from 'react'
import {Link, withRouter} from 'react-router-dom'
import {Chart, Geom, Axis, Tooltip, Legend} from 'bizcharts';

const cols = {
    time: {alias: '月服务使用量'}, // 数据字段别名映射
    num: {}
};

//withRouter一定要写在前面，不然路由变化不会反映到props中去
@withRouter
class BarChart extends React.Component {

    render() {
        return (
            <div className="App">
                <Chart data={this.props.data} scale={cols} forceFit>
                    <Axis name="time" title/>
                    <Axis name="num"/>
                    <Tooltip/>
                    <Geom type="interval" position="time*num" color="time"/>
                </Chart>
            </div>
        )
    }
}

export default BarChart

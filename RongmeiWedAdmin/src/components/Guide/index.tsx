import zIndex from '@material-ui/core/styles/zIndex'
import ReactDOM from 'react-dom';
import React from 'react'
import styles from './index.less'
import { string } from 'prop-types';


class Guide extends React.Component {
    state = {
        step: 0,
        left: 0,
        top: 0,
    }

    // componentDidMount() {
    //     var x = document.getElementById('mark-1')
    //     let truex=ReactDOM.findDOMNode(x)
    //     console.log(truex)
    //     console.log(x?.offsetLeft)
    //     console.log(x?.offsetTop)
    //     this.setState({
    //         left: x?.offsetLeft,
    //         top: x?.offsetTop,
    //     })
    // }
    clickmark() {
        console.log("点击遮罩")
        try {
            var x = document.getElementById('mark-1')
            // ReactDOM操作，用于获取真正的元素
            ReactDOM.findDOMNode(x)!.style.zIndex = '101'
            // console.log(ReactDOM.findDOMNode(x).style)
            // console.log(x?.getBoundingClientRect().left)
            // x?.setAttribute('z-index','999')
        } catch (err) {
            console.log(err)
        }
        const timmer = setInterval(() => {
            console.log("1000")
            var mark = document.getElementById('markShow')
            console.log(mark?.style)
            // 点击遮罩1s后自动隐藏
            ReactDOM.findDOMNode(mark)!.style.display = 'none'
            clearInterval(timmer)
        }, 1000)


    }

    render() {
        return (
            <div id='markShow' className={styles.mark} onClick={() => this.clickmark()} style={{ display: this.props.display }}>
                <div className={styles.modalBg} style={{ left: '52%', top: '5%' }}>
                    <div className={styles.moadalText} style={{ left: 0, bottom: 0 }}>
                        <div style={{ fontSize: 13, fontWeight: 600 }}>
                            需要身份认证，才可以参与市场交易哦~
                        </div>
                        <div style={{ fontSize: 13, fontWeight: 600, color: '#FE2341', padding: '5px 10px 3px 10px', borderBottom: '1px solid #FE2341' }}>
                            快去认证吧!
                        </div>
                    </div>
                    <div className={styles.modalArrow} style={{ right: 120, top: 20 }} />
                </div>
            </ div>
        )

    }
}
export default Guide

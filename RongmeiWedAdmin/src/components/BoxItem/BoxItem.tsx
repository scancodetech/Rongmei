import React, {Component} from "react";
import editIcon from '@/assets/edit.svg';
import delIcon from '@/assets/del.svg';
import shareIcon from '@/assets/share.svg';
import distIcon from '@/assets/dist.svg';

class BoxItem extends Component{

    constructor(props) {
        super(props);
    }

    state = {
        backColor: '#fff',
        modShow: 'none',
        editColor: '#555',
        delColor: '#555',
        shareColor: '#555',
        distColor: '#555',
    }

    render() {
        return(
            <div
                style={{backgroundColor: this.state.backColor, width: '100%', height: '200px', position: 'relative'}}
                onMouseOver={()=>{this.setState({backColor: '#eee', modShow: 'block'})}}
                onMouseOut={()=>{this.setState({backColor: '#fff', modShow: 'none'})}}
                >
                <div style={{display: 'inline-block', width: '20%', height: '100%', verticalAlign: 'top'}}>
                    <div style={{width: '100%', height: '92%', marginLeft: '4%', marginTop: '5%', position: 'relative', borderRadius: '5px', backgroundColor: '#bbb'}}>
                        <img src={this.props.info.imgUrl} alt="Pic" style={{height: '100%', width: '100%'}}/>
                        <div style={{position: 'absolute', top: '0', left: '0', backgroundColor: 'red', color: 'white', padding: '5px 10px', borderRadius: '5px'}}>
                            {this.props.info.type}
                        </div>
                    </div>
                </div>
                <div style={{display: 'inline-block', width: '76%', marginLeft: '3%'}}>
                    <div style={{height: '40px', marginTop: '20px'}}>
                       <div style={{fontSize: '17pt', display: 'inline-block'}}>
                           {this.props.info.serName} - {this.props.info.name}
                       </div>
                        <div style={{fontSize: '10pt', color: '#888', float: 'right', display: 'inline-block', verticalAlign: 'bottom', margin: '6px 10px'}}>
                            {this.props.info.time}
                        </div>
                    </div>

                    <div style={{fontSize: '12pt'}}>
                        {this.props.info.intro}
                    </div>

                    <div style={{marginTop: '30px'}}>
                       <div style={{color: 'blue', display: 'inline-block', fontSize: '12pt'}}>
                           价格: ¥{this.props.info.price}/抽
                       </div>
                        <div style={{color: 'red', display: 'inline-block', marginLeft: '30px', fontSize: '12pt'}}>
                            限量:{this.props.info.limit}
                        </div>
                        <div style={{color: 'red', display: 'inline-block', marginLeft: '30px', fontSize: '12pt'}}>
                            已分发:{this.props.info.pub}/{this.props.info.limit}
                        </div>
                    </div>

                    <div style={{height: '25px', width: '100%', position: 'absolute', bottom: '20px', display: this.state.modShow}}>
                        <div
                            style={{width: '18%', height: '100%', display: 'inline-block', color: this.state.editColor}}
                            onMouseOver={()=>{this.setState({editColor: 'red'})}}
                            onMouseOut={()=>{this.setState({editColor: '#555'})}}
                            >
                            <img src={editIcon} alt="icon" style={{height: '80%', float: 'left', display: 'inline-block'}}/>
                            <p style={{display: 'inline-block', height: '100%', float: 'left', lineHeight: '100%', fontSize: '12pt', padding: '2px 8px'}}>修改信息</p>
                        </div>
                        <div
                            style={{width: '18%', height: '100%', display: 'inline-block', color: this.state.delColor}}
                            onMouseOver={()=>{this.setState({delColor: 'red'})}}
                            onMouseOut={()=>{this.setState({delColor: '#555'})}}
                        >
                            <img src={delIcon} alt="icon" style={{height: '80%', float: 'left', display: 'inline-block'}}/>
                            <p style={{display: 'inline-block', height: '100%', float: 'left', lineHeight: '100%', fontSize: '12pt', padding: '2px 8px'}}>删除盒蛋</p>
                        </div>
                        <div
                            style={{width: '18%', height: '100%', display: 'inline-block', color: this.state.shareColor}}
                            onMouseOver={()=>{this.setState({shareColor: 'red'})}}
                            onMouseOut={()=>{this.setState({shareColor: '#555'})}}
                        >
                            <img src={shareIcon} alt="icon" style={{height: '80%', float: 'left', display: 'inline-block'}}/>
                            <p style={{display: 'inline-block', height: '100%', float: 'left', lineHeight: '100%', fontSize: '12pt', padding: '2px 8px'}}>分享</p>
                        </div>
                        <div
                            style={{width: '18%', height: '100%', display: 'inline-block', color: this.state.distColor}}
                            onMouseOver={()=>{this.setState({distColor: 'red'})}}
                            onMouseOut={()=>{this.setState({distColor: '#555'})}}
                        >
                            <img src={distIcon} alt="icon" style={{height: '80%', float: 'left', display: 'inline-block'}}/>
                            <p style={{display: 'inline-block', height: '100%', float: 'left', lineHeight: '100%', fontSize: '12pt', padding: '2px 8px'}}>分发</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default BoxItem;

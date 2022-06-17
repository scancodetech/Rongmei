import React, {Component} from 'react';

class BoxShow extends Component{

    constructor(props) {
        super(props);
    }

    render() {
       return(
           <div style={{display: 'inline-block'}}>
               <div style={{width: '145px'}}>
                   <div style={{height: '20px'}}>
                       <span>
                           {this.props.name}
                       </span>

                       <span style={{float: 'right', marginRight: '10px'}}>
                           {this.props.limit}
                       </span>

                   </div>

                   <div style={{width: '100%', height: '145px', backgroundColor: '#ddd', marginTop: '5px'}}>
                       <img src={this.props.url} alt="img" style={{width: '100%', height: '100%'}}/>
                   </div>

               </div>
           </div>
       );
    }
}
export default BoxShow;

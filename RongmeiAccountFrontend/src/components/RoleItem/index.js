import React from 'react'
import {Typography, Button} from 'antd'

const {Paragraph, Text} = Typography;

class RoleItem extends React.Component{

    constructor(props) {
        super(props);
        this.handleAdd = this.handleAdd.bind(this);
    }


    handleAdd(){
        const {handleAdd, index} = this.props;
        handleAdd(index);
        // this.props.handleAdd(this.props.index);
    }

    render() {
        const {imgSrc, title, content} = this.props;
        return (
            <div>
                <img
                    style={{width: '23%'}}
                    src={imgSrc}
                    alt="img"
                />
                <Text style={styles.title}>
                    {title}
                </Text>
                <Paragraph style={styles.content}>
                    {content}
                </Paragraph>
                <Button
                    type="primary"
                    ghost
                    size=""
                    style={styles.button}
                    onClick={this.handleAdd}>
                    添加账号
                </Button>
            </div>
        );
    }
}


const styles = {
    title:{
        fontSize: '24',
        display: 'block',
        color: '#1890FF',
        marginTop: '20px'
    },

    content:{
        fontSize: '14',
        color: '#666666',
        width: '60%',
        height: '50px',
        marginLeft: '20%',
        marginTop: '20px',
        overflowY: 'auto'
    },

    button:{
        marginTop: '20px',
        marginBottom: '30px'
    }
}

export default RoleItem;

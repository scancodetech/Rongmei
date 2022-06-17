import React, {Component} from 'react';
import { Upload, message, Input } from 'antd';
import { CloudUploadOutlined } from '@ant-design/icons';
import ClassCropperModal from "@/components/CropImage/ClassCropperModal";
import auctionStyles from "@/pages/account/center/auction/auction.less";
import {uploadFileToCos} from "@/services/upload";

class BlindBox extends Component{

    constructor(props) {
        super(props);
    }

    state = {
        MAX_FILE_SIZE: 1024 * 1024,
        isCropperModalVisible: false,
        cropperModalFile: '',
        imgUrl: '',
        fileUrl: '',
        name: '',
        count: '',
        imgUploading: false,
        fileUploading: false,
    }

    handleBeforeUpload(file: File, fileList: File[]){
        if (file) {
            if (file.size <= this.state.MAX_FILE_SIZE) {
                // TODO:
                this.setState({
                    cropperModalFile: file,
                }, ()=>{
                    this.setState({
                        isCropperModalVisible: true,
                        imgUploading: true,
                    }, ()=>{
                        this.props.setArgument(this.props.index, "5", this.state.imgUploading);
                    })
                })

            } else {
                message.error('文件: ' + file.name + '大于1M，请重新选择')
            }
        }
    }

    async uploadFile(file: File){
        this.setState({
            fileUploading: true
        }, ()=>{
            this.props.setArgument(this.props.index, "4", this.state.fileUploading);
        })
        try {
            const resp = await uploadFileToCos('ora_' + file.name, file)
            if (resp.statusCode === 200) {
                this.setState({
                    fileUrl:"https://" + resp.Location,
                }, ()=>{
                    message.success(`${file.name} 文件上传成功`);
                    this.setState({
                        fileUploading: false
                    }, ()=>{
                        this.props.setArgument(this.props.index, "4", this.state.fileUploading);
                    })
                    console.log('file');
                    console.log(this.state.fileUrl);
                    this.props.setArgument(this.props.index, "3", this.state.fileUrl);
                })
            }
        } catch (e) {
            console.log(e)
            message.error(`${file.name} 文件上传失败`);
        }
        return false;
    }

    removeFile(){
        this.setState({
            fileUrl: '',
        }, ()=>{
            this.props.setArgument(this.props.index, "3", this.state.fileUrl);
        })
    }

    nameChange(event){
        this.setState({
            name: event.target.value,
        })
        this.props.setArgument(this.props.index, "0", event.target.value);
    }

    countChange(event){
        this.setState({
            count: event.target.value,
        })
        this.props.setArgument(this.props.index, "1", event.target.value);
    }

    render() {
       return(
           <div>
               <div style={{width: '145px'}}>
                   <Upload
                       listType="picture-card"
                       showUploadList={false}
                       className={auctionStyles.uploadAreaSmall}
                       beforeUpload={this.handleBeforeUpload.bind(this)}
                   >
                       {
                           this.state.imgUrl ?
                               <img src={this.state.imgUrl} alt='avatar' style={{height: '100%', borderRadius: '15px', overflow: 'hidden'}}/> :
                               <div>
                                   <p className="ant-upload-drag-icon">
                                       <CloudUploadOutlined style={{color: '#bbb', fontSize: 55}}/>
                                   </p>
                                   <p
                                       style={{color: 'red', fontWeight: 'bold'}}
                                       className="ant-upload-text">点击/拖动上传</p>
                                   <p className="ant-upload-hint">
                                       大小: 1MB以内
                                   </p>
                               </div>
                       }
                   </Upload>
                   <div style={{width: '100%', marginTop: '8px'}}>
                       <div style={{width: '30%', display: 'inline-block'}}>
                           名称:
                       </div>
                       <div style={{width: '70%', display: 'inline-block'}}>
                           <Input size='small' style={{borderBottom: '1px solid black'}} bordered={false} onChange={this.nameChange.bind(this)}/>
                       </div>
                   </div>
                   <div style={{width: '100%', marginTop: '10px', marginBottom: '8px'}}>
                       <div style={{width: '35%', display: 'inline-block'}}>
                           源文件:
                       </div>
                       <div style={{width: '65%', display: 'inline-block'}}>
                           <Upload
                                beforeUpload={this.uploadFile.bind(this)}
                                onRemove={this.removeFile.bind(this)}>
                               {
                                   this.state.fileUrl ?
                                       null :
                                       <p style={{color: 'red'}}>
                                           点击上传
                                       </p>
                               }
                           </Upload>
                       </div>
                   </div>
                   <div style={{width: '100%'}}>
                       <div style={{width: '30%', display: 'inline-block'}}>
                           数量:
                       </div>
                       <div style={{width: '70%', display: 'inline-block'}}>
                           <Input type={'number'} size='small' style={{borderBottom: '1px solid black'}} value={this.props.number} bordered={false} onChange={this.countChange.bind(this)} disabled/>
                       </div>
                   </div>
               </div>
               <div>
                   {
                       this.state.isCropperModalVisible ?
                       <ClassCropperModal
                           isClassCropperModalVisible={this.state.isCropperModalVisible}
                           uploadedImageFile={this.state.cropperModalFile}
                           onClose={() => {
                               this.setState({isCropperModalVisible: false})
                           }}
                           onSubmit={(url) => {
                               console.log('img');
                               console.log(url);
                               this.setState({
                                   imgUrl: url,
                                   isCropperModalVisible: false,
                                   imgUploading: false,
                               }, ()=>{
                                   this.props.setArgument(this.props.index, "2", this.state.imgUrl);
                                   this.props.setArgument(this.props.index, "5", this.state.imgUploading);
                               })
                           }}
                       /> :
                       null
                   }
               </div>

           </div>
       );
    }
}
export default BlindBox;

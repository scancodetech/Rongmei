import React from 'react'
import Cropper from 'react-cropper' // 引入Cropper
import 'cropperjs/dist/cropper.css' // 引入Cropper对应的css

import {uploadFileToCos} from "@/services/upload";
import {Modal} from 'antd';
import lrz from 'lrz';

export interface ClassCropperModalProps {
    uploadedImageFile: File;
    onClose: () => void;
    onSubmit: (url: string) => void;
    isClassCropperModalVisible: boolean;
}

export default class ClassCropperModal extends React.Component<ClassCropperModalProps> {
    private cropper: any;
    state = {
        src: null
    }
    onCropperInit = cropper => {
        this.cropper = cropper
    }

    componentDidMount() {
        const fileReader = new FileReader()
        fileReader.onload = e => {
            const dataURL = e.target.result
            this.setState({src: dataURL})
        }

        fileReader.readAsDataURL(this.props.uploadedImageFile)
    }

    handleSubmit = () => {
        if (!this.state.submitting) {
            this.cropper.getCroppedCanvas().toBlob(async blob => {
                this.setState({submitting: true})
                let targetFile = new File([blob], this.props.uploadedImageFile.name, {type: this.props.uploadedImageFile.type})
                const rst = await lrz(targetFile);
                const resp = await uploadFileToCos(this.props.uploadedImageFile.name, rst.file)
                if (resp.statusCode === 200) {
                    this.props.onSubmit("https://" + resp.Location);
                    this.props.onClose();
                }
                this.setState({submitting: false})
            })
        }
    }

    render() {
        return (
            <Modal visible={this.props.isClassCropperModalVisible}
                   onOk={() => this.handleSubmit()} onCancel={() => this.props.onClose()}
            >
                <div>
                    <div>
                        <div>
                            <Cropper
                                src={this.state.src}
                                onInitialized={this.onCropperInit.bind(this)}
                                // Cropper.js options
                                viewMode={1}
                                zoomable={false}
                                aspectRatio={1} // 固定为1:1  可以自己设置比例, 默认情况为自由比例
                                guides={true}
                            />
                        </div>
                    </div>
                </div>
            </Modal>
        )
    }
}

import React from 'react'
import './style.css'
import Header from '../../components/Header/index'
import { List, WhiteSpace, Carousel } from 'antd-mobile'

import demoPic1 from '../../assets/CustomizedDemoPic-1.png'
import demoPic2 from '../../assets/CustomizedDemoPic-2.png'
import demoPic3 from '../../assets/CustomizedDemoPic-3.png'

class PHH extends React.Component {
    state = {
        PHHItems: [
            {
                label: '9.99元定制',
                route: '',
                id: 999
            },
            // {
            //     label: '19.99元定制',
            //     route: '',
            //     id: 1999
            // },
            // {
            //     label: '49.99元定制',
            //     route: '',
            //     id: 4999
            // },
        ],
        imgHeight: 160,
        ImgList: [
            {
                type: '9.99',
                items: [
                    demoPic1,
                    demoPic2,
                    demoPic3,
                ]
            },
            // {
            //     type: '19.99',
            //     items: [
            //         'https://picsum.photos/1902/1080',
            //         'https://picsum.photos/500/500',
            //         'https://picsum.photos/300/400',
            //     ]
            // },
            // {
            //     type: '49.99',
            //     items: [
            //         'https://picsum.photos/1902/1080',
            //         'https://picsum.photos/500/500',
            //         'https://picsum.photos/300/400',
            //     ]
            // }
        ]
    }
    render() {
        return (
            <div className='phhPage'>
                <Header title='拼盒盒' theme={{ mode: 'dark' }} />
                <WhiteSpace size='md' />
                <div className='phhListChange'>
                    <List >
                        {
                            this.state.PHHItems.map((item, index) =>
                                <div key={index} >
                                    <List.Item arrow='horizontal' onClick={() => { this.props.history.push(`/phh/customized/${item.id}`) }}>
                                        {item.label}
                                    </List.Item>
                                    <div>
                                        <List.Item>
                                            <div className='PHHCarouselChange'>
                                                <span style={{ position: 'absolute', zIndex: 9999, fontWeight: 600, left: '50%', top: 25, transform: 'translateX(-50%)' }}>{`${this.state.ImgList[index].type}元接稿画师展示`}</span>
                                                <Carousel
                                                    infinite
                                                    autoplay={true}
                                                    dots={false}
                                                >
                                                    {
                                                        this.state.ImgList[index].items.map((item, index) =>
                                                            <a
                                                                key={index}
                                                                // href=""
                                                                style={{ display: 'inline-block', width: '100%', height: this.state.imgHeight }}
                                                            >
                                                                <img
                                                                    src={item}
                                                                    alt=""
                                                                    style={{ width: '100%', verticalAlign: 'top', height: '100%', opacity: '0.8' }}
                                                                    onLoad={() => {
                                                                        // fire window resize event to change height
                                                                        window.dispatchEvent(new Event('resize'));
                                                                        this.setState({ imgHeight: 'auto' });
                                                                    }}
                                                                />
                                                            </a>
                                                        )
                                                    }
                                                </Carousel>
                                            </div>
                                        </List.Item>
                                    </div>
                                </div>
                            )
                        }
                    </List>
                </div>

            </div>
        )
    }
}
export default PHH

import React from 'react'
import CustomBreadcrumb from '../../components/CustomBreadcrumb/index'
import TypingCard from '../../components/TypingCard'

export default class About extends React.Component {
    render() {
        return (
            <div>
                <CustomBreadcrumb arr={['关于我们']}/>
                <TypingCard
                    source={'崇新数字科技有限公司竭诚为您服务'}
                    title='关于我们'
                    extra={null}
                />
            </div>
        )
    }
}

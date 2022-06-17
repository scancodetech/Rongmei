import React from 'react'
import './style.css'
import Header from '../../../components/Header/index'

export default class Agreement extends React.Component {
    render() {
        return (
            <div className='agreementPage'>
                <Header title='测试服务协议' />
                <div style={{ color: "white", fontSize: 15 }}>
                    <p>欢迎参加由南京崇新数字科技有限公司（以下简称“我们”）为测试合作方（以下简称“您”）提供的新产品“跨次元”平台测试服务（以下简称“测试服务”）！</p>
                    <p>本测试服务协议（以下简称本“协议”）载列了测试服务方式及期限、使用规范、知识产权归属及其他相关条款。我们在此特别提醒您认真阅读、充分理解本协议各条款，特别是其中所涉及的免除、减轻我们责任的条款、对您权利限制等条款。其中，限制、免责条款可能以黑体加粗或加下划线的形式提示您重点注意。请您审慎阅读并选择接受或不接受本协议。<strong>一旦您勾选本协议并提交，即表示您同意遵守本协议的条款与条件，本协议将对您产生法律约束力。如果您不同意本协议，请不要进行任何后续操作。</strong></p>
                    <p></p>
                    <p><strong>1. 测试服务方式及期限</strong></p>
                    <p>1.1 我们针对特定新产品“跨次元”平台（以下简称“测试产品”），为验证其功能效果及测试稳定性，邀请您进行体验及试用，同时由您就试用体验向我们提供产品建议、需求、满意度以及问题反馈等试用反馈。</p>
                    <p>1.2 我们向您提供不超过12个月的测试服务，自您勾选并提交本协议之日起开始计算。</p>
                    <p><strong>2. 测试服务的使用</strong></p>
                    <p>2.1 您理解并同意，您只能在本协议所载测试服务期限或我们另行书面同意的期限内使用测试服务，前述期限到期后，您须配合我们对测试服务期间所安装的应用程序、软件等进行迭代。测试数据会按情况保留或删除。</p>
                    <p>2.2 您仅可以为测试服务之目的在我们明示认可的终端设备上安装、使用和运行我们提供的测试产品，不得为任何其他目的（包括但不限于商业运营、技术研究）使用测试产品或对其进行任何操作。</p>
                    <p>2.3 为取得良好的测试效果，您同意在测试服务期间根据我们的要求定期提供问题反馈、意见、建议及需求等试用反馈，并定期参加我们为测试服务之目的组织的会议或讨论。我们将及时协助您解答问题并就您的需求进行反馈。</p>
                    <p>2.4 您在使用测试服务过程中应当遵守本协议、《保密协议》及相关法律法规的要求，不得进行任何违反前述要求的操作，包括但不限于：</p>
                    <p>(1) 删除测试产品上关于我们的任何版权信息，修改、删除或避开测试产品为保护知识产权而设置的技术措施；</p>
                    <p>(2) 对测试产品进行逆向工程、反汇编、反编译、分解拆卸等处理或试图以其他方式发现测试产品和/或相关接口的源代码、目标代码、系统程序、过程、算法、方法或技术；</p>
                    <p>(3) 复制或模仿测试产品的任何设计、界面、功能，生成与测试产品相类似或竞争的产品或服务；</p>
                    <p>(4) 未经我们事先书面许可，对测试产品进行修改或制造、创建衍生产品或派生其他产品；</p>
                    <p>(5) 对测试产品及相关服务内容进行出租、出借、复制、修改、链接、转载、汇编、发表、出版、建立镜像站点等操作；</p>
                    <p>(6) 对测试产品及相关服务或者测试产品及相关服务运行过程中释放到任何终端内存中的数据、软件运行过程中客户端与服务器端的交互数据，以及测试产品及相关服务运行所必需的系统数据，进行复制、修改、增加、删除、挂接运行或创作任何衍生作品，形式包括但不限于使用插件、外挂或非经我们授权的第三方工具/服务接入测试产品及相关服务和相关系统；</p>
                    <p>(7) 通过修改或伪造测试产品运行中的指令、数据，增加、删减、变动测试产品的功能或运行效果，或者将用于上述用途的软件、方法进行运营或向公众传播，无论这些行为是否为商业目的；</p>
                    <p>(8) 通过非我们开发、授权的第三方软件、插件、外挂、系统，登录或使用测试产品及服务，或制作、发布、传播上述工具；</p>
                    <p>(9) 自行、授权他人或利用第三方软件对测试产品及相关服务及其组件、模块、数据等进行干扰；</p>
                    <p>(10) 以违反任何适用法律法规、侵犯他人合法权益、可能造成利益冲突、危害测试服务目的或违背良好社会风尚的方式使用测试产品；</p>
                    <p>(11) 在未经我们同意或违反适用法律法规的前提下，将测试产品用于核设施、军事用途、医疗设施、交通通讯或其他限制、敏感行业或领域；</p>
                    <p>(12) 其他以任何不合法或违反本协议的方式、为任何不合法或违反本协议的目的使用测试产品的行为。</p>
                    <p>2.5 您理解并确认，为更好地实现测试效果，我们有权获取您在使用测试服务期间产生的使用数据和/或用户个人信息我们承诺仅将该等数据应用于测试服务及优化产品功能之目的，并遵守本协议及相关法律法规以保护前述数据的安全。如果您停止使用测试产品或测试服务因任何原因结束后，我们可以对前述数据进行删除或匿名化处理，我们没有义务向您返还任何前述数据。</p>
                    <p><strong>3. 知识产权</strong></p>
                    <p>3.1 测试产品的一切著作权、商标权、专利权、商业秘密等知识产权，以及与测试产品相关的所有信息内容（包括但不限于图片、视频及相关数据、资料和信息）的知识产权及其他权益均归属于我们。我们授权您在本协议约定的目的、范围及期限内使用测试产品及相关资料，该项授权不可转让、不可再授权。除前述使用权外，我们未授予您任何其他附加权利。</p>
                    <p>3.2 未经我们书面同意，您不得使用测试产品及相关资料开发任何知识产权，也不得以任何形式侵犯我们的合法权益。</p>
                    <p><strong>4. 免责条款</strong></p>
                    <p>4.1 我们仅按照“原样”、“现状”向您提供测试服务。我们不保证测试服务的稳定性、持续性及服务质量，不保证测试服务不会中断或没有错误，不保证其会纠正测试产品的所有问题，也不保证测试服务能满足您的所有要求。如因此给您造成任何损失，我们不承担责任。</p>
                    <p>4.2 您理解并同意，我们有权根据测试服务及业务的需要随时中断、暂停或终止您对于测试服务的使用，在这种情况下，我们会尽合理努力通知您，如因此给您造成任何损失，我们不承担责任。</p>
                    <p>4.3 您理解并同意，在测试服务开展过程中，可能遇到不可抗力等因素（不可抗力是指不能预见、不能克服并不能避免的客观事件），包括但不限于政府行为、自然灾害、网络原因、黑客攻击、战争或任何其它类似事件。出现不可抗力情况时，我们将努力在第一时间及时修复，但因不可抗力给您造成的损失（如数据丢失、泄露），我们不承担责任。</p>
                    <p>4.4 我们有权自行决定、确认、更改及调整测试产品的功能服务、使用范围以及测试产品是否最终正式发布，如涉及前述更改或调整事项，我们将通知您查阅，如您拒绝更改或调整，可以退出测试服务。</p>
                    <p><strong>5. 违约责任</strong></p>
                    <p>如果您违反本协议或您应遵守的其他相关规则的任何条款，我们有权根据独立判断，视情况在不经事先通知您的情况下采取合理的处理措施，包括但不限于限制、暂停、终止您使用测试服务的部分或全部功能，您需自行承担由此导致的后果及损失。如因您的违约行为使我们遭受任何损失（包括但不限于因您的行为导致我们及相关方向任何第三方支付赔偿、遭受国家机关罚款等），您应当承担全部责任。</p>
                    <p><strong>6. 其他</strong></p>
                    <p>6.1 本协议的订立、解释、履行及争议解决，均适用中华人民共和国（不包括香港、澳门特别行政区和台湾地区）法律。因本协议及有关事项发生的争议，双方应本着诚实信用原则，通过友好协商解决。经协商仍无法达成一致的，双方均可向本协议签订地（即南京市江北新区）有管辖权的人民法院提起诉讼。</p>
                    <p>6.2 如本协议的任何条款被有管辖权的法院判定为无效或不可执行，本协议剩余的条款应保有完全的效力。</p>
                    <p>6.3 本协议是双方之间就测试服务合作的特别约定，如本协议与双方签署的其他合同约定存在冲突，以本协议内容为准。</p>
                    <p>6.4 本协议自您签署完成之日起生效，至双方权利义务履行完毕之日起终止。</p>
                
                </div>
            </div >
        )
    }
}
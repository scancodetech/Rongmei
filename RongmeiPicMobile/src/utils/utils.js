const COS = require('cos-js-sdk-v5');

function accMul(arg1, arg2) {
    let m = 0;
    const s1 = arg1.toString();
    const s2 = arg2.toString();
    m += s1.split(".").length > 1 ? s1.split(".")[1].length : 0;
    m += s2.split(".").length > 1 ? s2.split(".")[1].length : 0;
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / 10 ** m;
}

export function digitUppercase(n) {
    const fraction = ['角', '分'];
    const digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    const unit = [['元', '万', '亿'], ['', '拾', '佰', '仟', '万']];
    let num = Math.abs(n);
    let s = '';
    fraction.forEach((item, index) => {
        s += (digit[Math.floor(accMul(num, 10 * 10 ** index)) % 10] + item).replace(/零./, '');
    });
    s = s || '整';
    num = Math.floor(num);
    for (let i = 0; i < unit[0].length && num > 0; i += 1) {
        let p = '';
        for (let j = 0; j < unit[1].length && num > 0; j += 1) {
            p = digit[num % 10] + unit[1][j] + p;
            num = Math.floor(num / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
    }

    return s
        .replace(/(零.)*零元/, '元')
        .replace(/(零.)+/g, '零')
        .replace(/^整$/, '零元整');
}


/**
 * 生成指定区间的随机整数
 * @param min
 * @param max
 * @returns {number}
 */
export function randomNum(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}

/**
 * 计算提示框的宽度
 * @param str
 * @returns {number}
 */
export function calculateWidth(arr) {
    return 30 + arr[0].length * 15
}

/**
 * 图片预加载
 * @param arr
 * @constructor
 */
export function preloadingImages(arr) {
    arr.forEach(item => {
        const img = new Image()
        img.src = item
    })
}

export const getDateDiff = (dateTimeStamp) => {
    const minute = 1000 * 60;
    const hour = minute * 60;
    const day = hour * 24;
    const month = day * 30;
    const now = new Date().getTime();
    const diffValue = now - dateTimeStamp;
    if (diffValue < 0) {
        return "";
    }
    const monthC = diffValue / month;
    const weekC = diffValue / (7 * day);
    const dayC = diffValue / day;
    const hourC = diffValue / hour;
    const minC = diffValue / minute;
    let result;
    if (monthC >= 1) {
        result = `${Math.floor(monthC)}月前`;
    } else if (weekC >= 1) {
        result = `${Math.floor(weekC)}周前`;
    } else if (dayC >= 1) {
        result = `${Math.floor(dayC)}天前`;
    } else if (hourC >= 1) {
        result = `${Math.floor(hourC)}小时前`;
    } else if (minC >= 1) {
        result = `${Math.floor(minC)}分钟前`;
    } else
        result = "刚刚";
    return result;
}

/*
** randomWord 产生任意长度随机字母数字组合
** randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位
** xuanfeng 2014-08-28
*/
export function randomWord(randomFlag, min, max) {
    var str = "",
        range = min,
        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'];

    // 随机产生
    if (randomFlag) {
        range = Math.round(Math.random() * (max - min)) + min;
    }
    for (let i = 0; i < range; i++) {
        let pos = Math.round(Math.random() * (arr.length - 1));
        str += arr[pos];
    }
    return str;
}


/**
 * 复制内容到粘贴板
 */
export function copyToClip(content) {
    const aux = document.createElement("input");
    aux.setAttribute("value", content);
    document.body.appendChild(aux);
    aux.select();
    document.execCommand("copy");
    document.body.removeChild(aux);
}

export function formatDate(time) {
    let date = new Date(time);
    let Y = date.getFullYear() + "-";
    let M = (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "-";
    let D = (date.getDate() + 1 < 10 ? "0" + (date.getDate() + 1) : date.getDate() + 1) + " ";
    return Y + M + D;
}

export function formatDateToSecond(time) {
    let date = new Date(time);
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = (date.getDate() + 1 < 10 ? '0' + (date.getDate() + 1) : date.getDate() + 1) + ' ';
    let h = date.getHours() + ':';
    let m = date.getMinutes() + ':';
    let s = date.getSeconds();
    return Y + M + D + h + m + s;
}

export async function uploadFileToCos(key, fileObject) {
    const cos = new COS({
        SecretId: 'AKIDSkwD1ZGnW1GK8lj1OAh42nl7SYu1arXr',
        SecretKey: 'sYM7pRtBSa2iovaCfDYq2iWcltwnT0OX',
    });
    return await cos.putObject({
        Bucket: 'rongmei-1255617399', /* 必须 */
        Region: 'ap-nanjing',     /* 存储桶所在地域，必须字段 */
        Key: key,              /* 必须 */
        Body: fileObject, // 上传文件对象
        StorageClass: 'STANDARD',
        onProgress: function (progressData) {
            console.log(JSON.stringify(progressData));
        }
    });
}

export function uuid() {
    const s = [];
    const hexDigits = "0123456789abcdef";
    for (let i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    return s.join("");
}

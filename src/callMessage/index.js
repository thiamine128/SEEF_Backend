import {ElNotification} from "element-plus";

export function callError(message){
    return ElNotification({
        title: '发生错误',
        message: message,
        type: 'error',
    });
}

export function callSuccess(message){
    return ElNotification({
        title: '操作成功',
        message: message,
        type: 'success',
    });
}

export function callInfo(message){
    return ElNotification({
        title: '提示信息',
        message: message,
        type: 'info',
    });
}

export function callWarning(message){
    return ElNotification({
        title: '警告',
        message: message,
        type: 'warning',
    });
}

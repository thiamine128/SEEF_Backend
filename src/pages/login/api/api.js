import axios from "axios";
import {callError, callSuccess} from "@/callMessage";


export async function register(data) {
    try {
        const response = await axios.post('/user/register', data);
        if (response.status === 200) {
            if (response.data.code == 1) callSuccess('注册成功');
            else callError(response.data.msg);
        } else callError('网络错误');
    } catch (error) {
        //console.log('there are some errors in register');
    }
}

export async function sendEmail(email){
    try {
        const response = await axios.post('/user/sendmail', {
            email: email
        });
        if (response.status === 200) {
            // console.log('send email to ' + email);
            // console.log(response.data.code);
            if (response.data.code == 1) callSuccess('发送成功');
            else callError(response.data.msg);
        } else callError('网络错误');
    } catch (error) {
        //console.log('there are some errors in sendmail');
    }
}

export async function resetPassword(data){
    try {
        const response = await axios.post('/user/resetPassword', data);
        if (response.status === 200) {
            if (response.data.code == 1) callSuccess('新密码设置成功');
            else callError(response.data.msg);
        } else callError('网络错误');
    } catch (error) {
        //console.log('there are some errors in resetPassword');
    }
}

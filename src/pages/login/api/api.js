import axios from "axios";


export async function register(data) {
    try {
        const response = await axios.post('/user/register', data);
        if (response.status === 200) {
            if (response.data.code == 1) window.alert('注册成功');
            else window.alert(response.data.msg);
        } else window.alert('网络错误');
    } catch (error) {
        console.log('there are some errors in register');
    }
}

export async function sendEmail(email){
    try {
        const response = await axios.post('/user/sendmail', {
            email: email
        });
        if (response.status === 200) {
            console.log('send email to ' + email);
            console.log(response.data.code);
            if (response.data.code == 1) window.alert('发送成功');
            else window.alert(response.data.msg);
        } else window.alert('网络错误');
    } catch (error) {
        console.log('there are some errors in sendmail');
    }
}

export async function resetPassword(data){
    try {
        const response = await axios.post('/user/resetPassword', data);
        if (response.status === 200) {
            if (response.data.code == 1) window.alert('新密码设置成功');
            else window.alert(response.data.msg);
        } else window.alert('网络错误');
    } catch (error) {
        console.log('there are some errors in resetPassword');
    }
}

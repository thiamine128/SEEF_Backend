<template>
    <div class="bg-container"/>
    <div class="right-set">
        <div class="login-frame">
            <div class="login-content">
                <div :class="{ customFont: true }">软工教学论坛</div>
                <div :class="{ customFontEng: true }">software engineering education forum</div>
                <div class="select-set">
                    <div :class="{selectButtonSetNone: situation!==1, selectButtonSet: situation === 1}"
                        @click="setSituation(1)">
                        用户登录
                    </div>
                    <div :class="{selectButtonSetNone: situation!==2, selectButtonSet: situation === 2}"
                         @click="setSituation(2)">
                        邮箱登录
                    </div>
                    <div :class="{selectButtonSetNone: situation!==3, selectButtonSet: situation === 3}"
                         @click="setSituation(3)">
                        注册
                    </div>
                    <div :class="{selectButtonSetNone: situation!==4, selectButtonSet: situation === 4}"
                         @click="setSituation(4)">
                        找回密码
                    </div>
                </div>

                <div class="input-frame" v-if="situation !== 2 && situation !== 4">
                    <el-input v-model="username" style="width: 80%; height: 40px" placeholder="请输入用户名">
                    <template #prepend>姓名</template>
                    </el-input>
                </div>

                <div class="input-frame" v-if="situation !== 1">
                    <el-input v-model="email" style="width: 80%; height: 40px" placeholder="请输入邮箱">
                        <template #prepend>邮箱</template>
                    </el-input>
                </div>

                <div class="input-frame" v-if="situation === 3 || situation === 4">
                    <el-input v-model="code" style="width: 80%; height: 40px" placeholder="请输入验证码">
                        <template #prepend>验证</template>
                    </el-input>
                </div>

                <div class="input-frame">
                    <el-input v-model="password" style="width: 80%; height: 40px" type="password" :placeholder="passwordPlaceHolder" show-password>
                        <template #prepend>密码</template>
                    </el-input>
                </div>

                <el-button v-if="situation !== 1 && situation !== 2 && !isCool"
                    ref="codeButton" class="button-set" type="primary" plain @click="sendmailFunc()">发送验证码</el-button>

                <el-button v-if="situation !== 1 && situation !== 2  && isCool"
                           ref="codeButton" class="button-set" type="primary" plain disabled>{{countdown}}秒后重新发送</el-button>
                <div/>
                <el-button class="button-set" type="primary" plain @click="transit()">{{buttonName}}</el-button>

            </div>

            <img class="sosSet" alt="404" src="@/assets/login/sos.png">
        </div>
    </div>
</template>

<script>
import {useStore} from "vuex";
import {getPositionDataWithUnit} from "element-plus";
import {register, resetPassword, sendEmail} from "@/pages/login/api/api";
import {ref} from "vue";

export default {
    name: "login",
    data(){
        return{
            store: useStore(),
            username: '',
            password: '',
            code: '',
            email: '',
            situation: 1,
            buttonName: '登录',
            passwordPlaceHolder: '请输入密码',
            countdown: 180,
            isCool: false,
            intervalId: ref(null),
            clickSet: true
        }
    },
    methods:{
        setSituation(num){
            this.situation = num;
            if (num === 4) this.buttonName = '设置新密码';
            else if (num === 3) this.buttonName = '注册';
            else this.buttonName = '登录';
            if (num === 4) this.passwordPlaceHolder = '请输入新密码';
            else this.passwordPlaceHolder = '请输入密码';
        },
        transit(){
            if (this.clickSet){
                switch (this.situation){
                    case 1: this.login(); break;
                    case 2: this.eLogin(); break;
                    case 3: this.registerFunc(); break;
                    case 4: this.reset(); break;
                }
                this.clickSet = false;
                setTimeout(()=>{
                    this.clickSet = true;
                    console.log('clickset is true now');
                }, 5000);
            }

        },
        reset(){
            let data = {
                email: this.email,
                verificationCode: this.code,
                password: this.password
            };
            resetPassword(data);
        },
        async login(){
            let credential = {"name": this.username, "password": this.password};
            await this.store.dispatch('login', credential);
        },
        async eLogin(){
            let credential = {"email": this.email, "password": this.password};
            await this.store.dispatch('eLogin', credential);
        },
        registerFunc(){
            let data = {
                name: this.username,
                email: this.email,
                verificationCode: this.code,
                password: this.password
            };
            register(data);
        },
        sendmailFunc(){
            sendEmail(this.email);
            this.handleCool();
        },
        handleCool(){
            if (!this.isCool) {
                this.isCool = true;
                this.countdown = 180;
                this.intervalId = setInterval(this.countdownTimer, 1000)
            }
        },
        countdownTimer(){
            if (this.countdown > 0) {
                this.countdown--;
            } else {
                clearInterval(this.intervalId);
                this.isCool = false;
            }
        }
    }
}
</script>

<style scoped>
.sosSet{
    margin-top: 60%;
    width: 80%;
    aspect-ratio: 5/2;
    margin-bottom: 10%;
}
.selectButtonSet{
    width: 25%;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-bottom: 2px solid #00bbff;
    font-weight: bold;
    font-size: 10px;
    background-color: rgba(22, 22, 22, 0.05);
    cursor: pointer;
    transition: border-bottom 0.2s, background-color 0.2s;
}
.selectButtonSetNone{
    width: 25%;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 10px;
    cursor: pointer;
    border-bottom: 1px solid #d2d2d2;
}

.select-set{
    width: 80%;
    height: 30px;
    display: flex;
    flex-direction: row;
    align-items: center;
}

.button-set{
    width: 80%;
    margin-top: 1px;
}
.customFont {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 20px;
    text-align: center;
    width: 80%;
}
.customFontEng {
    font-family: 'consolas', sans-serif;
    font-weight: bold;
    font-size: 6px;
    text-align: center;
    width: 80%;
    margin-top: -5px;
}
.bg-container {
    background: url('@/assets/login/login_bg.png');
    opacity: 0.7;
    background-size: cover;
    position: fixed;
    height: 100vh;
    width: 100vw;
    z-index: -1;
    top: 0;
    left: 0;
}
.right-set{
    display: flex;
    flex-direction: row;
    justify-content: right;
    width: 100%;
    height: 100%;
}
.login-frame{
    width: 300px;
    height: 100%;
    background-color: rgba(255, 255, 255, 0.9);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    border-left: 1px solid rgba(22, 22, 22, 0.2);
}
.login-content{
    width: 100%;
    height: 400px;
    margin-top: 40%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 5px;
}
.input-frame{
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    width: 100%;
}
</style>

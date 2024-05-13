<template>
    <div class="col"
         @mouseenter="hover = true"
         @mouseleave="hover = false"
         @mouseover="hover = true">
        <a  :href="dest" class="linkStyle">
            <div v-if="buttonName !== '动态' || !badgeSet" :class="{ customFont: true }">{{buttonName}}</div>
            <el-badge v-if="buttonName === '动态' && badgeSet " :value="100" :max="99" type="primary">
                <div :class="{ customFont: true }">{{buttonName}}</div>
            </el-badge>
        </a>
    </div>
</template>

<script>
import store from "@/store/store";
export default {
    name: "navButton",
    props: ['buttonName', 'dest'],
    data(){
        return{
          hover: false, badgeSet: false, ws: null, badgeValue: 0
        }
    },
    methods:{
        setupWebSocket() {
            this.ws = new WebSocket(
                `ws://123.249.103.199:8080/api/webSocket/${store.getters.getToken}`
            );

            this.ws.onopen = function(event) {
                console.log('WebSocket 连接已打开', event);
            };

            this.ws.onerror = function(error) {
                console.error('WebSocket 出错', error);
            };

            this.ws.onmessage = function(event) {
                // 收到信息event.data
                console.log(event.data);
            };

            this.ws.onclose = function() {
                console.log('WebSocket 连接已关闭');
            };
        }
    },
    mounted() {
        if (this.buttonName === '动态') this.setupWebSocket();
    },
}
</script>

<style scoped>
.col{
    width: 100px;
    height: 90px;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: background-color 0.5s ease ;
}
.col:hover{
    background-color: rgba(255, 255, 255, 0.35);
}

.linkStyle{
    text-decoration: none;
    color: #000;
    cursor: pointer;
}
.customFont {
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-weight: bold;
    font-size: 16px;
    color: white;
}
</style>

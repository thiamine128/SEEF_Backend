<template>
    <div class="col"
         @mouseenter="hover = true"
         @mouseleave="hover = false"
         @mouseover="hover = true" @click="goto">
        <a class="linkStyle">
            <div v-if="buttonName !== '动态' || !badgeSet" :class="{ customFont: true }">{{buttonName}}</div>
            <el-badge v-if="buttonName === '动态' && badgeSet " :value="badgeValue" :max="99" type="primary">
                <div :class="{ customFont: true }">{{buttonName}}</div>
            </el-badge>
        </a>
    </div>
</template>

<script>
import store from "@/store/store";
import {callError} from "@/callMessage";
import {fetchTopic} from "@/pages/blog/api";
export default {
    name: "navButton",
    props: ['buttonName', 'dest'],
    data(){
        return{
          hover: false, badgeSet: false, badgeValue: 0
        }
    },
    methods:{

        goto(){
            if(this.dest === '/logout') store.dispatch('logout');
            else this.$router.push(this.dest);
        },

        async getEventCount(){
            try{

                const response = await this.$http.get('event/eventCount');
                console.log('event count: ');
                console.log(response);

                if (response.status === 200){
                    if (response.data.data != null && response.data.data > 0){
                        this.badgeSet = true;
                        this.badgeValue = response.data.data;
                    }
                }else callError('网络错误');

            }catch(error){
                callError(error);
            }

        }
    },
    mounted() {
        if (this.buttonName === '动态') this.getEventCount();
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

<template>
    <div class="frameSet">
        <div class="personContainer">

            <div class="personInfo">
                <div>
                    <img class="portraitSet" src="@/assets/blog/testPortrait.jpg" alt="404 not found">
                    <div :class="{ nameFont: true }"> 阮阳栋 </div>
                </div>

                <div class="textSet">
                    <div :class="{ contentFont: true }"> init.c:	mips_init() is called
                        Memory size: 65536 KiB, number of pages: 16384
                        to memory 80430000 for struct Pages.
                        pmap.c:	 mips vm init success
                        envid2env() work well! </div>
                    <div :class="{ dateFont: true }"> 发布于2024年4月23日 </div>
                </div>
            </div>

            <div class="bottom-style">

                <reply-box/>
                <reply-box/>
                <reply-box/>
                <reply-box/>

            </div>

        </div>
    </div>
</template>

<script>
import ReplyBox from "@/pages/blog/components/replyBox/index.vue";

export default {
    name: "commentBox",
    components: {ReplyBox},
}
</script>

<style scoped>

.portrait-name{
    display: flex;
    flex-direction: column;
}

.bottom-style{
    display: flex;
    flex-direction: column;
    width: 100%;
}

.frameSet{
    width: 100%;
    min-height: 130px;
    background-color: rgba(255, 255, 255, 0.9);
    border: 1px solid rgba(155, 155, 155, 0.2);
    display: flex;
}

.personContainer{
    margin-top: 5px;
    margin-left: 5px;
    width: 95%;
    min-height: 112px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.personInfo{
    width: 100%;
    height: 100px;
    display: flex;
    flex-direction: row;
}

.portraitSet{
    height: 80px;
    width: 80px;
    border: 2px solid rgba(205, 205, 205, 0.2);
}
.textSet{
    height: 100%;
    display: flex;
    flex-direction: column;
    margin-left: 8px;
}
.contentFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    padding: 10px;
    text-align: left;
    width: 680px;
    height: 70px;
    background-color: rgba(22, 22, 22, 0.07);
    overflow-x: auto;
}
.dateFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: right;
    color: gray;
    width: 700px;
    height: 20px;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 12px;
    font-weight: bold;
    text-align: center;
    margin-top: -5px;
    color: #0066ff;
}
</style>

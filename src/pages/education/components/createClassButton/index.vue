<template>
    <div class="create-class-container">
        <button class="first-button" @click="showCreatePage">创建班级</button>

        <div class="create-class-modal" v-if="showCreateClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showCreateClassModal = false">&times;</span>

                <h3>创建班级</h3>

                <form @submit.prevent="createClass">
                    <div class="input-group">
                        <label style="text-align: left" for="courseId">课程编号</label>
                        <input placeholder="请输入课程编号" type="number" id="courseId" v-model="courseId" required min="1">
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="className">班级名称</label>
                        <input placeholder="请输入班级名称" type="text" id="className" v-model="className" required>
                    </div>
                    <div class="input-group">
                        <label style="text-align: left" for="classTime">上课时间</label>
<!--                        <p style="font-size: 11px; color: grey;">请填写开课周次及时段，形如：<br>1~16周星期一，3~4节；1~8周星期二，5~6节；-->
<!--                        </p>-->
                        <input placeholder="例如1~16周星期一，3~4节" type="text" id="classTime" v-model="classTime" required>
                    </div>

                    <div class="input-group">
                        <label style="text-align: left" for="classLocation">上课地点</label>
                        <input placeholder="请输入上课地点" type="text" id="classLocation" v-model="classLocation" required>
                    </div>

                    <button style="width: 100%;" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {createClassAPI} from "@/pages/education/components/createClassButton/api/api";
import  {toRefs} from 'vue';
import {callError} from "@/callMessage";

export default {
    name: "createClassButton",
    data() {
        return {
            showCreateClassModal: false,
            courseId: null,
            className: null,
            classTime: '',
            classLocation: '',
            teacherId: null,
        };
    },

    methods: {

        showCreatePage() {
            this.showCreateClassModal = true;
        },

        createClass() {
            let transTime = this.classTime;
            try {
                transTime = this.parseTimeData(transTime);
            } catch (error) {
                callError('时间设置非法，请重新设置');
            }
            let data = {
                name: this.className,
                courseId: this.courseId,
                timeData: transTime,
                location: this.classLocation,
                time: this.classTime,
            };
            createClassAPI(data);
            this.showCreateClassModal = false;
        },
        parseTimeData(str) {
            let timeData = [];
            str.replace(';', '；');
            if (str.endsWith('；')) {
                str = str.slice(0, -1); // 如果字符串末尾是分号，就删除最后一个字符
            }
            let segments = str.split("；"); // 以中文分号分割不同的时间段
            // console.log(segments);
            let mp = new Map();
            mp.set('一', 1);mp.set('二', 2);mp.set('三', 3);mp.set('四', 4);
            mp.set('五', 5);mp.set('六', 6);mp.set('日', 7);mp.set('天', 7);
            segments.forEach(segment => {
                let parts = segment.split("星期"); // 以"星期"分割周数和具体时间
                let weekPart = parts[0];
                let dayPart = parts[1].split("，")[0] - 1;
                let timePart = parts[1].split("，")[1];

                let weekRange = weekPart.split("~");
                let startWeek = parseInt(weekRange[0]);
                let endWeek = parseInt(weekRange[1]);

                let day = mp.get(dayPart);

                let timeRange = timePart.split("节")[0];
                let start = parseInt(timeRange.split("~")[0]) - 1;
                let end = parseInt(timeRange.split("~")[1]);
                if(end > 14 || endWeek > 20) {
                    throw new Error("rnm还上课");
                }
                let len = end - start;

                for (let week = startWeek; week <= endWeek; week++) {
                    timeData.push({
                        "week": week,
                        "day": day,
                        "start": start,
                        "len": len
                    });
                }
            });
            return timeData;
        }
    },

}
</script>

<style scoped>
/*.create-class-container {*/
/*    display: flex;*/
/*    justify-content: center;*/
/*    align-items: center;*/
/*    margin: 20px;*/
/*}*/

/*.create-class-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*}*/

/*.create-class-modal {*/
/*    position: fixed;*/
/*    top: 50%;*/
/*    left: 50%;*/
/*    transform: translate(-50%, -50%);*/
/*    background-color: white;*/
/*    padding: 20px;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);*/
/*    z-index: 1000;*/
/*}*/

/*.modal-content {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    align-items: center;*/
/*}*/

/*.close-icon {*/
/*    position: absolute;*/
/*    top: 10px;*/
/*    right: 10px;*/
/*    font-size: 20px;*/
/*    cursor: pointer;*/
/*}*/

/*.input-group {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    margin-bottom: 15px;*/
/*}*/

/*.input-group label {*/
/*    margin-bottom: 5px;*/
/*}*/

/*.input-group input {*/
/*    padding: 5px;*/
/*    border: 1px solid #ccc;*/
/*    border-radius: 3px;*/
/*    width: 200px;*/
/*}*/

.input-group input[type="number"] {
    width: 188px;
}

/*.first-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
/*    transition: background-color 0.3s ease;*/
/*    font-size: 20px;*/
/*}*/

/*.first-button:hover {*/
/*    background-color: #0056b3;*/
/*}*/
.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 180px;
    height: 60px;
}

.create-class-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.create-class-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    z-index: 1000;
}

.modal-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.close-icon {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 20px;
    cursor: pointer;
}

.input-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
    width: 200px;
}

.input-group label {
    margin-bottom: 5px;
}

.input-group input {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.first-button {
    width: 100%;
    height: 100%;
    background-color: rgba(11, 11, 11, 0.02);
    border: none;
    cursor: pointer;
    /*box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
    transition: background-color 0.3s ease;
    font-size: 16px;
    font-weight: bold;
    color: #2b2b2b;
}

.first-button:hover {
    background-color: rgba(55, 55, 55, 0.1);
}
</style>

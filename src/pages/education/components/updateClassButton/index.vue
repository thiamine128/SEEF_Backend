<template>
    <div class="update-class-container">
        <button class="first-button" @click="showUpdateClassPage">更新班级信息</button>

        <div class="update-class-modal" v-if="showUpdateClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showUpdateClassModal = false">&times;</span>

                <h3>更新班级信息</h3>

                <form @submit.prevent="updateClass">

<!--                    <div class="input-group">-->
<!--                        <label for="className">课程/班级？名称:</label>-->
<!--                        <input type="text" id="className" v-model="className" required>-->
<!--                    </div>-->

                    <div class="input-group">
                        <label for="className">班级名称:</label>
                        <input type="text" id="className" v-model="className" required>
                    </div>

                    <div class="input-group">
                        <label for="classTime">上课时间:</label>
                        <p style="font-size: 11px; color: grey;">请填写开课周次及时段，形如：<br>1~16周星期一，3~4节；1~8周星期二，5~6节；
                        </p>
                        <input type="text" id="classTime" v-model="classTime" required>
                    </div>

                    <div class="input-group">
                        <label for="classLocation">上课地点:</label>
                        <input type="text" id="classLocation" v-model="classLocation" required>
                    </div>



                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {updateClassAPI} from "@/pages/education/components/updateClassButton/api/api";
import  {toRefs} from 'vue';
import {getClassData, getCourseData} from "@/pages/education/api";
import {callError} from "@/callMessage";

export default {
    name: "updateClassButton",
    data() {
        return {
            showUpdateClassModal: false,
            className: this.classInfo.name,
            classTime: this.classInfo.time,
            classLocation: this.classInfo.location,
            data: null,
        };
    },
    props: {
        classId: null,
        classInfo: null,
    },
    methods: {
        showUpdateClassPage() {
            this.showUpdateClassModal = true;
        },

        async updateClass(event) {
            let data = await getClassData(this.classId);
            let toBeUpdate = false;
            // console.log('before',data);
            if(this.classLocation !== this.classInfo.location) {
                data.location = this.classLocation;
                toBeUpdate = true;
                // console.log('change location');
            }
            else {
                data.location = this.classInfo.location;
            }
            if(this.classTime !== this.classInfo.time) {
                let lastIndex = this.classTime.lastIndexOf('节');
                if (lastIndex !== -1) {
                    this.classTime = this.classTime.substring(0, lastIndex + 1);
                }
                data.time = this.classTime;
                let transTime = this.classTime;
                try {
                    transTime = this.parseTimeData(transTime);
                    toBeUpdate = true;
                    data.timeData = transTime;
                    // console.log('change time');
                } catch (error) {
                    callError('时间设置非法，请重新设置');
                    return ;
                }
            }
            else {
                data.time = this.classInfo.time;
                data.timeData = this.classInfo.timeData;
            }
            if(this.className !== this.classInfo.className) {
                data.name = this.className;
                // console.log('change location');
                toBeUpdate = true;
            }
            else {
                data.name = this.classInfo.className;
            }
            // console.log('after', data);
            if(toBeUpdate) {
                await updateClassAPI(data);
                this.showUpdateClassModal = false;
            }
            else
                callError('未修改班级信息');
        },
        parseTimeData(str) {
            let timeData = [];
            str.replace(';', '；');
            if (str.endsWith('；')) {
                str = str.slice(0, -1);
            }
            let segments = str.split("；");
            // console.log(segments);
            let mp = new Map();
            mp.set('一', 0);mp.set('二', 1);mp.set('三', 2);mp.set('四', 3);
            mp.set('五', 4);mp.set('六', 5);mp.set('日', 6);mp.set('天', 6);
            segments.forEach(segment => {
                let parts = segment.split("星期"); // 以"星期"分割周数和具体时间
                let weekPart = parts[0];
                let dayPart = parts[1].split("，")[0];
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
    }
}
</script>

<style scoped>
.update-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px;
}

.update-class-button{
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.update-class-modal {
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
    font-size: 14px;
}

.input-group input {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.input-group input[type="number"] {
    width: 80px;
}

.first-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transition: background-color 0.3s ease;
    font-size: 20px;
}

.first-button:hover {
    background-color: #0056b3;
}
</style>

<template>
  <div class="timetable w100 h100">
    <div class="time-b w100">
      <div class="time-controller">
        <el-button-group style="margin-top: 20px">
          <el-button type="primary" icon="arrow-left" @click="changeCount(-1)"></el-button>
          <el-button round>第{{ currentWeek }}周</el-button>
          <el-button type="primary" icon="arrow-right" @click="changeCount(1)"></el-button>
        </el-button-group>
      </div>
    </div>
    <div class="timetable-b w100">
      <table class="timetable-content w100">
        <thead>
          <tr>
            <th></th>
            <th v-for="(item1, index1) in weeks" :key="index1">
              {{ "周" + numberToChinease(item1 + 1, "week") }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item2, index2) in maxCourseLength" :key="index2">
            <td style="height: 96%; display: flex; align-content: center; justify-content: center">
              <p style="display: flex; align-items: center">{{ "第" + numberToChinease(item2) + "节" }}</p>
            </td>
            <td v-for="(item3, index3) in weeks" :key="index3" :rowspan="timedata[index2][index3].len ? timedata[index2][index3].len : ''" v-show="timedata[index2][index3].len > 0">
              <div
                class="dmsjandjs-b"
                :style="[
                  {
                    background: timedata[index2][index3].courseId
                      ? numberToColor(timedata[index2][index3].courseId)
                      : 'rgba(255,255,255,0.9)',
                  },
                  { color: 'rgba(255,255,255,0.9)' },
                  { borderRadius: '1px' },
                  { padding: '1px' },
                  { height: '100%' },
                  {cursor: 'default'}
                ]"
                @click="goto(timedata[index2][index3].courseId)"
              >
              <p style="font-weight: bold">{{ timedata[index2][index3].courseName }}</p>
              <p>{{ timedata[index2][index3].location }}</p>

              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>


<script>
import {callError} from "@/callMessage";
import {numberToColor} from "@/pages/education/util/color"

export default {
  name: "timeTable",
  data() {
    return {
      startTime: "startTime",
      endTime: "endTime",
      weeks: [0, 1, 2, 3, 4, 5, 6],
      maxCourseLength: 14,
      timedata: [
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
        [{"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 1}, {"courseId": null, "len": 2}],
      ],
      classes: [

      ],
      currentWeek: 1
    };
  },
  mounted() {
    this.pullClasses()
  },
  methods: {
    async pullClasses(){
      try{
          const response = await this.$http.get(
              `course/getMyClasses`,{
              'Content-Type': 'application/x-www-form-urlencoded',
          });

          // console.log(response);
          if (response.status === 200) {
            this.classes = response.data.data;
            this.refresh()
          } else callError('网络错误');
      }catch (error){
          //callError(error);
      }
    },
    refresh() {
      for (let i = 0; i < 14; ++i) {
        for (let j = 0; j < 7; ++j) {
          this.timedata[i][j] = {"courseId": null, "len": 1};
        }
      }
      for (let i = 0; i < this.classes.length; ++i) {
        let cls = this.classes[i];
        if (cls.timeData)
          for (let it = 0; it < cls.timeData.length; ++it) {
            let shown = false;
            if (cls.timeData[it].week == this.currentWeek) {
              if (this.timedata[cls.timeData[it].start][cls.timeData[it].day].courseId == null) {
                this.timedata[cls.timeData[it].start][cls.timeData[it].day] = {"courseId": cls.courseId, "len": cls.timeData[it].len, "courseName": cls.courseName, "location": cls.location}
                shown = true;
              }
              let remLen = shown ? 0 : (cls.timeData[it].len - 1);
              for (let ct = 1; ct < cls.timeData[it].len; ++ct) {
                if (this.timedata[cls.timeData[it].start + ct][cls.timeData[it].day].courseId == null) {
                  if (shown == false)
                    this.timedata[cls.timeData[it].start + ct][cls.timeData[it].day] = {"courseId": cls.courseId, "len": remLen, "courseName": cls.courseName, "location": cls.location}
                  else
                  this.timedata[cls.timeData[it].start + ct][cls.timeData[it].day] = {"courseId": cls.courseId, "len": remLen}
                  shown = true;
                  remLen = 0;
                } else
                  remLen -= 1
              }
            }
          }
      }
    },
    changeCount(n) {
      if (this.currentWeek + n <= 0) return;
      this.currentWeek += n;
      this.refresh();
    },
    numberToChinease(n, identifier) {
      const chnArr = [
        "零",
        "一",
        "二",
        "三",
        "四",
        "五",
        "六",
        "七",
        "八",
        "九",
        "十",
        "十一",
        "十二",
        "十三",
        "十四"
      ];
      return identifier === "week" && (n === 0 || n === 7) ? "日" : chnArr[n];
    },
    numberToColor,
    goto(courseId){
        if (courseId){
            this.$router.push("/education/courses/" + courseId);
        }
    }
  },
};
</script>

<style scoped>
.timetable {
  background-color: rgba(241, 247, 255, 0.9);
  .w100 {
    width: 100%;
  }
  .h100 {
    height: 100%;
  }
  .time-b {
    height: 46px;
    margin-bottom: 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .time-detail {
      color: #333333;
      font-weight: 700;
      font-size: 20px;
      font-family: "Microsoft YaHei";
    }
  }
  .timetable-b {
    background-color: rgba(255, 255, 255, 0.9);
    overflow: auto;
    .timetable-content {
      height: 100%;
      table-layout: fixed;
      border-collapse: collapse;
      text-align: center;
      color: #333333;
      font-weight: 400;
      font-size: 18px;

      thead {
        height: 100px;

        th {
          border: 2px solid rgba(27, 100, 240, 0.1);
        }
      }
      tbody {
        height: 500px;

        td {
          padding: 1px;
          border: 1px solid rgba(27, 100, 240, 0.1);
          .dmsjandjs-b {
            display: flex;
            height: 100%;
            width: 100%;
            flex-direction: column;
            justify-content: center;
              align-items: center;
          }
        }
      }
    }
  }

}
.time-controller {
  margin-top: 10px;
  width: 100%;
  .el-button-group {
    .el-button {
      height: 46px;
      background: rgba(255, 255, 255, 0.9);
      font-size: 18px;
      font-weight: 600;
      border: 1px solid rgba(27, 100, 240, 0.1);
      border-radius: 55px;
      color: #1b64f0;
    }
    :nth-child(2) {
      margin: 0px 12px;
    }
  }
}
</style>

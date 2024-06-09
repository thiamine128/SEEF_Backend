import axios from "axios";
import {callError, callSuccess} from "@/callMessage";
import {createTopic} from "@/pages/blog/api";
// import createStore from '@/store/store';

export async function createCourseAPI(data) {
    let ok = false;
    // let newCourseId = null;
    try {
        const response = await axios.post('/course/create', data)
        if(response.status === 200) {
            // console.log(response.data.data.id);
            // newCourseId = response.data.data.id;
            if(response.data.code === 1) {
                callSuccess('添加课程成功，新课程id为'+response.data.data.id);
                ok = true;
            }
            else {
                callError('添加课程失败');
            }
        } else {
            callError('网络错误');
        }
    } catch (error) {
        // console.log("创建课程时出错")
    }
    if(ok) {
        let topicName = data.name;
        let introduction = data.introduction.substring(0, 20) + '...';
        await createTopic(topicName, introduction);
    }
}

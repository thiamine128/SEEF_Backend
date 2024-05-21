import store from "@/store/store";
import axios from "axios";

export async function getCourseData(courseId, useCache = true){
    console.log('读取course数据');
    let result = null;
    const pre = store.getters.getMapCourse(courseId);
    if (pre && useCache) {
        console.log('读取course缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/user?userId=${courseId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapCourse', {'courseId': courseId, 'courseData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

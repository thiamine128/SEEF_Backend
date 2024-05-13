import axios from 'axios';
import qs from 'qs';

export async function findCourseAPI(data) {
    try {
        const params = new URLSearchParams(data);

        // const params = qs.stringify(data);
        console.log(params)
        const response = await axios.get('/course/list', { params });
        // console.log(response.data.data);
        if (response.status === 200) {
            console.log(response.data.code);
            if (response.data.code === 1) {
                window.alert('查询课程成功');
                return response.data.data;
            } else {
                window.alert(response.data.msg);
            }
        } else {
            window.alert('网络错误');
        }
    } catch (error) {
        console.log('查询课程时发生错误', error);
    }
}

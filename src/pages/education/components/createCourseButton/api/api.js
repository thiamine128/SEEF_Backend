import axios from "axios";

export async function createCourseAPI(data) {
    try {
        const response = await axios.post('/course/create', data)

        console.log(response.status);
        if(response.status === 200) {
            console.log(response.data.code);
            if(response.data.code === 1) window.alert('添加成功');
            else window.alert(response.data.msg);
        } else {
            window.alert('网络错误');
        }
    } catch (error) {
        console.log("some errors happened when create a course")
    }
}

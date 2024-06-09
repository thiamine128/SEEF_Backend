import store from "@/store/store";
import axios from "axios";
import {callError, callSuccess} from "@/callMessage";
import {getUserData} from "@/pages/blog/api";

export async function getCourseData(courseId, useCache = false){
    //console.log('读取course数据');
    let result = null;
    const pre = store.getters.getMapCourse(courseId);
    if (pre && useCache) {
        //console.log('读取course缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/course/getCourseById?id=${courseId}`);
        // console.log(response.status);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapCourse', {'courseId': courseId, 'courseData': result});
        }
    }catch (error){
        callError(error);
    }
    return result;
}

export async function getClassData(classId, useCache = false){
    //console.log('读取class数据');
    let result = null;
    const pre = store.getters.getMapCourse(classId);
    if (pre && useCache) {
        //console.log('读取class缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/course/getClass?classId=${classId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapClass', {'classId': classId, 'courseData': result});
        }
    }catch (error){
        callError(error);
    }
    return result;
}

export async function getUserName(userId) {
    let data = await getUserData(userId);
    return data.name;
}

export async function getUserRealName(userId) {
    let data = await getUserData(userId);
    return data.realName;
}

export async function getMyClassAPI() {
    try {
        const response =await axios.get(`/course/getMyClasses`);
        //console.log(response.data.data);
        return response.data.data;
    }catch (error){
        callError(error);
    }
}

export async function listTeacherClassAPI(teacherId) {
    try {
        const response = await axios.get(`/course/listTeacherClass?teacherId=${teacherId}`);
        //console.log(response.data);
        return response.data.data;
    }catch (error){
        callError(error);
    }
}

export async function listMyClassAPI(userId) {
    try {
        const response = await axios.get(`/TA/listMyClass?userId=${userId}`);
        return response.data.data;
    } catch (error) {
        callError(error);
    }
}

export async function hasManageClassAPI(userId) {
    let res = await listMyClassAPI(userId);
    return res.length !== 0;
}

export async function isThisTAAPI(classId) {
    try{
        const response = await axios.get(`/TA/myClass?classId=${classId}`);
        if(response.data.code === 1) {
            return response.data.data;
        }
        else callError(response.data.msg);
    }catch (error) {
        callError('查询助教是否管理此班级时出错：' + error);
    }
}

export async function getClassStudentAPI(classId) {
    try {
        const response = await axios.get(`/course/getClassStudent?classId=${classId}`);
        if(response.data.code === 1) {
            return response.data.data;
        }
        else callError(response.data.msg);
    } catch (error) {
        callError('获取选课名单时出错：' + error);
    }
}

export async function uploadAPI() {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = ".*";
    fileInput.addEventListener("change", handleUpload);
    fileInput.click();
}

export async function handleUpload(event) {
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    try {
        const response = await axios.post('/common/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        // console.log(response.data);
        if(response.data.code === 1) {
            callSuccess('上传文件成功');
        }
        else {
            callError(response.data.msg);
        }
    }catch (error) {
        callError('上传文件时出错' + error);
    }

}

export async function deleteCourseAPI(courseId) {
    try {
        const response = await axios.delete(`/admin/deleteCourse?classId=${courseId}`);
        if(response.data.code === 1) {
            callSuccess('删除课程成功');
        }
        else {
            callError('删除课程时出错' + response.data.msg);
        }
    } catch (error) {
        callError('删除课程时出错' + error);
    }
}

export async function deleteClassAPI(classId) {
    try {
        const response = await axios.delete(`/admin/deleteClass?classId=${classId}`);
        if(response.data.code === 1) {
            callSuccess('删除班级成功');
        }
        else {
            callError('删除班级时出错' + response.data.msg);
        }
    } catch (error) {
        callError('删除班级时出错' + error);
    }
}


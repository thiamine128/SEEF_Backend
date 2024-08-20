import axios from "axios";
import {callError, callSuccess} from "@/callMessage";
import dayjs from "dayjs";
import store from "@/store/store";

export async function subscribe_func(userId){
    try{
        const response = await axios.post(`event/subscribe?user=${userId}`);
        if (response.status === 200) callSuccess('关注成功');
    }catch (error){
        //callError(error);
    }
}

export async function unSubscribe_func(userId){
    try{
        const response = await axios.post(`event/unsubscribe?user=${userId}`);
        if (response.status === 200) callSuccess('取消关注成功');
    }catch (error){
        //callError(error);
    }
}

export async function like_func(blogId, result){
    try{
        const response = await axios.post(`blog/like?blogId=${blogId}`);
        if (response.status === 200){
            if (result) callSuccess('点赞成功');
            else callSuccess('已取消点赞')
        }
    }catch (error){
        //callError(error);
    }
}

export async function favor_func(blogId, result, categoryName = '默认收藏夹'){
    try{
        //console.log('尝试收藏到 '+categoryName);
        const response = await axios.post(
            `blog/favor?blogId=${blogId}&category=${categoryName}`
        );
        if (response.status === 200){
            if (result) callSuccess('收藏成功');
            else callSuccess('已取消收藏')
        }
    }catch (error){
        //callError(error);
    }
}

export async function getUserData(userId, useCache = true){
    //console.log('读取user数据');
    let result = null;
    const pre = store.getters.getMapUser(userId);
    if (pre && useCache) {
        //console.log('读取user缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/user?userId=${userId}`);
        if (response.status === 200){
            result = response.data.data;
            result['name'] = result['nickname'];
            if (!result['name']) result['name'] = result['realName'];
            if (!result['name']) result['name'] = '非实在青少年';
            store.commit('addMapUser', {'userId': userId, 'userData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export async function getUserStuId(userId){
    //console.log('读取user数据');
    let result = null;
    try{
        const response = await axios.get(`/user?userId=${userId}`);
        if (response.status === 200){
            result = response.data.data;
            result = result["name"];
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export async function getBlogData(blogId, useCache = true){
    let result = null;
    const pre = store.getters.getMapBlog(blogId);
    if (pre && useCache) {
        //console.log('读取blog缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/blog/detail?blogId=${blogId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapBlog', {'blogId': blogId, 'blogData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export async function getCommentData(commentId){
    let result = null;
    const pre = store.getters.getMapComment(commentId);
    if (pre) {
        //console.log('读取comment缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/comment/getComment?id=${commentId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapComment', {'commentId': commentId, 'commentData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export async function getReplyData(replyId){
    let result = null;
    const pre = store.getters.getMapReply(replyId);
    if (pre) {
        //console.log('读取reply缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/reply/getReply?id=${replyId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapReply', {'replyId': replyId,'replyData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export function dateF(num) {
    return dayjs(num).format('YYYY年MM月DD日');
}

export async function deleteBlog(blogId){
    try{
        const response = await axios.delete(`blog/deleteBlog?blogId=${blogId}`);
        if (response.status === 200){
            callSuccess('删除帖子成功');
        }
    }catch(error){
        //callError(error);
    }
}

export async function deleteComment(commentId){
    try{
        const response = await axios.delete(`comment/deleteComment?commentId=${commentId}`);
        if (response.status === 200){
            callSuccess('删除评论成功');
        }
    }catch (error){
        //callError(error);
    }
}

export async function deleteReply(replyId){
    try{
        const response = await axios.delete(`reply/deleteReply?replyId=${replyId}`);
        if (response.status === 200){
            callSuccess('删除回复成功');
        }
    }catch (error){
        // callError(error);
    }
}

export async function getSubscribed(){
    let result = null;
    try{
        const response = await axios.get('event/subscribed');
        if (response.status === 200){
            result = response.data.data;
        }
    }catch (error){
        // callError(error);
    }
    return result;
}

export function uploadAvatar(){
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = ".jpg, .png";
    fileInput.addEventListener("change", handleImage);
    fileInput.click();
}

export async function handleImage(event){
    const file = event.target.files[0];
    const formData = new FormData();
    const fileUploadData = new FormData();
    formData.append('file', file);
    try {

        const response = await axios.post('user/requestUploadAvatar', formData, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        });

        //console.log(response.data);
        const keyData = {
            "name": 'ok',
            "policy": response.data.data.encodedPolicy,
            "OSSAccessKeyId": response.data.data.accessKeyId,
            "success_action_status": '200',
            "signature": response.data.data.postSignature,
            "key": response.data.data.objectName,
            "Content-Disposition": `attachment; filename=${response.data.data.objectName.replace('avatar/', '')}.png`
        };

        for (let key in keyData){
            fileUploadData.append(key, keyData[key]);
        }
        fileUploadData.append('file', file);

        const imgAxios = axios.create({baseURL: '/postFile'});

        const responseImage = await imgAxios.post(``,
        fileUploadData, {
            'Content-Type': 'application/x-www-form-urlencoded'
        });

        if (responseImage.status === 200){
            callSuccess('头像上传成功');
            setTimeout(()=>{
                location.reload();
            }, 1000);
        }

    } catch (error) {
        //console.error(error);
    }
}

export async function deleteSpace(categoryName){
    try{
        const response = await axios.delete(`space/delete?category=${categoryName}`);
        if (response.status === 200){
            callSuccess('删除分类成功');
            location.reload();
        }
    }catch (e){
        //console.log(e);
    }
}

export async function deleteCategory(categoryName){
    try{
        const response = await axios.delete(`blog/deleteFavourCategory?category=${categoryName}`);
        if (response.status === 200){
            callSuccess('删除收藏夹成功');
            location.reload();
        }
    }catch (e){
        //console.log(e);
    }
}

export async function createTopic(topicName, introduction){
    const response = await axios.post('/topic/create', {
        "name": topicName,
        "introduction": introduction
    }, {
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (response.status === 200) {
        if (response.data.code == 1) {
            //创建成功
        }
        else callError(response.data.msg);
    } else callError('网络错误');
}

export async function fetchTopic(courseName){
    let result = -1;
    const response = await axios.get(
        `topic/pagedList?page=1&pageSize=1&name=${courseName}`
    );
    if (response.status === 200) {
        if (response.data.code == 1) {
            //获取讨论区号成功
            try{
                result = response.data.data.records[0].id;
            }catch (e) {}
        }
        else callError(response.data.msg);
    } else callError('网络错误');
    return result;
}

export async function deleteSection(topicId){
    const response = await axios.delete(`topic/deleteTopic?topicId=${topicId}`);
    if (response.status === 200) {
        if (response.data.code == 1) {
            callSuccess('删除专区成功');
        }
        else callError(response.data.msg);
    } else callError('网络错误');
}

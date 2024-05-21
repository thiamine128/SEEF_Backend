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

export async function favor_func(blogId, result){
    try{
        const response = await axios.post(`blog/favor?blogId=${blogId}`);
        if (response.status === 200){
            if (result) callSuccess('收藏成功');
            else callSuccess('已取消收藏')
        }
    }catch (error){
        //callError(error);
    }
}

export async function getUserData(userId, useCache = true){
    console.log('读取user数据');
    let result = null;
    const pre = store.getters.getMapUser(userId);
    if (pre && useCache) {
        console.log('读取user缓存成功');
        return pre;
    }
    try{
        const response = await axios.get(`/user?userId=${userId}`);
        if (response.status === 200){
            result = response.data.data;
            store.commit('addMapUser', {'userId': userId, 'userData': result});
        }
    }catch (error){
        //callError(error);
    }
    return result;
}

export async function getBlogData(blogId){
    let result = null;
    const pre = store.getters.getMapBlog(blogId);
    if (pre) {
        console.log('读取blog缓存成功');
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
        console.log('读取comment缓存成功');
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
        console.log('读取reply缓存成功');
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
    return dayjs(num).format('YYYY-MM-DD');
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

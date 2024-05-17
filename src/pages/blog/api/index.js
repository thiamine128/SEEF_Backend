import axios from "axios";
import {callError, callSuccess} from "@/callMessage";

export async function subscribe_func(userId){
    try{
        const response = await axios.post(`event/subscribe?user=${userId}`);
        if (response.status === 200) callSuccess('关注成功');
        else callError('网络错误');
    }catch (error){
        callError(error);
    }
}

export async function unSubscribe_func(userId){
    try{
        const response = await axios.post(`event/unsubscribe?user=${userId}`);
        if (response.status === 200) callSuccess('取消关注成功');
        else callError('网络错误');
    }catch (error){
        callError(error);
    }
}

export async function like_func(blogId, result){
    try{
        const response = await axios.post(`blog/like?blogId=${blogId}`);
        if (response.status === 200){
            if (result) callSuccess('点赞成功');
            else callSuccess('已取消点赞')
        }
        else callError('网络错误');
    }catch (error){
        callError(error);
    }
}

export async function favor_func(blogId, result){
    try{
        const response = await axios.post(`blog/favor?blogId=${blogId}`);
        if (response.status === 200){
            if (result) callSuccess('收藏成功');
            else callSuccess('已取消收藏')
        }
        else callError('网络错误');
    }catch (error){
        callError(error);
    }
}

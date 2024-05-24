package com.software.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.software.config.WebConfiguration;
import com.software.constant.JwtClaimsConstant;
import com.software.constant.MessageConstant;
import com.software.constant.OperationTypeConstant;
import com.software.dto.*;
import com.software.entity.Blog;
import com.software.entity.Comment;
import com.software.entity.Event;
import com.software.entity.UserBlogOperation;
import com.software.exception.EmptyTagException;
import com.software.exception.InvalidCharacterException;
import com.software.mapper.*;
import com.software.result.PageResult;
import com.software.service.BlogService;
import com.software.service.EventService;
import com.software.utils.BaseContext;
import com.software.vo.BlogPreviewVO;
import com.software.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private EventService eventService;
    @Autowired
    private SpaceMapper spaceMapper;

    @Override
    public void create(BlogCreateDTO blogCreateDTO) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogCreateDTO.getTags().forEach(str -> {
            if (str.contains(";")) {
                throw new InvalidCharacterException(";", str);
            }
            if (str.isEmpty()) {
                throw new EmptyTagException(MessageConstant.EMPTY_TAG);
            }
        });
        String tags = ";" + blogCreateDTO.getTags().stream().map(str -> str + ";").collect(Collectors.joining());
        blogMapper.createBlog(blogCreateDTO.getTitle(), blogCreateDTO.getContext(), id, blogCreateDTO.getTopicId(), tags,blogCreateDTO.getCategory_id());
    }
    @Override
    public void update(BlogUpdateDTO blogCreateDTO) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogCreateDTO.getTags().forEach(str -> {
            if (str.contains(";")) {
                throw new InvalidCharacterException(";", str);
            }
            if (str.isEmpty()) {
                throw new EmptyTagException(MessageConstant.EMPTY_TAG);
            }
        });
        String tags = ";" + blogCreateDTO.getTags().stream().map(str -> str + ";").collect(Collectors.joining());
        blogMapper.updateBlog(blogCreateDTO.getTitle(), blogCreateDTO.getContent(), id, blogCreateDTO.getTopicId(), tags,blogCreateDTO.getCategory_id(),blogCreateDTO.getBlogId());
    }

    @Override
    public List<Category> getCategoryList(Long userId) {
        List <Category> categories = spaceMapper.getCategoryList(userId);
        return categories;
    }

    @Override
    public void createFavourCategory(String category) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogMapper.createFavourCategory(category,id);
    }

    @Override
    public void deleteFavourCategory(String category) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogMapper.deleteFavourCategory(category,id);
    }

    @Override
    public void updateFavourCategory(String newCategoryName, Long categoryId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        blogMapper.updateFavourCategory(newCategoryName,id,categoryId);
    }

    @Override
    public List<Category> getFavourCategoryList(Long userId) {
        List <Category> categories = blogMapper.getFavourCategoryList(userId);
        return categories;
    }

    @Override
    public String getFavourCategory(Long blogId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        return blogMapper.getFavourCategoryById(id,blogId);
    }

    @Override
    public Blog getDetail(Long blogId) {
        return blogMapper.getBlog(blogId);
    }

    @Override
    public PageResult viewComments(CommentPageQueryDto commentPageQueryDto) {
        PageHelper.startPage(commentPageQueryDto.getPage(), commentPageQueryDto.getPageSize());
        Page page = (Page) commentMapper.getComments(commentPageQueryDto.getBlogId());
        List list = page.getResult().stream().map(comment -> CommentVO.fromComment((Comment) comment, replyMapper.getReplies(((Comment) comment).getId()))).toList();
        return new PageResult(page.getTotal(), list);
    }

    @Override
    public void deleteblog(Long blogId) {
        blogMapper.deleteBlog(blogId);
    }

    @Override
    public void requestImage() {

    }

    @Override
    @Transactional
    public void like(Long blogId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if(blogMapper.isLiked(blogId, id)!=null){
            blogMapper.cancelLike(blogId, id);
            blogMapper.decreaseLikes(blogId);
            operationMapper.deleteOperation(id, blogId, OperationTypeConstant.LIKE);
            blogMapper.updatePopularity(blogId, -WebConfiguration.LIKE_SCORE);
        }
        else{
            eventService.newEvent(Event.like(id, blogId, getDetail(blogId).getUserId()));
            blogMapper.like(blogId, id);
            blogMapper.increaseLikes(blogId);
            operationMapper.insertOperation(id,blogId, OperationTypeConstant.LIKE);
            blogMapper.updatePopularity(blogId, WebConfiguration.LIKE_SCORE);
        }
    }

    @Override
    public Boolean isLike(Long blogId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if(blogMapper.isLiked(blogId, id)!=null)
            return true;
        return false;
    }

    @Override
    public void favor(Long blogId, String category) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if(blogMapper.isFavor(blogId, id)!=null){
            blogMapper.cancelFavor(blogId, id);
            blogMapper.decreaseFavors(blogId);
            operationMapper.deleteOperation(id, blogId, OperationTypeConstant.FAVOR);
            blogMapper.updatePopularity(blogId, -WebConfiguration.FAVOUR_SCORE);
        }
        else{
            blogMapper.favor(blogId, id, category);
            blogMapper.increaseFavors(blogId);
            eventService.newEvent(Event.favour(id,blogId,getDetail(blogId).getUserId()));
            operationMapper.insertOperation(id,blogId, OperationTypeConstant.FAVOR);
            blogMapper.updatePopularity(blogId, WebConfiguration.FAVOUR_SCORE);
        }
    }

    @Override
    public Boolean isFavor(Long blogId) {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        if(blogMapper.isFavor(blogId, id)!=null)
            return true;
        return false;
    }

    @Override
    public List<Long> getfavorBlogIds(Long id) {
        return blogMapper.getFavorBlogIds(id);
    }

    @Override
    public PageResult listFavor(List<Long> ids, int pages, int pageSize,int previewLength)   {
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        PageHelper.startPage(pages, pageSize);
        Page<Blog> page = (Page<Blog>) blogMapper.favorPageQuery(ids, pages,pageSize);
        return new PageResult(page.getTotal(),page.getResult().stream().map(blog ->{
            return BlogPreviewVO.fromBlog((Blog) blog, previewLength, blogMapper.isLiked(((Blog) blog).getId(),id),blogMapper.isFavor(((Blog) blog).getId(),id),blogMapper.getFavourCategoryById(id,((Blog)blog).getId()));
        }).toList());
    }
    @Transactional
    @Override
    public void increaseReadCnt(Long blogId) {
        blogMapper.increaseReadCnt(blogId);
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        System.out.println(operationMapper.getRecord(id, blogId, OperationTypeConstant.VIEW));
        if (operationMapper.getRecord(id, blogId, OperationTypeConstant.VIEW) == null) {
            operationMapper.insertOperation(id, blogId, OperationTypeConstant.VIEW);
            blogMapper.increaseReadUsers(blogId);
            blogMapper.updatePopularity(blogId, WebConfiguration.READ_SCORE);
        }
    }

    @Override
    public List<Long> recommend(Integer userId, int previewLength) throws TasteException {

        Long id = Long.valueOf(userId);
        List<UserBlogOperation> userList = blogMapper.getAllUserPreference();
        //创建数据模型
        DataModel dataModel = this.createDataModel(userList);
        //获取用户相似程度
        UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
        //获取用户邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
        //构建推荐器
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //推荐2个
        List<RecommendedItem> recommendedItems = recommender.recommend(userId, 10);
        List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        List<Blog> blogs=blogMapper.recommend(itemIds);
        List<BlogPreviewVO> results = blogs.stream().map(blog -> { return BlogPreviewVO.fromBlog((Blog) blog, previewLength, blogMapper.isLiked(((Blog) blog).getId(),id),blogMapper.isFavor(((Blog) blog).getId(),id),blogMapper.getFavourCategoryById(id,((Blog)blog).getId()));}).toList();
        return itemIds;

    }

    @Override
    public DataModel createDataModel(List<UserBlogOperation> userArticleOperations) {
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        Map<Integer, List<UserBlogOperation>> map = userArticleOperations.stream().collect(Collectors.groupingBy(UserBlogOperation::getUserId));
        Collection<List<UserBlogOperation>> list = map.values();
        for(List<UserBlogOperation> userPreferences : list){
            GenericPreference[] array = new GenericPreference[userPreferences.size()];
            for(int i = 0; i < userPreferences.size(); i++){
                UserBlogOperation userPreference = userPreferences.get(i);
                GenericPreference item = new GenericPreference(userPreference.getUserId(), userPreference.getBlogId(), userPreference.getValue());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);

    }



    @Override
    public PageResult getBlogs(BlogPreviewPageQueryDTO blogPageQueryDto) {
        PageHelper.startPage(blogPageQueryDto.getPage(), blogPageQueryDto.getPageSize());
        Page page = (Page) blogMapper.pageQuery(blogPageQueryDto);
        Map<String,Object> currentUser = BaseContext.getCurrentUser();
        Long id =(long) currentUser.get(JwtClaimsConstant.USER_ID);
        return new PageResult(page.getTotal(), page.getResult().stream().map(blog ->{
            return BlogPreviewVO.fromBlog((Blog) blog, blogPageQueryDto.getPreviewLength(), blogMapper.isLiked(((Blog) blog).getId(),id),blogMapper.isFavor(((Blog) blog).getId(),id),blogMapper.getFavourCategoryById(id,((Blog)blog).getId()));
        }).toList());
    }



}

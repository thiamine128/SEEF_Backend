package com.software.service;

import com.software.dto.*;
import com.software.entity.Blog;
import com.software.entity.UserBlogOperation;
import com.software.result.PageResult;
import com.software.vo.BlogPreviewVO;
import com.software.vo.CategoryVO;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;

import java.util.List;

public interface BlogService {
    void create(BlogCreateDTO blogCreateDTO);

    Blog getDetail(Long blogId);

    PageResult viewComments(CommentPageQueryDto commentPageQueryDto);

    void deleteblog(Long blogId);

    void requestImage();

    void like(Long blogId);

    Boolean isLike(Long blogId);

    void favor(Long blogId, String category);

    Boolean isFavor(Long blogId);

    List<Long> getfavorBlogIds(Long id);

    PageResult getBlogs(BlogPreviewPageQueryDTO blogPageQueryDto);

    PageResult listFavor(List<Long> ids, int page, int pageSize, int previewLength);

    void increaseReadCnt(Long blogId);

    public List<BlogPreviewVO> recommend(Integer userId, int previewLength) throws TasteException;

    public DataModel createDataModel(List<UserBlogOperation> userArticleOperations);

    void update(BlogUpdateDTO blogCreateDTO);

    List<CategoryVO> getCategoryList(Long userId);

    void createFavourCategory(String category);

    void deleteFavourCategory(String category);

    void updateFavourCategory(String newCategoryName, Long categoryId);

    List<Category> getFavourCategoryList(Long userId);

    String getFavourCategory(Long blogId);

    List<BlogPreviewVO> similar(Long blogId, Long count, int previewLength);
}
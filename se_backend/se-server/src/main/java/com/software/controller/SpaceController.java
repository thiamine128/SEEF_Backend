package com.software.controller;

import com.software.constant.MessageConstant;
import com.software.dto.Category;
import com.software.exception.InvalidParameterException;
import com.software.result.Result;
import com.software.service.BlogService;
import com.software.service.SpaceService;
import com.software.service.UserService;
import com.software.vo.CategoryVO;
import com.software.vo.UserBlogCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @Description：
 * @date
 */
@Tag(name = "空间接口")
@RestController
@RequestMapping("/api/space")
@Slf4j
public class SpaceController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    UserService userService;
    @PostMapping("/create")
    @Operation(summary = "创建分类")
    public Result createCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        }
        spaceService.createCategory(category);
        return Result.success();
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public Result deleteCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        }
        spaceService.deleteCategory(category);
        return Result.success();
    }

    @PostMapping("update")
    @Operation(summary = "更新分类名")
    public Result updateCategory(@RequestParam String newCategoryName, @RequestParam Long categoryId){
        if(newCategoryName== null|| newCategoryName.isBlank()){
            throw new InvalidParameterException(MessageConstant.PARAMETER_BLANK);
        }
        spaceService.updateCategory(newCategoryName,categoryId);
        return Result.success();
    }

    @GetMapping("/getALLCategory")
    @Operation(summary = "获得所有分类")
    public Result<List<CategoryVO>> getCategoryList(@RequestParam Long userId){
        List<CategoryVO> result = blogService.getCategoryList(userId);
        return  Result.success(result);
    }

    @GetMapping("/getCategory")
    @Operation(summary = "根据categorieId获取分类名")
    public Result<UserBlogCategoryVO> getCategoryById(@RequestParam Long categoryId){
        Category category = spaceService.getCategoryById(categoryId);
        String userName = userService.getUsername(category.getUserId());
        UserBlogCategoryVO result = new UserBlogCategoryVO(category.getCategoryName(), category.getUserId(),userName);
        return Result.success(result);
    }
}

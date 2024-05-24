package com.software.controller;

import com.software.dto.Category;
import com.software.result.Result;
import com.software.service.BlogService;
import com.software.service.SpaceService;
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
    @PostMapping("/create")
    @Operation(summary = "创建分类")
    public Result createCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new IllegalArgumentException("Invalid category");
        }
        spaceService.createCategory(category);
        return Result.success();
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public Result deleteCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new IllegalArgumentException("Invalid category");
        }
        spaceService.deleteCategory(category);
        return Result.success();
    }

    @PostMapping("update")
    @Operation(summary = "更新分类名")
    public Result updateCategory(@RequestParam String newCategoryName, @RequestParam Long categoryId){
        if(newCategoryName== null|| newCategoryName.isBlank()){
            throw new IllegalArgumentException("Invalid category");
        }
        spaceService.updateCategory(newCategoryName,categoryId);
        return Result.success();
    }

    @GetMapping("/getCategory")
    @Operation(summary = "获得所有分类")
    public Result<List<Category>> getCategoryList(@RequestParam Long userId){
        List<Category> result = blogService.getCategoryList(userId);
        return  Result.success(result);
    }
}

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
    public void createCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new IllegalArgumentException("Invalid category");
        }
        spaceService.createCategory(category);
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public void deleteCategory(@RequestParam String category){
        if(category== null|| category.isBlank()){
            throw new IllegalArgumentException("Invalid category");
        }
        spaceService.deleteCategory(category);
    }
    @GetMapping("/getCategory")
    @Operation(summary = "获得所有分类")
    public Result<List<Category>> getCategoryList(@RequestParam Long userId){
        List<Category> result = blogService.getCategoryList(userId);
        return  Result.success();
    }
}

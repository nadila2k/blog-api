package com.nadila.blogapi.controller;

import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.exception.AlreadyExistsException;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.requests.CategoryRequest;
import com.nadila.blogapi.response.ApiResponse;
import com.nadila.blogapi.service.category.CategoryService;
import com.nadila.blogapi.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable long id) {

        try {
            Category category = categoryService.getCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Category found",category));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Category not found",e));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,"Internal Server Error",e));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getCategories(){
        try {
            List<Category> categories = categoryService.getCategories();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Categories",categories));
        }catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Category not found",e));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,"Internal Server Error",e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            Category category = categoryService.addCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Category added",category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ResponseStatus.ERROR,"Category already exists",e));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        try {
            Category category = categoryService.updateCategory(categoryRequest,id);
            return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Category updated",category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ResponseStatus.ERROR,"Category already exists",e));
        }catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Category not found",e));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,"Internal Server Error",e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ResponseStatus.SUCCESS,"Category deleted",null));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ResponseStatus.ERROR,"Category not found",null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ResponseStatus.ERROR,"Internal Server Error",e));
        }
    }


}

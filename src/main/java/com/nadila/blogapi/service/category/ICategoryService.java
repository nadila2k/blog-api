package com.nadila.blogapi.service.category;

import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.requests.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategory(long id);
    List<Category> getCategories();
    Category addCategory(CategoryRequest category);
    Category updateCategory(CategoryRequest category,Long id);
    void deleteCategory(Long id);

}

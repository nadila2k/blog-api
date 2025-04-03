package com.nadila.blogapi.service.category;

import com.nadila.blogapi.exception.AlreadyExistsException;
import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.repository.CategoryRepository;
import com.nadila.blogapi.requests.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("category Not Found"));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(CategoryRequest category) {
        return Optional.of(category)
                .filter(categoryRequest -> !categoryRepository.existsByName(category.getName()))
                .map(categoryRequest -> {
                    Category category1 = new Category();
                    category1.setName(category.getName());
                    return categoryRepository.save(category1);
                }).orElseThrow(() -> new AlreadyExistsException("category already exists"));
    }

    @Override
    public Category updateCategory(CategoryRequest category, Long id) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    if (categoryRepository.existsByName(category.getName())) {
                        throw new AlreadyExistsException("Category already exists");
                    }
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new ResourceNotFound("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,() -> {throw new ResourceNotFound("Category not found");});

    }
}

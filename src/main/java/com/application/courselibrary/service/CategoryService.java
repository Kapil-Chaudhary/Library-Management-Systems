package com.application.courselibrary.service;

import com.application.courselibrary.entity.Category;
import com.application.courselibrary.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    public List<Category> findAllCategories();

    public Category findCategoryById(Long id);

    public void createCategory(Category category);

    public void updateCategory(Category category);

    public void deleteCategory(Long id);
}

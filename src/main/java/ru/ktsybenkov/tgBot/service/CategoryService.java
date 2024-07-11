package ru.ktsybenkov.tgBot.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ktsybenkov.tgBot.dao.CategoryRepository;
import ru.ktsybenkov.tgBot.entity.Category;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryByParent(Long parentCategoryId){
        return categoryRepository.findByParentId(parentCategoryId);
    }

    public Long getCategoryByName(String name){
        return categoryRepository.findFirstIdByName(name);
    }
}

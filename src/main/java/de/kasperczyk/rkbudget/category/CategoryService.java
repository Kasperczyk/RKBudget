package de.kasperczyk.rkbudget.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    PredefinedColor getPredefinedColorByName(String name) {
        return PredefinedColor.valueOf(name);
    }
}

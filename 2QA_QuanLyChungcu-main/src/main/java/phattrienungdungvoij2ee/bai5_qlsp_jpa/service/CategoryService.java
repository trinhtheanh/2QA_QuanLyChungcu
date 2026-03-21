package phattrienungdungvoij2ee.bai5_qlsp_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Category;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}

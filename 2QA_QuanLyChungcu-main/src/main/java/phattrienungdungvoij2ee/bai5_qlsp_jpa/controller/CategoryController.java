package phattrienungdungvoij2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Category;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String list(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("category") Category category,
                       RedirectAttributes redirectAttributes) {
        try {
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("successMsg", "Luu loai chung cu thanh cong!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Luu that bai: " + e.getMessage());
        }
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id,
                         RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMsg", "Xoa loai chung cu thanh cong!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Xoa that bai (co the da co chung cu dung loai nay)!");
        }
        return "redirect:/categories";
    }
}
package phattrienungdungvoij2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Account;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.ChungCu;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.AccountRepository;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.CategoryService;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.ChungCuService;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.ThongBaoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/Apartments")
public class ChungCuController {

    @Autowired
    private ChungCuService chungCuService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private AccountRepository accountRepository;

    // Thu muc luu anh: src/main/resources/static/uploads/
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public String listChungCus(Model model, Authentication authentication) {
        // Thong bao cho trang chu
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());

        // Phan quyen hien thi chung cu
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isManager = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"));

        if (isAdmin || isManager) {
            // Admin va Manager xem tat ca chung cu
            model.addAttribute("products", chungCuService.getAllChungCus());
            model.addAttribute("isAdmin", isAdmin);
        } else {
            // User (cu dan) chi xem chung cu cua minh
            Optional<Account> accountOpt = accountRepository.findByLoginName(authentication.getName());
            if (accountOpt.isPresent() && accountOpt.get().getChungCu() != null) {
                List<ChungCu> myChungCu = new ArrayList<>();
                myChungCu.add(accountOpt.get().getChungCu());
                model.addAttribute("products", myChungCu);
            } else {
                model.addAttribute("products", new ArrayList<>());
            }
            model.addAttribute("isAdmin", false);
        }

        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new ChungCu());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveChungCu(
            @ModelAttribute("product") ChungCu chungCu,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes
    ) {
        // Xu ly upload anh neu co file moi
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Tao thu muc neu chua co
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                // Tao ten file unique
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                Files.write(filePath, imageFile.getBytes());

                // Luu duong dan vao DB
                chungCu.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Upload anh that bai: " + e.getMessage());
                return "redirect:/Apartments";
            }
        }
        // Neu khong co file moi, giu nguyen image cu (da duoc bind tu hidden field)

        chungCuService.saveChungCu(chungCu);
        return "redirect:/Apartments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", chungCuService.getChungCuById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteChungCu(@PathVariable("id") Long id) {
        chungCuService.deleteChungCu(id);
        return "redirect:/Apartments";
    }
}
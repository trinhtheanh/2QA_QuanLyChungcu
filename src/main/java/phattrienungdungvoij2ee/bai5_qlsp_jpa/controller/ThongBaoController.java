package phattrienungdungvoij2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.ThongBao;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.ThongBaoService;

@Controller
@RequestMapping("/thongbao")
public class ThongBaoController {

    @Autowired
    private ThongBaoService thongBaoService;

    // Trang quan ly thong bao (chi MANAGER)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        return "thongbao/list";
    }

    // Trang xem thong bao cho tat ca user
    @GetMapping("/xem")
    public String viewAll(Model model) {
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        return "thongbao/view";
    }

    // Form them thong bao
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("thongbao", new ThongBao());
        return "thongbao/add";
    }

    // Luu thong bao (them moi hoac cap nhat)
    @PostMapping("/save")
    public String save(@ModelAttribute("thongbao") ThongBao thongBao,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        try {
            if (thongBao.getId() == null) {
                // Thong bao moi -> gan nguoi tao
                thongBao.setNguoiTao(authentication.getName());
            } else {
                // Dang cap nhat -> giu nguyen nguoi tao cu
                ThongBao existing = thongBaoService.getById(thongBao.getId());
                if (existing != null) {
                    thongBao.setNguoiTao(existing.getNguoiTao());
                    thongBao.setNgayTao(existing.getNgayTao());
                }
            }
            thongBaoService.save(thongBao);
            redirectAttributes.addFlashAttribute("successMsg", "Luu thong bao thanh cong!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Luu that bai: " + e.getMessage());
        }
        return "redirect:/thongbao";
    }

    // Form sua thong bao
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        ThongBao thongBao = thongBaoService.getById(id);
        if (thongBao == null) {
            return "redirect:/thongbao";
        }
        model.addAttribute("thongbao", thongBao);
        return "thongbao/edit";
    }

    // Xoa thong bao
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            thongBaoService.delete(id);
            redirectAttributes.addFlashAttribute("successMsg", "Xoa thong bao thanh cong!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Xoa that bai: " + e.getMessage());
        }
        return "redirect:/thongbao";
    }
}

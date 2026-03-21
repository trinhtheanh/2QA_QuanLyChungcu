package phattrienungdungvoij2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.ThongBaoService;

@Controller
@RequestMapping("/tintuc")
public class TinTucController {

    @Autowired
    private ThongBaoService thongBaoService;

    @GetMapping
    public String listTinTuc(Model model) {
        // Lay tat ca thong bao de hien thi nhu tin tuc / su kien
        model.addAttribute("thongbaos", thongBaoService.getAllThongBao());
        return "tintuc/list";
    }
}

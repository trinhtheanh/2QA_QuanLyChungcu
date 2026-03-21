package phattrienungdungvoij2ee.bai5_qlsp_jpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.service.ThongBaoService;

@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired
    private ThongBaoService thongBaoService;

    // Them danh sach thong bao vao moi trang de hien thi marquee
    @ModelAttribute
    public void addMarqueeData(Model model) {
        try {
            model.addAttribute("marqueeThongBaos", thongBaoService.getAllThongBao());
        } catch (Exception e) {
            // Tranh loi khi chua dang nhap hoac chua co du lieu
        }
    }
}

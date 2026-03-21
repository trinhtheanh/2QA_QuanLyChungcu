package phattrienungdungvoij2ee.bai5_qlsp_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.ThongBao;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.ThongBaoRepository;

import java.util.List;

@Service
public class ThongBaoService {

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    public List<ThongBao> getAllThongBao() {
        return thongBaoRepository.findAllByOrderByGhimDescNgayTaoDesc();
    }

    public List<ThongBao> getByLoai(String loai) {
        return thongBaoRepository.findByLoaiThongBaoOrderByNgayTaoDesc(loai);
    }

    public ThongBao getById(Long id) {
        return thongBaoRepository.findById(id).orElse(null);
    }

    public void save(ThongBao thongBao) {
        thongBaoRepository.save(thongBao);
    }

    public void delete(Long id) {
        thongBaoRepository.deleteById(id);
    }
}

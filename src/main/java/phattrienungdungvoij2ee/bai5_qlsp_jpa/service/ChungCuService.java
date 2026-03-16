package phattrienungdungvoij2ee.bai5_qlsp_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.ChungCu;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.ChungCuRepository;

import java.util.List;

@Service
public class ChungCuService {
    @Autowired
    private ChungCuRepository chungCuRepository;

    public List<ChungCu> getAllChungCus() {
        return chungCuRepository.findAll();
    }

    public void saveChungCu(ChungCu chungCu) {
        chungCuRepository.save(chungCu);
    }

    public ChungCu getChungCuById(long id) {
        return chungCuRepository.findById(id).orElse(null);
    }

    public void deleteChungCu(long id) {
        chungCuRepository.deleteById(id);
    }
}


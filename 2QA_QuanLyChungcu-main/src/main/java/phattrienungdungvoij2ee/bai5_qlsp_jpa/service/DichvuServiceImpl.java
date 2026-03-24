package phattrienungdungvoij2ee.bai5_qlsp_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Dichvu;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.repository.DichvuRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class DichvuServiceImpl implements DichvuService {
    @Autowired
    private DichvuRepository dichvuRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/services/";

    @Override
    public List<Dichvu> getAllServices() {
        return dichvuRepository.findAll();
    }

    @Override
    public Dichvu getServiceById(Long id) {
        return dichvuRepository.findById(id).orElse(null);
    }

    @Override
    public Dichvu saveService(Dichvu dichvu) {
        return dichvuRepository.save(dichvu);
    }

    @Override
    public void deleteService(Long id) {
        dichvuRepository.deleteById(id);
    }

    // Luu file anh va tra ve URL path
    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Tao thu muc neu chua co
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tao ten file duy nhat
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = UUID.randomUUID().toString() + extension;

        // Luu file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/services/" + filename;
    }

    // Luu dich vu kem anh
    public Dichvu saveServiceWithImage(Dichvu dichvu, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            dichvu.setImageUrl(imageUrl);
        }
        return dichvuRepository.save(dichvu);
    }
}

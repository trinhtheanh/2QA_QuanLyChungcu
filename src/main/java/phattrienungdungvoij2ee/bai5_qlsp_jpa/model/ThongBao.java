package phattrienungdungvoij2ee.bai5_qlsp_jpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Column(nullable = false, length = 255)
    private String tieuDe;

    @NotBlank(message = "Nội dung không được để trống")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String noiDung;

    @Column(nullable = false, length = 50)
    private String loaiThongBao;

    @Column(nullable = false)
    private LocalDateTime ngayTao;

    @Column(length = 100)
    private String nguoiTao;

    @Column(nullable = false)
    private boolean ghim = false;

    @PrePersist
    public void prePersist() {
        if (ngayTao == null) {
            ngayTao = LocalDateTime.now();
        }
    }
}

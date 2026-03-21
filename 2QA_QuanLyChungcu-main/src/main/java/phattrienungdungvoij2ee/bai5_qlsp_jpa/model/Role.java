package phattrienungdungvoij2ee.bai5_qlsp_jpa.model;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
}

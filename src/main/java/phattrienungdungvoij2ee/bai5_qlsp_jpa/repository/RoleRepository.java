package phattrienungdungvoij2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
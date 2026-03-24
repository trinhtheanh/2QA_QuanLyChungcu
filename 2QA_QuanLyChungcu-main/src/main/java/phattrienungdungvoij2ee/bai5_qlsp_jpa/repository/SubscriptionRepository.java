package phattrienungdungvoij2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(int userId);
    List<Subscription> findByServiceEntityId(Long serviceId);
    boolean existsByUserIdAndServiceEntityId(int userId, Long serviceId);
}

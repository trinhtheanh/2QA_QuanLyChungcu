package phattrienungdungvoij2ee.bai5_qlsp_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phattrienungdungvoij2ee.bai5_qlsp_jpa.model.Payment;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findBySubscriptionId(Long subscriptionId);
    List<Payment> findBySubscriptionUserId(int userId);
    Payment findBySubscriptionIdAndId(Long subscriptionId, Long paymentId);
}

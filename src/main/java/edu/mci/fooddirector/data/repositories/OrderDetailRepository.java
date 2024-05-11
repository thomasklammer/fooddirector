package edu.mci.fooddirector.data.repositories;

import edu.mci.fooddirector.data.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}

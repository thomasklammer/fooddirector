package edu.mci.fooddirector.data.repositories;

import edu.mci.fooddirector.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

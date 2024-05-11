package edu.mci.fooddirector.data.repositories;

import edu.mci.fooddirector.data.domain.Order;
import edu.mci.fooddirector.data.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);
}

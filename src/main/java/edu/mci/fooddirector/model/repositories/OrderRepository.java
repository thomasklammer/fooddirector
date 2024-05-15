package edu.mci.fooddirector.model.repositories;

import edu.mci.fooddirector.model.domain.Order;
import edu.mci.fooddirector.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId);

    @Query("select o from Order o where month(o.orderDate) = ?1 and year(o.orderDate) = ?2")
    List<Order> findByMonthAndYear(int month, int year);


}

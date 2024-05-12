package edu.mci.fooddirector.model.repositories;

import edu.mci.fooddirector.model.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
}

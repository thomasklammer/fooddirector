package edu.mci.fooddirector.model.repositories;

import edu.mci.fooddirector.model.domain.Address;
import edu.mci.fooddirector.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

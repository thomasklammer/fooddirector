package edu.mci.fooddirector.model.repositories;

import edu.mci.fooddirector.model.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

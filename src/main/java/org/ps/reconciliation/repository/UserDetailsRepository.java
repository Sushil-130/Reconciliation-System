package org.ps.reconciliation.repository;

import org.ps.reconciliation.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {

}

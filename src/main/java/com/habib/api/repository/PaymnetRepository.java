package com.habib.api.repository;

import com.habib.api.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")

public interface PaymnetRepository extends JpaRepository<Payment,Long> {
}

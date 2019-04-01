package com.habib.api.repository;

import com.habib.api.domains.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "supplier", path = "supplier")
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}

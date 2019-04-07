package com.habib.api.repository;

import com.habib.api.domains.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "supplier", path = "supplier")
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    List<Supplier> findAllByOrderBySuppliernameAsc();
}

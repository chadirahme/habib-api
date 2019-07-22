package com.habib.api.repository;

import com.habib.api.domains.PffInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pffinvoice", path = "pffinvoice")
public interface PffInvoiceRepository extends JpaRepository<PffInvoice,Long> {
}

package com.habib.api.repository;


import com.habib.api.domains.PffInvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "pffinvoicedetail", path = "pffinvoicedetail")
public interface PffInvoiceDetailRepository extends JpaRepository<PffInvoiceDetail,Long> {
}

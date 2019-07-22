package com.habib.api.repository;

import com.habib.api.domains.PffItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "pffitem", path = "pffitem")
public interface PffItemRepository extends JpaRepository<PffItem,Long> {

    List<PffItem> findAllByOrderBySortitem();
    List<PffItem> findAllByStatusOrderBySortitem(String status);
}

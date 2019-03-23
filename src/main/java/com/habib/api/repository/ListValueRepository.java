package com.habib.api.repository;

import com.habib.api.domains.ListValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "listvalue", path = "listvalue")
public interface ListValueRepository extends JpaRepository<ListValue,Long> {

   List<ListValue> findByListId(int listid);
}

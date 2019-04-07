package com.habib.api.repository;

import com.habib.api.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "payment", path = "payment")

public interface PaymnetRepository extends JpaRepository<Payment,Long> {

    @Query(
            value = "SELECT round(sum(amount),2) as total,month(paymentdate) as paymonth" +
                    " FROM payment p WHERE year(paymentdate) = ?" +
                    " group by year(paymentdate),month(paymentdate)",
            nativeQuery = true)
    List<PaymentsStatistics> findAllPaymentsByYear(Integer year);
}


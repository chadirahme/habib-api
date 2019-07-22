package com.habib.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pffinvoice")
@Getter
@Setter
@NoArgsConstructor
public class PffInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int invoiceitems;
    private Double total;
    //private Date createdtime;

    private String notes;
    private String paidby;

    @CreationTimestamp
    private LocalDateTime createdtime;

    @Transient
    private List<PffInvoiceDetail> pffInvoiceLines;

}

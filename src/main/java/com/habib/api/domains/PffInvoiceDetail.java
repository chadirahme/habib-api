package com.habib.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pffinvoicedetail")
@Getter
@Setter
@NoArgsConstructor
public class PffInvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long invoiceid;
    private int itemid;
    private String itemname;
    private int quantity;
    private Double price;
    private Double total;
}

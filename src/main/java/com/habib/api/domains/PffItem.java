package com.habib.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pffitem")
@Getter
@Setter
@NoArgsConstructor
public class PffItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemid;

    private String itemname;
    private String itemdescription;
    private String sellunit;
    private String status;
    private Double price;
    private int sortitem;

}

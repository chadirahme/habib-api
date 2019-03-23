package com.habib.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "listvalue")
@Getter
@Setter
@NoArgsConstructor
public class ListValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="listid")
    private int listId;

    @Column(name="listvalue")
    private String listValue;
}

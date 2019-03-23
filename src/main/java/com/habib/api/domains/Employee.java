package com.habib.api.domains;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeid;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="professionid", nullable=false)
    private ListValue profession;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private Date startdate;

}

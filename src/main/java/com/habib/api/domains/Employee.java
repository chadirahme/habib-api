package com.habib.api.domains;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startdate;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="hourrate")
    private Double hourRate;

}

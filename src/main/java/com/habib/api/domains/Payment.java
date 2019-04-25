package com.habib.api.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentid;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="supplierid", nullable=false)
    private Supplier supplier;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date paymentdate;

    private Date createdtime;

    private String description;

    private String paidby;

    private Double amount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userid", nullable=false)
    private User user;

    @Transient
    private Long supplierid;

    @Column(name="filepath")
    private String filepath;

    @Column(name="filename")
    private String filename;
}

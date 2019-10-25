package com.Spring.FrameWork.Spring.Entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EnableAutoConfiguration
@Table(name = "Customer",schema = "SYS")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="fName")
    private String fName;
    @Column(name="lName")
    private String lName;
    @Column(name="mobileNumber")
    private String mobileNumber;

    public Customer() {

    }

    public Customer(Customer customer) {
        this.fName = customer.fName;
        this.lName = customer.lName;
        this.mobileNumber = customer.mobileNumber;

    }
}

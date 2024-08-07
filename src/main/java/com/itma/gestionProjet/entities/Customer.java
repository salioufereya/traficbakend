package com.itma.gestionProjet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  //  @Column(name = "customerNumber")
    private Integer customerNumber;

    //@Column(name = "customerName")
    private String customerName;

    //@Column(name = "contactLastName")
    private String contactLastName;

    //@Column(name = "contactFirstName")
    private String contactFirstName;

    //@Column(name = "phone")
    private String phone;

    //@Column(name = "addressLine1")
    private String addressLine1;

   // @Column(name = "addressLine2")
    private String addressLine2;

  //  @Column(name = "city")
    private String city;

 //   @Column(name = "state")
    private String state;

   // @Column(name = "postalCode")
    private String postalCode;

    //  @Column(name = "country")
    private String country;

   // @Column(name = "salesRepEmployeeNumber")
    private Integer salesRepEmployeeNumber;

  //  @Column(name = "creditLimit")
    private Long creditLimit;

}

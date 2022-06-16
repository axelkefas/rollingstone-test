package com.axel.rollingstone.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_redeem_product")
public class RedeemProduct {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private int quantity;

    private int rating;
}

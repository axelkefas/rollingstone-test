package com.axel.rollingstone.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String detail;

    private String label;

    private String image;

    private int quantity;

    private int count_rating;

    private double rating;

    private double price;
}

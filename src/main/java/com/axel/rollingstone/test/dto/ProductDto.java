package com.axel.rollingstone.test.dto;

import lombok.Data;

@Data
public class ProductDto {

    private int id;

    private String name;

    private String detail;

    private String label;

    private int quantity;

    private int count_rating;

    private double rating;

    private double price;
}

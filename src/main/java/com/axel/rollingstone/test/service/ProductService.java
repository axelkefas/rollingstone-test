package com.axel.rollingstone.test.service;

import com.axel.rollingstone.test.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> saveProducts(List<Product> products);

    List<Product> getProducts();

    Product getProductById(int id);

    Product getProductByName(String name);

    void deleteProduct(int id);

    Product updateProduct(Product product);

    void redeemProduct(int userId, int id, int quantity);

    Product ratingProduct(int userId, int id, int rating);

}

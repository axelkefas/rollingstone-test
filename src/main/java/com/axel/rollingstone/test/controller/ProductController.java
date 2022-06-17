package com.axel.rollingstone.test.controller;

import com.axel.rollingstone.test.config.GlobalException;
import com.axel.rollingstone.test.config.StatusCode;
import com.axel.rollingstone.test.entity.Product;
import com.axel.rollingstone.test.entity.User;
import com.axel.rollingstone.test.service.ProductService;
import com.axel.rollingstone.test.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserService userService;

    @PostMapping("/gifts")
    public Product addProduct(@RequestBody Product product){
        return service.saveProduct(product);
    }

    @PostMapping("/gifts/{id}/redeem")
    public void redeemProduct(@RequestHeader("Authorization") String authorization, @PathVariable int id, @RequestBody Product product){

            String decryptId = decrypt(authorization);
            User user = userService.findUserById(Integer.parseInt(decryptId));

            service.redeemProduct(user.getId(),id, product.getQuantity());

    }

    @PostMapping("/gifts/{id}/rating")
    public void ratingProduct(@RequestHeader("Authorization") String authorization, @PathVariable int id, @RequestBody Product product){

        String decryptId = decrypt(authorization);
        User user = userService.findUserById(Integer.parseInt(decryptId));

        service.ratingProduct(user.getId(),id, (int)product.getRating());

    }

    @GetMapping("/gifts")
    public List<Product> findAllProducts(){
        return service.getProducts();
    }

    @GetMapping("/gifts/{id}")
    public Product findProductById(@PathVariable int id){
        return service.getProductById(id);
    }

    @PutMapping("/gifts")
    public Product updateProduct(@RequestBody Product product){
        return service.updateProduct(product);
    }

    @DeleteMapping("/gifts/{id}")
    public void deleteProduct(@PathVariable int id){
        service.deleteProduct(id);
    }

    private String decrypt(String token){
        try {
            StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
            textEncryptor.setPassword("123");

            return textEncryptor.decrypt(token);
        }
        catch (Exception e)
        {
            throw new GlobalException(StatusCode.UNAUTHORIZED,"Unauthorized");
        }
    }
}

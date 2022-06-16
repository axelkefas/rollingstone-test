package com.axel.rollingstone.test.service.impl;

import com.axel.rollingstone.test.config.GlobalException;
import com.axel.rollingstone.test.config.StatusCode;
import com.axel.rollingstone.test.entity.Product;
import com.axel.rollingstone.test.entity.RedeemProduct;
import com.axel.rollingstone.test.entity.User;
import com.axel.rollingstone.test.repository.ProductRepository;
import com.axel.rollingstone.test.repository.RedeemProductRepository;
import com.axel.rollingstone.test.repository.UserRepository;
import com.axel.rollingstone.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedeemProductRepository redeemProductRepository;

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Product getProductByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        if(existingProduct==null)
        {
            throw new GlobalException(StatusCode.NOT_FOUND,product.getName()+" not found");
        }
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }

    @Override
    public void redeemProduct(int userId, int id, int quantity) {

        Optional<Product> productOptional = repository.findById(id);
        if(!productOptional.isPresent())
        {
            throw new GlobalException(StatusCode.NOT_FOUND, "Product Not Found");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
        {
            throw new GlobalException(StatusCode.NOT_FOUND, "User Not Found");
        }

        if(quantity < 1)
        {
            throw new GlobalException(StatusCode.INTERNAL_ERROR, "Quantity minimal 1");
        }
        if(productOptional.get().getQuantity()>quantity)
        {
            throw new GlobalException(StatusCode.INTERNAL_ERROR,"Insufficient Quantity");
        }

        RedeemProduct redeemProduct = new RedeemProduct();
        redeemProduct.setProduct(productOptional.get());
        redeemProduct.setUser(userOptional.get());
        redeemProduct.setQuantity(quantity);

        redeemProductRepository.save(redeemProduct);

        Product product = productOptional.get();
        int total = product.getQuantity() - quantity;
        product.setQuantity(total);
        repository.save(product);
    }

    @Override
    public Product ratingProduct(int userId, int id, int rating) {

        Optional<Product> productOptional = repository.findById(id);
        if(!productOptional.isPresent())
        {
            throw new GlobalException(StatusCode.NOT_FOUND, "Product Not Found");
        }
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
        {
            throw new GlobalException(StatusCode.NOT_FOUND, "User Not Found");
        }
        Optional<RedeemProduct> redeemProductOptional = redeemProductRepository.findRedeemProductByUserAndProduct(userOptional.get(),productOptional.get());
        if(redeemProductOptional.isPresent())
        {
            throw new GlobalException(StatusCode.INTERNAL_ERROR, "Product have been rating");
        }

        RedeemProduct redeemProduct = new RedeemProduct();
        redeemProduct.setRating(rating);
        redeemProductRepository.save(redeemProduct);

        Product product = productOptional.get();
        int countProduct = redeemProductRepository.countRedeemProductByProduct(product);
        int countRating = redeemProductRepository.getRatingTotal(product.getId());

        double fixedRating = Math.round(((double)countRating / (double)countProduct * 100) * 10) / 10.0;

        product.setRating(fixedRating);
        return repository.save(product);

    }
}

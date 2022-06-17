package com.axel.rollingstone.test.repository;

import com.axel.rollingstone.test.entity.Product;
import com.axel.rollingstone.test.entity.RedeemProduct;
import com.axel.rollingstone.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedeemProductRepository extends JpaRepository<RedeemProduct, Integer> {
    Optional<RedeemProduct> findRedeemProductByUserAndProduct(User user, Product product);

    Integer countRedeemProductByProduct(Product product);

    @Query(value = "SELECT SUM(rating) FROM tbl_redeem_product WHERE product_id = ?1", nativeQuery = true)
    Integer getRatingTotal(int id);
}

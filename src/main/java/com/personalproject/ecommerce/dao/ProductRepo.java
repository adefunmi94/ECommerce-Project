package com.personalproject.ecommerce.dao;

import com.personalproject.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
public interface ProductRepo extends JpaRepository<Product, Long> {
}

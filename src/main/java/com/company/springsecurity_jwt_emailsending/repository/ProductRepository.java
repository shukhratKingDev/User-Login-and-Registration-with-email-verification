package com.company.springsecurity_jwt_emailsending.repository;

import com.company.springsecurity_jwt_emailsending.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@RepositoryRestResource(path = "product")
public interface ProductRepository  extends JpaRepository<Product , UUID> {
}

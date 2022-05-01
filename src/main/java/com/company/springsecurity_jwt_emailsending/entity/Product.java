package com.company.springsecurity_jwt_emailsending.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)// to listen changes in table.
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @CreatedBy
    private UUID createdBy; /// who add product
    @LastModifiedBy
    private UUID updatedBy;// who update product
    @CreationTimestamp
    private Timestamp createdAat; // when created
    @UpdateTimestamp
    private Timestamp updatedAt; // when updated
}

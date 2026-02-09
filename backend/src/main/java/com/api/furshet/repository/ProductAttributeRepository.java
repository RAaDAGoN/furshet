package com.api.furshet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.furshet.domain.entity.ProductAttribute;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    boolean existsByAttribute_Id(Long attributeId);
}

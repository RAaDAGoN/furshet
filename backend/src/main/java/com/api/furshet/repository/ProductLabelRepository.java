package com.api.furshet.repository;

import com.api.furshet.domain.entity.ProductLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLabelRepository extends JpaRepository<ProductLabel, Long> {
    boolean existsByLabel_Id(Long labelId);
}

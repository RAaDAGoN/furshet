package com.api.furshet.repository;

import com.api.furshet.domain.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    public List<Label> findByActiveTrue();
}

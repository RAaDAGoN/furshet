package com.api.furshet.repository;

import com.api.furshet.domain.entity.CallbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallbackRequestRepository extends JpaRepository<CallbackRequest, Long> {
}

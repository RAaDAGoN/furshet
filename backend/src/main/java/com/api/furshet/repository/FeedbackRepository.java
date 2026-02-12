package com.api.furshet.repository;

import com.api.furshet.domain.entity.Feedback;
import com.api.furshet.domain.enums.TypeFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("""
        select f from Feedback f
        order by 
          case when f.typeFeedback = com.api.furshet.domain.enums.TypeFeedback.IN_PROGRESS then 0 else 1 end,
          f.id desc
    """)
    List<Feedback> findAllWithInProgressFirst();

    List<Feedback> findAllByTypeFeedbackAndActiveTrue(TypeFeedback typeFeedback);

    List<Feedback> findAllByActiveTrue();
}

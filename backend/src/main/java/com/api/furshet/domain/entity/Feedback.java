package com.api.furshet.domain.entity;

import com.api.furshet.domain.enums.TypeFeedback;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String FI;
    private String rating;
    private String date;
    private String comment;

    @Builder.Default
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeFeedback typeFeedback = TypeFeedback.IN_PROGRESS;
}

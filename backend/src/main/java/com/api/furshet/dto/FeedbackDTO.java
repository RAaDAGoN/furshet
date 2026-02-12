package com.api.furshet.dto;

import com.api.furshet.domain.enums.TypeFeedback;
import lombok.Data;

@Data
public class FeedbackDTO {
    Long id;
    private String FI;
    private String rating;
    private String date;
    private String comment;

    private Boolean active;
    private TypeFeedback typeFeedback;
}

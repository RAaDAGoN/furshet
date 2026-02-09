package com.api.furshet.dto;

import com.api.furshet.domain.entity.Point;
import com.api.furshet.domain.enums.TypePhoto;
import lombok.Data;

@Data
public class CateringDTO {
    private Long id;
    private Long pointId;
    private String filename;
    private TypePhoto typePhoto;
}

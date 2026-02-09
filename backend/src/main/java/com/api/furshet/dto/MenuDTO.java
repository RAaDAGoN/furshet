package com.api.furshet.dto;

import com.api.furshet.domain.enums.TypeMenu;
import lombok.Data;

@Data
public class MenuDTO {
    private Long id;
    private TypeMenu name;
    private String filename;
    private Boolean active;
    private String translate;

}

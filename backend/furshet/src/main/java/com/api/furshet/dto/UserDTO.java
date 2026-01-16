package com.api.furshet.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userName;
    private String password;
    private Boolean isActive;
}

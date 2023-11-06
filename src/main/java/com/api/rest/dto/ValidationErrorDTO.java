package com.api.rest.dto;

import lombok.Data;

@Data
public class ValidationErrorDTO {

    private String code;
    private String message;
}

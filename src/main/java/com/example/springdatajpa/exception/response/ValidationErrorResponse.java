package com.example.springdatajpa.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private List<SingleValidationError> errors = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class SingleValidationError {
        private String field;
        private String message;
    }
}

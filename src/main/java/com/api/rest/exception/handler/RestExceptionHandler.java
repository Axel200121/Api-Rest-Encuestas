package com.api.rest.exception.handler;


import com.api.rest.dto.ErrorDetailDTO;
import com.api.rest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest httpServletRequest){
        ErrorDetailDTO errorDetailDTO = new ErrorDetailDTO();
        errorDetailDTO.setTimeStamp(new Date().getTime());
        errorDetailDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetailDTO.setTitle("Recurso no econtrado");
        errorDetailDTO.setDetail(exception.getClass().getName());
        errorDetailDTO.setDeveloperMessage(exception.getMessage());
        return  new ResponseEntity<>(errorDetailDTO,null,HttpStatus.NOT_FOUND);
    }
}

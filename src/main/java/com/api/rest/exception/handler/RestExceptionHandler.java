package com.api.rest.exception.handler;


import com.api.rest.dto.ErrorDetailDTO;
import com.api.rest.dto.ValidationErrorDTO;
import com.api.rest.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;



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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorDetailDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){

        ErrorDetailDTO errorDetailDTO = new ErrorDetailDTO();
        errorDetailDTO.setTimeStamp(new Date().getTime());
        errorDetailDTO.setStatus(HttpStatus.BAD_REQUEST.value());

        String requesPath = (String) httpServletRequest.getAttribute("javax.servlet.error.request_uri");//para obtener la ruta
        if (requesPath == null){
            requesPath = httpServletRequest.getRequestURI();
        }

        errorDetailDTO.setTitle("Validación Fallida");
        errorDetailDTO.setDetail("La validación de entrada fallo");
        errorDetailDTO.setDeveloperMessage(exception.getMessage());

        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();
        for (FieldError fieldError: fieldErros){//obtenemos sus errores de cada campo
            List<ValidationErrorDTO> validationErrorList = errorDetailDTO.getErrors().get(fieldError.getField());
            if (validationErrorList == null){
                validationErrorList = new ArrayList<ValidationErrorDTO>();
                errorDetailDTO.getErrors().put(fieldError.getField(),validationErrorList);
            }
            ValidationErrorDTO validationErrorDTO1 = new ValidationErrorDTO();
            validationErrorDTO1.setCode(fieldError.getCode());
            validationErrorDTO1.setMessage(messageSource.getMessage(fieldError,null));
            validationErrorList.add(validationErrorDTO1);
        }

        return errorDetailDTO;

    }
}

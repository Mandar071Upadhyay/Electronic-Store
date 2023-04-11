package com.lcwd.electroinic.store.exceptions;

import com.lcwd.electroinic.store.dtos.ApiResponseMessage;
import com.lcwd.electroinic.store.dtos.ImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponseMessage> resouceNotFoundExceptionHandler(ResourceNotFoundException ex)
{
    ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).success(true).build();
return new ResponseEntity<>(apiResponseMessage,HttpStatus.NOT_FOUND);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String,Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exceptions)
{
    List<ObjectError> allErrors = exceptions.getBindingResult().getAllErrors();
    Map<String,Object> response=new HashMap<>();
allErrors.stream().forEach(objectError->{
    String message=objectError.getDefaultMessage();
    String field=((FieldError)objectError).getField();
    response.put(field,message);
});
return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> badApiRequestExceptionHandler(BadApiRequestException ex)
{
    ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message(ex.getMessage()).success(true).httpStatus(HttpStatus.BAD_REQUEST).build();
    return new ResponseEntity<>(apiResponseMessage,HttpStatus.BAD_REQUEST);
}

}

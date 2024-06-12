package com.tuti.presentacion.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tuti.exception.Excepcion;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler {

   @org.springframework.web.bind.annotation.ExceptionHandler(Excepcion.class)
   public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, Excepcion e) {
	   int statusCode= e.getStatusCode();
	   
	   ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURI());
       return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
   }
}
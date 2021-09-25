package br.com.diolabs.restful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.diolabs.restful.exception.SoldadoBadRequestException;
import br.com.diolabs.restful.exception.SoldadoNotFoundException;

@ControllerAdvice
public class SoldadoControllerAdvice {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SoldadoNotFoundException.class)
    public void notFound(){}


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SoldadoBadRequestException.class)
    public void badRequest(){}
    
}

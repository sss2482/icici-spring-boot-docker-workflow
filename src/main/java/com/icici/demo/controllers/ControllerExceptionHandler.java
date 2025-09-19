// package com.icici.demo.controllers;

// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice
// public class ControllerExceptionHandler {
//     @ExceptionHandler(value = {TransactionNotFoundException.class})
//     @ResponseStatus(value = HttpStatus.NOT_FOUND)
//     public ErrorMessage resourceNotFoundException(TransactionNotFoundException ex) {
//         ErrorMessage message = new ErrorMessage(
//                 HttpStatus.NOT_FOUND.value(),
//                 ex.getMessage(),ex.getMessage());
//         return message;
//     }
// }

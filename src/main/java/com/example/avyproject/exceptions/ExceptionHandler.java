package com.example.avyproject.exceptions;

import com.example.avyproject.entity.LessonProgress;
import com.example.avyproject.exceptions.payment.SubscriptionNotActivatedException;
import com.example.avyproject.exceptions.payment.SubscriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity UserNotFoundException(UserNotFoundException exception,
                                                HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity InvalidFormatException(InvalidFormatException exception,
                                                 HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity AccountNotFoundException(AccountNotFoundException exception,
                                                   HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity PasswordIncorrectException(PasswordIncorrectException exception,
                                                   HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity RoleNotFoundException(RoleNotFoundException exception,
                                                     HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity CourseNotFoundException (CourseNotFoundException exception,
                                                HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity NoImageAttachedException (NoImageAttachedException exception,
                                                   HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity NoVideoAttached (NoVideoAttachedException exception,
                                                    HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity NoFileAttached (NoFileAttachedException exception,
                                           HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity ModuleNotFound (ModuleNotFoundException exception,
                                          HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity LessonProgressNotFound (LessonProgressNotFoundException exception,
                                                  HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity CourseProgressNotFound (CourseProgressNotFoundException exception,
                                                  HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity SubscriptionNotFound (SubscriptionNotFoundException exception,
                                                HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity SubscriptionNotActivated (SubscriptionNotActivatedException exception,
                                                    HttpServletRequest request) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}

package com.medtriage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNoDisponibleException.class)
    public ProblemDetail handleDoctorNoDisponible(DoctorNoDisponibleException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("dctor no disponible");
        return problem;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntime(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("solicitud invalida");
        return problem;
    }
}

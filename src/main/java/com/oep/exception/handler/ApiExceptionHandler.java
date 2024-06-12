package com.oep.exception.handler;

import com.oep.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ProblemDetail handleServiceException(ServiceException e, WebRequest request) {
        return getProblemDetail(HttpStatus.BAD_REQUEST, URI.create("about::blank"), e.getMessage(), URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        return problemDetail;
    }

}

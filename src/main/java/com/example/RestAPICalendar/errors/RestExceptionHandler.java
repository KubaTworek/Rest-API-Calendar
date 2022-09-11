package com.example.RestAPICalendar.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CalendarErrorResponse> handleException(CalendarNotFoundException exc){
        CalendarErrorResponse error = new CalendarErrorResponse();

        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CalendarErrorResponse> handleException(WorkingTimeIsNullException exc){
        CalendarErrorResponse error = new CalendarErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CalendarErrorResponse> handleException(WrongTimeException exc){
        CalendarErrorResponse error = new CalendarErrorResponse();

        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CalendarErrorResponse> handleException(TimeIsNullException exc){
        CalendarErrorResponse error = new CalendarErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<CalendarErrorResponse> handleHttpMediaTypeNotAcceptableException() {
        CalendarErrorResponse error = new CalendarErrorResponse();

        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        error.setMessage("Acceptable content type:" + MediaType.APPLICATION_JSON_VALUE);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

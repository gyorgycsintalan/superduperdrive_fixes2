package com.udacity.jwdnd.course1.cloudstorage.exception_handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class StorageExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
             HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.getModel().put("error", true);
        modelAndView.getModel().put("errorMessage", "The file size exceeds the 1MB limit!");
        return modelAndView;
    }

}

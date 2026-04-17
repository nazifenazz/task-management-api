package com.nazifenaz.gorevyonetim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IslemGecersizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> islemGecersizHatasi(IslemGecersizException e) {
        Map<String, String> hata = new HashMap<>();
        hata.put("mesaj", e.getMessage());
        return hata;
    }

    @ExceptionHandler(BulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bulunamadiHatasi(BulunamadiException e) {
        Map<String, String> hata = new HashMap<>();
        hata.put("mesaj", e.getMessage());
        return hata;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validasyonHatasi(MethodArgumentNotValidException e) {
        Map<String, String> hata = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            hata.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return hata;
    }
}
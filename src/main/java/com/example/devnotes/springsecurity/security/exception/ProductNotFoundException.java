package com.example.devnotes.springsecurity.security.exception;


import com.example.devnotes.global.error.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(Long id) {
        super(id + " Product not found");
    }
}

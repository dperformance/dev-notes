package com.example.devnotes.security.basic.exception;


import com.example.devnotes.global.error.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException(Long id) {
        super(id + " Product not found");
    }
}

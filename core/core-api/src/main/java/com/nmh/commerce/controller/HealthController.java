package com.nmh.commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    public ResponseEntity<Object> health() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

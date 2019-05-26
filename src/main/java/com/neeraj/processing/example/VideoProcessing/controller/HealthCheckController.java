package com.neeraj.processing.example.VideoProcessing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author neeraj on 2019-05-26
 * Copyright (c) 2019, VideoProcessing.
 * All rights reserved.
 */
@RestController
public class HealthCheckController {

    @GetMapping
    public ResponseEntity checkHealth() {
        return ResponseEntity.ok("Hey I am healthy!!!!");
    }
}

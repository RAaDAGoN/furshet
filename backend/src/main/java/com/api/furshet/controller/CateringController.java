package com.api.furshet.controller;

import com.api.furshet.domain.entity.Catering;
import com.api.furshet.service.CateringService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/caterings")
@AllArgsConstructor
public class CateringController {
    private final CateringService cateringService;

    @GetMapping
    public ResponseEntity<List<Catering>> getAll() {
        return new ResponseEntity<>(cateringService.findAll(), HttpStatus.OK);
    }
}

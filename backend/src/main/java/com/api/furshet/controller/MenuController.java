package com.api.furshet.controller;

import com.api.furshet.domain.entity.Menu;
import com.api.furshet.domain.entity.Product;
import com.api.furshet.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<List<Menu>> getAll() {
        return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
    }
}

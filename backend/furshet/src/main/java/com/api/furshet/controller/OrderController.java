package com.api.furshet.controller;

import com.api.furshet.domain.entity.Order;
import com.api.furshet.dto.OrderRequestDTO;
import com.api.furshet.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderRequestDTO dto){
        return new ResponseEntity<>(orderService.createOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
//        return new ResponseEntity<>(orderService.update(order), HttpStatus.OK);
//    }

    @DeleteMapping
    public HttpStatus delete(@PathVariable Long id){
        orderService.delete(id);
        return HttpStatus.OK;
    }
}

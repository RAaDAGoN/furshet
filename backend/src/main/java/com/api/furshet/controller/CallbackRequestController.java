package com.api.furshet.controller;

import com.api.furshet.domain.entity.CallbackRequest;
import com.api.furshet.dto.CallbackRequestDto;
import com.api.furshet.service.CallbackRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/callbacks")
@AllArgsConstructor
public class CallbackRequestController {
    private final CallbackRequestService callbackRequestService;

//    @PostMapping
//    public ResponseEntity<List<CallbackRequest>> create(@RequestPart("callback") List<CallbackRequestDto> dtolist) {
//        List<CallbackRequest> callback = dtolist.stream()
//                .map(dto -> callbackRequestService.create(dto))
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(callback, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<CallbackRequest> create(@RequestBody CallbackRequestDto dto) {
        return new ResponseEntity<>(callbackRequestService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CallbackRequest>> getAll() {
        return new ResponseEntity<>(callbackRequestService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping
    public HttpStatus delete(@PathVariable Long id) {
        callbackRequestService.delete(id);
        return HttpStatus.OK;
    }
}

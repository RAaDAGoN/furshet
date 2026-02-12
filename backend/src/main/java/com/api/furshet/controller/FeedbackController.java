package com.api.furshet.controller;

import com.api.furshet.domain.entity.Feedback;
import com.api.furshet.dto.FeedbackDTO;
import com.api.furshet.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> findAll() {
        return new ResponseEntity<>(feedbackService.findAllByTypeFeedbackAndActiveTrue(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Feedback> create(@RequestBody FeedbackDTO feedbackDTO) {
        return new ResponseEntity<>(feedbackService.create(feedbackDTO), HttpStatus.CREATED);
    }
}

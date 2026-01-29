package com.api.furshet.service;

import com.api.furshet.domain.entity.Label;
import com.api.furshet.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public List<Label> findAllActive() {
        return labelRepository.findByActiveTrue();
    }
}

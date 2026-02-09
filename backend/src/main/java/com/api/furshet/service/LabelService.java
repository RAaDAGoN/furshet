package com.api.furshet.service;

import com.api.furshet.domain.entity.Attribute;
import com.api.furshet.domain.entity.Label;
import com.api.furshet.dto.AttributeDTO;
import com.api.furshet.dto.LabelDTO;
import com.api.furshet.repository.LabelRepository;
import com.api.furshet.repository.ProductLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final ProductLabelRepository productLabelRepository;

    public List<Label> findAllActive() {
        return labelRepository.findByActiveTrue();
    }

    public List<Label> findAll(){
        return labelRepository.findAll();
    }

    public Label findById(Long id){
        return labelRepository.findById(id).orElseThrow(() -> new RuntimeException("Attribute not found " + id));
    }

    public Label create(LabelDTO dto) {
        Label label = Label.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .color(dto.getColor())
                .active(dto.getActive())
                .build();

        return labelRepository.save(label);
    }

    public Label update(LabelDTO dto){
        if (dto.getId() != null) {
            Label label = labelRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Label not found " + dto.getId()));

            label.setName(dto.getName());
            label.setCode(dto.getCode());
            label.setColor(dto.getColor());
            label.setActive(dto.getActive());

            return labelRepository.save(label);
        }
        else {
            return create(dto);
        }
    }

    public void delete(Long id){
        Label label = labelRepository.findById(id).orElseThrow(() -> new RuntimeException("Label not found " + id));

        if (Boolean.FALSE.equals(label.getActive())) {return;}

        if (productLabelRepository.existsByLabel_Id(label.getId())) {
            label.setActive(false);
            labelRepository.save(label);
        } else {
            labelRepository.deleteById(id);
        }
    }
}

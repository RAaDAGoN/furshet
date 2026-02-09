package com.api.furshet.service;

import com.api.furshet.domain.entity.Attribute;
import com.api.furshet.domain.entity.Label;
import com.api.furshet.domain.entity.ProductAttribute;
import com.api.furshet.dto.AttributeDTO;
import com.api.furshet.repository.AttributeRepository;
import com.api.furshet.repository.ProductAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final ProductAttributeRepository productAttributeRepository;

    public Attribute create(AttributeDTO attributeDTO) {
        Attribute attribute = Attribute.builder()
                .name(attributeDTO.getName())
                .banOnDeletion(attributeDTO.getBanOnDeletion())
                .build();

        return attributeRepository.save(attribute);
    }

    public Attribute update(AttributeDTO dto){
        if (dto.getId() != null) {
            Attribute attribute = attributeRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Attribute not found " + dto.getId()));

            attribute.setName(dto.getName());
            attribute.setBanOnDeletion(dto.getBanOnDeletion());

            return attributeRepository.save(attribute);
        }
        else {
            return create(dto);
        }
    }

    public List<Attribute> getAttributes() {
        return attributeRepository.findAll();
    }

    public Attribute findById(Long id){
        return attributeRepository.findById(id).orElseThrow(() -> new RuntimeException("Attribute not found " + id));
    }

    public void delete(Long id){
        Attribute attribute = attributeRepository.findById(id).orElseThrow(() -> new RuntimeException("Attribute not found " + id));

        if (Boolean.TRUE.equals(attribute.getBanOnDeletion())) {return;}

        if (productAttributeRepository.existsByAttribute_Id(attribute.getId())) {
            attribute.setBanOnDeletion(true);
            attributeRepository.save(attribute);
        } else {
            attributeRepository.deleteById(id);
        }
    }
}

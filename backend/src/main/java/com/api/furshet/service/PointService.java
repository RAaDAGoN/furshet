package com.api.furshet.service;

import com.api.furshet.domain.entity.Point;
import com.api.furshet.dto.PointDTO;
import com.api.furshet.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public Point create(PointDTO pointDTO) {
        Point point = Point.builder()
                .name(pointDTO.getName())
                .build();

        return pointRepository.save(point);
    }

    public Point update(PointDTO pointDTO) {
        if (pointDTO.getId() != null) {
            Point point = pointRepository.findById(pointDTO.getId()).orElseThrow(() -> new RuntimeException("Point not found " + pointDTO.getId()));

            point.setName(pointDTO.getName());

            return pointRepository.save(point);
        } else {
            return create(pointDTO);
        }
    }

    public List<Point> findAll() {return pointRepository.findAll();}

    public Point findById(Long id) {
        return pointRepository.findById(id).orElseThrow(() -> new RuntimeException("Point not found " + id));
    }

    public void delete(Long id) {
        pointRepository.deleteById(id);
    }
}

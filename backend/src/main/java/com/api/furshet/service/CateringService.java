package com.api.furshet.service;

import com.api.furshet.domain.entity.Catering;
import com.api.furshet.domain.entity.Point;
import com.api.furshet.dto.CateringDTO;
import com.api.furshet.repository.CateringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CateringService {
    private final CateringRepository cateringRepository;
    private final PointService pointService;

    @Value("${upload.path}")
    private String uploadPath;

    private Catering create(CateringDTO cateringDTO, MultipartFile file) {
        Point point = pointService.findById(cateringDTO.getPointId());

        Catering catering = Catering.builder()
                .point(point)
                .typePhoto(cateringDTO.getTypePhoto())
                .build();

        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            catering.setFilename(fileName);
        }

        return cateringRepository.save(catering);
    }

    public Catering update(CateringDTO cateringDTO, MultipartFile file) {
        if (cateringDTO.getId() != null) {
            Catering catering = cateringRepository.findById(cateringDTO.getId()).orElseThrow(() -> new RuntimeException("Catering not found " + cateringDTO.getId()));

            Point point = pointService.findById(cateringDTO.getPointId());

            catering.setPoint(point);
            catering.setTypePhoto(cateringDTO.getTypePhoto());

            if (file != null && !file.isEmpty()) {
                if (catering.getFilename() != null) {
                    File oldFile = new File(uploadPath + "/" + catering.getFilename());
                    if (oldFile.exists() && oldFile.isFile()) {
                        boolean deleted = oldFile.delete();
                        if (!deleted) {
                            System.out.println("Не удалось удалить старый файл: " + oldFile.getAbsolutePath());
                        }
                    }
                }

                String fileName = saveFile(file);
                catering.setFilename(fileName);
            }
            return cateringRepository.save(catering);
        } else {
            return create(cateringDTO, file);
        }
    }

    private String saveFile(MultipartFile file) {
        try {
            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + "_" + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + fileName));

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Catering> findAll() {
        return  cateringRepository.findAll();
    }

    public Catering findById(Long id) {
        return cateringRepository.findById(id).orElseThrow(() -> new RuntimeException("Catering not found " + id));
    }

    public void deleteById(Long id) {
        cateringRepository.deleteById(id);
    }
}

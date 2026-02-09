package com.api.furshet.service;

import com.api.furshet.domain.entity.Menu;
import com.api.furshet.dto.MenuDTO;
import com.api.furshet.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Menu create(MenuDTO dto, MultipartFile file) {
        Menu menu = Menu.builder()
                .name(dto.getName())
                .filename(dto.getFilename())
                .active(dto.getActive())
                .translate(dto.getTranslate())
                .build();

        if(file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            menu.setFilename(fileName);
        }

        return menuRepository.save(menu);
    }

    public Menu update(MenuDTO dto, MultipartFile file) {
        if(dto.getId() != null) {

            Menu menu = menuRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Menu not found " + dto.getId()));

            menu.setName(dto.getName());
            menu.setActive(dto.getActive());
            menu.setTranslate(dto.getTranslate());

            if(file != null && !file.isEmpty()) {
                if(menu.getFilename() != null){
                    File oldFile = new File(uploadPath + "/" + menu.getFilename());
                    if(!oldFile.exists() && oldFile.isFile()){
                        boolean deleted = oldFile.delete();
                        if(!deleted){
                            System.out.println("Could not delete old file " + oldFile.getAbsolutePath());
                        }

                    }
                }

                String fileName = saveFile(file);
                menu.setFilename(fileName);
            }

            return menuRepository.save(menu);
        } else {
            return create(dto, file);
        }
    }

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found " + id));
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
}

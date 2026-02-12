package com.api.furshet.service;

import com.api.furshet.domain.entity.TelegramUsers;
import com.api.furshet.dto.TelegramUsersDTO;
import com.api.furshet.repository.TelegramUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUsersService {
    private final TelegramUsersRepository telegramUsersRepository;

    public TelegramUsers create(TelegramUsersDTO dto) {
        TelegramUsers telegramUsers = TelegramUsers.builder()
                .name(dto.getName())
                .build();

        return telegramUsersRepository.save(telegramUsers);
    }

    public TelegramUsers update(TelegramUsersDTO dto) {
        if (dto.getId() != null) {
            TelegramUsers telegramUsers = telegramUsersRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("User not found " + dto.getId()));

            telegramUsers.setName(dto.getName() != null ? dto.getName() : telegramUsers.getName());

            return telegramUsersRepository.save(telegramUsers);
        } else {
            return create(dto);
        }
    }

    public List<TelegramUsers> findAll() {
        return telegramUsersRepository.findAll();
    }

    public TelegramUsers findById(Long id) {
        return telegramUsersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
    }

    public void delete(Long id) {
        telegramUsersRepository.deleteById(id);
    }
}

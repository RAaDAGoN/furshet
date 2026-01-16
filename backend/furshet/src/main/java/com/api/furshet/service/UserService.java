package com.api.furshet.service;

import com.api.furshet.domain.entity.User;
import com.api.furshet.dto.UserDTO;
import com.api.furshet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(UserDTO dto) {
        User user = User.builder()
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .isActive(dto.getIsActive())
                .build();

        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {userRepository.deleteById(id);}
}

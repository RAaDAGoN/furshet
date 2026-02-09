package com.api.furshet.repository;

import com.api.furshet.domain.entity.TelegramUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUsersRepository extends JpaRepository<TelegramUsers, Long> {
}

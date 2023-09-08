package com.romoshi.bot.repositories;

import com.romoshi.bot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(String chatId);
}

package com.romoshi.bot.services;

import com.romoshi.bot.entity.User;
import com.romoshi.bot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean checkIfUserExists(String chatId) {
        User user = userRepository.findByChatId(chatId);
        return user != null;
    }
}

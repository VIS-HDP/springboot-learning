package com.jwt.demo.service;

import com.jwt.demo.domain.User;
import com.jwt.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }
    public User save(User user){
        return  userRepository.save(user);
    }
}

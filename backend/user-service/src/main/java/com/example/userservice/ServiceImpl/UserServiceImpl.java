package com.example.userservice.ServiceImpl;

import com.example.userservice.Model.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public void saveUser(User user) {


        user.setName(user.getName());
        user.setEmail(user.getEmail());
        user.setTelephone(user.getTelephone());
        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);


    }

}

package com.axel.rollingstone.test.service.impl;

import com.axel.rollingstone.test.config.GlobalException;
import com.axel.rollingstone.test.config.StatusCode;
import com.axel.rollingstone.test.entity.User;
import com.axel.rollingstone.test.repository.UserRepository;
import com.axel.rollingstone.test.service.UserService;
import org.jasypt.util.text.StrongTextEncryptor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user){
        Optional<User> optionalUser = userRepository.findUserByUsername(user.getUsername());
        if(optionalUser.isPresent())
        {
            throw new GlobalException(StatusCode.DUPLICATE,user.getUsername()+ " Already Exist");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if(existingUser == null)
        {
            throw new GlobalException(StatusCode.NOT_FOUND,user.getUsername()+ " Not Found");
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    @Override
    public String login(String username, String password){
        Optional<User> optionalUser = userRepository.findUserByUsernameAndPassword(username,password);
        if(!optionalUser.isPresent()){
            throw new GlobalException(StatusCode.WRONG_CREDENTIAL,"Wrong username/password");
        }

        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword("123");

        return textEncryptor.encrypt(String.valueOf(optionalUser.get().getId()));
    }


    @Override
    public User findUserById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new GlobalException(StatusCode.NOT_FOUND,"User Not Found");
        }
        return optionalUser.get();
    }
}

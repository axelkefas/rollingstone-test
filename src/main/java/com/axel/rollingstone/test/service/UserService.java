package com.axel.rollingstone.test.service;

import com.axel.rollingstone.test.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getUsers();

    User updateUser(User user);

    void deleteUser(int id);

    String login(String username, String password);

    User findUserById(int id);
}

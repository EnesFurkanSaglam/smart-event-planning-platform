package com.efs.backend.Service;


import com.efs.backend.Model.User;

import java.util.List;


public interface IUserService {

    List<User> getUsers();

    User getUserById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    User updateUser(User user);

    User getUserByUsername(String username);

}

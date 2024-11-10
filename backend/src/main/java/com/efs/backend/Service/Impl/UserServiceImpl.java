package com.efs.backend.Service.Impl;

import com.efs.backend.DAO.IUserRepository;
import com.efs.backend.Model.User;
import com.efs.backend.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {

        Optional<User> result = userRepository.findById(id);

        User user = null;
        if (result.isPresent()){
            user = result.get();
        }else{
            return null;
            //excetion fırlatılmasıl lazım
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {

        Optional<User> result = userRepository.getByUsername(username);

        User user = null;
        if (result.isPresent()){
            user = result.get();
        }else{
            return null;
            //excetion fırlatılmasıl lazım
        }
        return user;
    }

    //
//    @Override
//    public void saveUser(User user) {
//        userRepository.save(user);
//    }
//
//    @Override
//    public void deleteUserById(Long id) {
//        userRepository.deleteById(id);
//    }
//
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }



}

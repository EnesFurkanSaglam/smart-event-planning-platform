package com.efs.backend.DAO;

import com.efs.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {

    Optional<User> getByUsername(String username);
}

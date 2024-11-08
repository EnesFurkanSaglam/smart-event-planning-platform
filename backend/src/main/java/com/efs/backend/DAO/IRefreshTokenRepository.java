package com.efs.backend.DAO;

import com.efs.backend.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> getByRefreshToken(String refreshToken);
}

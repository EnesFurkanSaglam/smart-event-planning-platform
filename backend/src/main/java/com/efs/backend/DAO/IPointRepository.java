package com.efs.backend.DAO;

import com.efs.backend.Model.Point;
import com.efs.backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPointRepository extends JpaRepository<Point,Long> {

    @Query(value = "SELECT * FROM point WHERE user_id = ?1",nativeQuery = true)
    Optional <List<Point>> getByUserId(Long userId);






}

package com.efs.backend.DAO;

import com.efs.backend.Model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPointRepository extends JpaRepository<Point,Long> {

}

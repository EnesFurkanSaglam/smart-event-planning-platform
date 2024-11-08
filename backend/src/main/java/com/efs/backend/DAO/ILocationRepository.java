package com.efs.backend.DAO;

import com.efs.backend.Model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<Location,Long> {
}

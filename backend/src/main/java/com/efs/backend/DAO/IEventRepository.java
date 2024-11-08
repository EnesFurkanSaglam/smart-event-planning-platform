package com.efs.backend.DAO;

import com.efs.backend.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event,Long> {
}

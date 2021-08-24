package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.Events;

@Repository
public interface EventAttendanceRepository extends JpaRepository<Events, Integer> {

}

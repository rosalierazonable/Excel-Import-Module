package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.GuestAttendance;

@Repository
public interface GuestAttendanceRepository extends JpaRepository<GuestAttendance, Integer> {

}

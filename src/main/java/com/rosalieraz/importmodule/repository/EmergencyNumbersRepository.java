package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.EmergencyNumbers;

@Repository
public interface EmergencyNumbersRepository extends JpaRepository<EmergencyNumbers, Integer> {

}

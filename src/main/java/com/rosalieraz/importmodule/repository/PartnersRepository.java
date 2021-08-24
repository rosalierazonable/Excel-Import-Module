package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.Partners;

@Repository
public interface PartnersRepository extends JpaRepository<Partners, Integer> {

}

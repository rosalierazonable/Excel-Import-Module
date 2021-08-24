package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.Interests;

@Repository
public interface InterestsRepository extends JpaRepository<Interests, Integer> {

}

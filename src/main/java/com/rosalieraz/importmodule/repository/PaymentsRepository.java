package com.rosalieraz.importmodule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rosalieraz.importmodule.model.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer> {

}

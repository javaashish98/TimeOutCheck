package com.copart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.copart.entity.Emoloyee;

public interface EmployeeDataRepo extends JpaRepository<Emoloyee, Integer> {

}

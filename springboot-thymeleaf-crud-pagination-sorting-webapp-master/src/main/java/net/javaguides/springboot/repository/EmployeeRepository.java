package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

// public interface EmployeeRepository extends JpaRepository<Employee, Long>{
// JpaRepository - Employee  from model package
// Long - is data type of primary key in JPA repository which was "private long id; "
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    // Now we can able to get CRUD data operation for employee entity
    // by using jpa repository

}

package com.example.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.crud.model.Employee;
import com.example.crud.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // we are injecting EmployeeRepository by @Autowired annotation
    @Autowired
    private EmployeeRepository employeeRepository;

    // overriding getAllEmployee() Method
    @Override
    public List<Employee> getAllEmployees() {
        //  findAll() will return list of all employee to the controller
        return employeeRepository.findAll();
    }

    //  2nd step to add saveEmployee Feature
    @Override
    public void saveEmployee(Employee employee) {
        //  SaveEmployee method will save employee in database
        this.employeeRepository.save(employee);
    }

    // 2nd step to implement end to end Update Employee
    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return employee;
    }

    // 2nd step to implement end to end Delete Employee
    @Override
    public void deleteEmployeeById(long id) {
        this.employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }
}

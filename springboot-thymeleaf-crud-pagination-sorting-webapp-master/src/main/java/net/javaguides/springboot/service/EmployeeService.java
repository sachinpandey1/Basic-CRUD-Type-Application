package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Employee;

public interface EmployeeService {

	// getAllEmployees() is a list type method
	List<Employee> getAllEmployees();

	//  1st step to add saveEmployee Feature
	void saveEmployee(Employee employee);   // saveEmployee Method

	// 1st step to implement end to end Update Employee
	Employee getEmployeeById(long id);

	// 1st step to implement end to end Update Employee
	void deleteEmployeeById(long id);

	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}

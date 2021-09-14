package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;

@Controller
public class EmployeeController {

	//  inject service package,  to use Employee Service class
	@Autowired
	private EmployeeService employeeService;

	// For Home Page, all redirect will come here,  return redirect:/
	// viewHomePage display list of employees
	// Creating method handler for home page index.html to display a list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {

		return findPaginated(1, "firstName", "asc", model);
		// model.addAttribute("listEmployee", employeeService.getAllEmployees());
		// return index
	}

	//Add Employee button request will be processed here
	// we have Created a method handler which will handle @{/showNewEmployeeForm}  request
	//  4th step to add saveEmployee Feature,
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		// created employee object
		Employee employee = new Employee();
		//model.addAttribute(Key, Value);
		// Thymleaf template will access this empty employee object for binding from data
		model.addAttribute("employee", employee);
		return "new_employee";
	}

	// 7th step to add saveEmployee Feature,
	//Add Save Employee button
	// we have Created a method handler which will handle @{/saveEmployee}  request
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	// 4th step to implement end to end Update Employee
	// here we call getEmployeeById(id) method from EmployeeServiceImpl
	// we have Created a method handler which will handle @{/showFormForUpdate}  request
	// employee.id from index.html will be used below
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee"; // it will return to update_employee.html
	}

	// 4th step to implement end to end Update Employee
	// /deleteEmployee/{id}  request will get processed here
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
}

package com.example.thymeleafdemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.thymeleafdemo.entity.Employee;
import com.example.thymeleafdemo.service.EmployeeService;


@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	// constructor for constructor injection
	@Autowired /*since we have 1 cosntructor @Wired is optional*/
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
		// add mapping for "/list"
	
		@GetMapping("/list")
		public String listEmployees(Model theModel) {
			
			// get employees from DB
			List<Employee>theEmployees=employeeService.findAll();
			
			// add to the spring model
			theModel.addAttribute("employees",theEmployees);
			
			// this is what I will return through html file in templates folder
			return"employees/list-employees";
		}
		
		//This is a code for showing a Form for Adding new Employee - employee-form.html
		
		@GetMapping("/showFormForAdd")
		public String showFormForAdd(Model theModel) {
			
			// Create model attribute to bind form data
			
			Employee theEmployee=new Employee();
			
			theModel.addAttribute("employee", theEmployee);
			
			//this is a path to the employee-form.html file that will be returned (it is sitting in the employees folder in Templates folder)
			return "employees/employee-form";	
		}
		
		//This is a code for showing a Form for Updating new Employee - employee-form.html
		
		@GetMapping("/showFormForUpdate")
		public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
			
			// get employee from the service
			Employee theEmployee = employeeService.findById(theId);
			
			// set employee as a model attribute to pre-populate the form
			theModel.addAttribute("employee", theEmployee);
			
			// send to over to the form
			
			return "employees/employee-form";	
			
		}
			
		// Saving Employee to the Database
		
		@PostMapping("/save")
		public String saveEmployee(@ModelAttribute("employee")Employee theEmployee) {
			
			// save the employee
			employeeService.save(theEmployee);
			
			// use a redirect to prevent from duplicate submission
			
			return "redirect:/employees/list";	
		}
		
		
		//Deleting new Employee - employee-form.html
		@GetMapping("/delete")
		public String delete(@RequestParam("employeeId") int theId) {
			
			// delete employee
			employeeService.deleteById(theId);
			
			// send to over to the form
			return "redirect:/employees/list";

	}
}
	



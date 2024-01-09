package com.gl.employeemanagementsystem.controller;

import com.gl.employeemanagementsystem.model.Employee;
import com.gl.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/", "/list"})
    public String listEmployees(@RequestParam(name = "query", defaultValue = "") String query, Model model) {
        try {
            List<Employee> employees;
            if (query != null && !query.isEmpty()) {
                employees = employeeService.getAllEmployees();
            } else {
                employees = employeeService.searchEmployees(query);
            }
            model.addAttribute("employees", employees);
            model.addAttribute("searchQuery", query);
            return "employee/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching Employees: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @GetMapping("/create")
    public String createEmployeeForm(Model model) {
        try {
            model.addAttribute("formHeading", "Add a New Employee");
            model.addAttribute("employee", new Employee());
            return "employee/create";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding Employee: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "employee/create";
            }
            employeeService.saveEmployee(employee);
            return "redirect:/employees/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating Employee: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            employee.ifPresent(value -> model.addAttribute("employee", value));
            model.addAttribute("formHeading", "Edit Employee");
            return "employee/edit";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading Employee details for editing: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @PostMapping("/edit")
    public String updateEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("formHeading", "Edit Employee");
                return "employee/edit";
            }
            employeeService.saveEmployee(employee);
            model.addAttribute("successMessage", "Employee details updated successfully");
            return "redirect:/employees/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating Employee details: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model) {
        try {
            employeeService.deleteEmployee(id);
            return "redirect:/employees/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting Employee: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        try {
            List<Employee> employees = employeeService.searchEmployees(query);
            model.addAttribute("employees", employees);
            model.addAttribute("searchQuery", query); // Pass the search query to the view
            return "employee/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error searching for employees: " + e.getLocalizedMessage());
            return "employee/error";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "employee/error";
    }
}

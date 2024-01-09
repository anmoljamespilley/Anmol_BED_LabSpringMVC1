package com.gl.employeemanagementsystem.service;

import com.gl.employeemanagementsystem.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    void saveEmployee(Employee employee);

    void deleteEmployee(Long id);

    List<Employee> searchEmployees(String query);

    void updateEmployee(Long id, Employee updatedEmployee);
}

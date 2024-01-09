package com.gl.employeemanagementsystem.serviceimpl;

import com.gl.employeemanagementsystem.exception.EmployeeNotFoundException;
import com.gl.employeemanagementsystem.model.Employee;
import com.gl.employeemanagementsystem.repository.EmployeeRepository;
import com.gl.employeemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String query) {
        // Implement the search logic based on your requirements
        // For example, you can use the employeeRepository to perform a search query
        // This is a placeholder and needs to be adapted based on your data structure
        return employeeRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query);
    }

    @Override
    public void updateEmployee(Long id, Employee updatedEmployee) {
        // Retrieve the existing employee from the database
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);

        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();

            // Update the fields of the existing employee with the new values
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setEmail(updatedEmployee.getEmail());

            // Save the updated employee
            employeeRepository.save(existingEmployee);
        } else {
            // Handle the case where the employee with the given id is not found
            throw new EmployeeNotFoundException(STR."Employee not found with id: \{id}");
        }
    }
}

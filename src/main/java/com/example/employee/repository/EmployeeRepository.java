// Write your code here
package com.example.employee.repository;

import java.util.ArrayList;

import com.example.employee.model.Employee;

/**
 * EmployeeRepository
 */
public interface EmployeeRepository {

    ArrayList<Employee> getEmployees();
    Employee addEmployee(Employee employee);
    Employee getEmployeeById(int employeeId);
    Employee updateEmployee(int employeeId, Employee employee);
    void deleteEmployee(int employeeId);
    
}
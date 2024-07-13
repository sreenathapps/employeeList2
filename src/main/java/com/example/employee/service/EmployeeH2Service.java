/*
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.*;
 * 
 */

// Write your code here
package com.example.employee.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.employee.model.*;
import com.example.employee.repository.EmployeeRepository;

import java.util.*;

@Service
public class EmployeeH2Service implements EmployeeRepository {
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<Employee> getEmployees() {
        List<Employee> employeeList = db.query("SELECT * FROM EMPLOYEELIST", new EmployeeRowMapper());
        ArrayList<Employee> employees = new ArrayList<>(employeeList);
        return employees;
    }
    @Override
    public Employee addEmployee(Employee employee) {

        db.update("INSERT INTO EMPLOYEELIST(employeeId,employeeName, email, department) VALUES (?, ?, ?, ?)", employee.getEmployeeId(),employee.getEmployeeName(), employee.getEmail(), employee.getDepartment());
        Employee savedEmployee = db.queryForObject("SELECT * FROM EMPLOYEELIST WHERE EMPLOYEEID = ? EMPLOYEENAME = ? ", new EmployeeRowMapper() ,employee.getEmployeeId(), employee.getEmployeeName());
        return savedEmployee;
    }
    @Override
    public Employee getEmployeeById(int employeeId) {
        try {
            Employee employee = db.queryForObject("SELECT * FROM EMPLOYEELIST WHERE EMPLOYEEID = ? ", new EmployeeRowMapper(), employeeId);
            return employee;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public Employee updateEmployee(int employeeId, Employee employee) {
        if (employee.getEmployeeName() != null) {
            db.update("UPDATE EMPLOYEELIST SET EMPLOYEENAME = ? WHERE EMPLOYEEID = ?",employee.getEmployeeName(), employeeId);
        }
        if(employee.getEmail()!= null) {
            db.update("UPDATE EMPLOYEELIST SET EMAIL = ? WHERE EMPLOYEEID = ?", employee.getEmail(), employeeId );
        }
        if (employee.getDepartment() != null) {
            db.update("UPDATE EMPLOYEELIST SET DEPARTMENT = ? WHERE EMPLOYEEID = ? ", employee.getDepartment(), employeeId);
        }
        return  getEmployeeById(employeeId);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        Employee employee = getEmployeeById(employeeId);
        db.update("DELETE FROM EMPLOYEELIST WHERE EMPLOYEEID = ? ", employeeId);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    
}
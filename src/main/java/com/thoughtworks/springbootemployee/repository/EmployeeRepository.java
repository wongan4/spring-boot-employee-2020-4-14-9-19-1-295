package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByGender();


//    private Map<Integer, Employee> idEmployeeMap;
//
//    public Map<Integer, Employee> getIdEmployeeMap() {
//        return idEmployeeMap;
//    }
//
//    public void setIdEmployeeMap(Map<Integer, Employee> idEmployeeMap) {
//        this.idEmployeeMap = idEmployeeMap;
//    }
//
//    public List<Employee> getEmployees() {
//        return new ArrayList<>(this.idEmployeeMap.values());
//    }
//
//    public Employee getEmployeeById(int employeeId) {
//        return this.idEmployeeMap.get(employeeId);
//    }
//
//    public void addEmployee(Employee employee) {
//        this.idEmployeeMap.put(employee.getId(), employee);
//    }

//    public boolean isContainsEmployeeId(int id) {
//        return this.idEmployeeMap.containsKey(id);
//    }
//
//    public void updateEmployee(Employee employee) {
//        this.idEmployeeMap.put(employee.getId(), employee);
//    }
//
//    public void deleteEmployeeById(int employeeId) {
//        this.idEmployeeMap.remove(employeeId);
//    }
}

package com.example.avaliacao.service;

import com.example.avaliacao.domain.Employee;
import com.example.avaliacao.domain.Pessoa;
import com.example.avaliacao.repositories.EmployeeRepository;
import com.example.avaliacao.repositories.PessoaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository= employeeRepository;
    }

    public Optional<Employee> findById(long id) {
        return this.employeeRepository.findById(id);
    }

    public List<Employee> findAll() {
            return (List<Employee>) employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        log.info(String.format(Locale.getDefault(),"save: employee.getId(): %d", employee.getId()));
        return employeeRepository.save(employee);
    }
}

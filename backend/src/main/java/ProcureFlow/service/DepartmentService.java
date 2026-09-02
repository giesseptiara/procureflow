package ProcureFlow.service;

import ProcureFlow.entity.Department;
import ProcureFlow.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import ProcureFlow.dto.CreateDepartmentRequest;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(CreateDepartmentRequest request) {

        if (departmentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department already exists"
            );
        }

        Department department = new Department(request.getName());

        return departmentRepository.save(department);
    }

    public Department updateDepartment(
            Long id,
            CreateDepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department not found"
                ));

        if (departmentRepository.existsByNameIgnoreCase(request.getName())
                && !department.getName().equalsIgnoreCase(request.getName())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Department already exists"
            );
        }

        department.setName(request.getName());

        return departmentRepository.save(department);
    }

    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Department not found"
                ));

        departmentRepository.delete(department);
    }
}
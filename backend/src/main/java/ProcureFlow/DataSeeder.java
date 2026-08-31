package ProcureFlow;

import ProcureFlow.entity.Department;
import ProcureFlow.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDepartments(DepartmentRepository departmentRepository) {
        return args -> {

            if (departmentRepository.count() == 0) {
                departmentRepository.save(new Department("IT"));
                departmentRepository.save(new Department("Finance"));
                departmentRepository.save(new Department("Human Resources"));
                departmentRepository.save(new Department("Purchasing"));
            }
        };
    }
}
package com.company.student_management_system.repository;

import com.company.student_management_system.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    boolean existsByUsername(String username);

}

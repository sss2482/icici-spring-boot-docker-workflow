package com.icici.demo.repos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

//import com.icici.demo.entities.Transactions;
import com.icici.demo.entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByName(String name);

    Page<Users> findAll(Pageable pageable);
}
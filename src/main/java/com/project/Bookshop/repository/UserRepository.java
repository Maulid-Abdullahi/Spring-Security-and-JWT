package com.project.Bookshop.repository;

import com.project.Bookshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);
}

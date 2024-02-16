package com.example.cleanarch.infrastructor.db.persistence.jpa;

import com.example.cleanarch.infrastructor.db.persistence.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserJpaRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

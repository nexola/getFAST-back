package com.univercode.getfast.repositories;

import com.univercode.getfast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmailAndDeletedFalse(String username);

    User findByEmailAndPasswordAndDeletedFalse(String email, String password);
}
